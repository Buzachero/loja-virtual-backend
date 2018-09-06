package com.buzachero.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buzachero.cursomc.domain.Categoria;
import com.buzachero.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository catRepo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = catRepo.findById(id);
		return obj.orElse(null);
	}
}
