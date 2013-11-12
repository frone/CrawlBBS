package com.crawler.entity;

import java.util.Date;

public class Article {
//	发贴：发贴人，回复数量，标题，正文，发贴时间，点击
	private String replyId;
	private int articleId;
	private String title;
	private String url;
	private String authorName;
	private int viewCounts;
	private int replyCounts;
	private Date postTime;
	private String content;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public int getViewCounts() {
		return viewCounts;
	}
	public void setViewCounts(int viewCounts) {
		this.viewCounts = viewCounts;
	}
	public int getReplyCounts() {
		return replyCounts;
	}
	public void setReplyCounts(int replyCounts) {
		this.replyCounts = replyCounts;
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int i) {
		this.articleId = i;
	}
}
