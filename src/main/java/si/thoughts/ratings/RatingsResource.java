package si.thoughts.ratings;

import com.kumuluz.ee.logs.cdi.Log;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("ratings")
@Log
public class RatingsResource {
    @Inject
    private ConfigurationProperties cfg;

    @POST
    public Response addNewRating(@QueryParam("textId") int textId,
                                 @QueryParam("rating") int rating){
        try(
                Connection con = DriverManager.getConnection(cfg.getDbUrl(),cfg.getDbUser(),cfg.getDbPassword());
                Statement stmt = con.createStatement();
                ){
            stmt.executeUpdate("INSERT INTO rating_metadata (textId,rating,created)"
                + " VALUES ('" + textId + "', '" + rating + "', now())");
        }catch (SQLException e){
            System.err.println(e);
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok().build();
    }

    @GET
    public Response getRatings(){
        List<Rating> ratings = new LinkedList<Rating>();

        try(
            Connection con = DriverManager.getConnection(cfg.getDbUrl(),cfg.getDbUser(),cfg.getDbPassword());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM rating_metadata");
            ){
                while(rs.next()){
                    Rating rating = new Rating();
                    rating.setId(rs.getInt(1));
                    rating.setTextId(rs.getInt(2));
                    rating.setRating(rs.getInt(3));
                    rating.setCreated(rs.getTimestamp(4).toInstant());
                    ratings.add(rating);
                }
        }
        catch(SQLException e){
            System.err.println(e);
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok(ratings).build();
    }
}
