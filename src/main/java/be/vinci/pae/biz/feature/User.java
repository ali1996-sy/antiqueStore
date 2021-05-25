package be.vinci.pae.biz.feature;

import be.vinci.pae.biz.dto.UserDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface User.
 */
public interface User extends UserDTO {

  /**
   * Check password.
   *
   * @param password the password
   * @return true, if successful
   */
  boolean checkPassword(String password);

  /**
   * Hash password.
   *
   * @param password the password
   * @return the string
   */
  String hashPassword(String password);

  /**
   * Check can by admin.
   *
   * @param user the user
   * @return true, if successful
   */
  boolean checkCanByAdmin(UserDTO user);

  /**
   * Change to admin.
   *
   * @param user the user
   * @return true, if successful
   */
  boolean changeToAdmin(UserDTO user);

  /**
   * Check email unique.
   *
   * @param email the email
   * @return true, if successful
   */
  boolean checkEmailUnique(String email);

  /**
   * Check username unique.
   *
   * @param username the username
   * @return true, if successful
   */
  boolean checkUsernameUnique(String username);

  /**
   * Check valide caracters email.
   *
   * @param email the email
   * @return true, if successful
   */
  boolean checkValideCaractersEmail(String email);

  /**
   * Check valide caracters username.
   *
   * @param username the username
   * @return true, if successful
   */
  boolean checkValideCaractersUsername(String username);

}
