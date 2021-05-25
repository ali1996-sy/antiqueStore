package be.vinci.pae.biz.ucc.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import be.vinci.pae.biz.dto.OptionDTO;
import be.vinci.pae.biz.ucc.OptionUCC;
import be.vinci.pae.dal.DalServices;
import be.vinci.pae.dal.dao.OptionDAO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.FatalException;
import jakarta.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class OptionUCCImpl.
 */
public class OptionUCCImpl implements OptionUCC {

  /** The user DAO. */
  @Inject
  private OptionDAO optionDAO;


  /** The dal service. */
  @Inject
  DalServices dalService;

  /**
   * Put in option.
   *
   * @param optionDTO the option DTO
   * @return true, if successful
   */
  @Override
  public boolean putInOption(OptionDTO optionDTO) {

    if (!optionDTO.getState().toLowerCase().equals("pending")) {
      throw new BizException("état invalide !");
    }


    dalService.startTransaction();
    optionDTO.setTermDate(Date.valueOf(LocalDate.now().plusDays(optionDTO.getOptionTerm() + 1)));
    try {
      int optionId = optionDAO.insertOption(optionDTO);

      if (optionId < 0) {
        dalService.rollBackTransaction();
        throw new BizException();
      } else {
        dalService.commitTransaction();
        return true;
      }
    } catch (FatalException e) {
      dalService.rollBackTransaction();
      throw new BizException(e.getMessage());
    }
  }

  /**
   * Cancel option.
   *
   * @param optionId the option id
   * @param state the state
   * @return true, if successful
   */
  @Override
  public boolean cancelOption(int optionId, String state) {
    return changeOptionState(optionId, state);
  }

  /**
   * Cancel option.
   *
   * @param optionId the option id
   * @param state the state
   * @return true, if successful
   */
  @SuppressWarnings("unlikely-arg-type")
  @Override
  public boolean changeOptionState(int optionId, String state) {

    dalService.startTransaction();



    try {
      boolean updated = optionDAO.changeOptionState(optionId, state);
      if (updated) {
        dalService.commitTransaction();
        return true;
      } else {
        dalService.rollBackTransaction();
        return false;
      }
    } catch (FatalException e) {
      throw new BizException(e.getMessage());
    }



  }

  /**
   * Select all option.
   *
   * @return the list
   */
  @Override
  public List<OptionDTO> selectAllOption() {
    dalService.startTransaction();
    try {
      List<OptionDTO> optionDTO = optionDAO.selectAll();
      if (optionDTO == null) {

        dalService.rollBackTransaction();
        throw new BizException();
      } else {
        dalService.commitTransaction();
        return optionDTO;
      }
    } catch (FatalException e) {
      throw new BizException(e.getMessage());

    }
  }
}
