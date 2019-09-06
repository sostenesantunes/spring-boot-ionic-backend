package com.sostenesantunes.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sostenesantunes.cursomc.domain.Pedido;
import com.sostenesantunes.cursomc.repositories.PedidoRepository;
import com.sostenesantunes.cursomc.services.exceptions.ObjectNotfoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository catRepos;
	
	public Pedido findById(Integer id) {
		Optional<Pedido> obj = catRepos.findById(id);
		return obj.orElseThrow(() -> new ObjectNotfoundException(
					"Objeto não encontrado Id: " + id  +", Tipo: " + Pedido.class.getName()));

	}
}
