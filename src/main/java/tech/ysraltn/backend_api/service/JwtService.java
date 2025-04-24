package tech.ysraltn.backend_api.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.ysraltn.backend_api.model.User;

import javax.crypto.SecretKey;
import java.util.Date;
import org.springframework.security.core.userdetails.UserDetails;


@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    public String generateToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes()); //secretın byteından keyi üretir

        return Jwts.builder()
                .setSubject(user.getEmail()) //subjectte email veya username tutulur
                .claim("role", user.getRole().name()) //custom claim role
                .setIssuedAt(new Date()) //oluşturma zamanı
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact(); //tokenı üret
    }

	public String extractUsername(String token) {
	    return Jwts.parserBuilder()
	            .setSigningKey(secret.getBytes())
	            .build()
	            .parseClaimsJws(token)
	            .getBody()
	            .getSubject(); //sunbject alanını çıkarttık (email)
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
	    final String username = extractUsername(token); //gerçekten böyle bir user var mı diye bakılıyor
	    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
	    Date expiration = Jwts.parserBuilder()
	            .setSigningKey(secret.getBytes())
	            .build()
	            .parseClaimsJws(token)
	            .getBody()
	            .getExpiration();
	    return expiration.before(new Date());
	}
}

