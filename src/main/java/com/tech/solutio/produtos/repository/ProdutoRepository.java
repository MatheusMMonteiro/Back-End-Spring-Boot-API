package com.tech.solutio.produtos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.solutio.produtos.model.Produtos;

public interface ProdutoRepository extends JpaRepository<Produtos, Long> {

	public Optional<Produtos> findByNome(String name);
	
	public List<Produtos> findAllByNomeContainingIgnoreCase(String nome);
	
	
	
}
