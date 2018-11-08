package com.buzachero.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.buzachero.cursomc.domain.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

	@Query("SELECT cidade FROM Cidade cidade WHERE cidade.estado.id = :estadoId ORDER BY cidade.nome")
	@Transactional(readOnly=true)
	public List<Cidade> findCidades(@Param("estadoId") Integer estadoId);
	
}
