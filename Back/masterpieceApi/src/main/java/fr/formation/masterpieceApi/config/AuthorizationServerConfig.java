package fr.formation.masterpieceApi.config;

import fr.formation.masterpieceApi.services.EmployeeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    // Get custom properties from application.properties
    // Could be different between environments
    @Value("${jwt-auth-server.keyStore}")
    private String keyStore;

    @Value("${jwt-auth-server.keyPass}")
    private String keyPass;

    @Value("${jwt-auth-server.keyAlias}")
    private String keyAlias;

    @Value("${jwt-auth-server.accessTokenValiditySeconds}")
    private int accessTokenValiditySeconds;

    // Defined as Spring bean in WebSecurity
    private final AuthenticationManager authenticationManager;

    // EmployeeService as custom user details service to authenticate users
    // with username and password from the database
    private final EmployeeService employeeService;

    // Custom token converter to store custom info within access token
    private final CustomAccessTokenConverter customAccessTokenConverter;

    protected AuthorizationServerConfig(
            PasswordEncoder passwordEncoder, AuthenticationManager authenticationManagerBean,
            EmployeeService employeeService,
            CustomAccessTokenConverter customAccessTokenConverter) {
        authenticationManager = authenticationManagerBean;
        this.passwordEncoder = passwordEncoder;
	    this.employeeService = employeeService;
	    this.customAccessTokenConverter = customAccessTokenConverter;
    }

    /*
     * Token service using random UUID values for the access token and refresh
     * token values. Specifies the token store and disables the refresh token.
     */
    @Bean
    protected DefaultTokenServices tokenServices() {
	DefaultTokenServices services = new DefaultTokenServices();
	services.setTokenStore(tokenStore());
	services.setSupportRefreshToken(false);
	return services;
    }

    /*
     * JwtTokenStore can read and write JWT thanks to the token converter.
     */
    @Bean
    protected TokenStore tokenStore() {
	return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
	return new CustomTokenEnhancer();
    }

    /*
     * Configure the properties and enhanced functionality
     * of the Authorization Server endpoints.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer configurer)
	    throws Exception {
	TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
	tokenEnhancerChain.setTokenEnhancers(
		Arrays.asList(tokenEnhancer(), accessTokenConverter()));
	configurer.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain)
		.authenticationManager(authenticationManager)
		.userDetailsService(employeeService);
    }

    /*
     * A token converter for JWT and specifies a signing key (private/public key pair).
     */
    @Bean
    protected JwtAccessTokenConverter accessTokenConverter() {
	JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	Resource resource = new ClassPathResource(keyStore);
	char[] password = keyPass.toCharArray();
	KeyStoreKeyFactory factory = new KeyStoreKeyFactory(resource, password);
	converter.setKeyPair(factory.getKeyPair(keyAlias));
	converter.setAccessTokenConverter(customAccessTokenConverter);
	return converter;
    }

    /*
     * Change authorization server security allowing form auth for clients (vs
     * HTTP Basic). The client_id is sent as form parameter instead.
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer configurer)
	    throws Exception {
	configurer.allowFormAuthenticationForClients();
    }

    /*
     * In memory client with empty secret, application is a "private" API
     * with a single client, but Spring forces a client authentication.
     *
     * Authorized grant types are short-listed to only password.
     *
     * The scope is trusted (convention) and no need to specify it during client
     * authentication. We do not use scope-based authorization in this
     * application.
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
	    throws Exception {
	clients.inMemory().withClient("masterpiece-spa")
		.secret(passwordEncoder.encode("")).scopes("trusted")
		.authorizedGrantTypes("password")
		.accessTokenValiditySeconds(accessTokenValiditySeconds);
    }

}
