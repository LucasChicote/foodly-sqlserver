package com.foodly.api.config;

import com.foodly.api.client.ViaCepClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfig {

    @Bean
    public ViaCepClient viaCepClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://viacep.com.br/ws")
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();

        return factory.createClient(ViaCepClient.class);
    }
}
