package org.tttalk.openfire.plugin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class AnnouncementServlet extends AbstractVolunteerServlet {
	private static final long serialVersionUID = 1159875340630997082L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Log.info(request.toString());

		String announcementId = request.getParameter("announcement_id");
		String title = request.getParameter("title");
		String[] volunteers = request.getParameter("volunteers").split(",");
		plugin.announcement(volunteers, announcementId, title);

		PrintWriter out = response.getWriter();
		out.println("success");
	}

	@Override
	String getUri() {
		return "/qa";
	}

}
