package com.tpv.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final PasswordEncoder passwordEncoder;

	@Bean
	@Order(1)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults()); // Enable OpenID
																										// Connect 1.0
		http
				// Redirect to the login page when not authenticated from the
				// authorization endpoint
				.exceptionHandling((exceptions) -> exceptions.defaultAuthenticationEntryPointFor(
						new LoginUrlAuthenticationEntryPoint("/login"),
						new MediaTypeRequestMatcher(MediaType.TEXT_HTML)))
				// Accept access tokens for User Info and/or Client Registration
				.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(Customizer.withDefaults()));

		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/auth/**", "/clients/**").permitAll()
				.anyRequest().authenticated())
				// /auth/** permitAll and others must be authenticated
				// authorization server filter chain
				.formLogin(Customizer.withDefaults());
		http.csrf(csrf -> csrf.ignoringRequestMatchers("/auth/**", "/clients/**"));
		return http.build();
	}

	// @Bean
	// public RegisteredClientRepository registeredClientRepository() {
	// final RegisteredClient oidcClient =
	// RegisteredClient.withId(UUID.randomUUID().toString())
	// .clientId("oidc-client").clientSecret(this.passwordEncoder.encode("secret"))
	// .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
	// .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
	// .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
	// // .redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
	// .redirectUri("https://oauthdebugger.com/debug")
	// // .postLogoutRedirectUri("http://127.0.0.1:8080/hola!")
	// .scope(OidcScopes.OPENID).scope(OidcScopes.PROFILE)
	// .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build()).build();
	//
	// return new InMemoryRegisteredClientRepository(oidcClient);
	// }

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
		return context -> {
			final Authentication principal = context.getPrincipal();
			if (context.getTokenType().getValue().equals("id_token")) {
				context.getClaims().claim("token_type", "id token");
			}
			if (context.getTokenType().getValue().equals("access_token")) {
				context.getClaims().claim("token_type", "access token");
				final Set<String> roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.collect(Collectors.toSet());
				context.getClaims().claim("roles", roles).claim("username", principal.getName());
			}
		};
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		final KeyPair keyPair = generateRsaKey();
		final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		final RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString())
				.build();
		final JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	private static KeyPair generateRsaKey() {
		final KeyPair keyPair;
		try {
			final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (final Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}

}
