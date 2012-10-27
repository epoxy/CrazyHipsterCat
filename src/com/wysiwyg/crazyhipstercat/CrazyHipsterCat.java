package com.wysiwyg.crazyhipstercat;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CrazyHipsterCat extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crazy_hipster_cat_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crazy_hipster_cat_activity, menu);
        return true;
    }
}
