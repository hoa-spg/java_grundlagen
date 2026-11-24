package at.spengergasse.company;

import com.vaadin.flow.component.page.AppShellConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Startpunkt der Webanwendung: startet Spring Boot (mit eingebautem
 * Webserver) – danach ist die Anwendung unter http://localhost:8080 erreichbar.
 */
@SpringBootApplication
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
