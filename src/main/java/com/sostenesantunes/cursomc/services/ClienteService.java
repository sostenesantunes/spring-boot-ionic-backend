package com.sostenesantunes.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sostenesantunes.cursomc.domain.Cliente;
import com.sostenesantunes.cursomc.repositories.ClienteRepository;
import com.sostenesantunes.cursomc.services.exceptions.ObjectNotfoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository catRepos;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = catRepos.findById(id);
		return obj.orElseThrow(() -> new ObjectNotfoundException(
					"Objeto não encontrado Id: " + id  +", Tipo: " + Cliente.class.getName()));	
		
	}
}
