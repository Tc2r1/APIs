package com.tc2r.apiexampletmdb;

import android.animation.Animator;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tc2r.apiexampletmdb.Model.MovieData;
import com.tc2r.apiexampletmdb.adapters.MovieDataAdapter;
import com.tc2r.apiexampletmdb.custom.CustomRecyclerViewScrollListener;
import com.tc2r.apiexampletmdb.task.TMDBDataAPITask;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Tc2r on 4/14/2017.
 * <p>
 * Description: An App that shows how to use TMDB API.
 */

public class MainActivity extends AppCompatActivity {
	// Declare Variables
	private static final String TAG = "MainActivity.class";
	private ArrayList<MovieData> movies;
	private LayoutInflater layoutInflater;
	private RecyclerView movieListLayout;
	private LinearLayoutManager layoutManager;
	private MovieDataAdapter movieDataAdapter;
	private Toolbar mToolbar, mSearchToolbar;
	private Menu search_menu;
	private MenuItem item_search;
	private InputMethodManager inputMethodManager;
	private int lastPage, totalResults;
	private ImageView searchButton;
	private FrameLayout aboutLayout;
	private TMDBDataAPITask omdbDataAPITask;
	private CustomRecyclerViewScrollListener scrollListener;
	private String query;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialize Variables
		movies = new ArrayList<>();
		layoutInflater = LayoutInflater.from(this);
		movieListLayout = (RecyclerView) findViewById(R.id.result_list);
		aboutLayout = (FrameLayout) findViewById(R.id.frame_container);
		searchButton = (ImageView) findViewById(R.id.search_btn);
		mToolbar = (Toolbar) findViewById(R.id.toolbar);

		// action bar
		setSupportActionBar(mToolbar);
		setSearchtoolbar();

		// no back button
		if (getActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		}

		//Initialing layout
		layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
		movieListLayout.setLayoutManager(layoutManager);
		movieListLayout.setHasFixedSize(true);
		movieListLayout.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
		movieListLayout.setItemAnimator(new DefaultItemAnimator());
		movieListLayout.setAdapter(movieDataAdapter);


		// Set Listeners

