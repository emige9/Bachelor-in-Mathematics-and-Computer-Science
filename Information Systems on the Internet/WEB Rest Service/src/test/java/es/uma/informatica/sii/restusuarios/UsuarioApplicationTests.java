package es.uma.informatica.sii.restusuarios;

import es.uma.informatica.sii.restusuarios.controladores.Mapper;
import es.uma.informatica.sii.restusuarios.dtos.UsuarioDTO;
import es.uma.informatica.sii.restusuarios.dtos.UsuarioNuevoDTO;
import es.uma.informatica.sii.restusuarios.entidades.Usuario;
import es.uma.informatica.sii.restusuarios.repositorios.UsuarioRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayName("En el servicio de usuarios")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UsuarioApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Value(value="${local.server.port}")
	private int port;
	
	@Autowired
	private UsuarioRepo usuarioRepository;
	
	@BeforeEach
	public void initializeDatabase() {
		usuarioRepository.deleteAll();
	}
	
	private URI uri(String scheme, String host, int port, String ...paths) {
		UriBuilderFactory ubf = new DefaultUriBuilderFactory();
		UriBuilder ub = ubf.builder()
				.scheme(scheme)
				.host(host).port(port);
		for (String path: paths) {
			ub = ub.path(path);
		}
		return ub.build();
	}
	
	private RequestEntity<Void> get(String scheme, String host, int port, String path) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.get(uri)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		return peticion;
	}
	
	private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.delete(uri)
			.build();
		return peticion;
	}
	
	private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.post(uri)
			.contentType(MediaType.APPLICATION_JSON)
			.body(object);
		return peticion;
	}
	
	private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
		URI uri = uri(scheme, host,port, path);
		var peticion = RequestEntity.put(uri)
			.contentType(MediaType.APPLICATION_JSON)
			.body(object);
		return peticion;
	}

	private void compruebaCampos(UsuarioDTO expected, UsuarioDTO actual) {
		assertThat(actual.getApellidos()).isEqualTo(expected.getApellidos());
		assertThat(actual.getNombre()).isEqualTo(expected.getNombre());
		assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
	}

	private void compruebaCampos(UsuarioNuevoDTO expected, UsuarioDTO actual) {
		assertThat(actual.getApellidos()).isEqualTo(expected.getApellidos());
		assertThat(actual.getNombre()).isEqualTo(expected.getNombre());
		assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
	}

	private void compruebaCampos(UsuarioNuevoDTO expected, Usuario actual) {
		assertThat(actual.getApellidos()).isEqualTo(expected.getApellidos());
		assertThat(actual.getNombre()).isEqualTo(expected.getNombre());
		assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
	}
	
	
	@Nested
	@DisplayName("cuando no hay usuarios")
	public class ListaVacia {
		
		@Test
		@DisplayName("devuelve la lista de usuarios vacía")
		public void devuelveLista() {
			
			var peticion = get("http", "localhost",port, "/api/v1/usuario");
			
			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<UsuarioDTO>>() {});
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody()).isEmpty();
		}
		
		@Nested
		@DisplayName("inserta un usuario")
		public class InsertaUsuarios {
			@Test
			@DisplayName("sin ID")
			public void sinID() {
				var usuario = UsuarioNuevoDTO.builder()
					.nombre("Antonio")
					.apellidos("García")
					.email("antonio@uma.es")
					.contrasenia("123456789")
					.build();
				var peticion = post("http", "localhost", port, "/api/v1/usuario", usuario);
				
				var respuesta = restTemplate.exchange(peticion, Void.class);
				
				compruebaRespuesta(usuario, respuesta);
			}

			private void compruebaRespuesta(UsuarioNuevoDTO usuario, ResponseEntity<Void> respuesta) {
				assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
				assertThat(respuesta.getHeaders().get("Location").get(0))
					.startsWith("http://localhost:"+port+"/api/v1/usuario");
				
				List<Usuario> usuarios = usuarioRepository.findAll();
				assertThat(usuarios).hasSize(1);
				assertThat(respuesta.getHeaders().get("Location").get(0))
					.endsWith("/"+ usuarios.get(0).getId());
				compruebaCampos(usuario, usuarios.get(0));
			}
		}
		
		@Test
		@DisplayName("devuelve error cuando se pide un usuario concreto")
		public void devuelveErrorAlConsultarUsuario() {
			var peticion = get("http", "localhost",port, "/api/v1/usuario/1");
			
			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<UsuarioDTO>>() {});
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
			assertThat(respuesta.hasBody()).isEqualTo(false);	
		}
		
		@Test
		@DisplayName("devuelve error cuando se modifica un usuario concreto")
		public void devuelveErrorAlModificarUsuario() {
			var usuario = UsuarioNuevoDTO.builder()
				.nombre("Antonio")
				.apellidos("García")
				.email("antonio@uma.es")
				.contrasenia("123456789")
				.build();
			var peticion = put("http", "localhost",port, "/api/v1/usuario/1", usuario);
			
			var respuesta = restTemplate.exchange(peticion, Void.class);
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}
		
		@Test
		@DisplayName("devuelve error cuando se elimina un usuario concreto")
		public void devuelveErrorAlEliminarUsuario() {
			var peticion = delete("http", "localhost",port, "/api/v1/usuario/1");
			
			var respuesta = restTemplate.exchange(peticion, Void.class);
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}
	}
	
	@Nested
	@DisplayName("cuando hay usuarios")
	public class ListaConDatos {
		private UsuarioNuevoDTO antonio = UsuarioNuevoDTO.builder()
			.nombre("Antonio")
			.apellidos("García")
			.email("antonio@uma.es")
			.contrasenia("123456789")
			.build();

		private UsuarioNuevoDTO victoria = UsuarioNuevoDTO.builder()
			.nombre("Victoria")
			.apellidos("Rodríguez")
			.email("victoria@uma.es")
			.contrasenia("987654321")
			.build();


		@BeforeEach
		public void introduceDatos() {
			usuarioRepository.save(Mapper.toUsuario(antonio));
			usuarioRepository.save(Mapper.toUsuario(victoria));
		}
		
		@Test
		@DisplayName("devuelve la lista de usuarios correctamente")
		public void devuelveLista() {
			var peticion = get("http", "localhost",port, "/api/v1/usuario");
			
			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<UsuarioDTO>>() {});
			
			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody()).hasSize(2);
		}
		
		@Nested
		@DisplayName("intenta insertar un usuario")
		public class InsertaUsuarios {
			@Test
			@DisplayName("y lo consigue cuando no coincide el e-mail con otro")
			public void diferenteEmail() {
				var usuario = UsuarioNuevoDTO.builder()
					.nombre("Sonia")
					.apellidos("Ramos")
					.email("sonia@uma.es")
					.contrasenia("123454321")
					.build();
				var peticion = post("http", "localhost", port, "/api/v1/usuario", usuario);
				
				var respuesta = restTemplate.exchange(peticion, Void.class);
				
				compruebaRespuesta(usuario, respuesta);
			}

			@Test
			@DisplayName("pero da error cuando coincide el e-mail con otro")
			public void mismoEmail() {
				var usuario = UsuarioNuevoDTO.builder()
					.nombre("Sonia")
					.apellidos("Ramos")
					.email("victoria@uma.es")
					.contrasenia("123454321")
					.build();
				var peticion = post("http", "localhost", port, "/api/v1/usuario", usuario);

				var respuesta = restTemplate.exchange(peticion, Void.class);

				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
				assertThat(respuesta.hasBody()).isFalse();
			}

			private void compruebaRespuesta(UsuarioNuevoDTO usuario, ResponseEntity<Void> respuesta) {
				assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
				assertThat(respuesta.getHeaders().get("Location").get(0))
					.startsWith("http://localhost:"+port+"/api/v1/usuario");
				
				List<Usuario> usuarios = usuarioRepository.findAll();
				assertThat(usuarios).hasSize(3);
				
				Usuario sonia = usuarios.stream()
						.filter(c->c.getNombre().equals("Sonia"))
						.findAny()
						.get();
				
				assertThat(respuesta.getHeaders().get("Location").get(0))
					.endsWith("/"+sonia.getId());
				compruebaCampos(usuario, sonia);
			}
		}
		
		@Nested
		@DisplayName("al consultar un usuario concreto")
		public class ObtenerUsuarios {
			@Test
			@DisplayName("lo devuelve cuando existe")
			public void devuelveUsuario() {
				var peticion = get("http", "localhost",port, "/api/v1/usuario/1");
				
				var respuesta = restTemplate.exchange(peticion, UsuarioDTO.class);
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
				assertThat(respuesta.hasBody()).isTrue();
				assertThat(respuesta.getBody()).isNotNull();
			}
			
			@Test
			@DisplayName("da error cuando no existe")
			public void errorCuandoUsuarioNoExiste() {
				var peticion = get("http", "localhost",port, "/api/v1/usuario/28");
				
				var respuesta = restTemplate.exchange(peticion,
						new ParameterizedTypeReference<List<UsuarioDTO>>() {});
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}
		}
		
		@Nested
		@DisplayName("al modificar un usuario")
		public class ModificarUsuarios {
			@Test
			@DisplayName("lo modifica correctamente cuando existe")
			@DirtiesContext
			public void modificaCorrectamente() {
				var usuario = UsuarioNuevoDTO.builder()
					.nombre("Sonia")
					.apellidos("Ramos")
					.email("sonia@uma.es")
					.contrasenia("123454321")
					.build();

				var peticion = put("http", "localhost",port, "/api/v1/usuario/1", usuario);
				
				var respuesta = restTemplate.exchange(peticion,UsuarioDTO.class);
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
				Usuario usuarioBD = usuarioRepository.findById(1L).get();
				compruebaCampos(usuario, usuarioBD);
			}
			@Test
			@DisplayName("da error cuando no existe")
			public void errorCuandoNoExiste() {
				var usuario = UsuarioNuevoDTO.builder()
					.nombre("Sonia")
					.apellidos("Ramos")
					.email("sonia@uma.es")
					.contrasenia("123454321")
					.build();
				var peticion = put("http", "localhost",port, "/api/v1/usuario/28", usuario);
				
				var respuesta = restTemplate.exchange(peticion,Void.class);
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}

			@Test
			@DisplayName("da error cuando el e-mail coincide con otro usuario")
			public void errorCuandoElEmailCoincideConOtro() {
				var usuario = UsuarioNuevoDTO.builder()
					.nombre("Sonia")
					.apellidos("Ramos")
					.email("victoria@uma.es")
					.contrasenia("123454321")
					.build();
				var peticion = put("http", "localhost",port, "/api/v1/usuario/1", usuario);

				var respuesta = restTemplate.exchange(peticion,Void.class);

				assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
				assertThat(respuesta.hasBody()).isFalse();
			}

			@Test
			@DisplayName("no se modifica cuando el e-mail coincide con otro usuario")
			public void noModificaCuandoElEmailCoincideConOtro() {
				var usuario = UsuarioNuevoDTO.builder()
					.nombre("Sonia")
					.apellidos("Ramos")
					.email("victoria@uma.es")
					.contrasenia("123454321")
					.build();
				var peticion = put("http", "localhost",port, "/api/v1/usuario/1", usuario);
				restTemplate.exchange(peticion,Void.class);

				var previo = usuarioRepository.findById(1L).get();
				compruebaCampos(antonio, previo);
			}
		}
		
		@Nested
		@DisplayName("al eliminar un usuario")
		public class EliminarUsuarios {
			@Test
			@DisplayName("lo elimina cuando existe")
			public void eliminaCorrectamente() {
				//List<Usuario> usuariosAntes = usuarioRepository.findAll();
				//usuariosAntes.forEach(c->System.out.println(c));
				var peticion = delete("http", "localhost",port, "/api/v1/usuario/1");
				
				var respuesta = restTemplate.exchange(peticion,Void.class);
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
				List<Usuario> usuarios = usuarioRepository.findAll();
				assertThat(usuarios).hasSize(1);
				assertThat(usuarios).allMatch(c->c.getId()!=1);
			}
			
			@Test
			@DisplayName("da error cuando no existe")
			public void errorCuandoNoExiste() {
				var peticion = delete("http", "localhost",port, "/api/v1/usuario/28");
				
				var respuesta = restTemplate.exchange(peticion,Void.class);
				
				assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
				assertThat(respuesta.hasBody()).isEqualTo(false);
			}
		}
	}
	
}
