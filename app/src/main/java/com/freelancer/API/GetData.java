package com.freelancer.API;

import com.freelancer.Message;
import com.freelancer.Offer;
import com.freelancer.Proje;
import com.freelancer.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetData {
    @GET("/api/users/")
    Call<User> getUser(@Query("mail") String mailAdresi);

    @GET("/api/users/")
    Call<List<User>> getUsers();

    @GET("/api/projects/")
    Call<List<Proje>> getProjects();

    @GET("/api/offers/")
    Call<List<Offer>> getOffers(int projectId);

    @GET("/api//messages/")
    Call<List<Message>> getMessages(int reveiverId);

    @POST("/api/users")
    Call<User> NewUser(@Body User user);

    /*@POST("/api/users/")
    Call<User> getUser(@Path("mail") String email);*/

    @POST("/api/projects")
    Call<Proje> NewProject(@Body Proje proje);

    @POST("/api/offers")
    Call<Offer> NewOffer(@Body Offer offer);

    @POST("/api/messages")
    Call<Boolean> NewMessage(@Body Message message);

    @PUT("api/users/{id}/")
    Call<User> UpdateUser(@Path("id") int id, @Body User user);

    @PUT("api/users/{id}/")
    Call<User> UpdatePassword(@Path("id") int id, @Body User user);

    @PUT("api/projects/{id}/")
    Call<Proje> UpdateProject(@Path("id") int id, @Body Proje proje);

}
