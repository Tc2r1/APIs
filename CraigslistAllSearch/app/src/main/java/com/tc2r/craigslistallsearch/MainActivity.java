package com.tc2r.craigslistallsearch;
//

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tc2r.craigslistallsearch.Fragments.DirectFragment;
import com.tc2r.craigslistallsearch.Fragments.EbayFragment;
import com.tc2r.craigslistallsearch.Fragments.SearchFragment;
import com.tc2r.craigslistallsearch.Fragments.SiteFragment;
import com.tc2r.craigslistallsearch.widgets.CustomViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
	private CustomViewPager mViewPager;
	private AdView mAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		mViewPager = (CustomViewPager) findViewById(R.id.map_viewpager);
		mViewPager.setPageMargin(0);
		// Stop scrolling
		mViewPager.setPagingEnabled(false);

		CoordinatorLayout.LayoutParams paramBundle = (CoordinatorLayout.LayoutParams) this.mViewPager.getLayoutParams();
		paramBundle.topMargin= 0;

		SetupViewPager(mViewPager);

		tabs.setupWithViewPager(mViewPager);

		mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
						.addTestDevice("88BA19F3F8B575C75CDBA660B25FB77B")
						.build();
		mAdView.loadAd(adRequest);
		mAdView.bringToFront();
	}


	// Adds fragments to Tabs
	private void SetupViewPager(ViewPager viewPager) {
		Adapter adapter = new Adapter(getSupportFragmentManager(), this);
		adapter.addFragment(new SearchFragment(), getString(R.string.fragment_cl_search_title));
		adapter.addFragment(new DirectFragment(), getString(R.string.fragment_cl_direct_title));
		adapter.addFragment(new SiteFragment(), getString(R.string.fragment_cl_site_title));
		adapter.addFragment(new EbayFragment(), getString(R.string.fragment_ebay_deal_title));
		viewPager.setAdapter(adapter);

	}
	private static class Adapter extends FragmentPagerAdapter {
		private final List<Fragment> mFragmentList = new ArrayList<>();
		private final List<String> mFragmentTitleList = new ArrayList<>();
		private Map<Integer, String> mFragmentTags;
		private FragmentManager mFragmentManager;
		private Context mContext;

		Adapter(FragmentManager fm, Context context) {
			super(fm);
			mFragmentManager = fm;
			mFragmentTags = new HashMap<Integer, String>();
			mContext = context;
		}


		//Create a method to return tag of a previously created fragment.
		public Fragment getFragment(int position) {
			String tag = mFragmentTags.get(position);
			if (tag == null)
				return null;
			return mFragmentManager.findFragmentByTag(tag);
		}

		void addFragment(Fragment fragment, String title) {
			mFragmentList.add(fragment);
			mFragmentTitleList.add(title);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}

		// Override InstantiateItem to save the tag of the fragment into a hashmap
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Object obj = super.instantiateItem(container, position);

			if (obj instanceof Fragment) {
				// record the fragment tag here.

				Fragment f = (Fragment) obj;
				String tag = f.getTag();
				mFragmentTags.put(position, tag);
			}
			return obj;
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

		@Override
		public Parcelable saveState() {
			// Do Nothing
			return null;
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {

		}


		@Override
		public CharSequence getPageTitle(int position) {
			return mFragmentTitleList.get(position);
		}
	}
}
