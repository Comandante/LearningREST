package auth.client.v1;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpGet;
import auth.api.v1.pojo.Client;
import common.exceptions.ClientException;
import common.exceptions.HttpEventLogId;
import common.pojo.EntityPage;
import common.pojo.StatusEntity;
import common.rest.client.RestServiceClientSupport;
import common.rest.client.transport.HttpClientSSLKeyStore;
import common.rest.client.transport.ITransport;
import common.rest.client.transport.support.ObjectMapperProvider;

import javax.ws.rs.core.HttpHeaders;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Client service client implementation
 */
public class AuthServiceClient extends RestServiceClientSupport implements IAuthServiceClient {
    protected static final String CLIENT_ID = "Client-ID";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    private static final String TRANSACTION_GUID_PARAM = "transactionGUID";
    private static final HttpClientSSLKeyStore TRUST_STORE;

    private String endpoint;

    private String clientId;

    static {
        try {
            final InputStream trustStoreStream = AuthServiceClient.class.getResourceAsStream("/cert/oauthTrustStore.jks");
            final byte[] trustStoreBytes = IOUtils.toByteArray(trustStoreStream);
            TRUST_STORE = new HttpClientSSLKeyStore(new ByteArrayInputStream(trustStoreBytes), "5ecret0AUTHPa55word");
        } catch (Exception e) {
            throw new ClientException(new StatusEntity("400", e), e, HttpEventLogId.InvalidClientInput);
        }
    }

    public AuthServiceClient() {
    }

    public AuthServiceClient(final String endpoint, final String clientId, final ITransport transport) {
        this.endpoint = endpoint;
        this.clientId = clientId;
        this.transport = transport;

        // @formatter:off
        mapper = ObjectMapperProvider.getMapper(
                ObjectMapperProvider.Config.newInstance().
                        withDateFormat(SIMPLE_DATE_FORMAT).
                        writeEmptyArrays(true).writeNullMapValues(true)
        );
        // @formatter:on

    }

    private Map<String, String> buildHeaders(final String transactionGUID, final String url, final String method)
            throws ClientException {
        validateGUID(TRANSACTION_GUID_PARAM, transactionGUID, true);

        final String txGUID = StringUtils.defaultIfEmpty(transactionGUID, UUID.randomUUID().toString());
        final Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, JSON_CONTENT_TYPE);
        headers.put(HttpHeaders.CONTENT_TYPE, JSON_CONTENT_TYPE);
        headers.put(TRANSACTION_GUID, txGUID);
        headers.put(CLIENT_ID, clientId);
        return headers;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<Client> getClients(String transactionGUID) {
        final String confUrl = endpoint + "clients";

        final ITransport.Response<List, StatusEntity> response =
                transport.performGet(confUrl, buildHeaders(transactionGUID, endpoint, HttpGet.METHOD_NAME),
                        createResponseHandler(List.class));
        return checkForError(response);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public EntityPage<Client> getClients(final String transactionGUID, final Integer page) {
        final String confUrl = endpoint + "clients?page=" + page;

        final ITransport.Response<EntityPage, StatusEntity> response =
                transport.performGet(confUrl, buildHeaders(transactionGUID, endpoint, HttpGet.METHOD_NAME),
                        createResponseHandler(EntityPage.class));
        return checkForError(response);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public EntityPage<Client> getClients(final String transactionGUID, Integer page, Integer size) {
        final String confUrl = endpoint + "clients?page=" + page + "&size=" + size;

        final ITransport.Response<EntityPage, StatusEntity> response =
                transport.performGet(confUrl, buildHeaders(transactionGUID, endpoint, HttpGet.METHOD_NAME),
                        createResponseHandler(EntityPage.class));
        return checkForError(response);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Client getClient(final String transactionGUID, String email) {
        final String confUrl = endpoint + "clients/" + email;

        final ITransport.Response<Client, StatusEntity> response =
                transport.performGet(confUrl, buildHeaders(transactionGUID, endpoint, HttpGet.METHOD_NAME),
                        createResponseHandler(Client.class));
        return checkForError(response);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public StatusEntity createClient(final String transactionGUID, final Client token) {
        final String confUrl = endpoint + "clients";

        final ITransport.Response<StatusEntity, StatusEntity> response =
                transport.performPost(confUrl, buildHeaders(transactionGUID, endpoint, HttpGet.METHOD_NAME),
                        marshal(token), createResponseHandler(StatusEntity.class));
        return checkForError(response);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public StatusEntity updateClient(final String transactionGUID, final Client token) {
        final String confUrl = endpoint + "clients";

        final ITransport.Response<StatusEntity, StatusEntity> response =
                transport.performPut(confUrl, buildHeaders(transactionGUID, endpoint, HttpGet.METHOD_NAME),
                        marshal(token), createResponseHandler(StatusEntity.class));
        return checkForError(response);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public StatusEntity deleteClient(String transactionGUID, String clientID) {
        final String confUrl = endpoint + "clients/" + clientID;

        final ITransport.Response<StatusEntity, StatusEntity> response =
                transport.performDelete(confUrl, buildHeaders(transactionGUID, endpoint, HttpGet.METHOD_NAME),
                        createResponseHandler(StatusEntity.class));
        return checkForError(response);
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
