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
  }
  
  @AfterAll
  public static void tearDownClass() {
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

  @Test
  public void testUserIsLoggedIn() {
    System.out.println("userIsLoggedIn");
    SessionManager instance = null;
    boolean expResult = false;
    boolean result = instance.userIsLoggedIn();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }
  
}
