package security.demo.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import security.demo.student.Student;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	@Autowired
//	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
//		this.passwordEncoder = passwordEncoder;
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http	/* for basic auth
				.authorizeRequests()
				.anyRequest()
				.authenticated()
				.and()
				.httpBasic();
				*/
		
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
				.antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
				//.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
				//.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
				//.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
				//.antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())
				.anyRequest()
				.authenticated()
				.and()
				//.httpBasic();
				.formLogin().loginPage("/login").permitAll()
				.defaultSuccessUrl("/cources", true)// true is optional i think
				.passwordParameter("password") // we change it when required
				.usernameParameter("username") //we change it wehen required
				.and()
				.rememberMe() //default to 2 weeks 
					.tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
					.key("somethingverysecured")
					.rememberMeParameter("remember-me")//we change it wehen required
				.and()
				.logout()
					.logoutUrl("/logout")
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // this is used when csrf is disable otherwise remove it
					.clearAuthentication(true)
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID", "remember-me")
					.logoutSuccessUrl("/login"); 
					
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		
		UserDetails shubham = User.builder()
				.username("shubham")
				.password(passwordEncoder.encode("123"))
				//.roles(ApplicationUserRole.STUDENT.name())	// Student role
				.authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
				.build();
		
		UserDetails linda = User.builder()
				.username("linda")
				.password(passwordEncoder.encode("lily"))
				//.roles(ApplicationUserRole.ADMIN.name())	//Admin role
				.authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
				.build();
		
		UserDetails tom = User.builder()
				.username("tom")
				.password(passwordEncoder.encode("tom123"))
				//.roles(ApplicationUserRole.ADMINTRAINEE.name())	//AdminTrainee role
				.authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
				.build();
		
		return new InMemoryUserDetailsManager(
				shubham, linda ,tom
		);
	}
	
	

}
