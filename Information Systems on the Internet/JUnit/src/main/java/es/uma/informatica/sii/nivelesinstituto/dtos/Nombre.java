package es.uma.informatica.sii.nivelesinstituto.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nombre {
    private String nombre;

    public static Nombre of(String nombre) {
        return Nombre.builder().nombre(nombre).build();
    }
}
