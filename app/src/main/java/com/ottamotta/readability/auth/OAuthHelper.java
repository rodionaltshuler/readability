package com.ottamotta.readability.auth;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

public class OAuthHelper {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static Handler handler = new Handler(Looper.getMainLooper());

    interface Listener {
        void onRequestUrlReceived(String url);

        void onAccessTokenReceived(String accessToken);

        void onAuthFailed();
    }

    private Listener listener;

    private OAuthConsumer consumer;
    private OAuthProvider provider;
    private String callbackUrl;

    private static final String AUTHORIZE_ENDPOINT = "https://www.readability.com/api/rest/v1/oauth/authorize";
    private static final String REQUEST_TOKEN_ENDPOINT = "https://www.readability.com/api/rest/v1/oauth/request_token";

    private static final String ACCESS_TOKEN_ENDPOINT = "https://www.readability.com/api/rest/v1/oauth/access_token";

    static final String CLIENT_KEY = "rodionaltshuler";
    static final String CLIENT_SECRET = "6c5EThaahvfhAPjbrMzYQw7WzU8kb8fK";

    static final String CALLBACK_URL = "readability://ottamotta.com";

    private static OAuthHelper instance;

    public static synchronized OAuthHelper getInstance() {
        if (instance == null) {
            instance = new OAuthHelper();
        }
        return instance;
    }

    private OAuthHelper() {
        consumer = new CommonsHttpOAuthConsumer(CLIENT_KEY, CLIENT_SECRET);
        provider = new CommonsHttpOAuthProvider(
                REQUEST_TOKEN_ENDPOINT,
                ACCESS_TOKEN_ENDPOINT,
                AUTHORIZE_ENDPOINT);
        provider.setOAuth10a(true);
        callbackUrl = CALLBACK_URL;
    }

    public void startAuth() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    String authUrl = provider.retrieveRequestToken(consumer,
                            callbackUrl);
                    notifyRequestUrlReceived(authUrl);
                } catch (Exception e) {
                    notifyAuthFailed();
                }
            }
        });
    }

    private String[] getVerifier(@NonNull Uri uri) {
        String token = uri.getQueryParameter("oauth_token");
        String verifier = uri.getQueryParameter("oauth_verifier");
        return new String[]{token, verifier};
    }

    public String[] getAccessToken(String verifier)
            throws OAuthMessageSignerException, OAuthNotAuthorizedException,
            OAuthExpectationFailedException, OAuthCommunicationException {
        provider.retrieveAccessToken(consumer, verifier);
        return new String[]{
                consumer.getToken(), consumer.getTokenSecret()
        };
    }

    public void setCallbackUrl(final String url) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Uri uri = Uri.parse(url);
                final String[] verifier = getVerifier(uri);
                try {
                    String[] accessToken = getAccessToken(verifier[1]);
                    notifyAccessTokenReceived(accessToken[1]);
                } catch (Exception e) {
                    notifyAuthFailed();
                }
            }
        });

    }

    private void notifyRequestUrlReceived(final String url) {
        if (listener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onRequestUrlReceived(url);
                }
            });
        }
    }

    private void notifyAccessTokenReceived(final String accessToken) {
        if (listener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onAccessTokenReceived(accessToken);
                }
            });
        }
    }

    private void notifyAuthFailed() {
        if (listener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onAuthFailed();
                }
            });
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
