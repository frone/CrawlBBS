package com.crawler.entity;

import java.util.Date;

public class Reply {
//	�ظ�:����ID�������ˣ��ظ����ݣ�����ʱ��
	private int articleId;
	private String ReplierName;
	private Date postTime;
	private String content;
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public String getReplierName() {
		return ReplierName;
	}
	public void setReplierName(String ReplierName) {
		this.ReplierName = ReplierName;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
