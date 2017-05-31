package com.tc2r.apiexamplegithub.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tc2r.apiexamplegithub.Model.User;
import com.tc2r.apiexamplegithub.R;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Tc2r on 4/20/2017.
 * <p>
 * Description:
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{

	// Declare Variables List of Model Objects, Context from calling activity.
	private List<User.ItemsEntity> itemsEntities;
	private Context context;

	// Default Constructor
	public UserAdapter(List<User.ItemsEntity> itemsEntities,Context context) {
		this.itemsEntities = itemsEntities;
		this.context = context;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		// Create a view using our row layout.xml file and return it
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

		View itemView = layoutInflater.inflate(R.layout.item_user, parent, false);

		MyViewHolder itemViewHolder = new MyViewHolder(itemView);

		return itemViewHolder;
	}


	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		// Create a model object that references to current recyclerview's  items.
		User.ItemsEntity mUser = itemsEntities.get(position);

		// set all data for that item
		holder.titleTV.setText(mUser.getLogin());
		holder.scoreTV.setText(mUser.getScore()+"");

		Log.wtf(TAG, "AVATAR: "+ mUser.getAvatar_url() + ".jpg");
		// Load images using Picasso or Glide
		Picasso.with(context)
						.load(mUser.getAvatar_url()+".jpg")
						.fit()
						.centerCrop()
						.into(holder.avatarIV);
	}

	@Override
	public int getItemCount() {
		// How many model items
		return itemsEntities.size();
	}

	// Create custom ViewHolder that will initialize all layout objects of our recyclerview.
	public class MyViewHolder extends RecyclerView.ViewHolder{
		public TextView titleTV, scoreTV;
		public ImageView avatarIV;
		public MyViewHolder(View itemView) {
			super(itemView);

			titleTV = (TextView) itemView.findViewById(R.id.tv_user_title);
			scoreTV = (TextView) itemView.findViewById(R.id.tv_user_score);
			avatarIV = (ImageView) itemView.findViewById(R.id.iv_user_avatar);
		}
	}

}
