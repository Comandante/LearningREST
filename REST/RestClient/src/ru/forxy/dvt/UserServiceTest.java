package ru.forxy.dvt;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import ru.forxy.service.IUserService;
import ru.forxy.pojo.User;

import java.util.List;

@ContextConfiguration(locations =
        {"classpath:ru/forxy/spring-context.xml"})
public class UserServiceTest extends AbstractJUnit4SpringContextTests {
    private static Logger LOGGER = Logger.getLogger(UserServiceTest.class);

    @Autowired
    @Qualifier("userServiceClient")
    private IUserService userService;

    @Test
    public void testGetAllUsers() {
        List<User> users = userService.getUsers();
        Assert.assertTrue(CollectionUtils.isNotEmpty(users));
        LOGGER.info("Users successfully retrieved " + users);
    }

    @Test
    public void addUser() {
        User user = userService.getUser(0);
        Assert.assertNull(user);
        LOGGER.info("User(0) not yet exists: " + user);
        User newUser = new User(0, "Xander");
        userService.addUser(newUser);
        user = userService.getUser(0);
        Assert.assertNotNull(user);
        LOGGER.info("User(0) successfully added: " + user);
        userService.deleteUser(0);
        user = userService.getUser(0);
        Assert.assertNull(user);
        LOGGER.info("User(0) successfully removed: " + user);
    }
}
