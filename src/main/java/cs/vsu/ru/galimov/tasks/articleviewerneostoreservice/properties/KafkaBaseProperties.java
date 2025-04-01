package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "spring.kafka")
@Component
public class KafkaBaseProperties {

    private String bootstrapServers;
}
