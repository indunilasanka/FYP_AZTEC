package aztec.rbir_rest2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;

//import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.util.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class SpringSecConfig extends WebSecurityConfigurerAdapter {
    static final String SIGNING_KEY = "MaYzkSjmkzPC57L";
    static final Integer ENCODING_STRENGTH = 256;
    static final String SECURITY_REALM = "Sprinb Boot JWT Example Realm";

    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService)
                //.passwordEncoder(new ShaPasswordEncoder(ENCODING_STRENGTH));
        
        auth.userDetailsService(userDetailsService);//make sure to encode password before checking
    }

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/**").permitAll();
        
    	http.headers().cacheControl();

    }
   
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers(HttpMethod.GET,"/webjars/**","/swagger-ui.html","/swagger-resources/**", "/v2/api-docs/**")
        .antMatchers("/documents/**")
        .antMatchers("/request/**");
    }
    
    @Bean
    public CorsFilter corsFilter() throws Exception {
        return new CorsFilter();
    }

    
    /*
    @Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
*/
    
    /*
   //this implementation should be changed in the future
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET,"/webjars/**","/swagger-ui.html","/swagger-resources/**", "/v2/api-docs/**")
        .antMatchers("/documents/**")
        .antMatchers(HttpMethod.OPTIONS, "/**");
        super.configure(web);
    }
    */
    
    

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("kelum");
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
    
    
    
}



