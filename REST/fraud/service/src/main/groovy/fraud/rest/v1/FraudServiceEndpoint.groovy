package fraud.rest.v1

import common.pojo.SortDirection
import common.rest.AbstractService
import fraud.logic.IFraudServiceFacade
import fraud.rest.v1.check.Transaction

import javax.ws.rs.*
import javax.ws.rs.core.*

@Path('/check')
@Produces(MediaType.APPLICATION_JSON)
class FraudServiceEndpoint extends AbstractService {

    IFraudServiceFacade fraudServiceFacade

    @GET
    Response check(final Transaction transaction, final UriInfo uriInfo, final HttpHeaders headers) {
        Response.ok(fraudServiceFacade.check(transaction)).build()
    }

    @GET
    @Path('/transactions/')
    Response getTransactions(@QueryParam('page') final Integer page,
                             @QueryParam('size') final Integer size,
                             @QueryParam('sort_dir') final SortDirection sortDirection,
                             @QueryParam('sorted_by') final String sortedBy,
                             @QueryParam('transaction_id') final String idFilter,
                             @Context final UriInfo uriInfo,
                             @Context final HttpHeaders headers) {
        respondWith(page == null && size == null ?
                fraudServiceFacade.getAllTransactions() :
                fraudServiceFacade.getTransactions(page, size, sortDirection, sortedBy,
                        new Transaction()),
                uriInfo, headers).build()
    }

    @GET
    @Path('/transactions/{transaction_id}/')
    Response getTransaction(@PathParam('transaction_id') final String transactionID,
                            @Context final UriInfo uriInfo,
                            @Context final HttpHeaders headers) {
        respondWith(fraudServiceFacade.getTransaction(transactionID), uriInfo, headers).build()
    }
}