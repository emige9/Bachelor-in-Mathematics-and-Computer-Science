package prDatos;
import java.util.Arrays;

public class Datos {
	private double[] datos;
	private String[] errores;
	private double min;
	private double max;
	
	public Datos(String[] data, double min, double max) {
		pasarDatos(data);
		this.min=min;
		this.max=max;
	}
	
	public double calcMedia() {
		double media=0;
		int n=0;
		for(int i=0; i<datos.length;i++) {
			if(datos[i]>=min && datos[i]<=max) {
				media+=datos[i];
				n++;
			}
		}
		
		if(n==0) {
			throw new DatosException("No hay datos en el rango especificado");
		}
		
		return media/n;
	}
	
	public double calcDesvTipica() {
		double desviacion=0;
		double media=this.calcMedia();
		int n=0;
		
		for(int i=0; i<datos.length; i++) {  //for(double d : datos)
			if(datos[i]>=min && datos[i]<=max) {
				desviacion+=Math.pow(datos[i]-media, 2);
				n++;
			}
		}
		return Math.sqrt(desviacion/n);
	}
	
	public void setRango(String rango) {
		try {
			min=Double.parseDouble(rango.substring(0, rango.indexOf(";")));
			max=Double.parseDouble(rango.substring(rango.indexOf(";")+1));
			} catch(IndexOutOfBoundsException ie) {
				throw new DatosException("Error en los datos al establecer el rango" + "("+ rango + ")");
			} catch(NumberFormatException ne) {
				throw new DatosException("Error en los datos al establecer el rango" + "("+ rango + ")");
			}
	}
	
	public double[] getDatos() {
		return datos;
	}
	
	public String[] getErrores() {
		return errores;
	}
	
	public String toString() {
		String res= "Min: " + min + ", Max: " + max + ", ";
		res+=Arrays.toString(datos) + ", ";
		res+=Arrays.toString(errores) + ", ";
		
		try {
			res+="Media: " + calcMedia() + ", ";
		} catch(DatosException e) {
			res+="Media: ERROR" + ", ";
		}
		
		try {
			res+="DesvTipica: " + calcDesvTipica();
		}catch(DatosException e) {
			res+="DesvTipica: ERROR";
		}
		
		return res;
	}
	
	private void pasarDatos(String[] data) {
		datos=new double[data.length];
		errores=new String[data.length];
		int nDatos=0;
		int nErrores=0;
		
		for(String dat : data) {
			try {
				datos[nDatos]=Double.parseDouble(dat);
				nDatos++;
			} catch(NumberFormatException exception) {
				errores[nErrores]=dat;
				nErrores++;
			}
		}
		datos=Arrays.copyOf(datos, nDatos);
		errores=Arrays.copyOf(errores, nErrores);
	}
}
