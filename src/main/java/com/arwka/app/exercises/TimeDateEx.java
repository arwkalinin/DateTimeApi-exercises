package com.arwka.app.exercises;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;

/**
 * Task completion.
 *
 * @version 1.0-SNAPSHOT
 */
@RequiredArgsConstructor
public class TimeDateEx {

  private final Clock clock;

  /**
   * EX 1: Write a method returning dates of this month.
   *
   * @return List<LocalDate> of this month.
   */
  public List<LocalDate> getDatesOfThisMonth() {

    final LocalDate currentDate = LocalDate.ofInstant(clock.instant(), ZoneId.systemDefault());
    final List<LocalDate> datesOfMonth = new ArrayList<>(currentDate.lengthOfMonth());

    for (int day = 1; day <= currentDate.lengthOfMonth(); day++) {
      datesOfMonth.add(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), day));
    }

    return datesOfMonth;
  }

  /**
   * EX 2: Write a method, returning LocalDate after 2 weeks from now in Tokyo timezone.
   *
   * @return LocalDate after 2 weeks from now in Tokyo timezone
   */
  public LocalDate getDateInTwoWeeksInTokyo() {
    return ZonedDateTime.ofInstant(clock.instant(), ZoneId.of("Asia/Tokyo"))
        .plusWeeks(2)
        .toLocalDate();
  }

  /**
   * EX 3: Write a method, returning date after N working days from today's date (excluding holidays
   * and weekends)
   *
   * @param daysToCount - working days to count
   * @param holidays    - list of holidays (LocalDate)
   * @param weekends    - list of weekends (DayOfWeek)
   * @return LocalDate after daysToCount working days.
   */
  public LocalDate getLocalDateAfterWorkDays(
      int daysToCount, List<DayOfWeek> weekends, List<LocalDate> holidays) {

    LocalDate datePointer = LocalDate.ofInstant(clock.instant(), ZoneId.systemDefault());

    while (daysToCount != 0) {
      datePointer = datePointer.plusDays(1);
      if (weekends.contains(datePointer.getDayOfWeek()) || holidays.contains(datePointer)) {
        System.out.println("getLocalDateAfterWorkDays ... " + datePointer + ": It's holiday/weekend.");
      } else {
        daysToCount--;
      }
    }

    return datePointer;
  }

  /**
   * EX 4: Write a method, returning count (period) of working hours (including seconds) between two
   * dates with time.
   *
   * @param from        - from LocalDateTime
   * @param to          - to LocalDateTime
   * @param weekends    - list of weekends (DayOfWeek)
   * @param hoursPerDay - working hours per day
   * @return Duration
   */
  public Duration getWorkingTimeBetween(
      LocalDateTime from, LocalDateTime to, float hoursPerDay, List<DayOfWeek> weekends) {

    if (from.isAfter(to)) {
      throw new IllegalArgumentException("FROM date is after TO date");
    }

    LocalDateTime datePointer = from;

    int daysToCount = (int) Duration.between(from, to).toDays();
    final int totalDays = daysToCount;
    int weekendsCount = 0;

    while (daysToCount-- != 0) {
      if (weekends.contains(datePointer.getDayOfWeek())) {
        weekendsCount++;
      }

      datePointer = datePointer.plusDays(1);
    }

    long seconds = Duration.between(from, to)
        .minusDays(weekendsCount)
        .getSeconds();

    return Duration.ofSeconds(seconds)
        .minusHours((long) ((24 - hoursPerDay) * (totalDays - weekendsCount)));
  }


  /**
   * EX 5: Write a method receiving String of Date like "16.09.2016 11::46::01", timezone and
   * returning date and time in UTC.
   *
   * @param parsingString - input String
   * @param timeZone      - timezone of input String
   * @return ZonedDateTime in ZoneId.of("UTC") from parsingString & timeZone
   */
  public ZonedDateTime parseAndGetZonedDateTimeInUtc(String parsingString, TimeZone timeZone) {

    String[] separatedDateAndTime = parsingString.split(" ");
    String[] separatedDate = separatedDateAndTime[0].split("\\.");

    LocalDate parsedDate
        = LocalDate.parse(separatedDate[2] + "-" + separatedDate[1] + "-" + separatedDate[0]);
    LocalTime parsedTime
        = LocalTime.parse(separatedDateAndTime[1].replace("::", ":"));

    ZonedDateTime originZonedDateTime
        = ZonedDateTime.of(parsedDate, parsedTime, timeZone.toZoneId());

    return originZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
  }


  /**
   * EX 6: Write a method, returning current time in all timezones.
   *
   * @return List<ZonedDateTime>
   */
  public List<ZonedDateTime> printTimeInAllTimeZones() {

    ZonedDateTime originZonedDateTime = ZonedDateTime.now();
    List<ZonedDateTime> allTimeZonesTime = new ArrayList<>();

    ZoneId.getAvailableZoneIds().forEach(zoneIdUnit -> {
      System.out.println("ex6: " + originZonedDateTime.withZoneSameInstant(ZoneId.of(zoneIdUnit)));
      allTimeZonesTime.add(originZonedDateTime.withZoneSameInstant(ZoneId.of(zoneIdUnit)));
    });

    return allTimeZonesTime;
  }


  /**
   * EX 7: Write a method for calculating execution time of methods.
   *
   * @param lambda - input lambda-function
   * @return long time elapsed in nanoseconds
   */
  public long printFunctionExecutionTime(DoSomeJob lambda) {

    long timeBefore = System.nanoTime();
    lambda.test();
    long timeAfter = System.nanoTime();

    long resultedTime = timeAfter - timeBefore;
    System.out.println("Time elapsed (ex 7): " + resultedTime);

    return resultedTime;
  }

}
