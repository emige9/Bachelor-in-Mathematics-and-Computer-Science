package kanban;

public class KanbanException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public KanbanException() {
		super();
	}
	
	public KanbanException(String msg) {
		super(msg);
	}
}
