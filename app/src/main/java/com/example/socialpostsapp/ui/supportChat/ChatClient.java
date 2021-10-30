package com.example.socialpostsapp.ui.supportChat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.*;

public class ChatClient {

    static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    static public CollectionReference collectionReference = firebaseFirestore.collection("support").document(FirebaseAuth.getInstance().getUid()).collection("chat");

    public static Task<QuerySnapshot> getMessages(){
        return collectionReference.orderBy("sendAt", Query.Direction.DESCENDING).get();
    }

    public static void sendMessage(String text){
        Map<String, Object> map = new HashMap<>();
        map.put("message", text);
        map.put("isSupport", false);
        Timestamp timestamp = new Timestamp(new Date());
        map.put("sendAt", timestamp);
        collectionReference.add(map);
    }

}
