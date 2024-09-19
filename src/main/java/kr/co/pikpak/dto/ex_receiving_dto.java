package kr.co.pikpak.dto;

import lombok.Data;

@Data
public class ex_receiving_dto {
	int exreceiving_idx, exreceiving_qty;
	String request_cd, deliver_cd, supplier_cd, exreceiving_cd, product_cd, exreceiving_st, exreceiving_area;
	String exreceiving_size, make_dt, exreceiving_dt, exreceiving_nm, update_dt, update_nm, lot_no;
}
