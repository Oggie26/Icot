package Project.example.Project_1.config;

import Project.example.Project_1.enity.User;
import Project.example.Project_1.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;


import java.io.IOException;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    // list danh sach api valid
    private final List<String> AUTH_PERMISSION = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/api/auth/login",
            "/api/auth/register",
            "/api/user/{id}"
    );

    private boolean isPermitted(String uri){
        AntPathMatcher  antPathMatcher = new AntPathMatcher();
        return AUTH_PERMISSION.stream().anyMatch(pattern -> antPathMatcher.match(pattern, uri));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        System.out.println(uri);
        if(isPermitted(uri)){
            filterChain.doFilter(request, response);
        }else {
            if (AUTH_PERMISSION.contains(uri)) {
                // yêu cầu truy cập 1 api => ai cũng truy cập đc
                filterChain.doFilter(request, response); // cho phép truy cập dô controller
            } else {
                String token = getToken(request);
                if (token == null) {
                    resolver.resolveException(request, response, null, new AuthException("Empty token!"));
                    return;
                }

                User user;
                try {
                    // từ token tìm ra thằng đó là ai
                    user = tokenService.extractAccount(token);
                } catch (ExpiredJwtException expiredJwtException) {
                    // token het han
                    resolver.resolveException(request, response, null, new AuthException("Expired Token!"));
                    return;
                } catch (MalformedJwtException malformedJwtException) {
                    resolver.resolveException(request, response, null, new AuthException("Invalid Token!"));
                    return;
                }
                // token dung
                UsernamePasswordAuthenticationToken
                        authenToken =
                        new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
                authenToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenToken);
                // token ok, cho vao`
                filterChain.doFilter(request, response);
            }
        }


    }
    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.substring(7);
    }}
