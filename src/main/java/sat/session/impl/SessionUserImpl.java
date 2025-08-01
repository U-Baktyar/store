package sat.session.impl;

import sat.configuration.annotation.Autowired;
import sat.configuration.annotation.Component;
import sat.entity.user.User;
import sat.session.SessionUser;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionUserImpl implements SessionUser {
    public final Map<UUID, User> users;



    @Autowired
    public SessionUserImpl() {
        this.users = new ConcurrentHashMap<>();
    }

    @Override
    public User getAuthentication(UUID sessionID) {
        return users.get(sessionID);
    }

    @Override
    public void setAuthentication(UUID sessionID, User user) {
        users.put(sessionID, user);
    }

    @Override
    public void removeAuthentication(UUID sessionID) {
        users.remove(sessionID);
    }

    @Override
    public boolean isAuthenticated(UUID sessionID) {
        return users.containsKey(sessionID);
    }
}
