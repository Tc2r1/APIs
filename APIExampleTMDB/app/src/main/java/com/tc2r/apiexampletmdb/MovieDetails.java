package com.tc2r.apiexampletmdb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tc2r.apiexampletmdb.Model.MovieData;

public class MovieDetails extends AppCompatActivity {
	ImageView posterIV, backdropIV;
	TextView titleTV, releaseDateTV, timeTV, voteAvgTV, voteCountTV, genreTV, statusTV, imdbTV, taglineTV, descriptionTV;
	MovieData currentMovie;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;

		Bundle b = this.getIntent().getExtras();
		currentMovie = new MovieData();

		if (b != null) {
			currentMovie = b.getParcelable("CurrentMovie");
		}

		setContentView(R.layout.activity_movie_details);

		posterIV = (ImageView) findViewById(R.id.posterIV);
		backdropIV = (ImageView) findViewById(R.id.backdropIV);
		titleTV = (TextView) findViewById(R.id.titleTV);
		releaseDateTV = (TextView) findViewById(R.id.releaseDateTV);
		timeTV = (TextView) findViewById(R.id.timeTV);
		voteAvgTV = (TextView) findViewById(R.id.voteAvgTV);
		voteCountTV = (TextView) findViewById(R.id.voteCountTV);
		genreTV = (TextView) findViewById(R.id.genreTV);
		statusTV = (TextView) findViewById(R.id.statusTV);
		imdbTV = (TextView) findViewById(R.id.imdbTV);
		taglineTV = (TextView) findViewById(R.id.taglineTV);
		descriptionTV = (TextView) findViewById(R.id.movieDescriptionTV);
		titleTV.setText(currentMovie.getTitle());
		releaseDateTV.setText(currentMovie.getYear());

		if (currentMovie.getVoteAvg() > 0) {
			voteAvgTV.setText(String.valueOf(currentMovie.getVoteAvg()));
		}
		if (currentMovie.getVoteCount() > 0) {
			voteCountTV.setText(String.valueOf(currentMovie.getVoteCount()));
		}
		if(currentMovie.getRuntime().length() > 0){
			timeTV.setText(currentMovie.getRuntime() + getString(R.string.movie_minutes));
		}
		if(currentMovie.getGenres().length() > 0){
			genreTV.setText(currentMovie.getGenres());
		}

		statusTV.setText(currentMovie.getStatus());
		taglineTV.setText(currentMovie.getTagline());
		descriptionTV.setText(currentMovie.getOverview());

		// Set On click for IMDB Link
		imdbTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// construct url
				String imdbUrl = context.getResources().getString(R.string.imdb_link) + currentMovie.getImdbID();

				// and send user to it.
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl));
				startActivity(intent);
			}
		});

		// Load images with Picasso
		Picasso.with(context).load(currentMovie.getBackDropURL())
						.fit()
						.centerCrop()
						.error(R.drawable.all_placeholder)
						.into(backdropIV, new Callback() {
							@Override
							public void onSuccess() {
							}

							@Override
							public void onError() {
								Picasso.with(context).load("https://image.tmdb.org/t/p/w780" + currentMovie.getPosterImage())
												.fit()
												.centerInside()
												.error(R.drawable.all_placeholder)
												.into(backdropIV);
							}
						});
		Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + currentMovie.getPosterImage())
						.fit()
						.placeholder(R.drawable.all_placeholder)
						.into(posterIV);
	}
}
