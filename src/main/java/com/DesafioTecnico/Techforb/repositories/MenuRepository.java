package com.DesafioTecnico.Techforb.repositories;

import com.DesafioTecnico.Techforb.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
