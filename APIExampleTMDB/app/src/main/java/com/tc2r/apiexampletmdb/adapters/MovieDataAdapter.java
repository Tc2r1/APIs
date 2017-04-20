package com.tc2r.apiexampletmdb.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tc2r.apiexampletmdb.MainActivity;
import com.tc2r.apiexampletmdb.Model.MovieData;
import com.tc2r.apiexampletmdb.Model.MovieInfo.MovieModel;
import com.tc2r.apiexampletmdb.Model.MovieInfo.Result;
import com.tc2r.apiexampletmdb.MovieDetails;
import com.tc2r.apiexampletmdb.R;
import com.tc2r.apiexampletmdb.remote.MovieLookupAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Tc2r on 4/14/2017.
 * <p>
 * Description:
 */
public class MovieDataAdapter extends RecyclerView.Adapter<MovieDataAdapter.ViewHolder> {

	private static final String TAG = "MovieDataAdapter";
	private MainActivity mainActivity;
	private ArrayList<MovieData> moviesList;

	public MovieDataAdapter(MainActivity mainActivity, ArrayList<MovieData> movies) {
		this.mainActivity = mainActivity;
		this.moviesList = movies;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// Inflate the viewgroup with an instance of movierow
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movierow, parent, false));

	}
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final MovieData currentObject = moviesList.get(position);

		// Assign date to the object.
		holder.setData(currentObject, position);
	}
	@Override
	public int getItemCount() {
		return moviesList.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		TextView title, rating;
		ImageView posterIV, aboutIV, backdropIV;
		LinearLayout movieBgLayout;


		ViewHolder(View itemView) {
			super(itemView);
			// Set listener for entire item
			itemView.setOnClickListener(this);

			//get the reference of each object in inflated view
			this.title = (TextView) itemView.findViewById(R.id.movie_name);
			this.rating = (TextView) itemView.findViewById(R.id.rating_tv);
			this.posterIV = (ImageView) itemView.findViewById(R.id.movie_poster);
			this.aboutIV = (ImageView) itemView.findViewById(R.id.about_iv);
			this.movieBgLayout = (LinearLayout) itemView.findViewById(R.id.movie_text_bg);
			this.backdropIV = (ImageView) itemView.findViewById(R.id.movie_backdrop);

		}

		@Override
		public void onClick(View view) {
			if (view instanceof View) {
				Intent intent = new Intent(mainActivity, MovieDetails.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable("CurrentMovie", moviesList.get(getAdapterPosition()));
				intent.putExtras(bundle);
				view.getContext().startActivity(intent);
			}
		}

		public void setData(final MovieData currentObject, int position) {

			title.setText(currentObject.getTitle());

			// check rating, set if it exist.
			if (currentObject.getVoteAvg() > 0.0) {
				rating.setText(String.valueOf(currentObject.getVoteAvg()));
			}

			// Call API for Data on specific Movie using Retrofit2
			// On Successful call, save data to object.
			MovieLookupAPI.Factory.getInstance().getMovieDetails(currentObject.getiD()).enqueue(new retrofit2.Callback<MovieModel>() {
				@Override
				public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
					//Log.i("TEST", new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getImdb_Id()));

					final MovieModel tempMovie = response.body();
					// Set MovieData Object with detailed Items!
					currentObject.setAdult(tempMovie.isAdult());
					// Genres
					StringBuilder builder = new StringBuilder();
					for (int i = 0; i < tempMovie.getGenres().size(); i++) {
						builder.append(tempMovie.getGenres().get(i).getName() + " ");
					}
					currentObject.setGenres(builder.toString());
					currentObject.setImdbID(tempMovie.getImdb_Id());
					currentObject.setRuntime(String.valueOf(tempMovie.getRuntime()));
					currentObject.setStatus(tempMovie.getStatus());
					currentObject.setTagline(tempMovie.getTagline());
					currentObject.setVoteCount(tempMovie.getVote_count());

					// Get Backdrop Url and Build it
					if (currentObject.getBackDropURL() == null) {
						currentObject.setBackDropURL("https://image.tmdb.org/t/p/w780" + tempMovie.getBackdrop_path());
					}
					// Load images with Picasso, if backdrops are loaded, change text bar color to match
					// if not loaded, change text bar color to match poster image.

					Picasso.with(mainActivity).load(currentObject.getBackDropURL())
									.fit()
									.centerCrop()
									.error(R.drawable.placeholder)
									.into(backdropIV, new Callback() {
										@Override
										public void onSuccess() {
											paintTextBackground(movieBgLayout, backdropIV);
										}

										@Override
										public void onError() {
											Log.d(TAG, currentObject.getBackDropURL() + "not found.");
											Picasso.with(mainActivity).load("https://image.tmdb.org/t/p/w780" + currentObject.getPosterImage())
															.fit()
															.centerInside()
															.error(R.drawable.placeholder)
															.placeholder(R.drawable.placeholder)
															.into(backdropIV);
											paintTextBackground(movieBgLayout, posterIV);

										}
									});

					// Get Video Urls
					List<Result> currentVideos = response.body().getVideos().getResults();
					if (currentVideos.size() != 0) {
						currentObject.setVideoUrl(currentVideos.get(0).getKey());
						Log.w("YouTube: ", "https://www.youtube.com/watch?v=" + currentObject.getVideoUrl());
					} else {
					}
				}

				@Override
				public void onFailure(Call<MovieModel> call, Throwable t) {
					Log.e(TAG, "FAILED" + t.getMessage());
				}
			});

			// Check for the poster image
			if (currentObject.getPosterImage() != null && currentObject.getPosterImage() != "null") {
				//currentObject.setPosterImage("https://image.tmdb.org/t/p/w500" + currentObject.getPosterImage());

				// poster url is found
				posterIV.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						// construct url
						String imdbUrl = mainActivity.getResources().getString(R.string.imdb_link) + currentObject.getImdbID();
						Log.d(TAG, imdbUrl);

						// and send user to it.
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl));
						view.getContext().startActivity(intent);
					}
				});

				// Load Poster Image
				Picasso.with(mainActivity)
								.load("https://image.tmdb.org/t/p/w500" + currentObject.getPosterImage())
								.fit()
								.placeholder(R.drawable.placeholder)
								.error(R.drawable.placeholder)
								.into(posterIV);
			} else {
				// If no image found, use place holder
				posterIV.setImageResource(R.drawable.placeholder);
			}
		}
	}

	private void paintTextBackground(final LinearLayout movieBgLayout, ImageView posterIV) {
		// ON FINISH/RESULT, CHANGE THE BG OF THE COLOR OF THE LAYOUT UNDERNEATH!
		BitmapDrawable drawable = ((BitmapDrawable) posterIV.getDrawable());
		Bitmap bitmap = drawable.getBitmap();
		Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
			@Override
			public void onGenerated(Palette palette) {
				int defaultValue = 0x000000;
				int vibrantDark = palette.getMutedColor(defaultValue);
				movieBgLayout.setBackgroundColor(vibrantDark);
			}
		});
	}

}