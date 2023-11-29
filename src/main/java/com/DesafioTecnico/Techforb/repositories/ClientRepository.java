package com.DesafioTecnico.Techforb.repositories;


import com.DesafioTecnico.Techforb.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByDni(String dni);
    Client findByName(String name);

}
