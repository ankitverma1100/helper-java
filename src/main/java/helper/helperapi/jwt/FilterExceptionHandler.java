package helper.helperapi.jwt;


import helper.helperapi.exception.InvalidJwttokenException;
import helper.helperapi.sqlModels.ObjectCommonResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FilterExceptionHandler extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		ObjectCommonResponseModel responesModel = new ObjectCommonResponseModel();
		responesModel.setStatus(false);
//		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
            filterChain.doFilter(request, response);
        } 
		catch(InvalidJwttokenException ije) {
        	responesModel.setMessage(ije.getMessages());
        	resolver.resolveException(request, response, null, ije);
        } catch (io.jsonwebtoken.SignatureException singnature) {
        	resolver.resolveException(request, response, null, new InvalidJwttokenException("Invalid Signature"));
        } catch (NullPointerException e) {
        	resolver.resolveException(request, response, null, e);
		} catch (Exception e) {
			e.printStackTrace();
            resolver.resolveException(request, response, null, new InvalidJwttokenException("Some Token Failure"));
        }
		
	}

	@Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
}
