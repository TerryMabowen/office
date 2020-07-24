package com.mbw.office.demo.calendar;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

/** /api.php
 * @author Mabowen
 * @date 2020-07-15 15:37
 */
public interface CalendarRequestProxy {

    @GET("/api.php")
    public Call<BaseApiResponse<List<CalendarApiData>>> getCalendar(@Query("query") String query,
                                                                    @Query("resource_id") String resourceId,
                                                                    @Query("format") String format);
}
