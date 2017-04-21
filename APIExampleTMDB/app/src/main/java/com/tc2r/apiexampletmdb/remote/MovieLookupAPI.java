package com.tc2r.apiexampletmdb.remote;

import com.tc2r.apiexampletmdb.Model.MovieInfo.MovieModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Tc2r on 4/18/2017.
 * <p>
 * Description:
 */

public interface MovieLookupAPI {

	static final String URL_MOVIE_MEDIA = "https://api.themoviedb.org/3/";
	String hiKddEnY = "a70bede5f34a31b5f0098fc98ccfb971";


	// IE: https://api.themoviedb.org/3/movie/157336?api_key=a70bede5f34a31b5f0098fc98ccfb971&append_to_response=videos,images
	@GET("movie/{id}?" + "api_key="+ hiKddEnY+"&append_to_response=videos,images&language=en-US&include_image_language=en,null")
	Call<MovieModel> getMovieDetails(@Path("id") int movieid);

	class Factory{
		// Creates a singleton
		private static MovieLookupAPI service;
		public static MovieLookupAPI getInstance(){
			if(service == null) {
				// Retrofit Reference
				Retrofit retrofit = new Retrofit.Builder()
								.addConverterFactory(GsonConverterFactory.create())
								.baseUrl(URL_MOVIE_MEDIA)
								.build();

				// End Points
				service = retrofit.create(MovieLookupAPI.class);
				return service;
			}else{
				return service;
			}
		}
	}
}
