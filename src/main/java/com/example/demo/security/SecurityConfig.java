package com.example.demo.security;

/*
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

*/


import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  //  @Autowired
  //  private CustomUserDetailService customUserDetailService;
    @Autowired
    private UserService userService;
@Bean
 public UserDetailsService getUserDetailsService(){

   return new CustomUserDetailService();
 }

 @Bean
 public BCryptPasswordEncoder getPassword(){
  return new BCryptPasswordEncoder();
 }


 @Bean
 public DaoAuthenticationProvider daoProvider(){
  DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
  dao.setUserDetailsService(getUserDetailsService());
  dao.setPasswordEncoder(getPassword());
  return dao;
 }

  @Override
  protected void configure(AuthenticationManagerBuilder auth)
          throws Exception {
    auth.authenticationProvider(daoProvider());
  }


  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .authorizeRequests()
                .antMatchers("/user/**").hasRole("USER").antMatchers("/**").permitAll()

                //.anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/dologin")
                .defaultSuccessUrl("/user/profile",true)
                .and().csrf().disable();//loginPage("/login").permitAll()
                // .loginProcessingUrl("/dologin")
               // .defaultSuccessUrl("/user/profile/{id}", true);


    }}
/*
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
}
*/
