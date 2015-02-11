package org.tttalk.openfire.plugin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jivesoftware.admin.AuthCheckFilter;
import org.jivesoftware.openfire.XMPPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class VolunteerCancelServlet extends HttpServlet {
	private static final long serialVersionUID = 4000625286902414898L;

	private static final Logger Log = LoggerFactory
			.getLogger(VolunteerCancelServlet.class);

	private VolunteerPlugin plugin;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		plugin = (VolunteerPlugin) XMPPServer.getInstance().getPluginManager()
				.getPlugin("tttalk.volunteer");
		AuthCheckFilter.addExclude("tttalk.volunteer/cancel");
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

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

}
