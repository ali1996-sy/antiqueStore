package be.vinci.pae.dal.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Set;

import be.vinci.pae.biz.dto.FurnitureDTO;
import be.vinci.pae.biz.factory.FurnitureFactory;
import be.vinci.pae.dal.DalBackendServices;
import be.vinci.pae.dal.dao.FurnitureDAO;
import be.vinci.pae.exception.FatalException;
import jakarta.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class FurnitureDAOImpl.
 */
public class FurnitureDAOImpl implements FurnitureDAO {

  /** The furniture factory. */
  @Inject
  private FurnitureFactory furnitureFactory;

  /** The dal service. */
  @Inject
  private DalBackendServices dalService;

  /**
   * Select dynamic search.
   *
   * @param customerName the customer name
   * @param minPrice the min price
   * @param maxPrice the max price
   * @param types the types
   * @return the list
   */
  @Override
  public Deque<FurnitureDTO> selectDynamicSearch(String customerName, double minPrice,

      double maxPrice, Set<String> types) {

    Deque<FurnitureDTO> listFurniture = new ArrayDeque<FurnitureDTO>();
    String query = getQuery(customerName, minPrice, maxPrice, types);
    PreparedStatement ps = dalService.getPreparedStatement(query);
    try {
      int counter = 1;
      if (!customerName.isEmpty()) {
        ps.setString(counter, customerName);
        counter++;
      }
      if (minPrice >= 0 && maxPrice > minPrice) {
        ps.setDouble(counter, minPrice);
        counter++;
        ps.setDouble(counter, maxPrice);
        counter++;
      } else if (minPrice >= 0 && maxPrice < minPrice) {
        ps.setDouble(counter, minPrice);
        counter++;
      } else if (maxPrice > 0 && maxPrice > minPrice) {
        ps.setDouble(counter, maxPrice);
        counter++;
      }
      for (String type : types) {
        ps.setString(counter, type);
        counter++;
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        listFurniture.add(setAttributByResultSet(rs));
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return listFurniture;
  }

  /**
   * Sets the attribut by result set.
   *
   * @param rs the rs
   * @return the furniture DTO
   */
  public FurnitureDTO setAttributByResultSet(ResultSet rs) {

    try {
      FurnitureDTO furniture = furnitureFactory.getFurnitureDTO();
      furniture.setBuyer(rs.getInt("buyer"));
      furniture.setDateOfSale(rs.getDate("date_of_sale"));
      furniture.setDelivery(rs.getInt("delivery"));
      furniture.setDepositDate(rs.getDate("deposit_date"));
      furniture.setDescription(rs.getString("description"));
      furniture.setFavouritePhoto(rs.getInt("favorite_photo"));
      furniture.setFurnitureCollectionDate(rs.getDate("furniture_collection_date"));
      furniture.setFurnitureId(rs.getInt("furniture_id"));
      furniture.setFurnitureType(rs.getString("type_furniture"));
      furniture.setPurchasePrice(rs.getInt("purchase_price"));
      furniture.setRequestForVisit(rs.getInt("request_for_visit"));
      furniture.setSellingPrice(rs.getInt("selling_price"));
      furniture.setSpecialSalePrice(rs.getInt("special_sale_price"));
      furniture.setState(rs.getString("state"));
      furniture.setWithdrawalDate(rs.getDate("withdrawal_date"));
      return furniture;
    } catch (SQLException e) {
      throw new FatalException("On peut pas renvoyer de null");
    }

  }


  /**
   * Gets the query.
   *
   * @param customerName the customer name
   * @param minPrice the min price
   * @param maxPrice the max price
   * @param types the types
   * @return the query
   */
  private String getQuery(String customerName, double minPrice, double maxPrice,
      Set<String> types) {
    String query = "";
    Deque<String> conditions = new ArrayDeque<String>();
    if (!customerName.isEmpty()) {
      query = "SELECT DISTINCT f.*,t.name as type_furniture "
          + "FROM project.furniture f, project.photos p"
          + ",project.furniture_types t ,project.users u " + "WHERE f.furniture_id=p.furniture AND "
          + "t.furniture_type_id=f.furniture_type  AND u.user_id=f.buyer AND ";
      conditions.add("u.last_name = ?");
    } else {
      query = "SELECT DISTINCT f.*,t.name as type_furniture "
          + "FROM project.furniture f, project.photos p,project.furniture_types t "
          + "WHERE f.furniture_id=p.furniture AND t.furniture_type_id=f.furniture_type AND  ";
    }
    if (minPrice >= 0 && maxPrice > minPrice) {
      conditions.add("f.selling_price >= ? AND f.selling_price <= ?");

    } else if (minPrice >= 0 && maxPrice < minPrice) {
      conditions.add("f.selling_price >= ? ");
    } else if (maxPrice > 0 && maxPrice > minPrice) {
      conditions.add(" f.selling_price <= ? ");
    }
    if (!types.isEmpty()) {
      typeConditions(types, conditions);
    }
    if (conditions.isEmpty()) {
      query = "SELECT DISTINCT f.*,t.name as type_furniture "
          + "FROM project.furniture f, project.photos p,project.furniture_types t "
          + "WHERE f.furniture_id=p.furniture AND t.furniture_type_id=f.furniture_type  ";
    }
    query = createQuery(query, conditions);
    return query;
  }

  /**
   * Creates the query.
   *
   * @param query the query
   * @param conditions the conditions
   * @return the string
   */
  private String createQuery(String query, Deque<String> conditions) {
    boolean firstCondition = true;
    for (String condition : conditions) {
      if (firstCondition) {
        query = query.concat(condition);
        firstCondition = false;
      } else {
        query = query.concat(" AND ").concat(condition);
      }

    }
    return query;
  }

  /**
   * Type conditions.
   *
   * @param types the types
   * @param conditions the conditions
   */
  private void typeConditions(Set<String> types, Deque<String> conditions) {
    boolean firstCondition = true;
    String typeCondition = "(";
    int i = 0;
    while (i < types.size()) {
      if (firstCondition) {
        typeCondition = typeCondition.concat("t.name = ?");
        firstCondition = false;
      } else {
        typeCondition = typeCondition.concat(" OR t.name = ?");
      }
      i++;
    }
    typeCondition = typeCondition.concat(")");
    conditions.add(typeCondition);
  }

  /**
   * Change furniture state.
   *
   * @param furnitureId the furniture id
   * @param state the state
   * @return true, if successful
   */
  @Override
  public boolean changeFurnitureState(int furnitureId, String state) {
    PreparedStatement ps;
    if (state.equals("saled")) {
      ps = dalService.getPreparedStatement(
          "UPDATE project.furniture SET state = ?, date_of_sale= ? WHERE furniture_id = ?");
    } else {
      ps = dalService
          .getPreparedStatement("UPDATE project.furniture SET state = ? WHERE furniture_id = ?");
    }


    try {

      ps.setString(1, state.toLowerCase());
      int compteur = 2;
      if (state.equals("saled")) {
        Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
        ps.setTimestamp(compteur, timestamp);
        compteur++;
      }
      ps.setInt(compteur, furnitureId);

      ps.execute();
      return true;
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }

  }

  /**
   * Select all.
   *
   * @return the sets the
   */
  @Override
  public Deque<FurnitureDTO> selectAll() {
    Deque<FurnitureDTO> setFurnitureDTOs = new ArrayDeque<FurnitureDTO>();
    PreparedStatement ps =
        dalService.getPreparedStatement("SELECT DISTINCT f.*,t.name as type_furniture "
            + "FROM project.furniture f, project.photos p,project.furniture_types t "
            + "WHERE f.furniture_id=p.furniture AND t.furniture_type_id=f.furniture_type "
            + "ORDER BY f.furniture_id");
    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        setFurnitureDTOs.add(setAttributByResultSet(rs));
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return setFurnitureDTOs;
  }

  /**
   * Select all by state.
   *
   * @param state the state
   * @return the sets the
   */
  @Override

  public Deque<FurnitureDTO> selectAllByState(String state) {
    Deque<FurnitureDTO> setFurnitureDTOs = new ArrayDeque<FurnitureDTO>();
    PreparedStatement ps =
        dalService.getPreparedStatement("SELECT  DISTINCT f.*,t.name as type_furniture "
            + "FROM project.furniture f, project.photos p,project.furniture_types t "
            + "WHERE f.furniture_id=p.furniture " + "AND t.furniture_type_id=f.furniture_type "
            + "AND f.state= ?");
    try {
      ps.setString(1, state);
    } catch (SQLException se) {
      throw new FatalException(se.getMessage());
    }
    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        setFurnitureDTOs.add(setAttributByResultSet(rs));
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return setFurnitureDTOs;
  }

  /**
   * Select by id.
   *
   * @param furnitureId the furniture id
   * @return the furniture DTO
   */
  @Override
  public FurnitureDTO selectById(int furnitureId) {
    PreparedStatement ps = dalService
        .getPreparedStatement("SELECT f.*,t.name as type_furniture FROM project.furniture f,"
            + " project.photos p,project.furniture_types t WHERE f.furniture_id=p.furniture "
            + "AND t.furniture_type_id=f.furniture_type AND  f.furniture_id = ?");
    try {
      ps.setInt(1, furnitureId);
    } catch (SQLException se) {
      throw new FatalException(se.getMessage());
    }
    try (ResultSet rs = ps.executeQuery()) {
      if (rs.next()) {
        return setAttributByResultSet(rs);
      } else {
        throw new FatalException("Not found!");
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

  /**
   * Select by user id.
   *
   * @param userId the user id
   * @return the deque
   */
  @Override
  public Deque<FurnitureDTO> selectByUserId(int userId) {
    Deque<FurnitureDTO> setFurnitureDTOs = new ArrayDeque<FurnitureDTO>();
    PreparedStatement ps = dalService
        .getPreparedStatement("SELECT f.*,t.name as type_furniture FROM project.furniture f,"
            + " project.photos p,project.furniture_types t,"
            + "project.visit_requests v WHERE f.favorite_photo = p.photo_id "
            + "AND t.furniture_type_id=f.furniture_type AND"
            + " v.visit_id=f.request_for_visit AND (f.buyer = ? OR v.seller= ?)");
    try {
      ps.setInt(1, userId);
      ps.setInt(2, userId);
    } catch (SQLException se) {
      throw new FatalException(se.getMessage());
    }
    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        setFurnitureDTOs.add(setAttributByResultSet(rs));
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return setFurnitureDTOs;
  }

  /**
   * Update selling price.
   *
   * @param furnitureId the furniture id
   * @param price the price
   * @return true, if successful
   */
  @Override
  public boolean updateSellingPrice(int furnitureId, double price) {
    PreparedStatement ps = dalService.getPreparedStatement(
        "UPDATE project.furniture SET selling_price = ? WHERE furniture_id = ?");
    try {
      ps.setInt(1, furnitureId);
      ps.setDouble(2, price);
      return ps.execute();
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }

  }

  /**
   * Update furniture.
   *
   * @param furniture the furniture
   * @return true, if successful
   */
  @Override
  public boolean updateFurniture(FurnitureDTO furniture) {
    PreparedStatement ps = dalService
        .getPreparedStatement("UPDATE project.furniture SET deposit_date= ? , selling_price= ? "
            + ", special_sale_price= ? , state= ? , date_of_sale= ? , "
            + "purchase_price= ? , withdrawal_date= ? , favorite_photo= ? , description= ? ,"
            + "furniture_collection_date= ? , buyer= ?" + " WHERE furniture_id = ? ");
    try {
      if (furniture.getDepositDate() != null) {

        ps.setDate(1, new java.sql.Date(furniture.getDepositDate().getTime()));
      } else {
        ps.setDate(1, null);
      }
      ps.setDouble(2, furniture.getSellingPrice());
      ps.setDouble(3, furniture.getSpecialSalePrice());
      ps.setString(4, furniture.getState());
      if (furniture.getDateOfSale() != null) {
        ps.setDate(5, new java.sql.Date(furniture.getDateOfSale().getTime()));
      } else {
        ps.setDate(5, null);
      }
      ps.setDouble(6, furniture.getPurchasePrice());
      if (furniture.getWithdrawalDate() != null) {
        ps.setDate(7, new java.sql.Date(furniture.getWithdrawalDate().getTime()));
      } else {
        ps.setDate(7, null);
      }
      ps.setInt(8, furniture.getFavouritePhoto());
      ps.setString(9, furniture.getDescription());
      if (furniture.getFurnitureCollectionDate() != null) {
        ps.setDate(10, new java.sql.Date(furniture.getFurnitureCollectionDate().getTime()));
      } else {
        ps.setDate(10, null);
      }
      if (furniture.getBuyer() != 0) {
        ps.setInt(11, furniture.getBuyer());
      } else {
        ps.setObject(11, null);
      }
      ps.setInt(12, furniture.getFurnitureId());
      ps.execute();

      return true;
    } catch (SQLException e) {

      throw new FatalException(e.getMessage());
    }

  }



  /**
   * Insert furniture.
   *
   * @param furniture the furniture
   * @param idType the id type
   * @return true, if successful
   */

  @Override
  public int insertFurniture(FurnitureDTO furniture, int idType) {

    PreparedStatement ps = dalService.getPreparedStatement("INSERT INTO project."
        + "furniture( state ,  furniture_type , request_for_visit,description) "
        + "VALUES (?,?, ?, ?) returning furniture_id");

    try {

      ps.setString(1, furniture.getState());
      ps.setInt(2, idType);
      ps.setInt(3, furniture.getRequestForVisit());
      ps.setString(4, furniture.getDescription());


      ResultSet rs = ps.executeQuery();

      int id = getId(rs);

      return id;

    } catch (SQLException e) {


      throw new FatalException(e.getMessage());

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
   * Select ALL types.
   *
   * @return the deque
   */
  @Override
  public Deque<String> selectALLTypes() {
    PreparedStatement ps =
        dalService.getPreparedStatement("SELECT t.name FROM  project.furniture_types t");
    Deque<String> types = new ArrayDeque<String>();
    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        types.add(rs.getString(1));
      }

    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return types;
  }



  /**
   * Select all furniture for sale.
   *
   * @return the list
   */
  @Override
  public List<FurnitureDTO> selectAllFurnitureForSale() {
    List<FurnitureDTO> listFurnitureDTOs = new ArrayList<FurnitureDTO>();
    PreparedStatement ps =
        dalService.getPreparedStatement("SELECT  DISTINCT f.*,t.name as type_furniture "
            + "FROM project.furniture f, project.photos p, project.furniture_types t "
            + "WHERE f.furniture_id=p.furniture AND t.furniture_type_id=f.furniture_type "
            + "AND f.state = 'for_sale' ");

    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        listFurnitureDTOs.add(setAttributByResultSet(rs));
      }
    } catch (SQLException e) {

      throw new FatalException(e.getMessage());
    }
    return listFurnitureDTOs;
  }

  /**
   * Select id type by value.
   *
   * @param type the type
   * @return the int
   */
  @Override
  public int selectIdTypeByValue(String type) {
    PreparedStatement ps = dalService.getPreparedStatement(
        "SELECT t.furniture_type_id FROM project.furniture_types t WHERE t.name=?");
    try {
      ps.setString(1, type);
      try (ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
          return rs.getInt(1);
        }
        return 0;
      }
    } catch (SQLException e1) {

      throw new FatalException(e1.getMessage());
    }
  }

  /**
   * Adds the antique price.
   *
   * @param furnitureId the furniture id
   * @param antiquePrice the antique price
   * @return true, if successful
   */
  public boolean addAntiquePrice(int furnitureId, double antiquePrice) {

    PreparedStatement ps = dalService.getPreparedStatement(
        "UPDATE project.furniture SET special_sale_price = ? WHERE furniture_id = ?");

    try {
      ps.setDouble(1, antiquePrice);
      ps.setInt(2, furnitureId);
      ps.execute();
      return true;
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }

  }

  /**
   * Sale furniture.
   *
   * @param furnitureId the furniture id
   * @param state the state
   * @param userId the user id
   * @param price the price
   * @return true, if successful
   */
  @Override
  public boolean saleFurniture(int furnitureId, String state, int userId, double price) {
    PreparedStatement ps = dalService.getPreparedStatement(
        "UPDATE project.furniture SET state = ?, date_of_sale= ?, buyer= ?, selling_price = ? "
            + "WHERE furniture_id = ?");



    try {
      ps.setString(1, state.toLowerCase());
      ps.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
      ps.setInt(3, userId);
      ps.setDouble(4, price);
      ps.setInt(5, furnitureId);

      ps.execute();
      return true;
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

}
