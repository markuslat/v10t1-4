package com.example.v10;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {


    WebView web;
    EditText setText;
    Context context;
    Button shoutOutButton;
    Button initializeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        shoutOutButton = findViewById( R.id.shoutOutButton );
        shoutOutButton.setVisibility( View.INVISIBLE );
        initializeButton = findViewById( R.id.initializeButton );
        initializeButton.setVisibility( View.INVISIBLE );

        web = (WebView) findViewById(R.id.webView1);
        setText = (EditText) findViewById(R.id.setText);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("file:///android_asset/home.html");

        setText.setOnKeyListener(new View.OnKeyListener() {
            InputMethodManager imm = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE );
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (setText.getText().toString().equals("index.html")) {
                        web.loadUrl("file:///android_asset/" + setText.getText());
                        shoutOutButton.setVisibility( View.VISIBLE );
                        initializeButton.setVisibility( View.VISIBLE );
                        if (imm != null) {
                            imm.hideSoftInputFromWindow( setText.getWindowToken(), 0 );
                        }
                    } else {
                        shoutOutButton.setVisibility( View.INVISIBLE );
                        initializeButton.setVisibility( View.INVISIBLE );
                        web.loadUrl("http://" + setText.getText());
                        if (imm != null) {
                            imm.hideSoftInputFromWindow( setText.getWindowToken(), 0 );
                        }
                    }
                    return true;
                }
                return false;
            }
        });

    }

    public void refreshPage(View view) {

        String url = web.getUrl();
        web.loadUrl( url );
    }

    public void shoutOutButton(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            web.evaluateJavascript("javascript:shoutOut()", null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void initializeButton(View v) {
            web.evaluateJavascript("javascript:initialize()", null);
    }


    public void onBackPressed(View v) {
        if (web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
        }
    }
    public void next (View v) {
        if (web.canGoForward()) {
            web.goForward();
        }
    }
}
