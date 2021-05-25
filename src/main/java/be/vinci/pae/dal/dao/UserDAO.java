package be.vinci.pae.dal.dao;

import java.util.Deque;
import java.util.Set;

import be.vinci.pae.biz.dto.AddressDTO;
import be.vinci.pae.biz.dto.UserDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserDAO.
 */
public interface UserDAO {


  /**
   * Select user by username.
   *
   * @param username the username
   * @return the user DTO
   */
  UserDTO selectUserByUsername(String username);

  /**
   * Select user by email.
   *
   * @param email the email
   * @return the user DTO
   */
  UserDTO selectUserByEmail(String email);

  /**
   * Insert user.
   *
   * @param userDTO the user DTO
   * @param addressId the address id
   * @return the int
   */
  int insertUser(UserDTO userDTO, int addressId);

  /**
   * Insert address.
   *
   * @param addressDTO the address DTO
   * @return the int
   */
  int insertAddress(AddressDTO addressDTO);

  /**
   * Update admin field.
   *
   * @param userId the user id
   * @param admin the admin
   * @return true, if successful
   */
  boolean updateAdminField(int userId, boolean admin);

  /**
   * Update antique dealer field.
   *
   * @param userId the user id
   * @param antiqueDealer the antique dealer
   * @return true, if successful
   */
  boolean updateAntiqueDealerField(int userId, boolean antiqueDealer);

  /**
   * Update validate registration field.
   *
   * @param userID the user ID
   * @param validateRegistration the validate registration
   * @return true, if successful
   */
  boolean updateValidateRegistrationField(int userID, boolean validateRegistration);


  /**
   * Select all user by name or and by post code or and by city.
   *
   * @param name the name
   * @param city the city
   * @param postCode the post code
   * @return the list
   */
  Set<UserDTO> selectAllUserByNameOrAndByPostCodeOrAndByCity(String name, String city,
      int postCode);

  /**
   * Gets the user password.
   *
   * @param username the username
   * @return the user password
   */
  String getUserPassword(String username);

  /**
   * Gets the user by username.
   *
   * @param username the username
   * @return the user by username
   */
  UserDTO getUserByUsername(String username);

  /**
   * Select user by ID.
   *
   * @param id the id
   * @return the user DTO
   */
  UserDTO selectUserByID(int id);

  /**
   * Select all user.
   *
   * @return the sets the
   */
  Deque<UserDTO> selectAllUser();

  /**
   * Update sell furniture number.
   *
   * @param id the id
   * @return true, if successful
   */
  boolean updateSellFurnitureNumber(int id);

  /**
   * Update buy furniture number.
   *
   * @param id the id
   * @return true, if successful
   */
  boolean updateBuyFurnitureNumber(int id);
}
