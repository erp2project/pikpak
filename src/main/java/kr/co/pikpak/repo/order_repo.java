package kr.co.pikpak.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pikpak.dto.order_dto;
import kr.co.pikpak.dto.order_list_dto;
import kr.co.pikpak.dto.product_cd_dto;

@Mapper
public interface order_repo {
	
	//주문 등록
	int order_enroll(kr.co.pikpak.dto.order_dto order_dto);
	
	//주문 목록
	public List<order_list_dto> order_list(String user_company);
	
	//상품 코드 조회
	public List<product_cd_dto> product_cd_search(String product_cd);
	
	//주문 수정
	public int order_modify(order_dto order_dto);
	
	//주문 승인
	public int order_approval(order_dto order_dto);
	
	//주문 삭제
	public int order_delete(int order_idx);
}
