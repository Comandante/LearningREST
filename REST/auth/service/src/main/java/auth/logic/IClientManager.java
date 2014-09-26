package auth.logic;

import common.pojo.EntityPage;
import common.pojo.SortDirection;
import auth.rest.v1.pojo.Client;

/**
 * Entry point into auth service business logic
 */
public interface IClientManager {

    Iterable<Client> getAllClients();

    EntityPage<Client> getClients(final Integer page, final Integer size, final SortDirection sortDirection,
                                  final String sortedBy, final Client filter);

    Client getClient(final String clientID);

    void updateClient(final Client auth);

    void createClient(final Client auth);

    void deleteClient(final String clientID);
}
