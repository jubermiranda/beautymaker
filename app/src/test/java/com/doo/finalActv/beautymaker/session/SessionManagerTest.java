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
  }
  
  @AfterEach
  public void tearDown() {
  }

  /**
   * Test of getInstance method, of class SessionManager.
   */
  @Test
  public void testGetInstance() {
    System.out.println("getInstance");
    SessionManager expResult = null;
    SessionManager result = SessionManager.getInstance();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getUser method, of class SessionManager.
   */
  @Test
  public void testGetUser() {
    System.out.println("getUser");
    SessionManager instance = null;
    User expResult = null;
    User result = instance.getUser();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of userIsLoggedIn method, of class SessionManager.
   */
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
