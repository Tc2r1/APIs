package com.tc2r.apiexampleomdb;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Tc2r on 4/14/2017.
 * <p>
 * Description:
 */

public class NetworkCheck {


	@SuppressWarnings("isNetworkAvailable")
	public static boolean isNetworkAvailable(Context context) {

		int[] networkTypes = {ConnectivityManager.TYPE_MOBILE,
						ConnectivityManager.TYPE_WIFI};
		try {
			ConnectivityManager connectivityManager =
							(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

			for (int networkType : networkTypes) {
				NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
				if (activeNetworkInfo != null &&
								activeNetworkInfo.getType() == networkType) {


					return true;
				}
			}

		} catch (Exception e) {

			return false;
		}
		return false;
	}
}
