package org.tttalk.openfire.plugin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class QAServlet extends AbstractVolunteerServlet {
	private static final long serialVersionUID = 1159875340630997082L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Log.info(request.toString());

		String qa_id = request.getParameter("qa_id");
		String answer = request.getParameter("answer");
		String[] volunteers = request.getParameter("volunteers").split(",");
		plugin.answer(volunteers, qa_id, answer);

		PrintWriter out = response.getWriter();
		out.println("success");
	}

	@Override
	String getUri() {
		return "/qa";
	}

}
