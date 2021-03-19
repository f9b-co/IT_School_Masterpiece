package fr.formation.masterpieceApi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /*
     * Configures the HTTP security for this application.
     *
     * Defines this application as stateless (no HTTP session),
     * disables HTTP basic auth and CSRF (no need with JWT if not cookie-based).
     *
     * Defines access level for HttpMethod and endpoints URL.
     * .cors().disable()
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
	http.httpBasic().disable().csrf().disable()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
		.antMatchers(HttpMethod.POST, "/api/employees").permitAll()
		.antMatchers(HttpMethod.GET, "/api/departments", "/api/tasks").permitAll()
		.antMatchers(HttpMethod.GET, "/api/employees", "/api/activities").authenticated()
		.antMatchers("/api/employees", "/api/activities").authenticated();
    }
}
