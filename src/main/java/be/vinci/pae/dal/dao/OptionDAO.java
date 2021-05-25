package be.vinci.pae.dal.dao;

import java.util.List;

import be.vinci.pae.biz.dto.OptionDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface OptionDAO.
 */
public interface OptionDAO {

  /**
   * Insert an option.
   *
   * @param optionDTO the option DTO
   * @return the optionID
   */
  int insertOption(OptionDTO optionDTO);

  /**
   * Select search of an option List.
   *
   * @param buyerID the ID of the buyer
   * @return the list of OptionDTO
   */
  List<OptionDTO> selectAllByUserId(int buyerID);

  /**
   * Change option state.
   *
   * @param optionId the option id
   * @param string the string
   * @return true, if successful
   */
  boolean changeOptionState(int optionId, String string);

  /**
   * Select all.
   *
   * @return the list
   */
  List<OptionDTO> selectAll();



}
