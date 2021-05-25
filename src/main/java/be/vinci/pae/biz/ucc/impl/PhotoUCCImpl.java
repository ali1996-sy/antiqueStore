package be.vinci.pae.biz.ucc.impl;

import java.util.Map;
import java.util.Set;

import be.vinci.pae.biz.dto.FurnitureDTO;
import be.vinci.pae.biz.dto.PhotoDTO;
import be.vinci.pae.biz.ucc.PhotoUCC;
import be.vinci.pae.dal.DalServices;
import be.vinci.pae.dal.dao.FurnitureDAO;
import be.vinci.pae.dal.dao.PhotoDAO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.FatalException;
import jakarta.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class PhotoUCCImpl.
 */
public class PhotoUCCImpl implements PhotoUCC {

  /** The photo DAO. */
  @Inject
  PhotoDAO photoDAO;

  /** The furniture dao. */
  @Inject
  FurnitureDAO furnitureDao;
  /** The dal service. */
  @Inject
  DalServices dalService;

  /**
   * Select all photo by furniture id.
   *
   * @param furnitureId the furniture id
   * @return the sets the
   */
  @Override
  public Set<PhotoDTO> selectAllPhotoByFurnitureId(int furnitureId) {
    dalService.startTransaction();
    Set<PhotoDTO> setPhoto = null;
    try {
      setPhoto = photoDAO.selectAllPhotoByFurnitureId(furnitureId);
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw new BizException(e.getMessage());
    }
    dalService.commitTransaction();
    return setPhoto;

  }

  /**
   * Select all photo by visit request id.
   *
   * @param visitRequestId the visit request id
   * @return the map
   */
  @Override
  public Map<PhotoDTO, FurnitureDTO> selectAllPhotoByVisitRequestId(int visitRequestId) {
    dalService.startTransaction();
    Map<PhotoDTO, FurnitureDTO> mapPhoto = null;
    try {
      mapPhoto = photoDAO.selectAllPhotoByVisitRequestId(visitRequestId);
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw new BizException(e.getMessage());
    }
    dalService.commitTransaction();
    return mapPhoto;

  }

  /**
   * Select all photo.
   *
   * @return the sets the
   */
  @Override
  public Set<PhotoDTO> selectAllPhoto() {
    dalService.startTransaction();
    Set<PhotoDTO> setPhoto = null;
    try {
      setPhoto = photoDAO.selectAllPhoto();
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw new BizException(e.getMessage());
    }
    dalService.commitTransaction();
    return setPhoto;

  }

  /**
   * Select photos by type.
   *
   * @param type the type
   * @return the sets the
   */
  @Override
  public Set<PhotoDTO> selectPhotosByType(String type) {
    dalService.startTransaction();
    Set<PhotoDTO> setPhoto = null;
    try {
      setPhoto = photoDAO.selectPhotosByType(type);
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw new BizException(e.getMessage());
    }
    dalService.commitTransaction();
    return setPhoto;

  }

  /**
   * Insert photo.
   *
   * @param photoText the photo text
   * @param furniture the furniture
   * @return the photo DTO
   */
  @Override
  public PhotoDTO insertPhoto(String photoText, int furniture) {

    dalService.startTransaction();
    PhotoDTO photo = null;
    try {
      photo = photoDAO.addPhotos(furniture, photoText);
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw new BizException(e.getMessage());
    }
    dalService.commitTransaction();
    return photo;

  }

  /**
   * get a photo.
   *
   * @param photoId the ID of the photo
   * @return the photo DTO
   */
  @Override
  public PhotoDTO selectAPhoto(int photoId) {
    dalService.startTransaction();
    PhotoDTO photo = null;
    try {
      photo = photoDAO.selectPhotoById(photoId);
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw new BizException(e.getMessage());
    }
    dalService.commitTransaction();
    return photo;
  }

}
