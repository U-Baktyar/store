package sat.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transaction;
import sat.configuration.annotation.Autowired;
import sat.configuration.annotation.Component;
import sat.dao.UserDAO;
import sat.entity.user.User;
import sat.exception.DataAccessException;
import sat.exception.UserNotFoundException;

@Component
public class UserDAOImpl implements UserDAO {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public User getByLogin(String login) throws DataAccessException, UserNotFoundException {
        try(
                EntityManager entityManager = entityManagerFactory.createEntityManager()
        ) {
            User user = entityManager
                    .createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();

            return user;

        } catch (NoResultException e){
            throw new UserNotFoundException("Пользователь с логином " + login + " не найден");
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void saveNewUser(User user) throws DataAccessException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {

            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();
                entityManager.persist(user);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new DataAccessException("Ошибка при сохранении пользователя " + e);
            }

        } catch (Exception e) {
            throw new DataAccessException("Не удалось создать EntityManager " + e.getMessage());
        }
    }

    @Override
    public void updateLastLogin(User user) {
    }
}
