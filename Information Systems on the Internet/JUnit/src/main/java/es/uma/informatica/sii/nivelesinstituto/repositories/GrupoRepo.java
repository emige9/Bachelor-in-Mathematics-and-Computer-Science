package es.uma.informatica.sii.nivelesinstituto.repositories;

import es.uma.informatica.sii.nivelesinstituto.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepo extends JpaRepository<Grupo, Long>{

}
