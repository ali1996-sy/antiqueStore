package be.vinci.pae.dal.dao;

import java.sql.ResultSet;
import java.util.Deque;
import java.util.List;
import java.util.Set;

import be.vinci.pae.biz.dto.FurnitureDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface FurnitureDAO.
 */
public interface FurnitureDAO {

  /**
   * Select dynamic search.
   *
   * @param customerName the customer name
   * @param minPrice the min price
   * @param maxPrice the max price
   * @param types the types
   * @return the list
   */
  Deque<FurnitureDTO> selectDynamicSearch(String customerName, double minPrice, double maxPrice,

      Set<String> types);

  /**
   * Select all.
   *
   * @return the sets the
   */
  Deque<FurnitureDTO> selectAll();

  /**
   * Select all by state.
   *
   * @param state the state
   * @return the sets the
   */
  Deque<FurnitureDTO> selectAllByState(String state);


  /**
   * Select all furniture for sale.
   *
   * @return the list
   */
  List<FurnitureDTO> selectAllFurnitureForSale();

  /**
   * Select by id.
   *
   * @param furnitureId the furniture id
   * @return the furniture DTO
   */
  FurnitureDTO selectById(int furnitureId);

  /**
   * Change furniture state.
   *
   * @param furnitureId the furniture id
   * @param state the state
   * @return true, if successful
   */
  boolean changeFurnitureState(int furnitureId, String state);

  /**
   * Update selling price.
   *
   * @param furnitureId the furniture id
   * @param price the price
   * @return true, if successful
   */
  boolean updateSellingPrice(int furnitureId, double price);

  /**
   * Update furniture.
   *
   * @param furniture the furniture
   * @return true, if successful
   */
  boolean updateFurniture(FurnitureDTO furniture);



  /**
   * Insert furniture.
   *
   * 
   * 
   * @param furniture the furniture
   * @param idType TODO
   * 
   * @return true, if successful
   * 
   */
  int insertFurniture(FurnitureDTO furniture, int idType);

  /**
   * Select ALL types.
   *
   * @return the deque
   */
  Deque<String> selectALLTypes();

  /**
   * Select id type by value.
   *
   * @param type the type
   * @return the int
   */
  int selectIdTypeByValue(String type);

  /**
   * Adds the antique price.
   *
   * @param furnitureId the furniture id
   * @param antiquePrice the antique price
   * @return true, if successful
   */
  boolean addAntiquePrice(int furnitureId, double antiquePrice);

  /**
   * Sale furniture.
   *
   * @param furnitureId the furniture id
   * @param lowerCase the lower case
   * @param userId the user id
   * @param price the price
   * @return true, if successful
   */
  boolean saleFurniture(int furnitureId, String lowerCase, int userId, double price);

  /**
   * Sets the attribut by result set.
   *
   * @param rs the rs
   * @return the furniture DTO
   */
  FurnitureDTO setAttributByResultSet(ResultSet rs);

  /**
   * Select by user id.
   *
   * @param userId the user id
   * @return the deque
   */
  Deque<FurnitureDTO> selectByUserId(int userId);
}
