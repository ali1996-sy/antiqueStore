package be.vinci.pae.biz.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import be.vinci.pae.biz.impl.VisitRequestImpl;

// TODO: Auto-generated Javadoc
/**
 * The Interface VisitRequestDTO.
 */
@JsonDeserialize(as = VisitRequestImpl.class)



public interface VisitRequestDTO {

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

  /** The states. */
  String[] states = {"cancelled", "finished", "pending"};

  /**
   * Gets the visit id.
   *
   * @return the visit id
   */
  int getVisitId();

  /**
   * Sets the visit id.
   *
   * @param visitId the new visit id
   */
  void setVisitId(int visitId);

  /**
   * Gets the request date.
   *
   * @return the request date
   */
  String getRequestDate();

  /**
   * Sets the request date.
   *
   * @param string the new request date
   */
  void setRequestDate(String string);

  /**
   * Gets the time slot.
   *
   * @return the time slot
   */
  String getTimeSlot();

  /**
   * Sets the time slot.
   *
   * @param timeSlot the new time slot
   */
  void setTimeSlot(String timeSlot);

  /**
   * Gets the furniture address.
   *
   * @return the furniture address
   */
  AddressDTO getFurnitureAddress();

  /**
   * Sets the furniture address.
   *
   * @param furnitureAddress the new furniture address
   */
  void setFurnitureAddress(AddressDTO furnitureAddress);

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
   * Gets the cancellations due.
   *
   * @return the cancellations due
   */
  String getCancellationsDue();

  /**
   * Sets the cancellations due.
   *
   * @param cancellationsDue the new cancellations due
   */
  void setCancellationsDue(String cancellationsDue);

  /**
   * Gets the chosen date.
   *
   * @return the chosen date
   */
  String getChosenDate();

  /**
   * Sets the chosen date.
   *
   * @param string the new chosen date
   */
  void setChosenDate(String string);

  /**
   * Checks if is immediately available.
   *
   * @return true, if is immediately available
   */
  boolean isImmediatelyAvailable();

  /**
   * Sets the immediately available.
   *
   * @param immediatelyAvailable the new immediately available
   */
  void setImmediatelyAvailable(boolean immediatelyAvailable);

  /**
   * Gets the seller.
   *
   * @return the seller
   */
  UserDTO getSeller();

  /**
   * Sets the seller.
   *
   * @param seller the new seller
   */
  void setSeller(UserDTO seller);

}
