package be.vinci.pae.biz.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import be.vinci.pae.biz.impl.FurnitureImpl;

// TODO: Auto-generated Javadoc
/**
 * The Interface FurnitureDTO.
 */
@JsonDeserialize(as = FurnitureImpl.class)

public interface FurnitureDTO {

  /**
   * The Enum State.
   */
  enum State {

    /** The for sale. */
    FOR_SALE("for_sale"),
    /** The saled. */
    SALED("saled"),
    /** The cancelled. */
    CANCELLED("cancelled"),
    /** The reserved. */
    RESERVED("reserved"),
    /** The deposited. */
    DEPOSITED("deposited"),
    /** The purchased. */
    PURCHASED("purchased"),
    /** The request canceled. */
    REQUEST_CANCELED("request_canceled"),
    /** The delivered. */
    DELIVERED("delivered"),
    /** The removed. */
    REMOVED("removed"),
    /** The collected. */
    COLLECTED("collected"),
    /** The restored. */
    RESTORED("restored"),
    /** The requested. */
    REQUESTED("requested"),
    /** The in option. */
    IN_OPTION("in_option");


    /** The state. */
    private String state;

    /**
     * Instantiates a new state.
     *
     * @param state the state
     */
    State(String state) {
      this.state = state;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
      return state;
    }
  }
  //
  // /** The states. */
  // /*
  // * Deprecated
  // */
  // String[] states = {"for_sale", "deposited", "removed", "reserved", "collected", "delivered",
  // "Acheté", "Demande annulée"};

  /**
   * Gets the furniture id.
   *
   * @return the furniture id
   */
  int getFurnitureId();

  /**
   * Sets the furniture id.
   *
   * @param furnitureId the new furniture id
   */
  void setFurnitureId(int furnitureId);

  /**
   * Gets the furniture type.
   *
   * @return the furniture type
   */
  String getFurnitureType();

  /**
   * Sets the furniture type.
   *
   * @param furnitureType the new furniture type
   */
  void setFurnitureType(String furnitureType);

  /**
   * Gets the deposit date.
   *
   * @return the deposit date
   */
  Date getDepositDate();

  /**
   * Sets the deposit date.
   *
   * @param depositDate the new deposit date
   */
  void setDepositDate(Date depositDate);

  /**
   * Gets the selling price.
   *
   * @return the selling price
   */
  double getSellingPrice();

  /**
   * Sets the selling price.
   *
   * @param sellingPrice the new selling price
   */
  void setSellingPrice(double sellingPrice);

  /**
   * Gets the special sale price.
   *
   * @return the special sale price
   */
  double getSpecialSalePrice();

  /**
   * Sets the special sale price.
   *
   * @param specialSalePrice the new special sale price
   */
  void setSpecialSalePrice(double specialSalePrice);

  /**
   * Gets the state.
   *
   * @return the state
   */
  String getState();

  /**
   * Sets the state.
   *
   * @param state the new state
   */
  void setState(String state);

  /**
   * Gets the furniture collection date.
   *
   * @return the furniture collection date
   */
  Date getFurnitureCollectionDate();

  /**
   * Sets the furniture collection date.
   *
   * @param furnitureCollectionDate the new furniture collection date
   */
  void setFurnitureCollectionDate(Date furnitureCollectionDate);

  /**
   * Gets the date of sale.
   *
   * @return the date of sale
   */
  Date getDateOfSale();

  /**
   * Sets the date of sale.
   *
   * @param dateOfSale the new date of sale
   */
  void setDateOfSale(Date dateOfSale);

  /**
   * Gets the purchase price.
   *
   * @return the purchase price
   */
  double getPurchasePrice();

  /**
   * Sets the purchase price.
   *
   * @param purchasePrice the new purchase price
   */
  void setPurchasePrice(double purchasePrice);

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

  /**
   * Gets the delivery.
   *
   * @return the delivery
   */
  int getDelivery();

  /**
   * Sets the delivery.
   *
   * @param delivery the new delivery
   */
  void setDelivery(int delivery);

  /**
   * Gets the request for visit.
   *
   * @return the request for visit
   */
  int getRequestForVisit();

  /**
   * Sets the request for visit.
   *
   * @param requestForVisit the new request for visit
   */
  void setRequestForVisit(int requestForVisit);

  /**
   * Gets the withdrawal date.
   *
   * @return the withdrawal date
   */
  Date getWithdrawalDate();

  /**
   * Sets the withdrawal date.
   *
   * @param withdrawalDate the new withdrawal date
   */
  void setWithdrawalDate(Date withdrawalDate);

  /**
   * Gets the description.
   *
   * @return the description
   */
  String getDescription();

  /**
   * Sets the description.
   *
   * @param description the new description
   */
  void setDescription(String description);

  /**
   * Gets the favourite photo.
   *
   * @return the favourite photo
   */
  int getFavouritePhoto();

  /**
   * Sets the favourite photo.
   *
   * @param favouritePhoto the new favourite photo
   */
  void setFavouritePhoto(int favouritePhoto);

}
