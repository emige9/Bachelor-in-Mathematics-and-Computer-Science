public class Yytoken {
    public final static int A = 127;
    public final static int B = 128;
    public final static int C = 129;
    public final static int D = 130;
    public final static int EOLN = 131;

    private int token;
    private int valor;

    public Yytoken(int token, int valor) {
         this.token = token;
         this.valor = valor;
    }
    public Yytoken(int token, String lexema) {
         this(token, Integer.parseInt(lexema));
    }

    public int getToken()  {
         return token;
    }

    public int getValor() {
         return valor;
    }

    public String toString() {
         return "<"+token+","+valor+">";
    }
}
