package be.vinci.pae.biz.impl;

import be.vinci.pae.biz.feature.Photo;

// TODO: Auto-generated Javadoc
/**
 * The Class PhotoImpl.
 */
public class PhotoImpl implements Photo {

  /** The photo id. */
  private int photoId;

  /** The photo. */
  private String photo;

  /** The furniture. */
  private int furniture;

  /** The furniture state. */
  private String furnitureState;

  /**
   * Gets the photo id.
   *
   * @return the photo id
   */
  public int getPhotoId() {
    return photoId;
  }

  /**
   * Sets the photo id.
   *
   * @param photoId the new photo id
   */
  public void setPhotoId(int photoId) {
    this.photoId = photoId;
  }

  /**
   * Gets the photo.
   *
   * @return the photo
   */
  public String getPhoto() {
    return photo;
  }

  /**
   * Sets the photo.
   *
   * @param photo the new photo
   */
  public void setPhoto(String photo) {
    this.photo = photo;
  }

  /**
   * Gets the furniture.
   *
   * @return the furniture
   */
  public int getFurniture() {
    return furniture;
  }

  /**
   * Sets the furniture.
   *
   * @param furniture the new furniture
   */
  public void setFurniture(int furniture) {
    this.furniture = furniture;
  }

  /**
   * Gets the furniture state.
   *
   * @return the furniture state
   */
  @Override
  public String getFurnitureState() {
    return furnitureState;
  }

  /**
   * Sets the furniture state.
   *
   * @param state the new furniture state
   */
  @Override
  public void setFurnitureState(String state) {
    this.furnitureState = state;

  }
}
