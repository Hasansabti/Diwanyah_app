package tech.sabtih.jalsaapp.net;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;

import retrofit2.http.Query;
import tech.sabtih.jalsaapp.dummy.JalsaMedia;
import tech.sabtih.jalsaapp.dummy.itemnop;
import tech.sabtih.jalsaapp.dummy.user;
import tech.sabtih.jalsaapp.dummy.userdata;

public interface ApiInterface {

    @GET("/jalsa/api/?service=gallery") // specify the sub url for our base url
    Call<List<JalsaMedia>> getMediaList(@Query("path") String parent);

    @GET("/jalsa/api/?service=newfolder") // specify the sub url for our base url
    Call<List<JalsaMedia>> createfile(@Query("name") String name,@Query("parent") String parent);

    @GET("/jalsa/api/?service=delete") // specify the sub url for our base url
    Call<List<JalsaMedia>> deletefiles(@Query("files[]") List<String> files,@Query("parent") String parent);

    @GET("/jalsa/api/?service=nop")
    Call<itemnop> getNop();

    @GET("/jalsa/api/?service=users")
    Call<List<user>> getUSers();

    @GET("/jalsa/api/?service=userdata")
    Call<userdata> getUsersata(@Query("id") int id);



}