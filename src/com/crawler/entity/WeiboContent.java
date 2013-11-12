package com.crawler.entity;

public class WeiboContent {
	private int articleId;
	private String authorName;
	private String postTime;
	private String content;
	private int plusCounts;
	private int forwardCounts;
	private int commentCounts;
	
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getPostTime() {
		return postTime;
	}
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPlusCounts() {
		return plusCounts;
	}
	public void setPlusCounts(int plusCounts) {
		this.plusCounts = plusCounts;
	}
	public int getForwardCounts() {
		return forwardCounts;
	}
	public void setForwardCounts(int forwardCounts) {
		this.forwardCounts = forwardCounts;
	}
	public int getCommentCounts() {
		return commentCounts;
	}
	public void setCommentCounts(int commentCounts) {
		this.commentCounts = commentCounts;
	}
	
}
