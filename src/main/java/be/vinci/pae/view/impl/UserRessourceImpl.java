package be.vinci.pae.view.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.glassfish.jersey.server.ContainerRequest;

import java.time.Instant;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import be.vinci.pae.biz.dto.AddressDTO;
import be.vinci.pae.biz.dto.UserDTO;
import be.vinci.pae.biz.factory.AddressFactory;
import be.vinci.pae.biz.factory.UserFactory;
import be.vinci.pae.biz.ucc.UserUCC;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.utils.Config;
import be.vinci.pae.view.filters.Authorize;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

// TODO: Auto-generated Javadoc
/**
 * The Class UserResource.
 */
@Singleton
@Path("/users")
public class UserRessourceImpl {



  /** The jwt algorithm. */
  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperties("JWTSecret"));

  /** The json mapper. */
  private final ObjectMapper jsonMapper = new ObjectMapper();

  /** The user ucc. */
  @Inject
  private UserUCC userUcc;

  /** The user factory. */
  @Inject
  UserFactory userFactory;

  /** The address factory. */
  @Inject
  AddressFactory addressFactory;

  /**
   * Gets the user.
   *
   * @param request the request
   * @return the user
   */
  @GET
  @Path("me")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public Response getUser(@Context ContainerRequest request) {


    String token = request.getHeaderString("authorization");
    int userId = (Integer) request.getProperty("user");
    try {
      UserDTO userDto = userUcc.userById(userId);

      if (userDto == null) {
        return Response.status(Status.UNAUTHORIZED).entity("connectez-vous ")
            .type(MediaType.TEXT_PLAIN).build();
      }
      ObjectNode node =
          jsonMapper.createObjectNode().put("token", token).putPOJO("user", userDto.getUsername());
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }

  }


  /**
   * Login.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response login(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("login") || !json.hasNonNull("password")) {

      return Response.status(Status.UNAUTHORIZED).entity("Login and password needed")
          .type(MediaType.TEXT_PLAIN).build();
    }

    String login = json.get("login").asText();
    String password = json.get("password").asText();

    try {
      UserDTO userDto = userUcc.login(login, password);

      if (userDto == null) {
        return Response.status(Status.UNAUTHORIZED)
            .entity("Login ou password ne sont pas  corrects ").type(MediaType.TEXT_PLAIN).build();
      }
      if (!userDto.isValidateRegistration()) {
        return Response.status(Status.UNAUTHORIZED)
            .entity("Connection impossible utilisateur non confirmé").type(MediaType.TEXT_PLAIN)
            .build();
      }
      // Create token
      String token;
      try {
        token =
            JWT.create().withIssuer("auth0").withClaim("administrator", userDto.isAdministrator())
                .withClaim("antiqueDealer", userDto.isAntiqueDealer())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(20000)))
                .withClaim("user", userDto.getId()).sign(this.jwtAlgorithm);
      } catch (Exception e) {
        throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR)
            .entity("Unable to create token").type("text/plain").build());
      }
      Map<String, Object> a = new HashMap<String, Object>();
      a.put("username", userDto.getUsername());
      a.put("id", userDto.getId());

      // Build response
      // A developper !!
      ObjectNode node = jsonMapper.createObjectNode().put("token", token).putPOJO("user", a);

      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (Exception e) {
      return Response.status(Status.NOT_ACCEPTABLE)
          .entity("Votre pseudo ou mot de passe  sont incorrects.").type(MediaType.TEXT_PLAIN)
          .build();
    }

  }

  /**
   * Register.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response register(JsonNode json) {
    jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    try {
      UserDTO user = jsonMapper.readValue(json.toPrettyString(), UserDTO.class);
      AddressDTO address = jsonMapper.readValue(json.toPrettyString(), AddressDTO.class);
      user.setAddressDTO(address);
      int userId = userUcc.register(user);
      if (userId <= -1) {
        return Response.status(Status.CONFLICT)
            .entity("La requete ne peut pas être exécuté dans l'état actuel")
            .type(MediaType.TEXT_PLAIN).build();
      }
      return Response.ok(jsonMapper.createObjectNode(), MediaType.APPLICATION_JSON).build();
    } catch (Exception e) {
      return Response.status(Status.BAD_REQUEST).entity("Erreur d'inscription")
          .type(MediaType.TEXT_PLAIN).build();
    }

  }

  /**
   * Validate registration.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("confirm_user")
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public Response validateRegistration(JsonNode json) {
    if (!json.hasNonNull("id") || !json.hasNonNull("validateRegistration")) {
      return Response.status(Status.BAD_REQUEST).entity("Empty field not accepted")
          .type(MediaType.TEXT_PLAIN).build();
    }

    int userId = Integer.parseInt(json.get("id").asText());
    boolean validateRegistrationBoxChecked =
        Boolean.parseBoolean(json.get("validateRegistration").asText());
    boolean updated = false;
    try {

      updated = userUcc.validateRegistration(userId, validateRegistrationBoxChecked);
      if (updated) {
        return Response.status(Status.OK).build();
      } else {
        return Response.status(Status.CONFLICT).entity("request not accepted")
            .type(MediaType.TEXT_PLAIN).build();
      }
    } catch (Exception e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }
  }

  /**
   * Gets the all user.
   *
   * @param request the request
   * @return the all user
   */
  @GET
  @Path("all")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public Response getAllUser(@Context ContainerRequest request) {
    try {
      Deque<UserDTO> setUserDto = userUcc.getAllUsers();
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("allUsers", setUserDto);
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (Exception e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }
  }



  /**
   * Update status.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("updateStatus")
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public Response updateStatus(JsonNode json) {
    if (!json.hasNonNull("userId") || !json.hasNonNull("status")) {
      return Response.status(Status.BAD_REQUEST).entity("Empty field not accepted")
          .type(MediaType.TEXT_PLAIN).build();
    }
    int userId = Integer.parseInt(json.get("userId").asText());
    String status = json.get("status").asText();

    try {
      boolean updated;
      if (status.equals("customer")) {
        updated = userUcc.updateAdmin(userId, false) && userUcc.updateAntiqueDealer(userId, false);

      } else if (status.toLowerCase().equals("is_administrator")) {
        updated = userUcc.updateAdmin(userId, true);
      } else {
        updated = userUcc.updateAntiqueDealer(userId, true);
      }


      if (updated) {
        return Response.status(Status.OK).entity("user status updated !").type(MediaType.TEXT_PLAIN)
            .build();
      } else {
        return Response.status(Status.CONFLICT).entity("request not accepted")
            .type(MediaType.TEXT_PLAIN).build();
      }
    } catch (Exception e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }
  }


  /**
   * Dynamic customer search.
   *
   * @param json the json
   * @return the list
   */
  @POST
  @Path("customerSearch")
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public Response dynamicCustomerSearch(JsonNode json) {
    if (!json.hasNonNull("ville") || !json.hasNonNull("customerName")
        || !json.hasNonNull("postCode")) {
      throw new IllegalArgumentException();
    }
    int postCode = json.get("postCode").asInt();
    String city = json.get("ville").asText();
    if (city.equals("empty")) {
      city = "";
    }
    String name = json.get("customerName").asText();
    if (name.equals("empty")) {
      name = "";
    }
    try {



      Set<UserDTO> userDTOSet = userUcc.dynamicCustomerSearch(name, postCode, city);

      if (userDTOSet == null) {
        return Response.status(Status.CONFLICT).entity("request not accepted")
            .type(MediaType.TEXT_PLAIN).build();
      } else {
        ObjectNode node = jsonMapper.createObjectNode().putPOJO("usersToSearch", userDTOSet);
        return Response.ok(node, MediaType.APPLICATION_JSON).build();
      }
    } catch (Exception e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }

  }
}

