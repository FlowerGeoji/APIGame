package app.geoji.flower.apigameandroidtester.lib;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiInterface {
    @GET
    Single<JsonObject> getGameApi(@Url String url, @QueryMap Map<String, Object> map);

    @POST
    Single<JsonObject> postGameApi(@Url String url, @Body JsonObject json);

    @PUT
    Single<JsonObject> putGameApi(@Url String url, @Body JsonObject json);

    @PATCH
    Single<JsonObject> patchGameApi(@Url String url, @Body JsonObject json);

    @DELETE
    Single<JsonObject> deleteGameApi(@Url String url, @Body JsonObject json);
}
