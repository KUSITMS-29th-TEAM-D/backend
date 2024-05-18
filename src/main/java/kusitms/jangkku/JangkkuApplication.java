package kusitms.jangkku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JangkkuApplication {

    public static void main(String[] args) {
        SpringApplication.run(JangkkuApplication.class, args);
    }

}
