package be.vinci.pae.dal.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Deque;

import be.vinci.pae.biz.dto.AddressDTO;
import be.vinci.pae.biz.dto.UserDTO;
import be.vinci.pae.biz.dto.VisitRequestDTO;
import be.vinci.pae.biz.factory.AddressFactory;
import be.vinci.pae.biz.factory.UserFactory;
import be.vinci.pae.biz.factory.VisitRequestFactory;
import be.vinci.pae.dal.DalBackendServices;
import be.vinci.pae.dal.dao.VisitRequestDAO;
import be.vinci.pae.exception.FatalException;
import jakarta.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class VisitRequestDAOImpl.
 */
public class VisitRequestDAOImpl implements VisitRequestDAO {

  /** The visit request factory. */
  @Inject
  VisitRequestFactory visitRequestFactory;
  /** The dal service. */
  @Inject
  DalBackendServices dalService;

  /** The address factory. */
  @Inject
  AddressFactory addressFactory;

  /** The user factory. */
  @Inject
  UserFactory userFactory;

  /**
   * Creat visit request.
   *
   * @param address the address
   * @param plageHoraire the plage horaire
   * @param seller the seller
   * @return the int
   */
  @Override
  public int creatVisitRequest(int address, String plageHoraire, int seller) {
    PreparedStatement creatVisitRequest =
        dalService.getPreparedStatement("INSERT INTO project.visit_requests("
            + "request_date, time_slot, furniture_address, state,seller)"
            + "VALUES (?, ?, ?, ?, ?) returning visit_id");
    try {
      creatVisitRequest.setString(1, Date.valueOf(LocalDate.now()).toString());
      creatVisitRequest.setString(2, plageHoraire);
      creatVisitRequest.setInt(3, address);
      creatVisitRequest.setString(4, VisitRequestDTO.State.PENDING.getValue());
      creatVisitRequest.setInt(5, seller);
      ResultSet rs = creatVisitRequest.executeQuery();
      return getId(rs);
    } catch (SQLException se) {
      throw new FatalException("Insert error ! \n" + se.getMessage());
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
      } else {
        throw new FatalException("ResultSet is empty! \n");
      }
    } catch (SQLException se) {
      throw new FatalException(se.getMessage());
    }
  }

  /**
   * Cancel visit request.
   *
   * @param visitRequest the visit request
   * @param explanatoryNoteOfCancellation the explanatory note of cancellation
   * @return true, if successful
   */
  @Override
  public boolean cancelVisitRequest(int visitRequest, String explanatoryNoteOfCancellation) {
    PreparedStatement cancelVisitRequest =
        dalService.getPreparedStatement(" UPDATE project.visit_requests"
            + " SET  collections_due=? , state=? " + " WHERE visit_id=?");
    try {
      cancelVisitRequest.setString(1, explanatoryNoteOfCancellation);
      cancelVisitRequest.setInt(3, visitRequest);
      cancelVisitRequest.setString(2, VisitRequestDTO.State.CANCELLED.getValue());
      if (cancelVisitRequest.executeUpdate() == 1) {
        return true;
      } else {
        throw new FatalException("Update : false !");
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }

  }

  /**
   * Confirm visit request.
   *
   * @param visitRequest the visit request
   * @param chosenDate the chosen date
   * @param immediatelyAvailable the immediately available
   * @return true, if successful
   */
  @Override
  public boolean confirmVisitRequest(int visitRequest, String chosenDate,
      boolean immediatelyAvailable) {
    PreparedStatement confirmVisitRequest =
        dalService.getPreparedStatement("UPDATE project.visit_requests"
            + " SET  chosen_date=? , state=? ,immediatly_avialable=? " + " WHERE visit_id=? ");
    try {
      confirmVisitRequest.setString(1, chosenDate);
      confirmVisitRequest.setBoolean(3, immediatelyAvailable);
      confirmVisitRequest.setString(2, VisitRequestDTO.State.FINISHED.getValue());
      confirmVisitRequest.setInt(4, visitRequest);
      if (confirmVisitRequest.executeUpdate() == 1) {
        return true;
      } else {
        throw new FatalException("Update : false !");
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }

  }

  /**
   * Gets the visit request.
   *
   * @param rs the rs
   * @param addresDto the addres dto
   * @return the visit request
   */
  private VisitRequestDTO getVisitRequest(ResultSet rs, AddressDTO addresDto) {
    try {
      VisitRequestDTO visitRequest = visitRequestFactory.getVisitRequestDTO();
      visitRequest.setCancellationsDue(rs.getString("collections_due"));
      visitRequest.setChosenDate(rs.getString("chosen_date"));
      visitRequest.setImmediatelyAvailable(rs.getBoolean("immediatly_avialable"));
      visitRequest.setRequestDate(rs.getString("request_date"));
      visitRequest.setSeller(getUserByResultSet(rs));
      visitRequest.setState(rs.getString("state"));
      visitRequest.setTimeSlot(rs.getString("time_slot"));
      visitRequest.setVisitId(rs.getInt("visit_id"));
      addresDto.setStreet(rs.getString("street"));
      addresDto.setBuildingNumber(rs.getString("building_number"));
      addresDto.setCity(rs.getString("city"));
      addresDto.setCountry(rs.getString("country"));
      addresDto.setPostCode(rs.getInt("post_code"));
      visitRequest.setFurnitureAddress(addresDto);
      return visitRequest;
    } catch (SQLException e) {
      throw new FatalException(e.getMessage().toString());
    }
  }

  /**
   * Select all visit request.
   *
   * @return the deque
   */
  @Override
  public Deque<VisitRequestDTO> selectAllVisitRequest() {
    PreparedStatement selectVisitRequest = dalService.getPreparedStatement(
        "SELECT v.* ,u.username, u.last_name ,u.first_name ,a.* FROM project.visit_requests v ,"
            + "project.users u , project.addresses a  WHERE v.seller = u.user_id  AND "
            + "v.furniture_address = a.address_id");
    try {
      ResultSet rs = selectVisitRequest.executeQuery();
      Deque<VisitRequestDTO> liste = new ArrayDeque<VisitRequestDTO>();
      while (rs.next()) {
        AddressDTO addresDto = addressFactory.getAddressDTO();
        VisitRequestDTO visitRequest = getVisitRequest(rs, addresDto);
        liste.add(visitRequest);
      }
      return liste;

    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
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

    PreparedStatement selectVisitRequest =
        dalService.getPreparedStatement("SELECT v.* , u.username, u.last_name ,u.first_name"
            + " ,a.* FROM project.visit_requests v "
            + ",project.users u , project.addresses a  WHERE v.seller = u.user_id "
            + " AND v.furniture_address = a.address_id " + "AND u.user_id = ?");
    try {

      selectVisitRequest.setInt(1, byUserId);
      ResultSet rs = selectVisitRequest.executeQuery();
      Deque<VisitRequestDTO> liste = new ArrayDeque<VisitRequestDTO>();
      while (rs.next()) {

        AddressDTO addresDto = addressFactory.getAddressDTO();
        VisitRequestDTO visitRequest = getVisitRequest(rs, addresDto);
        liste.add(visitRequest);
      }
      return liste;

    } catch (SQLException e) {

      throw new FatalException(e.getMessage());
    }
  }

  /**
   * Gets the user by result set.
   *
   * @param rs the rs
   * @return the user by result set
   */
  private UserDTO getUserByResultSet(ResultSet rs) {
    UserDTO user = userFactory.getUserDTO();

    try {
      user.setId(rs.getInt("seller"));
      user.setLastName(rs.getString("last_name"));
      user.setFirstName(rs.getString("first_name"));
      user.setUsername(rs.getString("username"));

      return user;
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

}
