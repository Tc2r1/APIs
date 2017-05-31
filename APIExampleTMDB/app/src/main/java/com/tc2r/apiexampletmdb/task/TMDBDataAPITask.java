package com.tc2r.apiexampletmdb.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tc2r.apiexampletmdb.MainActivity;
import com.tc2r.apiexampletmdb.R;
import com.tc2r.apiexampletmdb.Model.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tc2r on 4/14/2017.
 * <p>
 * Description:
 */
public class TMDBDataAPITask extends AsyncTask<String, Integer, String> {

	private ProgressDialog progressDialog;
	private Context context;
	private MainActivity activity;
	private static MovieData currentMovie;
	private static final String TAG = "TMDBDataAPITask";
	private static final String MEDIA_URL ="https://image.tmdb.org/t/p/w780";
	private int page;
	private String totalResults;


	/**
	 * Description:
	 * Construct a task using data passed
	 *
	 * @param //the calling activity
	 */
	public TMDBDataAPITask(MainActivity activity, final int page) {
		this.activity = activity;
		this.page = page;
		this.context = this.activity.getApplicationContext();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// context, title, prompt string

		if (page < 2) {
			progressDialog = ProgressDialog.show(
							this.activity,
							context.getString(R.string.search_progress_title),
							context.getString(R.string.search_progress_desc),
							true);
		}
	}


	// Worker thread to get data from the internet!
	@Override
	protected String doInBackground(String... params) {

		try {
			Log.d(TAG, "DoInBackground! " + Thread.currentThread().getName());

			// Gather the resulting data into a string


			String result = TMDBHelper.downloadFromServer(page, params);
			return result;
		} catch (TMDBHelper.ApiException e) {
			// If Errors, save.
			return new String();
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		// Dismiss loading Dialog

		if (page < 2) {
			progressDialog.dismiss();
		}
		// Parse the result

		ArrayList<MovieData> moviesList = new ArrayList<MovieData>();

		// Check for error

		if (result.length() == 0) {
			this.activity.alert(context.getString(R.string.error_movie_not_found));
			return;
		}

		try {
			JSONObject responseObject = new JSONObject(result);
			totalResults = responseObject.getString("total_results");
			int totalTest = Integer.parseInt(totalResults);
			if (totalTest > 0) {
				JSONArray movies = responseObject.getJSONArray("results");

				// Loop through Json array and assign values to ArrayList
				for (int i = 0; i < movies.length(); i++) {

					// tracker Variable used for easier code management
					JSONObject movie = movies.getJSONObject(i);

					// Save Json Data to individual variables
					Boolean adult = movie.getBoolean("adult");
					String movieTitle = movie.getString("title");
					String overview = movie.getString("overview");
					String movieYear = movie.getString("release_date");
					int iD = movie.getInt("id");
					Double voteAvg = movie.getDouble("vote_average");
					String posterUrl = movie.getString("poster_path");
					String backDropUrl = null;

					currentMovie = new MovieData(movieTitle,
									movieYear,
									"",
									iD,
									backDropUrl,
									"",
									"",
									"",
									"",
									posterUrl,
									"",
									overview,
									adult,
									voteAvg,
									0);

					if (!movie.getString("backdrop_path").equalsIgnoreCase("null")){
						backDropUrl = MEDIA_URL+ movie.getString("backdrop_path");
					}

					// Save this Movie to movieData Array.
					moviesList.add(currentMovie);
				}
			}else{
				switch(page){
					case 1:
						this.activity.alert(responseObject.getString("Error"));
						break;
					default:
						this.activity.alert(this.activity.getString(R.string.all_end_results));
						break;
				}
				return;
			}
		}catch (JSONException e) {
			e.printStackTrace();
		}
		// Update the UI of the activity
		this.activity.setData(moviesList, totalResults);
	}
}
