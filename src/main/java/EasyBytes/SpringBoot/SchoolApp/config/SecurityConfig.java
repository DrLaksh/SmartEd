package EasyBytes.SpringBoot.SchoolApp.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

     @Bean
     SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

          http.csrf((csrf) -> csrf.ignoringRequestMatchers("/saveMsg")
                          .ignoringRequestMatchers("/api/**")
                          .ignoringRequestMatchers("/data-api/**")
                          .ignoringRequestMatchers("/eazyschool/actuator/**"))
                  .authorizeHttpRequests((requests) -> requests.requestMatchers("/dashboard").authenticated()
                          .requestMatchers("/", "/home").permitAll()
                          .requestMatchers("/displayMessages/**").hasRole("ADMIN")
                          .requestMatchers("/displayProfile").authenticated()
                          .requestMatchers("/updateProfile").authenticated()
                          .requestMatchers("/holidays/**").permitAll()
                          .requestMatchers("/api/**").authenticated()
                          .requestMatchers("/eazyschool/actuator/**").hasRole("ADMIN")
                          .requestMatchers("/student/**").hasRole("STUDENT")
                          .requestMatchers("/admin/**").hasRole("ADMIN")
                          .requestMatchers("/closeMsg/**").hasRole("ADMIN")
                          .requestMatchers("/contact").permitAll()
                          .requestMatchers("/saveMsg").permitAll()
                          .requestMatchers("/courses").permitAll()
                          .requestMatchers("/about").permitAll()
                          .requestMatchers("/data-api/**").authenticated()
                          .requestMatchers("/assets/**").permitAll()
                          .requestMatchers("/login").permitAll()
                          .requestMatchers("/public/**").permitAll()
                          .requestMatchers("/logout").permitAll())
                  .formLogin(loginConfigurer -> loginConfigurer.loginPage("/login")
                          .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll())
                  .logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl("/login?logout=true")
                          .invalidateHttpSession(true).permitAll())
                  .httpBasic(Customizer.withDefaults());
          return http.build();
     }

     //adding BCrypt password Encoder here on project
     @Bean
     public PasswordEncoder passwordEncoder(){
          return new BCryptPasswordEncoder();
     }

}
