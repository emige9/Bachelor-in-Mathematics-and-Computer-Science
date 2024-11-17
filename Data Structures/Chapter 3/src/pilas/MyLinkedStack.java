package pilas;

import dataStructures.stack.EmptyStackException;
import dataStructures.stack.Stack;

import java.util.StringJoiner;

public class MyLinkedStack<T> implements Stack<T> {

    private static class Node<E> {
        E elem;
        Node<E> next;

        public Node(E x, Node<E> nextNode){
            elem = x;
            next = nextNode;
        }
    }

    private Node<T> top;

    public MyLinkedStack(){
        top = null;
    }

    @Override
    public boolean isEmpty() {
        return top==null;
    }

    @Override
    public void push(T x) {
        top = new Node<>(x, top);
    }

    @Override
    public T top() {
        if (isEmpty()){
            throw new EmptyStackException("top on empty stack");
        }
        return top.elem;
    }

    @Override
    public void pop() {
        if (isEmpty()){
            throw new EmptyStackException("pop on empty stack");
        }

        top = top.next;
    }

    public String toString(){
        StringJoiner sj = new StringJoiner(", ", "Pila(", ")");
        Node<T> current = top;

        while(current!=null){
            sj.add(current.elem.toString());
            current = current.next;
        }
        return sj.toString();
    }
}
