package ru.forxy.auth.db.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.forxy.common.status.ISystemStatusComponent;
import ru.forxy.auth.pojo.Client;

/**
 * Data Access Object for Forxy database to manipulate Clients.
 */
public interface IClientDAO extends PagingAndSortingRepository<Client, String>, ISystemStatusComponent {

    Page<Client> findAll(final Pageable pageable, final Client filter);
}
