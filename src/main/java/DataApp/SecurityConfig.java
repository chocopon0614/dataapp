package DataApp;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/open/**")
				.hasAnyAuthority("SCOPE_chocopon.cognito/body.read", "SCOPE_aws.cognito.signin.user.admin");
		http.oauth2ResourceServer().jwt();
	}
}