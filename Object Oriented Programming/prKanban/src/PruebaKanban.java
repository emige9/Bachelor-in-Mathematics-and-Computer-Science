import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

import kanban.CriterioMaximoEsfuerzo;
import kanban.CriterioPrioridad;
import kanban.Kanban;
import kanban.KanbanException;

public class PruebaKanban {

	public static void main(String[] args) throws KanbanException, FileNotFoundException {
		String[] tareas = {
			"NOINICIADA;Análisis Requisitos;5;10;9", 
			"TERMINADA;Definición del proyecto;4;50;50",
			"NOINICIADA;Carga datos;3;2;1", 
			"ENPROCESO;Diseñar Base de Datos;3;10;2", 
			"ENPROCESO;App Móvil;4;50;19",
			"TERMINADA;Debo fallar;6;20;8", 
			"ENPROC;Debo fallar Tambien;3;20;8", 
			"ENPROCESO;Y yo mal;3;0;8",
			"ENPROCESO;Y yo peor;3;3;-1", 
			"ENPROCESO;Identificación;1;20;3", 
			"Revisión", 
			"TAREALOCA;No se;3;5;6",
			"Optimizar Servidor,2;10" 
		};

		// Probamos creación y toString de Kanbas
		Kanban kanban = new Kanban(tareas);
		System.out.println("\nTAREAS LEIDAS CORRECTAMENTE:\n" + kanban);

		// Probamos el criterio prioridad
		System.out.println("\nLISTADO POR PRIORIDADES:");
		CriterioPrioridad crPrio = new CriterioPrioridad(1);
		for (int i = 1; i <= 5; i++) {
			crPrio.setPrioridad(i);
			System.out.println("Prioridad " + i);
			System.out.println("\t" + Arrays.toString(kanban.seleccionar(crPrio)));
		}

		// Probamos criterio máximo esfuerzo
		System.out.println("\nLISTADO DE TAREAS CON MÁXIMO NÚMERO DE HORAS ESTIMADAS:");
		CriterioMaximoEsfuerzo crME = new CriterioMaximoEsfuerzo();
		System.out.println("\t" + Arrays.toString(kanban.seleccionar(crME)) + "\n");

		// Probamos el método resumenTareas
		System.out.println(kanban.resumenTareas());
	}
}
