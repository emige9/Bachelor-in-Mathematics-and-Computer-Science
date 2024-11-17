/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise04;

/**
 *
 * @author galvez
 */
public class Driver {

    public static void main(String[] args) {
        Control c = new Control();
        for(int i=0;i<5;i++){
            new Process(i, c).start();
        }
    }
}
