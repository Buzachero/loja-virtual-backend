package com.buzachero.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buzachero.cursomc.domain.Cliente;
import com.buzachero.cursomc.repositories.ClienteRepository;
import com.buzachero.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository catRepo;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = catRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}
