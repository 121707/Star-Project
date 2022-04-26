package management_worker.config;

import management_worker.service.Imp.userDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    DataSource dataSource;
    @Autowired
    userDetailService userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**").permitAll() //直接允许访问
                .antMatchers("/js/**").permitAll() //直接允许访问
                .antMatchers("/adminlogin").permitAll() //直接允许访问
                .anyRequest().authenticated();
        http.headers()
                .frameOptions().sameOrigin()
                .httpStrictTransportSecurity().disable();

        http.formLogin()
                .loginPage("/adminlogin")
                .usernameParameter("name")
                .passwordParameter("password")
                .defaultSuccessUrl("/main")
                .loginProcessingUrl("/adminlogin")//login?erro
                .failureForwardUrl("/admin/loginfail");

        http.csrf().disable().httpBasic(); //关闭csrf保护
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //session配置成无状态模式，适用于REST API
//                .and().authorizeRequests().anyRequest().authenticated(); // 所有请求都要经过登录认知
        http.logout()
                .logoutUrl("/adminlogout")
                .logoutSuccessUrl("/");
        http.rememberMe()
                .rememberMeParameter("rememberme")
                .tokenValiditySeconds(200)
                .tokenRepository(tokenRepository());


    }
    //token持久化对象
    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        tokenRepository.setCreateTableOnStartup(false);//创建表，第一次设为true，建完设为false
        return tokenRepository;
    }
}
