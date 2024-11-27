package com.apiRest.pabloq.UserManager.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public static void clear(HttpServletResponse response, String name){
        ResponseCookie cookie = ResponseCookie.from(name, null)
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .secure(true)
                .sameSite("None")
                .build();
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(0);
//        cookie.setSecure(true);
//        cookie.setDomain("https://front-end-angular-login.vercel.app");
//        response.addCookie(cookie);
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

}
