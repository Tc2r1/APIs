package com.tc2r.apiexamplegithub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tc2r.apiexamplegithub.Model.User;
import com.tc2r.apiexamplegithub.Remote.MyApiEndpointsInterface;
import com.tc2r.apiexamplegithub.Adapters.UserAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

	// Declare Constants
	private static final String TAG = "MainActivity";

	// Declare Variables
	private RecyclerView recyclerView;
	private TextView titleTv;
	private TextView countTv;
	private EditText searchEt;
	private Button searchBtn;
	private UserAdapter mAdapter;
	List<User.ItemsEntity> Users;
	RecyclerView.LayoutManager mLayoutManager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// The Recyclerview, button and edit text reference
		recyclerView = (RecyclerView) findViewById(R.id.recyclerview_main);
		searchEt = (EditText) findViewById(R.id.et_main_search);
		searchBtn = (Button) findViewById(R.id.btn_main_search);


		// Initialize User Model Array, Layout Manager, and Recyclerview
		Users = new ArrayList<User.ItemsEntity>();
		mLayoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.setHasFixedSize(true);

		// Create listener for button to search user's input.

		searchBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					// Get the query from editText, as a string.
					String query = searchEt.getText().toString();
					query.trim();
					//query = query.replace(" ", "+");
					Log.i(TAG, "Query :" + query);

					// Execute request using saved query.
					// Callbacks
					MyApiEndpointsInterface
									.Factory
									.getInstance()
									.searchForUserName(query)
									.enqueue(new Callback<User>() {
										@Override
										public void onResponse(Call<User> call, retrofit2.Response<User> response) {
											Log.d(TAG, "onResponse: " + response.code());

											if (response.isSuccessful()) {
												User result = response.body();
												Users = result.getItems();
												mAdapter = new UserAdapter(Users, getApplicationContext());

												// Attach adapter to recyclerview
												recyclerView.setAdapter(mAdapter);
											}
										}
										@Override
										public void onFailure(Call<User> call, Throwable t) {
											Log.e(TAG, "FAILED" + t.getMessage());
										}
									});
				}catch (Exception e){

				}
			}
		});

	}
}
