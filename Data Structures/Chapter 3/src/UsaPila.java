import dataStructures.stack.Stack;
import dataStructures.stack.ArrayStack;


public class UsaPila {
    public static void main (String[] args){
        Stack<Integer> s = new ArrayStack<>();

        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);
        s.push(5);
        s.push(6);
        s.push(7);
        s.push(8);
        s.push(9);
        s.push(10);

        System.out.println(s);
        System.out.println(size(s));
        System.out.println(s);
    }

    private static int size (Stack<Integer> s){
        int n=0;

        while(!s.isEmpty()){
            n+=1;
            s.pop();
        }
        return n;
    }
}
