package be.vinci.pae.biz.factory.impl;

import be.vinci.pae.biz.dto.FurnitureDTO;
import be.vinci.pae.biz.factory.FurnitureFactory;
import be.vinci.pae.biz.impl.FurnitureImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class FurnitureFactroyImpl.
 */
public class FurnitureFactoryImpl implements FurnitureFactory {

  /**
   * Gets the furniture DTO.
   *
   * @return the furniture DTO
   */
  @Override
  public FurnitureDTO getFurnitureDTO() {
    return new FurnitureImpl();
  }

}
