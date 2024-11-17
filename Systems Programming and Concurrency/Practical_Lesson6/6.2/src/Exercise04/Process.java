
package Exercise04;

import java.util.Random;

public class Process extends Thread {
    private Control c;
    private int myCode;
    public Process(int code, Control c){
        this.c = c;
        this.myCode = code;
    }
    @Override
    public void run(){
        Random r = new Random();
        try { 
        while(true){
            int id = r.nextInt(Control.NUM_RESOURCES);
            int qty = r.nextInt(Control.TOTAL_RESOURCES[id])+1;
            c.provideRes(myCode, id, qty);
            sleep(r.nextInt(1000));
            c.releaseRes(myCode, id, qty);
        } } catch(Exception e){
            e.printStackTrace();
        }
    }
}
