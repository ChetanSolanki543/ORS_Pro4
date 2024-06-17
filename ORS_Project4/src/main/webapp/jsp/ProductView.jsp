<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.ProductCtl"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Product Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/ValidationToInput.js"></script>
<script>
	$(function() {
		$("#udate").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '2002:2024',
		});
	});
	
	// validation for Ammount
	function validateAmount(event) {
		const input = event.target;
		input.value = input.value.replace(/[^0-9.]/g, '');
		if(input.value.length > 0 && input.value[0] <= '5') {
			
			input.value = '';
		}
	}
	
	// validation for version 
	function validateVersion(event){
		const input = event.target;
		input.value = input.value.replace(/[^0-9.]/g, '');
	}
	
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ProductBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.PRODUCT_CTL%>" method="post">

			<%
				HashMap map = (HashMap) request.getAttribute("hg");
			 %>	

			<div align="center">
				<h1>
					<%
						List rlist = (List) request.getAttribute("rlist");
					%>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Product </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Product </font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>



			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>
				<tr>
					<th align="left">Product Name <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="ProductName" onkeypress="return validateInput%(event)"
						placeholder="Enter Product Name" size="25"
						value="<%=DataUtility.getStringData(bean.getProductName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("ProductName", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>

				<tr>
					<th align="left">Product Price <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="ProductAmmount" oninput="validateAmount(event)"
						placeholder="Enter Product Ammount" size="25" 
						value="<%=DataUtility.getStringData(bean.getProductAmmount())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("ProductAmmount", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Product Status<span style="color: red">*</span>
						:
					</th>
					<td>
						 <%=HTMLUtility.getList("ProductStatus", String.valueOf(bean.getProductStatus()), map)%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("ProductStatus", request)%></font></td>
				</tr>



				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">Purchase Date <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="purchaseDate"
						placeholder="Enter Purchase Date" size="25" id="udate" readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getPurchaseDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("purchaseDate", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=ProductCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ProductCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=ProductCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ProductCtl.OP_RESET%>"></td>

					<%
						}
					%>
				</tr>
			</table>
		</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>