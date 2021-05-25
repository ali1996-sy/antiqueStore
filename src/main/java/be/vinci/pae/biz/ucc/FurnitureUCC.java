package be.vinci.pae.biz.ucc;

import java.util.Deque;
import java.util.List;
import java.util.Set;

import be.vinci.pae.biz.dto.FurnitureDTO;
import be.vinci.pae.biz.dto.PhotoDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface FurnitureUCC.
 */
public interface FurnitureUCC {

  /**
   * Edits the furniture.
   *
   * @param furniture the furniture
   * @param photos the photos
   * @return the furniture DTO
   */
  boolean editFurniture(FurnitureDTO furniture, Set<PhotoDTO> photos);

  /**
   * Select all furniture by state.
   *
   * @param state the state
   * @return the sets the
   */
  Deque<FurnitureDTO> selectAllFurnitureByState(String state);

  /**
   * Select furniture by id.
   *
   * @param furnitureId the furniture id
   * @return the furniture DTO
   */
  FurnitureDTO selectFurnitureById(int furnitureId);

  /**
   * Select all furniture.
   *
   * @return the sets the
   */
  Deque<FurnitureDTO> selectAllFurniture();

  /**
   * Dynamic furniture search.
   *
   * @param customer the customer
   * @param min the min
   * @param max the max
   * @param types the types
   * @return the deque
   */
  Deque<FurnitureDTO> dynamicFurnitureSearch(String customer, int min, int max, Set<String> types);

  /**
   * Select all types.
   *
   * @return the deque
   */
  Deque<String> selectAllTypes();

  /**
   * Change furniture state.
   *
   * @param furnitureId the furniture id
   * @param state the state
   * @return true, if successful
   */
  boolean changeFurnitureState(int furnitureId, String state);


  /**
   * Select all furniture for sale.
   *
   * @return the list
   */
  List<FurnitureDTO> selectAllFurnitureForSale();

  /**
   * Change furniture state with antique dealer price.
   *
   * @param furnitureId the furniture id
   * @param furnitureState the furniture state
   * @param antiquePrice the antique price
   * @return true, if successful
   */
  boolean changeFurnitureStateWithAntiqueDealerPrice(int furnitureId, String furnitureState,
      double antiquePrice);

  /**
   * Sale furniture.
   *
   * @param furnitureId the furniture id
   * @param state the state
   * @param userId the user id
   * @param price the price
   * @return true, if successful
   * @Override
   */
  boolean saleFurniture(int furnitureId, String state, int userId, double price);

  /**
   * Select furniture by user id.
   *
   * @param userId the user id
   * @return the deque
   */
  Deque<FurnitureDTO> selectFurnitureByUserId(int userId);

  /**
   * Adds the furniture.
   *
   * @param furniture the furniture
   * @return the int
   */
  int addFurniture(FurnitureDTO furniture);
}
