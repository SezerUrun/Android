package com.freelancer.API;

import com.freelancer.Message;
import com.freelancer.Offer;
import com.freelancer.Proje;
import com.freelancer.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface GetData {
    @GET("/api/users/")
    Call<User> getUser(@Query("mail") String mailAdresi);

    @GET("/api/users/")
    Call<User> getUser(@Query("id") int id);

    @GET("/api/users/")
    Call<List<User>> getUsers();

    @GET("/api/projects/")
    Call<List<Proje>> getProjects();

    @GET("/api/projects/getbyownerid")
    Call<List<Proje>> getOwnerProjects(@Query("ownerid") int ownerId);

    @GET("/api/projects/")
    Call<List<Proje>> getWorkerProjects(@Query("workerid") int workerId);

    @GET("/api/offers/")
    Call<List<Offer>> getOffers(@Query("projectid") int projectId);

    @GET("/api/messages/")
    Call<List<Message>> getMessages(@Query("receiverid") int receiverId);

    @POST("/api/users")
    Call<User> NewUser(@Body User user);

    @POST("/api/projects")
    Call<Proje> NewProject(@Body Proje proje);

    @POST("/api/offers/add")
    Call<Offer> NewOffer(@Body Offer offer);

    @POST("/api/offers/accept/")
    Call<Boolean> AcceptOffer( @Body Offer offer);

    @POST("/api/messages")
    Call<Message> NewMessage(@Body Message message);

    @PUT("/api/users/")
    Call<User> UpdateUser( @Body User user);

    @PUT("/api/projects/")
    Call<Proje> UpdateProject(@Body Proje proje);

    @PUT("/api/projects/confirmbyworker")
    Call<Proje> SetCompletedWorker(@Query("projectid") int projectId);

    @PUT("/api/projects/confirmbyowner")
    Call<Proje> SetCompletedOwner(@Query("projectid") int projectId);

    @DELETE("/api/projects/")
    Call<String> DeleteProject(@Query("id") int projectId);
}
