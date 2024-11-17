import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class Maze {
	public static final int COLUMNS = 80;
	public static final int ROWS = 60;
	private static char[][] maze = new char[ROWS][COLUMNS];
	private static Pair Initial;
	private static Pair Goal;
	
	public Maze (double percentage) throws MazeException {
		if(percentage < 0 || percentage > 1) {
			throw new MazeException ("Invalid percentage");
		}
		
		int numObstacles = (int) (COLUMNS*ROWS*percentage);
		Random randNum = new Random();
		Set<Pair> obstacles = new HashSet<>();
		
		while (obstacles.size() < numObstacles) {
			int x = randNum.nextInt(ROWS);
			int y = randNum.nextInt(COLUMNS);
			obstacles.add(new Pair(x,y));
		}
		
		for(int i=0; i<ROWS; i++) {
			for(int j=0; j<COLUMNS; j++) {
				maze[i][j] = ' ';
			}
		}
		
		int xI = randNum.nextInt(ROWS);
		int yI = randNum.nextInt(COLUMNS);
		Initial = new Pair(xI,yI);
		
		int xG = randNum.nextInt(ROWS);
		int yG = randNum.nextInt(COLUMNS);
		Goal = new Pair(xG, yG);
		
		if(obstacles.contains(Initial) || obstacles.contains(Goal)) {
			throw new MazeException("Initial or Goal state are obstacles"); 
		}
		
		maze[xI][yI] = 'I';
		maze[xG][yG] = 'G';
		
		for(Pair p : obstacles) {
			maze[p.getFirst()][p.getSecond()]= '*';
		}
		
	}
	
	public Set<Pair> neighborNodes(Pair p){
		Set<Pair> neighborNodes = new HashSet<>();
		Pair[] neighbors = new Pair[4];
		
		neighbors[0] = new Pair(p.getFirst()+1, p.getSecond());
		neighbors[1] = new Pair(p.getFirst(), p.getSecond()+1);
		neighbors[2] = new Pair(p.getFirst()-1, p.getSecond());
		neighbors[3] = new Pair(p.getFirst(), p.getSecond()-1);
		
		 
		
		for(int i=0; i < neighbors.length; i++) {
			if(isValid(neighbors[i])) {
				neighborNodes.add(neighbors[i]);
			}
		}
		
		return neighborNodes;
	}
	
	public boolean isValid(Pair p) {
		return p.getFirst()>=0 && 
				p.getFirst()<ROWS && 
				p.getSecond()>=0 && 
				p.getSecond()<COLUMNS && 
				maze[p.getFirst()][p.getSecond()] != '*';
	}
	
	public boolean isGoal(Pair p) {
		return maze[p.getFirst()][p.getSecond()] == 'G';
	}
	
	public Stack<Pair> solutionPath(Map<Pair,Pair> parent, Pair current){
		Stack<Pair> path = new Stack<>();
		
		while (!current.equals(Initial)) {
			path.push(current);
			current = parent.get(current);
		}
		path.push(Initial);
		
		return path;
	}
	
	public void reconstructionPath(Stack<Pair> solution) {
		while(!solution.isEmpty()) {
			Pair current = solution.pop();
			if(!current.equals(Initial) && !current.equals(Goal)) {
				setPath(current.getFirst(), current.getSecond());
			}
		}
	}
	
	public int heuristic(Pair p) {
		return Math.abs(p.getFirst()-Goal.getFirst()) + Math.abs(p.getSecond()-Goal.getSecond());
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		
		for(int i=0; i<ROWS; i++) {
			for(int j=0; j<COLUMNS; j++) {
				sb.append(String.valueOf(maze[i][j]));
			}
			sb.append("\n");
			
		}
		
		return sb.toString();
	}
	
	public Pair getInitial() {
		return Initial;
	}
	
	public Pair getGoal() {
		return Goal;
	}
	
	public void setPath(int x, int y) {
		maze[x][y] = '+';
	}
	
	
	
	public static void main (String args[]) throws FileNotFoundException {
		Maze maz = null;
		PrintWriter pw = new PrintWriter("output.txt"); 
		try {
			maz = new Maze(0.3);
		} catch (MazeException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
		Pair initial = maz.getInitial();
		
		Set<Pair> closedSet = new HashSet<>();
		Set<Pair> openSet = new HashSet<>();
		Map<Pair,Pair> parent = new HashMap<>();
		Map<Pair,Integer> g = new HashMap<>();
		Map<Pair,Integer> f = new HashMap<>();
		openSet.add(initial);
		g.put(initial, 0);
		f.put(initial, maz.heuristic(initial));
		
		Stack<Pair> solution = null;
		
		while(!openSet.isEmpty() && (solution == null)) {
			Pair current = null;
			int valor = 0;
			
			for(Pair p : openSet) {
				int coste = f.get(p);
				
				if(current == null) {
					current = p;
					valor = coste;
				} else if(coste<valor){
					current = p;
					valor = coste;
				}
			}
			
			if(maz.isGoal(current)) {
				solution = maz.solutionPath(parent, current);
			}
			
			openSet.remove(current);
			closedSet.add(current);
			
			for(Pair neighbor : maz.neighborNodes(current)) {
				if(!closedSet.contains(neighbor)) {
					int tentative_g = g.get(current) + 1;
					
					if(!openSet.contains(neighbor) || tentative_g < g.get(neighbor)) {
						parent.put(neighbor,current);
						g.put(neighbor, tentative_g);
						
						f.put(neighbor, g.get(neighbor) + maz.heuristic(neighbor));
						
						if(!openSet.contains(neighbor)) {
							openSet.add(neighbor);
						}
					}
				}
				
				
			}
		}
		
		if(openSet.isEmpty()) {
			System.out.println("This maze doesn't have solution");
			pw.println("This maze doesn´t have solution");
		} else {
			maz.reconstructionPath(solution);
		}
		
		System.out.println(maz.toString());
		pw.println(maz.toString());
		pw.close();
	}
	

}




