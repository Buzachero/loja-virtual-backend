package com.buzachero.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

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
	
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> find() {
		List<Estado> estadosList = estadoService.findAll();
		List<EstadoDTO> estadosListDTO = estadosList.stream().map(estado -> new EstadoDTO(estado)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(estadosListDTO);
	}
	
	@GetMapping("/{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidadesByEstado(@PathVariable Integer estadoId) {
		List<Cidade> cidadesList = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> cidadesListDTO = cidadesList.stream().map(cidade -> new CidadeDTO(cidade)).collect(Collectors.toList());
		return ResponseEntity.ok().body(cidadesListDTO);
	}
	
}
