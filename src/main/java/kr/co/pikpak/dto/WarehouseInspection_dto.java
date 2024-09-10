package kr.co.pikpak.dto;

import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Repository("warehouse_check_dto")
public class WarehouseInspection_dto {
	Integer a_check_idx;
	String area_cd;
	String manager_nm;
	String statement;
	String check_log;
	String check_start_dt;
	String check_end_dt;
}
