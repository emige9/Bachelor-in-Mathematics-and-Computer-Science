package kanban;

import java.util.Arrays;

public class CriterioMaximoEsfuerzo implements Criterio{
	
	public Tarea[] filtrar(Tarea[] tareas) {
		Tarea[] filtradas = new Tarea[tareas.length];
		double horasEstMax=0;
		int i=0;
		
		for(Tarea tarea : tareas) {
			double horasEst = tarea.getHorasEstimadas();
			if(horasEst>horasEstMax) {
				horasEstMax=horasEst;
				i=0;
			}
			if(horasEst==horasEstMax) {
				filtradas[i]=tarea;
				i++;
			}
		}
		filtradas=Arrays.copyOf(filtradas, i);
		return filtradas;
	}
}
