<%@page import="com.glyxybxhtxt.util.Me "%>
<%@page import="com.glyxybxhtxt.util.RealMe "%>
<%@page import="com.glyxybxhtxt.util.GetInfo "%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>桂医报修系统易班授权</title>
</head>
<body>
	<%
	if(session.getAttribute("eid")==null){
		session.setAttribute("eid", request.getParameter("eid"));
	}
	Object o = session.getAttribute("realme");
	if(o==null)
	{ 
		GetInfo getinfo = new GetInfo("3fe1ac4f66fe7bbc","3ba03e09d703867644388a54e90f4c6a","http://f.yiban.cn/iapp645713");
		getinfo.getRealMe(request, response);
//		RealMe rm = new RealMe();
//		Me m = new Me();
//		m.setId("6615683");m.setHead("http://img02.fs.yiban.cn/5000209/avatar/user/200");
//		rm.setM(m);
//		rm.setRealName("田七");
//		rm.setSid("20179085");
//		session.setAttribute("realme", rm);
//		response.sendRedirect("Login");
	}
	Object o2 = session.getAttribute("realme");
	if(o2!=null){
		response.sendRedirect("Login");
	}
	
	%>
</body>
</html>