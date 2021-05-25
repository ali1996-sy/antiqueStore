package be.vinci.pae.biz.ucc.impl;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Set;

import be.vinci.pae.biz.dto.FurnitureDTO;
import be.vinci.pae.biz.dto.FurnitureDTO.State;
import be.vinci.pae.biz.dto.PhotoDTO;
import be.vinci.pae.biz.ucc.FurnitureUCC;
import be.vinci.pae.dal.DalServices;
import be.vinci.pae.dal.dao.FurnitureDAO;
import be.vinci.pae.dal.dao.PhotoDAO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.FatalException;
import jakarta.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class FurnitureUCCImpl.
 */
public class FurnitureUCCImpl implements FurnitureUCC {
  /** The user DAO. */
  @Inject
  private FurnitureDAO furnitureDAO;

  /** The photo DAO. */
  @Inject
  private PhotoDAO photoDAO;

  /** The dal service. */
  @Inject
  DalServices dalService;

  /**
   * Edits the furniture.
   *
   * @param furnitureEdit the furniture edit
   * @param photos the photos
   * @return the furniture DTO
   */
  @Override
  public boolean editFurniture(FurnitureDTO furnitureEdit, Set<PhotoDTO> photos) {

    dalService.startTransaction();
    try {
      photos.forEach(photo -> {
        if (photo.getPhotoId() < 0) {
          photoDAO.addPhotos(furnitureEdit.getFurnitureId(), photo.getPhoto()).getPhotoId();

        }
      });
      if (furnitureDAO.updateFurniture(furnitureEdit)) {
        dalService.commitTransaction();
        return true;
      }
      dalService.rollBackTransaction();
    } catch (FatalException e) {

      throw new BizException(e.getMessage().toString());
    }
    return false;

  }

  /**
   * Change furniture state.
   *
   * @param furnitureId the furniture id
   * @param state the state
   * @return true, if successful
   */
  @Override
  public boolean changeFurnitureState(int furnitureId, String state) {
    if (furnitureId < 0) {
      throw new IllegalArgumentException();
    }
    dalService.startTransaction();

    if (furnitureDAO.changeFurnitureState(furnitureId, state.toLowerCase())) {
      dalService.commitTransaction();
      return true;
    } else {
      dalService.rollBackTransaction();
      throw new BizException();
    }
  }

  /**
   * Adds the furniture.
   *
   * @param furniture the furniture
   * @return the int
   */
  @Override
  public int addFurniture(FurnitureDTO furniture) {

    dalService.startTransaction();
    try {
      int typeId = furnitureDAO.selectIdTypeByValue(furniture.getFurnitureType());
      furniture.setState(FurnitureDTO.State.REQUESTED.getValue());
      int furnitureId = furnitureDAO.insertFurniture(furniture, typeId);
      dalService.commitTransaction();
      return furnitureId;
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw e;
    }
  }


  /**
   * Select furniture by id.
   *
   * @param furnitureId the furniture id
   * @return the furniture DTO
   */
  @Override
  public FurnitureDTO selectFurnitureById(int furnitureId) {
    dalService.startTransaction();
    try {
      FurnitureDTO furnitureDTO = furnitureDAO.selectById(furnitureId);
      if (furnitureDTO == null) {
        dalService.rollBackTransaction();
        return null;
      } else {
        dalService.commitTransaction();
        return furnitureDTO;
      }
    } catch (FatalException e) {
      throw new BizException(e.getMessage().toString());
    }
  }

  /**
   * Select furniture by user id.
   *
   * @param userId the user id
   * @return the deque
   */
  @Override
  public Deque<FurnitureDTO> selectFurnitureByUserId(int userId) {
    dalService.startTransaction();
    try {
      Deque<FurnitureDTO> furnitureDTO = furnitureDAO.selectByUserId(userId);
      if (furnitureDTO == null) {
        dalService.rollBackTransaction();
        return null;
      } else {
        dalService.commitTransaction();
        return furnitureDTO;
      }
    } catch (FatalException e) {
      throw new BizException(e.getMessage().toString());
    }
  }

