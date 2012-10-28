package com.wysiwyg.crazyhipstercat;

import com.google.ads.AdView;

import android.app.Activity;
import android.graphics.Path.FillType;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import com.google.ads.*;

public class CrazyHipsterCat extends Activity implements OnTouchListener{

	private RelativeLayout relativeLayout;
	private MediaPlayer mp;
	private AdView adView;
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
	    
		relativeLayout=(RelativeLayout) findViewById(R.id.relative_layout);
		relativeLayout.setBackgroundResource(R.drawable.cat);
		relativeLayout.setOnTouchListener(this);
		mp = MediaPlayer.create(getBaseContext(), R.raw.cat01);
		mp.setLooping(true);
		
//	    // Initiate a generic request to load it with an ad
	    adView.loadAd(adRequest);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.crazy_hipster_cat_activity, menu);
		return true;
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN){
			relativeLayout.setBackgroundResource(R.drawable.cat2);
			mp.start();
		}
		if (event.getAction() == MotionEvent.ACTION_UP){
			relativeLayout.setBackgroundResource(R.drawable.cat);
			mp.pause();
		}
		return true;
	}
	public void onDestroy() {
	    if (adView != null) {
	      adView.destroy();
	    }
	    super.onDestroy();
	  }
}
