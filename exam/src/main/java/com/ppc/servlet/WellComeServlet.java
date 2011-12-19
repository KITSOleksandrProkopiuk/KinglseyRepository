package com.ppc.servlet;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WellComeServlet extends HttpServlet {
	private static final long serialVersionUID = 6975905555488773005L;

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) {
		try {
			
			req.setCharacterEncoding("Cp1251");
			
			String name = req.getParameter("user_name");
			String a = new String(name.getBytes(), "Cp1251");
			System.out.println(a);

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
	}
}
