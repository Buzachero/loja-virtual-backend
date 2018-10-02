package com.buzachero.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.buzachero.cursomc.domain.Cliente;
import com.buzachero.cursomc.dto.ClienteDTO;
import com.buzachero.cursomc.repositories.ClienteRepository;
import com.buzachero.cursomc.services.exceptions.DataIntegrityException;
import com.buzachero.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = clienteRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente update(Cliente obj) {
		Cliente newCliente = find(obj.getId());
		updateData(newCliente, obj);
		return clienteRepo.save(newCliente);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			clienteRepo.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir porque há entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
		List<Cliente> categorias = clienteRepo.findAll();		
		return categorias;
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy); 
		return clienteRepo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}
}
