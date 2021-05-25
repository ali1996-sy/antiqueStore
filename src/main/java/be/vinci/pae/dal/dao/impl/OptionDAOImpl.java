package be.vinci.pae.dal.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import be.vinci.pae.biz.dto.OptionDTO;
import be.vinci.pae.biz.factory.OptionFactory;
import be.vinci.pae.dal.DalBackendServices;
import be.vinci.pae.dal.dao.OptionDAO;
import be.vinci.pae.exception.FatalException;
import jakarta.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class OptionDAOImpl.
 */
public class OptionDAOImpl implements OptionDAO {

  /** The option factory. */
  @Inject
  private OptionFactory optionFactory;

  /** The dal service. */
  @Inject
  private DalBackendServices dalService;

  /**
   * Insert option.
   *
   * @param optionDTO the option DTO
   * @return the int
   */
  @Override
  public int insertOption(OptionDTO optionDTO) {
    int optionId = -1;

    PreparedStatement insertOption = dalService.getPreparedStatement(
        "INSERT INTO project.options " + "(buyer, furniture, state, option_term, date_term ) "
            + "VALUES(?,?,?,?,?)  RETURNING option_id");
    try {
      insertOption.setInt(1, optionDTO.getBuyer());
      insertOption.setInt(2, optionDTO.getFurniture());
      insertOption.setString(3, optionDTO.getState().toLowerCase());
      insertOption.setInt(4, optionDTO.getOptionTerm());
      insertOption.setDate(5, optionDTO.getTermDate());
      ResultSet rs = insertOption.executeQuery();
      optionId = getId(rs);
      return optionId;
    } catch (Exception se) {
      throw new FatalException(se.getMessage());
    }
  }

  /**
   * Gets the id.
   *
   * @param rs the rs
   * @return the id
   */
  private int getId(ResultSet rs) {
    try {
      if (rs.next()) {
        return rs.getInt(1);
      }
    } catch (SQLException se) {
      throw new FatalException(se.getMessage());
    }
    throw new FatalException("Id not found!");
  }

  /**
   * Sets the attribut by result set.
   *
   * @param rs the rs
   * @return the option DTO
   */
  private OptionDTO setAttributByResultSet(ResultSet rs) {
    try {
      OptionDTO option = optionFactory.getOptionDTO();
      basicSetAttributByResultSet(rs, option);
      return option;
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

  /**
   * Sets the attribut by result set with name and description.
   *
   * @param rs the rs
   * @return the option DTO
   */
  private OptionDTO setAttributByResultSetWithNameAndDescription(ResultSet rs) {
    try {
      OptionDTO option = optionFactory.getOptionDTO();
      basicSetAttributByResultSet(rs, option);
      option.setBuyerName(rs.getString("username"));
      option.setDescription(rs.getString("description"));
      option.setAntiqueDealer(rs.getBoolean("is_antique_dealer"));
      return option;
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

  /**
   * Basic set attribut by result set.
   *
   * @param rs the rs
   * @param option the option
   * @throws SQLException the SQL exception
   */
  private void basicSetAttributByResultSet(ResultSet rs, OptionDTO option) throws SQLException {
    option.setBuyer(rs.getInt("buyer"));
    option.setFurniture(rs.getInt("furniture"));
    option.setOptionId(rs.getInt("option_id"));
    option.setOptionTerm(rs.getInt("option_term"));
    option.setState(rs.getString("state"));
    option.setTermDate(rs.getDate("date_term"));
  }


  /**
   * Select all by user id.
   *
   * @param buyerID the buyer ID
   * @return the list
   */
  @Override
  public List<OptionDTO> selectAllByUserId(int buyerID) {
    HashSet<OptionDTO> setOptionDTOs = new HashSet<OptionDTO>();
    PreparedStatement ps =
        dalService.getPreparedStatement("SELECT * FROM project.options o WHERE o.buyer= ?");
    try {
      ps.setInt(1, buyerID);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          setOptionDTOs.add(setAttributByResultSet(rs));
        }
        return new ArrayList<OptionDTO>(setOptionDTOs);
      }
    } catch (SQLException se) {
      throw new FatalException(se.getMessage());
    }
  }



  /**
   * Change option state.
   *
   * @param optionId the option id
   * @param state the state
   * @return true, if successful
   */
  @Override
  public boolean changeOptionState(int optionId, String state) {
    PreparedStatement ps = dalService.getPreparedStatement(
        "UPDATE project.options SET state = ? WHERE option_id = ? RETURNING option_id");
    try {
      ps.setString(1, state.toLowerCase());
      ps.setInt(2, optionId);
      ps.executeQuery();
      return true;
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

  /**
   * Select all.
   *
   * @return the list
   */
  @Override
  public List<OptionDTO> selectAll() {
    HashSet<OptionDTO> listOptionDTOs = new HashSet<OptionDTO>();
    PreparedStatement ps = dalService
        .getPreparedStatement("SELECT o.*, u.username, f.description, u.is_antique_dealer "
            + "FROM project.options o, project.users u,"
            + " project.furniture f WHERE o.buyer=u.user_id AND o.furniture = "
            + "furniture_id ORDER BY o.state DESC");
    try {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          listOptionDTOs.add(setAttributByResultSetWithNameAndDescription(rs));
        }
        return new ArrayList<OptionDTO>(listOptionDTOs);
      }
    } catch (SQLException se) {
      throw new FatalException(se.getMessage());
    }
  }

}
