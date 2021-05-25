package be.vinci.pae.biz.ucc;

import java.util.List;

import be.vinci.pae.biz.dto.OptionDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface OptionUCC.
 */
public interface OptionUCC {


  /**
   * Put in option.
   *
   * @param optionDTO the option DTO
   * @return true, if successful
   */
  boolean putInOption(OptionDTO optionDTO);

  /**
   * Cancel an option.
   *
   * @param idOption the ID of the Option
   * @param state the state
   * @return true, if successful
   */
  boolean cancelOption(int idOption, String state);

  /**
   * Cancel an option.
   *
   * @param idOption the ID of the Option
   * @param state the state
   * @return true, if successful
   */
  boolean changeOptionState(int idOption, String state);

  /**
   * Select all option.
   *
   * @return the list
   */
  List<OptionDTO> selectAllOption();
}
