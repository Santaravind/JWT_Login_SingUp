package com.JWT.Authentication.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

//import java.lang.classfile.Signature;
import java.security.Key;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {
    private  static  String secretKey= "YUhSMGNITTZMeTkzWldKaGRHVXRhRzkzZEM1amIyMGlhWE1pTkdjdE9TNWpiMjA9";

    private static long jwlExpiration=1800000;

   public static String extractUsername(String token){
       return extreactClaims(token, Claims::getSubject);
   }

   public static <T> T extreactClaims(String token, Function<Claims, T> claimsResolver){
       final Claims claims=extectAllClaims(token);
       return claimsResolver.apply(claims);

   }

   private static Claims extectAllClaims(String token){
       return Jwts.parser()
               .setSigningKey(getSignKey())
               .build()
               .parseClaimsJws(token)
               .getBody();
   }

public long getExpriretionTime(){
       return jwlExpiration;
}

    //generate token with extra clamims

    public String generateToken(Map<String, Objects> extraCalims, UserDetails userDetails){
        return buildToken(extraCalims,userDetails,jwlExpiration);
    }

    public String buildToken(Map<String, Objects> extraCalims, UserDetails userDetails, long jwlExpiration){

        return  Jwts.builder()
                .setClaims(extraCalims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwlExpiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public static Key getSignKey(){
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    //check if the token is valid or not

    public boolean isTokenValid(String token,UserDetails userDetails){
       final  String userName=extractUsername(token);
       return (userName.equals(userDetails.getUsername())&& !isTokenKexpired(token));
    }

    public boolean isTokenKexpired(String token){
       return extractExpireation(token).before(new Date());
    }

    private  Date extractExpireation(String token){
       return extreactClaims(token,Claims::getExpiration);
    }
}
