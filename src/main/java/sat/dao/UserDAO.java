package sat.dao;

import sat.entity.user.User;
import sat.exception.UserNotFoundException;

public interface UserDAO {
    User getByLogin(String login) throws UserNotFoundException;
    void saveNewUser(User user);
    void updateLastLogin(User user);
}

