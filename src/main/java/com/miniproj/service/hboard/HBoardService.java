package com.miniproj.service.hboard;

import java.util.List;

import com.miniproj.model.HBoardDTO;
import com.miniproj.model.HBoardVO;

public interface HBoardService {
	// 게시판 전체의 리스트 조회
	List<HBoardVO> getAllBoard() throws Exception;

	// 게시글 저장
	boolean saveBoard(HBoardDTO boardDTO) throws Exception;
	
}
