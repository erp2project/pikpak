package kr.co.pikpak.dto;

import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.Setter;

//재고 리스트 
@Getter
@Setter
@Repository("inventory_dto")
public class WarehouseInventory_dto {
	Integer wh_warehouse_idx ;
	String area_cd;
	String location_cd;
	String location_sz;
	String product_cd;
	String product_nm;
	String product_qty;
	String intransit_qty;
	String allocated_qty;
	String incoming_qty;
	String inventory_log;
	String update_by;
	String update_dt;
	
}
