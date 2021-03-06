package kr.co.seoRak.myInfo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.seoRak.common.db.MyAppSqlConfig;
import kr.co.seoRak.repository.domain.Member;
import kr.co.seoRak.repository.domain.MyBookList;
import kr.co.seoRak.repository.mapper.MyBookListMapper;
import kr.co.seoRak.repository.mapper.PointMapper;

@WebServlet("/myInfo.do")
public class MyInfoController extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("user");
		String memberId = member.getMemberId();
		
		MyBookListMapper mapperMbl = MyAppSqlConfig.getSqlSessionInstance().getMapper(MyBookListMapper.class);
		PointMapper mapperPoint = MyAppSqlConfig.getSqlSessionInstance().getMapper(PointMapper.class);
		int memberPoint = mapperPoint.selectPointTotalById(memberId);
		
		List<MyBookList> listMbl = mapperMbl.selectById(memberId);
		request.setAttribute("listMbl", listMbl);
		request.setAttribute("member", member);
		request.setAttribute("memberPoint", memberPoint);
		
		// http://localhost:8000/seoRak/jsp/myInfo2.jsp
		System.out.println(request.getContextPath());
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/mylist/myInfo.jsp");
		rd.forward(request, response);

	}
	
	

}
