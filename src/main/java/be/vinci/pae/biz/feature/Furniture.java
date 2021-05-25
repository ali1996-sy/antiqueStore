package be.vinci.pae.biz.feature;

import be.vinci.pae.biz.dto.FurnitureDTO;

/**
 * The Interface Furniture.
 */
public interface Furniture extends FurnitureDTO {
  boolean checkStateIsAcceptable(String state, int furnitureId);
}
