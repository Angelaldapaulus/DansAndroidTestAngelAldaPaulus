package com.example.dansandroidtestangelaldapaulus;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface apiservice {
    @GET("positions.json")
    Call<List<itemjob>> getJobList(
            @Query("description") String description,
            @Query("location") String location,
            @Query("full_time") String fullTime,
            @Query("page") int page
    );
}