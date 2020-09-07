package com.buzachero.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.buzachero.cursomc.domain.Cliente;
import com.buzachero.cursomc.dto.ClienteDTO;
import com.buzachero.cursomc.dto.ClienteNewDTO;
import com.buzachero.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;

	@ApiOperation(value = "Recupera um cliente pelo id",
			notes = "Recupera as informações de um cliente pelo seu identificador",
			nickname = "findCliente",
			consumes = "application/json",
			produces = "application/json",
			response = Cliente.class)
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> findCliente(@PathVariable Integer id) {
		Cliente obj = clienteService.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Recupera um cliente pelo e-mail",
			notes = "Recupera as informações de um cliente pelo seu e-mail cadastrado",
			nickname = "findClienteByEmail",
			consumes = "application/json",
			produces = "application/json",
			response = Cliente.class)
	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Cliente> findClienteByEmail(@RequestParam(value="value") String email) {
		Cliente cliente = clienteService.findByEmail(email);
		return ResponseEntity.ok().body(cliente);
	}

	@ApiOperation(value = "Cadastra um cliente",
			notes = "Cadastra as informações de um cliente",
			nickname = "insertCliente",
			consumes = "application/json",
			produces = "application/json")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insertCliente(@Valid @RequestBody ClienteNewDTO clienteNewDTO) {
		Cliente cliente = clienteService.fromDTO(clienteNewDTO);
		cliente = clienteService.insert(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(cliente.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Atualiza um cliente",
			notes = "Atualiza as informações de um cliente cadastrado",
			nickname = "updateCliente",
			consumes = "application/json",
			produces = "application/json")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateCliente(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
		Cliente obj = clienteService.fromDTO(objDTO);
		obj.setId(id);
		obj = clienteService.update(obj);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Remove um cliente pelo id",
			notes = "Remove todas as informações de um cliente cadastrado pelo seu identificador",
			nickname = "deleteCliente",
			consumes = "application/json",
			produces = "application/json")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Lista todos os clientes cadastrados",
			notes = "Lista as informações de todos os clientes cadastrados",
			nickname = "findAll",
			consumes = "application/json",
			produces = "application/json")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = clienteService.findAll();
		List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@ApiOperation(value = "Lista uma página de clientes",
			notes = "Lista as informações dos clientes cadastrados de forma paginada",
			nickname = "findClientePage",
			consumes = "application/json",
			produces = "application/json")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Cliente> list = clienteService.findClientePage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

	@ApiOperation(value = "Atualiza/Adiciona foto de perfil do cliente",
			notes = "Atualiza ou adiciona a foto de perfil do cliente cadastrado",
			nickname = "uploadProfilePicture",
			consumes = "application/json",
			produces = "application/json")
	@RequestMapping(value="/picture", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file) {
		URI uri = clienteService.uploadProfilePicture(file);
				
		return ResponseEntity.created(uri).build();
	}
	
}
