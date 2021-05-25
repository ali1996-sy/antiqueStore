package be.vinci.pae.biz.dto;

import java.sql.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import be.vinci.pae.biz.impl.OptionImpl;

// TODO: Auto-generated Javadoc
/**
 * The Interface OptionDTO.
 */
@JsonDeserialize(as = OptionImpl.class)

public interface OptionDTO {

  /**
   * The Enum State.
   */
  enum State {

    /** The cancelled. */
    CANCELLED("cancelled"),
    /** The finished. */
    FINISHED("finished"),
    /** The pending. */
    PENDING("pending");

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

  /**
   * Checks if is antique dealer.
   *
   * @return true, if is antique dealer
   */
  boolean isAntiqueDealer();

  /**
   * Sets the antique dealer.
   *
   * @param antiqueDealer the new antique dealer
   */
  void setAntiqueDealer(boolean antiqueDealer);

  /**
   * Gets the option id.
   *
   * @return the option id
   */
  int getOptionId();



  /**
   * Sets the option id.
   *
   * @param optionId the new option id
   */
  void setOptionId(int optionId);

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
   * Gets the furniture.
   *
   * @return the furniture
   */
  int getFurniture();

  /**
   * Sets the furniture.
   *
   * @param furniture the new furniture
   */
  void setFurniture(int furniture);

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
   * Gets the option term.
   *
   * @return the option term
   */
  int getOptionTerm();

  /**
   * Sets the option term.
   *
   * @param optionTerm the new option term
   */
  void setOptionTerm(int optionTerm);

  /**
   * Gets the buyer name.
   *
   * @return the buyer name
   */
  String getBuyerName();

  /**
   * Sets the buyer name.
   *
   * @param buyerName the new buyer name
   */
  void setBuyerName(String buyerName);

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
   * Gets the term date.
   *
   * @return the term date
   */
  Date getTermDate();

  /**
   * Sets the term date.
   *
   * @param termDate the new term date
   */
  void setTermDate(Date termDate);

}
