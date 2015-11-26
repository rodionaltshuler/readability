package com.ottamotta.readability.credentials;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ottamotta.readability.ReadabilityApp;

public class CredentialsManager {

    private static CredentialsManager instance;

    private CredentialsRepository credentialsRepository;

    public static synchronized CredentialsManager getInstance() {
        if (null == instance) {
            instance = new CredentialsManager();
        }
        return instance;
    }

    private CredentialsManager() {
        credentialsRepository = new CredentialsRepository(ReadabilityApp.getInstance());
    }

    public void updateOAuthData(@NonNull OAuthCredentials oAuthCredentials) {
        credentialsRepository.save(oAuthCredentials);
    }

    public
    @Nullable
    OAuthCredentials getoAuthCredentials() {
        return credentialsRepository.getCredentials();
    }
}
