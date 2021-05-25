package be.vinci.pae.dal.impl;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import be.vinci.pae.dal.DalBackendServices;
import be.vinci.pae.dal.DalServices;
import be.vinci.pae.exception.FatalException;
import be.vinci.pae.utils.Config;

/**
 * The Class DalServicesImpl.
 */
public class DalServicesImpl implements DalBackendServices, DalServices {

  /** The ds. */
  private BasicDataSource ds;

  /** The thread local. */
  private ThreadLocal<Connection> threadLocal;

  /**
   * Instantiates a new dal services impl.
   */
  public DalServicesImpl() {
    this.ds = new BasicDataSource();
    setBasicDataSourceProperties();
    this.threadLocal = new ThreadLocal<Connection>();

  }


  /**
   * Gets the prepared statement.
   *
   * @param request the request
   * @return the prepared statement
   */
  @Override
  public PreparedStatement getPreparedStatement(String request) {
    try {

      Connection connection = threadLocal.get();
      PreparedStatement statement = connection.prepareStatement(request);
      return statement;
    } catch (SQLException e) {
      throw new FatalException(e.getMessage() + " Statement KO !");

    }

  }

  /**
   * Start transaction.
   */
  @Override
  public void startTransaction() {
    try {
      Connection connection = null;
      getConnection();
      connection = threadLocal.get();
      connection.setAutoCommit(false);
    } catch (SQLException e) {

      throw new FatalException("Transaction can't be started \n" + e.getMessage());

    }
  }

  /**
   * Commit transaction.
   */
  @Override
  public void commitTransaction() {
    try {

      Connection conn = threadLocal.get();
      if (conn == null) {
        throw new FatalException("Thread local is empty !");
      }
      conn.commit();
      conn.setAutoCommit(true);
      threadLocal.remove();
      conn.close();
    } catch (Exception e) {
      throw new FatalException("Transaction can't be commited !\n" + e.getMessage());

    }

  }

  /**
   * Roll back transaction.
   */
  @Override
  public void rollBackTransaction() {
    Connection conn = null;
    try {

      conn = threadLocal.get();

      if (conn == null) {
        throw new FatalException("Thread local is empty !");
      }

      conn.rollback();
      conn.setAutoCommit(true);
      threadLocal.remove();
      conn.close();
    } catch (SQLException e) {
      throw new FatalException("Transaction can't be rollback !\n" + e.getMessage());


    }
  }

  /**
   * Gets the connection.
   *
   * 
   */
  @Override
  public void getConnection() {
    try {
      Connection connection = ds.getConnection();
      threadLocal.set(connection);
    } catch (SQLException e) {
      throw new FatalException("This ressources not avaible !\n" + e.getMessage());
    }

  }

  /**
   * Sets the basic data source properties.
   *
   * 
   */
  private BasicDataSource setBasicDataSourceProperties() {

    try {
      ds.setDriverClassName("org.postgresql.Driver");
      ds.setUrl(Config.getProperties("database.url"));
      ds.setUsername(Config.getProperties("database.user"));
      ds.setPassword(Config.getProperties("database.password"));
      ds.setInitialSize(0);
      ds.setMaxTotal(4);
      ds.setMaxIdle(1);
      return ds;
    } catch (Exception e) {
      throw new FatalException("Ressource properties not accepted, " + e.getMessage());
    }


  }



}
