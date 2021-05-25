package be.vinci.pae.biz.factory.impl;

import be.vinci.pae.biz.dto.UserDTO;
import be.vinci.pae.biz.factory.UserFactory;
import be.vinci.pae.biz.impl.UserImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class UserFactoryImpl.
 */
public class UserFactoryImpl implements UserFactory {

  /**
   * Gets the user DTO.
   *
   * @return the user DTO
   */
  @Override
  public UserDTO getUserDTO() {
    return new UserImpl();
  }
}
