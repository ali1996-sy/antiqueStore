package be.vinci.pae.dal.dao;

import java.util.Deque;

import be.vinci.pae.biz.dto.VisitRequestDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface VisitRequestDAO.
 */
public interface VisitRequestDAO {

  /**
   * Creat visit request.
   *
   * @param address the address
   * @param plageHoraire the plage horaire
   * @param seller the seller
   * @return the int
   */
  int creatVisitRequest(int address, String plageHoraire, int seller);

  /**
   * Cancel visit request.
   *
   * @param visitRequest the visit request
   * @param explanatoryNoteOfCancellation the explanatory note of cancellation
   * @return true, if successful
   */
  boolean cancelVisitRequest(int visitRequest, String explanatoryNoteOfCancellation);

  /**
   * Confirm visit request.
   *
   * @param visitRequest the visit request
   * @param chosenDate the chosen date
   * @param immediatelyAvailable the immediately available
   * @return true, if successful
   */
  boolean confirmVisitRequest(int visitRequest, String chosenDate, boolean immediatelyAvailable);

  /**
   * Select all visit request.
   *
   * @return the deque
   */
  Deque<VisitRequestDTO> selectAllVisitRequest();

  /**
   * Selectt visit request by id.
   *
   * @param byUserId the byUserId
   * @return the deque
   */
  Deque<VisitRequestDTO> selectVisitRequestById(int byUserId);

}
