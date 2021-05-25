package be.vinci.pae.dal.dao;

import java.util.Map;
import java.util.Set;

import be.vinci.pae.biz.dto.FurnitureDTO;
import be.vinci.pae.biz.dto.PhotoDTO;


// TODO: Auto-generated Javadoc
/**
 * The Interface PhotoDAO.
 */
public interface PhotoDAO {

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
   * Adds the photos.
   *
   * @param furniture the furniture
   * @param photo the photo
   * @return the int
   */
  PhotoDTO addPhotos(int furniture, String photo);

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
   * Select a photo.
   *
   * @param photoId the ID of the photo
   * @return the PhotoDTO
   */
  PhotoDTO selectPhotoById(int photoId);


}
