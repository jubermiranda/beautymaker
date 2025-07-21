package com.doo.finalActv.beautymaker.ui.client;

import com.doo.finalActv.beautymaker.serivce.event.EventManager;
import com.doo.finalActv.beautymaker.serivce.event.model.NotificationEvent;
import com.doo.finalActv.beautymaker.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServicesViewTest {
  
  public ServicesViewTest() {
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
  public void testAddSomeTestServices() {
    /**
    * Manual test for verifying service data rendering in the view.
    *
    * Although database tests are typically placed elsewhere,
    * this quick manual check is kept here for convenience.
    *
    * This test manually inserts a few service records into the database
    * using raw queries (from `TestUtils`), so that the application can be run in the test
    * environment and the developer can manually verify that the view
    * correctly displays the inserted data.
    */

    // Subscribe to NotificationEvent to print notifications
    EventManager.getInstance().subscribe(
        NotificationEvent.class,
        event -> {
          System.out.println("[Notification]>>>\n" + event.title + "\n" + event.message + "\n<<<\n");
        }
    );
    
    // 30 min
    TestUtils.addTestService("Manicure", "Nail care and polish", 1800);

    // 45 min
    TestUtils.addTestService("Pedicure", "Foot care and massage", 2700);

    // 60 min
    TestUtils.addTestService("Haircut", "Professional haircut service", 3600);

    // 150 min
    TestUtils.addTestService("Hair Color Makeover", "Complete hair coloring and styling", 9000);

    // 120 min
    TestUtils.addTestService("Facial Treatment", "Deep cleansing and rejuvenation", 7200);

    // 180 min
    TestUtils.addTestService("Laser Hair Removal", "Permanent hair removal treatment", 10800);

    // 90 min
    TestUtils.addTestService("Highlights", "Hair highlights and lowlights", 5400);
    
    // wait for the event to be processed
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
}
