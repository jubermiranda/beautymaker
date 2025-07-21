package com.doo.finalActv.beautymaker.ui;

import com.doo.finalActv.beautymaker.serivce.event.EventManager;
import com.doo.finalActv.beautymaker.serivce.event.model.NotificationEvent;
import com.doo.finalActv.beautymaker.utils.TestUtils;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeHomeViewTest {

  public EmployeeHomeViewTest() {
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
  public void testAddSomeEmployeeUsers() {
    // Subscribe to the event manager to listen for notification events
    // when running the test, so we can see the output of some errors 
    // or the sucessful operations
    EventManager.getInstance().subscribe(
        NotificationEvent.class,
        event -> {
          System.out.println("[Notification]>>>\n" + event.title + "\n" + event.message + "\n<<<\n");
        }
    );

    // to add employee users, change the params as needed and run the test
    // or add multiple calls to this method with different parameters
    // TIP: trying to add an employee with the same email/username as an existing user in database
    // will show an error notification
    TestUtils.createTestEmployee(
        "Alice",
        "alice.b@mail.com",
        "alice1234".toCharArray(),
        LocalDate.of(1990, 5, 15),
        LocalDate.of(2020, 1, 10)
    );
    TestUtils.createTestEmployee(
        "Bob",
        "bob.the.bob@mail.com",
        "bobthebob1234".toCharArray(),
        LocalDate.of(1985, 3, 20),
        LocalDate.of(2009, 6, 1)
    );
    TestUtils.createTestEmployee(
        "Mary Jane",
        "im_jane_mary@gmail.com",
        "maryjane1234".toCharArray(),
        LocalDate.of(2000, 9, 27),
        LocalDate.of(2020, 3, 15)
    );
    TestUtils.createTestEmployee(
        "John Doe",
        "john_doe@gmail.com",
        "john1234".toCharArray(),
        LocalDate.of(1995, 11, 5),
        LocalDate.of(2021, 2, 20)
    );
    TestUtils.createTestEmployee(
        "Jane Smith",
        "smith.jane@gmail.com",
        "janesmith1234".toCharArray(),
        LocalDate.of(1992, 7, 30),
        LocalDate.of(2019, 8, 25)
    );
    TestUtils.createTestEmployee(
        "Sarah Johnson",
        "sarah_jh@mail.com",
        "sarahjohnson1234".toCharArray(),
        LocalDate.of(1988, 4, 10),
        LocalDate.of(2018, 5, 5)
    );
    TestUtils.createTestEmployee(
        "Michael Brown",
        "mich_brown@outlook.com",
        "michaelbrown1234".toCharArray(),
        LocalDate.of(1993, 12, 15),
        LocalDate.of(2022, 3, 1)
    );



    // wait for the event to be processed
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testAddSomeEmployeesRatings(){
    EventManager.getInstance().subscribe(
        NotificationEvent.class,
        event -> {
          System.out.println("[Notification]>>>\n" + event.title + "\n" + event.message + "\n<<<\n");
        }
    );

    // bob ratings
    TestUtils.addTestRatingForEmployee("Bob", 5, "Great service!");
    TestUtils.addTestRatingForEmployee("Bob", 4, "Very good, but could be better.");
    TestUtils.addTestRatingForEmployee("Bob", 2, "I had to wait for almost an hour before being attended to, which was quite frustrating. I understand that delays can happen, but this really affected my experience. I hope things can be more punctual next time.");
    TestUtils.addTestRatingForEmployee("Bob", 4, "Average service, nothing special.");
    TestUtils.addTestRatingForEmployee("Bob", 3, "Good service, but not exceptional.");

    // alice ratings
    TestUtils.addTestRatingForEmployee("Alice", 5, "Excellent service! Highly recommend.");
    TestUtils.addTestRatingForEmployee("Alice", 4, "");
    TestUtils.addTestRatingForEmployee("Alice", 5, "Good service, very professional.");
    TestUtils.addTestRatingForEmployee("Alice", 4, "I had a great experience with Alice");
    TestUtils.addTestRatingForEmployee("Alice", 4, "Good");
    TestUtils.addTestRatingForEmployee("Alice", 5, "Amazing service! Will come back again.");
    TestUtils.addTestRatingForEmployee("Alice", 3, "Average service. I expected more.");
    TestUtils.addTestRatingForEmployee("Alice", 5, "Absolutely fantastic!!!");
    TestUtils.addTestRatingForEmployee("Alice", 4, "Very good, but could be better.");

    // mary ratings
    TestUtils.addTestRatingForEmployee("Mary Jane", 5, "Great service! Very satisfied.");
    TestUtils.addTestRatingForEmployee("Mary Jane", 5, "I had a wonderful experience with Mary Jane. She was very attentive and made sure I was comfortable throughout the entire process. The service was top-notch, and I left feeling pampered and relaxed. Highly recommend her services!");
    TestUtils.addTestRatingForEmployee("Mary Jane", 5, "Mary Jane is an amazing employee! She always goes above and beyond to make sure her clients are happy. Her attention to detail is impeccable, and she has a great sense of style. I always leave her salon feeling like a million bucks!");
    TestUtils.addTestRatingForEmployee("Mary Jane", 5, "Best service ever! Mary Jane is a true professional and knows how to make her clients feel special. She takes the time to listen to what you want and delivers results that exceed expectations. I can't recommend her enough!");
    TestUtils.addTestRatingForEmployee("Mary Jane", 5, "Mary is an absolute gem! makes you feel like a VIP from the moment you walk in. Her skills are unmatched and she has a way of making you feel like the most important person in the room. I wish i had found her sooner!");
    TestUtils.addTestRatingForEmployee("Mary Jane", 5, "I give Mary 5 cause is not possible to give her 10 or more!");
    TestUtils.addTestRatingForEmployee("Mary Jane", 5, "Mary Jane is a true artist! the most talented and skilled employee I have ever met");

    // wait for notifications to be processed
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
