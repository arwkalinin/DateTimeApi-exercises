package com.arwka.app.exercises;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TimeDateExMother {

  public static List<LocalDate> getHolidaysDateList() {
    return Arrays.asList(
        LocalDate.of(LocalDate.now().getYear(), 1, 1),
        LocalDate.of(LocalDate.now().getYear(), 1, 2),
        LocalDate.of(LocalDate.now().getYear(), 1, 3),
        LocalDate.of(LocalDate.now().getYear(), 1, 4),
        LocalDate.of(LocalDate.now().getYear(), 1, 5),
        LocalDate.of(LocalDate.now().getYear(), 1, 7),
        LocalDate.of(LocalDate.now().getYear(), 2, 23),
        LocalDate.of(LocalDate.now().getYear(), 3, 8),
        LocalDate.of(LocalDate.now().getYear(), 5, 1),
        LocalDate.of(LocalDate.now().getYear(), 5, 9),
        LocalDate.of(LocalDate.now().getYear(), 6, 12),
        LocalDate.of(LocalDate.now().getYear(), 11, 4)
    );
  }

  public static List<DayOfWeek> getWeekendDayList() {
    return Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
  }

  public static DoSomeJob getSimpleLambda() {

    return () -> {
      double i = 0;
      while (i++ < 3) {
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    };
  }


}
