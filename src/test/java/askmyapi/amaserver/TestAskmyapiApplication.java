package askmyapi.amaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@Testcontainers
public class TestAskmyapiApplication {

    public static void main(String[] args) {
        SpringApplication.from(AskmyapiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
