package ru.forxy.db.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.forxy.common.status.ISystemStatusComponent;
import ru.forxy.user.pojo.User;

import java.util.List;

/**
 * Data Access Object for User database to manipulate Users.
 */
public interface IUserDAO extends PagingAndSortingRepository<User, String>, ISystemStatusComponent {

    List<User> findByLastName(String LastName);

    List<User> findByFirstName(String firstName);
}
