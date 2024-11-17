public class DEC extends EXP{
    

    public DEC(AST declaracion, AST d, TIPO t){
        super(declaracion,d);
        this.tipo = t;
    }

    public void ctd(){
       if(izq!=null){
       		izq.ctd();
       }
       if(der!=null){
       		der.ctd();
       }
    }
}
