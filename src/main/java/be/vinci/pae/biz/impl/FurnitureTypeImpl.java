package be.vinci.pae.biz.impl;

import be.vinci.pae.biz.feature.FurnitureType;

// TODO: Auto-generated Javadoc
/**
 * The Class FurnitureTypeImpl.
 */
public class FurnitureTypeImpl implements FurnitureType {

  /** The furniture type id. */
  private int furnitureTypeId;

  /** The name. */
  private String name;

  /**
   * Gets the furniture type id.
   *
   * @return the furniture type id
   */
  public int getFurnitureTypeId() {
    return furnitureTypeId;
  }

  /**
   * Sets the furniture type id.
   *
   * @param furnitureTypeId the new furniture type id
   */
  public void setFurnitureTypeId(int furnitureTypeId) {
    this.furnitureTypeId = furnitureTypeId;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }
}
