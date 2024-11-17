package es.uma.informatica.sii.nivelesinstituto.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Nivel {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String nombre;
    @OneToMany(mappedBy = "nivel")
    @JsonManagedReference
    private List<Grupo> grupos;
}
