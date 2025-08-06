package com.hgithub.com.italo_kelmy.loja_service.Controller;

import com.hgithub.com.italo_kelmy.loja_service.Entity.Eletronico;
import com.hgithub.com.italo_kelmy.loja_service.Service.EletronicosService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/produtos")
public class EletronicoController {

    private final EletronicosService service;

    @Autowired
    public EletronicoController(EletronicosService service) {
        this.service = service;
    }

    @GetMapping("/catalago")
    public List<Eletronico> lista() {
        return service.listaDeProdutos();
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<?> buscarPorNome(@PathVariable String nome) {
        return service.buscarPorNome(nome);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<?> buscarPorCategoria(@PathVariable String categoria) {
        return service.buscarPorCategoria(categoria);
    }

    @PostMapping("/buscarPeloId")
    public ResponseEntity<?> buscarProdutoPorId(@RequestBody Eletronico id) {
        return service.buscarProdutoPorId(id);
    }

    @PostMapping("/comprarProduto")
    public ResponseEntity<?> comprar(@RequestBody Eletronico eletronico) {
        int quantidadeEscolhida = eletronico.getQuantidade();
        return service.buscar_Produto_PorId_ParaCompra(eletronico, quantidadeEscolhida);
    }


}
