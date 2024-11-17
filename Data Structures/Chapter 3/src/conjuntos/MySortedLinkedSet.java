package conjuntos;

import dataStructures.set.Set;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class MySortedLinkedSet<T extends Comparable<? super T>> implements Set<T> {

    private static class Node<E>{
        E elem;
        Node<E> next;

        public Node(E x, Node<E> nextNode){
            elem = x;
            next = nextNode;
        }
    }

    private Node<T> first;
    private int size;

    public MySortedLinkedSet(){
        first = null;
        size = 0;
    }

    //Complejidad: O(1)
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    //Complejidad: O(1)
    @Override
    public int size() {
        return size;
    }

    //Complejidad: O(n)
    @Override
    public void insert(T x) {
        Node<T> previous = null;
        Node<T> current = first;

        while (current != null && current.elem.compareTo(x)<0){    // current.elem < x
            previous = current;
            current = current.next;
        }

        //current apunta a x ó a un dato mayor que x ó no apunta a nadie

        if(current == null || !current.elem.equals(x)){
            Node<T> nuevo = new Node<>(x,current);

            if(previous != null){
                previous.next = nuevo;
            } else {
                first = nuevo;
            }
            size++;
        }
    }

    @Override
    public boolean isElem(T x) {
        //Node<T> previous = null;
        Node<T> current = first;

        while (current != null && current.elem.compareTo(x)<0){    // current.elem < x
          //  previous = current;
            current = current.next;
        }

        //current apunta a x ó a un dato mayor que x ó no apunta a nadie

        return current!= null && current.elem.equals(x);
    }

    @Override
    public void delete(T x) {
        Node<T> previous = null;
        Node<T> current = first;

        while (current != null && current.elem.compareTo(x)<0){    // current.elem < x
            previous = current;
            current = current.next;
        }

        //current apunta a x ó a un dato mayor que x ó no apunta a nadie

        if(current!=null && current.elem.equals(x)){
            if (previous != null){
                previous.next = current.next;
            } else {
                first = current.next;
            }
            size--;
        }
    }

    public String toString(){
        StringJoiner sj = new StringJoiner(", ", "{", "}");
        for(Node<T> current = first; current != null; current = current.next){
            sj.add(current.elem.toString());
        }

        return sj.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new SortedLinkedSetIterator();
    }

    private class SortedLinkedSetIterator implements Iterator<T> {
        Node<T> current;

        public SortedLinkedSetIterator(){
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()){
                throw new NoSuchElementException("no existe siguiente");
            }
            T result = current.elem;
            current = current.next;
            return result;
        }
    }
}
