package com.lenovo.weixin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lenovo.weixin.utils.ClientUtil;
import com.lenovo.weixin.utils.LoadConfig;

/**
 * Servlet implementation class RServlet
 */
@WebServlet("/RServlet")
public class RServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoadConfig lc = new LoadConfig("conf.properties");
		ServletInputStream inputStream = request.getInputStream();
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		String buffer = null;
		StringBuffer strBuffer = new StringBuffer();
		while ((buffer = bufferReader.readLine()) != null) {
			strBuffer.append(buffer);
		}
		String postData = strBuffer.toString();
		String jsonStr = "{\"touser\":\"" + request.getParameter("fromUser")
				+ "\",\"msgtype\":\"text\",\"agentid\":2,\"text\":{\"content\":\"" + postData + "\"}}";
		ClientUtil.post(lc.getProperty("sendUrl"), jsonStr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
