package com.doo.finalActv.beautymaker.session;

import com.doo.finalActv.beautymaker.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SessionManagerTest {
  
  public SessionManagerTest() {
  }
  
  @BeforeAll
  public static void setUpClass() {
    this.createTestUser();
  }
  
  @AfterAll
  public static void tearDownClass() {
    SessionManager.getInstance().logout();
    this.deleteTestUser();
  }
  
  @BeforeEach
  public void setUp() {
    SessionManager sessionManager = SessionManager.getInstance();
  }
  
  @AfterEach
  public void tearDown() {
  }

  @Test
  public void testGetInstance() {
    SessionManager result = SessionManager.getInstance();
    assertNotNull(result);
    assertTrue(result instanceof SessionManager);

    SessionManager shouldBeSameInstance = SessionManager.getInstance();
    assertSame(result, shouldBeSameInstance, "getInstance should return the same instance");
  }

  @Test
  public void testGetUserReturnsNullIfUserNotLogged() {
    User result = SessionManager.getInstance().getUser();
    assertNull(result, "getUser should return null if no user is logged in");
  }

  /**
   * Test of userIsLoggedIn method, of class SessionManager.
   * This test checks when method should return false.
   */
  @Test
  public void testUserIsLoggedInFalseCase() {
    boolean result = SessionManager.getInstance().userIsLoggedIn();
    assertFalse(result, "userIsLoggedIn should return false when no user is logged in");
  }

  // ----------------

  /**
  *  Below tests needs that user is logged in
  */

  @Test
  public void testGetUser() {
    TestUtils.createTestUser();
    TestUtils.loginTestUser();

    User expectedUser = TestUtils.getTestUser();
    User result = sessionManager.getUser();
    assertNotNull(result, "getUser should return a User object if user is logged in");
    assertEquals(expectedUser, result, "getUser should return the logged-in user");
  }
}
