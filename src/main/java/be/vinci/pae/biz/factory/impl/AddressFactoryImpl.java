package be.vinci.pae.biz.factory.impl;

import be.vinci.pae.biz.dto.AddressDTO;
import be.vinci.pae.biz.factory.AddressFactory;
import be.vinci.pae.biz.impl.AddressImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class AddressFactoryImpl.
 */
public class AddressFactoryImpl implements AddressFactory {

  /**
   * Gets the address DTO.
   *
   * @return the address DTO
   */
  @Override
  public AddressDTO getAddressDTO() {
    return new AddressImpl();
  }

}