		// scrollListener for lazy loading data
		scrollListener = new CustomRecyclerViewScrollListener(layoutManager) {
			@Override
			public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
				if (lastPage > page) {
					omdbDataAPITask = new TMDBDataAPITask(MainActivity.this, lastPage + 1);
					setPage(lastPage);

				} else {
					lastPage = page;
					omdbDataAPITask = new TMDBDataAPITask(MainActivity.this, lastPage + 1);
				}
				omdbDataAPITask.execute(query);
			}
		};

		// Assign listener to Recyclerview
		movieListLayout.addOnScrollListener(scrollListener);

		// Button listener for search.
		searchButton.setOnClickListener(new View.OnClickListener() {
																			@Override
																			public void onClick(View v) {

																				// Check for Connection
																				if (!NetworkCheck.isNetworkAvailable(getApplicationContext())) {
																					alert(getString(R.string.error_connecting_internet));
																					return;
																				}
																				// circleReveal only works on API 14+
																				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
																					circleReveal(R.id.searchtoolbar, 1, true, true);
																				} else {
																					mSearchToolbar.setVisibility(View.VISIBLE);
																					searchButton.setVisibility(View.GONE);
																				}

																				item_search.expandActionView();
																			}
																		});

		// Restore data on orientation change.
		final Object[] data = (Object[]) getLastCustomNonConfigurationInstance();
		if (data != null) {
			movies = (ArrayList<MovieData>) data[0];
			query = (String) data[1];
			movieDataAdapter = (MovieDataAdapter) data[2];
			inputMethodManager = (InputMethodManager) data[3];
			lastPage = (int) data[4];

			// Call Adapter to update again
			movieDataAdapter = new MovieDataAdapter(this, movies);
			movieListLayout.setAdapter(movieDataAdapter);
		}
	}

	// Simple method to show messages
	public void alert(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}

	/**
	 * Description:
	 * Adds new results to Array List of results,
	 * then updates adapter to display results.
	 * <p>
	 * //@param params newMovies - Array of new results
	 * //@param totalResults - the total number of results for this query.
	 */
	public void setData(ArrayList<MovieData> newMovies, String totalResults) {

		if (this.movies == null) {
			// Save the Movie data
			this.movies = newMovies;


			if (totalResults != null) {
				this.totalResults = Integer.parseInt(totalResults);

			}
			movieDataAdapter = new MovieDataAdapter(this, movies);
			movieListLayout.setAdapter(movieDataAdapter);

		} else {
			// Add more movies to array
			this.movies.addAll(newMovies);

		}
		movieDataAdapter.notifyDataSetChanged();
	}

	//Save any fetched data for orientation changes.
	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		Object[] myStuff = new Object[5];
		myStuff[0] = this.movies;
		myStuff[1] = this.query;
		myStuff[2] = this.movieDataAdapter;
		myStuff[3] = this.inputMethodManager;
		myStuff[4] = this.lastPage;

		return myStuff;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar items clicked.
		int id = item.getItemId();

		switch (id) {
			case R.id.action_search:
				// Check for Connection
				if(!NetworkCheck.isNetworkAvailable(getApplicationContext())) {
					alert(getString(R.string.error_connecting_internet));
					return true;
				}
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					circleReveal(R.id.searchtoolbar, 1, true, true);
				} else {
					mSearchToolbar.setVisibility(View.VISIBLE);
					searchButton.setVisibility(View.GONE);
				}
				item_search.expandActionView();
				return true;

			case R.id.action_settings:

				FragmentManager fragmentManager = getSupportFragmentManager();
				FragmentTransaction fragmentTransaction =
								fragmentManager.beginTransaction();
				fragmentTransaction.replace(android.R.id.content, new AboutFragment());
				fragmentTransaction.commit();

				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Description:
	 * Initializes Search Toolbar
	 */
	public void setSearchtoolbar() {
		mSearchToolbar = (Toolbar) findViewById(R.id.searchtoolbar);
		if (mSearchToolbar != null) {

			mSearchToolbar.inflateMenu(R.menu.menu_search);
			search_menu = mSearchToolbar.getMenu();

			// When navigation Arrow is clicked, removes Search Toolbar
			mSearchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						circleReveal(R.id.searchtoolbar, 1, true, false);

					} else {
						mSearchToolbar.setVisibility(View.GONE);
						searchButton.setVisibility(View.VISIBLE);
					}
				}
			});

			item_search = search_menu.findItem(R.id.action_filter_search);
			MenuItemCompat.setOnActionExpandListener(item_search, new MenuItemCompat.OnActionExpandListener() {
				@Override
				public boolean onMenuItemActionExpand(MenuItem item) {
					return true;
				}

				@Override
				public boolean onMenuItemActionCollapse(MenuItem item) {

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						circleReveal(R.id.searchtoolbar, 1, true, false);
					} else {
						mSearchToolbar.setVisibility(View.GONE);
						searchButton.setVisibility(View.VISIBLE);
					}
					return true;
				}
			});
			startSearchView();

		} else {
			Log.d(TAG, "Searchbar = null");
		}
	}

	/**
	 * Description:
	 * Creates a Circle Reveal on the action bar when the search action button is cliked
	 * <p>
	 * //@param vID - The View of the Search toolbar
	 * //@param posFromRight - Positive or negative determines direction of nav icon from searchbar
	 * //@param containsOverflow
	 * //@param isShow - the Visibility state of the search toolbar
	 */
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public void circleReveal(int vID, int posFromRight, boolean containsOverflow, final boolean isShow) {

		final View myView = findViewById(vID);

		int width = myView.getWidth();

		if (posFromRight > 0) {
			width -= (posFromRight * getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) - (getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2));
		}

		if (containsOverflow) {
			width -= getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);
		}

		int cx = width;
		int cy = myView.getHeight() / 2;

		// Create animation
		Animator anim;
		if (isShow) {
			anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, (float) width);
		} else {
			anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float) width, 0);
		}
		anim.setDuration(220);

		// Make the view invisible after the animation.
		anim.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if (!isShow) {
					myView.setVisibility(View.INVISIBLE);
					searchButton.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}
		});

		if (isShow) {
			myView.setVisibility(View.VISIBLE);
			searchButton.setVisibility(View.GONE);
		}
		// start the animation
		anim.start();
	}

	public void startSearchView() {
		final SearchView searchView = (SearchView)
						search_menu.findItem(R.id.action_filter_search).getActionView();

		// Disable submit button in the keyboard
		searchView.setSubmitButtonEnabled(false);

		// Change search close button image

		ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
		closeButton.setImageResource(R.drawable.ic_close);

		// set hint and the text colors
		EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
		txtSearch.setHint(R.string.search_hint);
		txtSearch.setHintTextColor(ContextCompat.getColor(this, R.color.textColorPrimary));
		txtSearch.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));

		// set the cursor color

		AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
		try {
			Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
			mCursorDrawableRes.setAccessible(true);
			mCursorDrawableRes.set(searchTextView, R.drawable.search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
		} catch (Exception e) {
			e.printStackTrace();
		}


		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				doSearch(query);
				searchView.clearFocus();
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return true;
			}

			private void doSearch(String tmpQuery) {
				query = tmpQuery;

				// Reset search state before new search.
				if (movies != null) {
					// clear array
					movies.clear();
					movies = null;
				}
				if (movieListLayout.getAdapter() != null) {
					// notify adapter
					movieListLayout.getAdapter().notifyDataSetChanged();
				}
				// reset listener
				lastPage = 1;
				scrollListener.resetState();

				// Create an instance of API task
				omdbDataAPITask = new

								TMDBDataAPITask(MainActivity.this, 1);

				try {
					query = query.trim();
					query = query.replace(" ", "+");

					// Execute request using saved location!
					omdbDataAPITask.execute(query);

				} catch (Exception e) {
					omdbDataAPITask.cancel(true);
					alert(getString(R.string.error_movie_not_found));
				}
			}
		});
	}

	private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
		private int spanCount;
		private int spacing;
		private boolean includeEdge;

		public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
			this.spanCount = spanCount;
			this.spacing = spacing;
			this.includeEdge = includeEdge;
		}

		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
			int position = parent.getChildAdapterPosition(view);
			if (includeEdge) {

				if (position < spanCount) { // top edge
					outRect.top = spacing;
				}
				outRect.bottom = spacing; // item bottom
			} else {
				if (position >= spanCount) {
					outRect.top = spacing; // item top
				}
			}
		}
	}

	/**
	 * Converting dp to pixel
	 */
	private int dpToPx(int dp) {
		Resources r = getResources();
		return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
	}
}
