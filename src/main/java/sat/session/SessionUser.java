package sat.session;

import sat.entity.user.User;

import java.util.UUID;

public interface SessionUser {
    User getAuthentication(UUID sessionID);

    void setAuthentication(UUID sessionID, User user);

    void removeAuthentication(UUID sessionID);

    boolean isAuthenticated(UUID sessionID);
}
