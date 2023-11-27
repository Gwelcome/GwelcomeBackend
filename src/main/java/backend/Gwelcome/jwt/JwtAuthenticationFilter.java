package backend.Gwelcome.jwt;

import backend.Gwelcome.dto.ResponseDTO;
import backend.Gwelcome.exception.ErrorCode;
import backend.Gwelcome.model.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseBearerToken(request);
            log.info("Filter is running...");

            if (token != null && !token.equalsIgnoreCase("null")) {
                Claims claims = jwtProvider.validateAndGetUserId(token);
                String memberId = claims.getSubject().substring(13,45);
                Date expiration = claims.getExpiration();
                log.info("Authenticated user ID : " + memberId);
                log.info("Authenticated expiration : " + expiration);

                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        memberId,
                        null,
                        AuthorityUtils.createAuthorityList(String.valueOf(Role.USER)));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            logger.error("Could not set user authentication in security context {}", e);
            jwtExceptionHandler(response, ErrorCode.JWT_TOKEN_EXPIRED.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Could not set user authentication in security context {}", e);
            jwtExceptionHandler(response, ErrorCode.JWT_UNSUPPORTED.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Could not set user authentication in security context {}", e);
            jwtExceptionHandler(response, ErrorCode.JWT_MALFORMED.getMessage());
        } catch (SignatureException e) {
            logger.error("Could not set user authentication in security context {}", e);
            jwtExceptionHandler(response, ErrorCode.JWT_SIGNATURE.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Could not set user authentication in security context {}", e);
            jwtExceptionHandler(response, ErrorCode.JWT_ILLEGAL_ARGUMENT.getMessage());
        }
    }
    private String parseBearerToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    private void jwtExceptionHandler(HttpServletResponse response, String message) throws IOException{
        ResponseDTO<String> dto = new ResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), message);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getWriter(), dto);
    }
}