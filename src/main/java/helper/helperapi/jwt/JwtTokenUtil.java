package helper.helperapi.jwt;

import helper.helperapi.entity.LoginViewFromEXUser;
import helper.helperapi.exception.InvalidJwttokenException;
import helper.helperapi.models.JwtToeknResponseModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -5269623043455540460L;

	public static final String JWT_TOKEN_SECRET_KEY = "ou2dkZA9xum71%Bbzsga*fr^tl3t5g&Ro%NMNVm*i7*lyW^%@";
	public static final int JWT_TOKEN_LIFE_TIME_IN_SECONDS = 60 * 60 * 24;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(JWT_TOKEN_SECRET_KEY.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
		} catch (io.jsonwebtoken.ExpiredJwtException tokenexpired) {
			tokenexpired.printStackTrace();
			throw new InvalidJwttokenException("JWT Token Expired");
		}
		catch (io.jsonwebtoken.MalformedJwtException malformedToken) {
			throw new InvalidJwttokenException("Invalid JWT Token");
		}
		return claims;
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(String userid) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return createToken(claims, userid);
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (1000 * JWT_TOKEN_LIFE_TIME_IN_SECONDS)))

				.signWith(SignatureAlgorithm.HS256, JWT_TOKEN_SECRET_KEY.getBytes(StandardCharsets.UTF_8)).compact();
	}

	public Boolean validateToken(String token, LoginViewFromEXUser userDetails) {
		return (extractUsername(token).toUpperCase().equals(userDetails.getUserid().toUpperCase())) && !isTokenExpired(token);
	}

	public String getUserIdString(String token) {
		return extractUsername(token);
	}
}