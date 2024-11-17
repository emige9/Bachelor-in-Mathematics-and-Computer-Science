package es.uma.informatica.sii.restusuarios.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Entity
public class Usuario {
	
	@Id @GeneratedValue
	private Long id;
	private String nombre;
	private String apellidos;
	private String email;
	private String contrasenia;

}
