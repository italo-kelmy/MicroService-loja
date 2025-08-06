package com.hgithub.com.italo_kelmy.loja_service.Repository;

import com.hgithub.com.italo_kelmy.loja_service.Entity.Eletronico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EletronicosRepository extends JpaRepository<Eletronico, Long> {
    Optional<Eletronico> findByNome(String nome);

    List<Eletronico> findByCategoria(String categoria);
}
