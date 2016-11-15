package jhi.brapi.util;

import java.util.*;
import java.util.concurrent.*;

public class TokenManager
{
	Map<String, SessionInfo> sessions = new ConcurrentHashMap<>();
	long age = 30000;

	public TokenManager()
	{
		Timer timer = new Timer();
		timer.schedule(new ExpireSessionsTask(), 0, 60000);
	}

	public void addSession(String username, String token)
	{
		sessions.put(token, new SessionInfo(username));
	}

	// TODO: Incoming calls need to check token status
	// TODO: Expired tokens will be removed from map (so simple checks on token validity)

	boolean isValid(String token)
	{
		// "A"
		SessionInfo info = sessions.get(token);

		if (info == null || info.lastAccessed < (System.currentTimeMillis() - age))
			return false;

		// Update timestamp
		info.lastAccessed = System.currentTimeMillis();

		// A final check just in-case the expiry thread removed the token since "A"
		return sessions.containsKey(token);
	}

	static class SessionInfo
	{
		String username;
		long lastAccessed;

		SessionInfo(String username)
		{
			this.username = username;
			update();
		}

		void update()
		{
			lastAccessed = System.currentTimeMillis();
		}
	}

	class ExpireSessionsTask extends TimerTask
	{
		public void run()
		{
			sessions.entrySet().removeIf(session -> session.getValue().lastAccessed < (System.currentTimeMillis() - age));
		}
	}
}