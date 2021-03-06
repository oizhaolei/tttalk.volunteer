package org.tttalk.openfire.plugin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class VolunteerCancelServlet extends AbstractVolunteerServlet {
	private static final long serialVersionUID = 4000625286902414898L;


	@Override
	String getUri() {
		return "/cancel";
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Log.info(request.toString());

		String message_id = request.getParameter("message_id");
		String[] volunteers = request.getParameter("volunteers").split(",");
		plugin.cancel(volunteers, message_id);

		PrintWriter out = response.getWriter();
		out.println("success");

	}

}
