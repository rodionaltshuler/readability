package com.ottamotta.readability.auth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ottamotta.readability.R;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = AuthActivity.class.getSimpleName();

    @Bind(R.id.progress)
    ProgressBar progressBar;

    @Bind(R.id.web_view)
    WebView webView;

    @BindInt(R.integer.max_progress)
    int maxProgress;

    private OAuthHelper oAuthHelper = OAuthHelper.getInstance();

    private OAuthHelper.Listener oauthListener = new OAuthHelper.Listener() {
        @Override
        public void onRequestUrlReceived(String url) {
            webView.loadUrl(url);
            Log.d(TAG, "Got url: " + url);
        }

        @Override
        public void onAccessTokenReceived(String accessToken) {
            Toast.makeText(AuthActivity.this, "Got access token: " + accessToken, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAuthFailed() {
            Toast.makeText(AuthActivity.this, "Not authorized", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        ButterKnife.bind(this);
        setupWebView();
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {
        public void onProgressChanged(WebView view, int progress) {
            Log.d(TAG, "Progress changed: " + progress);
            progressBar.setProgress(progress);
            if (maxProgress == progress) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    };

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(OAuthHelper.CALLBACK_URL)) {
                oAuthHelper.setCallbackUrl(url);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    private void setupWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);
    }

    @Override
    protected void onResume() {
        super.onResume();
        oAuthHelper.setListener(oauthListener);
        oAuthHelper.startAuth();
    }

    @Override
    protected void onPause() {
        super.onPause();
        oAuthHelper.setListener(null);
    }

}

