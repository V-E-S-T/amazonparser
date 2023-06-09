package com.amazonparser.config;

import com.amazonparser.domain.User;
import com.amazonparser.repo.UserDetailsRepo;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/js/**", "/error**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserDetailsRepo userDetailsRepo) {
        return new PrincipalExtractor() {
            @Override
            public Object extractPrincipal(Map<String, Object> map) {
                String id = (String) map.get("sub");

                User user = userDetailsRepo.findById(id).orElseGet(new Supplier<User>() {
                    @Override
                    public User get() {
                        User newUser = new User();

                        newUser.setId(id);
                        newUser.setName((String) map.get("name"));
                        newUser.setEmail((String) map.get("email"));
                        newUser.setGender((String) map.get("gender"));
                        newUser.setLocale((String) map.get("locale"));
                        newUser.setUserpic((String) map.get("picture"));

                        return newUser;
                    }
                });

                user.setLastVisit(LocalDateTime.now());

                return userDetailsRepo.save(user);
            }
        };
    }

}
