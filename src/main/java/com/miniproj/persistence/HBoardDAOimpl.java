package com.miniproj.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproj.model.HBoardDTO;
import com.miniproj.model.HBoardVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository // 아래의 클래스가 DAO 객체임을 명시
public class HBoardDAOimpl implements HBoardDAO {

	@Inject
	private SqlSession ses;

	private static String ns = "com.miniproj.mappers.hboardmapper.";

	@Override
	public List<HBoardVO> selectAllBoard() {
		
		log.info("Here is HBoardDaoImpl..........");

//		return ses.selectList(ns + "getAllHBoar"); // select 쿼리태그의 id를 잘못 준 경우
		return ses.selectList(ns + "getAllHBoard"); 
	}

	@Override
	public int insertNewBoard(HBoardDTO newBoard) throws Exception {
		return ses.insert(ns + "saveNewBoard", newBoard);
	}

}
