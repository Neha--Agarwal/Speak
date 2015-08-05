package com.neha.andro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main extends Activity implements OnInitListener {

	private TextToSpeech myTTS;
	private List<String> phrases = new ArrayList<String>();
	String speak="Hello there!";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final EditText text= (EditText) findViewById(R.id.editText);

		Button startButton = (Button) findViewById(R.id.start_button);
		startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				speak= text.getText().toString();
				Intent checkIntent = new Intent();
				checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
				startActivityForResult(checkIntent, 1);	
			}
		});	
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {

			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				myTTS = new TextToSpeech(this, this);
				myTTS.setLanguage(Locale.US);
			} else {
				Intent ttsLoadIntent = new Intent();
				ttsLoadIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(ttsLoadIntent);
			}
		}
	}

	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			myTTS.speak(speak, TextToSpeech.QUEUE_FLUSH, null);

		} else if (status == TextToSpeech.ERROR) {
			myTTS.shutdown();
		}
	}
}
