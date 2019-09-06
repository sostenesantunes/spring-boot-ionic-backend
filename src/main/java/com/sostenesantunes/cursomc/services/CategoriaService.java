package com.sostenesantunes.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sostenesantunes.cursomc.domain.Categoria;
import com.sostenesantunes.cursomc.repositories.CategoriaRepository;
import com.sostenesantunes.cursomc.services.exceptions.ObjectNotfoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository catRepos;
	
	public Categoria findById(Integer id) {
		Optional<Categoria> obj = catRepos.findById(id);
		return obj.orElseThrow(() -> new ObjectNotfoundException(
					"Objeto não encontrado Id: " + id  +", Tipo: " + Categoria.class.getName()));

	}
}
