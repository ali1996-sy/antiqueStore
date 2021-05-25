package be.vinci.pae.view.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.vinci.pae.biz.dto.FurnitureDTO;
import be.vinci.pae.biz.dto.PhotoDTO;
import be.vinci.pae.biz.factory.FurnitureFactory;
import be.vinci.pae.biz.factory.PhotoFactory;
import be.vinci.pae.biz.ucc.FurnitureUCC;
import be.vinci.pae.biz.ucc.PhotoUCC;
import be.vinci.pae.biz.ucc.UserUCC;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.view.filters.Authorize;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

// TODO: Auto-generated Javadoc
/**
 * The Class UserResource.
 */
@Singleton
@Path("/furniture")
public class FurnitureRessourceImpl {

  /** The furniture UCC. */
  @Inject
  private FurnitureUCC furnitureUCC;

  /** The photo UCC. */
  @Inject
  private PhotoUCC photoUCC;

  /** The user UCC. */
  @Inject
  private UserUCC userUCC;
  /** The furniture factory. */
  @Inject
  FurnitureFactory furnitureFactory;

  /** The photo factory. */
  @Inject
  PhotoFactory photoFactory;
  /** The json mapper. */
  private final ObjectMapper jsonMapper = new ObjectMapper();

  /**
   * Change furniture state.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("changeFurnitureState")
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public Response changeFurnitureState(JsonNode json) {

    if (!json.hasNonNull("furnitureId") || !json.hasNonNull("state")
        || !json.hasNonNull("userId")) {
      return Response.status(Status.BAD_REQUEST).entity("wrong field").type(MediaType.TEXT_PLAIN)
          .build();
    }

    int furnitureId = json.get("furnitureId").asInt();

    String state = json.get("state").asText();

    if (furnitureId < 1 || state.isEmpty()) {
      return Response.status(Status.BAD_REQUEST)
          .entity("wrong furniture id or unauthorized state values").type(MediaType.TEXT_PLAIN)
          .build();
    } else {
      try {
        if (furnitureUCC.changeFurnitureState(furnitureId, state)) {
          return Response.ok(MediaType.APPLICATION_JSON).build();
        } else {
          return Response.status(Status.NOT_ACCEPTABLE).entity("furniture state is not updated")
              .type(MediaType.TEXT_PLAIN).build();
        }
      } catch (BizException e) {
        return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
            .type(MediaType.TEXT_PLAIN).build();
      }
    }
  }

  /**
   * sale furniture.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("saleFurniture")
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public Response saleFurniture(JsonNode json) {

    if (!json.hasNonNull("furnitureId") || !json.hasNonNull("state") || !json.hasNonNull("userId")
        || !json.hasNonNull("price")) {
      return Response.status(Status.BAD_REQUEST).entity("wrong field").type(MediaType.TEXT_PLAIN)
          .build();
    }

    int furnitureId = json.get("furnitureId").asInt();

    String state = json.get("state").asText();
    int userId = json.get("userId").asInt();
    double price = json.get("price").asInt();

    if (furnitureId < 1 || state.isEmpty() || price <= 0 || userId <= 0) {
      return Response.status(Status.BAD_REQUEST).entity("wrong field values")
          .type(MediaType.TEXT_PLAIN).build();
    } else {
      try {
        if (furnitureUCC.saleFurniture(furnitureId, state, userId, price)
            && userUCC.updateBuyFurnitureNumber(userId)) {
          return Response.ok(MediaType.APPLICATION_JSON).build();
        } else {
          return Response.status(Status.NOT_ACCEPTABLE).entity("furniture state is not updated")
              .type(MediaType.TEXT_PLAIN).build();
        }
      } catch (BizException e) {
        return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
            .type(MediaType.TEXT_PLAIN).build();
      }
    }
  }

  /**
   * Modify furniture.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("modifyFurniture")
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public Response modifyFurniture(JsonNode json) {

    jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    try {
      FurnitureDTO furniture = jsonMapper.readValue(json.toPrettyString(), FurnitureDTO.class);
      if (json.get("favoritePhoto").get("photoId").asInt() < 0) {
        int id = photoUCC.insertPhoto(json.get("favoritePhoto").get("photo").asText(),
            furniture.getFurnitureId()).getPhotoId();
        furniture.setFavouritePhoto(id);
      } else {
        furniture.setFavouritePhoto(json.get("favoritePhoto").get("photoId").asInt());
      }
      Set<PhotoDTO> photos = new HashSet<PhotoDTO>();
      for (int i = 0; i < json.get("images").size(); i++) {
        PhotoDTO photo = jsonMapper.convertValue(json.get("images").get(i), PhotoDTO.class);
        photos.add(photo);

      }
      if (furniture.getState().equals(FurnitureDTO.State.DEPOSITED.getValue())) {
        furniture.setDepositDate(Date.valueOf(LocalDate.now()));
      }
      if (furnitureUCC.editFurniture(furniture, photos)) {
        furniture = furnitureUCC.selectFurnitureById(furniture.getFurnitureId());
        ObjectNode node = jsonMapper.createObjectNode().putPOJO("furniutre", furniture);
        return Response.ok(node, MediaType.APPLICATION_JSON).build();
      }
    } catch (JsonProcessingException e) {

      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    } catch (BizException e) {

      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }

    return Response.status(Status.BAD_REQUEST).entity("Meuble  non modifié")
        .type(MediaType.TEXT_PLAIN).build();
  }

  /**
   * Modify furniture by visit request.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("modifyFurnitureByVisitRequest")
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public Response modifyFurnitureByVisitRequest(JsonNode json) {
    jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    try {

      FurnitureDTO furniture = jsonMapper.readValue(json.toPrettyString(), FurnitureDTO.class);
      Set<PhotoDTO> photos = new HashSet<PhotoDTO>();
      int sellerId = json.get("sellerId").asInt();
      if (json.get("annuler").asBoolean()) {
        furnitureUCC.changeFurnitureState(furniture.getFurnitureId(),
            FurnitureDTO.State.CANCELLED.getValue());
        furniture = furnitureUCC.selectFurnitureById(furniture.getFurnitureId());
        ObjectNode node = jsonMapper.createObjectNode().putPOJO("furniutre", furniture);
        return Response.ok(node, MediaType.APPLICATION_JSON).build();
      } else if (furnitureUCC.editFurniture(furniture, photos)
          && furnitureUCC.changeFurnitureState(furniture.getFurnitureId(),
              FurnitureDTO.State.PURCHASED.getValue())
          && userUCC.updateSellFurnitureNumber(sellerId)) {
        furniture = furnitureUCC.selectFurnitureById(furniture.getFurnitureId());
        ObjectNode node = jsonMapper.createObjectNode().putPOJO("furniutre", furniture);
        return Response.ok(node, MediaType.APPLICATION_JSON).build();
      }
    } catch (JsonProcessingException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    } catch (Exception e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();

    }
    return Response.status(Status.BAD_REQUEST).entity("Meuble  non modifié")
        .type(MediaType.TEXT_PLAIN).build();
  }

  /**
   * Gets the all furniture.
   *
   * @return the all furniture
   */
  @GET
  @Path("allFurnitures")
  @Authorize
  public Response getAllFurniture() {
    try {
      Deque<FurnitureDTO> allFurnitures = furnitureUCC.selectAllFurniture();
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("allFurnitures", allFurnitures);
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }

  }



