package com.sostenesantunes.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.sostenesantunes.cursomc.domain.Categoria;
import com.sostenesantunes.cursomc.domain.Produto;
import com.sostenesantunes.cursomc.repositories.CategoriaRepository;
import com.sostenesantunes.cursomc.repositories.ProdutoRepository;
import com.sostenesantunes.cursomc.services.exceptions.ObjectNotfoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository catRepos;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto findById(Integer id) {
		Optional<Produto> obj = catRepos.findById(id);
		return obj.orElseThrow(() -> new ObjectNotfoundException(
					"Objeto não encontrado Id: " + id  +", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest	 = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return catRepos.findDistinctByNomeContainingAndCategoriasIn	(nome, categorias, pageRequest);
	}
}
