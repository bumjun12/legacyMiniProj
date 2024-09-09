package com.miniproj.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproj.model.BoardDetailInfo;
import com.miniproj.model.BoardUpFilesVODTO;
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

	@Override
	public int selectMaxBoardNo() throws Exception {
		return ses.selectOne(ns + "getMaxNo");
		
	}

	@Override
	public int insertBoardUpFile(BoardUpFilesVODTO file) throws Exception {
		return ses.insert(ns + "saveUpFile", file);
		
	}

	@Override
	public HBoardVO selectBoardByBoardNo(int boardNo) throws Exception {
		return ses.selectOne(ns + "selectBoardByBoardNo", boardNo);
		
	}

	@Override
	public List<BoardUpFilesVODTO> selectBoardUpfileByBoardNo(int boardNo) throws Exception {
		return ses.selectList(ns + "selectBoardUpfileByBoardNo", boardNo);
		
	}

	@Override
	public HBoardDTO testResultMap(int boardNo) throws Exception {
		
		return ses.selectOne(ns + "selectResultmapTest", boardNo);
		
	}

	@Override
	public List<BoardDetailInfo> selectBoardDetailByBoardNo(int boardNo) throws Exception {
		return ses.selectList(ns + "selectBoardDetailInfoByBoardNo", boardNo);
	}
	
	
	

	@Override
	public int selectDateDiff(String ipAddr, int boardNo) throws Exception {
		// 넘겨줘야할 파라미터가 2개 이상이면, Map을 이용하여 파라미터를 매핑하여 넘겨준다
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("readWho", ipAddr);
		params.put("boardNo", boardNo);
		
		return ses.selectOne(ns + "selectBoardDateDiff", params);
	}

	@Override
	public int insertBoardReadLog(String ipAddr, int boardNo)  throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("readWho", ipAddr);
		params.put("boardNo", boardNo);
		return ses.insert(ns + "saveBoardReadLog", params);
	}

	@Override
	public int updateReadWhen(String ipAddr, int boardNo)throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("readWho", ipAddr);
		params.put("boardNo", boardNo);
		return ses.update(ns + "updateBoardReadLog", params);
		
	}

	@Override
	public int updateReadCount(int boardNo)throws Exception {
		return ses.update(ns + "updateReadCount", boardNo);
	}

}
