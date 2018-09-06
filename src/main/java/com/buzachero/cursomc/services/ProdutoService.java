package com.buzachero.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buzachero.cursomc.domain.Produto;
import com.buzachero.cursomc.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
	public Produto buscar(Integer id) {
		Optional<Produto> obj = produtoRepo.findById(id);
		return obj.orElse(null);
	}
}
