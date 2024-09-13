package kr.co.pikpak.dto;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("LoginDTO")
public class LoginDTO {
	String user_id, user_nm, user_pw, user_tel, user_mail, user_type;
}
