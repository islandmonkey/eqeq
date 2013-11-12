package com.islandmonkey.eqeq;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.media.audiofx.Equalizer;
import android.media.AudioManager;
import android.app.IntentService;
import android.content.Context;


public class EQActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eq);
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.eq, menu);
        return true;
    }
    
}

class EQSystem extends Equalizer {

	public EQSystem(int priority, int audioSession)
			throws IllegalStateException, IllegalArgumentException,
			UnsupportedOperationException, RuntimeException {
		super(priority, audioSession);
		
	}
}

