package com.freelancer.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GetData {
    @GET("/api/users/")
    Call<List<User>> getUsers();

    @POST("/api/users")
    Call<User> register(@Body User user);

    @PUT("api/users/{id}/")
    Call<User> update(@Path("id") int id, @Body User user);
}
