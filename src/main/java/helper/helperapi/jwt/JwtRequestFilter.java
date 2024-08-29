package helper.helperapi.jwt;


import helper.helperapi.entity.LoginViewFromEXUser;
import helper.helperapi.exception.InvalidJwttokenException;
import helper.helperapi.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		// Get authorization header and validate
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (null == header || "" == header || !header.startsWith("Bearer ")) {
        	throw new InvalidJwttokenException("JWT Token Required");
//        	filterChain.doFilter(request, response);
//            return;
        }
		
		final String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;

		if (null != authorizationHeader && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}
		
//		if (!loginLogoutMongoService.isTokenExistsAndValidInMongo(jwt)) {
//			throw new InvalidJwttokenException("Session expired, please login again");
//		}
		
		if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
//			UserDetails userDetails = this.MyUserDetailsService.loadUserByUsername(username);
			LoginViewFromEXUser userDetails = jwtUserDetailsService
					.loadUserByUsername(username);

			if (jwtUtil.validateToken(jwt, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				throw new InvalidJwttokenException("Session expired, please login again");
			}
		}
		

		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		// TODO Auto-generated method stub
//		return super.shouldNotFilter(request);
		return skipFilterUrls.stream().anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
	}
	
	@Autowired
	private UserDetailService jwtUserDetailsService;
	
	private static List<String> skipFilterUrls = Arrays.asList(
			"/helper-api/userlogin"
			);
			

	@Autowired
	JwtTokenUtil jwtUtil;
//	@Autowired
//	LoginLogoutMongoService loginLogoutMongoService;
}
