package com.abhi.studentmembership.controller;

import java.io.IOException;
import java.util.Optional;

import com.abhi.studentmembership.entities.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abhi.studentmembership.config.JwtUtils;
import com.abhi.studentmembership.dto.AuthRequest;
import com.abhi.studentmembership.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthenticationManager authManager;
	
	private final UserDetailsService userDetailsService;
	
	private final JwtUtils jwtUtils;
	
	private final UserRepository userRepository;
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String HEADER_STRING = "Authorization";
	
	
	@PostMapping("/authenticate")
	public void createAuthToken(@RequestBody AuthRequest req,HttpServletResponse res) throws IOException, JSONException {
		
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
			
		}catch(BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or Password");
		}catch(DisabledException ex) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND,"User is not created");
			return;
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getEmail());
		Optional<User> optUser = userRepository.findByEmail(userDetails.getUsername());
		final String jwt = jwtUtils.generateToken(userDetails);
		
		if(optUser.isPresent()) {
			res.getWriter().write(new JSONObject()
					              .put("userId", optUser.get().getId())
					              .put("role", optUser.get().getRole())
					              .toString());
		}
		
		res.setHeader("Access-Control-Expose-Headers", "Authorization");
		res.setHeader("Access-Control-Allow-Headers", "Authorization,X-Pingother,Origin,X-Requested-With,Content-Type,Accept,X-Custom-Header");
		res.setHeader(HEADER_STRING,TOKEN_PREFIX + jwt);
		
		//return new AuthResponse(jwt);
	}

}
