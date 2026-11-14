package com.step9.flashcards;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.Locale;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    private WebView webView;
    private TextToSpeech tts;
    private boolean ttsReady = false;
    private String pendingText = null;

    public class SpeechBridge {
        @JavascriptInterface
        public void speak(final String text) {
            runOnUiThread(() -> doSpeak(text));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tts = new TextToSpeech(this, this);

        webView = new WebView(this);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);

        webView.addJavascriptInterface(new SpeechBridge(), "AndroidTTS");
        webView.loadUrl("file:///android_asset/index.html");
        setContentView(webView);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.US);
            tts.setSpeechRate(0.9f);
            ttsReady = true;
            if (pendingText != null) {
                doSpeak(pendingText);
                pendingText = null;
            }
        }
    }

    private void doSpeak(String text) {
        if (tts == null) return;
        if (!ttsReady) {
            pendingText = text;
            return;
        }
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "card");
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
