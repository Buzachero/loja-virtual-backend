package com.buzachero.cursomc.resources;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buzachero.cursomc.domain.Produto;
import com.buzachero.cursomc.dto.CategoriaDTO;
import com.buzachero.cursomc.dto.ProdutoDTO;
import com.buzachero.cursomc.resources.utils.URL;
import com.buzachero.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;
	
	@ApiOperation(value = "Recupera um produto pelo id",
					notes = "Recupera as informações de um produto pelo seu identificador",
					nickname = "findProdutoById",
					consumes = "application/json",
					produces = "application/json",
					response = Produto.class)
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> findProdutoById(@PathVariable Integer id) {
		Produto obj = produtoService.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Lista os produtos por página",
			notes = "Lista as informações dos produtos de forma paginada",
			nickname = "listProductsPerPage",
			consumes = "application/json",
			produces = "application/json",
			response = ProdutoDTO.class)
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> listProductsPerPage(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
}
