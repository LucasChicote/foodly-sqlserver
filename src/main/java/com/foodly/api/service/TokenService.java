package com.foodly.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.foodly.api.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        Algorithm algoritmo = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("foodly-api")
                .withSubject(usuario.getEmail())
                .withClaim("role", usuario.getRole().name())
                .withExpiresAt(getExpirationDate())
                .sign(algoritmo);
    }

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("foodly-api")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            return "";
        }
    }

    public String getRole(String tokenJWT) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("foodly-api")
                    .build()
                    .verify(tokenJWT)
                    .getClaim("role")
                    .asString();
        } catch (JWTVerificationException ex) {
            return "";
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
