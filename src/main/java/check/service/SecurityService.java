package check.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public final static String ANONYMOUS = "ANONYMOUS";

    public String getCurrentUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String res = ANONYMOUS;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            res = authentication.getName();
        }

        return res;
    }
}
