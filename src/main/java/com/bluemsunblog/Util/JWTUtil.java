package com.bluemsunblog.Util;

import com.bluemsunblog.entity.User;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    //密钥
    private static final String jwtToken = "qweryyeueueiiwiiwi";

    public static String creatToken(User user){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",user.getUserId());
        claims.put("userName",user.getUserName());
        claims.put("userStatus",user.getUserStatus());
        claims.put("userEmail",user.getUserEmail());
        claims.put("userPhone",user.getUserPhone());
        claims.put("userProhibit",user.getUserProhibit());
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,jwtToken)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+24*7*60*60*1000));
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String,Object> checkToken(String token){
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
