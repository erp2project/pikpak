package kr.co.pikpak.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pikpak.dto.product_dto_lhwtemp;
import kr.co.pikpak.repo.InoutBoundRepo;

@Service
public class InoutBoundServiceImpl implements InoutBoundService{
	
	@Autowired
	InoutBoundRepo iorepo;
	
	@Override
	public List<product_dto_lhwtemp> select_pdlist() {
		List<product_dto_lhwtemp> all = iorepo.selectProduct();
		return all;
	}
}
