package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.model.*;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("list")
public class ListResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShoppingLists() {
        Shop deShop = Shop.getShop();
        List<ShoppingList> shoppinglists = deShop.getAllShoppingLists();
        return Response.ok(shoppinglists).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}")
    public Response getShoppingListByName(@PathParam("name") String name) {
        Shop shop = Shop.getShop();
        ShoppingList shoppinglist = shop.getShoppingListByName(name);

        if (shoppinglist == null) {
            Map<String, String> messages = new HashMap<>();
            messages.put("error", "No list by that name");
            return Response.status(Status.NOT_FOUND).entity(messages).build();
        }
        return Response.ok(shoppinglist).build();

    }

    @POST
    @Path("addlist")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewList(ShoppingListRequest shoppingListRequest) {
        Shop shop = Shop.getShop();
        for (Shopper shopper : shop.getAllPersons())
            if (shoppingListRequest.getOwner().equals(shopper.getName())) {
                ShoppingList nieuweList = new ShoppingList(shoppingListRequest.getName(), shopper);
                shopper.addList(nieuweList);
                return Response.ok(nieuweList).build();
            }
        return Response.serverError().build();
    }

    @PUT
    @Path("/clear/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response resetList(@PathParam("name") String name) {

        ShoppingList shoppingList = Shop.getShop().getShoppingListByName(name);
        shoppingList.listReset(name);
        return Response.ok(Shop.getShop().getShoppingListByName(name)).build();
    }
}
