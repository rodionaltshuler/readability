package com.ottamotta.readability.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ottamotta.readability.R;
import com.ottamotta.readability.common.ui.BaseActivity;
import com.ottamotta.readability.library.LibraryActivity;
import com.ottamotta.readability.user.OAuthCredentials;
import com.ottamotta.readability.user.UserManager;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;

public class AuthActivity extends BaseActivity {

    private static final String TAG = AuthActivity.class.getSimpleName();

    @Bind(R.id.progress)
    ProgressBar progressBar;

    @Bind(R.id.web_view)
    WebView webView;

    @BindInt(R.integer.max_progress)
    int maxProgress;

    private AuthManager oAuthHelper = AuthManager.getInstance();

    private AuthManager.Listener oauthListener = new AuthManager.Listener() {
        @Override
        public void onRequestUrlReceived(String url) {
            webView.loadUrl(url);
            Log.d(TAG, "Got url: " + url);
        }

        @Override
        public void onAuthCredsReceived(OAuthCredentials credentials) {
            Log.d(TAG, "Got OAuth credentials: " + credentials.toString());
            LibraryActivity.start(AuthActivity.this);
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
            if (url.startsWith(AuthManager.CALLBACK_URL)) {
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
        if (UserManager.getInstance().getoAuthCredentials() != null) {
            LibraryActivity.start(this);
            finish();
        } else {
            oAuthHelper.setListener(oauthListener);
            oAuthHelper.startAuth(UserManager.getInstance().getMe());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        oAuthHelper.setListener(null);
    }

}

