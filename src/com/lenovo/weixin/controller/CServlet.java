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

import org.apache.log4j.Logger;

import com.lenovo.weixin.beans.LinkHeadBean;
import com.lenovo.weixin.service.CService;
import com.lenovo.weixin.utils.EhcacheUtil;
import com.lenovo.weixin.utils.IPUtils;

/**
 * Servlet implementation class CServlet
 */
@WebServlet("/CServlet")
public class CServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(CServlet.class);
	private EhcacheUtil ehcacheUtil = EhcacheUtil.getEhcacheUtil();

	/**
	 * Default constructor.
	 */
	public CServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		logger.info("client IP : " + IPUtils.getIpAddr(request));
		logger.info(request.getParameter("key"));
		if (request.getParameter("key") == null) {
			out.print(ehcacheUtil.getCache("order"));
			ehcacheUtil.remove("order");
//			logger.info(ehcacheUtil.getCache("server_status_infrom"));
		}else{
			out.print(ehcacheUtil.getCache(request.getParameter("key")));
//			ehcacheUtil.remove(request.getParameter("key"));
			logger.info(ehcacheUtil.getCache(request.getParameter("key")));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		logger.info(request.getParameter("msg_signature"));
//		logger.info(request.getParameter("timestamp"));
//		logger.info(request.getParameter("nonce"));
		// logger.info(request.getParameter("message"));
		logger.info("client IP : " + IPUtils.getIpAddr(request));
		LinkHeadBean linkHead = new LinkHeadBean();
		linkHead.setMsg_signature(request.getParameter("msg_signature"));
		linkHead.setTimestamp(request.getParameter("timestamp"));
		linkHead.setNonce(request.getParameter("nonce"));
		ServletInputStream inputStream = request.getInputStream();
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		String buffer = null;
		StringBuffer strBuffer = new StringBuffer();
		while ((buffer = bufferReader.readLine()) != null) {
			strBuffer.append(buffer);
		}
		String postData = strBuffer.toString();
//		logger.info(postData);
		CService.post(linkHead, postData);
		PrintWriter out = response.getWriter();
		out.print("ok");
	}

}
