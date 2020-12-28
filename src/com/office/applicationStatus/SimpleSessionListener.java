package com.office.applicationStatus;

import java.io.Serializable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class SimpleSessionListener implements HttpSessionListener, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final Logger logger = Logger.getLogger(SimpleSessionListener.class);
	private static int activeSessions;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		activeSessions++;
		logger.info("session created - total active sessions: " + activeSessions);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
//		HttpSession session = se.getSession();
////		ArcUsers user = (ArcUsers) session.getAttribute("user");
//		activeSessions--;
////		if (user != null)
//		logger.info("session destroyed - total active sessions: " + activeSessions);
	}
}