  /**
   * Select all furniture by state.
   *
   * @param state the state
   * @return the sets the
   */
  @Override
  public Deque<FurnitureDTO> selectAllFurnitureByState(String state) {
    for (State furnitureState : State.values()) {
      if (furnitureState.getValue().toLowerCase().equals(state.toLowerCase())) {
        dalService.startTransaction();
        Deque<FurnitureDTO> furnitures = furnitureDAO.selectAllByState(state);
        if (furnitures == null) {
          dalService.rollBackTransaction();
          return new ArrayDeque<FurnitureDTO>();
        } else {
          dalService.commitTransaction();
          return furnitures;
        }
      }
    }
    throw new BizException("state doesn't exist");
  }

  /**
   * Dynamic furniture search.
   *
   * @param customer the customer
   * @param min the min
   * @param max the max
   * @param types the types
   * @return the deque
   */
  @Override
  public Deque<FurnitureDTO> dynamicFurnitureSearch(String customer, int min, int max,
      Set<String> types) {

    dalService.startTransaction();
    try {
      Deque<FurnitureDTO> furnitures = furnitureDAO.selectDynamicSearch(customer, min, max, types);
      dalService.commitTransaction();
      return furnitures;
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw new BizException(e.getMessage().toString());
    }

  }

  /**
   * Select all furniture.
   *
   * @return the sets the
   */
  @Override
  public Deque<FurnitureDTO> selectAllFurniture() {
    dalService.startTransaction();
    try {
      Deque<FurnitureDTO> furnitures = furnitureDAO.selectAll();
      dalService.commitTransaction();
      return furnitures;
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw e;
    }
  }

  /**
   * Select all types.
   *
   * @return the deque
   */
  @Override
  public Deque<String> selectAllTypes() {
    dalService.startTransaction();
    try {
      Deque<String> types = furnitureDAO.selectALLTypes();
      dalService.commitTransaction();
      return types;
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw new BizException(e.getMessage().toString());
    }

  }


  /**
   * Select all furniture for sale.
   *
   * @return the list
   */
  @Override
  public List<FurnitureDTO> selectAllFurnitureForSale() {
    dalService.startTransaction();
    try {
      List<FurnitureDTO> list = furnitureDAO.selectAllFurnitureForSale();

      dalService.commitTransaction();
      return list;
    } catch (FatalException e) {

      dalService.rollBackTransaction();
      throw new BizException(e.getMessage());
    }
  }

  /**
   * Change furniture state with antique dealer price.
   *
   * @param furnitureId the furniture id
   * @param furnitureState the furniture state
   * @param antiquePrice the antique price
   * @return true, if successful
   */
  @Override
  public boolean changeFurnitureStateWithAntiqueDealerPrice(int furnitureId, String furnitureState,
      double antiquePrice) {
    boolean updated = changeFurnitureState(furnitureId, furnitureState);
    dalService.startTransaction();
    boolean updatedPrice = furnitureDAO.addAntiquePrice(furnitureId, antiquePrice);
    if (updated && updatedPrice) {
      dalService.commitTransaction();
      return true;
    } else {
      dalService.rollBackTransaction();
      return false;
    }

  }

  /**
   * Sale furniture.
   *
   * @param furnitureId the furniture id
   * @param state the state
   * @param userId the user id
   * @param price the price
   * @return true, if successful
   */
  @Override
  public boolean saleFurniture(int furnitureId, String state, int userId, double price) {
    if (furnitureId < 0) {
      throw new IllegalArgumentException();
    }
    dalService.startTransaction();


    if (furnitureDAO.saleFurniture(furnitureId, state.toLowerCase(), userId, price)) {

      dalService.commitTransaction();
      return true;
    } else {
      dalService.rollBackTransaction();
      return false;
    }
  }



}
