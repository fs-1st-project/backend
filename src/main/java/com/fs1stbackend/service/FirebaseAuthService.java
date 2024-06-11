package com.fs1stbackend.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;

@Service
public class FirebaseAuthService {

    public FirebaseToken verifyIdToken(String idToken) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().verifyIdToken(idToken);
    }

    public UserRecord getUser(String uid) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().getUser(uid);
    }

    public UserRecord createUser(String uid, String email, String displayName) throws FirebaseAuthException {
        UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                .setUid(uid)
                .setEmail(email)
                .setDisplayName(displayName);
        return FirebaseAuth.getInstance().createUser(createRequest);
    }

    public String createCustomToken(String uid) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().createCustomToken(uid);
    }
}
