package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.SchoolBean;
import com.rays.pro4.Model.SchoolModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "SchoolCtl", urlPatterns = { "/ctl/SchoolCtl" })
public class SchoolCtl extends BaseCtl {
	
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		HashMap map = new HashMap();
		map.put("Success", "Success");
		map.put("Pending", "Pending");

		request.setAttribute("hg", map);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("SchoolName"))) {
			request.setAttribute("SchoolName", PropertyReader.getValue("error.require", "School Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("SchoolName"))) {
			request.setAttribute("SchoolName","name must contains alphabets only");
			pass = false;
		} else if (request.getParameter("SchoolName").length() > 20) {
			request.setAttribute("SchoolName","name must contains only 20 alphabets");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("SchoolTeacher"))) {
			request.setAttribute("SchoolTeacher", PropertyReader.getValue("error.require", "School Teacher"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("StudentName"))) {
			request.setAttribute("StudentName", PropertyReader.getValue("error.require", "School Status"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "Date"));
			pass = false;
		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		SchoolBean bean = new SchoolBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setSchoolName(DataUtility.getString(request.getParameter("schoolName")));

		bean.setSchoolTeacher(DataUtility.getString(request.getParameter("schoolTeacher")));
		
		bean.setStudentName(DataUtility.getString(request.getParameter("studentName")));

		bean.setDob(DataUtility.getDate(request.getParameter("date")));
		
		bean.setId(DataUtility.getLong(request.getParameter("schoolName")));

		populateDTO(bean, request);
		
		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		SchoolModel model = new SchoolModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("school Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			SchoolBean bean;

			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("uctl Do Post");

		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><<><>**********" + id + op);

		SchoolModel model = new SchoolModel();

		if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SCHOOL_LIST_CTL, request, response);

		}
		if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SCHOOL_CTL, request, response);

		}

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			SchoolBean bean = (SchoolBean) populateBean(request);

			if (id > 0) {

				try {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("School is successfully Updated", request);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("school not update");
					e.printStackTrace();
				}

			} else {

				try {
					long pk = model.add(bean);
					
					ServletUtility.setSuccessMessage("School is successfully Added", request);
					
					bean.setId(pk);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("school not added");
					e.printStackTrace();
				}

			}

		}

	}

	@Override
	protected String getView() {

		return ORSView.SCHOOL_VIEW;
	}

}
