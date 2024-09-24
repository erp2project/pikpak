package kr.co.pikpak.dto;

import lombok.Data;

@Data
public class outgoing_enroll_dto {
	int outgoing_idx;
	String outenroll_cd, order_cd, product_cd, product_nm, outenroll_st, expect_dt;
	String exoutgoing_area, outenroll_log, outgoing_dt, update_dt, operator_id, update_id;
}
