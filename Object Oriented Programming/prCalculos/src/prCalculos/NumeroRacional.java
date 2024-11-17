package prCalculos;

public class NumeroRacional {
	private int numerador;
	private int denominador;
	
	public NumeroRacional() {
		numerador=0;
		denominador=1;
	}
	
	public NumeroRacional(int num, int den) {
		
		if(den==0) {
			throw new RuntimeException("El denominador no puede ser 0");
		} else {
			numerador=num;
			denominador=den;
			this.normalizar();
		}
	}
	
	public int getNumerador() {
		return numerador;
	}
	
	public int getDenominador() {
		return denominador;
	}
	
	public NumeroRacional suma(NumeroRacional num) {
		int n=((this.getNumerador()*num.getDenominador()) + (this.getDenominador()*num.getNumerador()));
		int d=this.getDenominador()*num.getDenominador();
		return new NumeroRacional(n,d);
	}
	
	public NumeroRacional resta(NumeroRacional num) {
		int n=((this.getNumerador()*num.getDenominador()) - (this.getDenominador()*num.getNumerador()));
		int d=this.getDenominador()*num.getDenominador();
		return new NumeroRacional(n,d);
	}
	
	public NumeroRacional mult(NumeroRacional num) {
		int n=(this.getNumerador()*num.getNumerador());
		int d=(this.getDenominador()*num.getDenominador());
		return new NumeroRacional(n,d);
	}
	
	public NumeroRacional div(NumeroRacional num) {
		int n=(this.getNumerador()*num.getDenominador());
		int d=(this.getDenominador()*num.getNumerador());
		return new NumeroRacional(n,d);
	}
	
	public String toString() {
		String res="";
		
		if(this.getDenominador()==1) {
			res+=Integer.toString(this.getNumerador());
		}else {
			res+=Integer.toString(this.getNumerador()) + "/" + Integer.toString(this.getDenominador());
		}
		
		return res;
	}
	
	private void normalizar() {
		if(this.denominador!=0 && this.numerador!=0) {
			int max_com_div=mcd(numerador,denominador);
			numerador /= max_com_div;
			denominador /=max_com_div;
			
			if(denominador<0) {
				numerador=-numerador;
				denominador=-denominador;
			}
		}
	}
	
	private static int mcd(int n, int m) {
		n=Math.abs(n);
		m=Math.abs(m);
		
		if(n<m) {
			int x=n;
			n=m;
			m=x;
		}
		
		while (m!=0) {
			int r=n%m;
			n=m;
			m=r;
		}
		
		return n;
	}
}
