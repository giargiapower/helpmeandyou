package com.GestoreCredenziali.GestoreCredenziali.Controller;

import com.GestoreCredenziali.GestoreCredenziali.Model.Account;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/utenti")
public class LoginController {
	private static final String authorizationRequestBaseUri = "oauth2/authorization";
	Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	// Metodo che restituisce la pagina di login
	@GetMapping("/oauth_login")
	public String getLoginPage(Model model) {
		Iterable<ClientRegistration> clientRegistrations = null;
		ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
		if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
			clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
		}

		Objects.requireNonNull(clientRegistrations).forEach(registration ->
				oauth2AuthenticationUrls.put(registration.getClientName(),
						authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
		model.addAttribute("urls", oauth2AuthenticationUrls);

		return "oauth_login";
	}

	// Metodo che restituisce le informazioni dell'utente loggato
	@GetMapping("/loginSuccess")
	public ResponseEntity<Account> getLoginInfo(OAuth2AuthenticationToken authentication) {
		OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();

		if (!StringUtils.isEmpty(userInfoEndpointUri)) {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
			HttpEntity entity = new HttpEntity("", headers);
			ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
			Map<String, Object> userAttributes = response.getBody();
			Account account = new Account((String) Objects.requireNonNull(userAttributes).get("email"), (String) userAttributes.get("given_name"), (String) userAttributes.get("family_name"));
			return ResponseEntity.ok(account);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Metodo di test che ritorna tutte le informazioni estratte dell'utente loggato
	@GetMapping("/userinfo")
	public ResponseEntity<Map<String, Object>> getUserInfo(OAuth2AuthenticationToken authentication) {
		OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
		Map<String, Object> userAttributes = response.getBody();
		return ResponseEntity.ok(userAttributes);
	}

	@GetMapping("/loginFailure")
	public ResponseEntity<String> loginFailure() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fallito");
	}

	// Metodo di test che effettua il logout
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/";
	}

}
