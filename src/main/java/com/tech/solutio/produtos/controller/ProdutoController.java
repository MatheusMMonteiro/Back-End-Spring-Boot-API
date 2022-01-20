package com.tech.solutio.produtos.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tech.solutio.produtos.model.Produtos;
import com.tech.solutio.produtos.repository.ProdutoRepository;
import com.tech.solutio.produtos.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository repository;

	@Autowired
	private ProdutoService service;

	@GetMapping()
	public ResponseEntity<List<Produtos>> buscarTodos() {
		List<Produtos> objetoLista = repository.findAll();

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}

	@GetMapping("/nome/{nome_produto}")
	public ResponseEntity<List<Produtos>> buscarNome(@PathVariable(value = "nome_produto") String nome) {
		List<Produtos> objetoLista = repository.findAllByNomeContainingIgnoreCase(nome);

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}
	
	@GetMapping("/fornecedor/{fornecedor_produto}")
	public ResponseEntity<List<Produtos>> buscarFornecedor(@PathVariable(value = "fornecedor_produto") String fornecedor) {
		List<Produtos> objetoLista = repository.findAllByFornecedorContainingIgnoreCase(fornecedor);

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}
	
	@GetMapping("/id/{id_produto}")
	public ResponseEntity<Produtos> buscarPorId(@PathVariable(value = "id_produto") Long id){
		return repository.findById(id).map(resp -> ResponseEntity.status(200).body(resp))
				.orElseThrow(() -> {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Id inexistente, passe um Id válido para pesquisa");
				});
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Produtos> cadastrar(@RequestBody Produtos produto){
		return service.cadastrarProduto(produto)
				.map(resp -> ResponseEntity.status(201).body(produto))
				.orElseThrow(() ->{
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Usuario existente, cadastre outro usuário!");
				});
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Produtos> atualizar (@Valid @RequestBody Produtos produto){
		return service.atualizarProduto(produto)
				.map(resp -> ResponseEntity.status(201).body(produto))
				.orElseThrow(() ->{
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"Necessário que passe um idUsuario válido");
				});				
	}
	
	@DeleteMapping("/deletar/{id_produto}")
	public ResponseEntity<Object> deletar(@PathVariable(value = "id_produto") Long id){
		return repository.findById(id).map(resp ->{
			repository.deleteById(id);
			return ResponseEntity.status(200).build();
		}).orElseThrow(() ->{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"ID inexistente, passe um ID valido para deletar!.");
		});	
	}
	
	

}
