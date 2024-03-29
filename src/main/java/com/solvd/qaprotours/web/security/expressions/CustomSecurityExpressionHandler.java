package com.solvd.qaprotours.web.security.expressions;

import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.service.TourService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

/**
 * @author Lisov Ilya
 */
public class CustomSecurityExpressionHandler
        extends DefaultMethodSecurityExpressionHandler {

    private ApplicationContext applicationContext;
    private final AuthenticationTrustResolver trustResolver =
            new AuthenticationTrustResolverImpl();

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            final Authentication authentication,
            final MethodInvocation invocation
    ) {
        CustomMethodSecurityExpressionRoot root =
                new CustomMethodSecurityExpressionRoot(authentication);
        root.setTrustResolver(this.trustResolver);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setRoleHierarchy(getRoleHierarchy());
        root.setTicketService(this.applicationContext
                .getBean(TicketService.class));
        root.setTourService(this.applicationContext
                .getBean(TourService.class));
        return root;
    }

    @Override
    public void setApplicationContext(
            final ApplicationContext applicationContext
    ) {
        super.setApplicationContext(applicationContext);
        this.applicationContext = applicationContext;
    }

}
