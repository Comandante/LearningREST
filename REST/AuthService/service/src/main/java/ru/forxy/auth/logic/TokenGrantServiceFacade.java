package ru.forxy.auth.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.forxy.auth.db.dao.ITokenDAO;
import ru.forxy.auth.exceptions.AuthServiceEventLogId;
import ru.forxy.auth.pojo.Token;
import ru.forxy.common.exceptions.ServiceException;
import ru.forxy.common.pojo.EntityPage;
import ru.forxy.common.pojo.SortDirection;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation class for AuthService business logic
 */
public class TokenGrantServiceFacade implements ITokenGrantServiceFacade {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private ITokenDAO tokenDAO;

    private PasswordEncoder passwordEncoder;

    public List<Token> getAllTokens() {
        List<Token> allTokens = new LinkedList<Token>();
        for (Token token : tokenDAO.findAll()) {
            allTokens.add(token);
        }
        return allTokens;
    }

    @Override
    public EntityPage<Token> getTokens(final Integer page, final Integer size, final SortDirection sortDirection,
                                       final String sortedBy, final Token filter) {
        if (page >= 1) {
            int pageSize = size == null ? DEFAULT_PAGE_SIZE : size;
            PageRequest pageRequest;
            if (sortDirection != null && sortedBy != null) {
                Sort.Direction dir = sortDirection == SortDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
                pageRequest = new PageRequest(page - 1, pageSize, dir, sortedBy);
            } else {
                pageRequest = new PageRequest(page - 1, pageSize);
            }
            final Page<Token> p = tokenDAO.findAll(pageRequest, filter);
            return new EntityPage<>(p.getContent(), p.getSize(), p.getNumber(), p.getTotalElements());
        } else {
            throw new ServiceException(AuthServiceEventLogId.InvalidPageNumber, page);
        }
    }

    @Override
    public Token getToken(final String email) {
        Token token = tokenDAO.findOne(email);
        if (token == null) {
            throw new ServiceException(AuthServiceEventLogId.TokenNotFound, email);
        }
        return token;
    }

    @Override
    public void updateToken(final Token token) {
        if (tokenDAO.exists(token.getTokenKey())) {
            tokenDAO.save(token);
        } else {
            throw new ServiceException(AuthServiceEventLogId.TokenNotFound, token.getTokenKey());
        }
    }

    @Override
    public void createToken(final Token token) {
        if (!tokenDAO.exists(token.getTokenKey())) {
            tokenDAO.save(token);
        } else {
            throw new ServiceException(AuthServiceEventLogId.TokenAlreadyExists, token.getTokenKey());
        }
    }

    @Override
    public void deleteToken(final String email) {
        if (tokenDAO.exists(email)) {
            tokenDAO.delete(email);
        } else {
            throw new ServiceException(AuthServiceEventLogId.TokenNotFound, email);
        }
    }

    public void setTokenDAO(final ITokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }

    public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
