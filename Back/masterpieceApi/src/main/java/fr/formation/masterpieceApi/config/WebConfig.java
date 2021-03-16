package fr.formation.masterpieceApi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*
     * Defines the "/api" prefix for all @RestController in the application.
     *
     * Configuring this way prevents conflicts and ease configuration with
     * oauth authentication endpoints (i.e. "/oauth/token").
     * Specified in application properties would change the endpoint to
     * "/api/oauth/token" and impact security endpoints configuration.
     *
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
	configurer.addPathPrefix("/api",
		HandlerTypePredicate.forAnnotation(RestController.class));
    }
}
