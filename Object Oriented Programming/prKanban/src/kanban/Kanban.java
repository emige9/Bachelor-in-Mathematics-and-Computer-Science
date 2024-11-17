package kanban;
import java.util.*;

public class Kanban {
	private Tarea[] tareas;
	
	public Kanban(String[] lista) {
		tareas = new Tarea[lista.length];
		almacenarTareas(lista);
	}
	
	private void almacenarTareas(String[] lista) {
		int i=0;
		for(String tarea : lista) {
			try(Scanner scTarea = new Scanner(tarea)){
				scTarea.useDelimiter("[;]");
				tareas[i] = new Tarea(scTarea.next(),scTarea.next(),scTarea.nextInt(),scTarea.nextDouble(),scTarea.nextDouble());
				i++;
			} catch (KanbanException | NoSuchElementException ke) {
				
			}
		}
		tareas = Arrays.copyOf(tareas, i);
	}
	
	public Tarea[] getTareas() {
		return tareas;
	}
	
	public String resumenTareas() {
		StringBuilder sb = new StringBuilder("RESUMEN DE TAREAS\n");
		int numNoIniciadas=0;
		int numIniciadas=0;
		int numTerminadas=0;
		double totalHorasConsumidas=0;
		double totalHorasEstimadas=0;
		
		for(Tarea tarea : tareas) {
			String estado = tarea.getEstado();
			switch(estado) {
				case "NOINICIADA": numNoIniciadas++; break;
				case "ENPROCESO": numIniciadas++; break;
				case "TERMINADA": numTerminadas++; break;
			}
			totalHorasConsumidas+=tarea.getHorasConsumidas();
			totalHorasEstimadas+=tarea.getHorasEstimadas();
		}
		sb.append(numNoIniciadas + " No iniciada. " + numIniciadas + " En proceso. " + numTerminadas + " Terminadas\n");
		sb.append("Horas Consumidas: " + totalHorasConsumidas + " Horas Estimadas: " + totalHorasEstimadas + "\n");
		return sb.toString();
	}
	
	public String toString() {
		return Arrays.toString(tareas);
	}
	
	public Tarea[] seleccionar(Criterio cr) {
		return cr.filtrar(tareas);
	}
}
