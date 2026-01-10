package com.example.backend.global.user;

import com.example.backend.auth.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserContextImpl implements UserContext {

    @Override
    public Long currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getId();
        }
        return null; // Or throw exception if user is required
    }

    @Override
    public int currentGeneration() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            Integer generation = ((CustomUserDetails) authentication.getPrincipal()).getGeneration();
            return generation != null ? generation : 0;
        }
        return 0; // Default or throw
    }
}
