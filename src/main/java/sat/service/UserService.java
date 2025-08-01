package sat.service;

import sat.entity.user.User;
import sat.exception.DataAccessException;
import sat.exception.UserNotFoundException;

public interface UserService {
    boolean isUserExist(String login);
    User getByLoginUser(String login) throws UserNotFoundException;
    void saveNewUser(User user) throws DataAccessException;
}
