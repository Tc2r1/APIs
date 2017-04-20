package com.tc2r.apiexampletmdb;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Tc2r on 4/14/2017.
 * <p>
 * Description:
 */
public class AboutFragment extends Fragment {
	private TextView yourDescription, yourTitle, yourRank, yourDesignation;
	private ImageView yourImage, icon1, icon2, icon3, icon4 ;
	private Intent intent;
	private Uri uri;
	private LinearLayout yourBorder;

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container,
													 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.about_card_template, container, false);
		yourDescription = (TextView) view.findViewById(R.id.card_description);
		yourTitle = (TextView) view.findViewById(R.id.card_title);
		yourDesignation = (TextView) view.findViewById(R.id.card_designation);
		yourRank = (TextView) view.findViewById(R.id.card_ranklimit);
		yourImage = (ImageView) view.findViewById(R.id.card_image);
		yourBorder = (LinearLayout) view.findViewById(R.id.text_border_layout);
		icon1 = (ImageView) view.findViewById(R.id.icon_1);
		icon2 = (ImageView) view.findViewById(R.id.icon_2);
		icon3 = (ImageView) view.findViewById(R.id.icon_3);
		icon4 = (ImageView) view.findViewById(R.id.icon_4);

		icon1.setImageResource(R.drawable.icon_linkedin);
		icon2.setImageResource(R.drawable.icon_facebook);
		icon3.setImageResource(R.drawable.icon_android);
		icon4.setImageResource(R.drawable.icon_twitter);

		icon1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				uri = Uri.parse("https://www.linkedin.com/in/nudennie-white-99411075");
				intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);


			}
		});
		icon2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				uri = Uri.parse("https://www.facebook.com/Nwhite1985?");
				intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);


			}
		});
		icon3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				uri = Uri.parse("https://play.google.com/store/apps/developer?id=Tc2r");
				intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);


			}
		});
		icon4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				uri = Uri.parse("https://twitter.com/Tc2r1");
				intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);


			}
		});
		// YOUR POSITION TITLE IN THE PROJECT.
		yourTitle.setText(R.string.Team_Dre_Title);

		// YOUR CARD NUMBER (FAVORITE NUMBER)
		yourDesignation.setText("#23");

		// YOUR RANK
		yourRank.setText("SSJ");

		// YOUR DESCRIPTION
		yourDescription.setText(R.string.Team_Dre_Desc);

		// YOUR IMAGE (PLEASE SEND ME A 664X374 IMAGE TO USE FOR YOUR CARD
		yourImage.setImageResource(R.drawable.cardimage_dre);

		yourImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v){
				removeSelf();
			}
		});
		yourBorder.setBackgroundResource(R.drawable.text_border_about2);

		return view;
	}
	public void removeSelf(){
		getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
	}

}
