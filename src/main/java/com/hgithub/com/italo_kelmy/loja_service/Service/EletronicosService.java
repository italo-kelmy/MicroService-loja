package com.hgithub.com.italo_kelmy.loja_service.Service;

import com.hgithub.com.italo_kelmy.loja_service.Component.Carrinho;
import com.hgithub.com.italo_kelmy.loja_service.Entity.Eletronico;
import com.hgithub.com.italo_kelmy.loja_service.Repository.EletronicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EletronicosService {
    private final EletronicosRepository repository;

    @Autowired
    public EletronicosService(EletronicosRepository repository) {
        this.repository = repository;
    }

    public List<Eletronico> listaDeProdutos() {
        return repository.findAll();
    }

    public ResponseEntity<?> buscarProdutoPorId(Eletronico produtoId) {
        Optional<Eletronico> findByID = repository.findById(produtoId.getId());

        if (findByID.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }

        return ResponseEntity.ok(findByID.get());
    }

    public ResponseEntity<?> buscar_Produto_PorId_ParaCompra(Eletronico produtoId, int quantidade) {
        Optional<Eletronico> findByID = repository.findById(produtoId.getId());

        if (findByID.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }


        Eletronico eletronico = findByID.get();

        if (eletronico.getQuantidade() < quantidade) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Quantidade Insuficiente");
        }

        if (eletronico.getQuantidade() <= 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Apenas quantidade maior que 0");
        }

        eletronico.setQuantidade(eletronico.getQuantidade() - quantidade);
        repository.save(eletronico);
        return ResponseEntity.ok(eletronico);
    }


    public ResponseEntity<?> buscarPorNome(String nome) {
        Optional<Eletronico> findByNome = repository.findByNome(nome);

        if (findByNome.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vish, Que triste\nSua busca por  " + nome + "  não obteve resultado");
        }

        return ResponseEntity.ok(findByNome);
    }


    public ResponseEntity<?> buscarPorCategoria(String categoria) {
        List<Eletronico> findByCategoria = repository.findByCategoria(categoria);

        if (findByCategoria.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada");
        }

        return ResponseEntity.ok(findByCategoria);
    }


}
