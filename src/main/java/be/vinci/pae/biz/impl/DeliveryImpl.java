package be.vinci.pae.biz.impl;

import java.util.Date;

import be.vinci.pae.biz.dto.AddressDTO;
import be.vinci.pae.biz.feature.Delivery;

// TODO: Auto-generated Javadoc
/**
 * The Class DeliveryImpl.
 */
public class DeliveryImpl implements Delivery {

  /** The delivery ID. */
  private int deliveryID;

  /** The delivery address. */
  private AddressDTO deliveryAddress;

  /** The delivery date. */
  private Date deliveryDate;

  /** The buyer. */
  private int buyer;

  /**
   * Gets the delivery address.
   *
   * @return the delivery address
   */
  public AddressDTO getDeliveryAddress() {
    return deliveryAddress;
  }

  /**
   * Sets the delivery address.
   *
   * @param deliveryAddress the new delivery address
   */
  public void setDeliveryAddress(AddressDTO deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  /**
   * Gets the delivery ID.
   *
   * @return the delivery ID
   */
  public int getDeliveryID() {
    return deliveryID;
  }

  /**
   * Sets the delivery ID.
   *
   * @param deliveryID the new delivery ID
   */
  public void setDeliveryID(int deliveryID) {
    this.deliveryID = deliveryID;
  }

  /**
   * Gets the delivery date.
   *
   * @return the delivery date
   */
  public Date getDeliveryDate() {
    return deliveryDate;
  }

  /**
   * Sets the delivery date.
   *
   * @param deliveryDate the new delivery date
   */
  public void setDeliveryDate(Date deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  /**
   * Gets the buyer.
   *
   * @return the buyer
   */
  public int getBuyer() {
    return buyer;
  }

  /**
   * Sets the buyer.
   *
   * @param buyer the new buyer
   */
  public void setBuyer(int buyer) {
    this.buyer = buyer;
  }
}
