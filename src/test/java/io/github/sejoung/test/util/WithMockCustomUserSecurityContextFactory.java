package io.github.sejoung.test.util;

import io.github.sejoung.dto.Detail;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory
    implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        List<GrantedAuthority> grantedAuthorities = new ArrayList();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        User principal = new User(customUser.username(), "1234", true, true, true, true,
            grantedAuthorities);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            principal, principal.getPassword(), principal.getAuthorities());

        authentication.setDetails(new Detail(customUser.username(), "aaaa"));
        context.setAuthentication(authentication);
        return context;
    }
}
