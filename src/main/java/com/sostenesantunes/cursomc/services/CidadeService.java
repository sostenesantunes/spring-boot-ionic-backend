package com.sostenesantunes.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sostenesantunes.cursomc.domain.Cidade;
import com.sostenesantunes.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository catRepo;
	
	public List<Cidade> findByEstado(Integer estadoId) {
		return catRepo.findCidades(estadoId);
	}
}
