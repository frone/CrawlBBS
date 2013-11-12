package com.crawler.dao;

import java.sql.*;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.crawler.entity.*;

public class ArticleDao {
	public static final Logger log = Logger.getLogger(ArticleDao.class);
	Connection conn = null;

	public int insertArticleReturnID(Article art) {
		int artID = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int ViewCounts = art.getViewCounts();
		int RepCounts = art.getReplyCounts();
		SimpleDateFormat longFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into article"
				+ "(ReplyCounts, ViewCounts, Content, Title, Url, AuthorName, PostTime) "
				+ "values (?,?,?,?,?,?,?)";
		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, RepCounts);
			ps.setInt(2, ViewCounts);
			ps.setString(3, art.getContent());
			ps.setString(4, art.getTitle());
			ps.setString(5, art.getUrl());
			ps.setString(6, art.getAuthorName());
			ps.setString(7, longFormat.format(art.getPostTime()));
			int re = ps.executeUpdate();
			if (re == 1) {
				String sql2 = "select ArticleId from article where Url = ? LIMIT 1";
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setString(1, art.getUrl());
				rs = ps2.executeQuery();
				if (rs.next()) {
					artID = rs.getInt("ArticleId");
				}
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			DBUtils.close(rs, ps, conn);
		}

		return artID;
	}
	/**
	 * check whether the same article has been crawled 
	 * @param title	the title of article
	 * @return	true or false
	 */
	public boolean checkCrwaledByTitle(String title) {
		boolean flag = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			String sql = "select 1 from article where title = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, title);
			rs = ps.executeQuery();
			if (rs.next()) {
				flag = true;				
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally{
			DBUtils.close(rs, ps, conn);
		}

		return flag;
	}
	/**
	 * check whether the same article has been crawled 
	 * @param url	the url of article
	 * @return	true or false
	 */
	public boolean checkCrwaledByURL(String url) {
		boolean flag = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			String sql = "select 1 from article where url = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, url);
			rs = ps.executeQuery();
			if (rs.next()) {
				flag = true;				
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally{
			DBUtils.close(rs, ps, conn);
		}
		return flag;
	}
}
