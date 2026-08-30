package com.rideflowauthservice.filters;

import com.rideflowauthservice.service.CustomUserDetailsService;
import com.rideflowauthservice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final RequestMatcher requestMatcher = PathPatternRequestMatcher.withDefaults()
            .matcher("/api/v1/auth/login");
    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token=null;
        if(request.getCookies()!=null){
            for(Cookie cookie: request.getCookies()){
                if(cookie.getName().equals("Jwt_Token")){
                    token=cookie.getValue();
                    break;
                }
            }
        }
        if(token==null){
            // user couldn't provide any jwt-token so don't move forward
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String email=jwtService.extractUserEmail(token);
        if(email!=null){
            UserDetails userDetails= userDetailsService.loadUserByUsername(email);
            if(jwtService.isTokenValid(token,userDetails.getUsername())){
                //And here Authenticated place we are sending null bcz we are already authenticated
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null);
                //Main use case WebAuthenticationDetailsSource().buildDetails(request) is to convert HttpRequest to Spring Understandable format
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //After Authenticating SuccessFully we are storing in SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response); // ALWAYS continue the chain
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        RequestMatcher requestMatcher1=new NegatedRequestMatcher(requestMatcher);
        return requestMatcher1.matches(request);
    }
}