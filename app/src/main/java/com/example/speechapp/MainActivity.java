package com.example.speechapp;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    public static final int TTS_ENGINE_REQUEST = 101;
    private TextToSpeech textToSpeech;
    private EditText textforspeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textforspeech=findViewById(R.id.Speech);
    }

    public void performSpeech(View view) {
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, TTS_ENGINE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == TTS_ENGINE_REQUEST && requestCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
            textToSpeech = new TextToSpeech(this, this);
        }
        else

        {
            Intent installintent = new Intent();
            installintent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installintent);
        }

    }



    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS)
        {
            int languagestatus=textToSpeech.setLanguage(Locale.US);
            if (languagestatus==TextToSpeech.LANG_MISSING_DATA||languagestatus==TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Toast.makeText(this,"Languages is not supported...",Toast.LENGTH_SHORT).show();
            }
            else {
                String data=textforspeech.getText().toString();
                int speechstatus=textToSpeech.speak(data,TextToSpeech.QUEUE_FLUSH,null);
                if (speechstatus==TextToSpeech.ERROR){
                    Toast.makeText(this,"Error while speech",Toast.LENGTH_SHORT).show();
                }

            }

        }
        else
            {
                Toast.makeText(this,"text to speech failed..",Toast.LENGTH_SHORT).show();

        }
    }
}
