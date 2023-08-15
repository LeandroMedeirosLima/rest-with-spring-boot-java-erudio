package br.com.erudio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.com.erudio.security.jwt.JwtTokenProvider;

//TODO Implementar a classe de configuração 
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider tokenProvider;
    
    
    
}
