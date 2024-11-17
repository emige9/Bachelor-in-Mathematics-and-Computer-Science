package es.uma.informatica.sii.restusuarios.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UsuarioNuevoDTO {
    private String nombre;
    private String apellidos;
    private String contrasenia;
    private String email;
}