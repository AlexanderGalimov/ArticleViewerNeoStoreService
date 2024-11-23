package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component.activity;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class ActivityTrackingFilter implements Filter {

    private final ActivityMonitor activityMonitor;

    public ActivityTrackingFilter(ActivityMonitor activityMonitor) {
        this.activityMonitor = activityMonitor;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            String path = httpRequest.getRequestURI();
            if (!"/actuator/prometheus".equals(path)) {
                activityMonitor.updateLastActivity();
            }
        }
        chain.doFilter(request, response);
    }
}
