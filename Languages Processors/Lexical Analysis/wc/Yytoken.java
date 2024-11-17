public class Yytoken {
    public final static int PALABRA = 127;
    public final static int CARACTER = 128;
    public final static int EOLN = 131;

    private int token;
    private String valor;

    public Yytoken(int token, String valor) {
         this.token = token;
         this.valor = valor;
    }

    public int getToken()  {
         return token;
    }

    public String getValor() {
         return valor;
    }
    
    public int getCaracteres(){
    	 return valor.length();
    }

    public String toString() {
         return "<"+token+","+valor+">";
    }
}
