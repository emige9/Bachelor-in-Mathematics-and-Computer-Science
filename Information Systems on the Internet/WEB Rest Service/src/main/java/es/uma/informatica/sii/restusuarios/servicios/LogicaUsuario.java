package es.uma.informatica.sii.restusuarios.servicios;

import es.uma.informatica.sii.restusuarios.controladores.Mapper;
import es.uma.informatica.sii.restusuarios.dtos.UsuarioNuevoDTO;
import es.uma.informatica.sii.restusuarios.entidades.Usuario;
import es.uma.informatica.sii.restusuarios.excepciones.UsuarioExistenteException;
import es.uma.informatica.sii.restusuarios.excepciones.UsuarioNoEncontrado;
import es.uma.informatica.sii.restusuarios.repositorios.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LogicaUsuario {

	private UsuarioRepo repo;

	@Autowired
	public LogicaUsuario(UsuarioRepo repo) {
		this.repo = repo;
	}

	public List<Usuario> getTodosUsuarios() {
		return repo.findAll();
	}

	// TODO

	public Usuario addUsuario(Usuario usuario) throws UsuarioExistenteException {

		Optional<Usuario> usuarioExistente = repo.findByEmail(usuario.getEmail());
		if (!usuarioExistente.isEmpty()) {
			throw new UsuarioExistenteException();
		} else {
			return repo.save(usuario);
		}
	}

	public Optional<Usuario> getUsuarioPorId(Long id) {
		return repo.findById(id);
	}

	public void eliminarUsuarioPorId(Long id) throws UsuarioNoEncontrado {
		Optional<Usuario> usuarioOptional = repo.findById(id);
		if (usuarioOptional.isPresent()) {
			repo.deleteById(id);
		} else {
			throw new UsuarioNoEncontrado();
		}
	}

	public Usuario modificarUsuario(Long id, UsuarioNuevoDTO usuarioNuevoDTO)
			throws UsuarioNoEncontrado, UsuarioExistenteException {

		Optional<Usuario> usuarioExistenteOptional = repo.findById(id);

		if (usuarioExistenteOptional.isPresent()) {
			Usuario usuarioExistente = usuarioExistenteOptional.get();

			if (!usuarioExistente.getEmail().equals(usuarioNuevoDTO.getEmail())) {

				Optional<Usuario> usuarioExistentePorEmail = repo.findByEmail(usuarioNuevoDTO.getEmail());
				if (usuarioExistentePorEmail.isPresent()) {
					throw new UsuarioExistenteException();
				}
			}
			usuarioExistente.setNombre(usuarioNuevoDTO.getNombre());
			usuarioExistente.setApellidos(usuarioNuevoDTO.getApellidos());
			usuarioExistente.setEmail(usuarioNuevoDTO.getEmail());

			return repo.save(usuarioExistente);
		} else {

			throw new UsuarioNoEncontrado();
		}
	}

}
