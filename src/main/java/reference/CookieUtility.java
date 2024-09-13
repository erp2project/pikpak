//package com.counseling.cms.utility;
package reference;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtility {
	public static String getCookie(HttpServletRequest req, String name) {

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
	}
	
	public static void deleteCookie(HttpServletResponse response, String name, String path) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(path); // 쿠키의 경로를 설정
        cookie.setMaxAge(0); // 쿠키의 수명을 0으로 설정하여 삭제
        response.addCookie(cookie); // 쿠키를 응답에 추가
    }
}
