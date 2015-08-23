package com.wysiwyg.crazyhipstercat;

import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class WorldCupCat extends RelativeLayout implements OnTouchListener, OnClickListener{

	private ImageView[] vmCatImage;
	private ImageView vmCatMouth;
	private ImageButton[] flagButton;
	private LinearLayout flagLayout;
	
	@SuppressWarnings("deprecation")
	public WorldCupCat(Context context) {
		super(context);
		
		//Initiate images
		setBackgroundColor(Color.BLACK);
		vmCatImage = new ImageView[4];
		vmCatImage[0] = new ImageView(context);
		vmCatImage[0].setImageDrawable(getResources().getDrawable(R.drawable.brazil));
		
		vmCatMouth = new ImageView(context);
		vmCatMouth.setImageDrawable(getResources().getDrawable(R.drawable.footballmouth));
		vmCatMouth.setVisibility(INVISIBLE);
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		addView(vmCatImage[0], params);
		addView(vmCatMouth, params);
		
		setOnTouchListener((OnTouchListener) context);

		//Set up flag-buttons
		flagButton = new ImageButton[4];
		LinearLayout.LayoutParams dimenParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		flagButton[0] = new ImageButton(context);
		flagButton[0].setBackgroundDrawable(getResources().getDrawable(R.drawable.flag_brazil_small));
		flagButton[0].setLayoutParams(dimenParams);
		flagButton[0].setOnClickListener((OnClickListener) this);
		flagButton[1] = new ImageButton(context);
		flagButton[1].setBackgroundDrawable(getResources().getDrawable(R.drawable.flag_germany_small));
		flagButton[1].setLayoutParams(dimenParams);
		flagButton[1].setOnClickListener((OnClickListener) this);
		flagButton[2] = new ImageButton(context);
		flagButton[2].setBackgroundDrawable(getResources().getDrawable(R.drawable.flag_argentina_small));
		flagButton[2].setLayoutParams(dimenParams);
		flagButton[2].setOnClickListener((OnClickListener) this);
		flagButton[3] = new ImageButton(context);
		flagButton[3].setBackgroundDrawable(getResources().getDrawable(R.drawable.flag_holland_small));
		flagButton[3].setLayoutParams(dimenParams);
		flagButton[3].setOnClickListener((OnClickListener) this);
		
		//LinearLayout with flagbuttons
		flagLayout = new LinearLayout(context);
		flagLayout.setOrientation(LinearLayout.HORIZONTAL);
		flagLayout.addView(flagButton[0]);
		flagLayout.addView(flagButton[1]);
		flagLayout.addView(flagButton[2]);
		flagLayout.addView(flagButton[3]);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay(); 
		int width = display.getWidth();
		int height = display.getHeight();
		int flagWidth = width/4;
		int flagHeight = flagWidth*2/3;
		LinearLayout.LayoutParams flagParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, flagHeight);
		addView(flagLayout, flagParams);
		
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN){
			//Add mouth
			System.out.println("VM nertryckt");
			vmCatMouth.setVisibility(VISIBLE);
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_UP){
			//Reset cat picture
			System.out.println("VM sl�ppt");
			vmCatMouth.setVisibility(INVISIBLE);
			return true;
		}
		return false;
	}
	@Override
	public void onClick(View v) {
		if(v==flagButton[0]){
			vmCatImage[0].setImageDrawable(getResources().getDrawable(R.drawable.brazil));
		}
		if(v==flagButton[1]){
			vmCatImage[0].setImageDrawable(getResources().getDrawable(R.drawable.germany));
		}
		if(v==flagButton[2]){
			vmCatImage[0].setImageDrawable(getResources().getDrawable(R.drawable.argentina));
		}
		if(v==flagButton[3]){
			vmCatImage[0].setImageDrawable(getResources().getDrawable(R.drawable.holland));
		}
	}
}
