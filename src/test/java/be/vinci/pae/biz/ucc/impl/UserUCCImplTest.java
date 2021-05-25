package be.vinci.pae.biz.ucc.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

import be.vinci.pae.biz.dto.UserDTO;
import be.vinci.pae.biz.factory.UserFactory;
import be.vinci.pae.biz.impl.binder.TestBinder;
import be.vinci.pae.biz.ucc.UserUCC;
import be.vinci.pae.dal.dao.UserDAO;
import be.vinci.pae.exception.BizException;


/**
 * The Class UserUCCImplTest.
 */
class UserUCCImplTest {

  /** The user ucc. */
  private UserUCC userUcc;

  /** The user dao. */
  private UserDAO userDao;

  /** The user factroy. */
  private UserFactory userFactory;

  /** The user dto. */
  private UserDTO userDto;

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @BeforeEach
  void setUp() throws Exception {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestBinder());
    this.userFactory = locator.getService(UserFactory.class);
    userDao = locator.getService(UserDAO.class);
    this.userUcc = locator.getService(UserUCC.class);
    userDto = userFactory.getUserDTO();
    userDto.setFirstName("jean");
    userDto.setLastName("test");
    userDto.setEmail("test@test.com");
    userDto.setPassword(BCrypt.hashpw("123", BCrypt.gensalt()));
    userDto.setUsername("jeantest");
  }

  /**
   * Test login with right password and username.
   */
  @DisplayName("testLoginWithRightPasswordAndRightUsername")

  @Test
  void testLoginWithRightPasswordAndUsername() {
    Mockito.when(userDao.selectUserByUsername("jeantest")).thenReturn(userDto);

    assertDoesNotThrow(() -> userUcc.login("jeanTest", "123"));
  }

  /**
   * Test login with wrong password and wrong username.
   */
  @DisplayName("testLoginWithWrongPasswordAndWrongUsername")

  @Test
  void testLoginWithWrongPasswordAndWrongUsername() {
    Mockito.when(userDao.selectUserByUsername("jeanTest")).thenReturn(null);
    assertThrows(BizException.class, () -> userUcc.login("jnTest", "123"));
  }

  /**
   * Test login with wrong password and right username.
   */
  @DisplayName("testLoginWithWrongPasswordAndRightUsername")

  @Test
  void testLoginWithWrongPasswordAndRightUsername() {
    Mockito.when(userDao.selectUserByUsername("jeanTest")).thenReturn(null);
    assertThrows(BizException.class, () -> userUcc.login("jeanTest", "12"));
  }

  /**
   * Test login with empty password and empty username.
   */
  @DisplayName("testLoginWithEmptyPasswordAndEmptyUsername")

  @Test
  void testLoginWithEmptyPasswordAndEmptyUsername() {
    Mockito.when(userDao.selectUserByUsername("")).thenReturn(null);
    assertThrows(BizException.class, () -> userUcc.login("", ""));
  }

  /**
   * Test login with null password and null username.
   */
  @DisplayName("testLoginWithNullPasswordAndNullUsername")

  @Test
  void testLoginWithNullPasswordAndNullUsername() {
    Mockito.when(userDao.selectUserByUsername(null)).thenReturn(null);
    assertThrows(BizException.class, () -> userUcc.login(null, null));
  }

  /**
   * Test login with wrong password and right email.
   */
  @DisplayName("testLoginWithWrongPasswordAndRightEmail")

  @Test
  void testLoginWithWrongPasswordAndRightEmail() {
    Mockito.when(userDao.selectUserByUsername("test@test.com")).thenReturn(null);
    assertThrows(BizException.class, () -> userUcc.login("test@test.com", "000"));
  }

  /**
   * Test login with wrong password and right email.
   */
  @DisplayName("testLoginWithRightPasswordAndRightEmail")

  @Test
  void testLoginWithRightPasswordAndRightEmail() {
    Mockito.when(userDao.selectUserByUsername("test@test.com")).thenReturn(userDto);
    assertDoesNotThrow(() -> userUcc.login("test@test.com", "123"));
  }

  /**
   * Test login with right password and wrong email.
   */
  @DisplayName("testLoginWithRightPasswordAndWrongEmail")

  @Test
  void testLoginWithRightPasswordAndWrongEmail() {
    Mockito.when(userDao.selectUserByUsername("j@gmail.com")).thenReturn(null);
    assertThrows(BizException.class, () -> userUcc.login("j@gmail.com", "123"));
  }

  /**
   * Test login with wrong password and wrong email.
   */
  @DisplayName("testLoginWithWrongPasswordAndWrongEmail")

  @Test
  void testLoginWithWrongPasswordAndWrongEmail() {
    Mockito.when(userDao.selectUserByUsername("j@gmail.com")).thenReturn(null);
    assertThrows(BizException.class, () -> userUcc.login("j@gmail.com", "000"));
  }

  /**
   * Test login with right password and right email upper case.
   */
  @DisplayName("testLoginWithRightPasswordAndRightEmailUpperCase")

  @Test
  void testLoginWithRightPasswordAndRightEmailUpperCase() {
    Mockito.when(userDao.selectUserByUsername("test@test.com")).thenReturn(userDto);
    assertDoesNotThrow(() -> userUcc.login("tESt@test.com", "123"));
  }

  /**
   * Test login with right password and right username upper case.
   */
  @DisplayName("testLoginWithRightPasswordAndRightUsernameUpperCase")

  @Test
  void testLoginWithRightPasswordAndRightUsernameUpperCase() {
    Mockito.when(userDao.selectUserByUsername("jeantest")).thenReturn(userDto);
    assertDoesNotThrow(() -> userUcc.login("JeanTest", "123"));
  }



}
