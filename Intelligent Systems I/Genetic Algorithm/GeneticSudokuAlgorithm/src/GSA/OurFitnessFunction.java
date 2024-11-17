package GSA;

import java.util.HashSet;
import java.util.Set;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;

public class OurFitnessFunction extends FitnessFunction{
	
	private int[] sudoku;
	
	private static final long serialVersionUID = 1L;
	
	public OurFitnessFunction(int[] sudoku) {
		this.sudoku = sudoku;
	}

	@Override
	protected double evaluate(IChromosome chromosome) {
		int cont = 0;
		int posHole = 0;
		// hacer copia sudoku con los huecos rellenados por el cromosoma
		
		Gene[] genArray = chromosome.getGenes();
		for(int pos=0; pos<sudoku.length; pos++) {
			if(sudoku[pos] == 0) {
				sudoku[pos] = (Integer) genArray[posHole].getAllele();
				posHole++;
			}	
		}
		
		
		for(int i=0; i<9; i++) {
			Set<Integer> difColumnValues = new HashSet<>();
			for(int j=0; j<9; j++) {
				difColumnValues.add(sudoku[9*j+i]);
			}
			cont+=difColumnValues.size();
		}
		
		for(int i=0; i<9; i=i+3) {
			for(int j=0; j<9; j=j+3) {
				Set<Integer> difBlockValues = new HashSet<>();
				for(int k=0; k<9; k++) {
					difBlockValues.add(sudoku[9*(i+(k/3)) + j + k%3]);
				}
				cont += difBlockValues.size();
			}
		
		}
		
		return cont;
	}

}
