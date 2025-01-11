package org.Akhil.shortener.config;

import org.Akhil.shortener.model.UserRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleValidate {
    @Autowired
    private UserRequestContext context;
    public boolean validateRole(){
        return context.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_USER"));
    }
}
