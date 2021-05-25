package be.vinci.pae.biz.dto;

import java.sql.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import be.vinci.pae.biz.impl.UserImpl;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserDTO.
 */
@JsonDeserialize(as = UserImpl.class)

public interface UserDTO {

  /**
   * Gets the id.
   *
   * @return the id
   */
  int getId();

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  void setId(int id);

  /**
   * Gets the first name.
   *
   * @return the first name
   */
  String getFirstName();

  /**
   * Sets the first name.
   *
   * @param firstName the new first name
   */
  void setFirstName(String firstName);

  /**
   * Gets the last name.
   *
   * @return the last name
   */
  String getLastName();

  /**
   * Sets the last name.
   *
   * @param lastName the new last name
   */
  void setLastName(String lastName);

  /**
   * Gets the addressDTO.
   *
   * @return the addressDTO
   */
  AddressDTO getAddressDTO();

  /**
   * Sets the addressDTO.
   *
   * @param addressDTO the new addressDTO
   */
  void setAddressDTO(AddressDTO addressDTO);

  /**
   * Gets the email.
   *
   * @return the email
   */
  String getEmail();

  /**
   * Sets the email.
   *
   * @param email the new email
   */
  void setEmail(String email);

  /**
   * Gets the registration date.
   *
   * @return the registration date
   */
  Date getRegistrationDate();

  /**
   * Sets the registration date.
   *
   * @param registrationDate the new registration date
   */
  void setRegistrationDate(Date registrationDate);

  /**
   * Checks if is validate registration.
   *
   * @return true, if is validate registration
   */
  boolean isValidateRegistration();

  /**
   * Sets the validate registration.
   *
   * @param validateRegistration the new validate registration
   */
  void setValidateRegistration(boolean validateRegistration);

  /**
   * Checks if is admin.
   *
   * @return true, if is admin
   */
  boolean isAdministrator();

  /**
   * Sets the admin.
   *
   * @param isAdmin the new admin
   */
  void setAdmin(boolean isAdmin);

  /**
   * Checks if is antique dealer.
   *
   * @return true, if is antique dealer
   */
  boolean isAntiqueDealer();

  /**
   * Sets the antique dealer.
   *
   * @param isAntiqueDealer the new antique dealer
   */
  void setAntiqueDealer(boolean isAntiqueDealer);

  /**
   * Gets the password.
   *
   * @return the password
   */
  String getPassword();

  /**
   * Sets the password.
   *
   * @param password the new password
   */
  void setPassword(String password);

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
   * Gets the buy furniture number.
   *
   * @return the buy furniture number
   */
  int getBuyFurnitureNumber();

  /**
   * Sets the buy furniture number.
   *
   * @param buyFurnitureNumber the new buy furniture number
   */
  void setBuyFurnitureNumber(int buyFurnitureNumber);

  /**
   * Gets the sell furniture number.
   *
   * @return the sell furniture number
   */
  int getSellFurnitureNumber();

  /**
   * Sets the sell furniture number.
   *
   * @param sellFurnitureNumber the new sell furniture number
   */
  void setSellFurnitureNumber(int sellFurnitureNumber);

  /**
   * Gets the username.
   *
   * @return the username
   */
  String getUsername();

  /**
   * Sets the username.
   *
   * @param username the new username
   */
  void setUsername(String username);
}
