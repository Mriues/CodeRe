package com.lpm.shop.interceptor;

import com.lpm.shop.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * token拦截器
 * @author Master
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //return super.preHandle(request, response, handler);
        String uri = request.getRequestURI();
        if (uri.contains("/login") || uri.contains("/register")){
            return true;
        }

        String token = request.getHeader(jwtUtils.getHeader());
        if (StringUtils.isEmpty(token)){
            token = request.getParameter(jwtUtils.getHeader());
        }
        Claims claims = jwtUtils.getTokenClaims(token);
        if (claims == null || jwtUtils.isTokenExpired(claims.getExpiration())){
            if (request.getSession() != null ){
                String newToken = jwtUtils.createToken(claims.getSubject());
                request.setAttribute("Authorization",newToken);
                return true;
            }
            request.removeAttribute("identityId");
            //返回登录页面
            request.setAttribute("msg","登录已过期");
            request.getRequestDispatcher("/login").forward(request,response);
            return false;
        }


        request.setAttribute("identityId",claims.getSubject());
        return true;
    }
}
