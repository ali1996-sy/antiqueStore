package be.vinci.pae.biz.impl;

import java.sql.Date;

import be.vinci.pae.biz.feature.Option;

// TODO: Auto-generated Javadoc
/**
 * The Class OptionImpl.
 */
public class OptionImpl implements Option {
  private boolean antiqueDealer;
  /** The option id. */
  private int optionId;

  /** The buyer. */
  private int buyer;

  /** The furniture. */
  private int furniture;

  /** The state. */
  private String state;

  /** The option term. */
  private int optionTerm;

  /** The term date. */
  private Date termDate;

  private String buyerName;

  private String description;

  public String getBuyerName() {
    return buyerName;
  }

  public void setBuyerName(String buyerName) {
    this.buyerName = buyerName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the option id.
   *
   * @return the option id
   */
  public int getOptionId() {
    return optionId;
  }

  /**
   * Sets the option id.
   *
   * @param optionId the new option id
   */
  public void setOptionId(int optionId) {
    this.optionId = optionId;
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

  /**
   * Gets the furniture.
   *
   * @return the furniture
   */
  public int getFurniture() {
    return furniture;
  }

  /**
   * Sets the furniture.
   *
   * @param furniture the new furniture
   */
  public void setFurniture(int furniture) {
    this.furniture = furniture;
  }

  /**
   * Gets the state.
   *
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * Sets the state.
   *
   * @param state the new state
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * Gets the option term.
   *
   * @return the option term
   */
  public int getOptionTerm() {
    return optionTerm;
  }

  /**
   * Sets the option term.
   *
   * @param optionTerm the new option term
   */
  public void setOptionTerm(int optionTerm) {
    this.optionTerm = optionTerm;
  }

  /**
   * Gets the term date.
   *
   * @return the termDate
   */
  public Date getTermDate() {
    return termDate;
  }

  /**
   * Sets the term date.
   *
   * @param termDate the new term date
   */
  public void setTermDate(Date termDate) {
    this.termDate = termDate;
  }

  public boolean isAntiqueDealer() {
    return antiqueDealer;
  }

  public void setAntiqueDealer(boolean antiqueDealer) {
    this.antiqueDealer = antiqueDealer;
  }

}
