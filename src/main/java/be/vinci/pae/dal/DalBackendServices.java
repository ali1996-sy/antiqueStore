package be.vinci.pae.dal;

import java.sql.PreparedStatement;

// TODO: Auto-generated Javadoc
/**
 * The Interface DalServices.
 */
public interface DalBackendServices {
  /**
   * Gets the prepared statement.
   *
   * @param request the request
   * @return the prepared statement
   */
  PreparedStatement getPreparedStatement(String request);

  /**
   * Gets the connection.
   *
   * 
   */
  void getConnection();



}
