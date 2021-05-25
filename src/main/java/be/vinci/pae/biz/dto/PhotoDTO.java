package be.vinci.pae.biz.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import be.vinci.pae.biz.impl.PhotoImpl;

// TODO: Auto-generated Javadoc
/**
 * The Interface PhotoDTO.
 */
@JsonDeserialize(as = PhotoImpl.class)

public interface PhotoDTO {

  /**
   * Gets the photo id.
   *
   * @return the photo id
   */
  int getPhotoId();

  /**
   * Gets the furniture state.
   *
   * @return the furniture state
   */
  String getFurnitureState();

  /**
   * Sets the furniture state.
   *
   * @param state the new furniture state
   */
  void setFurnitureState(String state);

  /**
   * Sets the photo id.
   *
   * @param photoId the new photo id
   */
  void setPhotoId(int photoId);

  /**
   * Gets the photo.
   *
   * @return the photo
   */
  String getPhoto();

  /**
   * Sets the photo.
   *
   * @param photo the new photo
   */
  void setPhoto(String photo);

  /**
   * Gets the furniture.
   *
   * @return the furniture
   */
  int getFurniture();

  /**
   * Sets the furniture.
   *
   * @param furniture the new furniture
   */
  void setFurniture(int furniture);

}
