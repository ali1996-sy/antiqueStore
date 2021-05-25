package be.vinci.pae.biz.ucc;

import java.util.Map;
import java.util.Set;

import be.vinci.pae.biz.dto.FurnitureDTO;
import be.vinci.pae.biz.dto.PhotoDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface PhotoUCC.
 */
public interface PhotoUCC {

  /**
   * Select all photo by furniture id.
   *
   * @param furnitureId the furniture id
   * @return the sets the
   */
  Set<PhotoDTO> selectAllPhotoByFurnitureId(int furnitureId);


  /**
   * Select all photo.
   *
   * @return the sets the
   */
  Set<PhotoDTO> selectAllPhoto();


  /**
   * Insert photo.
   *
   * @param photoText the photo text
   * @param furniture the furniture
   * @return the photo DTO
   */
  PhotoDTO insertPhoto(String photoText, int furniture);


  /**
   * Select all photo by visit request id.
   *
   * @param visitRequestId the visit request id
   * @return the map
   */
  Map<PhotoDTO, FurnitureDTO> selectAllPhotoByVisitRequestId(int visitRequestId);


  /**
   * Select photos by type.
   *
   * @param type the type
   * @return the sets the
   */
  Set<PhotoDTO> selectPhotosByType(String type);

  /**
   * Select the a photo.
   *
   * @param photoId the ID of the photo
   * @return the photo DTO
   */
  PhotoDTO selectAPhoto(int photoId);

}
