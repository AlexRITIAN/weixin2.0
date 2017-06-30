package com.lenovo.weixin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.log4j.Logger;

import com.lenovo.weixin.beans.LinkHeadBean;
import com.lenovo.weixin.service.WService;

/**
 * Servlet implementation class WServlet
 */
@WebServlet("/WServlet")
public class WServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private static Logger logger = Logger.getLogger(WServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LinkHeadBean linkHead = new LinkHeadBean();
		linkHead.setMsg_signature(request.getParameter("msg_signature"));
		linkHead.setTimestamp(request.getParameter("timestamp"));
		linkHead.setNonce(request.getParameter("nonce"));
		String echostr = WService.get(linkHead, request.getParameter("echostr"));
		PrintWriter out = response.getWriter();
//		logger.info(echostr);
		out.print(echostr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LinkHeadBean linkHead = new LinkHeadBean();
		linkHead.setMsg_signature(request.getParameter("msg_signature"));
		linkHead.setTimestamp(request.getParameter("timestamp"));
		linkHead.setNonce(request.getParameter("nonce"));
		
		
   
		String postData = null;

		// 获取请求输入流
		ServletInputStream inputStream = request.getInputStream();
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		String buffer = null;
		StringBuffer strBuffer = new StringBuffer();
		while ((buffer = bufferReader.readLine()) != null) {
			strBuffer.append(buffer);
		}
		postData = strBuffer.toString();
//		logger.info(postData);
		PrintWriter out = response.getWriter();
		out.print(WService.post(linkHead, postData));
	}

}
