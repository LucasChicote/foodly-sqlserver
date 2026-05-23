package com.foodly.api.service;

import com.foodly.api.dto.RestauranteDTO;
import com.foodly.api.model.Restaurante;
import com.foodly.api.model.Usuario;
import com.foodly.api.repository.RestauranteRepository;
import com.foodly.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;

    public RestauranteService(RestauranteRepository restauranteRepository,
                              UsuarioRepository usuarioRepository) {
        this.restauranteRepository = restauranteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public RestauranteDTO.Response criar(RestauranteDTO.Request dto, String emailDono) {
        Usuario dono = usuarioRepository.findUsuarioByEmail(emailDono)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Restaurante restaurante = new Restaurante();
        restaurante.setNome(dto.nome());
        restaurante.setDescricao(dto.descricao());
        restaurante.setCategoria(dto.categoria());
        restaurante.setImagemUrl(dto.imagemUrl());
        restaurante.setDono(dono);

        return RestauranteDTO.Response.fromRestaurante(restauranteRepository.save(restaurante));
    }

    public RestauranteDTO.Response atualizar(Long id, RestauranteDTO.Request dto, String emailDono) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));

        Usuario dono = usuarioRepository.findUsuarioByEmail(emailDono)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (!restaurante.getDono().getId().equals(dono.getId())) {
            throw new IllegalArgumentException("Você não tem permissão para editar este restaurante");
        }

        restaurante.setNome(dto.nome());
        restaurante.setDescricao(dto.descricao());
        restaurante.setCategoria(dto.categoria());
        restaurante.setImagemUrl(dto.imagemUrl());

        return RestauranteDTO.Response.fromRestaurante(restauranteRepository.save(restaurante));
    }

    public void deletar(Long id, String emailDono) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));

        Usuario dono = usuarioRepository.findUsuarioByEmail(emailDono)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (!restaurante.getDono().getId().equals(dono.getId())) {
            throw new IllegalArgumentException("Você não tem permissão para deletar este restaurante");
        }

        restauranteRepository.deleteById(id);
    }

    public List<RestauranteDTO.Response> listarTodos() {
        return restauranteRepository.findAll()
                .stream()
                .map(RestauranteDTO.Response::fromRestaurante)
                .toList();
    }

    public List<RestauranteDTO.Response> listarDoOwner(String emailDono) {
        Usuario dono = usuarioRepository.findUsuarioByEmail(emailDono)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return restauranteRepository.findByDonoId(dono.getId())
                .stream()
                .map(RestauranteDTO.Response::fromRestaurante)
                .toList();
    }
}
