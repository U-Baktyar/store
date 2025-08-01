package sat.service.impl;

import sat.configuration.annotation.Autowired;
import sat.configuration.annotation.Component;
import sat.dao.UserDAO;
import sat.entity.user.User;
import sat.exception.DataAccessException;
import sat.exception.UserNotFoundException;
import sat.service.UserService;

@Component
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean isUserExist(String login) {
        try {
            User user = this.getByLoginUser(login);
            return user != null;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    public User getByLoginUser(String login) throws UserNotFoundException {
        return userDAO.getByLogin(login);
    }

    @Override
    public void saveNewUser(User user) throws DataAccessException {
        userDAO.saveNewUser(user);
    }
}
