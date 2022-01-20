package com.tech.solutio.produtos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.solutio.produtos.model.Produtos;
import com.tech.solutio.produtos.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;
	
	public Optional<Object> cadastrarProduto (Produtos produto){
		return repository.findByNome(produto.getNome()).map(resp ->{
			return Optional.empty();
			}).orElseGet(() ->{
				return Optional.ofNullable(repository.save(produto));
			});
	}
	public Optional<Produtos> atualizarProduto(Produtos produto){
		return repository.findById(produto.getId()).map(resp ->{
			resp.setNome(produto.getNome());
			resp.setFornecedor(produto.getFornecedor());
			resp.setValor(produto.getValor());
			resp.setUsuario(produto.getUsuario());
			return Optional.ofNullable(repository.save(resp));
		}).orElseGet(() ->{
			return Optional.empty();
		});
	}

}
