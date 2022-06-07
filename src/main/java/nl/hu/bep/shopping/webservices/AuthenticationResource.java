package nl.hu.bep.shopping.webservices;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nl.hu.bep.shopping.model.LogonRequest;
import nl.hu.bep.shopping.model.MyUser;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.security.Key;
import java.util.*;

@Path("authenticate")
public class AuthenticationResource {

    final static public Key key = MacProvider.generateKey();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(LogonRequest logonRequest) {
        try {
            String role = MyUser.validateLogin(logonRequest.username, logonRequest.password);
            if (role == null) throw new IllegalArgumentException("no user found");

            String token = createToken(logonRequest.username, role);

            return Response.ok(new AbstractMap.SimpleEntry<>("JWT", token)).build();
        } catch (JwtException | IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

    public String createToken(String username, String role) throws JwtException {
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 30);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration.getTime())
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}
