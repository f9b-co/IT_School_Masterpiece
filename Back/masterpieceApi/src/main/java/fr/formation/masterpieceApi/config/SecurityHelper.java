package fr.formation.masterpieceApi.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Map;

/*
 * Helper class to access the Spring security context.
 *
 * Provides convenient methods to deal with the currently authenticated user.
 */
public final class SecurityHelper {

    private SecurityHelper() {
	// Ensure non-instantiability, helper class with static methods only
    }

    /*
     * Returns the currently authenticated user identifier.
     *
     */
    @SuppressWarnings("unchecked")
    public static Long getUserId() {
	Authentication auth = getAuthentication();
	OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth
		.getDetails();
	Map<String, Object> decodedDetails = (Map<String, Object>) details
		.getDecodedDetails();
	Integer value = (Integer) decodedDetails
		.get(CustomTokenEnhancer.USER_ID_KEY);
	return value.longValue();
    }

    /*
     * Returns the currently authenticated user username.
     *
     * Alias for getPrincipal()
     *
     * @return the authenticated user username
     */
    public static String getUsername() {
	return getPrincipal();
    }

    /*
     * Returns the currently authenticated principal.
     *
     * The principal is the string representation of the "user details" object,
     * in other words its username.
     *
     * return the principal
     * see also alias getUsername()
     */
    public static String getPrincipal() {
	return (String) getAuthentication().getPrincipal();
    }

    /*
     * Returns the Authentication object associated to the currently
     * authenticated principal, or an authentication request token.
     *
     * return the Authentication or null if no authentication information is available
     */
    public static Authentication getAuthentication() {
	return SecurityContextHolder.getContext().getAuthentication();
    }
}
