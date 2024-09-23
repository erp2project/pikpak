package kr.co.pikpak.dto;

import lombok.Data;

@Data
public class UserAddDTO {
	int user_idx;
	String user_id, user_nm, user_pw, user_tel, user_mail, user_type, user_co, user_lv;
	String target_table;
}
