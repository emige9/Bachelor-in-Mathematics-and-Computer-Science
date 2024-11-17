package kanban;

import java.util.Arrays;

public class CriterioPrioridad implements Criterio{
	int prioridad;
	
	public CriterioPrioridad(int prio) throws KanbanException{
		if(prio<1 || prio>5) {
			throw new KanbanException("La prioridad debe estar entre 1 y 5");
		} else {
			prioridad=prio;
		}
	}
	
	public void setPrioridad(int nueva) throws KanbanException{
		if(nueva<1 || nueva>5) {
			throw new KanbanException("La prioridad debe estar entre 1 y 5");
		} else {
			prioridad=nueva;
		}
	}
	
	public Tarea[] filtrar (Tarea[] tareas) {
		Tarea[] filtradas = new Tarea[tareas.length];
		int i=0;
		for(Tarea tarea : tareas) {
			if(tarea.getPrioridad() == prioridad) {
				filtradas[i]=tarea;
				i++;
			}
		}
		filtradas=Arrays.copyOf(filtradas, i);
		return filtradas;
	}
}
