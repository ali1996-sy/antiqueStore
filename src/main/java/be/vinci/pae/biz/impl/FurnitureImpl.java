package be.vinci.pae.biz.impl;

import java.util.Date;

import be.vinci.pae.biz.feature.Furniture;

// TODO: Auto-generated Javadoc
/**
 * The Class FurnitureImpl.
 */
public class FurnitureImpl implements Furniture {

  /** The furniture id. */
  private int furnitureId;

  /** The furniture type. */
  private String furnitureType;

  /** The deposit date. */
  private Date depositDate;

  /** The selling price. */
  private double sellingPrice;

  /** The special sale price. */
  private double specialSalePrice;

  /** The state. */
  private String state;

  /** The furniture collection date. */
  private Date furnitureCollectionDate;

  /** The date of sale. */
  private Date dateOfSale;

  /** The purchase price. */
  private double purchasePrice;

  /** The buyer. */
  private int buyer;

  /** The delivery. */
  private int delivery;

  /** The request for visit. */
  private int requestForVisit;

  /** The withdrawal date. */
  private Date withdrawalDate;

  /** The description. */
  private String description;

  /** The favourite photo. */
  private int favouritePhoto;

  /**
   * Gets the furniture type.
   *
   * @return the furniture type
   */
  @Override
  public String getFurnitureType() {
    return furnitureType;
  }

  /**
   * Sets the furniture type.
   *
   * @param furnitureType the new furniture type
   */
  @Override
  public void setFurnitureType(String furnitureType) {
    this.furnitureType = furnitureType;
  }

  /**
   * Gets the deposit date.
   *
   * @return the deposit date
   */
  @Override
  public Date getDepositDate() {
    return depositDate;
  }

  /**
   * Sets the deposit date.
   *
   * @param depositDate the new deposit date
   */
  @Override
  public void setDepositDate(Date depositDate) {
    this.depositDate = depositDate;
  }

  /**
   * Gets the selling price.
   *
   * @return the selling price
   */
  @Override
  public double getSellingPrice() {
    return sellingPrice;
  }

  /**
   * Sets the selling price.
   *
   * @param sellingPrice the new selling price
   */
  @Override
  public void setSellingPrice(double sellingPrice) {
    this.sellingPrice = sellingPrice;
  }

  /**
   * Gets the special sale price.
   *
   * @return the special sale price
   */
  @Override
  public double getSpecialSalePrice() {
    return specialSalePrice;
  }

  /**
   * Sets the special sale price.
   *
   * @param specialSalePrice the new special sale price
   */
  @Override
  public void setSpecialSalePrice(double specialSalePrice) {
    this.specialSalePrice = specialSalePrice;
  }

  /**
   * Gets the state.
   *
   * @return the state
   */
  @Override
  public String getState() {
    return state;
  }

  /**
   * Sets the state.
   *
   * @param state the new state
   */
  @Override
  public void setState(String state) {
    this.state = state;
  }

  /**
   * Gets the furniture collection date.
   *
   * @return the furniture collection date
   */
  @Override
  public Date getFurnitureCollectionDate() {
    return furnitureCollectionDate;
  }

  /**
   * Sets the furniture collection date.
   *
   * @param furnitureCollectionDate the new furniture collection date
   */
  @Override
  public void setFurnitureCollectionDate(Date furnitureCollectionDate) {
    this.furnitureCollectionDate = furnitureCollectionDate;
  }

  /**
   * Gets the date of sale.
   *
   * @return the date of sale
   */
  @Override
  public Date getDateOfSale() {
    return dateOfSale;
  }

  /**
   * Sets the date of sale.
   *
   * @param dateOfSale the new date of sale
   */
  @Override
  public void setDateOfSale(Date dateOfSale) {
    this.dateOfSale = dateOfSale;
  }

  /**
   * Gets the purchase price.
   *
   * @return the purchase price
   */
  @Override
  public double getPurchasePrice() {
    return purchasePrice;
  }

  /**
   * Sets the purchase price.
   *
   * @param purchasePrice the new purchase price
   */
  @Override
  public void setPurchasePrice(double purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  /**
   * Gets the buyer.
   *
   * @return the buyer
   */
  @Override
  public int getBuyer() {
    return buyer;
  }

  /**
   * Sets the buyer.
   *
   * @param buyer the new buyer
   */
  @Override
  public void setBuyer(int buyer) {
    this.buyer = buyer;
  }

  /**
   * Gets the delivery.
   *
   * @return the delivery
   */
  @Override
  public int getDelivery() {
    return delivery;
  }

  /**
   * Sets the delivery.
   *
   * @param delivery the new delivery
   */
  @Override
  public void setDelivery(int delivery) {
    this.delivery = delivery;
  }

  /**
   * Gets the request for visit.
   *
   * @return the request for visit
   */
  @Override
  public int getRequestForVisit() {
    return requestForVisit;
  }

  /**
   * Sets the request for visit.
   *
   * @param requestForVisit the new request for visit
   */
  @Override
  public void setRequestForVisit(int requestForVisit) {
    this.requestForVisit = requestForVisit;
  }

  /**
   * Gets the withdrawal date.
   *
   * @return the withdrawal date
   */
  @Override
  public Date getWithdrawalDate() {
    return withdrawalDate;
  }

  /**
   * Sets the withdrawal date.
   *
   * @param withdrawalDate the new withdrawal date
   */
  @Override
  public void setWithdrawalDate(Date withdrawalDate) {
    this.withdrawalDate = withdrawalDate;
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description the new description
   */
  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the favourite photo.
   *
   * @return the favourite photo
   */
  @Override
  public int getFavouritePhoto() {
    return favouritePhoto;
  }

  /**
   * Sets the favourite photo.
   *
   * @param favouritePhoto the new favourite photo
   */
  @Override
  public void setFavouritePhoto(int favouritePhoto) {
    this.favouritePhoto = favouritePhoto;
  }

  /**
   * Gets the furniture id.
   *
   * @return the furniture id
   */
  public int getFurnitureId() {
    return furnitureId;
  }

  /**
   * Sets the furniture id.
   *
   * @param furnitureId the new furniture id
   */
  public void setFurnitureId(int furnitureId) {
    this.furnitureId = furnitureId;
  }

  @Override
  public boolean checkStateIsAcceptable(String state, int furnitureId) {
    return false;
  }

}
