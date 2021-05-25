package be.vinci.pae.view.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Deque;
import java.util.HashSet;

import be.vinci.pae.biz.dto.AddressDTO;
import be.vinci.pae.biz.dto.FurnitureDTO;
import be.vinci.pae.biz.dto.PhotoDTO;
import be.vinci.pae.biz.dto.UserDTO;
import be.vinci.pae.biz.dto.VisitRequestDTO;
import be.vinci.pae.biz.factory.AddressFactory;
import be.vinci.pae.biz.factory.FurnitureFactory;
import be.vinci.pae.biz.factory.PhotoFactory;
import be.vinci.pae.biz.factory.UserFactory;
import be.vinci.pae.biz.factory.VisitRequestFactory;
import be.vinci.pae.biz.ucc.FurnitureUCC;
import be.vinci.pae.biz.ucc.PhotoUCC;
import be.vinci.pae.biz.ucc.UserUCC;
import be.vinci.pae.biz.ucc.VisitRequestUCC;
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
 * The Class VisitRequestRessourceImpl.
 */
@Singleton
@Path("/visitrequest")

public class VisitRequestRessourceImpl {



  /** The visit request UCC. */
  @Inject
  private VisitRequestUCC visitRequestUCC;

  /** The visitRequestFactory factory. */
  @Inject
  VisitRequestFactory visitRequestFactory;

  /** The address factory. */
  @Inject
  AddressFactory addressFactory;

  /** The photo factory. */
  @Inject
  PhotoFactory photoFactory;

  /** The furniture factory. */
  @Inject
  FurnitureFactory furnitureFactory;

  /** The user factory. */
  @Inject
  UserFactory userFactory;

  /** The user ucc. */
  @Inject
  UserUCC userUcc;

  /** The furniture ucc. */
  @Inject
  FurnitureUCC furnitureUcc;

  /** The photo ucc. */
  @Inject
  PhotoUCC photoUcc;

  /** The json mapper. */
  private final ObjectMapper jsonMapper = new ObjectMapper();

  /**
   * Change furniture state.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("createVisitRequest")
  @Authorize
  @Consumes(MediaType.APPLICATION_JSON)
  public Response creatVisitRequest(JsonNode json) {

    jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    VisitRequestDTO visitRequestDTO = visitRequestFactory.getVisitRequestDTO();
    String timeSlot = json.get("timeSlot").asText();
    visitRequestDTO.setTimeSlot(timeSlot);
    JsonNode furnitures = json.get("furnitures");
    int seller = json.get("seller").asInt();
    UserDTO userDto = userUcc.userById(seller);
    visitRequestDTO.setSeller(userDto);
    AddressDTO address;
    try {

      if (json.hasNonNull("address")) {
        address = jsonMapper.readValue(json.get("address").toPrettyString(), AddressDTO.class);
        address.setId(userUcc.addAddress(address));
        visitRequestDTO.setFurnitureAddress(address);
      } else {

        visitRequestDTO.setFurnitureAddress(userDto.getAddressDTO());
      }
    } catch (Exception e) {
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    int visitRequestId = visitRequestUCC.createVisitRequest(visitRequestDTO);
    for (JsonNode furniture : furnitures) {

      FurnitureDTO furnitureDTO = null;
      try {
        furnitureDTO = jsonMapper.readValue(furniture.toPrettyString(), FurnitureDTO.class);
        furnitureDTO.setRequestForVisit(visitRequestId);
        String photoText = furniture.get("photo").asText();
        int furnitureId = furnitureUcc.addFurniture(furnitureDTO);
        PhotoDTO photoDto = photoUcc.insertPhoto(photoText, furnitureId);
        furnitureDTO.setFurnitureId(furnitureId);
        furnitureDTO.setFavouritePhoto(photoDto.getPhotoId());
        furnitureUcc.editFurniture(furnitureDTO, new HashSet<PhotoDTO>());

      } catch (Exception e) {

        return Response.status(Status.INTERNAL_SERVER_ERROR).build();

      }

    }
    return Response.ok(jsonMapper.createObjectNode(), MediaType.APPLICATION_JSON).build();

  }

  /**
   * Cancel visit request.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("cancelVisitRequest")
  @Consumes(MediaType.APPLICATION_JSON)

  public Response cancelVisitRequest(JsonNode json) {

    if (!json.hasNonNull("visitId")) {
      return Response.status(Status.BAD_REQUEST)
          .entity("wrong furniture id or unauthorized state values").type(MediaType.TEXT_PLAIN)
          .build();
    }
    jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    VisitRequestDTO visitRequest = null;

    try {
      visitRequest = jsonMapper.readValue(json.toPrettyString(), VisitRequestDTO.class);
      visitRequestUCC.cancelVisitRequest(visitRequest);

      JsonNode furniture = json.get("furniture");
      for (JsonNode ele : furniture) {

        furnitureUcc.changeFurnitureState(ele.get("furnitureId").asInt(),
            FurnitureDTO.State.CANCELLED.getValue());
      }
      return Response.ok(jsonMapper.createObjectNode(), MediaType.APPLICATION_JSON).build();

    } catch (Exception e1) {
      return Response.status(Status.NOT_ACCEPTABLE).entity("VisitRequest is not Canceled")
          .type(MediaType.TEXT_PLAIN).build();

    }
  }

  /**
   * Confirm visit request.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("confirmVisitRequest")
  @Consumes(MediaType.APPLICATION_JSON)

  public Response confirmVisitRequest(JsonNode json) {

    if (!json.hasNonNull("visitId") && !json.hasNonNull("chosenDate")) {
      return Response.status(Status.BAD_REQUEST)
          .entity("wrong furniture id or unauthorized state values").type(MediaType.TEXT_PLAIN)
          .build();
    }
    jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    VisitRequestDTO visitRequest = null;

    try {
      visitRequest = jsonMapper.readValue(json.toPrettyString(), VisitRequestDTO.class);
      visitRequestUCC.confirmVisitRequest(visitRequest);
      return Response.ok(jsonMapper.createObjectNode(), MediaType.APPLICATION_JSON).build();

    } catch (Exception e1) {
      return Response.status(Status.NOT_ACCEPTABLE).entity("VisitRequest is not Confirm")
          .type(MediaType.TEXT_PLAIN).build();
    }

  }

  /**
   * Sellect all visit request.
   *
   * @return the response
   */
  @GET
  @Path("selectAllVisitRequest")

  public Response selectAllVisitRequest() {

    try {
      Deque<VisitRequestDTO> visitRequests = visitRequestUCC.selectAlltVisitRequest();
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("allVisitRequests", visitRequests);
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity("VisitRequest is not Sellected")
          .type(MediaType.TEXT_PLAIN).build();

    }

  }


  /**
   * Select visit request by user id.
   *
   * @param json the json
   * @return the response
   */
  @POST
  @Path("selectAllVisitRequestByUserId")
  @Consumes(MediaType.APPLICATION_JSON)

  public Response selectVisitRequestByUserId(JsonNode json) {

    try {
      Deque<VisitRequestDTO> visitRequests =
          visitRequestUCC.selectVisitRequestById(json.get("userId").asInt());
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("allVisitRequests", visitRequests);

      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {

      return Response.status(Status.NOT_ACCEPTABLE).entity("VisitRequest is not Sellected")
          .type(MediaType.TEXT_PLAIN).build();

    }

  }
}
