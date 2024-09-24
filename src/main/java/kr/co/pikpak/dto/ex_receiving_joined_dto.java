package kr.co.pikpak.dto;

import lombok.Data;

@Data
public class ex_receiving_joined_dto {
	String exreceiving_idx, deliver_cd, exreceiving_cd, supplier_cd, supplier_nm, product_cd, product_nm, exreceiving_size;
	String exreceiving_qty, return_qty, exreceiving_area, departure_dt, exreceiving_st, update_id, make_dt;
}
