package kanban;

public class Tarea {
	private String estado;
	private String titulo;
	private int prioridad;
	private double horasEstimadas;
	private double horasConsumidas;
	
	public Tarea(String estado, String titulo, int prioridad, double horasEstimadas, double horasConsumidas) throws KanbanException{
		if(titulo.equals("")) {
			throw new KanbanException("No se ha introducido titulo");
		} else if(!esEstadoValido(estado)) {
			throw new KanbanException("El estado no es valido");
		} else if(prioridad<1 || prioridad>5) {
			throw new KanbanException("La prioridad no es valida");
		} else if(horasConsumidas<0) {
			throw new KanbanException("El numero de horas consumidas no es valida");
		} else if(horasEstimadas<=0) {
			throw new KanbanException("El numero de horas estimadas no es valido");
		} else {
			this.estado=estado;
			this.titulo=titulo;
			this.prioridad=prioridad;
			this.horasEstimadas=horasEstimadas;
			this.horasConsumidas=horasConsumidas;
		}
	}
	
	Tarea(String titulo) throws KanbanException{
		this.titulo=titulo;
		estado="NOINICIADA";
		prioridad=1;
		horasEstimadas=1;
		horasConsumidas=0;
	}
	
	public String getEstado() {
		return this.estado;
	}
	
	public String getTitulo() {
		return this.titulo;
	}
	
	public int getPrioridad() {
		return this.prioridad;
	}
	
	public double getHorasEstimadas() {
		return this.horasEstimadas;
	}
	
	public double getHorasConsumidas() {
		return this.horasConsumidas;
	}
	
	public void setEstado(String nuevoEstado) throws KanbanException{
		if(!esEstadoValido(nuevoEstado)) {
			throw new KanbanException("El estado no es valido");
		} else {
			this.estado=nuevoEstado;
		}
	}
	
	public void setPrioridad(int nueva) throws KanbanException{
		if(nueva<1 || nueva>5) {
			throw new KanbanException("La prioridad no es válida");
		} else {
			this.prioridad=nueva;
		}
	}
	
	public void setHorasConsumidas(double nuevasHoras)throws KanbanException{
		if(nuevasHoras<0) {
			throw new KanbanException("El número de horas consumidas no es válido");
		} else {
			this.horasConsumidas=nuevasHoras;
		}
	}
	
	public static boolean esEstadoValido(String estado) {
		return estado.equals("NOINICIADA") || estado.equals("ENPROCESO") || estado.equals("TERMINADA");
	}
	
	public boolean equals(Object obj) {
		boolean res = obj instanceof Tarea;
		Tarea tarea = res ? (Tarea)obj : null;
		return res && (titulo.equalsIgnoreCase(tarea.getTitulo()));
	}
	
	public int hashCode() {
		return titulo.hashCode();
	}
	
	public String toString() {
		return "Tarea: " + titulo + ". " + estado + " prioridad: "+ prioridad + " horas:(" + horasConsumidas + "/" + horasEstimadas + ")";
	}
}
