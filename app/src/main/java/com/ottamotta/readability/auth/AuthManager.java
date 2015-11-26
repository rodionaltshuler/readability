package com.ottamotta.readability.auth;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.ottamotta.readability.credentials.OAuthCredentials;
import com.ottamotta.readability.credentials.CredentialsManager;

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

public class AuthManager {

    private CredentialsManager credentialsManager = CredentialsManager.getInstance();

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static Handler handler = new Handler(Looper.getMainLooper());

    interface Listener {
        void onRequestUrlReceived(String url);

        void onAuthCredsReceived(OAuthCredentials creds);

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

    private static AuthManager instance;

    public static synchronized AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    private AuthManager() {
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

    public OAuthCredentials getOAuthData(String verifier)
            throws OAuthMessageSignerException, OAuthNotAuthorizedException,
            OAuthExpectationFailedException, OAuthCommunicationException {
        provider.retrieveAccessToken(consumer, verifier);
        return new OAuthCredentials(consumer.getToken(), consumer.getTokenSecret());
    }

    public void setCallbackUrl(final String url) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Uri uri = Uri.parse(url);
                final String[] verifier = getVerifier(uri);
                try {
                    OAuthCredentials oAuthCredentials = getOAuthData(verifier[1]);
                    saveOAuthData(oAuthCredentials);
                    notifyOAuthDataReceived(oAuthCredentials);
                } catch (Exception e) {
                    notifyAuthFailed();
                }
            }
        });

    }

    private void saveOAuthData(OAuthCredentials oAuthCredentials) {
        credentialsManager.updateOAuthData(oAuthCredentials);
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

    private void notifyOAuthDataReceived(final OAuthCredentials oAuthCredentials) {
        if (listener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onAuthCredsReceived(oAuthCredentials);
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

    public static String getClientKey() {
        return CLIENT_KEY;
    }

    public static String getClientSecret() {
        return CLIENT_SECRET;
    }
}
