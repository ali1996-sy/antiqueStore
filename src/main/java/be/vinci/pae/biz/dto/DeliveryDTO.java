package be.vinci.pae.biz.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import be.vinci.pae.biz.impl.DeliveryImpl;


// TODO: Auto-generated Javadoc
/**
 * Thes Interface DeliveryDTO.
 */
@JsonDeserialize(as = DeliveryImpl.class)

public interface DeliveryDTO {

  /**
   * Gets the delivery address.
   *
   * @return the delivery address
   */
  AddressDTO getDeliveryAddress();

  /**
   * Sets the delivery address.
   *
   * @param deliveryAddress the new delivery address
   */
  void setDeliveryAddress(AddressDTO deliveryAddress);

  /**
   * Gets the delivery ID.
   *
   * @return the delivery ID
   */
  int getDeliveryID();

  /**
   * Sets the delivery ID.
   *
   * @param deliveryID the new delivery ID
   */
  void setDeliveryID(int deliveryID);

  /**
   * Gets the delivery date.
   *
   * @return the delivery date
   */
  Date getDeliveryDate();

  /**
   * Sets the delivery date.
   *
   * @param deliveryDate the new delivery date
   */
  void setDeliveryDate(Date deliveryDate);

  /**
   * Gets the buyer.
   *
   * @return the buyer
   */
  int getBuyer();

  /**
   * Sets the buyer.
   *
   * @param buyer the new buyer
   */
  void setBuyer(int buyer);

}
