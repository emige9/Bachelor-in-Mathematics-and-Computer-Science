/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Tema 4. Árboles
  Pablo López
*/

import dataStructures.list.LinkedList;
import dataStructures.list.List;

public class BinTree<T> {

    private static class Tree<E> {
        private E elem;
        private Tree<E> left;
        private Tree<E> right;

        public Tree(E e, Tree<E> l, Tree<E> r) {
            elem = e;
            left = l;
            right = r;
        }
    }

    private Tree<T> root;

    public BinTree() {
        root = null;
    }

    public BinTree(T x) {
        root = new Tree<>(x, null, null);
    }

    public BinTree(T x, BinTree<T> l, BinTree<T> r) {
        root = new Tree<>(x, l.root, r.root);
    }

    private static boolean isLeaf(Tree<?> current) {
        return current != null && current.left == null && current.right == null;
    }

    public int weight() {
        return weightRec(root) ;
    }

    private static int weightRec(Tree<?> node){            // static <E> para que no pueda acceder a root (cambiar E por ? porque solo se usa una vez)
        if(node == null){
            return 0;
        } else {
            return 1 + weightRec(node.left) + weightRec(node.right);
        }
    }

    public int height() {
        return 0;
    }

    public List<T> border() {
        return borderRec(root);
    }

    private static <E> List<E> borderRec(Tree<E> node){
        if(node == null){
            return new LinkedList<>();
        } else if (isLeaf(node)){
            List<E> result = new LinkedList<>();
            result.append(node.elem);
            return result;
        } else {
            List<E> borderLeft = borderRec(node.left);
            List<E> borderRight = borderRec(node.right);

            for(E x: borderRight){
                borderLeft.append(x);
            }

            return borderLeft;
        }
    }

    public boolean isElem(T x) {
        return false;
    }

    public List<T> atLevel(int i) {
        return null;
    }

    public List<T> inOrder() {
        return null;
    }

    public void trim() {
        root = trimRec(root);
    }

    private static <E> Tree<E> trimRec(Tree<E> node){
        if(node == null || isLeaf(node)){
            return null;
        } else {
            node.left = trimRec(node.left);
            node.right = trimRec(node.right);
            return node;
        }
    }

    /**
     * Returns representation of tree as a String.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + toStringRec(this.root) + ")";
    }

    private static String toStringRec(Tree<?> tree) {
        return tree == null ? "null" : "Node<" + toStringRec(tree.left) + ","
                + tree.elem + "," + toStringRec(tree.right)
                + ">";
    }

    /**
     * Returns a String with the representation of tree in DOT (graphviz).
     */
    public String toDot(String treeName) {
        final StringBuffer sb = new StringBuffer();
        sb.append(String.format("digraph \"%s\" {\n", treeName));
        sb.append("node [fontname=\"Arial\", fontcolor=red, shape=circle, style=filled, color=\"#66B268\", fillcolor=\"#AFF4AF\" ];\n");
        sb.append("edge [color = \"#0070BF\"];\n");
        toDotRec(root, sb);
        sb.append("}");
        return sb.toString();
    }

    private static void toDotRec(Tree<?> current, StringBuffer sb) {
        if (current != null) {
            final int currentId = System.identityHashCode(current);
            sb.append(String.format("%d [label=\"%s\"];\n", currentId, current.elem));
            if (!isLeaf(current)) {
                processChild(current.left, sb, currentId);
                processChild(current.right, sb, currentId);
            }
        }
    }

    private static void processChild(Tree<?> child, StringBuffer sb, int parentId) {
        if (child == null) {
            sb.append(String.format("l%d [style=invis];\n", parentId));
            sb.append(String.format("%d -> l%d;\n", parentId, parentId));
        } else {
            sb.append(String.format("%d -> %d;\n", parentId, System.identityHashCode(child)));
            toDotRec(child, sb);
        }
    }
}
