package com.sostenesantunes.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sostenesantunes.cursomc.domain.Categoria;
import com.sostenesantunes.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository catRepos;
	
	public Categoria findById(Integer id) {
		Optional<Categoria> obj = catRepos.findById(id);
		
		
		return obj.orElse(null);
	}
}
