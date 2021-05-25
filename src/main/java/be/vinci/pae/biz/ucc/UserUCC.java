package be.vinci.pae.biz.ucc;

import java.util.Deque;
import java.util.Set;

import be.vinci.pae.biz.dto.AddressDTO;
import be.vinci.pae.biz.dto.UserDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserUCC.
 */
public interface UserUCC {

  /**
   * Register.
   *
   * @param user the user
   * @return the user DTO
   */
  int register(UserDTO user);

  /**
   * Login.
   *
   * @param username the username
   * @param password the password
   * @return the user DTO
   */
  UserDTO login(String username, String password);

  /**
   * Update admin.
   *
   * @param userId the user id
   * @param admin the admin
   * @return true, if successful
   */
  boolean updateAdmin(int userId, boolean admin);

  /**
   * Validate registration.
   *
   * @param userId the user id
   * @param validateRegistration the validate registration
   * @return true, if successful
   */
  boolean validateRegistration(int userId, boolean validateRegistration);

  /**
   * Update antique dealer.
   *
   * @param userId the user id
   * @param antiqueDealer the antique dealer
   * @return true, if successful
   */
  boolean updateAntiqueDealer(int userId, boolean antiqueDealer);

  /**
   * Dynamic customer search.
   *
   * @param name the name
   * @param postCode the post code
   * @param city the city
   * @return the list
   */

  Set<UserDTO> dynamicCustomerSearch(String name, int postCode, String city);

  /**
   * Login by id.
   *
   * @param id the id
   * @return the user DTO
   */
  UserDTO userById(int id);

  /**
   * Gets the all users.
   *
   * @return the all users
   */
  Deque<UserDTO> getAllUsers();

  /**
   * Update buy furniture number.
   *
   * @param userId the user id
   * @return true, if successful
   */
  boolean updateBuyFurnitureNumber(int userId);

  /**
   * Update sell furniture number.
   *
   * @param userId the user id
   * @return true, if successful
   */
  boolean updateSellFurnitureNumber(int userId);

  /**
   * Adds the address.
   *
   * @param address the address
   * @return the int
   */
  int addAddress(AddressDTO address);



}
