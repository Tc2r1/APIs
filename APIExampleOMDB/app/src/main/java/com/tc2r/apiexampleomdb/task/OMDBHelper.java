package com.tc2r.apiexampleomdb.task;

import android.content.Context;
import android.util.Log;

import com.tc2r.apiexampleomdb.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tc2r on 4/10/2017.
 * <p>
 * Description:
 */

public class OMDBHelper {
 private static Context context;

	// Url needed to access:
	private static final String URL_HARD_CODE =
					"http://www.omdbapi.com/?s=";
	private static final int HTTP_STATUS_OK = 200;
	private static byte[] buff = new byte[1024];
	private static final String TAG = "OMDBHelper";

	public OMDBHelper(Context context) {
		this.context = context;
	}

	public static class ApiException extends Exception {
		private static final long serialVerionUID = 1L;

		public ApiException(String errorMessage) {
			super(errorMessage);
		}

		public ApiException(String errorMessage, Throwable thr) {
			super(errorMessage, thr);
		}
	}

	/**
	 * Description:
	 * download most popular tracks in given location
	 *
	 * @param params search strings
	 * @param params the page number to load new movies
	 * @return Array of json strings returned by the API
	 * @Throws API Exception
	 */

	public static synchronized String downloadFromServer(int pageLoad, String... params)
					throws ApiException {

		// Tracking User's Search Results
		String setval = null;
		String movieQuery = params[0];
		int page = pageLoad;


		// Complete request url
		String url = URL_HARD_CODE + movieQuery + "&page="+ page;

		Log.d(TAG, "Retrieving " + url);

		// Create an http client and a request object

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		try {

			//Attempt execute the request
			HttpResponse response = client.execute(request);
			StatusLine status = response.getStatusLine();
			Log.d(TAG, String.valueOf(status));
			if (status.getStatusCode() != HTTP_STATUS_OK) {
				// Errors
				throw new ApiException(context.getString(R.string.error_invalid_response)
								+ String.valueOf(status));

			}

			// process the data retrieved

			HttpEntity entity = response.getEntity();
			InputStream ist = entity.getContent();
			ByteArrayOutputStream content = new ByteArrayOutputStream();

			int readCount = 0;
			while ((readCount = ist.read(buff)) != -1) {
				content.write(buff, 0, readCount);
			}
			Log.d(TAG, "Retrieving " + url);
			// Get the json array as a string
			setval = new String(content.toByteArray());

		} catch (IOException e) {
			throw new ApiException(context.getString(R.string.error_connecting_server) + e.getMessage(), e);
		}

		// Return the string of Json data.
		return setval;
	}
}
