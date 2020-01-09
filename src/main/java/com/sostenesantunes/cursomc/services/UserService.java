package com.sostenesantunes.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.sostenesantunes.cursomc.security.UserSecurity;

public class UserService {
	// Method que retorna Usuário logado
	public static UserSecurity authenticated() {
		try {
			return (UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		} catch (Exception e) {
			return null;
		}
			
	} 

}
