package com.tc2r.apiexamplegithub.Remote;

import com.tc2r.apiexamplegithub.Model.User;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tc2r on 4/20/2017.
 * <p>
 * Description:
 */

public interface MyApiEndpointsInterface {

	// Declare Constants
	public static final String BASE_URL = "https://api.github.com/";

	@GET("/search/users")
	Call<User> searchForUserName(@Query("q") String name);

	class Factory{
		// Creates a singleton
		private static MyApiEndpointsInterface service;
		public static MyApiEndpointsInterface getInstance(){
			if(service == null) {
				// OkHttp Reference
				OkHttpClient okHttpClient = new OkHttpClient.Builder()
								.addInterceptor(
												new Interceptor() {
													@Override
													public Response intercept(Chain chain) throws IOException {
														Request request = chain.request().newBuilder()
																		.addHeader("Accept", "application/vnd.github.v3+json").build();
														return chain.proceed(request);
													}
												}
								).build();
				// Retrofit Reference
				Retrofit retrofit = new Retrofit.Builder()
								.baseUrl(BASE_URL)
								.client(okHttpClient)
								.addConverterFactory(GsonConverterFactory.create())
								.build();

				// End Points
				service = retrofit.create(MyApiEndpointsInterface.class);
				return  service;
			}else{
				return service;
			}
		}
	}
}
