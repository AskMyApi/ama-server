package askmyapi.amaserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.data.redis")
@Getter
@Setter
public class RedisConfigProperties {
    private String host;
    private int port;
}
