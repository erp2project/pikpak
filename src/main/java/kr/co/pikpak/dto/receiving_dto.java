package kr.co.pikpak.dto;

import lombok.Data;

@Data
public class receiving_dto {
	int receiving_idx, receiving_qty;
	String deliver_cd, exreceiving_cd, receiving_cd, lon_no, supplier_cd, product_cd, receving_size, location_cd;
	String operator_id, inventory_dt, receiving_dt;
}
