package com.example.mazegame;


import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

public class DataManager {

    private final FirebaseDatabase realTimeDB;


    private ArrayList<User> users;
    private User currentUser;
    private static DataManager single_instance = null;

    private DataManager(){
        realTimeDB = FirebaseDatabase.getInstance();
    }

    public static DataManager getInstance() {
        return single_instance;
    }
    public ArrayList<User> getUsers() {
        return users;
    }
    public static DataManager initHelper() {
        if (single_instance == null) {
            single_instance = new DataManager();
        }
        return single_instance;
    }

    public FirebaseDatabase getRealTimeDB() {
        return realTimeDB;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public DataManager setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        return this;
    }

//    public void storeUserInDB(User userToStore) {
//        realTimeDB.collection("Users")
//                .document(userToStore.getUserId())
//                .set(userToStore)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d("pttt", "DocumentSnapshot Successfully written!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("pttt", "Error adding document", e);
//                    }
//                });
//    }





    public static ArrayList<Level> generateLevels() {
        ArrayList<Level> levels = new ArrayList<>();

        levels.add(new Level(1, 4,4));
        levels.add(new Level(2, 5,5));
        levels.add(new Level(3, 6,6));
        levels.add(new Level(4, 7,7));
        levels.add(new Level(5, 8,8));
        levels.add(new Level(6, 9,9));
        levels.add(new Level(7, 10,10));
        levels.add(new Level(8, 11,11));
        return levels;
    }


}
