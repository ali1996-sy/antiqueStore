package be.vinci.pae.dal;

// TODO: Auto-generated Javadoc
/**
 * The Interface DalServices.
 */
public interface DalServices {

  /**
   * Start transaction.
   */
  void startTransaction();

  /**
   * Commit transaction.
   */
  void commitTransaction();

  /**
   * Roll back transaction.
   */
  void rollBackTransaction();
}
