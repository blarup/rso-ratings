package si.thoughts.ratings.api.v1.resources;

import si.thoughts.ratings.lib.RatingMetadata;
import si.thoughts.ratings.services.beans.RatingMetadataBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/ratings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RatingMetadataResource {

    @Inject
    private RatingMetadataBean ratingMetadataBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getRatingMetadata() {

        List<RatingMetadata> ratingMetadata = ratingMetadataBean.getRatingMetadataFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(ratingMetadata).build();
    }

    @POST
    public Response createRatingMetadata(RatingMetadata ratingMetadata) {

        if ((ratingMetadata.getRating() == null || ratingMetadata.getTextId() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            ratingMetadata = ratingMetadataBean.createRatingMetadata(ratingMetadata);
        }

        return Response.status(Response.Status.CONFLICT).entity(ratingMetadata).build();

    }
}
