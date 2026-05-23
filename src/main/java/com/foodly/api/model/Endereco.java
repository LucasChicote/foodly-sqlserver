package com.foodly.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Endereco {

    private String cep;
    private String logradouro;
    private String bairro;
}
