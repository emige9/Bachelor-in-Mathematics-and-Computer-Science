package es.uma.informatica.sii.nivelesinstituto;

import es.uma.informatica.sii.nivelesinstituto.dtos.Nombre;
import es.uma.informatica.sii.nivelesinstituto.entities.Grupo;
import es.uma.informatica.sii.nivelesinstituto.entities.Nivel;
import es.uma.informatica.sii.nivelesinstituto.repositories.GrupoRepo;
import es.uma.informatica.sii.nivelesinstituto.repositories.NivelRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("En el servicio de niveles y grupos")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NivelesInstitutoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private NivelRepo nivelRepository;

    @Autowired
    private GrupoRepo grupoRepository;

    @BeforeEach
    public void initializeDatabase() {
        nivelRepository.deleteAll();
        grupoRepository.deleteAll();
    }

    private URI uri(String scheme, String host, int port, String... paths) {
        UriBuilderFactory ubf = new DefaultUriBuilderFactory();
        UriBuilder ub = ubf.builder()
                .scheme(scheme)
                .host(host).port(port);
        for (String path : paths) {
            ub = ub.path(path);
        }
        return ub.build();
    }

    private RequestEntity<Void> get(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.get(uri)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        return peticion;
    }

    private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.delete(uri)
                .build();
        return peticion;
    }

    private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(object);
        return peticion;
    }

    private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(object);
        return peticion;
    }

    @Nested
    @DisplayName("cuando no hay niveles")
    public class NivelesVacios {

        @Test
        @DisplayName("devuelve la lista de niveles vacía")
        public void devuelveNiveles() {

            var peticion = get("http", "localhost", port, "/niveles");

            var respuesta = restTemplate.exchange(peticion,
                    new ParameterizedTypeReference<List<Nivel>>() {
                    });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody()).isEmpty();
        }

        @Test
        @DisplayName("devuelve error al obtener un nivel concreto")
        public void errorAlObtenerNivelConcreto() {
            var peticion = get("http", "localhost", port, "/niveles/1");

            var respuesta = restTemplate.exchange(peticion,
                    new ParameterizedTypeReference<Nivel>() {
                    });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        @Test
        @DisplayName("devuelve error al modificar un nivel que no existe")
        public void modificarNivelInexistente() {
            var nivel = Nivel.builder().nombre("ESO").build();
            var peticion = put("http", "localhost", port, "/niveles/1", nivel);

            var respuesta = restTemplate.exchange(peticion, Void.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        @Test
        @DisplayName("devuelve error al eliminar un nivel que no existe")
        public void eliminarNivelInexistente() {
            var peticion = delete("http", "localhost", port, "/niveles/1");

            var respuesta = restTemplate.exchange(peticion, Void.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        @Test
        @DisplayName("inserta correctamente un nivel")
        public void insertaNivel() {

            // Preparamos el ingrediente a insertar
            var nivel = Nivel.builder()
                    .nombre("ESO")
                    .build();
            // Preparamos la petición con el ingrediente dentro
            var peticion = post("http", "localhost", port, "/niveles", nivel);

            // Invocamos al servicio REST
            var respuesta = restTemplate.exchange(peticion, Void.class);

            // Comprobamos el resultado
            assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
            assertThat(respuesta.getHeaders().get("Location").get(0))
                    .startsWith("http://localhost:" + port + "/niveles");

            List<Nivel> nivelesBD = nivelRepository.findAll();
            assertThat(nivelesBD).hasSize(1);
            assertThat(respuesta.getHeaders().get("Location").get(0))
                    .endsWith("/" + nivelesBD.get(0).getId());
            assertThat(nivel.getNombre()).isEqualTo(nivelesBD.get(0).getNombre());
        }
    }

    @Nested
    @DisplayName("En el sercivio de niveles y grupos cuando hay datos")
    public class NivelesLlenos {

        @BeforeEach
        public void insertarDatos() {

            var eso = new Nivel();
            eso.setNombre("ESO");
            nivelRepository.save(eso);

            var grupo = new Grupo();
            grupo.setNombre("GrupoA");
            grupo.setNivel(eso);
            grupoRepository.save(grupo);

        }

        @Test
        @DisplayName("devuelve una lista de niveles")
        public void devuelveListaNiveles() {
            var peticion = get("http", "localhost", port, "/niveles");

            var respuesta = restTemplate.exchange(peticion,
                    new ParameterizedTypeReference<List<Nivel>>() {
                    });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody().size()).isEqualTo(1);
        }

        /*
         * @Test
         * 
         * @DisplayName("inserta correctamente un nivel")
         * public void insertaNivel() {
         * 
         * // Preparamos el ingrediente a insertar
         * var nivel = Nivel.builder()
         * .nombre("FP")
         * .build();
         * // Preparamos la petición con el ingrediente dentro
         * var peticion = post("http", "localhost", port, "/niveles", nivel);
         * 
         * // Invocamos al servicio REST
         * var respuesta = restTemplate.exchange(peticion, Void.class);
         * 
         * // Comprobamos el resultado
         * assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
         * assertThat(respuesta.getHeaders().get("Location").get(0))
         * .startsWith("http://localhost:" + port + "/niveles");
         * 
         * List<Nivel> nivelesBD = nivelRepository.findAll();
         * assertThat(nivelesBD).hasSize(2);
         * assertThat(respuesta.getHeaders().get("Location").get(0))
         * .endsWith("/" + nivelesBD.get(1).getId());
         * assertThat(nivel.getNombre()).isEqualTo(nivelesBD.get(1).getNombre());
         * }
         */

        @Test
        @DisplayName("da error cuando inserta un nivel que ya existe")
        public void insertaNivelExistente() {

            // Preparamos el ingrediente a insertar
            var nivel = Nivel.builder()
                    .nombre("ESO")
                    .build();
            // Preparamos la petición con el ingrediente dentro
            var peticion = post("http", "localhost", port, "/niveles", nivel);

            // Invocamos al servicio REST
            var respuesta = restTemplate.exchange(peticion, Void.class);

            // Comprobamos el resultado
            assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
        }

        @Test
        @DisplayName("obtiene un nivel concreto")
        public void obtenerNivelConcreto() {
            var peticion = get("http", "localhost", port, "/niveles/1");

            var respuesta = restTemplate.exchange(peticion,
                    new ParameterizedTypeReference<Nivel>() {
                    });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody().getNombre()).isEqualTo("ESO");
        }

        @Test
        @DisplayName("modificar un nivel correctamente")
        public void modificarNivel() {
            var nivel = Nivel.builder().nombre("Bachillerato").build();
            var peticion = put("http", "localhost", port, "/niveles/1", nivel);

            var respuesta = restTemplate.exchange(peticion, Void.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(nivelRepository.findById(1L).get().getNombre()).isEqualTo("Bachillerato");
        }

        @Test
        @DisplayName("da error al modificar un nivel con un nombre ya existente")
        public void modificarNivelConNombreExistente() {
            var bachillerato = new Nivel();
            bachillerato.setNombre("Bachillerato");
            nivelRepository.save(bachillerato);
            var nivel = Nivel.builder().nombre("ESO").build();
            var peticion = put("http", "localhost", port, "/niveles/2", nivel);

            var respuesta = restTemplate.exchange(peticion, Void.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
        }

        @Test
        @DisplayName("eliminar un nivel correctamente")
        public void eliminarNivel() {
            var bachillerato = new Nivel();
            bachillerato.setNombre("Bachillerato");
            nivelRepository.save(bachillerato);

            var peticion = delete("http", "localhost", port, "/niveles/2");

            var respuesta = restTemplate.exchange(peticion, Void.class);

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(nivelRepository.count()).isEqualTo(1);
        }

        @Test
        @DisplayName("devuelve error al eliminar un nivel que tiene grupos asociados")
        public void errorAlEliminarNivelConGrupos() {
            // Preparamos el nivel existente
            var nivel = Nivel.builder()
                    .nombre("Bachillerato")
                    .build();

            // Insertamos el nivel
            var peticionNivel = post("http", "localhost", port, "/niveles", nivel);
            restTemplate.exchange(peticionNivel, Void.class);

            // Preparamos el grupo asociado al nivel
            var grupo = Grupo.builder()
                    .nombre("GrupoC")
                    .nivel(nivel)
                    .build();

            // Insertamos el grupo asociado al nivel
            var peticionGrupo = post("http", "localhost", port, "/niveles/2/grupos", grupo);
            restTemplate.exchange(peticionGrupo, Void.class);

            // Intentamos eliminar el nivel que tiene grupos asociados
            var peticionEliminarNivel = delete("http", "localhost", port, "/niveles/2");
            var respuestaEliminarNivel = restTemplate.exchange(peticionEliminarNivel, Void.class);

            // Comprobamos el resultado
            assertThat(respuestaEliminarNivel.getStatusCode().value()).isEqualTo(403);
        }

        @Test
        @DisplayName("inserta correctamente un grupo en un nivel")
        public void insertaGrupoEnNivel() {

            // Preparamos el grupo a insertar en el nivel
            var grupo = Grupo.builder()
                    .nombre("GrupoB")
                    .build();

            // Insertamos el grupo en el nivel recién creado
            var peticionGrupo = post("http", "localhost", port, "/niveles/1/grupos", grupo);
            var respuestaGrupo = restTemplate.exchange(peticionGrupo, Void.class);

            // Comprobamos el resultado
            assertThat(respuestaGrupo.getStatusCode().value()).isEqualTo(201);

            // Comprobamos que el grupo se haya insertado correctamente en el nivel
            List<Grupo> gruposBD = grupoRepository.findAll();
            assertThat(gruposBD).hasSize(2);
            assertThat(gruposBD.get(1).getNombre()).isEqualTo("GrupoB");
        }

        @Test
        @DisplayName("devuelve error al añadir grupo a nivel inexistente")
        public void errorAlAgregarGrupoANivelInexistente() {
            // Preparamos el grupo a insertar en un nivel inexistente
            var grupo = Nombre.builder()
                    .nombre("GrupoA")
                    .build();

            // Intentamos agregar el grupo a un nivel inexistente (nivel con ID 999)
            var peticion = post("http", "localhost", port, "/niveles/999/grupos", grupo);
            var respuesta = restTemplate.exchange(peticion, Void.class);

            // Comprobamos el resultado
            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        @Test
        @DisplayName("devuelve error al añadir grupo con nombre existente en nivel")
        public void errorAlAgregarGrupoConNombreExistenteEnNivel() {

            // Preparamos el nuevo grupo a insertar con el mismo nombre
            var nuevoGrupo = Nombre.builder()
                    .nombre("GrupoA")
                    .build();

            // Intentamos agregar el grupo con el mismo nombre al nivel
            var peticionGrupo = post("http", "localhost", port, "/niveles/1/grupos", nuevoGrupo);
            var respuestaGrupo = restTemplate.exchange(peticionGrupo, Void.class);

            // Comprobamos el resultado
            assertThat(respuestaGrupo.getStatusCode().value()).isEqualTo(403);
        }

        @Test
        @DisplayName("modifica el nombre de un grupo correctamente")
        public void modificaNombreDeGrupo() {
            var nuevoNombre = Nombre.builder()
                    .nombre("NuevoGrupo")
                    .build();
            var peticionModificarNombre = put("http", "localhost", port, "/niveles/1/grupos/1", nuevoNombre);
            var respuestaModificarNombre = restTemplate.exchange(peticionModificarNombre, Void.class);

            // Comprobamos el resultado
            assertThat(respuestaModificarNombre.getStatusCode().value()).isEqualTo(200);
            assertThat(grupoRepository.findById(1L).get().getNombre()).isEqualTo("NuevoGrupo");
        }

        @Test
        @DisplayName("devuelve error al modificar nombre de grupo de nivel inexistente")
        public void errorAlModificarNombreDeGrupoDeNivelInexistente() {
            // Intentamos modificar el nombre de un grupo de un nivel inexistente (nivel con
            // ID 999, grupo con ID 1)
            var nuevoNombre = Nombre.builder()
                    .nombre("NuevoGrupo")
                    .build();
            var peticion = put("http", "localhost", port, "/niveles/999/grupos/1", nuevoNombre);
            var respuesta = restTemplate.exchange(peticion, Void.class);

            // Comprobamos el resultado
            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        @Test
        @DisplayName("devuelve error al modificar nombre de grupo inexistente de nivel existente")
        public void errorAlModificarNombreDeGrupoInexistenteDeNivelExistente() {
            var nuevoNombre = Nombre.builder()
                    .nombre("NuevoGrupo")
                    .build();
            var peticion = put("http", "localhost", port, "/niveles/1/grupos/2", nuevoNombre);
            var respuesta = restTemplate.exchange(peticion, Void.class);

            // Comprobamos el resultado
            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        @Test
        @DisplayName("devuelve error al modificar nombre de grupo con nombre existente en nivel")
        public void errorAlModificarNombreDeGrupoConNombreExistenteEnNivel() {
            var nivel = Nivel.builder()
                    .nombre("FP")
                    .build();

            // Insertamos el nivel
            var peticionNivel = post("http", "localhost", port, "/niveles", nivel);
            restTemplate.exchange(peticionNivel, Void.class);

            var grupo1 = Grupo.builder()
                    .nombre("GrupoB")
                    .nivel(nivel)
                    .build();
            var grupo2 = Grupo.builder()
                    .nombre("NuevoGrupo")
                    .nivel(nivel)
                    .build();

            var peticionGrupo1 = post("http", "localhost", port, "/niveles/2/grupos", grupo1);
            restTemplate.exchange(peticionGrupo1, Void.class);

            var peticionGrupo2 = post("http", "localhost", port, "/niveles/2/grupos", grupo2);
            restTemplate.exchange(peticionGrupo2, Void.class);

            var nuevoNombre = Nombre.builder()
                    .nombre("NuevoGrupo")
                    .build();

            var peticion = put("http", "localhost", port, "/niveles/2/grupos/2", nuevoNombre);
            var respuesta = restTemplate.exchange(peticion, Void.class);

            // Comprobamos el resultado
            assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
        }

        @Test
        @DisplayName("devuelve error al eliminar grupo de nivel inexistente")
        public void errorAlEliminarGrupoDeNivelInexistente() {
            // Intentamos eliminar un grupo de un nivel inexistente (nivel con ID 999, grupo
            // con ID 1)
            var peticion = delete("http", "localhost", port, "/niveles/999/grupos/1");
            var respuesta = restTemplate.exchange(peticion, Void.class);

            // Comprobamos el resultado
            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        @Test
        @DisplayName("devuelve error al eliminar grupo inexistente de nivel existente")
        public void errorAlEliminarGrupoInexistenteDeNivelExistente() {

            // Intentamos eliminar un grupo inexistente (grupo con ID 1) de un nivel
            // existente
            var peticion = delete("http", "localhost", port, "/niveles/1/grupos/2");
            var respuesta = restTemplate.exchange(peticion, Void.class);

            // Comprobamos el resultado
            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        @Test
        @DisplayName("devuelve error al eliminar un grupo que no está asociado al nivel")
        public void errorAlEliminarGrupoNoAsociadoANivel() {
            // Preparamos el nivel existente
            var nivel = Nivel.builder()
                    .nombre("Bachillerato")
                    .build();

            // Insertamos el nivel
            var peticionNivel = post("http", "localhost", port, "/niveles", nivel);
            restTemplate.exchange(peticionNivel, Void.class);

            // Preparamos un grupo que no está asociado al nivel
            var grupo = Grupo.builder()
                    .nombre("GrupoC")
                    .build();

            // Insertamos el grupo sin asociarlo al nivel
            var peticionGrupo = post("http", "localhost", port, "/niveles/2/grupos", grupo);
            restTemplate.exchange(peticionGrupo, Void.class);

            // Intentamos eliminar el grupo del nivel
            var peticionEliminarGrupo = delete("http", "localhost", port, "/niveles/2/grupos/4");
            var respuestaEliminarGrupo = restTemplate.exchange(peticionEliminarGrupo, Void.class);

            // Comprobamos el resultado
            assertThat(respuestaEliminarGrupo.getStatusCode().value()).isEqualTo(404);
        }

        @Test
        @DisplayName("elimina un grupo de un nivel")
        public void eliminaGrupoDeNivel() {
            // Preparamos el nivel existente
            var nivel = Nivel.builder()
                    .nombre("Bachillerato")
                    .build();

            // Insertamos el nivel
            var peticionNivel = post("http", "localhost", port, "/niveles", nivel);
            restTemplate.exchange(peticionNivel, Void.class);

            // Preparamos el grupo existente asociado al nivel
            var grupo = Grupo.builder()
                    .nombre("GrupoB")
                    .nivel(nivel)
                    .build();

            // Insertamos el grupo en el nivel
            var peticionGrupo = post("http", "localhost", port, "/niveles/2/grupos", grupo);
            restTemplate.exchange(peticionGrupo, Void.class);

            // Eliminamos el grupo del nivel
            var peticionEliminarGrupo = delete("http", "localhost", port, "/niveles/2/grupos/2");
            var respuestaEliminarGrupo = restTemplate.exchange(peticionEliminarGrupo, Void.class);

            // Comprobamos el resultado
            assertThat(respuestaEliminarGrupo.getStatusCode().value()).isEqualTo(200);

            // Verificamos que el grupo ha sido eliminado correctamente
            assertThat(grupoRepository.count()).isEqualTo(1);
        }

    }

}
