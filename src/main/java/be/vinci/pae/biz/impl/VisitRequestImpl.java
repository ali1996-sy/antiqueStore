package be.vinci.pae.biz.impl;


import be.vinci.pae.biz.dto.AddressDTO;
import be.vinci.pae.biz.dto.UserDTO;
import be.vinci.pae.biz.feature.VisitRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class VisitRequestImpl.
 */
public class VisitRequestImpl implements VisitRequest {

  /** The visit id. */
  private int visitId;

  /** The request date. */
  private String requestDate;

  /** The time slot. */
  private String timeSlot;

  /** The furniture address. */
  private AddressDTO furnitureAddress;

  /** The state. */
  private String state;

  /** The cancellations due. */
  private String cancellationsDue;

  /** The chosen date. */
  private String chosenDate;

  /** The immediately available. */
  private boolean immediatelyAvailable;

  /** The seller. */
  private UserDTO seller;

  /**
   * Gets the visit id.
   *
   * @return the visit id
   */
  public int getVisitId() {
    return visitId;
  }

  /**
   * Sets the visit id.
   *
   * @param visitId the new visit id
   */
  public void setVisitId(int visitId) {
    this.visitId = visitId;
  }

  /**
   * Gets the request date.
   *
   * @return the request date
   */
  public String getRequestDate() {
    return requestDate;
  }

  /**
   * Sets the request date.
   *
   * @param requestDate the new request date
   */
  public void setRequestDate(String requestDate) {
    this.requestDate = requestDate;
  }

  /**
   * Gets the time slot.
   *
   * @return the time slot
   */
  public String getTimeSlot() {
    return timeSlot;
  }

  /**
   * Sets the time slot.
   *
   * @param timeSlot the new time slot
   */
  public void setTimeSlot(String timeSlot) {
    this.timeSlot = timeSlot;
  }

  /**
   * Gets the furniture address.
   *
   * @return the furniture address
   */
  public AddressDTO getFurnitureAddress() {
    return furnitureAddress;
  }

  /**
   * Sets the furniture address.
   *
   * @param furnitureAddress the new furniture address
   */
  public void setFurnitureAddress(AddressDTO furnitureAddress) {
    this.furnitureAddress = furnitureAddress;
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
   * Gets the cancellations due.
   *
   * @return the cancellations due
   */
  public String getCancellationsDue() {
    return cancellationsDue;
  }

  /**
   * Sets the cancellations due.
   *
   * @param cancellationsDue the new cancellations due
   */
  public void setCancellationsDue(String cancellationsDue) {
    this.cancellationsDue = cancellationsDue;
  }

  /**
   * Gets the chosen date.
   *
   * @return the chosen date
   */
  public String getChosenDate() {
    return chosenDate;
  }

  /**
   * Sets the chosen date.
   *
   * @param chosenDate the new chosen date
   */
  public void setChosenDate(String chosenDate) {
    this.chosenDate = chosenDate;
  }

  /**
   * Checks if is immediately available.
   *
   * @return true, if is immediately available
   */
  public boolean isImmediatelyAvailable() {
    return immediatelyAvailable;
  }

  /**
   * Sets the immediately available.
   *
   * @param immediatelyAvailable the new immediately available
   */
  public void setImmediatelyAvailable(boolean immediatelyAvailable) {
    this.immediatelyAvailable = immediatelyAvailable;
  }

  /**
   * Gets the seller.
   *
   * @return the seller
   */
  public UserDTO getSeller() {
    return seller;
  }

  /**
   * Sets the seller.
   *
   * @param seller the new seller
   */
  public void setSeller(UserDTO seller) {
    this.seller = seller;
  }

}
