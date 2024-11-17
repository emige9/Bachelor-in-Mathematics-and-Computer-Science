package Exercise03;

import java.util.concurrent.Semaphore;

public class TableSemaphores extends Table {
	
	private Semaphore agentSem = new Semaphore(1, true);
	private Semaphore smokersSem[] = new Semaphore[Ingredients.values().length];
	
	public TableSemaphores() {
		for(int i=0; i<smokersSem.length; i++) {
			smokersSem[i] = new Semaphore(0, true);
		}
	}

	@Override
	public void lookingForEverythingBut(Ingredients ing) throws InterruptedException {
		smokersSem[ing.ordinal()].acquire();
		
	}

	@Override
	public void smokingFinished() {
		System.out.println("Smoker finishes to smoke.");
		agentSem.release();
		
	}

	@Override
	public void putIngredientsExcept(Ingredients i) throws InterruptedException {
		agentSem.acquire();
		System.out.println("Agent puts all but "+ i);
		smokersSem[i.ordinal()].release();
	}

}
