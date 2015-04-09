package org.tttalk.openfire.plugin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class UpdateUserPwdServlet extends AbstractVolunteerServlet {
	private static final long serialVersionUID = 1159875340630997082L;
	private static final Logger Log = LoggerFactory
			.getLogger(UpdateUserPwdServlet.class);

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Log.info(request.toString());

		String username = request.getParameter("user");
		String newPwd = request.getParameter("new_pwd");
		plugin.updateUserPwd(username, newPwd);

		PrintWriter out = response.getWriter();
		out.println("success");
	}

	@Override
	String getUri() {
		return "/change_user_pwd";
	}
}