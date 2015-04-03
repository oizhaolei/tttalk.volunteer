package org.tttalk.openfire.plugin;

//import java.io.File;
import java.io.File;

import org.dom4j.Element;
import org.jivesoftware.openfire.MessageRouter;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.user.User;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.openfire.user.UserNotFoundException;
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
	public static final String PLUGIN_NAME = "tttalk.volunteer";

	private static final String VOLUNTEER_NAMESPACE = "http://tttalk.org/protocol/volunteer";
	private static final String TAG_REQUEST = "request";
	private static final String TAG_CANCEL = "cancel";
	private static final String TAG_QA = "qa";
	private static final String TAG_ANNOUNCEMENT = "announcement";

	private static final Logger log = LoggerFactory
			.getLogger(VolunteerPlugin.class);

	private static final String TTTALK_USER_VOLUNTEER = "tttalk.user.volunteer";

	public String getVolunteer() {
		return JiveGlobals.getProperty(TTTALK_USER_VOLUNTEER);
	}

	private final XMPPServer server;
	private UserManager userManager;

	public VolunteerPlugin() {
		server = XMPPServer.getInstance();
		router = server.getMessageRouter();
		userManager = UserManager.getInstance();

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

		Element tttalkNode = message.addChildElement(TAG_CANCEL,
				VOLUNTEER_NAMESPACE);
		// tttalkNode.addAttribute("test", "true");
		// tttalkNode.addAttribute("ver", "1");

		tttalkNode.addAttribute("title", subject);
		tttalkNode.addAttribute("message_id", messageId);

		for (String v : volunteers) {
			message.setTo(v);
			log.info(message.toXML());
			router.route(message);
		}
	}

	public void request(String[] volunteers, String content, String messageId) {
		Message message = new Message();
		message.setFrom(getVolunteer() + "@"
				+ server.getServerInfo().getXMPPDomain());
		String subject = "request";
		message.setSubject(subject);
		message.setBody(content);

		Element tttalkNode = message.addChildElement(TAG_REQUEST,
				VOLUNTEER_NAMESPACE);
		tttalkNode.addAttribute("title", subject);
		tttalkNode.addAttribute("message_id", messageId);
		
		for (String v : volunteers) {
			message.setTo(v);
			log.info(message.toXML());
			router.route(message);
		}
	}

	public void qa(String[] volunteers, String qaId, String answer) {
		Message message = new Message();
		message.setFrom(getVolunteer() + "@"
				+ server.getServerInfo().getXMPPDomain());
		String subject = "qa";
		message.setSubject(subject);
		message.setBody(answer);

		Element tttalkNode = message.addChildElement(TAG_QA,
				VOLUNTEER_NAMESPACE);
		// tttalkNode.addAttribute("test", "true");
		// tttalkNode.addAttribute("ver", "1");

		tttalkNode.addAttribute("title", subject);
		tttalkNode.addAttribute("qa_id", qaId);

		for (String v : volunteers) {
			message.setTo(v);
			log.info(message.toXML());
			router.route(message);
		}
	}

	public void announcement(String[] volunteers, String announcementId,
			String title) {
		Message message = new Message();
		message.setFrom(getVolunteer() + "@"
				+ server.getServerInfo().getXMPPDomain());
		String subject = "announcement";
		message.setSubject(subject);
		message.setBody(title);

		Element tttalkNode = message.addChildElement(TAG_ANNOUNCEMENT,
				VOLUNTEER_NAMESPACE);
		// tttalkNode.addAttribute("test", "true");
		// tttalkNode.addAttribute("ver", "1");

		tttalkNode.addAttribute("title", subject);
		tttalkNode.addAttribute("announcement_id", announcementId);

		for (String v : volunteers) {
			message.setTo(v);
			log.info(message.toXML());
			router.route(message);
		}
	}

	public void updateUserPwd(String jid, String newPwd) {

		String username = getUserName(jid);
		try {
			User user = userManager.getUser(username);
			user.setPassword(newPwd);
			log.info(String.format("updateUserPwd:%s,%s", username, newPwd));
		} catch (UserNotFoundException e) {
			log.error(e.getMessage(), e);
		}
	}

	private String getUserName(String jid) {
		return jid.substring(0, jid.indexOf('@'));
	}
}
