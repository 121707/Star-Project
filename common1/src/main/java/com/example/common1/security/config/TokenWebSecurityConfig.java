package com.example.common1.security.config;

import com.example.common1.security.filter.TokenAuthFilter;
import com.example.common1.security.filter.TokenLoginFilter;
import com.example.common1.security.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private RedisTemplate redisTemplate;

    private TokenManager tokenManager;
    private DefaultPasswordEncoder defaultPasswordEncoder;
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenSucessHandler tokenSucessHandler;

//    @Autowired
//    CustomAuthenticationProvider customAuthenticationProvider;

//    @Autowired
//    public TokenWebSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
//                                  TokenManager tokenManager, RedisTemplate redisTemplate) {
//        this.userDetailsService = userDetailsService;
//        this.defaultPasswordEncoder = defaultPasswordEncoder;
//        this.tokenManager = tokenManager;
//        this.redisTemplate = redisTemplate;
//    }

    @Autowired
    public TokenWebSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder , TokenManager tokenManager) {
        this.userDetailsService = userDetailsService;
//        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
    }

    /**
     * ????????????
     * @param http
     * @throws Exception
     */
    //????????????????????????token???redis????????????
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .and().csrf().disable()
//                .authenticationEntryPoint(new UnauthEntryPoint())//??????????????????
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("star.pro.com/login").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/logout")//????????????
                .addLogoutHandler(new TokenLogoutHandler(tokenManager));
        http.formLogin().defaultSuccessUrl("/shop/main");


//        .and()
//        .authenticationProvider(customAuthenticationProvider);

        http.addFilterAt(new TokenLoginFilter(authenticationManager(), tokenManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenAuthFilter(authenticationManager(), tokenManager), UsernamePasswordAuthenticationFilter.class).httpBasic();
 //                .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate)).and()
//                .addFilter(new TokenAuthFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();
//                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))

    }

    //??????userDetailsService???????????????
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());

    }
    //?????????????????????????????????????????????
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/api/**");
//    }
}
