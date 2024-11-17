
public class multiplicacionesOptima {
	private int[] fila;
	private int[] columna;
	private Integer[][] M;

	public multiplicacionesOptima(int[] f, int[] c) {
		fila = f;
		columna = c;

		M = new Integer[fila.length][fila.length];
	}

	public int resolverBU() {
		for (int i = M.length - 1; i >= 0; i--) {
			for (int j = i; j < M[0].length; j++) {
				if (i == j) {
					M[i][j] = 0;
				} else {
					int minimo = Integer.MAX_VALUE;

					for (int k = i; k < j; k++) {
						int opcion = M[i][k] + M[k + 1][j] + fila[i] * columna[k] * columna[j];

						if (opcion < minimo) {
							minimo = opcion;
						}
					}
					M[i][j] = minimo;
				}
			}
		}

		return M[0][fila.length - 1];
	}

	private void mostrar() {
		for (int i = 0; i < fila.length; i++) {
			for (int j = 0; j < fila.length; j++) {
				System.out.print(M[i][j] + " ");
			}
			System.out.println();
		}
	}

	public int resolverMemo(int i, int j) {
		if (M[i][j] == null) {
			if (i == j) {
				M[i][j] = 0;
			} else {
				int minimo = Integer.MAX_VALUE;

				for (int k = i; k < j; k++) {
					int opcion = resolverMemo(i, k) + resolverMemo(k + 1, j) + fila[i] * columna[k] * columna[j];
					if (opcion < minimo) {
						minimo = opcion;
					}
				}
				M[i][j] = minimo;
			}
		}

		return M[i][j];
	}

	public static void main(String[] args) {
		int[] fila = { 10, 100, 50 };
		int[] col = { 100, 5, 50 };
		multiplicacionesOptima obj = new multiplicacionesOptima(fila, col);
		System.out.println("Resultado = " + obj.resolverBU());
		System.out.println();
		obj.mostrar();
		System.out.println();
		System.out.println("Resultado = " + obj.resolverMemo(0, col.length-1));


	}
}
