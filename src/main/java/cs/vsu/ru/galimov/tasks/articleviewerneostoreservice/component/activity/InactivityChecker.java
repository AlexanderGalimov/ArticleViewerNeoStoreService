package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component.activity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InactivityChecker {

    private final ActivityMonitor activityMonitor;
    private final ApplicationContext applicationContext;

    @Value("${inactivity.checker.time}")
    private long inactivityTime;

    public InactivityChecker(ActivityMonitor activityMonitor, ApplicationContext applicationContext) {
        this.activityMonitor = activityMonitor;
        this.applicationContext = applicationContext;
    }

    @Scheduled(fixedDelay = 60000)
    public void checkInactivity() {
        if (activityMonitor.isInactive(inactivityTime)) {
            System.out.println("Shutting down...");
            SpringApplication.exit(applicationContext, () -> 0);
        }
    }
}
