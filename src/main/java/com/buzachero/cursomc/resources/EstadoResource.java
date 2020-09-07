package com.buzachero.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import com.buzachero.cursomc.domain.Cliente;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buzachero.cursomc.domain.Cidade;
import com.buzachero.cursomc.domain.Estado;
import com.buzachero.cursomc.dto.CidadeDTO;
import com.buzachero.cursomc.dto.EstadoDTO;
import com.buzachero.cursomc.services.CidadeService;
import com.buzachero.cursomc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;

	@ApiOperation(value = "Recupera todos os estados",
			notes = "Recupera as informações de todos os estados",
			nickname = "findAllEstados",
			consumes = "application/json",
			produces = "application/json",
			response = EstadoDTO[].class)
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAllEstados() {
		List<Estado> estadosList = estadoService.findAll();
		List<EstadoDTO> estadosListDTO = estadosList.stream().map(estado -> new EstadoDTO(estado)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(estadosListDTO);
	}

	@ApiOperation(value = "Recupera todas as cidades do estado pelo id",
			notes = "Recupera as informações de todas as cidades de um determinado estado pelo seu identificador",
			nickname = "findCidadesByEstado",
			consumes = "application/json",
			produces = "application/json",
			response = CidadeDTO[].class)
	@GetMapping("/{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidadesByEstado(@PathVariable Integer estadoId) {
		List<Cidade> cidadesList = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> cidadesListDTO = cidadesList.stream().map(cidade -> new CidadeDTO(cidade)).collect(Collectors.toList());
		return ResponseEntity.ok().body(cidadesListDTO);
	}
	
}
