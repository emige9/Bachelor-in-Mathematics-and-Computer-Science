package es.uma.informatica.practica3;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.Collections;
import java.util.List;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import es.uma.informatica.practica3.dtos.IngredienteDTO;
import es.uma.informatica.practica3.dtos.ProductoDTO;
import es.uma.informatica.practica3.entidades.Ingrediente;
import es.uma.informatica.practica3.entidades.Producto;
import es.uma.informatica.practica3.repositorios.IngredienteRepository;
import es.uma.informatica.practica3.repositorios.ProductoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayName("En el servicio de productos")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class Practica3ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Value(value="${local.server.port}")
	private int port;

	@Autowired
	private IngredienteRepository ingredienteRepo;

	@Autowired
	private ProductoRepository productoRepo;


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

	private void compruebaCampos(Ingrediente expected, Ingrediente actual) {
		assertThat(actual.getNombre()).isEqualTo(expected.getNombre());
	}

	private void compruebaCampos(Producto expected, Producto actual) {	
		assertThat(actual.getNombre()).isEqualTo(expected.getNombre());
		assertThat(actual.getDescripcion()).isEqualTo(expected.getDescripcion());
		assertThat(actual.getIngredientes()).isEqualTo(expected.getIngredientes());
	}

	@Nested
	@DisplayName("cuando la base de datos está vacía")
	public class BaseDatosVacia {

		@Test
		@DisplayName("error al obtener un ingrediente concreto")
		public void errorAlObtenerIngredienteConcreto() {
			var peticion = get("http", "localhost",port, "/ingredientes/1");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<IngredienteDTO>() {});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}
		
		@Test
		@DisplayName("devuelve error al modificar un ingrediente que no existe")
		public void modificarIngredienteInexistente() {
			var ingrediente = IngredienteDTO.builder().nombre("Chorizo").build();
			var peticion = put("http", "localhost",port, "/ingredientes/1", ingrediente);

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		@Test
		@DisplayName("devuelve error al eliminar un ingrediente que no existe")
		public void eliminarIngredienteInexistente() {
			var peticion = delete("http", "localhost",port, "/ingredientes/1");

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		@Test
		@DisplayName("inserta correctamente un ingrediente")
		public void insertaIngrediente() {

			// Preparamos el ingrediente a insertar
			var ingrediente = IngredienteDTO.builder()
					.nombre("Chorizo")
					.build();
			// Preparamos la petición con el ingrediente dentro
			var peticion = post("http", "localhost",port, "/ingredientes", ingrediente);

			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);

			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
			assertThat(respuesta.getHeaders().get("Location").get(0))
			.startsWith("http://localhost:"+port+"/ingredientes");

			List<Ingrediente> ingredientesBD = ingredienteRepo.findAll();
			assertThat(ingredientesBD).hasSize(1);
			assertThat(respuesta.getHeaders().get("Location").get(0))
			.endsWith("/"+ingredientesBD.get(0).getId());
			compruebaCampos(ingrediente.ingrediente(), ingredientesBD.get(0));
		}

		@Test
		@DisplayName("da error con un producto concreto")
		public void errorConProductoConcreto() {
			var peticion = get("http", "localhost",port, "/productos/1");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<ProductoDTO>() {});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		@Test
		@DisplayName("devuelve error al modificar un producto que no existe")
		public void modificarProductoInexistente() {
			var producto = ProductoDTO.builder().nombre("Chorizo").build();
			var peticion = put("http", "localhost",port, "/productos/1", producto);

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		@Test
		@DisplayName("devuelve error al eliminar un producto que no existe")
		public void eliminarProductoInexistente() {
			var peticion = delete("http", "localhost",port, "/productos/1");

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		@Test
		@DisplayName("inserta correctamente un producto")
		public void insertaProducto() {

			// Preparamos el producto a insertar
			var producto = ProductoDTO.builder()
					.nombre("Hamburguesa")
					.build();
			// Preparamos la petición con el ingrediente dentro
			var peticion = post("http", "localhost",port, "/productos", producto);

			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);

			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
			assertThat(respuesta.getHeaders().get("Location").get(0))
			.startsWith("http://localhost:"+port+"/productos");

			List<Producto> productosBD = productoRepo.findAll();
			assertThat(productosBD).hasSize(1);
			assertThat(respuesta.getHeaders().get("Location").get(0))
			.endsWith("/"+productosBD.get(0).getId());
			compruebaCampos(producto.producto(), productosBD.get(0));
		}
		
				
		@Test
		@DisplayName("devuelve error cuando no aporto información del ingrediente al insertar producto")
		public void errorAlInsertarProductoSinInfoDeIngrediente() {

			var ingrediente = IngredienteDTO.builder().build();
			
			// Preparamos el producto a insertar
			var producto = ProductoDTO.builder()
					.nombre("Hamburguesa")
					.ingredientes(Collections.singleton(ingrediente))
					.build();
			// Preparamos la petición con el ingrediente dentro
			var peticion = post("http", "localhost",port, "/productos", producto);

			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);

			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		@Test
		@DisplayName("inserta un producto con ingrediente inexistente")
		public void insertaProductoConIngredienteInexistente() {

			var ingrediente = IngredienteDTO.builder()
					.nombre("Especias")
					.build();

			// Preparamos el producto a insertar
			var producto = ProductoDTO.builder()
					.nombre("Hamburguesa")
					.ingredientes(Collections.singleton(ingrediente))
					.build();
			// Preparamos la petición con el ingrediente dentro
			var peticion = post("http", "localhost",port, "/productos", producto);

			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);

			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}
	}

	@Nested
	@DisplayName("cuando la base de datos tiene datos")
	public class BaseDatosConDatos {

		@BeforeEach
		public void insertarDatos() {

			var chorizo = new Ingrediente();
			chorizo.setNombre("Chorizo");
			ingredienteRepo.save(chorizo);

			var producto = new Producto();
			producto.setNombre("Hamburguesa");
			producto.setIngredientes(Collections.singleton(chorizo));

			productoRepo.save(producto);
		}

		@Test
		@DisplayName("da error cuando inserta un ingrediente que ya existe")
		public void insertaIngredienteExistente() {

			// Preparamos el ingrediente a insertar
			var ingrediente = IngredienteDTO.builder()
					.nombre("Chorizo")
					.build();
			// Preparamos la petición con el ingrediente dentro
			var peticion = post("http", "localhost",port, "/ingredientes", ingrediente);

			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);

			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(409);
		}

		@Test
		@DisplayName("da error cuando inserta un producto que ya existe")
		public void insertaProductoExistente() {
			var ingrediente = IngredienteDTO.builder()
					.nombre("Especias")
					.build();

			// Preparamos el producto a insertar
			var producto = ProductoDTO.builder()
					.nombre("Hamburguesa")
					.ingredientes(Collections.singleton(ingrediente))
					.build();
			
			// Preparamos la petición con el ingrediente dentro
			var peticion = post("http", "localhost",port, "/productos", producto);
			
			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);

			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(409);
		}

		@Test
		@DisplayName("obtiene un producto correctamente")
		public void errorConProductoConcreto() {
			var peticion = get("http", "localhost",port, "/productos/1");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<ProductoDTO>() {});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody().getNombre()).isEqualTo("Hamburguesa");
		}

		@Test
		@DisplayName("modificar un producto correctamente")
		public void modificarProducto() {
			var producto = ProductoDTO.builder().nombre("Chorizo").build();
			var peticion = put("http", "localhost",port, "/productos/1", producto);

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(productoRepo.findById(1L).get().getNombre()).isEqualTo("Chorizo");
		}

		@Test
		@DisplayName("eliminar un producto correctamente")
		public void eliminarProducto() {
			var peticion = delete("http", "localhost",port, "/productos/1");

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(productoRepo.count()).isEqualTo(0);
		}
		
		@Test
		@DisplayName("devuelve una lista de productos")
		public void devuelveListaProductos() {
			var peticion = get("http", "localhost",port, "/productos");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<ProductoDTO>>() {});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody().size()).isEqualTo(1);
		}
		
		@Test
		@DisplayName("devuelve una lista de ingredientes")
		public void devuelveListaIngredientes() {
			var peticion = get("http", "localhost",port, "/ingredientes");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<IngredienteDTO>>() {});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody().size()).isEqualTo(1);
		}
		
		@Test
		@DisplayName("modificar un ingrediente correctamente")
		public void modificarIngrediente() {
			var ingrediente = IngredienteDTO.builder().nombre("Morcilla").build();
			var peticion = put("http", "localhost",port, "/ingredientes/1", ingrediente);

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(ingredienteRepo.findById(1L).get().getNombre()).isEqualTo("Morcilla");
		}

		@Test
		@DisplayName("eliminar un ingrediente correctamente")
		public void eliminarIngrediente() {
			var mantequilla = new Ingrediente();
			mantequilla.setNombre("Mantequilla");
			ingredienteRepo.save(mantequilla);
			
			var peticion = delete("http", "localhost",port, "/ingredientes/2");

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(ingredienteRepo.count()).isEqualTo(1);
		}
		
		@Test
		@DisplayName("obtiene un ingrediente concreto")
		public void obtenerIngredienteConcreto() {
			var peticion = get("http", "localhost",port, "/ingredientes/1");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<IngredienteDTO>() {});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody().getNombre()).isEqualTo("Chorizo");
		}

		@Test
		@DisplayName("inserta correctamente un producto dando ID de ingrediente")
		public void insertaProductoIndicandoIngredienteConId() {

			var chorizo = IngredienteDTO.builder().id(1L).build();
			
			// Preparamos el producto a insertar
			var producto = ProductoDTO.builder()
					.nombre("Salchicha")
					.ingredientes(Collections.singleton(chorizo))
					.build();
			// Preparamos la petición con el ingrediente dentro
			var peticion = post("http", "localhost",port, "/productos", producto);

			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);

			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
			assertThat(respuesta.getHeaders().get("Location").get(0))
			.startsWith("http://localhost:"+port+"/productos");

			List<Producto> productosBD = productoRepo.findAll();
			Producto salchicha = productosBD.stream()
									.filter(p->p.getNombre().equals("Salchicha"))
									.findFirst()
									.get();
			assertThat(productosBD).hasSize(2);
			assertThat(respuesta.getHeaders().get("Location").get(0))
				.endsWith("/"+salchicha.getId());
			compruebaCampos(producto.producto(), salchicha);
		}


	}
}
