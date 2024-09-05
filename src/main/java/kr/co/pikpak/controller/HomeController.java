package kr.co.pikpak.controller;

import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import jakarta.annotation.Resource;
import jakarta.servlet.ServletResponse;
import kr.co.pikpak.dto.HomeLoginDTO;
import kr.co.pikpak.service.HomeService;

@Controller
public class HomeController {
	@Resource(name="HomeLoginDTO")
	HomeLoginDTO hldto;
	
	@Autowired
	private HomeService hs;
	
	PrintWriter pw = null;
	
	
	@PostMapping("/login/auth")
	public String loginVerify(@ModelAttribute HomeLoginDTO logindto, ServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
		List<HomeLoginDTO> userData = hs.userAuth(logindto.getUser_id());
		String encodedPass = DigestUtils.sha256Hex(logindto.getUser_pw());
		System.out.println(userData);
		try {
			this.pw = res.getWriter();
			if(userData.size()==0) {
				this.pw.print("<script>"
						+ "alert('아이디 및 패스워드를 확인하세요');"
						+ "history.go(-1);"
						+ "</script>");
			}
			else {
				if(encodedPass.equals(userData.get(0).getUser_pw())) {
					this.pw.print("<script>"
							+ "alert('로그인 하셨습니다');"
							+ "location='/home'"
							+ "</script>");
				}
				else {
					this.pw.print("<script>"
							+ "alert('아이디 및 패스워드를 확인하세요');"
							+ "history.go(-1);"
							+ "</script>");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DDL 쿼리 오류 발생");
		} finally {
			this.pw.close();
		}
				
		
		return null;
	}
}
