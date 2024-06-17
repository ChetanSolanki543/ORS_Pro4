package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.SchoolBean;
import com.rays.pro4.Util.JDBCDataSource;

public class SchoolModel {

	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_school");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(SchoolBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_school values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getSchoolName());
		pstmt.setString(3, bean.getSchoolTeacher());
		pstmt.setString(4, bean.getStudentName());
		pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));

		int i = pstmt.executeUpdate();
		System.out.println("Product Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(SchoolBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_school where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Product delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(SchoolBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_school set schoolName = ?, SchoolTeacher = ?,StudentName = ?, Dob = ? where id = ?");

		pstmt.setString(1, bean.getSchoolName());
		pstmt.setString(2, bean.getSchoolTeacher());
		pstmt.setString(3, bean.getStudentName());
		pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("product update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public SchoolBean findByPK(long pk) throws Exception {

		String sql = "select * from st_school where id = ?";
		SchoolBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new SchoolBean();
			bean.setId(rs.getLong(1));
			bean.setSchoolName(rs.getString(2));
			bean.setSchoolTeacher(rs.getString(3));
			bean.setStudentName(rs.getString(4));
			bean.setDob(rs.getDate(5));

		}

		rs.close();

		return bean;
	}

	public List search(SchoolBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_school where 1=1");
		if (bean != null) {

			if (bean.getSchoolName() != null && bean.getSchoolName().length() > 0) {
				sql.append(" AND schoolName like '" + bean.getSchoolName() + "%'");
			}

			if (bean.getSchoolTeacher() != null && bean.getSchoolTeacher().length() > 0) {
				//System.out.println(">>>>>>>>>>1111"+bean.getSchoolTeacher());
				sql.append(" AND SchoolTeacher like '" + bean.getSchoolTeacher() + "%'");
			}
			if (bean.getStudentName() != null && bean.getStudentName().length() > 0) {
				sql.append(" AND StudentName like '" + bean.getStudentName() + "%'");
			}

			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				Date d = new Date(bean.getDob().getTime());
				sql.append(" AND Dob = '" + d + "'");
				System.out.println("done");
			}

			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}

		}

		if (pageSize > 0) {  

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);

		}

		System.out.println("sql query search >>= " + sql.toString());
		List list = new ArrayList();

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new SchoolBean();
			bean.setId(rs.getLong(1));
			bean.setSchoolName(rs.getString(2));
			bean.setSchoolTeacher(rs.getString(3));
			bean.setStudentName(rs.getString(4));
			bean.setDob(rs.getDate(5));

			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_school");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			SchoolBean bean = new SchoolBean();
			bean.setId(rs.getLong(1));
			bean.setSchoolName(rs.getString(2));
			bean.setSchoolTeacher(rs.getString(3));
			bean.setStudentName(rs.getString(4));
			bean.setDob(rs.getDate(5));

			list.add(bean);

		}

		rs.close();

		return list;
	}

}
