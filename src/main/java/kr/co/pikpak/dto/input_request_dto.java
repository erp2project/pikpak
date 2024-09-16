package kr.co.pikpak.dto;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("ir_dto")
public class input_request_dto {
	int request_idx, product_qty;
	String request_cd, supplier_cd, supplier_nm, product_cd, product_nm;
	String operator_id, operator_nm, request_st, add_req, hope_dt, request_dt, update_dt, update_nm;
}
