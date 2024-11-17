package es.uma.informatica.sii.restusuarios.controladores;

import es.uma.informatica.sii.restusuarios.dtos.UsuarioDTO;
import es.uma.informatica.sii.restusuarios.dtos.UsuarioNuevoDTO;
import es.uma.informatica.sii.restusuarios.entidades.Usuario;

public class Mapper {
    public static UsuarioDTO toUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
            .id(usuario.getId())
            .nombre(usuario.getNombre())
            .apellidos(usuario.getApellidos())
            .email(usuario.getEmail())
            .build();
    }

    public static Usuario toUsuario(UsuarioNuevoDTO usuarioNuevoDTO) {
        return es.uma.informatica.sii.restusuarios.entidades.Usuario.builder()
            .nombre(usuarioNuevoDTO.getNombre())
            .apellidos(usuarioNuevoDTO.getApellidos())
            .contrasenia(usuarioNuevoDTO.getContrasenia())
            .email(usuarioNuevoDTO.getEmail())
            .build();
    }
}
