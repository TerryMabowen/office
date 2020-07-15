package com.mbw.office.test.jackson;

import com.mbw.office.demo.calendar.CalendarApiService;
import org.junit.Test;

/**
 * @author Mabowen
 * @date 2020-07-15 11:51
 */
public class CalendarTest {
    private com.mbw.office.demo.jackson.CalendarApiService calendarApiService = new com.mbw.office.demo.jackson.CalendarApiService();
    private CalendarApiService apiService = new CalendarApiService();

    @Test
    public void f1() {
        calendarApiService.queryCalendar("2020", "01");
    }

    @Test
    public void f2() {
        String year = "2020";
        String month = "05";
        String query = year + "年" + month + "月";
        apiService.getCalendar(query);
    }
}
