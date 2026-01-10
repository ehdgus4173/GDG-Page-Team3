package com.example.backend.auth.filter;

import com.example.backend.auth.security.CustomUserDetails;
import com.example.backend.auth.service.FirebaseService;
import com.example.backend.member.entity.User;
import com.example.backend.member.repository.UserRepository;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final FirebaseService firebaseService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                FirebaseToken decodedToken = firebaseService.verifyIdToken(token);
                String email = decodedToken.getEmail();

                if (email != null) {
                    User user = userRepository.findByEmailIgnoreCase(email).orElse(null);

                    if (user != null) {
                        CustomUserDetails userDetails = new CustomUserDetails(
                                user.getId(),
                                user.getEmail(),
                                user.getGeneration(),
                                user.getRole());

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        log.warn("User not found in DB for email: {}", email);
                    }
                }
            } catch (Exception e) {
                log.error("Invalid Firebase Token: {}", e.getMessage());
                // Do not throw exception, just proceed without authentication
                // Security config will handle 401/403 if needed
            }
        }

        filterChain.doFilter(request, response);
    }
}
