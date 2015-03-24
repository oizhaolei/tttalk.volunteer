package org.tttalk.openfire.plugin;

import java.io.IOException;

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
public abstract class AbstractVolunteerServlet extends HttpServlet {
	private static final long serialVersionUID = 4039409827926990397L;

	static final Logger Log = LoggerFactory
			.getLogger(AbstractVolunteerServlet.class);

	VolunteerPlugin plugin;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		plugin = (VolunteerPlugin) XMPPServer.getInstance().getPluginManager()
				.getPlugin(VolunteerPlugin.PLUGIN_NAME);
		AuthCheckFilter.addExclude(VolunteerPlugin.PLUGIN_NAME + getUri());
	}

	// TODO: sign check
	abstract String getUri();

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
