/**
 * APELLIDOS : ..........................  NOMBRE: ............
 *
 * TITULACION: ..............................
 *
 * Computes Topological Sorting for DiGraphs
 */

package dataStructures.graph;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.set.HashSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

import java.util.Iterator;

public class TopologicalSortingDic<V> {

    private List<V> topSort;
    private boolean hasCycle;

    public TopologicalSortingDic(DiGraph<V> graph) {

        topSort = new ArrayList<>();
        // dictionary: vertex -> # of pending predecessors
        Dictionary<V, Integer> pendingPredecessors = new HashDictionary<>();
        // completar
        Set<V> sources = new HashSet<>(); //conjunto para guardar las fuentes en cada iteración
        //añadir predecesores
        for(V v : graph.vertices()){
            pendingPredecessors.insert(v, graph.inDegree(v));
        }
        boolean finished = false;
        while(!finished && !hasCycle){
            for(Tuple2<V, Integer> t : pendingPredecessors.keysValues()){
                if(t._2() == 0){
                    sources.insert(t._1());
                }
            }
            if(pendingPredecessors.isEmpty()){
                finished = true;
            }else if(sources.isEmpty()){
                hasCycle = true;
            }else{
                for (Iterator<V> it = sources.iterator(); it.hasNext(); ) {
                    V v = it.next();
                    topSort.append(v);
                }
                actualizarPendingPredecessors(pendingPredecessors, sources);
                actualizarDiccionario(graph, pendingPredecessors, sources);
                actualizarSources(sources);
            }
        }
    }

    private void actualizarDiccionario(DiGraph<V> graph, Dictionary<V, Integer> pendingPredecessors,
                                                                                    Set<V> sources){
        for(V vDict : pendingPredecessors.keys()){
            for (Iterator<V> it = sources.iterator(); it.hasNext(); ) {
                V source = it.next();
                if(graph.predecessors(vDict).isElem(source)){
                    pendingPredecessors.insert(vDict,pendingPredecessors.valueOf(vDict)-1);
                }
            }
        }
    }

    private void actualizarSources(Set<V> sources){
        for (Iterator<V> it = sources.iterator(); it.hasNext(); ) {
            V source = it.next();
            sources.delete(source);
        }
    }

    private void actualizarPendingPredecessors(Dictionary<V, Integer> pendingPredecessors,Set<V> sources){
        for (Iterator<V> it = sources.iterator(); it.hasNext(); ) {
            V source = it.next();
            pendingPredecessors.delete(source);
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public List<V> order() {
        return hasCycle ? null : topSort;
    }
}
