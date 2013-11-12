package com.crawler.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.crawler.entity.*;

public class ReplyDao {
	public static final Logger log = Logger.getLogger(ReplyDao.class);
	Connection conn = null;
	SimpleDateFormat longFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public int insertReply(Reply rep) {
		int reNum = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "insert into reply"
				+ "(ArticleId, ReplierName, Content, PostTime) "
				+ "values (?,?,?,?)";
		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, rep.getArticleId());
			ps.setString(2, rep.getReplierName());
			ps.setString(3, rep.getContent());
			ps.setString(4, longFormat.format(rep.getPostTime()));
			reNum = ps.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			DBUtils.close(rs, ps, conn);
		}
		return reNum;
	}

	public void insertReplys(List<Reply> li) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = li.size();
		String sql = "insert into reply"
				+ "(ArticleId, ReplierName, Content, PostTime) "
				+ "values (?,?,?,?)";
		int i = 0;
		try {
			conn = DBUtils.getConnection();
			for (i = 0; i < size; i++) {
				ps = conn.prepareStatement(sql);
				if (null != li.get(i).getReplierName() && "" != li.get(i)
						.getReplierName().trim()
						&& null != li.get(i).getContent() && "" != li
								.get(i).getContent().trim()) {
					ps.setInt(1, li.get(i).getArticleId());
					ps.setString(2, li.get(i).getReplierName());
					ps.setString(3, li.get(i).getContent());
					ps.setString(4, longFormat.format(li.get(i).getPostTime()));
					ps.executeUpdate();
				}
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error("wrong time" + li.get(i).getArticleId() + "\t"
					+ li.get(i).getReplierName());
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			log.error("wrong time" + li.get(i).getArticleId() + "\t"
					+ li.get(i).getReplierName());
		}
		DBUtils.close(rs, ps, conn);
	}
}
