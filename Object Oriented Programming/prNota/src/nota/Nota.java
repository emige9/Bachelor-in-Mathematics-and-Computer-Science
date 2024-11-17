package nota;

public class Nota {
	
	public static double NotaMemo(double[][] c, int H) {
		int n = c.length;
		Double[][] N = new Double[n+1][H+1];
		
		return notaMemoRec(c,H,N,1,H);
	}
	
	public static double notaMemoRec(double[][]c, int H, Double[][] N, int i, int j) {
		int n=c.length;
		if(N[i][j]==null) {
			if(i==n) {
				N[i][j]=0;
			}else {
				Double max = Double.MIN_VALUE;
				
				for (int k=0; k<=n; k++) {
					double opcion = c[i][k] + notaMemoRec(c,H,N,i+1,j-k);
					if(opcion>max) {
						max=opcion;
					}
				}
				N[i][j]=max;
			}
		}
		return N[i][j];
	}
}
