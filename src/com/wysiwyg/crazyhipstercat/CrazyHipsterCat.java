package com.wysiwyg.crazyhipstercat;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

public class CrazyHipsterCat extends Activity implements OnTouchListener{

	private RelativeLayout relativeLayout;
	private MediaPlayer mp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crazy_hipster_cat_activity);
        relativeLayout=(RelativeLayout) findViewById(R.id.relative_layout);
        relativeLayout.setBackgroundResource(R.drawable.cat);
        relativeLayout.setOnTouchListener(this);
        mp = new MediaPlayer();
        mp.create(getBaseContext(), R.raw.cat01);
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
			mp.stop();
		}
		return true;
	}
}
