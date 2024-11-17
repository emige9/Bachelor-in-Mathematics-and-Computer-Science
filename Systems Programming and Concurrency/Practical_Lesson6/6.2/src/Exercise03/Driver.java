package Exercise03;

import java.util.ArrayList;
import java.util.List;

public class Driver {

	public static void main(String[] args) {
		Table t = new TableSemaphores();
		Agent a = new Agent(t);
		List<Smoker> smokers = new ArrayList<Smoker>();
		for(Ingredients ing: Ingredients.values()){
			smokers.add(new Smoker(ing, t));
		}
		a.start();
		for(Smoker s: smokers)
			s.start();
	}

}
