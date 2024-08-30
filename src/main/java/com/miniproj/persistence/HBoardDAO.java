package com.miniproj.persistence;

import java.util.List;

import com.miniproj.model.HBoardVO;

public interface HBoardDAO {
	// 게시판 전체 목록 가져오는 메서드
	List<HBoardVO> selectAllBoard();
}