  /**
   * Gets the all furniture forSale.
   *
   * @return the all furniture forSale
   */
  @GET
  @Path("allForSaleFurniture")
  @Authorize
  public Response getAllFurnitureForSale() {
    try {
      List<FurnitureDTO> allFurnitures = furnitureUCC.selectAllFurnitureForSale();
      ObjectNode node =
          jsonMapper.createObjectNode().putPOJO("allFurnituresForSale", allFurnitures);
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }

  }

  /**
   * Gets the all types.
   *
   * @return the all types
   */
  @GET
  @Path("types")
  @Authorize
  public Response getAllTypes() {
    try {
      Deque<String> allTypes = furnitureUCC.selectAllTypes();
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("get", allTypes);
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }

  }

  /**
   * Gets the all furniture by state.
   *
   * @param json the json
   * @return the all furniture by state
   */
  @POST
  @Path("allFurnitureByState")
  @Authorize
  public Response getAllFurnitureByState(JsonNode json) {
    if (!json.hasNonNull("state")) {
      return null;
    }
    try {
      String furnitureState = json.get("state").asText();
      Deque<FurnitureDTO> furnitures = furnitureUCC.selectAllFurnitureByState(furnitureState);
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("allFurnitures", furnitures);
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }
  }

  /**
   * Dynamic furnitureearch.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("furnitureSearch")
  @Authorize
  public Response dynamicFurnitureearch(JsonNode json) {
    if (!json.hasNonNull("customer") && !json.hasNonNull("max") && !json.hasNonNull("min")) {
      return null;
    }
    try {
      String customer = json.get("customer").asText();
      int max = json.get("max").asInt();
      int min = json.get("min").asInt();
      JsonNode typesNodes = json.get("types");
      HashSet<String> types = new HashSet<String>();
      for (int i = 0; i < typesNodes.size(); i++) {
        types.add(typesNodes.get(i).asText());
      }
      Deque<FurnitureDTO> furnitures =
          furnitureUCC.dynamicFurnitureSearch(customer, min, max, types);
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("allFurnitures", furnitures);
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }
  }

  /**
   * Gets the furniture by id.
   *
   * @param json the json
   * @return the furniture by id
   */
  @POST
  @Path("furnitureId")
  @Authorize
  public Response getFurnitureById(JsonNode json) {
    try {
      if (!json.hasNonNull("furnitureId")) {
        return null;
      }
      int furnitureId = json.get("furnitureId").asInt();
      if (furnitureId < 0) {
        return null;
      }
      FurnitureDTO furniture = furnitureUCC.selectFurnitureById(furnitureId);
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("furniture", furniture);
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }
  }

  /**
   * Gets the furniture by user id.
   *
   * @param json the json
   * @return the furniture by user id
   */
  @POST
  @Path("userId")
  @Authorize
  public Response getFurnitureByUserId(JsonNode json) {
    try {
      if (!json.hasNonNull("userId")) {
        return null;
      }
      int userId = json.get("userId").asInt();
      if (userId < 0) {
        return null;
      }
      Deque<FurnitureDTO> allFurnitures = furnitureUCC.selectFurnitureByUserId(userId);
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("allFurnitures", allFurnitures);
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }
  }


}
