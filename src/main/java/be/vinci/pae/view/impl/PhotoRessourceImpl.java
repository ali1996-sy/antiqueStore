package be.vinci.pae.view.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map;
import java.util.Set;

import be.vinci.pae.biz.dto.FurnitureDTO;
import be.vinci.pae.biz.dto.PhotoDTO;
import be.vinci.pae.biz.ucc.PhotoUCC;
import be.vinci.pae.exception.BizException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;


// TODO: Auto-generated Javadoc
/**
 * The Class PhotoRessourceImpl.
 */
@Singleton
@Path("/photos")
public class PhotoRessourceImpl {

  /** The photo UCC. */
  @Inject
  private PhotoUCC photoUCC;
  /** The json mapper. */
  private final ObjectMapper jsonMapper = new ObjectMapper();

  /**
   * Gets the all photos.
   *
   * @param json the json
   * @return the all photos
   */
  @POST

  @Path("photosByFurnitureId")
  public Response getPhotosById(JsonNode json) {
    if (!json.hasNonNull("furnitureId")) {

      return Response.status(Status.BAD_REQUEST).entity("wrong id").type(MediaType.TEXT_PLAIN)
          .build();
    }
    try {
      Set<PhotoDTO> allPhotos =
          photoUCC.selectAllPhotoByFurnitureId(json.get("furnitureId").asInt());
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("allPhotos", allPhotos);
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }
  }

  /**
   * Gets the photos by visit request id.
   *
   * @param json the json
   * @return the photos by visit request id
   */
  @POST
  @Path("photosByVisitRequestId")
  public Response getPhotosByVisitRequestId(JsonNode json) {
    if (!json.hasNonNull("visitRequestId")) {

      return Response.status(Status.BAD_REQUEST).entity("wrong id").type(MediaType.TEXT_PLAIN)
          .build();
    }
    try {
      Map<PhotoDTO, FurnitureDTO> allPhotos =
          photoUCC.selectAllPhotoByVisitRequestId(json.get("visitRequestId").asInt());
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("allPhotos", allPhotos.keySet())
          .putPOJO("furniture", allPhotos.values());
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }
  }



  /**
   * Gets the all photos.
   *
   * @return the all photos
   */
  @GET
  @Path("allPhotos")
  public Response getAllPhotos() {
    try {
      Set<PhotoDTO> allPhotos = photoUCC.selectAllPhoto();
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("allPhotos", allPhotos);
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }
  }

  /**
   * Gets the photos by type.
   *
   * @param json the json
   * @return the photos by type
   */
  @POST
  @Path("photosByType")
  public Response getPhotosByType(JsonNode json) {
    if (!json.hasNonNull("type")) {

      return Response.status(Status.BAD_REQUEST).entity("wrong type").type(MediaType.TEXT_PLAIN)
          .build();
    }
    try {
      Set<PhotoDTO> allPhotos = photoUCC.selectPhotosByType(json.get("type").asText());
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("allPhotos", allPhotos);
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }
  }

  /**
   * Gets the photo.
   * 
   * @param json the json
   * @return a photo
   */
  @POST
  @Path("photoById")
  public Response getAPhoto(JsonNode json) {
    if (!json.hasNonNull("photoId")) {

      return Response.status(Status.BAD_REQUEST).entity("wrong id").type(MediaType.TEXT_PLAIN)
          .build();
    }
    int photoId = json.get("photoId").asInt();
    // System.out.println(photoId);
    try {
      PhotoDTO photo = photoUCC.selectAPhoto(photoId);
      ObjectNode node = jsonMapper.createObjectNode().putPOJO("photo", photo);
      return Response.ok(node, MediaType.APPLICATION_JSON).build();
    } catch (BizException e) {
      return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage())
          .type(MediaType.TEXT_PLAIN).build();
    }
  }

}
