package be.vinci.pae.biz.factory.impl;

import be.vinci.pae.biz.dto.OptionDTO;
import be.vinci.pae.biz.factory.OptionFactory;
import be.vinci.pae.biz.impl.OptionImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class OptionFactoryImpl.
 */
public class OptionFactoryImpl implements OptionFactory {

  /**
   * Gets the option DTO.
   *
   * @return the option DTO
   */
  @Override
  public OptionDTO getOptionDTO() {
    return new OptionImpl();
  }
}
