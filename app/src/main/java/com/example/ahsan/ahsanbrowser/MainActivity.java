package com.example.ahsan.ahsanbrowser;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    EditText editText;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);

        button = (Button) findViewById(R.id.button);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.GONE);


        webView = (WebView) findViewById(R.id.webView);

        if(savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        }else {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(false);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.setBackgroundColor(Color.WHITE);

            webView.setWebViewClient(new ourViewClient());

            webView.setWebChromeClient(new WebChromeClient() {
            @Override
                public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                progressBar.setVisibility(progressBar.VISIBLE);
                }
                if (progress == 100){
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
            });
        }
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        webView.loadUrl("https://" + editText.getText().toString());
        editText.setText("");

    }
});
    }
    public class ourViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            CookieManager.getInstance().setAcceptCookie(true);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_back:
                if(webView.canGoBack()){
                    webView.goBack();
                }
                return true;

            case R.id.item_forward:
                if(webView.canGoForward()){
                    webView.goForward();
                }
                return true;

                case R.id.item_home:
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    webView.loadUrl("https://google.com");
                    editText.setText("");
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}

