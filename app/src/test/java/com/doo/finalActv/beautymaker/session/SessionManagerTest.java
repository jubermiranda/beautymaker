package com.doo.finalActv.beautymaker.session;

import com.doo.finalActv.beautymaker.model.User;
import com.doo.finalActv.beautymaker.utils.TestUtils;
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
  }
  
  @AfterAll
  public static void tearDownClass() {
  }
  
  @BeforeEach
  public void setUp() {
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
  public void testUserNotLoggedIn() {
    User user = SessionManager.getInstance().getUser();
    boolean isLogged = SessionManager.getInstance().userIsLoggedIn();

    assertNull(user, "getUser should return null if no user is logged in");
    assertFalse(isLogged, "userIsLoggedIn should return false when no user is logged in");
  }

  // ----------------
  /**
  *  Below tests needs that user is logged in
  */

  @Test
  public void testGetUser() {
    SessionManager sessionManager = SessionManager.getInstance();
    TestUtils.loginTestUser();

    User expectedUser = TestUtils.getTestUser();
    User result = sessionManager.getUser();
    assertNotNull(result, "getUser should return a User object if user is logged in");
    assertEquals(expectedUser, result, "getUser should return the logged-in user");
    
    TestUtils.logoutTestUser();
  }

}
