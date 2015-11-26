package com.ottamotta.readability.user;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ottamotta.readability.ReadabilityApp;

public class UserManager {

    private static UserManager instance;

    private UserRepository userRepository;

    public static synchronized UserManager getInstance() {
        if (null == instance) {
            instance = new UserManager();
        }
        return instance;
    }

    private UserManager() {
        userRepository = new UserRepository(ReadabilityApp.getInstance());
    }

    public synchronized
    @Nullable
    User getMe() {
        return userRepository.getMe();
    }

    public synchronized void setCurrentUser(User me) {
        userRepository.save(me);
    }

    public void updateOAthData(@NonNull OAuthCredentials oAuthCredentials) {
        User me = getMe();
        me.setoAuthCredentials(oAuthCredentials);
        userRepository.save(me);
    }

    public
    @Nullable
    OAuthCredentials getoAuthCredentials() {
        if (getMe() == null) {
            return null;
        }
        return getMe().getoAuthCredentials();
    }
}
