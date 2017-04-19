package com.tc2r.apiexampleomdb.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tc2r.apiexampleomdb.MainActivity;
import com.tc2r.apiexampleomdb.R;
import com.tc2r.apiexampleomdb.data.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tc2r on 4/10/2017.
 * <p>
 * Description:
 */
public class OMDBDataAPITask extends AsyncTask<String, Integer, String> {

	private ProgressDialog progressDialog;
	private Context context;
	private MainActivity activity;
	private static final String TAG = "OMDBDataAPITask";
	private int page;
	private String totalResults;


	/**
	 * Description:
	 * Construct a task using data passed
	 *
	 * @param //the calling activity
	 */
	public OMDBDataAPITask(MainActivity activity, final int page) {
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


			String result = OMDBHelper.downloadFromServer(page, params);
			return result;
		} catch (OMDBHelper.ApiException e) {
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
			if (responseObject.getBoolean("Response")) {
				JSONArray movies = responseObject.getJSONArray("Search");
				totalResults = responseObject.getString("totalResults");

				// Loop through Json array and assign values to ArrayList
				for (int i = 0; i < movies.length(); i++) {

					// tracker Variable used for easier code management
					JSONObject movie = movies.getJSONObject(i);

					// Save Json Data to individual variables
					String movieTitle = movie.getString("Title");
					String movieYear = movie.getString("Year");
					String imdbID = movie.getString("imdbID");
					String type = movie.getString("Type");
					String posterUrl = movie.getString("Poster");

					Log.d(TAG, posterUrl.toString());
					if (posterUrl.equals("N/A")) {
						posterUrl = null;

					}
					// Save this Movie to movieData Array.
					moviesList.add(new MovieData(movieTitle, movieYear, imdbID, type, posterUrl));
				}
			}else{

				switch(page){
					case 1:
						this.activity.alert(responseObject.getString("Error"));
						break;
					default:
						this.activity.alert(this.activity.getString(R.string.end_of_results));
						break;
				}
				return;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// Update the UI of the activity
		this.activity.setData(moviesList, totalResults);
	}
}
