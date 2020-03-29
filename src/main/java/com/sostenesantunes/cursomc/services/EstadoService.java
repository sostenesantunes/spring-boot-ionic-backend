package com.sostenesantunes.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sostenesantunes.cursomc.domain.Estado;
import com.sostenesantunes.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository catRepo;
	
	public List<Estado> findAll(){
		return catRepo.findAllByOrderByNome();
	}
}
