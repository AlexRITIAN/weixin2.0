package com.lenovo.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.lenovo.weixin.service.BService;
import com.lenovo.weixin.utils.ParseJSON;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class BServlet
 */
@WebServlet("/BServlet")
public class BServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(BServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		request.getRequestDispatcher("/WEB-INF/jsp/" + BService.get(request.getParameter("code"), request.getParameter("state"), session) + ".jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		// response.setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		JSONArray data = null;
		try {
			String post = BService.post(request.getParameter("name"), request.getParameter("state"), session);
			logger.info(post);
			data = ParseJSON.getJSONArray(post);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			e.printStackTrace();
		}
		out.print(data);
	}

}
