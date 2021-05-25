package be.vinci.pae.view.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

import be.vinci.pae.biz.dto.OptionDTO;
import be.vinci.pae.biz.ucc.FurnitureUCC;
import be.vinci.pae.biz.ucc.OptionUCC;
import be.vinci.pae.biz.ucc.UserUCC;
import be.vinci.pae.exception.BizException;
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
 * The Class OptionRessourceImpl.
 */
@Singleton
@Path("/option")
public class OptionRessourceImpl {


  /** The option UCC. */
  @Inject
  private OptionUCC optionUCC;

  /** The furniture UCC. */
  @Inject
  private FurnitureUCC furnitureUCC;
  @Inject
  private UserUCC userrUCC;


  /** The json mapper. */
  private final ObjectMapper jsonMapper = new ObjectMapper();



  /**
   * Login.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("put")
  @Consumes(MediaType.APPLICATION_JSON)

  public Response putOption(JsonNode json) {

    int furnitureId = json.get("furniture").asInt();
    String state = json.get("furnitureState").asText();

    jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    try {
      OptionDTO optionDTO = jsonMapper.readValue(json.toPrettyString(), OptionDTO.class);
      if (optionDTO.getOptionTerm() == 0) {
        optionDTO.setOptionTerm(5);
      }
      if (optionUCC.putInOption(optionDTO)
          && furnitureUCC.changeFurnitureState(furnitureId, state)) {

        return Response.ok(MediaType.APPLICATION_JSON).build();

      }
    } catch (JsonProcessingException e) {
      throw new BizException(e.getMessage());
    }

    return Response.status(Status.NOT_FOUND).entity("Furniture or buyer not found")
        .type(MediaType.TEXT_PLAIN).build();
  }

  /**
   * Update option.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("update")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateOption(JsonNode json) {
    if (!json.hasNonNull("state") || !json.hasNonNull("optionId") || !json.hasNonNull("furniture")
        || !json.hasNonNull("furnitureState")) {
      return Response.status(Status.BAD_REQUEST).entity("wrong field").type(MediaType.TEXT_PLAIN)
          .build();
    }

    int optionId = json.get("optionId").asInt();
    String state = json.get("state").asText();
    int furnitureId = json.get("furniture").asInt();
    String furnitureState = json.get("furnitureState").asText();
    boolean updateOption = optionUCC.changeOptionState(optionId, state);
    boolean updateFurniture = furnitureUCC.changeFurnitureState(furnitureId, furnitureState);
    if (updateOption && updateFurniture) {
      return Response.ok(MediaType.APPLICATION_JSON).build();
    }

    return Response.status(Status.NOT_FOUND).entity("Furniture or buyer not found")
        .type(MediaType.TEXT_PLAIN).build();
  }

  /**
   * sale the furniture with a special price.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("saledWithSpecialPrice")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response saleWithAntiqueDealerPrice(JsonNode json) {
    if (!json.hasNonNull("optionState") || !json.hasNonNull("optionId")
        || !json.hasNonNull("furniture") || !json.hasNonNull("furnitureState")
        || !json.hasNonNull("antiquePrice")) {
      return Response.status(Status.BAD_REQUEST).entity("wrong field").type(MediaType.TEXT_PLAIN)
          .build();
    }

    int optionId = json.get("optionId").asInt();
    String state = json.get("optionState").asText();
    int furnitureId = json.get("furniture").asInt();
    String furnitureState = json.get("furnitureState").asText();
    double antiquePrice = json.get("antiquePrice").asDouble();
    boolean updateOption = optionUCC.changeOptionState(optionId, state);
    int userId = json.get("buyerId").asInt();
    boolean updateUser = userrUCC.updateBuyFurnitureNumber(userId);
    boolean updateFurniture = furnitureUCC.changeFurnitureStateWithAntiqueDealerPrice(furnitureId,
        furnitureState, antiquePrice);
    if (updateOption && updateFurniture && updateUser) {
      return Response.ok(MediaType.APPLICATION_JSON).build();
    }

    return Response.status(Status.NOT_FOUND).entity("Furniture or buyer not found")
        .type(MediaType.TEXT_PLAIN).build();
  }

  /**
   * All option.
   *
   * @return the response
   */
  @GET
  @Path("all")
  public Response allOption() {
    try {
      List<OptionDTO> allOptions = optionUCC.selectAllOption();
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("allOptions", allOptions);
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }

  }
}
