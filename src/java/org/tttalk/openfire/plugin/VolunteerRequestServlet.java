package org.tttalk.openfire.plugin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class VolunteerRequestServlet extends AbstractVolunteerServlet {
	private static final long serialVersionUID = 4000625286902414898L;

	@Override
	String getUri() {
		return "/request";
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Log.info(request.toString());

		String content = request.getParameter("content");
		String messageId = request.getParameter("message_id");
		int fee = Integer.valueOf(request.getParameter("fee"));
		String[] onlineVolunteers =  request.getParameter("volunteers").split(",");
		plugin.request(onlineVolunteers, content, messageId, fee);

		PrintWriter out = response.getWriter();
		out.println("success");

	}

}
