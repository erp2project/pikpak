package kr.co.pikpak.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pikpak.dto.outgoing_cd_dto;
import kr.co.pikpak.dto.return_dto;
import kr.co.pikpak.dto.return_list_dto;

@Service
public class return_serviceImpl implements return_service{
	
	@Autowired
	private kr.co.pikpak.repo.return_repo return_repo;
	
	@Override
	public int return_approval(return_dto return_dto, int type) {
		int result = return_repo.return_approval(return_dto, type);
		return result;
	}
	
	@Override
	public int return_delete(int return_idx) {
		int result = return_repo.return_delete(return_idx);
		return result;
	}
	
	@Override
	public List<return_list_dto> return_list(String user_company) {
		List<return_list_dto> list = return_repo.return_list(user_company);
		
		return list;
	}
	
	@Override
	public List<return_list_dto> return_list_all() {
		List<return_list_dto> alllist = return_repo.return_list_all();
		return alllist;
	}
	
	@Override
	public List<outgoing_cd_dto> outgoing_cd_search(String outgoing_cd) {
		List<outgoing_cd_dto> list = return_repo.outgoing_cd_search(outgoing_cd);
		
		return list;
	}
	
	@Override
	public int return_enroll(return_dto return_dto) {
		int result = return_repo.return_enroll(return_dto);
		
		return result;
	}
}
