package org.tttalk.openfire.plugin;

//import java.io.File;
import java.io.File;

import org.dom4j.Element;
import org.jivesoftware.openfire.MessageRouter;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;

/**
 * 1. Accept translate request, then call translate api.<br/>
 * 2. Present a callback interface, send result to a particular user.
 *
 * @author zhaolei
 */
public class VolunteerPlugin implements Plugin {
	private static final Logger log = LoggerFactory
			.getLogger(VolunteerPlugin.class);

	private static final String TTTALK_USER_VOLUNTEER = "tttalk.user.volunteer";

	public String getVolunteer() {
		return JiveGlobals.getProperty(TTTALK_USER_VOLUNTEER);
	}

	private final XMPPServer server;

	public VolunteerPlugin() {
		server = XMPPServer.getInstance();
		router = server.getMessageRouter();
	}

	@Override
	public void initializePlugin(PluginManager pManager, File pluginDirectory) {
		// register with interceptor manager
	}

	@Override
	public void destroyPlugin() {
		// unregister with interceptor manager
	}

	private final MessageRouter router;

	public void cancel(String[] volunteers, String messageId) {
		Message message = new Message();
		message.setFrom(getVolunteer() + "@"
				+ server.getServerInfo().getXMPPDomain());
		String subject = "cancel";
		message.setSubject(subject);
		message.setBody(subject);

		Element tttalkNode = message.addChildElement("volunteer",
				"http://jabber.org/protocol/tranlate");
		// tttalkNode.addAttribute("test", "true");
		// tttalkNode.addAttribute("ver", "1");

		tttalkNode.addAttribute("title", "cancel");
		tttalkNode.addAttribute("message_id", messageId);

		for (String v : volunteers) {
			message.setTo(v);
			log.info(message.toXML());
			router.route(message);
		}
	}

	public void request(String[] volunteers, String content, String messageId,
			int fee) {
		Message message = new Message();
		message.setFrom(getVolunteer() + "@"
				+ server.getServerInfo().getXMPPDomain());
		String subject = "request";
		message.setSubject(subject);
		message.setBody(content);

		Element tttalkNode = message.addChildElement("volunteer",
				"http://jabber.org/protocol/tranlate");
		tttalkNode.addAttribute("message_id", messageId);
		tttalkNode.addAttribute("fee", String.valueOf(fee));
		
		for (String v : volunteers) {
			message.setTo(v);
			log.info(message.toXML());
			router.route(message);
		}
	}
}
