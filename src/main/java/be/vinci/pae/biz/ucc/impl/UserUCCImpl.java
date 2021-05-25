package be.vinci.pae.biz.ucc.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Deque;
import java.util.Set;

import be.vinci.pae.biz.dto.AddressDTO;
import be.vinci.pae.biz.dto.UserDTO;
import be.vinci.pae.biz.feature.User;
import be.vinci.pae.biz.ucc.UserUCC;
import be.vinci.pae.dal.DalServices;
import be.vinci.pae.dal.dao.UserDAO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.FatalException;
import jakarta.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class UserUCCImpl.
 */
public class UserUCCImpl implements UserUCC {


  /** The user DAO. */
  @Inject
  private UserDAO userDAO;

  /** The dal service. */
  @Inject
  DalServices dalService;



  /**
   * Register.
   *
   * @param userDTO the user DTO
   * @return the int
   */
  @Override
  public int register(UserDTO userDTO) {
    userDTO.setAdmin(false);
    userDTO.setAntiqueDealer(false);
    userDTO.setValidateRegistration(false);
    userDTO.setBuyFurnitureNumber(0);
    userDTO.setSellFurnitureNumber(0);
    userDTO.setRegistrationDate(Date.valueOf(LocalDate.now()));
    String password = userDTO.getPassword();
    User user = (User) userDTO;
    userDTO.setPassword(user.hashPassword(password));
    dalService.startTransaction();

    try {
      UserDTO username = userDAO.selectUserByUsername(userDTO.getUsername());
      UserDTO email = userDAO.selectUserByEmail(userDTO.getEmail());
      if (username != null || email != null) {
        throw new BizException("Utilisateur existant");
      }
      int addressId = userDAO.insertAddress(userDTO.getAddressDTO());
      int userId = userDAO.insertUser(userDTO, addressId);
      if (addressId < 0) {
        dalService.rollBackTransaction();
        return -1;
      } else if (userId < 0) {
        dalService.rollBackTransaction();
        return -1;
      }

      dalService.commitTransaction();
      return userId;

    } catch (FatalException e) {
      throw new BizException("probelme d'inscription");
    }

  }

  /**
   * Login by id.
   *
   * @param id the id
   * @return the user DTO
   */
  @Override
  public UserDTO userById(int id) {
    if (id <= 0) {
      throw new BizException("mauvais UserId");
    }
    dalService.startTransaction();
    UserDTO userDTO = userDAO.selectUserByID(id);
    if (userDTO != null /* &&(user.checkPassword(password)) */) {
      dalService.commitTransaction();
      return userDTO;

    } else {
      dalService.rollBackTransaction();
      throw new BizException("Echec de loging");
    }
  }

  /**
   * Login.
   *
   * @param usernameOrEmail the username or email
   * @param password the password
   * @return the user DTO
   */
  @Override
  public UserDTO login(String usernameOrEmail, String password) {
    if (usernameOrEmail == null || password == null) {
      throw new BizException();
    }
    dalService.startTransaction();
    UserDTO userDTO = userExistOrAlreadyExistByUsernameOrEmail(usernameOrEmail);
    User user = (User) userDTO;

    if (user.checkPassword(password)) {
      dalService.commitTransaction();
      return userDTO;
    } else {
      dalService.rollBackTransaction();
      throw new BizException();
    }
  }

  private UserDTO userExistOrAlreadyExistByUsernameOrEmail(String usernameOrEmail) {
    UserDTO userDTO = userDAO.selectUserByUsername(usernameOrEmail.toLowerCase());
    if (userDTO == null) {
      userDTO = userDAO.selectUserByEmail(usernameOrEmail.toLowerCase());
      if (userDTO == null) {
        throw new BizException("L'utilisateur n'existe pas !");
      }
    }
    return userDTO;
  }



  /**
   * Dynamic customer search.
   *
   * @param name the name
   * @param postCode the post code
   * @param city the city
   * @return the list
   */
  @Override
  public Set<UserDTO> dynamicCustomerSearch(String name, int postCode, String city) {
    dalService.startTransaction();
    Set<UserDTO> users =
        userDAO.selectAllUserByNameOrAndByPostCodeOrAndByCity(name, city, postCode);
    if (users == null) {
      dalService.rollBackTransaction();
      throw new BizException();
    } else {
      dalService.commitTransaction();
      return users;
    }
  }


  /**
   * Update admin.
   *
   * @param userId the user id
   * @param admin the admin
   * @return true, if successful
   */
  @Override
  public boolean updateAdmin(int userId, boolean admin) {
    dalService.startTransaction();
    boolean updated = userDAO.updateAdminField(userId, admin);
    if (updated == false) {
      dalService.rollBackTransaction();
      throw new BizException();

    } else {
      dalService.commitTransaction();
      return true;
    }
  }


  /**
   * Validate registration.
   *
   * @param userId the user id
   * @param validateRegistration the validate registration
   * @return true, if successful
   */
  @Override
  public boolean validateRegistration(int userId, boolean validateRegistration) {

    dalService.startTransaction();
    try {
      if (userDAO.updateValidateRegistrationField(userId, validateRegistration)) {
        dalService.commitTransaction();
        return true;
      }
      dalService.rollBackTransaction();
      throw new BizException("l'utilisateur n'est pas confimé");
    } catch (FatalException e) {
      throw new BizException(e.getStackTrace().toString());
    }

  }

  /**
   * Update buy furniture number.
   *
   * @param userId the user id
   * @return true, if successful
   */
  @Override
  public boolean updateBuyFurnitureNumber(int userId) {

    dalService.startTransaction();
    try {
      if (userDAO.updateBuyFurnitureNumber(userId)) {
        dalService.commitTransaction();
        return true;
      }
      dalService.rollBackTransaction();
      throw new BizException("updateBuyFurniture failed");
    } catch (FatalException e) {
      throw new BizException(e.getStackTrace().toString());
    }

  }

  /**
   * Update sell furniture number.
   *
   * @param userId the user id
   * @return true, if successful
   */
  @Override
  public boolean updateSellFurnitureNumber(int userId) {

    dalService.startTransaction();
    try {
      if (userDAO.updateSellFurnitureNumber(userId)) {
        dalService.commitTransaction();
        return true;
      }
      dalService.rollBackTransaction();
      throw new BizException("updateSellFurniture failed");
    } catch (FatalException e) {
      throw new BizException(e.getStackTrace().toString());
    }

  }


  /**
   * Update antique dealer.
   *
   * @param userId the user id
   * @param antiqueDealer the antique dealer
   * @return true, if successful
   */
  @Override
  public boolean updateAntiqueDealer(int userId, boolean antiqueDealer) {
    dalService.startTransaction();
    boolean updated = userDAO.updateAntiqueDealerField(userId, antiqueDealer);
    if (updated == false) {
      dalService.rollBackTransaction();
      throw new BizException();

    } else {
      dalService.commitTransaction();
      return true;
    }

  }

  /**
   * Gets the all users.
   *
   * @return the all users
   */
  @Override
  public Deque<UserDTO> getAllUsers() {
    dalService.startTransaction();
    Deque<UserDTO> set = userDAO.selectAllUser();
    dalService.commitTransaction();

    return set;

  }

  /**
   * Adds the address.
   *
   * @param address the address
   * @return the int
   */
  @Override
  public int addAddress(AddressDTO address) {
    dalService.startTransaction();
    try {
      int id = userDAO.insertAddress(address);
      dalService.commitTransaction();
      return id;
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw new BizException();
    }
  }
}
