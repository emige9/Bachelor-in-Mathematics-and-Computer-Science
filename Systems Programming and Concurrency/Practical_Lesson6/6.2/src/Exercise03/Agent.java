package Exercise03;

import java.util.Random;

public class Agent extends Thread {
	private Random rnd = new Random();
	private Table t;
	public Agent(Table t) {
		this.t = t;
	}

	// This function returns a random ingredient
	private Ingredients randomIngredient() {
		return Ingredients.values()[rnd.nextInt(Ingredients.values().length)];
	}
	
	@Override
	public void run(){
		while(true){
			try {
				Ingredients ing = randomIngredient();
				System.out.println("Agent is putting everything but "+ing);
				t.putIngredientsExcept(ing);
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
