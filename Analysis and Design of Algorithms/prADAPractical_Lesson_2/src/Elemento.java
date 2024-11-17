
public class Elemento {
		//Clase de elementos no ordenables
		private int at1;
		private int at2;
		
		public Elemento() {
			at1 = 0;
			at2 = 0;
		}
		public Elemento(int at1, int at2) {
			this.at1 = at1;
			this.at2 = at2;
		}
		//...Otros métodos de la clase...
		
		@Override
		public boolean equals(Object o) {
			return (o instanceof Elemento && at1==((Elemento)o).at1 && at2 == ((Elemento)o).at2 );
		}
		@Override
		public int hashCode() {
			return at1 + at2;
		}
		@Override
		public String toString() {
			return "(" + at1 + "," + at2 + ")";
		}
}
