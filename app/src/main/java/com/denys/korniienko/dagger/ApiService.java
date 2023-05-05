package com.denys.korniienko.dagger;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface ApiService {
    @GET("/{number}")
    Observable<ResponseBody> getNumber(@Path("number") int number);

    @GET("/random/math")
    Observable<ResponseBody> getRandom();
}
