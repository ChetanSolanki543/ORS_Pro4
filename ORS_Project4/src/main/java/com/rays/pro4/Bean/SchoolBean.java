package com.rays.pro4.Bean;

import java.util.Date;

public class SchoolBean extends BaseBean {

	private String SchoolName;
	private String SchoolTeacher;
	private String StudentName;
	private Date dob;
	
	
	public String getSchoolName() {
		return SchoolName;
	}
	public void setSchoolName(String schoolName) {
		SchoolName = schoolName;
	}
	public String getSchoolTeacher() {
		return SchoolTeacher;
	}
	public void setSchoolTeacher(String schoolTeacher) {
		SchoolTeacher = schoolTeacher;
	}
	public String getStudentName() {
		return StudentName;
	}
	public void setStudentName(String studentName) {
		StudentName = studentName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}	
	
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return SchoolName;
	}

	@Override
	public int compareTo(BaseBean o) {
				return 0;
	}

}
