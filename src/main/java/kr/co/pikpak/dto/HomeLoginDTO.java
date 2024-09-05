package kr.co.pikpak.dto;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("HomeLoginDTO")
public class HomeLoginDTO {
	String user_id, user_pw, user_tel, user_mail, user_type;
}
