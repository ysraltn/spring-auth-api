package tech.ysraltn.backend_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.ysraltn.backend_api.service.JwtService;
import tech.ysraltn.backend_api.service.UserDetailsServiceImpl;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        final String userEmail;

        // Authorization header kontrolü: "Bearer token"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        	System.out.println("Auth header: " + authHeader);
        	System.out.println("JWT filtresi atlandı: " + request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }


        jwt = authHeader.substring(7); // "Bearer " sonrasını al
        userEmail = jwtService.extractUsername(jwt); // token içinden email al

        // Kullanıcı zaten authenticated değilse → doğrula
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {//SecurityContextHolderda Auth. nesnesi var mı?
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities() // tokena eklenecek kullanıcı rolu?
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken); // yukarıda Auth. objesinin yokluğunu kontrol ettik, şimdi nesneyi atıyoruz
            } // artık bu Authentication nesnesi diğer filtreler, fonksiyonlar ve anotasyonlarda kullanılabilir.
        }

        filterChain.doFilter(request, response);
    }
}
