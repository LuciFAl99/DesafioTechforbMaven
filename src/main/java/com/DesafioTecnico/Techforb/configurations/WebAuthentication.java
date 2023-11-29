package com.DesafioTecnico.Techforb.configurations;

import com.DesafioTecnico.Techforb.models.Client;
import com.DesafioTecnico.Techforb.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    ClientRepository clientRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(dni -> {

            Client client = clientRepository.findByDni(dni);

            if (client != null) {
                if (client.getDni().equals("23005612")) {
                    return new User(client.getDni(), client.getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN"));
                }
                return new User(client.getDni(), client.getPassword(),
                        AuthorityUtils.createAuthorityList("CLIENT"));

            } else {
                throw new UsernameNotFoundException("Usuario desconocido: " + dni);
            }
        });

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
