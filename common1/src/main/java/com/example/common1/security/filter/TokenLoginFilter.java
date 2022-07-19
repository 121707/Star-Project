package com.example.common1.security.filter;

import com.example.common1.security.entity.SecurityUser;
import com.example.common1.security.entity.User;
import com.example.common1.security.security.TokenManager;
import com.example.common1.utils.utils.R;
import com.example.common1.utils.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

//    private RedisTemplate redisTemplate;
    private TokenManager tokenManager;
    private AuthenticationManager authenticationManager;

//    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
//        this.authenticationManager = authenticationManager;
//        this.tokenManager = tokenManager;
//        this.redisTemplate = redisTemplate;
//        this.setPostOnly(false);
//        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login","POST"));
//    }

    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager) {
        super();
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login","POST"));
    }
    //1 获取表单提交用户名和密码
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        //获取表单提交数据
            String userName = this.obtainUsername(request);
            String passWord = this.obtainPassword(request);
//            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            User user = new User();
            user.setUserName(userName);
            user.setPassWord(passWord);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassWord(),
                    new ArrayList<>()));

    }

//    2 认证成功调用的方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        //认证成功，得到认证成功之后用户信息
        SecurityUser user = (SecurityUser)authResult.getPrincipal();
        //根据用户名生成token
        String token = tokenManager.createToken(user.getCurrentUserInfo().getUserName());
        //把用户名称和用户权限列表放到redis
//        redisTemplate.opsForValue().set(user.getCurrentUserInfo().getUsername(),user.getPermissionValueList());
        //返回token
        response.addHeader("token", token);
//        this.getSuccessHandler().onAuthenticationSuccess(request, response, chain, authResult);

//        response.sendRedirect("/shop/main");
//        System.out.println("pppp"+token);
        getSuccessHandler().onAuthenticationSuccess(request,response,authResult);
//        chain.doFilter(request,response);
//        ResponseUtil.out(response, R.ok().data("token",token));
    }

//    3 认证失败调用的方法
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        ResponseUtil.out(response, R.error());
    }
}
