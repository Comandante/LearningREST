package ru.forxy.auth.db.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.forxy.auth.pojo.Token;
import ru.forxy.common.status.ISystemStatusComponent;

/**
 * Data Access Object for Forxy database to manipulate tokens.
 */
public interface ITokenDAO extends PagingAndSortingRepository<Token, String>, ISystemStatusComponent {

    Page<Token> findAll(final Pageable pageable, final Token filter);
}

