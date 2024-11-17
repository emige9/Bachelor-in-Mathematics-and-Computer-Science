package es.uma.informatica.sii.restusuarios.controladores;

import es.uma.informatica.sii.restusuarios.dtos.UsuarioDTO;
import es.uma.informatica.sii.restusuarios.dtos.UsuarioNuevoDTO;
import es.uma.informatica.sii.restusuarios.entidades.Usuario;
import es.uma.informatica.sii.restusuarios.excepciones.UsuarioExistenteException;
import es.uma.informatica.sii.restusuarios.excepciones.UsuarioNoEncontrado;
import es.uma.informatica.sii.restusuarios.servicios.LogicaUsuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuario")
public class ControladorRest {
	private LogicaUsuario servicio;

	public ControladorRest(LogicaUsuario servicioUsuarios) {
		servicio = servicioUsuarios;
	}

	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> listaDeUsuarios() {
		return ResponseEntity.ok(servicio.getTodosUsuarios().stream()
				.map(Mapper::toUsuarioDTO)
				.toList());
	}

	@PostMapping
	public ResponseEntity<UsuarioDTO> aniadirUsuario(@RequestBody UsuarioNuevoDTO usuario,
			UriComponentsBuilder builder) {
		// TODO
		try {
			Usuario user = Mapper.toUsuario(usuario);

			// Llamar al servicio para añadir el usuario
			Usuario usuarioCreado = servicio.addUsuario(user);

			// Convertir el Usuario a UsuarioDTO
			UsuarioDTO usuarioDTO = Mapper.toUsuarioDTO(usuarioCreado);

			URI uri = builder
					.path("/api")
					.path("/v1")
					.path("/usuario")
					.path(String.format("/%d", usuarioDTO.getId()))
					.build()
					.toUri();

			return ResponseEntity.created(uri).body(usuarioDTO);
		} catch (UsuarioExistenteException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

	}

	@GetMapping("{id}")
	public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
		Optional<Usuario> usuario = servicio.getUsuarioPorId(id);

		if (usuario.isPresent()) {

			UsuarioDTO usuarioDTO = Mapper.toUsuarioDTO(usuario.get());
			return ResponseEntity.ok(usuarioDTO);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
		try {
			servicio.eliminarUsuarioPorId(id);
			return ResponseEntity.ok().build();
		} catch (UsuarioNoEncontrado e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> modificarUsuario(@PathVariable Long id,
			@RequestBody UsuarioNuevoDTO usuarioNuevoDTO) {
		try {
			Usuario usuarioModificado = servicio.modificarUsuario(id, usuarioNuevoDTO);
			UsuarioDTO usuarioModificadoDTO = Mapper.toUsuarioDTO(usuarioModificado);
			return ResponseEntity.ok(usuarioModificadoDTO);
		} catch (UsuarioNoEncontrado e) {
			return ResponseEntity.notFound().build();
		} catch (UsuarioExistenteException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}

}
