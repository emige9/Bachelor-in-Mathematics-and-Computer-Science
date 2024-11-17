package colas;

import dataStructures.queue.EmptyQueueException;
import dataStructures.queue.Queue;
import pilas.MyLinkedStack;

import java.util.StringJoiner;

public class MyLinkedQueue<T> implements Queue<T> {

    private static class Node<E> {
        E elem;
        MyLinkedQueue.Node<E> next;

        public Node(E x, MyLinkedQueue.Node<E> nextNode){
            elem = x;
            next = nextNode;
        }
    }

    private Node<T> first;
    private Node<T> last;

    public  MyLinkedQueue(){
        first = null;
        last = null;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public void enqueue(T x) {
        Node<T> nuevo = new Node<>(x,null);
        if(last != null){
            last.next = nuevo;
        } else {
            first = nuevo;
        }
        last= nuevo;
    }

    @Override
    public T first() {
        if(isEmpty()){
            throw new EmptyQueueException("first on empty queue");
        }
        return first.elem;
    }

    @Override
    public void dequeue() {
        if(isEmpty()){
            throw new EmptyQueueException("dequeue on empty queue");
        }

        first = first.next;

        if(first==null){     // una vez actualizado first, comprobamos si es null para ver si la cola se qued vacía
            last = null;
        }
    }

    public String toString(){
        StringJoiner sj = new StringJoiner(", ", "Cola(", ")");
    /*
        while(current!=null){
            sj.add(current.elem.toString());
            current = current.next;
        }
     */

        for(Node<T> current=first; current!=null; current=current.next){
            sj.add(current.elem.toString());
        }
        return sj.toString();
    }
}
