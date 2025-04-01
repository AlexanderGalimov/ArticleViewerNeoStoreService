package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "kafka.consumer")
@Component
public class KafkaConsumerProperties {

    private String autoCommit;

    private String sessionTimeout;

    private String  autoCommitInterval;

    private String offsetReset;

    private int concurrency;

    private String groupId;
}
