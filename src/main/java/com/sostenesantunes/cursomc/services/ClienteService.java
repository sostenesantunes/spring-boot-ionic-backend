package com.sostenesantunes.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sostenesantunes.cursomc.domain.Cidade;
import com.sostenesantunes.cursomc.domain.Cliente;
import com.sostenesantunes.cursomc.domain.Endereco;
import com.sostenesantunes.cursomc.domain.enums.TipoCliente;
import com.sostenesantunes.cursomc.dto.ClienteDTO;
import com.sostenesantunes.cursomc.dto.ClienteNewDTO;
import com.sostenesantunes.cursomc.repositories.CidadeRepository;
import com.sostenesantunes.cursomc.repositories.ClienteRepository;
import com.sostenesantunes.cursomc.repositories.EnderecoRepository;
import com.sostenesantunes.cursomc.services.exceptions.DataIntegrityViolation;
import com.sostenesantunes.cursomc.services.exceptions.ObjectNotfoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository catRepos;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = catRepos.findById(id);
		return obj.orElseThrow(() -> new ObjectNotfoundException(
					"Objeto não encontrado Id: " + id  +", Tipo: " + Cliente.class.getName()));		
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = catRepos.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = findById(obj.getId());
		updateData(newObj, obj);
		return catRepos.save(newObj);
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			catRepos.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolation(
					"Não é possível excluir porque há Entidades relacionadas");
		}
	}
	
	public List<Cliente> findAll(){
		return catRepos.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest	 = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return catRepos.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto){
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto){
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	protected void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
