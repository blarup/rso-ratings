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

    @DELETE
    @Path("delete_ratings")
    public Response deleteRatings(@QueryParam("textId") int textId){
        try(
                Connection con = DriverManager.getConnection(cfg.getDbUrl(),cfg.getDbUser(),cfg.getDbPassword());
                Statement stmt = con.createStatement();
                ){
            stmt.executeUpdate("DELETE FROM ratings where textId = " + textId);
        }catch(SQLException e){
            System.err.println(e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.noContent().build();
    }

    @GET
    @Path("info")
    public Response getInfo(){
        String result = "Database url: " + cfg.getDbUrl() + " | "
                + "User: " + cfg.getDbUser() + " | " + "Password: " +cfg.getDbPassword();
        return Response.ok(result).build();
    }

    @GET
    @Path("average_rating")
    public Response getAverageRating(@QueryParam("textId") int textId){
        double result = Double.NaN;
        try(
                Connection con = DriverManager.getConnection(cfg.getDbUrl(),cfg.getDbUser(),cfg.getDbPassword());
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT avg(rating) FROM rating_metadata" +
                        " where textId = " + textId);
                ){
            while(rs.next()){
                double value = rs.getDouble(1);
                if(!rs.wasNull()){
                    result = value;
                }
            }
        }catch(SQLException e){
            System.err.println(e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(result).build();
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
