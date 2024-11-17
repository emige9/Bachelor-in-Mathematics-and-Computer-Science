import java.util.Arrays;

public class Analizador {
	
	 private static int numPruebas = 5;
	 private static Temporizador t = new Temporizador();
	      
     public static void main(String[] args) {
        long n1 = 10;
        long n2 = 20;
            
        double ratio = mediaDePruebas(n2)/mediaDePruebas(n1);

        if (ratio>1000){
             if (ratio<3000000){
                 System.out.println("2N");
             } else{
                 System.out.println("NF");
             }
        } else {
             n1 = 10000;
             n2 = 20000;

             ratio = mediaDePruebas(n2)/mediaDePruebas(n1);
                
             if (6 <= ratio && ratio < 10.0){               
                 System.out.println("N3");
             } else if (3.2 <= ratio && ratio < 6.0){          
                 System.out.println("N2");
             } else if (1.8<=ratio &&ratio<3.2){               
                 System.out.println("NLOGN");
             } else {
                 n1 =1000;
                 n2 =1000000;

                 ratio=mediaDePruebas(n2)/mediaDePruebas(n1);
                    
                 if (700<=ratio&& ratio<1300){
                     System.out.println("N");
                 } else if (ratio<=1.005) {
                     System.out.println("1");
                 }else {
                     System.out.println("LOGN");
                 }
             }
         }
     }
        
     private static double mediaDePruebas(long n) {
         int mediaTiempos=0;

         for (int i = 0 ; i<numPruebas ; i++){
             t.reiniciar();
             t.iniciar();
             Algoritmo.f(n);
             t.parar();
             mediaTiempos += t.tiempoPasado();
            
         }
         return mediaTiempos/(double)numPruebas;
     }
}