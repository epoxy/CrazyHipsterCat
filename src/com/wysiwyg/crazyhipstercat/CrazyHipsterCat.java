package com.wysiwyg.crazyhipstercat;

import com.google.ads.AdView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import com.google.ads.*;
import com.wysiwyg.crazyhipstercat.R.id;

public class CrazyHipsterCat extends Activity implements OnTouchListener, OnClickListener{

	private RelativeLayout relativeLayout;
	private MediaPlayer mp;
	private AdView adView;
	private Vibrator v;
	private long[] vibratePattern = {0, 500, 1000};
	private CheckBox mute, vibrate;
	private Button leftButton, rightButton;
	private String catString = "cat";
	private String catPictureString, catPicture;
	private int catPictureIdentifier;
	private int nbrOfPictures=5;
	private int resID;
	private Drawable drawableCat;
	private int buttonColor, textColor;
	//private RelativeLayout.LayoutParams adParams;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crazy_hipster_cat_activity);

		//request TEST ads to avoid being disabled for clicking your own ads
		AdRequest adRequest = new AdRequest();
		adRequest.addTestDevice(AdRequest.TEST_EMULATOR);// Emulator
		adRequest.addTestDevice("0019a4c2749d9e");// Test Android Device

		// Create the adView
		adView = (AdView)findViewById(R.id.adMob);

		catPictureIdentifier = nbrOfPictures;
		catPictureString = catString + catPictureIdentifier;
		catPicture = catPictureString + "closed";
		relativeLayout=(RelativeLayout) findViewById(R.id.relative_layout);
		relativeLayout.setBackgroundResource(R.drawable.cat5closed);
		relativeLayout.setOnTouchListener(this);
		mp = MediaPlayer.create(getBaseContext(), R.raw.cat01);
		mp.setLooping(true);

		// Initiate a generic request to load it with an ad
		adView.loadAd(adRequest);

		getBaseContext();
		// Get instance of Vibrator from current Context
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		//Colors
		textColor = (0xdd99ff00);
		buttonColor = (0xcccccccc);
		
		//Checkboxes
		mute = (CheckBox) findViewById(id.chkMute);
		vibrate = (CheckBox) findViewById(id.chkVibrate);
		mute.setTextColor(textColor);
		vibrate.setTextColor(textColor);
		mute.setTypeface(Typeface.DEFAULT_BOLD);
		vibrate.setTypeface(Typeface.DEFAULT_BOLD);

		//Buttons
		leftButton = (Button) findViewById(R.id.buttonLeft);
		rightButton = (Button) findViewById(R.id.buttonRight);
		leftButton.setBackgroundColor(buttonColor);
		rightButton.setBackgroundColor(buttonColor);
		leftButton.setTypeface(Typeface.DEFAULT_BOLD);
		rightButton.setTypeface(Typeface.DEFAULT_BOLD);
		leftButton.setOnClickListener((OnClickListener) this);
		rightButton.setOnClickListener((OnClickListener) this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.crazy_hipster_cat_activity, menu);
		return true;
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN){
			
			catPicture = catPictureString + "open";
			resID = getResources().getIdentifier(catPicture, "drawable", getPackageName());
			drawableCat = getResources().getDrawable(resID);
//			relativeLayout.setBackground(drawableCat);
			relativeLayout.setBackgroundDrawable(drawableCat);
			if(!mute.isChecked()){
				mp.start();
			}
			if(vibrate.isChecked()){
				startVibrateOnHold();
			}
		}
		if (event.getAction() == MotionEvent.ACTION_UP){
			
			catPicture = catPictureString + "closed";
			resID = getResources().getIdentifier(catPicture, "drawable", getPackageName());
			drawableCat = getResources().getDrawable(resID);
			relativeLayout.setBackgroundDrawable(drawableCat);
			if(!mute.isChecked()){
				mp.pause();
			}
			if(vibrate.isChecked()){
				stopVibrateOnRelease();
			}
		}
		return true;
	}
	
	public void onClick(View v) {
		if(v==findViewById(R.id.buttonLeft)){
			if(catPictureIdentifier!=1){
				catPictureIdentifier--;
				System.out.println("left" + catPictureIdentifier);
			}
		}
		if(v==findViewById(R.id.buttonRight)){
			if(catPictureIdentifier!=nbrOfPictures){
				catPictureIdentifier++;
				System.out.println("right" + catPictureIdentifier);
			}
		}
		catPictureString = catString + catPictureIdentifier;
		catPicture = catPictureString + "closed";
		resID = getResources().getIdentifier(catPicture, "drawable", getPackageName());
		drawableCat = getResources().getDrawable(resID);
		relativeLayout.setBackgroundDrawable(drawableCat);
		System.out.println(catPicture);
		
	}

	public void startVibrateOnHold(){
		v.vibrate(vibratePattern, 0);
	}
	public void stopVibrateOnRelease(){
		v.cancel();
	}

	public void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}
}
