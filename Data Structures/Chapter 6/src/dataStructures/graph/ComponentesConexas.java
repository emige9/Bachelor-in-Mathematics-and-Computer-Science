package dataStructures.graph;/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Tema 6. Grafos
  Pablo López
*/

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.graph.DepthFirstTraversal;
import dataStructures.graph.Graph;
import dataStructures.set.HashSet;
import dataStructures.set.Set;

public class ComponentesConexas<V> {

    private Set<Set<V>> componentes;
    private Dictionary<V, Integer> idComponentes;

    public ComponentesConexas(Graph<V> g) {

        componentes = new HashSet<>();
        idComponentes = new HashDictionary<>();
        Set<V> porVisitar = new HashSet<>();

        for (V v : g.vertices()){
            porVisitar.insert(v);
        }

        int idComp = 1;
        while (!porVisitar.isEmpty()){
            V orig = porVisitar.iterator().next();
            Traversal<V> traversalOrig = new DepthFirstTraversal<>(g, orig);
            Set<V> compOrig = new HashSet<>();

            for (V v : traversalOrig.vertices()){
                compOrig.insert(v);
                idComponentes.insert(v, idComp);
                porVisitar.delete(v);
            }

            componentes.insert(compOrig);
            idComp++;
        }

    }

    public Set<Set<V>> componentes() {

        return componentes;
    }

    public boolean conectados(V x, V y) {
        return idComponentes.valueOf(x).equals(idComponentes.valueOf(y));
    }
}
