package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.model.*;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@DeclareRoles({"admin", "user"})

@PermitAll
@Path("shopper")
public class PersonResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List <Shopper> getShoppers() {
        return Shopper.getAllShoppers();
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShoppingListsFromPerson(@PathParam("name") String name) {
        Shop shop = Shop.getShop();
        List<ShoppingList> allListsFromPerson = shop.getListFromPerson(name); //warning: might return null!
        return Response.ok(allListsFromPerson).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addShopper(ShopperRequest shopperRequest) {

        Shopper nieuweShopper = new Shopper(shopperRequest.getName());

        return Response.ok(nieuweShopper).build();
    }

    @RolesAllowed("admin")
    @DELETE
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteShopper(@PathParam("name") String name) {
        return Shopper.removeShopper(name)
                ? Response.ok().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
