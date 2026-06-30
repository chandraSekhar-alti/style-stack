package com.example.stylestackapp.security.jwt;

import com.example.stylestackapp.auth.entity.UserSession;
import com.example.stylestackapp.auth.repository.UserSessionRepo;
import com.example.stylestackapp.auth.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsServiceImpl userDetailsService;
  private final UserSessionRepo userSessionRepo;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String jwt = authHeader.substring(7);

    String userName = jwtService.extractUserName(jwt);

    if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

      String jwtId = jwtService.extractJwtId(jwt);
      Optional<UserSession> userSession = userSessionRepo.findByAccessTokenJti(jwtId);

      if (userSession.isEmpty() || !userSession.get().isActive()) {
        filterChain.doFilter(request, response);
        return;
      }

      if (jwtService.isTokenValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
      }
    }
  }
}
