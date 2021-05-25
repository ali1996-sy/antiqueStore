package be.vinci.pae.biz.factory.impl;

import be.vinci.pae.biz.dto.PhotoDTO;
import be.vinci.pae.biz.factory.PhotoFactory;
import be.vinci.pae.biz.impl.PhotoImpl;


/**
 * The Class PhotoFactoryImpl.
 */
public class PhotoFactoryImpl implements PhotoFactory {

  /**
   * Gets the photo DTO.
   *
   * @return the photo DTO
   */
  @Override
  public PhotoDTO getPhotoDTO() {
    return new PhotoImpl();
  }

}
