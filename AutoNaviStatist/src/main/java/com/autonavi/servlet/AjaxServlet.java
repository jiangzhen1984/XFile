package com.autonavi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.autonavi.service.MapDownloadDataService;

public class AjaxServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3965961456963475638L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");

		
		resp.setCharacterEncoding("UTF8");
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		if ("rmd".equals(action)) {
			JSONObject data = retrieveMapData();
			out.print(data.toString());
		}
		
		out.flush();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	
	
	private JSONObject retrieveMapData() {
		MapDownloadDataService mls = new MapDownloadDataService();
		return mls.getMapData();
	}
	
}
