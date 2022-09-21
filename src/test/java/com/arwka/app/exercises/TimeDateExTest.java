package com.arwka.app.exercises;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.TimeZone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TimeDateExTest {

  Clock clock = Clock.fixed(Instant.parse("2020-11-30T18:30:24.00Z"), ZoneId.of("UTC"));
  TimeDateEx timeDateEx = new TimeDateEx(clock);
  List<LocalDate> holidayDates = TimeDateExMother.getHolidaysDateList();
  List<DayOfWeek> weekendDays = TimeDateExMother.getWeekendDayList();


  // EX 1
  @Test
  @DisplayName("GetDatesOfThisMonth should return correct dates.")
  public void getDatesOfThisMonth_Test() {
    List<LocalDate> datesOfThisMonth = timeDateEx.getDatesOfThisMonth();

    Assertions.assertAll(
        () -> Assertions.assertEquals(
            30, datesOfThisMonth.size()),
        () -> Assertions.assertEquals(
            LocalDate.of(2020, 11, 1), datesOfThisMonth.get(0)),
        () -> Assertions.assertEquals(
            LocalDate.of(2020, 11, 30), datesOfThisMonth.get(
            datesOfThisMonth.size() - 1)),
        () -> Assertions.assertEquals(datesOfThisMonth.get(0).getMonth(), Month.NOVEMBER)
    );
  }


  // EX 2
  @Test
  @DisplayName("getDateInTwoWeeksInTokyo should return correct date.")
  public void getDateInTwoWeeksInTokyo_Test() {

    ZonedDateTime shouldBeDateTime
        = ZonedDateTime.parse("2020-12-15T03:30:24+09:00[Asia/Tokyo]");

    Assertions.assertEquals(shouldBeDateTime.toLocalDate(), timeDateEx.getDateInTwoWeeksInTokyo());
  }


  // EX 3
  @Test
  @DisplayName("getLocalDateAfterWorkDays should return correct LocalDate")
  public void getLocalDateAfterWorkDays_Test() {
    Assertions.assertEquals(
        LocalDate.of(2020, 12, 28),
        timeDateEx.getLocalDateAfterWorkDays(20, weekendDays, holidayDates)
    );
  }


  // EX 4
  @Test
  @DisplayName("getWorkingTimeBetween with some args should return duration with correct hours.")
  public void getWorkingTimeBetween_Test() {

    Duration resultDuration = timeDateEx.getWorkingTimeBetween(
        LocalDateTime.of(2022, 10, 12, 10, 0, 0),
        LocalDateTime.of(2022, 10, 25, 12, 0, 15),
        8, weekendDays
    );

    Assertions.assertAll(
        () -> Assertions.assertEquals(74, resultDuration.toHours()),
        () -> Assertions.assertEquals(266415, resultDuration.toSeconds())
    );
  }

  @Test
  @DisplayName("getWorkingTimeBetween with date1 after date2 should throws RuntimeException")
  public void getWorkingTimeBetweenThrows_Test() {

    Assertions.assertThrows(RuntimeException.class, () -> {
      timeDateEx.getWorkingTimeBetween(
          LocalDateTime.of(2025, 10, 12, 10, 0, 0),
          LocalDateTime.of(2022, 10, 5, 9, 0, 15),
          8, weekendDays
      );
    }, "dateTime1 is after dateTime2");

  }


  // EX 5
  @Test
  @DisplayName("parseAndGetZonedDateTimeInUtc with parsingString and timeZone should return ZonedDateTime")
  public void parseAndGetZonedDateTimeInUtc_Test() {

    String inputString = "16.09.2016 11::46::01";
    TimeZone inputTimeZone = TimeZone.getTimeZone("Asia/Tokyo");

    ZonedDateTime excepted = ZonedDateTime.parse("2016-09-16T02:46:01Z[UTC]");
    ZonedDateTime result = timeDateEx.parseAndGetZonedDateTimeInUtc(inputString, inputTimeZone);

    Assertions.assertEquals(excepted, result);
  }


  // EX 6
  @Test
  @DisplayName("printTimeInAllTimeZones should print time in all time zones and return correct list to test")
  public void printTimeInAllTimeZones_Test() {

    List<ZonedDateTime> zonedDateTimes = timeDateEx.printTimeInAllTimeZones();

    Assertions.assertAll(
        () -> Assertions.assertEquals(ZoneId.getAvailableZoneIds().size(), zonedDateTimes.size())
    );
  }


  // EX 7
  @Test
  @DisplayName("printFunctionExecutionTime with lambda should print and return correct nano time")
  public void printFunctionExecutionTime_Test() {

    long executionTime = timeDateEx.printFunctionExecutionTime(TimeDateExMother.getSimpleLambda());

    Assertions.assertTrue(executionTime > 3000000);
  }


}
