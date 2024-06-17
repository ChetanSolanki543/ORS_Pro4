package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.SchoolBean;
import com.rays.pro4.Model.SchoolModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "SchoolListCtl", urlPatterns = { "/ctl/SchoolListCtl" })
public class SchoolListCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
//		HashMap map = new HashMap();
//		map.put("Success", "Success");
//		map.put("Pending", "Pending");
//
//		request.setAttribute("lg", map);
		SchoolModel model = new SchoolModel();
		
		try {
			List prolist = model.list();
			request.setAttribute("prolist", prolist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		SchoolBean bean = new SchoolBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setSchoolName(DataUtility.getString(request.getParameter("schoolName")));

		bean.setSchoolTeacher(DataUtility.getString(request.getParameter("schoolTeacher")));
		bean.setStudentName(DataUtility.getString(request.getParameter("studentName")));

		bean.setDob(DataUtility.getDate(request.getParameter("date")));

		bean.setId(DataUtility.getLong(request.getParameter("proList")));

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List nextList = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		SchoolBean bean = (SchoolBean) populateBean(request);

		SchoolModel model = new SchoolModel();

		try {
			list = model.search(bean, pageNo, pageSize);
			nextList = model.search(bean, pageNo + 1, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("list" + list);

		request.setAttribute("nextlist", nextList.size());

		if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No record found ", request);
		}

		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		
		List list;
		List nextList = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		SchoolBean bean = (SchoolBean) populateBean(request);

		String[] ids = request.getParameterValues("ids");

		SchoolModel model = new SchoolModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
			
			String pName = request.getParameter("schoolName");
			String pCategory = request.getParameter("studentName");
			String pAmmount = request.getParameter("schoolTeacher");
			String pDate = request.getParameter("date");
			
			if(pName.equals("") && pCategory.equals("") && pAmmount == null && pDate.equals("")) {
				ServletUtility.setErrorMessage("Please fill at least one search field", request);
			}
			
			
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SCHOOL_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SCHOOL_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				SchoolBean deletebean = new SchoolBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));

					try {
						model.delete(deletebean);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					ServletUtility.setSuccessMessage("Product is Deleted Successfully", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}

		try {

			list = model.search(bean, pageNo, pageSize);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

		} catch (Exception e) {
			ServletUtility.handleException(e, request, response);
			return;
		}
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record found ", request);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SCHOOL_LIST_VIEW;
	}

}
