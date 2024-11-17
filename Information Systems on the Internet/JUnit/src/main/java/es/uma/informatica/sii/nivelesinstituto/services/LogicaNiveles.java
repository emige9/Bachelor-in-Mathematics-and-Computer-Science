package es.uma.informatica.sii.nivelesinstituto.services;

import es.uma.informatica.sii.nivelesinstituto.entities.Grupo;
import es.uma.informatica.sii.nivelesinstituto.entities.Nivel;
import es.uma.informatica.sii.nivelesinstituto.exceptions.ElementoNoExisteException;
import es.uma.informatica.sii.nivelesinstituto.exceptions.ElementoYaExistenteException;
import es.uma.informatica.sii.nivelesinstituto.exceptions.NivelNoVacioException;
import es.uma.informatica.sii.nivelesinstituto.repositories.GrupoRepo;
import es.uma.informatica.sii.nivelesinstituto.repositories.NivelRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LogicaNiveles {
    private NivelRepo nivelRepo;
    private GrupoRepo grupoRepo;

    public LogicaNiveles(NivelRepo nivelRepo, GrupoRepo grupoRepo) {
        this.nivelRepo = nivelRepo;
        this.grupoRepo = grupoRepo;
    }

    public List<Nivel> getNiveles() {
        return nivelRepo.findAll();
    }

    public Nivel addNivel(Nivel nivel) {
        nivel.setId(null);
        nivel.setGrupos(Collections.EMPTY_LIST);
        nivelRepo.findByNombre(nivel.getNombre()).ifPresent(n -> {
            throw new ElementoYaExistenteException("Nivel ya existe");
        });
        return nivelRepo.save(nivel);
    }

    public Nivel getNivel(Long id) {
        var nivel = nivelRepo.findById(id);
        if (nivel.isEmpty()) {
            throw new ElementoNoExisteException("Nivel no encontrado");
        } else {
            return nivel.get();
        }
    }

    public Nivel updateNivel(Nivel nivel) {
        if (nivelRepo.existsById(nivel.getId())) {
            var opNivel = nivelRepo.findByNombre(nivel.getNombre());
            if (opNivel.isPresent() && opNivel.get().getId() != nivel.getId()) {
                throw new ElementoYaExistenteException("Nivel ya existe");
            }
            opNivel = nivelRepo.findById(nivel.getId());
            opNivel.ifPresent(n -> {
                n.setNombre(nivel.getNombre());
            });
            return nivelRepo.save(opNivel.get());
        } else {
            throw new ElementoNoExisteException("Nivel no encontrado");
        }
    }

    public void deleteNivel(Long id) {
        var nivel = nivelRepo.findById(id);
        if (nivel.isPresent()) {
            if (!nivel.get().getGrupos().isEmpty()) {
                throw new NivelNoVacioException("Nivel no vacío");
            }
            nivelRepo.deleteById(id);
        } else {
            throw new ElementoNoExisteException("Nivel no encontrado");
        }
    }

    public Grupo addGrupo(Long id, Grupo grupo) {
        var nivel = getNivel(id);
        nivel.getGrupos().stream()
            .filter(g -> g.getNombre().equals(grupo.getNombre()) && g.getId() != grupo.getId() )
            .findAny().ifPresent(g -> {
                throw new ElementoYaExistenteException("Grupo ya existe en el nivel");
             });
        grupo.setId(null);
        grupo.setNivel(nivel);
        return grupoRepo.save(grupo);
    }

    public void deleteGrupo(Long id, Long idGrupo) {
        existenRelacionados(id, idGrupo);
        grupoRepo.deleteById(idGrupo);
    }

    public Grupo updateGrupo(Long idNivel, Grupo grupo) {
        existenRelacionados(idNivel, grupo.getId());
        var nivel = getNivel(idNivel);
        nivel.getGrupos().stream()
            .filter(g -> g.getNombre().equals(grupo.getNombre()) && g.getId() != grupo.getId()).findAny().ifPresent(g -> {
                throw new ElementoYaExistenteException("Grupo ya existe en el nivel");
            });
        grupo.setNivel(nivel);
        return grupoRepo.save(grupo);
    }

    private void existenRelacionados(Long id, Long idGrupo) {
        var nivel = getNivel(id);
        var grupo = getGrupo(idGrupo);
        if (grupo.getNivel().getId()!= id) {
            throw new ElementoNoExisteException("Grupo no encontrado en el nivel");
        }
    }

    private Grupo getGrupo(Long idGrupo) {
        var grupo = grupoRepo.findById(idGrupo);
        if (grupo.isEmpty()) {
            throw new ElementoNoExisteException("Grupo no encontrado");
        }
        return grupo.get();
    }
}
