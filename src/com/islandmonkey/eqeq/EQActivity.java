/*
The MIT License (MIT)

Copyright (c) 2013 Aidan Fell

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.islandmonkey.eqeq;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.audiofx.AudioEffect;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.islandmonkey.eqeq.AboutForm;
import android.widget.CompoundButton;
import android.app.Dialog;

/**
 * The following class initialises all essential utilities to load the app.
 * @author Aidan Fell
 *
 */

public class EQActivity extends Activity {
	final public int version = android.os.Build.VERSION.SDK_INT;
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eq);
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        // TODO: Make it work!
        this.checkForIntSpeaker(am);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.eq, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    		case R.id.about_form:
    			Intent intent = new Intent(this, AboutForm.class);
    			this.startActivity(intent); // That's right now (well, it at least looks better)
    			break;
    	}
    	return true;
    }
    // TODO: Make this work with onCreate (currently passes NullPointerException with onCreate!)
    public boolean checkForIntSpeaker(AudioManager am) {
    	if (am.isWiredHeadsetOn()) {
    		Bundle savedInstanceState = new Bundle();
    		IsHeadsetOn headset = new IsHeadsetOn();
    		headset.onCreateDialog(savedInstanceState);
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
}

class IsHeadsetOn extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.dialog1_title)
			.setMessage(R.string.dialog1_message);
		return builder.create();
		
	}
}

/**
 * 
 * This class contains the core equaliser system.
 * <p>
 * It initialises the AudioEffect listeners that detect when the switch/checkbox is changed,
 * subsequently turning the equaliser system on/off.
 * @param setListener - sets listeners.
 *
 */

class EQSystem extends Equalizer {

	public EQSystem(int priority, int audioSession)
			throws IllegalStateException, IllegalArgumentException,
			UnsupportedOperationException, RuntimeException {
		super(priority, audioSession);
		
	}
}

