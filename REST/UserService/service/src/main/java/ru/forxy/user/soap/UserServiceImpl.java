package ru.forxy.user.soap;

import org.apache.commons.lang.exception.ExceptionUtils;
import ru.forxy.common.exceptions.ServiceException;
import ru.forxy.common.pojo.EntityPage;
import ru.forxy.common.support.SystemProperties;
import ru.forxy.user.logic.IUserServiceFacade;
import ru.forxy.user.rest.v1.pojo.User;
import ru.forxy.user.soap.gen.v1.DeleteUserRQType;
import ru.forxy.user.soap.gen.v1.ErrorListType;
import ru.forxy.user.soap.gen.v1.ErrorType;
import ru.forxy.user.soap.gen.v1.FaultType;
import ru.forxy.user.soap.gen.v1.GetUserRQType;
import ru.forxy.user.soap.gen.v1.GetUserRSType;
import ru.forxy.user.soap.gen.v1.GetUsersRQType;
import ru.forxy.user.soap.gen.v1.GetUsersRSType;
import ru.forxy.user.soap.gen.v1.MessageUserServiceFault;
import ru.forxy.user.soap.gen.v1.PageInfoType;
import ru.forxy.user.soap.gen.v1.RequestType;
import ru.forxy.user.soap.gen.v1.ResponseInfoType;
import ru.forxy.user.soap.gen.v1.ResponseStatusType;
import ru.forxy.user.soap.gen.v1.ResponseType;
import ru.forxy.user.soap.gen.v1.UserServicePortType;
import ru.forxy.user.soap.gen.v1.UserType;
import ru.forxy.user.soap.mapper.UserMapper;

import javax.jws.WebParam;

/**
 * SOAP implementation of UserService
 */
public class UserServiceImpl implements UserServicePortType {

    private static final ResponseStatusType OK;
    private static final ResponseStatusType FAIL;

    static {
        OK = new ResponseStatusType();
        OK.setStatusCode("0");
        OK.setStatusMessage("SUCCESS");

        FAIL = new ResponseStatusType();
        FAIL.setStatusCode("1");
        FAIL.setStatusMessage("FAIL");
    }

    private IUserServiceFacade userServiceFacade;

    @Override
    public GetUsersRSType getUsers(@WebParam(name = "GetUsersRQ",
            targetNamespace = "urn:ru:forxy:user:messages:v1",
            partName = "getUsersRequest") final GetUsersRQType getUsersRequest) throws MessageUserServiceFault {

        final PageInfoType pageInfo = getUsersRequest.getPageInfo();
        final GetUsersRSType response = buildResponse(new GetUsersRSType(), getUsersRequest);

        try {

            final EntityPage<User> page = userServiceFacade.getUsers(pageInfo.getNumber(), pageInfo.getSize(),
                    null, null, null);
            response.setPage(UserMapper.fromUserPage(page));

        } catch (ServiceException e) {
            throw new MessageUserServiceFault("Could not find user", buildFault(e), e);
        }
        return response;
    }

    @Override
    public GetUserRSType getUser(@WebParam(name = "GetUserRQ",
            targetNamespace = "urn:ru:forxy:user:messages:v1",
            partName = "getUserRequest") final GetUserRQType getUserRequest) throws MessageUserServiceFault {

        final UserType userType = getUserRequest.getUser();
        final GetUserRSType response = buildResponse(new GetUserRSType(), getUserRequest);

        try {

            User user = userServiceFacade.getUser(userType.getEmail());
            response.setUser(UserMapper.fromUser(user));

        } catch (ServiceException e) {
            throw new MessageUserServiceFault("Could not find user", buildFault(e), e);
        }
        return response;
    }

    @Override
    public ResponseType deleteUser(@WebParam(name = "DeleteUserRQ",
            targetNamespace = "urn:ru:forxy:user:messages:v1",
            partName = "deleteUserRequest") final DeleteUserRQType deleteUserRequest) throws MessageUserServiceFault {

        final String email = deleteUserRequest.getEmail();

        try {

            userServiceFacade.deleteUser(email);

        } catch (ServiceException e) {
            throw new MessageUserServiceFault("Could not delete user with email = " + email, buildFault(e), e);
        }
        return buildResponse(new ResponseType(), deleteUserRequest);
    }

    private static <T extends ResponseType> T buildResponse(final T response, final RequestType request) {
        if (request == null || response == null) {
            return null;
        }
        response.setResponseInfo(new ResponseInfoType());
        response.getResponseInfo().setRequestInfo(request.getRequestInfo());
        response.getResponseInfo().setResponder(SystemProperties.getHostAddress());
        response.getResponseInfo().setResponseStatus(OK);
        return response;
    }

    private static FaultType buildFault(final Throwable t) {
        final FaultType fault = new FaultType();
        fault.setErrorList(new ErrorListType());
        final ErrorType error = new ErrorType();
        error.setErrorMessage(t.getMessage());
        error.setErrorType("InternalError");
        error.setStackTrace(ExceptionUtils.getFullStackTrace(t));
        fault.getErrorList().getError().add(error);
        return fault;
    }

    public void setUserServiceFacade(final IUserServiceFacade userServiceFacade) {
        this.userServiceFacade = userServiceFacade;
    }
}
