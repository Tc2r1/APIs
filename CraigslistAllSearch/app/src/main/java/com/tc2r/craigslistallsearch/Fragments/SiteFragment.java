package com.tc2r.craigslistallsearch.Fragments;


import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tc2r.craigslistallsearch.R;

public class SiteFragment extends Fragment implements View.OnClickListener {


	// Constants
	public static final String BASE_URL = "http://allcraigslistsearch.com/allclsites.html";
	public final String TAG = this.getClass().getSimpleName();

	// Declare Variables
	private WebView mWebview;
	private View mView;


	public SiteFragment() {
		// Required empty public constructor
	}

	@SuppressWarnings("ResourceType")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
													 Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.fragment_all, container, false);
		mWebview = (WebView) mView.findViewById(R.id.webview_fragment);
		WebSettings settings = mWebview.getSettings();

		// Enable Java for webview
		settings.setJavaScriptEnabled(true);

		// Enable zoom
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);

		// Load page completely zoomed out
		settings.setLoadWithOverviewMode(true);
		settings.setUseWideViewPort(true);

		// Enables Plugin support
		settings.setPluginState(WebSettings.PluginState.ON);

		// Adjust thread priority
		settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

		// Every load is refreshed
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

		settings.setDomStorageEnabled(true);
		settings.setDatabaseEnabled(true);
		settings.setAppCacheEnabled(true);

		// Scroll bars inside webview
		mWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

		// Has focus of page
		mWebview.setFocusable(true);
		mWebview.setFocusableInTouchMode(true);

		// Do not open link in android browser, instead open in this Webview
		mWebview.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			@Override
			public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setMessage(R.string.notification_error_ssl_cert_invalid);
				builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						handler.proceed();
					}
				});
				builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						handler.cancel();
					}
				});
				final AlertDialog dialog = builder.create();
				dialog.show();
			}
			@Override
			public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
				super.onReceivedError(view, request, error);
				Log.wtf(TAG, error.toString());
			}
		});
		mWebview.setWebChromeClient(new WebChromeClient());
		mWebview.requestFocus();
		settings.setUserAgentString("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0");
		settings.setAllowFileAccess(true);
		settings.setAllowContentAccess(true);
		settings.setBuiltInZoomControls(true);
		settings.setDisplayZoomControls(false);
		settings.setGeolocationEnabled(true);
		mWebview.loadUrl(BASE_URL);
		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View v) {
	}
}

