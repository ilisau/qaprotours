package com.solvd.qaprotours.web.security.expressions;

import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.service.TourService;
import com.solvd.qaprotours.web.security.jwt.JwtUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import reactor.core.publisher.Mono;

/**
 * @author Lisov Ilya
 */
@Getter
@Setter
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private Object target;
    private HttpServletRequest request;

    private TicketService ticketService;
    private TourService tourService;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean canAccessUser(String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        String id = userDetails.getId();
        return userId.equals(id);
    }

    public boolean canAccessTicket(Long ticketId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        String id = userDetails.getId();
        return ticketService.getById(ticketId)
                .map(ticket -> ticket.getUserId().equals(id))
                .block();
    }

    public boolean canConfirmTicket() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return hasAnyRole(authentication, User.Role.EMPLOYEE);
    }

    public boolean canAccessDraftTour(Long tourId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return tourService.getById(tourId)
                .map(tour -> !tour.isDraft() || hasAnyRole(authentication, User.Role.EMPLOYEE))
                .block();
    }

    private boolean hasAnyRole(Authentication authentication, User.Role... roles) {
        for (User.Role role : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getAuthority());
            if (authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object getThis() {
        return target;
    }

}
