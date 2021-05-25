package be.vinci.pae.biz.impl;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Date;

import be.vinci.pae.biz.dto.AddressDTO;
import be.vinci.pae.biz.dto.UserDTO;
import be.vinci.pae.biz.feature.User;



// TODO: Auto-generated Javadoc
/**
 * The Class UserImpl.
 */
public class UserImpl implements User {

  /** The id. */
  private int id;

  /** The first name. */
  private String firstName;

  /** The last name. */
  private String lastName;

  /** The address DTO. */
  private AddressDTO addressDTO;

  /** The email. */
  private String email;

  /** The registration date. */
  private Date registrationDate;

  /** The validate registration. */
  private boolean validateRegistration;

  /** The is admin. */
  private boolean isAdmin;

  /** The is antique dealer. */
  private boolean isAntiqueDealer;

  /** The username. */
  private String username;

  /** The password. */
  private String password;

  /** The photo. */
  private String photo;

  /** The buy furniture number. */
  private int buyFurnitureNumber;

  /** The sell furniture number. */
  private int sellFurnitureNumber;

  /**
   * Gets the id.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the first name.
   *
   * @return the first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the first name.
   *
   * @param firstName the new first name
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets the last name.
   *
   * @return the last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the last name.
   *
   * @param lastName the new last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets the address DTO.
   *
   * @return the address DTO
   */
  public AddressDTO getAddressDTO() {
    return addressDTO;
  }

  /**
   * Sets the address DTO.
   *
   * @param addressDTO the new address DTO
   */
  public void setAddressDTO(AddressDTO addressDTO) {
    this.addressDTO = addressDTO;
  }

  /**
   * Gets the email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email.
   *
   * @param email the new email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the registration date.
   *
   * @return the registration date
   */
  public Date getRegistrationDate() {
    return registrationDate;
  }

  /**
   * Sets the registration date.
   *
   * @param registrationDate the new registration date
   */
  public void setRegistrationDate(Date registrationDate) {
    this.registrationDate = registrationDate;
  }

  /**
   * Checks if is validate registration.
   *
   * @return true, if is validate registration
   */
  public boolean isValidateRegistration() {
    return validateRegistration;
  }

  /**
   * Sets the validate registration.
   *
   * @param validateRegistration the new validate registration
   */
  public void setValidateRegistration(boolean validateRegistration) {
    this.validateRegistration = validateRegistration;
  }

  /**
   * Checks if is administrator.
   *
   * @return true, if is administrator
   */
  public boolean isAdministrator() {
    return isAdmin;
  }

  /**
   * Sets the admin.
   *
   * @param isAdmin the new admin
   */
  public void setAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  /**
   * Checks if is antique dealer.
   *
   * @return true, if is antique dealer
   */
  public boolean isAntiqueDealer() {
    return isAntiqueDealer;
  }

  /**
   * Sets the antique dealer.
   *
   * @param isAntiqueDealer the new antique dealer
   */
  public void setAntiqueDealer(boolean isAntiqueDealer) {
    this.isAntiqueDealer = isAntiqueDealer;
  }

  /**
   * Gets the username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username.
   *
   * @param username the new username
   */
  public void setUsername(String username) {
    this.username = username.toLowerCase();
  }

  /**
   * Gets the password.
   *
   * @return the password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Sets the password.
   *
   * @param password the new password
   */
  public void setPassword(String password) {
    this.password = password;
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
   * Gets the buy furniture number.
   *
   * @return the buy furniture number
   */
  public int getBuyFurnitureNumber() {
    return buyFurnitureNumber;
  }

  /**
   * Sets the buy furniture number.
   *
   * @param buyFurnitureNumber the new buy furniture number
   */
  public void setBuyFurnitureNumber(int buyFurnitureNumber) {
    this.buyFurnitureNumber = buyFurnitureNumber;
  }

  /**
   * Gets the sell furniture number.
   *
   * @return the sell furniture number
   */
  public int getSellFurnitureNumber() {
    return sellFurnitureNumber;
  }

  /**
   * Sets the sell furniture number.
   *
   * @param sellFurnitureNumber the new sell furniture number
   */
  public void setSellFurnitureNumber(int sellFurnitureNumber) {
    this.sellFurnitureNumber = sellFurnitureNumber;
  }

  /**
   * Check password.
   *
   * @param password the password
   * @return true, if successful
   */

  public boolean checkPassword(String password) {

    return BCrypt.checkpw(password, this.password);
  }


  /**
   * Hash password. s
   * 
   * @param password the password
   * @return the string
   */
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt(10));
  }

  /**
   * Check can by admin.
   *
   * @param user the user
   * @return true, if successful
   */
  @Override
  public boolean checkCanByAdmin(UserDTO user) {
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * Change to admin.
   *
   * @param user the user
   * @return true, if successful
   */
  @Override
  public boolean changeToAdmin(UserDTO user) {
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * Not implemented for liv 2.
   *
   * @param email the email
   * @return true, if successful
   */
  @Override
  public boolean checkEmailUnique(String email) {
    return false;
  }

  /**
   * Not implemented for liv 2.
   *
   * @param username the username
   * @return true, if successful
   */
  @Override
  public boolean checkUsernameUnique(String username) {
    return false;
  }

  /**
   * Check valide caracters email.
   *
   * @param email the email
   * @return true, if successful
   */
  @Override
  public boolean checkValideCaractersEmail(String email) {
    return email.matches("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)");
  }

  /**
   * Check valide caracters username.
   *
   * @param username the username
   * @return true, if successful
   */
  @Override
  public boolean checkValideCaractersUsername(String username) {
    return username.matches("^[a-zA-Z]*[0-9]*$");
  }


}
