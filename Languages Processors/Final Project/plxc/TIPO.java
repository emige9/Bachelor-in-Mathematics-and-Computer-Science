public class TIPO {
	
	public final static int INT = 1;
	public final static int FLOAT = 2;
	public final static int CHAR = 3;
	public final static int ARRAY = 4;
	public final static int STRING = 5;
	
	public int tipo;
	public TIPO subtipo;
	public int len;
	
	public TIPO(int t){
		tipo=t;
	}
	
	public TIPO(int t, TIPO s, int l){
		tipo=t;
		subtipo=s;
		len=l;
	}
	
	public boolean equals(TIPO t){
		return this.tipo==t.getTipo();
	}
	
	public void setTipo(int t){
		tipo=t;
	}
	
	public int getTipo(){
		return tipo;
	}
	
	public TIPO getSubtipo(){
		return subtipo;
	}
	
	public int getLen(){
		return len;
	}
	
	public boolean esInt(){
		return this.tipo==TIPO.INT;
	}
	
	public boolean esFloat(){
		return this.tipo==TIPO.FLOAT;
	}
	
	public boolean esChar(){
		return this.tipo==TIPO.CHAR;
	}
	
	public boolean esArray(){
		return this.tipo==TIPO.ARRAY;
	}
	
	public boolean esString(){
		return this.tipo==TIPO.STRING;
	}
}

