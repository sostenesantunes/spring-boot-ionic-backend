package com.sostenesantunes.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.sostenesantunes.cursomc.domain.Categoria;
import com.sostenesantunes.cursomc.dto.CategoriaDTO;
import com.sostenesantunes.cursomc.repositories.CategoriaRepository;
import com.sostenesantunes.cursomc.services.exceptions.DataIntegrityViolation;
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
	
	public  Categoria insert(Categoria obj) {
		obj.setId(null);
		
		return catRepos.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria newObj = findById(obj.getId());
		updateData(newObj, obj);
		return catRepos.save(newObj);
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			catRepos.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolation(
					"Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<Categoria> findAll(){
		return catRepos.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest	 = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return catRepos.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDto){
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	
	protected void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}
