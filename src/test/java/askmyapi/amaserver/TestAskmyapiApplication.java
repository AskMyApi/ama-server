package askmyapi.amaserver;

import org.springframework.boot.SpringApplication;

public class TestAskmyapiApplication {

    public static void main(String[] args) {
        SpringApplication.from(AskmyapiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
