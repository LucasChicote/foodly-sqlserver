package com.foodly.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurantes")
@Getter
@Setter
@NoArgsConstructor
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do restaurante é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(length = 100)
    private String categoria;

    @Column(name = "imagem_url", length = 500)
    private String imagemUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dono_id", nullable = false)
    private Usuario dono;
}
