package tech.sabtih.jalsaapp.net;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;

import retrofit2.http.Query;
import tech.sabtih.jalsaapp.dummy.JalsaMedia;
import tech.sabtih.jalsaapp.dummy.itemnop;

public interface ApiInterface {

    @GET("/jalsa/api/?service=gallery") // specify the sub url for our base url
    Call<List<JalsaMedia>> getMediaList(@Query("path") String parent);

    @GET("/jalsa/api/?service=nop")
    Call<itemnop> getNop();


}