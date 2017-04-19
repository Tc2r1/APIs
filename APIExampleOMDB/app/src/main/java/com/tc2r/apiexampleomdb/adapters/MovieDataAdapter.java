package com.tc2r.apiexampleomdb.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tc2r.apiexampleomdb.MainActivity;
import com.tc2r.apiexampleomdb.R;
import com.tc2r.apiexampleomdb.data.MovieData;

import java.util.ArrayList;

/**
 * Created by Tc2r on 4/10/2017.
 * <p>
 * Description:
 */
public class MovieDataAdapter extends RecyclerView.Adapter<MovieDataAdapter.ViewHolder>{

	private static final String TAG = "MovieDataAdapter";
	private MainActivity mainActivity;
	private ArrayList<MovieData> moviesList;


	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		TextView title, year;
		ImageView poster;


		ViewHolder(View itemView) {
			super(itemView);
			// Set listener for entire item
			itemView.setOnClickListener(this);

			//get the reference of each object in inflated view
			this.title = (TextView) itemView.findViewById(R.id.movie_name);
			this.year = (TextView) itemView.findViewById(R.id.movie_year);
			this.poster = (ImageView) itemView.findViewById(R.id.movie_poster);

		}

		@Override
		public void onClick(View view) {
			Log.d(TAG, "onClick " + getAdapterPosition() + " " + moviesList.get(getAdapterPosition()).getTitle());

			if (view instanceof View) {

				// construct url
				String imdbUrl = mainActivity.getResources().getString(R.string.imdb_link) + moviesList.get(getAdapterPosition()).getImdbID();
				Log.d(TAG, imdbUrl);

				// and send user to it.
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl));
				view.getContext().startActivity(intent);
			}
			Log.d(TAG, "OnClick Pressed");
		}

		public void setData(final MovieData currentObject, int position) {

			title.setText(currentObject.getTitle());
			year.setText(currentObject.getYear());

			// Check for the poster image
			if (currentObject.getPosterImage() != null && currentObject.getPosterImage() != "N/A") {

				// poster url is found
				poster.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						// On Poster Click, Show Poster Full Sized
						ShowPoster(v, currentObject);
					}
				});

				Glide.with(mainActivity).load(currentObject.getPosterImage())
								.placeholder(R.drawable.placeholder)
								.crossFade()
								.into(poster);
			} else {
				// If no image found, use place holder
				poster.setImageResource(R.drawable.placeholder);
			}
		}
	}


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
		holder.setData(currentObject,position);
	}

	@Override
	public int getItemCount() {
		return moviesList.size();
	}

	public void ShowPoster(View view, MovieData movie) {

		final View clickedView = view;
		clickedView.setVisibility(View.INVISIBLE);
		View child = mainActivity.getLayoutInflater().inflate(R.layout.poster, null);
		final RelativeLayout displayLayout = (RelativeLayout) mainActivity.findViewById(R.id.displayLayout);
		displayLayout.addView(child);
		ImageView largePoster = (ImageView) child.findViewById(R.id.poster_iv);


		// Use Glide to Dynamically set full display with image.
		Glide.with(mainActivity).load(movie.getPosterImage())
						.placeholder(R.drawable.placeholder)
						.skipMemoryCache(true)
						.crossFade()
						.into(largePoster);

		// When full display is tapped, animate it invisible.
		displayLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {



				// A simple fade out animation
				final Animation fadeOut = new AlphaAnimation(1,0);
				fadeOut.setInterpolator(new AccelerateInterpolator());
				fadeOut.setStartOffset(600);
				fadeOut.setDuration(700);

				// A simple fade in animation
				final Animation shrink = new ScaleAnimation(1, .2f, 1, .2f, displayLayout.getWidth() / 2, displayLayout.getHeight() / 2);
				shrink.setInterpolator(new AnticipateOvershootInterpolator());
				shrink.setStartOffset(500);
				shrink.setDuration(700);
				shrink.setFillAfter(false);
				shrink.setFillEnabled(true);

				// When animation resolves, layout change.
				shrink.setAnimationListener(new Animation.AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
					}

					@Override
					public void onAnimationEnd(Animation animation) {

						displayLayout.removeAllViews();

						if (clickedView != null) {
							clickedView.setVisibility(View.VISIBLE);
						}
					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}
				});

				AnimationSet animationSet = new AnimationSet(true);
				animationSet.addAnimation(shrink);
				animationSet.addAnimation(fadeOut);
				displayLayout.startAnimation(animationSet);
			}
		});
	}
}




