
public class Pair {
	private int first;
	private int second;
	
	public Pair(int x,int y) {
		first = x;
		second = y;
	}
	
	public int getFirst() {
		return first;
	}
	
	public int getSecond() {
		return second;
	}
	
	public String toString() {
		return "(" + first + "," + second + ")";
	}
	
	public boolean equals (Object obj) {
		
		return (obj instanceof Pair) && (first == ((Pair) obj).first)  && (second == ((Pair) obj).second);
	}
	
	public int hashCode() {
		return 7*Integer.hashCode(first) + 13*Integer.hashCode(second);
	}

}
