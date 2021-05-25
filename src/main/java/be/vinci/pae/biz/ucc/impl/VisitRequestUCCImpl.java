package be.vinci.pae.biz.ucc.impl;

import java.util.Deque;

import be.vinci.pae.biz.dto.VisitRequestDTO;
import be.vinci.pae.biz.ucc.VisitRequestUCC;
import be.vinci.pae.dal.DalServices;
import be.vinci.pae.dal.dao.VisitRequestDAO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.FatalException;
import jakarta.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class VisitRequestUCCImpl.
 */
public class VisitRequestUCCImpl implements VisitRequestUCC {
  /** The user DAO. */
  @Inject
  private VisitRequestDAO visitRequestDAO;

  /** The dal service. */
  @Inject
  DalServices dalService;



  /**
   * Creates the visit request.
   *
   * @param visitRequestDTO the visit request DTO
   * @return the int
   */
  @Override
  public int createVisitRequest(VisitRequestDTO visitRequestDTO) {
    dalService.startTransaction();

    try {

      String timeSlot = visitRequestDTO.getTimeSlot();
      int sellerId = visitRequestDTO.getSeller().getId();
      int address = visitRequestDTO.getFurnitureAddress().getId();
      int visitRequestId = visitRequestDAO.creatVisitRequest(address, timeSlot, sellerId);
      if (visitRequestId < 0) {
        dalService.rollBackTransaction();
        throw new BizException("VisitRequestNoAdded");
      }


      dalService.commitTransaction();
      return visitRequestId;
    } catch (Exception e) {

      throw new BizException(e.getMessage());
    }

  }

  /**
   * Cancel visit request.
   *
   * @param visitRequest the visit request
   * @return true, if successful
   */
  @Override
  public boolean cancelVisitRequest(VisitRequestDTO visitRequest) {
    dalService.startTransaction();

    try {
      visitRequestDAO.cancelVisitRequest(visitRequest.getVisitId(),
          visitRequest.getCancellationsDue());
      dalService.commitTransaction();
      return true;
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw new BizException(e.getMessage());
    }
  }

  /**
   * Confirm visit request.
   *
   * @param visitRequest the visit request
   * @return true, if successful
   */
  @Override
  public boolean confirmVisitRequest(VisitRequestDTO visitRequest) {
    dalService.startTransaction();
    try {
      visitRequestDAO.confirmVisitRequest(visitRequest.getVisitId(), visitRequest.getChosenDate(),
          visitRequest.isImmediatelyAvailable());
      dalService.commitTransaction();
      return true;
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw new BizException(e.getMessage());

    }

  }

  /**
   * Selec allt visit request.
   *
   * @return the deque
   */
  @Override
  public Deque<VisitRequestDTO> selectAlltVisitRequest() {
    dalService.startTransaction();

    try {
      Deque<VisitRequestDTO> list = visitRequestDAO.selectAllVisitRequest();
      dalService.commitTransaction();
      return list;
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw new BizException(e.getMessage());

    }

  }

  /**
   * Select visit request by id.
   *
   * @param byUserId the by user id
   * @return the deque
   */
  @Override
  public Deque<VisitRequestDTO> selectVisitRequestById(int byUserId) {
    dalService.startTransaction();

    try {
      Deque<VisitRequestDTO> list = visitRequestDAO.selectVisitRequestById(byUserId);
      dalService.commitTransaction();
      return list;
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw new BizException(e.getMessage());

    }

  }

}
