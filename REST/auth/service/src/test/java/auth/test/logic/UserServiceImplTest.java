package auth.test.logic;

import org.apache.commons.collections.CollectionUtils;
import org.apache.cxf.jaxrs.impl.HttpHeadersImpl;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import auth.db.dao.IUserDAO;
import auth.exceptions.AuthServiceEventLogId;
import auth.controller.v1.UserController;
import auth.api.v1.pojo.User;
import auth.test.BaseUserServiceTest;
import common.exceptions.ServiceException;
import common.pojo.EntityPage;
import common.pojo.SortDirection;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImplTest extends BaseUserServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplTest.class);

    private static final String TEST_USER_EMAIL = "kast.askadias@gmail.com";

    @Autowired
    UserController userService;

    @Autowired
    @Qualifier("userDAO.impl.mongo")
    IUserDAO userDAOMock;

    @Test
    public void testAddDeleteUser() {

        Message m = new MessageImpl();
        UriInfo uriInfo = new UriInfoImpl(m);
        UriInfo uriInfoMock = EasyMock.createMock(UriInfo.class);
        URI uri = UriBuilder.fromPath("http://auth.com/").build();


        HttpHeaders headers = new HttpHeadersImpl(m);

        User testUser = new User(TEST_USER_EMAIL, "");

        // Mock setup
        EasyMock.expect(uriInfoMock.getAbsolutePath()).andReturn(uri);
        EasyMock.expect(userDAOMock.exists(TEST_USER_EMAIL)).andReturn(false);
        EasyMock.expect(userDAOMock.save(testUser)).andReturn(testUser);
        EasyMock.expect(userDAOMock.findOne(TEST_USER_EMAIL)).andReturn(testUser);
        EasyMock.expect(userDAOMock.exists(TEST_USER_EMAIL)).andReturn(true);
        EasyMock.expect(userDAOMock.findOne(TEST_USER_EMAIL)).andReturn(null);
        try {
            EasyMock.replay(userDAOMock, uriInfoMock);

            // Create user
            userService.createUser(testUser, uriInfoMock, headers);

            // Login user
            Response response = userService.getUser(testUser.getEmail(), uriInfo, headers);
            Assert.assertNotNull(response);
            Assert.assertNotNull(response.getEntity());
            User user = (User) response.getEntity();//response.readEntity(User.class);
            Assert.assertEquals(TEST_USER_EMAIL, user.getEmail());

            // Remove user
            deleteUser(testUser, uriInfo, headers);

            // Check user has been removed
            try {
                userService.getUser(testUser.getEmail(), uriInfo, headers);
                Assert.fail();
            } catch (ServiceException e) {
                Assert.assertEquals(AuthServiceEventLogId.UserNotFound, e.getEventLogId());
                LOGGER.info("User {} has been successfully deleted.", TEST_USER_EMAIL);
            }
        } finally {
            EasyMock.reset(userDAOMock, uriInfoMock);
        }
    }

    private void deleteUser(User user, UriInfo uriInfo, HttpHeaders headers) {
        // Remove user
        userService.deleteUser(user.getEmail(), uriInfo, headers);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetAllUsers() {
        Message m = new MessageImpl();
        UriInfo uriInfo = new UriInfoImpl(m);
        HttpHeaders headers = new HttpHeadersImpl(m);

        List<User> users = new ArrayList<User>();
        users.add(new User(TEST_USER_EMAIL, ""));
        EasyMock.expect(userDAOMock.findAll(EasyMock.anyObject(Pageable.class), EasyMock.anyObject(User.class)))
                .andReturn(new PageImpl<User>(users));
        EasyMock.replay(userDAOMock);
        Response response = userService.getUsers(1, 10, SortDirection.ASC, "email", null, null, null, null, null,
                uriInfo, headers);
        EasyMock.reset(userDAOMock);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getEntity());


        EntityPage<User> userPage = (EntityPage<User>) response.getEntity();
                /*response.readEntity(new GenericType<EntityPage<User>>() {
        });*/
        Assert.assertTrue(CollectionUtils.isNotEmpty(userPage.getContent()));
    }
}
