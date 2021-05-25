package be.vinci.pae.biz.ucc;

import java.util.Deque;

import be.vinci.pae.biz.dto.VisitRequestDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface VisitRequestUCC.
 */
public interface VisitRequestUCC {

  /**
   * Creates the visit request.
   *
   * @param visitRequestDTO the visit request DTO
   * @return the int
   */
  int createVisitRequest(VisitRequestDTO visitRequestDTO);

  /**
   * Cancel visit request.
   *
   * @param visitRequest the visit request
   * @return true, if successful
   */
  boolean cancelVisitRequest(VisitRequestDTO visitRequest);

  /**
   * Confirm visit request.
   *
   * @param visitRequest the visit request
   * @return true, if successful
   */
  boolean confirmVisitRequest(VisitRequestDTO visitRequest);

  /**
   * Selec allt visit request.
   *
   * @return the deque
   */
  Deque<VisitRequestDTO> selectAlltVisitRequest();


  /**
   * Select visit request by id.
   *
   * @param byUserId the byUserId
   * @return the deque
   */
  Deque<VisitRequestDTO> selectVisitRequestById(int byUserId);

}
