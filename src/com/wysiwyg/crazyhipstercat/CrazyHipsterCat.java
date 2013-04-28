package com.wysiwyg.crazyhipstercat;

import java.io.IOException;
import java.util.Random;
import com.google.ads.AdView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.ads.*;
import com.wysiwyg.crazyhipstercat.R.id;

public class CrazyHipsterCat extends Activity implements OnTouchListener, OnClickListener{
	private RelativeLayout relativeLayout;
	private MediaPlayer mp, mpGoat;
	private AdView adView;
	private Vibrator v;
	private long[] vibratePattern = {0, 500, 1000};
	private CheckBox mute, vibrate, goatCheck;
	private Button leftButton, rightButton, randomButton;
	private String catString = "cat";
	private String catPictureString, catPicture;
	private int catPictureIdentifier;
	/*Increment at new release*/
	private int nbrOfPictures=7;
	private int startingCat=6;
	private int resID;
	private int oldIdentifier;
	private int buttonColor, textColor;
	private Random randomizer;
	private View[] goatImage;
	private boolean goatRunning;
	private int width;
	private int height;
	private int goatWidth;
	private int goatHeight;
	private RelativeLayout.LayoutParams goatLayout;
	private int apiLevel;
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crazy_hipster_cat_activity);

		//request TEST ads to avoid being disabled for clicking your own ads
		AdRequest adRequest = new AdRequest();
		adRequest.addTestDevice(AdRequest.TEST_EMULATOR);// Emulator
		adRequest.addTestDevice("0019a4c2749d9e");// Test Android Device

		//Get API-level
		apiLevel=Integer.valueOf(android.os.Build.VERSION.SDK_INT);
		Log.i("TAG", "API" + Integer.valueOf(android.os.Build.VERSION.SDK_INT));

		// Create the adView
		adView = (AdView)findViewById(R.id.adMob);

		catPictureIdentifier = startingCat;
		catPictureString = catString + catPictureIdentifier;
		catPicture = catPictureString + "closed";
		resID = getResources().getIdentifier(catPicture, "drawable", getPackageName());
		relativeLayout=(RelativeLayout) findViewById(R.id.relative_layout);
		relativeLayout.setBackgroundResource(resID);
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
		goatCheck = (CheckBox) findViewById(id.chkGoat);
		mute.setTextColor(textColor);
		vibrate.setTextColor(textColor);
		goatCheck.setTextColor(textColor);
		mute.setTypeface(Typeface.DEFAULT_BOLD);
		vibrate.setTypeface(Typeface.DEFAULT_BOLD);
		goatCheck.setTypeface(Typeface.DEFAULT_BOLD);

		//Buttons
		leftButton = (Button) findViewById(R.id.buttonLeft);
		rightButton = (Button) findViewById(R.id.buttonRight);
		randomButton = (Button) findViewById(R.id.buttonRandom);
		leftButton.setBackgroundColor(buttonColor);
		rightButton.setBackgroundColor(buttonColor);
		randomButton.setBackgroundColor(buttonColor);
		leftButton.setTypeface(Typeface.DEFAULT_BOLD);
		rightButton.setTypeface(Typeface.DEFAULT_BOLD);
		randomButton.setTypeface(Typeface.DEFAULT_BOLD);
		leftButton.setOnClickListener((OnClickListener) this);
		rightButton.setOnClickListener((OnClickListener) this);
		randomButton.setOnClickListener((OnClickListener) this);

		//Randomizer
		randomizer = new Random();

		//GoatImage
		goatImage = new ImageView[2];
		goatImage[0] = findViewById(R.id.goatImage);
		goatImage[1] = findViewById(R.id.goatImage2);
		goatImage[0].setVisibility(View.GONE);
		goatImage[1].setVisibility(View.GONE);
		//Adjust size of goat
		if(apiLevel>=11){
			DisplayMetrics metrics = this.getResources().getDisplayMetrics();
			width = metrics.widthPixels;
			height = metrics.heightPixels;
			goatWidth = width/3;
			goatHeight = height/3;
			goatLayout = new RelativeLayout.LayoutParams (goatWidth, goatHeight);
			for(View goatElem : goatImage){
				goatElem.setLayoutParams(goatLayout);
				goatElem.requestLayout();
			}
			goatImage[0].setX(width-goatWidth);
			goatImage[1].setX(0);
		}
		//GoatSound
		mpGoat = MediaPlayer.create(getBaseContext(), R.raw.goat);
		mpGoat.setLooping(false);

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
			relativeLayout.setBackgroundResource(resID);
			if(!mute.isChecked()){
				mp.start();
			}
			if(vibrate.isChecked()){
				startVibrateOnHold();
			}
			if(goatCheck.isChecked()){
				if(!goatRunning){
					showGoat(apiLevel);
				}
			}

		}
		if (event.getAction() == MotionEvent.ACTION_UP){

			catPicture = catPictureString + "closed";
			resID = getResources().getIdentifier(catPicture, "drawable", getPackageName());
			relativeLayout.setBackgroundResource(resID);
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
			catPictureIdentifier=(catPictureIdentifier+nbrOfPictures-1)%nbrOfPictures;
			System.out.println("left" + catPictureIdentifier);
		}
		if(v==findViewById(R.id.buttonRight)){
			catPictureIdentifier=(catPictureIdentifier+1)%nbrOfPictures;
			System.out.println("right" + catPictureIdentifier);
		}
		if(v==findViewById(R.id.buttonRandom)){
			do{
				oldIdentifier = catPictureIdentifier;
				catPictureIdentifier = (int) (nbrOfPictures*randomizer.nextDouble());
				System.out.println("random" + catPictureIdentifier);
			} while(oldIdentifier==catPictureIdentifier);
		}
		catPictureString = catString + catPictureIdentifier;
		catPicture = catPictureString + "closed";
		resID = getResources().getIdentifier(catPicture, "drawable", getPackageName());
		relativeLayout.setBackgroundResource(resID);
		System.out.println(catPicture);

	}
	@SuppressLint("NewApi")
	public void showGoat(int api){
		//Randomize position of goat
		if(api>=11){
			int randomYPos = (int) ((height-goatHeight-50)*(randomizer.nextDouble()));
			int randomSide = randomizer.nextInt(2);
			Log.i("TAG", "Randomizer" + randomSide);
			Log.i("TAG", "dandHeight" + height);
			Log.i("TAG", "dandHeight" + goatHeight);
			Log.i("TAG", "dandHeight" + randomYPos);
			goatImage[randomSide].setY(randomYPos);
			goatImage[randomSide].setVisibility(View.VISIBLE);
		}
		else{
			goatImage[0].setVisibility(View.VISIBLE);
		}
		if(!mute.isChecked()){
			mpGoat.start();
		}
		goatRunning=true;
		new CountDownTimer(1500, 1000) {
			public void onTick(long millisUntilFinished) {
			}

			public void onFinish() {
				goatImage[0].setVisibility(View.GONE);
				goatImage[1].setVisibility(View.GONE);
				try {
					mpGoat.prepare();
				} catch (IllegalStateException e) {
					Log.e("EXCEPTION", "Illegal state", e);
					e.printStackTrace();
				} catch (IOException e) {
					Log.e("EXCEPTION", "IOException", e);
					e.printStackTrace();
				}
				goatRunning=false;
			}
		}.start();
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
