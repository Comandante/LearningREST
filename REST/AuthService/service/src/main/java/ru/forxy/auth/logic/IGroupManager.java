package ru.forxy.auth.logic;

import ru.forxy.auth.rest.v1.pojo.Group;
import ru.forxy.common.pojo.EntityPage;
import ru.forxy.common.pojo.SortDirection;

import java.util.List;

/**
 * Entry point into auth service business logic
 */
public interface IGroupManager {

    Iterable<Group> getAllGroups();

    EntityPage<Group> getGroups(final Integer page, final Integer size, final SortDirection sortDirection,
                                  final String sortedBy, final Group filter);

    List<Group> getGroups(List<String> groupCodes);

    Group getGroup(final String groupCode);

    void updateGroup(final Group auth);

    void createGroup(final Group auth);

    void deleteGroup(final String groupCode);
}
