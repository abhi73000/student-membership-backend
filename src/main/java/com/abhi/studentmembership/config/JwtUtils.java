package com.abhi.studentmembership.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
public static final String SECRET = "AADGIWGUDWGDYYDUGWYDWYYGYFDSAHSJVDAH171251782515473854367543675472547223816387SDFS527541731457313";
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims,userDetails);
		
	}
	
	private String createToken(Map<String,Object> claims,UserDetails userDetails) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 30))
				.signWith(getSignKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Key getSignKey() {
		byte[] keybytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keybytes);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
	
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	
	public boolean validateToken(String token,UserDetails userDetails) {
		final String username = extractUsername(token);
		return(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
//	
//	public String generateToken(UserDetails userDetails) {
//		return Jwts.builder().setSubject(userDetails.getUsername())
//			   .setIssuedAt(new Date(System.currentTimeMillis()))
//			   .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
//			   .signWith(getSigninKey(),SignatureAlgorithm.HS256)
//				.compact();
//	}
//	
//	public String generateRefreshToken(Map<String, Object> extractCliams,UserDetails userDetails) {
//		return Jwts.builder().setClaims(extractCliams).setSubject(userDetails.getUsername())
//			   .setIssuedAt(new Date(System.currentTimeMillis()))
//			   .setExpiration(new Date(System.currentTimeMillis() + 604800000))
//			   .signWith(getSigninKey(),SignatureAlgorithm.HS256)
//				.compact();
//	}
//
//	public String extractUsername(String token) {
//		return extractClaim(token,Claims::getSubject);
//	}
//	private Key getSigninKey() {
//		byte[] key = Decoders.BASE64.decode("136891639816319319361adfdafadrqrssrtst165272652152752casgfadgdar");
//		return Keys.hmacShaKeyFor(key);
//	}
//
//	
//	private <T> T extractClaim(String token,Function<Claims,T> claimsResolvers) {
//		final Claims claims = extractAllCliams(token);
//		return claimsResolvers.apply(claims);
//	}
//
//	private Claims extractAllCliams(String token) {
//		// TODO Auto-generated method stub
//		return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
//	}
//	
//
//	public boolean isTokeValid(String token,UserDetails userDetails) {
//		final String username = extractUsername(token);
//		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//	}
//
//	
//	private boolean isTokenExpired(String token) {
//		return extractClaim(token, Claims::getExpiration).before(new Date());
//	}

}
