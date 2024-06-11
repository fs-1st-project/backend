package com.fs1stbackend.service;

import com.google.firebase.database.*;
import org.springframework.stereotype.Service;

/*Firebase 데이터베이스 접근*/

@Service
public class FirebaseService {

    public void fetchDataFromFirebase() {

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("restricted_access/secret_document");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object document = dataSnapshot.getValue();
                System.out.println(document);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error reading data: " + error.getMessage());
            }
        });
    }
}