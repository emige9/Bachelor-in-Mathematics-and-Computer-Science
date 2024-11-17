package Practice1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;


public class NNproject {
	
	public static final int COLUMNS = 3;
	public static final int ROWS = 1000;
	public static final int ROWS2 = 10000; 
	private static double[][] trainingInput = new double[ROWS][COLUMNS-1];
	private static double[][] trainingOutput = new double[ROWS][1];
	private static double[][] validationInput = new double[ROWS][COLUMNS-1];
	private static double[][] validationOutput = new double[ROWS][1];
	private static double[][] inputTest = new double[ROWS2][COLUMNS-1];
	private static double[][] outputTest = new double[ROWS2][1];
	
	private static double F(double x, double y) {
		return Math.sin(x)*Math.cos(y);
	}
	
	public static void generate(double[][] mInput, double[][] mOutput) {
		Random rand = new Random();
		
		for(int i=0; i<ROWS; i++) {
			for(int j=0; j<COLUMNS; j++) {
				if(j==2) {
					mOutput[i][0] = F(mInput[i][0],mInput[i][1]);
				} else {
					mInput[i][j] = -Math.PI + (2*Math.PI)*rand.nextDouble();
					
				}
			}
		}
	}
	
	public static void testgen(double[][] inputTest, double[][] outputTest) {
		double CONSTANT = 2*Math.PI/99;
		for(int i= 0; i<100; i++) {
			for(int j = 0; j <100; j++) {
				inputTest[i*100 + j][0] = -Math.PI + i*CONSTANT;
				inputTest[i*100 + j][1] = -Math.PI + j*CONSTANT;
				outputTest[i*100 + j][0] = F(inputTest[i*100 + j][0], inputTest[i*100 + j][1]);
			}
		}
	}
	
	public static double validationError(MLDataSet validationSet, BasicNetwork network) {
		double error = 0;
		
		for(MLDataPair pair : validationSet) {
			final MLData output = network.compute(pair.getInput());
			error += Math.pow(output.getData(0) - pair.getIdeal().getData(0), 2);
		}
		
		return error;
	}
	

	
	public static void main(String args[]) throws FileNotFoundException {
		PrintWriter txt = new PrintWriter("output.txt");
		
		BasicNetwork network = new BasicNetwork();
		network.addLayer(new BasicLayer(null, true, 2)) ;
		network.addLayer(new BasicLayer(new ActivationTANH(), true, 7 ));
		network.addLayer(new BasicLayer(new ActivationTANH(), false, 1 ));
		network.getStructure().finalizeStructure();
		network.reset();
		
		generate(trainingInput, trainingOutput);
		generate(validationInput, validationOutput);
		
		MLDataSet trainingSet = new BasicMLDataSet(trainingInput, trainingOutput);
		MLDataSet validationSet = new BasicMLDataSet(validationInput, validationOutput);
		
		ResilientPropagation train = new ResilientPropagation(network, trainingSet);
		
		
		final Grafica gr = new Grafica ("Training and validation set errors",
				"Training and validation set error",
				"Epochs",
				"Error",
				"%.0f",
				"%.5f");
		Grafica.Linea ln = gr.new Linea("Training error");
		Grafica.Linea ln2 = gr.new Linea("Validation error");

		
		double trainingError;
		int epoch = 1 ;
		System.out.println("Neural Network Results: ");
		txt.println("Neural Network Results: ");
		do {
		train.iteration();
		trainingError = train.getError();
		double valError = validationError(validationSet, network)/(double)ROWS;
		System.out.println("Epoch #" + epoch + " Training Error : " + trainingError);
		txt.print("Epoch #" + epoch + " Training Error : " + trainingError);
		epoch++;
		System.out.println("Validation Error: " + valError);
		txt.println(" Validation Error: " + valError);
		ln.anadeDatos(epoch, trainingError);
		ln2.anadeDatos(epoch, valError);
		} while (trainingError>0.02);
		txt.close();
		
		
		testgen(inputTest,outputTest);
		MLDataSet testSamples = new BasicMLDataSet(inputTest, outputTest);
		double testSamplesError = validationError(testSamples, network)/(double)ROWS2;
		System.out.println("Mean squarred error for the test samples: " + testSamplesError);
		
		PrintWriter graf3D = new PrintWriter("graf3D.txt");
		for(int i=0; i<ROWS2; i++) {
			graf3D.println("x: " + inputTest[i][0] + " y: " + inputTest[i][1] + " F(x,y): " + outputTest[i][0]);
		}
		graf3D.close();
	}
	

}

