package tech.sabtih.jalsaapp.net;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;

import tech.sabtih.jalsaapp.dummy.JalsaMedia;

public interface ApiInterface {


// For POST request

 //   @FormUrlEncoded    // annotation that used with POST type request
 //   @POST("/demo/login.php") // specify the sub url for our base url
 //   public void login(
  //          @Field("user_email") String user_email,
  //          @Field("user_pass") String user_pass, Callback<SignUpResponse> callback);

//user_email and user_pass are the post parameters and SignUpResponse is a POJO class which recieves the response of this API


// for GET request

    @GET("/jalsa/api/?service=gallery") // specify the sub url for our base url
    Call<List<JalsaMedia>> getMediaList();

// CountryResponse is a POJO class which receives the response of this API

}