package be.vinci.pae.dal.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import be.vinci.pae.biz.dto.AddressDTO;
import be.vinci.pae.biz.dto.UserDTO;
import be.vinci.pae.biz.factory.AddressFactory;
import be.vinci.pae.biz.factory.UserFactory;
import be.vinci.pae.dal.DalBackendServices;
import be.vinci.pae.dal.dao.UserDAO;
import be.vinci.pae.exception.FatalException;
import jakarta.inject.Inject;


// TODO: Auto-generated Javadoc
/**
 * The Class UserDAOImpl.
 */
public class UserDAOImpl implements UserDAO {
  /** The user factory. */
  @Inject
  UserFactory userFactory;
  /** The dal service. */
  @Inject
  DalBackendServices dalService;

  /** The address factory. */
  @Inject
  AddressFactory addressFactory;



  /**
   * Insert user.
   *
   * @param userDTO the user DTO
   * @param addressId the address id
   * @return the int
   */
  @Override

  public int insertUser(UserDTO userDTO, int addressId) {
    // catched (intern method)
    PreparedStatement insertUser = dalService.getPreparedStatement("INSERT INTO project.users "
        + "(first_name, last_name, registration_date, validate_registration, "
        + "is_administrator, username, email, photo, is_antique_dealer, password, "
        + "buy_furniture_number, sell_furniture_number, address_id)"
        + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING user_id");

    try {
      insertUser.setString(1, userDTO.getFirstName());
      insertUser.setString(2, userDTO.getLastName());
      insertUser.setDate(3, userDTO.getRegistrationDate());
      insertUser.setBoolean(4, userDTO.isValidateRegistration());
      insertUser.setBoolean(5, userDTO.isAdministrator());
      insertUser.setString(6, userDTO.getUsername());
      insertUser.setString(7, userDTO.getEmail());
      insertUser.setString(8, userDTO.getPhoto());
      insertUser.setBoolean(9, userDTO.isAntiqueDealer());
      insertUser.setString(10, userDTO.getPassword());
      insertUser.setInt(11, userDTO.getBuyFurnitureNumber());
      insertUser.setInt(12, userDTO.getSellFurnitureNumber());
      insertUser.setInt(13, addressId);
      ResultSet rs = insertUser.executeQuery();
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
   * Insert one user address.
   *
   * @param addressDTO the address DTO
   * @return address_id.
   */
  public int insertAddress(AddressDTO addressDTO) {
    PreparedStatement insertAddress = dalService.getPreparedStatement(
        "INSERT INTO project.addresses (street, building_number, city, post_code,country)"
            + "VALUES(?,?,?,?,?) RETURNING address_id");
    try {
      insertAddress.setString(1, addressDTO.getStreet());
      insertAddress.setString(2, addressDTO.getBuildingNumber());
      insertAddress.setString(3, addressDTO.getCity());
      insertAddress.setInt(4, addressDTO.getPostCode());
      insertAddress.setString(5, addressDTO.getCountry());
      ResultSet rs = insertAddress.executeQuery();
      return getId(rs);
    } catch (SQLException e) {
      throw new FatalException("Insert error ! \n" + e.getMessage());
    }
  }

  /**
   * Select user by username.
   *
   * @param username the username
   * @return the user DTO
   */
  @Override
  public UserDTO selectUserByUsername(String username) {

    PreparedStatement selectUserByUsername = dalService.getPreparedStatement(

        "SELECT * FROM project.users u,  project.addresses a WHERE u.username = ?");
    try {
      selectUserByUsername.setString(1, username);
      ResultSet rs = selectUserByUsername.executeQuery();
      if (rs.next()) {
        return setAttributByResultSet(rs);
      } else {
        return null;
      }
    } catch (Exception e) {

      throw new FatalException(e.getMessage());
    }


  }

  /**
   * Select user by email.
   *
   * @param email the email
   * @return the user DTO
   */
  @Override
  public UserDTO selectUserByEmail(String email) {
    PreparedStatement selectUserByEmail = dalService
        .getPreparedStatement("SELECT * FROM project.users ,project.addresses a WHERE email=?");
    try {
      selectUserByEmail.setString(1, email);
      ResultSet rs = selectUserByEmail.executeQuery();
      if (rs.next()) {
        return setAttributByResultSet(rs);
      } else {
        return null;
      }
    } catch (SQLException se) {
      throw new FatalException(se.getMessage());
    }
  }

  /**
   * Update admin field.
   *
   * @param userId the user id
   * @param admin the admin
   * @return true, if successful
   */
  public boolean updateAdminField(int userId, boolean admin) {

    PreparedStatement updateAdmin = dalService.getPreparedStatement("UPDATE project.users "
        + "SET is_administrator = ? " + " WHERE user_id = ? RETURNING is_administrator");
    try {

      updateAdmin.setBoolean(1, admin);
      updateAdmin.setInt(2, userId);

      updateAdmin.executeQuery();

      return true;

    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

  /**
   * Update antique dealer field.
   *
   * @param userId the user id
   * @param antiqueDealer the antique dealer
   * @return true, if successful
   */
  @Override
  public boolean updateAntiqueDealerField(int userId, boolean antiqueDealer) {
    PreparedStatement updateAntiqueDealer = dalService.getPreparedStatement("UPDATE project.users "
        + "SET is_antique_dealer = ? " + "WHERE user_id = ? RETURNING is_antique_dealer");
    try {
      updateAntiqueDealer.setBoolean(1, antiqueDealer);
      updateAntiqueDealer.setInt(2, userId);
      updateAntiqueDealer.executeQuery();
      return true;

    } catch (SQLException e) {
      throw new FatalException("update failed" + e.getMessage());
    }
  }

  /**
   * Update validate registration field.
   *
   * @param userId the user id
   * @param validateRegistration the validate registration
   * @return true, if successful
   */
  @Override
  public boolean updateValidateRegistrationField(int userId, boolean validateRegistration) {
    PreparedStatement updateValideRegistration =
        dalService.getPreparedStatement("UPDATE project.users" + " SET validate_registration = ? "
            + "WHERE user_id = ? RETURNING validate_registration");
    try {
      updateValideRegistration.setBoolean(1, validateRegistration);
      updateValideRegistration.setInt(2, userId);

      updateValideRegistration.executeQuery();
      return true;
    } catch (SQLException e) {
      throw new FatalException("update failed" + e.getMessage());
    }
  }

  /**
   * Sets the attribut by result set.
   *
   * @param rs the rs
   * @return the user DTO
   */
  private UserDTO setAttributByResultSet(ResultSet rs) {
    UserDTO user = userFactory.getUserDTO();
    AddressDTO addresDto = addressFactory.getAddressDTO();
    try {
      user.setId(rs.getInt("user_id"));
      user.setLastName(rs.getString("last_name"));
      user.setFirstName(rs.getString("first_name"));
      user.setRegistrationDate(rs.getDate("registration_date"));
      user.setValidateRegistration(rs.getBoolean("validate_registration"));
      user.setAdmin(rs.getBoolean("is_administrator"));
      user.setAntiqueDealer(rs.getBoolean("is_antique_dealer"));
      user.setPhoto(rs.getString("photo"));
      user.setPassword(rs.getString("password"));
      user.setEmail(rs.getString("email"));
      user.setUsername(rs.getString("username"));
      user.setSellFurnitureNumber(rs.getInt("sell_furniture_number"));
      user.setBuyFurnitureNumber(rs.getInt("buy_furniture_number"));
      addresDto.setStreet(rs.getString("street"));
      addresDto.setBuildingNumber(rs.getString("building_number"));
      addresDto.setCity(rs.getString("city"));
      addresDto.setCountry(rs.getString("country"));
      addresDto.setPostCode(rs.getInt("post_code"));
      addresDto.setId(rs.getInt("address_id"));
      user.setAddressDTO(addresDto);
      return user;
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

  /**
   * Gets the user password.
   *
   * @param username the username
   * @return the user password
   */
  public String getUserPassword(String username) {
    PreparedStatement selectUserByUsername = dalService
        .getPreparedStatement("SELECT u.password FROM project.users u WHERE u.username = ?");
    try {
      selectUserByUsername.setString(1, username);
      ResultSet rs = selectUserByUsername.executeQuery();
      if (rs.next()) {
        return rs.getString(1);
      } else {
        throw new FatalException("ResultSet is empty! \n");
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());

    }

  }

  /**
   * Select all user by name or and by post code or and by city.
   *
   * @param name the name
   * @param city the city
   * @param postCode the post code
   * @return the list
   */
  @Override
  public Set<UserDTO> selectAllUserByNameOrAndByPostCodeOrAndByCity(String name, String city,
      int postCode) {
    Set<UserDTO> userDTOSet = new HashSet<UserDTO>();
    PreparedStatement selectAllUserDynamicQuery = selectAllUserDynamicQuery(name, city, postCode);
    if (selectAllUserDynamicQuery == null) {
      throw new FatalException("This prepare statement is null!");
    }
    int compteur = 0;
    try {
      if (!city.isEmpty()) {
        compteur++;
        selectAllUserDynamicQuery.setString(compteur, city);
      }
      if (!name.isEmpty()) {
        compteur++;
        selectAllUserDynamicQuery.setString(compteur, name);
      }

      if (postCode > 0) {
        compteur++;
        selectAllUserDynamicQuery.setInt(compteur, postCode);
      }
      try (ResultSet rs = selectAllUserDynamicQuery.executeQuery()) {
        while (rs.next()) {

          userDTOSet.add(setAttributByResultSet(rs));
        }
      }

      return userDTOSet;
    } catch (Exception e) {
      throw new FatalException(e.getMessage());
    }
  }

  /**
   * Select all user dynamic query.
   *
   * @param name the name
   * @param city the city
   * @param postCode the post code
   * @return the prepared statement
   */
  private PreparedStatement selectAllUserDynamicQuery(String name, String city, int postCode) {
    String query = "";
    Set<String> set = new HashSet<String>();
    if (name.isEmpty() && city.isEmpty() && postCode <= 0) {
      return dalService
          .getPreparedStatement("SELECT DISTINCT u.*, a.* FROM project.users u , project.addresses"
              + " a WHERE a.address_id = u.address_id  ");
    } else {
      if (!name.isEmpty() && city.isEmpty() && postCode <= 0) {
        return dalService.getPreparedStatement(
            "SELECT DISTINCT u.*, a.* FROM project.users u , project.addresses a "
                + "WHERE a.address_id = u.address_id AND u.last_name = ? ");
      } else {
        query = "SELECT * FROM project.users u, project.addresses a "
            + "WHERE a.address_id = u.address_id ";
        if (!name.isEmpty()) {
          set.add("u.last_name = ? ");
        }
        if (!city.isEmpty()) {
          set.add("a.city = ? ");
        }
        if (postCode > 0) {
          set.add("a.post_code = ? ");
        }
        for (String string : set) {
          query = query.concat("AND ").concat(string);
        }
        return dalService.getPreparedStatement(query);
      }
    }
  }

  /**
   * Select user by ID.
   *
   * @param id the id
   * @return the user DTO
   */
  @Override
  public UserDTO selectUserByID(int id) {
    PreparedStatement selectUserByUsername = dalService.getPreparedStatement(
        "SELECT * FROM project.users u,project.addresses a WHERE u.user_id = ?");
    try {
      selectUserByUsername.setInt(1, id);
      ResultSet rs = selectUserByUsername.executeQuery();
      if (rs.next()) {
        return setAttributByResultSet(rs);
      } else {
        throw new FatalException("User not existing !");
      }
    } catch (SQLException a) {
      throw new FatalException(a.getMessage());
    }
  }



  /**
   * Select all user.
   *
   * @return the sets the
   */
  @Override
  public Deque<UserDTO> selectAllUser() {
    PreparedStatement selectUserByUsername = dalService.getPreparedStatement(
        "SELECT * FROM project.users u ,project.addresses a WHERE u.address_id=a.address_id");
    try {
      Deque<UserDTO> userSet = new ArrayDeque<UserDTO>();
      ResultSet rs = selectUserByUsername.executeQuery();
      while (rs.next()) {
        UserDTO user = setAttributByResultSet(rs);
        userSet.add(user);
      }

      return userSet;
    } catch (SQLException a) {
      throw new FatalException(a.getMessage());
    }
  }

  /**
   * Gets the user by username.
   *
   * @param username the username
   * @return the user by username
   */
  @Override
  public UserDTO getUserByUsername(String username) {
    PreparedStatement selectUserByUsername =
        dalService.getPreparedStatement("SELECT * FROM project.users u " + " WHERE u.username = ?");
    try {
      UserDTO user = userFactory.getUserDTO();
      selectUserByUsername.setString(1, username.toLowerCase());
      ResultSet rs = selectUserByUsername.executeQuery();
      if (rs.next()) {
        return setAttributByResultSet(rs);
      }
      return user;
    } catch (SQLException a) {
      throw new FatalException(a.getMessage());
    }
  }

  /**
   * Update sell furniture number.
   *
   * @param id the id
   * @return true, if successful
   */
  @Override
  public boolean updateSellFurnitureNumber(int id) {
    PreparedStatement ps =
        dalService.getPreparedStatement("UPDATE project.users  SET sell_furniture_number "
            + "=sell_furniture_number+1 WHERE user_id= ?");
    try {
      ps.setInt(1, id);
      if (ps.executeUpdate() == 1) {
        return true;
      }
      throw new FatalException("updating failed ");
    } catch (Exception e) {
      throw new FatalException(e.getMessage());
    }
  }

  /**
   * Update buy furniture number .
   *
   * @param id the id
   * @return true, if successful
   */
  @Override
  public boolean updateBuyFurnitureNumber(int id) {
    PreparedStatement ps = dalService
        .getPreparedStatement("UPDATE project.users  SET buy_furniture_number =buy_furniture_number"
            + "+1 WHERE user_id= ?");
    try {
      ps.setInt(1, id);
      if (ps.executeUpdate() == 1) {
        return true;
      }
      throw new FatalException("updating failed ");
    } catch (Exception e) {
      throw new FatalException(e.getMessage());
    }
  }


}
