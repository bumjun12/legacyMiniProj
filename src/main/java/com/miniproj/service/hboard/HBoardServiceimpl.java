package com.miniproj.service.hboard;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.miniproj.model.HBoardVO;
import com.miniproj.persistence.HBoardDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service // 아래의 클래스가 서비스 객체임을 컴파일러에게 알려줌


public class HBoardServiceimpl implements HBoardService {
	
	@Inject
	private HBoardDAO bDao;
	
	@Override
	public List<HBoardVO> getAllBoard() {
		System.out.println("HBoardServiceImpl...........");
		log.info("HBoardServiceImpl...........");
		
		List<HBoardVO> lst = bDao.selectAllBoard(); // DAO 메서드 호출
		
		return lst;
	}

}
