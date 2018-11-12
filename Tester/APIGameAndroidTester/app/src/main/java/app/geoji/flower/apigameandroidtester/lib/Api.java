package app.geoji.flower.apigameandroidtester.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Api {
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 15;

    private static final String PUFF_BASE_URL = "https://staging-apis.pufflive.me/";

    private static ApiInterface gameApiInterface;

    public Api(String token) {
        final CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .addInterceptor(chain -> {
                    Request newRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer "+token).build();
                    return chain.proceed(newRequest);
                })
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));

        gameApiInterface = retrofitBuilder.baseUrl(PUFF_BASE_URL)
                .build()
                .create(ApiInterface.class);
    }

    public Single<JsonObject> request(String method, String url, JsonObject data) {
//        Matcher matcher = Pattern.compile("/file/image").matcher(url);
//        if (matcher.matches()) {
//            return Observable.create(subscriber -> {
//                Log.d("API", "call the mocked /file/image with JsonData: " + (data == null ? "null" : data.toString()));
//                JsonObject responseData = new JsonObject();
//                if (data != null) {
//                    String imageUri = data.get("img").getAsString();
//                    responseData.addProperty("image_url", imageUri);
//                    subscriber.onNext(responseData);
//                }
//            });
//        }

        JsonObject newData = data != null ? new JsonParser().parse(data.toString()).getAsJsonObject() : new JsonObject();
        Single<JsonObject> observable;
        switch (method.trim().toLowerCase()) {
            case "get":
                final Map<String, Object> newDataMap = new Gson().fromJson(
                        newData.toString(), new TypeToken<HashMap<String, String>>() {
                        }.getType()
                );
                observable = gameApiInterface.getGameApi("v1/" + url, newDataMap);
                break;
            case "post":
                observable = gameApiInterface.postGameApi("v1/" + url, newData);
                break;
            case "put":
                observable = gameApiInterface.putGameApi("v1/" + url, newData);
                break;
            case "patch":
                observable = gameApiInterface.patchGameApi("v1/" + url, newData);
                break;
            case "delete":
                observable = gameApiInterface.deleteGameApi("v1/" + url, newData);
                break;
            default:
                throw new IllegalArgumentException("Not supported method");
        }
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
