package kr.co.pikpak.dto;

import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Repository("Stock_dto ")
public class Stock_dto {
	Integer wh_warehouse_idx ;
	String area_cd;
	String location_cd;
	String product_cd;
	String product_nm;
	String supplier_nm;
	String product_qty;
	String intransit_qty;
	String allocated_qty;
	String incoming_qty;
	String inventory_log;
	String update_dt;
	String update_by;
}
