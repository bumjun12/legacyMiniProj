package com.miniproj.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HBoardVO {
	
	private int boardNo;
	private String title;
	private String content;
	private String writer;
	private Timestamp postDate;
	private int readCount;
	private int ref;
	private int step;
	private int refOrder;

}
