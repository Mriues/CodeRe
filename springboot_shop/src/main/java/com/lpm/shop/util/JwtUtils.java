package com.lpm.shop.util;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

/**
 * jwt生成
 * @author Master
 */
@ConfigurationProperties(prefix = "config.jwt")
@Component
public class JwtUtils {

    //盐值
    private String secret;
    private long expire;
    private String header;

    public String createToken(String name){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setIssuedAt(nowDate)
                .setSubject(name)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public Claims getTokenClaims(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //判断是否失效
    public Boolean isTokenExpired(Date expirationTime){
        return expirationTime.before(new Date());
    }

    //获取失效时间
    public Date getExpirationFromToken(String token){
        return getTokenClaims(token).getExpiration();
    }

    //获取自主标识
    public String getUserFromToken(String token){
        return getTokenClaims(token).getSubject();
    }

    //获取jwt签发时间
    public Date getIssuedAtDateFromToken(String token){
        return getTokenClaims(token).getIssuedAt();
    }




    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    /*public String createToken(String name){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        //header
        JSONObject header = new JSONObject();
        header.put("alg","HS256");
        String headerStr = header.toJSONString();

        //payload
        JSONObject payload = new JSONObject();
        payload.put("username",name);
        payload.put("expire",expireDate);
        String payloadStr = payload.toJSONString();

        //sign
        String sign = DigestUtils.md5Hex(payloadStr + SECRET_KEY);
        String token = Base64.getEncoder().encodeToString(headerStr.getBytes()) + "." +
                Base64.getEncoder().encodeToString(payloadStr.getBytes()) + "." +
                Base64.getEncoder().encodeToString(sign.getBytes());
        return token;
    }*/

    /*public Boolean isValidToken(String token){
        String split[] = token.split("\\.");
        String payload = Base64.getDecoder().decode(split[1].getBytes()).toString();
        String sign = Base64.getDecoder().decode(split[2].getBytes()).toString();
        return null;
    }*/
}
