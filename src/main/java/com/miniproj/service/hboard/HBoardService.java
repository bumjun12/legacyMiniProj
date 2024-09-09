package com.miniproj.service.hboard;

import java.util.HashMap;
import java.util.List;

import com.miniproj.model.BoardDetailInfo;
import com.miniproj.model.HBoardDTO;
import com.miniproj.model.HBoardVO;

public interface HBoardService {
	// 게시판 전체의 리스트 조회
	List<HBoardVO> getAllBoard() throws Exception;

	// 게시글 저장
	boolean saveBoard(HBoardDTO boardDTO) throws Exception;

	HashMap<String, Object> viewBoardByBoardNo(int boardNo) throws Exception;
	
	// resultmap 테스트
	HBoardDTO testResultMap(int boardNo) throws Exception;
	
	// 게시글 상세 조회
	List<BoardDetailInfo> read(int boardNo, String ipAddr) throws Exception;
}
