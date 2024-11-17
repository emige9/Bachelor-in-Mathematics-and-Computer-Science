import kanban.*;

public class PruebaTarea {
	
	public static void main(String[] args) throws KanbanException{
		 try {
			Tarea t1 = new Tarea("NOINICIADA", "Mantenimiento", 3, 10, 9);
			System.out.println(t1);
		} catch (KanbanException e) {
			System.out.println(e.getMessage());
		}
		 
		 try {
				Tarea t2 = new Tarea ("TERMINADA", "Velocidad", 2, 15, 10);
				System.out.println(t2);
			} catch (KanbanException e) {
				System.out.println(e.getMessage());
			}
		 try {
				Tarea t3 = new Tarea ("HECHO", "Neumaticos", 4, 5, 5);
				System.out.println(t3);
			} catch (KanbanException e) {
				System.out.println(e.getMessage());
			}
		 
		 try {
				Tarea t4 = new Tarea ("ENPROCESO", "", 2, 6, 6);
				System.out.println(t4);
			} catch (KanbanException e) {
				System.out.println(e.getMessage());
			}
		 
		 try {
				Tarea t5 = new Tarea ("NOINICIADA", "Otros", 6, 7, 7);
				System.out.println(t5);
			} catch (KanbanException e) {
				System.out.println(e.getMessage());
			}
		
		try {
			Tarea t1 = new Tarea("NOINICIADA", "Mantenimiento", 3, 10, 9);
			System.out.println(t1);
			Tarea t2 = new Tarea ("TERMINADA", "Velocidad", 2, 15, 10);
			System.out.println(t2);
			
			if(t1.equals(t2)) {
				System.out.println("Son la misma tarea");
			} else {
				System.out.println("No son la misma taera");
			}
			
		}catch (KanbanException e) {
			System.out.println(e.getMessage());
		}
		
	}
		
		
	
}
