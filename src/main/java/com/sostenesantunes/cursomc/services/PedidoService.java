package com.sostenesantunes.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.sostenesantunes.cursomc.domain.Cliente;
import com.sostenesantunes.cursomc.domain.ItemPedido;
import com.sostenesantunes.cursomc.domain.PagamentoComBoleto;
import com.sostenesantunes.cursomc.domain.Pedido;
import com.sostenesantunes.cursomc.domain.enums.EstadoPagamento;
import com.sostenesantunes.cursomc.repositories.ItemPedidoRepository;
import com.sostenesantunes.cursomc.repositories.PagamentoRepository;
import com.sostenesantunes.cursomc.repositories.PedidoRepository;
import com.sostenesantunes.cursomc.security.UserSecurity;
import com.sostenesantunes.cursomc.services.exceptions.AuthorizationException;
import com.sostenesantunes.cursomc.services.exceptions.ObjectNotfoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository catRepos;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido findById(Integer id) {
		Optional<Pedido> obj = catRepos.findById(id);
		return obj.orElseThrow(() -> new ObjectNotfoundException(
					"Objeto não encontrado Id: " + id  +", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.findById(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = catRepos.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.findById(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSecurity user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest	 = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.findById(user.getId());
		return catRepos.findByCliente(cliente, pageRequest);

	}
}
