package Exercise03;

public class TableMonitors extends Table{
	
	private Ingredients missingIng = null;

	@Override
	public synchronized void lookingForEverythingBut(Ingredients ing) throws InterruptedException {
		while(ing != missingIng) {
			wait();
		}
		
		
	}

	@Override
	public synchronized void smokingFinished() {
		missingIng = null;
		notifyAll();
		System.out.println("Smoker finishes to smoke.");
	}

	@Override
	public synchronized void putIngredientsExcept(Ingredients i) throws InterruptedException {
		
		while(missingIng != null) {
			wait();
		}
		
		missingIng = i;
		System.out.println("Agent puts all but "+ i);
		notifyAll();
		
	}
}
