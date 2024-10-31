package com.apiRest.pabloq.UserManager.Jwt;

import com.apiRest.pabloq.UserManager.Entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String secretKey = "d3f0d1fd0c9c4e91617757bcb2ea53ea912709c7aa9ae5342f4ecff7a73bad4d92370465bf6ffe8e01a0f3d689a5e5f989fd27e3d5513d0654457a81e8aa3c387d7fe6648783e0bc68ebbf8f5d80d847d5e8398621d8c70adf9abc5d294cc6e979ca95459db12e8a51733f8adf9e2f8d7e9adab14a445d88ec4c962d2d04c6721e93823b6cf8e4aba2473ccc21b0b2876b78ebbdb13fe2072c550523ae1060bd9f8a228717969ef74000f5a833fafd8aa1f155f430a48cf42806ecdd6dfcb006cbf1a174c5ccf3ee73761e05e2155a79e392f5da379de10a4d12ec10c73d25563382f12b6eec2d38006de87373e7e493f5fc727b774f7a6e806ff3fdc0af95d0";

    public String getToken(User user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, User user) {
        return Jwts.builder()
                .claims(extraClaims)
                .claim("userId", user.getId())
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                //TODO revisar porque no lee nada aqui durante la ejecución
                .signWith(getKey())
//                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

}
