package es.uma.informatica.sii.nivelesinstituto.repositories;

import es.uma.informatica.sii.nivelesinstituto.entities.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NivelRepo extends JpaRepository<Nivel, Long> {
    Optional<Nivel> findByNombre(String nombre);
}
