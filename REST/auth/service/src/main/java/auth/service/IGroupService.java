package auth.service;

import auth.api.v1.pojo.Group;
import common.pojo.EntityPage;
import common.pojo.SortDirection;

import java.util.List;

/**
 * Entry point into auth service business logic
 */
public interface IGroupService {

    Iterable<Group> getAllGroups();

    EntityPage<Group> getGroups(final Integer page, final Integer size, final SortDirection sortDirection,
                                  final String sortedBy, final Group filter);

    List<Group> getGroups(List<String> groupCodes);

    Group getGroup(final String groupCode);

    void updateGroup(final Group auth);

    void createGroup(final Group auth);

    void deleteGroup(final String groupCode);
}
