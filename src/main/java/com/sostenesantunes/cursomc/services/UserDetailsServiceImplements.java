/**
 * 
 */
package com.sostenesantunes.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sostenesantunes.cursomc.domain.Cliente;
import com.sostenesantunes.cursomc.repositories.ClienteRepository;
import com.sostenesantunes.cursomc.security.UserSecurity;

/**
 * @author Sóstenes Antunes
 *
 */

@Service
public class UserDetailsServiceImplements implements UserDetailsService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = repo.findByEmail(email);
		if (cli == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSecurity(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}

}
