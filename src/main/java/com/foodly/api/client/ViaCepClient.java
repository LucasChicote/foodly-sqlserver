package com.foodly.api.client;

import com.foodly.api.model.Endereco;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("https://viacep.com.br/ws")
public interface ViaCepClient {

    @GetExchange("/{cep}/json")
    Endereco buscarCep(@PathVariable String cep);
}
