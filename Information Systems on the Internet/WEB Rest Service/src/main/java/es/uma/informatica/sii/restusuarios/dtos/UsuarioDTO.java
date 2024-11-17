package es.uma.informatica.sii.restusuarios.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
}
