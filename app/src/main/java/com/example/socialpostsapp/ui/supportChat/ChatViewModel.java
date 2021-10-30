package com.example.socialpostsapp.ui.supportChat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatViewModel extends ViewModel {

    MutableLiveData<List<Map<String, Object>>> messages = new MutableLiveData<>();

    public void getMessages(){
        ChatClient.getMessages().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Map<String, Object>> messagesList = new ArrayList<>();
                    for(DocumentSnapshot item : task.getResult().getDocuments()){
                        Map<String, Object> map = new HashMap<>();
                        map.put("message", item.get("message"));
                        map.put("isSupport", item.get("isSupport"));
                        map.put("sendAt", item.get("sendAt"));
                        messagesList.add(map);
                    }
                    messages.setValue(messagesList);
                }
            }
        });
    }

    public void sendMessage(String text){
        ChatClient.sendMessage(text);
    }

    public void listenForMessages(ChatRecyclerAdapter adapter) {
        ChatClient.collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.getDocumentChanges().size() == 1){
                    QueryDocumentSnapshot queryDocumentSnapshot = value.getDocumentChanges().get(0).getDocument();
                    Map<String, Object> map = new HashMap<>();
                    map.put("message", queryDocumentSnapshot.get("message"));
                    map.put("isSupport", queryDocumentSnapshot.get("isSupport"));
                    map.put("sendAt", queryDocumentSnapshot.get("sendAt"));
                    adapter.insetToList(map);
                }
            }
        });
    }


}
