package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.model.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("product")
public class ProductResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getProducts() {
        Shop shop = Shop.getShop();
        List<Product> products = shop.getAllProducts();
        return Response.ok(products).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public Response addProduct(ProductRequest productRequest) {

        // Roep constructor aan van Product anders komt ie niet in de lijst met producten
        Product nieuwProduct = new Product(productRequest.getName());
        ShoppingList list = Shop.getShop().getShoppingListByName(productRequest.getListName());

        return Response.ok(list.addItem(nieuwProduct, productRequest.getAmount())).build();
    }
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public Response changeProduct(ChangeProductRequest changeProductRequest) {
        Shop shop = Shop.getShop();
        for (Product item : shop.getAllProducts()){
            if (item.getName().equals(changeProductRequest.getName())){
                item.newName(changeProductRequest.getNewName());
                return Response.ok(item).build();
            }
        }
    return Response.serverError().build();
    }
}

