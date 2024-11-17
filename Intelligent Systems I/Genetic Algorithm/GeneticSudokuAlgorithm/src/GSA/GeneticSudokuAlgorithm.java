package GSA;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.Gene;
import org.jgap.GeneticOperator;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.ChromosomePool;
import org.jgap.impl.GaussianRandomGenerator;
import org.jgap.impl.IntegerGene;

import com.qqwing.QQWing;

public class GeneticSudokuAlgorithm {
	
	private static int numHoles(int[] sudokuArray) {
		int cont=0;
		
		for(int i=0; i<sudokuArray.length; i++) {
			if(sudokuArray[i] == 0) {
				cont++;
			}
		}
		
		return cont;
	}
	
	private static Integer[] randChromosome(int holes, int[] sudokuArray) {
		ArrayList<Integer> res =  new ArrayList<>();
		for(int i=0; i<9; i++) {
			ArrayList<Integer> row = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
			for(int j=0; j<9; j++) {
				
				if(sudokuArray[i*9+j] != 0) {
					row.remove(Integer.valueOf(sudokuArray[i*9+j]));
				}
			}
			Collections.shuffle(row);
			res.addAll(row);
		}
		Integer[] rowNumbers = new Integer[res.size()];
		rowNumbers = res.toArray(rowNumbers);
		return rowNumbers;
	}
	
	private static Genotype fillPopulation (Configuration conf, Chromosome sampleChromosome, int holes, int[] sudoku) throws InvalidConfigurationException {
		Population population = new Population(conf, 20);
		ArrayList<Chromosome> newChromosomes = new ArrayList<>();
		Genotype res;
		
		for(int i=0; i<20; i++) {
			Chromosome newChromosome = (Chromosome) sampleChromosome.clone();
			Integer[] row = randChromosome(holes, sudoku);
			
			for(int j=0; j<holes; j++) {
				newChromosome.getGene(j).setAllele(row[j]);
			}
			newChromosomes.add(newChromosome);
		}
		population.setChromosomes(newChromosomes);
		res = new Genotype(conf, population);
		return res;
	}
	
	public static int[] chromosomeToArray(IChromosome chromosome) {
        int[] res = new int[chromosome.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = (int) chromosome.getGene(i).getAllele();
        }
        return res;
    }
	
	private static int[] chromosomeIntoSudoku(IChromosome chromosome, int[] sudoku) {
		int[] chromosomeArray = chromosomeToArray(chromosome);
        int[] mainSudoku = sudoku;
        int j = 0;
        for (int i = 0; i < mainSudoku.length; i++) {
            if (mainSudoku[i] == 0) {
                mainSudoku[i] = chromosomeArray[j++];
            }
        }
        return mainSudoku;
	}
	
	public static void main(String[] args) throws InvalidConfigurationException, FileNotFoundException {
		QQWing MySudoku = new QQWing();
		int holes;
		Configuration conf = new Configuration();
		conf.setPreservFittestIndividual(true);
		PrintWriter pw = new PrintWriter("output.txt");
		
		
		System.out.println("We generate a 9x9 that is a valid sudoku puzzle:");
		pw.println("We generate a 9x9 that is a valid sudoku puzzle:");
		MySudoku.generatePuzzle();
		MySudoku.printPuzzle();
		int[] sudokuArray = MySudoku.getPuzzle();
		pw.println(MySudoku.getPuzzleString());
		holes = numHoles(sudokuArray);
		
		OurFitnessFunction myFunc = new OurFitnessFunction(sudokuArray);
		conf.setMinimumPopSizePercent(0);
        conf.setKeepPopulationSizeConstant(false);
        conf.setChromosomePool(new ChromosomePool());
        conf.setSelectFromPrevGen(1D);
        conf.setPopulationSize(20);//population is a set of generated sudokus in one evolution
        GeneticOperator myMutationOperator = new MutationOp(conf, 20);
		conf.addGeneticOperator(myMutationOperator);//our mutation operator
        GeneticOperator myCrossoverOperator = new CrossoverOp(conf,20);
		conf.addGeneticOperator(myCrossoverOperator);//our crossover operator
        conf.setRandomGenerator(new GaussianRandomGenerator());// it's necessary
        conf.setEventManager(new EventManager());
        conf.setFitnessEvaluator(new DefaultFitnessEvaluator());
        conf.addNaturalSelector(new BestChromosomesSelector(conf, 0.9D), false);
        conf.setPreservFittestIndividual(true);
		conf.setFitnessFunction(myFunc);
		Gene[] sampleGenes = new Gene[holes];
		
		for(int i=0; i<holes; i++) {
			sampleGenes[i] = new IntegerGene(conf, 1, 9);
		}
		
		Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);
		conf.setSampleChromosome(sampleChromosome);
		
		Genotype genotype;
		genotype = fillPopulation(conf, sampleChromosome, holes, sudokuArray);
		System.out.println("This is the initial population");
		pw.println("This is the initial population");
		
        java.util.List<IChromosome> initialPop = genotype.getPopulation().getChromosomes();
        
        for(IChromosome chromosome : initialPop) {
			System.out.println(Arrays.toString(chromosomeToArray(chromosome)));
			pw.println(Arrays.toString(chromosomeToArray(chromosome)));
		}
        
        double bestFitness = 0;
        for (IChromosome chromosome : initialPop) {
            if (bestFitness < chromosome.getFitnessValue()) {
                bestFitness = chromosome.getFitnessValue();
                System.out.println(bestFitness);
                pw.println(bestFitness);
            }
        }

        System.out.println("These are the evolutions so far");
        pw.println("These are the evolutions so far");
        for (int i = 0; i < 20; i++) {
            genotype.evolve();
            IChromosome bestChromosome = genotype.getPopulation().determineFittestChromosome();//list of evolved chromosomes,
            if (bestFitness < bestChromosome.getFitnessValue()) {
                bestFitness = bestChromosome.getFitnessValue();
            
                System.out.println(bestFitness);
                pw.println(bestFitness);
            }
            if (bestFitness == 162) break;
        }
        IChromosome bestSolutionSoFar = genotype.getFittestChromosome();
        System.out.println("The best solution has a fitness value of " +
                bestSolutionSoFar.getFitnessValue());
        pw.println("The best solution has a fitness value of " +
                bestSolutionSoFar.getFitnessValue());
        MySudoku.setPuzzle(chromosomeIntoSudoku(bestSolutionSoFar, sudokuArray));
        MySudoku.printPuzzle();
        pw.println(MySudoku.getPuzzleString());
	    
	    System.out.println("We solve the sudoku. This is the solution (QQWing):");
	    pw.println("We solve the sudoku. This is the solution (QQWing):");
	    MySudoku.solve();
	    MySudoku.printSolution();
	    pw.println(MySudoku.getSolutionString());
	    pw.close();
	}
	
}
