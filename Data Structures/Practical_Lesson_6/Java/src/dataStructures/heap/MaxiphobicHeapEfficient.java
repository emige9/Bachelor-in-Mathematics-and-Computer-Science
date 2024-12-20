/*
 * Estructuras de Datos. 2º Curso. ETSI Informática. UMA
 * PRACTICA 6. Montículos maxifóbicos en Java
 * APELLIDOS, NOMBRE:
 */

package dataStructures.heap;

// Solo tienes que completar el método merge.

/**
 * Heap implemented using maxiphobic heap-ordered binary trees.
 *
 * @param <T> Type of elements in heap.
 */
public class MaxiphobicHeapEfficient<T extends Comparable<? super T>> implements Heap<T> {

    private static class Tree<E> {
        private E elem;
        private int size;
        private Tree<E> left;
        private Tree<E> right;

        public Tree(E x, Tree<E> l, Tree<E> r) {
            elem = x;
            left = l;
            right = r;
            size = 1 + size(l) + size(r);
        }
    }

    private Tree<T> root;

    /**
     * Creates an empty Maxiphobic Heap.
     * <p>
     * Time complexity: O(1)
     */
    public MaxiphobicHeapEfficient() {
        root = null;
    }

    private static int size(Tree<?> heap) {
        return heap == null ? 0 : heap.size;
    }

    private static boolean isLeaf(Tree<?> current) {
        return current != null && current.left == null && current.right == null;
    }

    /*
        Al mezclar tenemos tres montículos: el ganador y los hijos del perdedor.
        Se cuelga a la izquierda el más pesado de los tres y a la derecha la mezcla
        de los más ligeros:

                               mínimo
                              /      \
                           más       mezcla de los
                         pesado       más ligeros

       Política de desempate:

       Si hay empate para elegir el montículo más pesado:

        1. elegir preferentemente al hijo izquierdo del perdedor
        2. si no, elegir preferentemente al hijo derecho del perdedor
        3. si no, elegir al ganador
    */

    private static <T extends Comparable<? super T>> Tree<T> merge(Tree<T> h1, Tree<T> h2) {
        // TODO
        if(h1 == null){
            return h2;
        } else if (h2 == null){
            return h1;
        }

        //Escogemos primeramente la raiz MaxiphobicHeap
        Tree<T> HeapRaiz = h1.elem.compareTo(h2.elem)<=0 ? h1 : h2;
        //Escogemos el arbol de ambos con la mayor clave
        Tree<T> h_key = h1.elem.compareTo(h2.elem)<=0 ? h2 : h1;
        //Nos guardamos los hijos del arbol perdedor
        Tree<T> ll = h1.elem.compareTo(h2.elem)<=0 ? h1.left : h2.left;
        Tree<T> rr = h1.elem.compareTo(h2.elem)<=0 ? h1.right : h2.right;
        //Para evitar si uno de los hijos guardados eran nulo, o lo que es lo mismo, eran hojas
        int ll_s = ll == null ? 0 : ll.size;
        int rr_s = rr == null ? 0 : rr.size;
        //Una vez hemos guardado los 3 arboles, nos quedamos con el que pesa mas de ellos
        Tree<T> t1, t2, t3;

        if(ll_s >= h_key.size && ll_s >= rr_s){
            t1 = ll;
            t2 = rr;
            t3 = h_key;
        } else if(rr_s>= h_key.size && rr_s >= ll_s){
            t1 = rr;
            t2 = ll;
            t3 = h_key;
        } else {
            t1 = h_key;
            t2 = ll;
            t3 = rr;
        }

        HeapRaiz.left = t1;
        HeapRaiz.right = merge(t2,t3);
        return new Tree<>(HeapRaiz.elem, HeapRaiz.left, HeapRaiz.right);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     */
    @Override
    public int size() {
        return root == null ? 0 : root.size;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     *
     * @throws {@code EmptyHeapException} if heap stores no element.
     */
    @Override
    public T minElem() {
        if (isEmpty())
            throw new EmptyHeapException("minElem on empty heap");
        else
            return root.elem;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(log n)
     *
     * @throws {@code EmptyHeapException} if heap stores no element.
     */
    @Override
    public void delMin() {
        if (isEmpty())
            throw new EmptyHeapException("delMin on empty heap");
        else
            root = merge(root.left, root.right);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(log n)
     */
    @Override
    public void insert(T value) {
        Tree<T> newHeap = new Tree<>(value, null, null);
        root = merge(root, newHeap);
    }

    public void clear() {
        root = null;
    }

    /**
     * Returns representation of tree as a String.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + toStringRec(this.root) + ")";
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

    private static String toStringRec(Tree<?> tree) {
        return tree == null ? "null" : "Node<" + toStringRec(tree.left) + ","
                + tree.elem + "," + toStringRec(tree.right)
                + ">";
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
