package com.tech.solutio.produtos.service;
import java.nio.charset.Charset;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tech.solutio.produtos.model.Usuario;
import com.tech.solutio.produtos.model.UsuarioLogin;
import com.tech.solutio.produtos.repository.UsuarioRepository;
import org.apache.tomcat.util.codec.binary.Base64;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	private static String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	
	public Optional<Object> cadastrarUsuario(Usuario usuario){
		return repository.findByUsuario(usuario.getUsuario()).map(usuarioExistente -> {
			return Optional.empty();
		}).orElseGet(() -> {
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			return Optional.ofNullable(repository.save(usuario));
		});
	}
	
	public Optional<UsuarioLogin> logar (Optional<UsuarioLogin> user){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());
		
		if(usuario.isPresent()) {
			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
				
				String auth = user.get().getUsuario() + ":" + user.get().getSenha();
				byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodeAuth);
				
				user.get().setIdUsuario(usuario.get().getId());
				user.get().setToken(authHeader);
				user.get().setNome(usuario.get().getNome());
				user.get().setSenha(usuario.get().getSenha());
				
				return user;
			}
		}
		return null;
	}
}
