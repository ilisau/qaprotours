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

/**
 * @author Lisov Ilya
 */
@Getter
@Setter
public class CustomMethodSecurityExpressionRoot
        extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private Object target;
    private HttpServletRequest request;

    private TicketService ticketService;
    private TourService tourService;

    /**
     * Constructor for internal use by the framework.
     * @param authentication the {@link Authentication} to use. Cannot be null.
     */
    public CustomMethodSecurityExpressionRoot(
            final Authentication authentication
    ) {
        super(authentication);
    }

    /**
     * Checks if the current user is the owner of the user with the given id.
     * @param userId the id of the user to check
     * @return true if the current user is the owner of the user with the given
     */
    public boolean canAccessUser(final String userId) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        JwtUserDetails userDetails = (JwtUserDetails) authentication
                .getPrincipal();
        String id = userDetails.getId();
        return userId.equals(id);
    }

    /**
     * Checks if the current user is the owner of the ticket with the given id.
     * @param ticketId the id of the ticket to check
     * @return true if the current user is the owner of the ticket with the
     */
    public boolean canAccessTicket(final Long ticketId) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        JwtUserDetails userDetails = (JwtUserDetails) authentication
                .getPrincipal();
        String id = userDetails.getId();
        return ticketService.getById(ticketId)
                .map(ticket -> ticket.getUserId().equals(id))
                .block();
    }

    /**
     * Checks if the current user is employee.
     * @return true if the current user is employee
     */
    public boolean canConfirmTicket() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        return hasAnyRole(authentication, User.Role.EMPLOYEE);
    }

    /**
     * Checks if the current user is the owner of the tour with the given id.
     * @param tourId the id of the tour to check
     * @return true if the current user is the owner of the tour with the given
     */
    public boolean canAccessDraftTour(final Long tourId) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        return tourService.getById(tourId)
                .map(tour -> !tour.isDraft()
                        || hasAnyRole(authentication, User.Role.EMPLOYEE))
                .block();
    }

    private boolean hasAnyRole(final Authentication authentication,
                               final User.Role... roles) {
        for (User.Role role : roles) {
            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority(role.getAuthority());
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
