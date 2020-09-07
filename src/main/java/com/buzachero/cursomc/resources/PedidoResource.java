package com.buzachero.cursomc.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.buzachero.cursomc.dto.EstadoDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.buzachero.cursomc.domain.Categoria;
import com.buzachero.cursomc.domain.Pedido;
import com.buzachero.cursomc.dto.CategoriaDTO;
import com.buzachero.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedidoService;

	@ApiOperation(value = "Recupera um pedido pelo id",
			notes = "Recupera as informações de um pedido pelo seu identificador",
			nickname = "findPedido",
			consumes = "application/json",
			produces = "application/json",
			response = Pedido.class)
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> findPedido(@PathVariable Integer id) {
		Pedido obj = pedidoService.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Cadastra um pedido",
			notes = "Cadastra as informações de um pedido",
			nickname = "insertPedido",
			consumes = "application/json",
			produces = "application/json")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insertPedido(@Valid @RequestBody Pedido pedido) {
		pedido = pedidoService.insert(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(pedido.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Lista uma página de pedidos",
				 notes = "Lista as informações dos pedidos de forma paginada",
				 nickname = "findPedidoPage",
				 consumes = "application/json",
				 produces = "application/json",
			     response = Pedido[].class)
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> findPedidoPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="instante") String orderBy, 
			@RequestParam(value="direction", defaultValue="DESC") String direction) {
		Page<Pedido> list = pedidoService.findPage(page, linesPerPage, orderBy, direction);		
		return ResponseEntity.ok().body(list);
	}
}
