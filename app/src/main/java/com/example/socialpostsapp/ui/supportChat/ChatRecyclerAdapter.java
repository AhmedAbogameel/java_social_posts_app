package com.example.socialpostsapp.ui.supportChat;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.socialpostsapp.R;
import com.google.firebase.Timestamp;


import java.text.SimpleDateFormat;
import java.util.*;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ChatViewHolder> {

    private List<Map<String, Object>> list = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    ChatRecyclerAdapter(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void insetToList(Map<String, Object> map){
        list.add(0, map);
        notifyItemInserted(0);
        linearLayoutManager.scrollToPosition(0);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_bubble, parent, false);
        ChatViewHolder ChatViewHolder = new ChatViewHolder(view);
        return ChatViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Map<String , Object> message = list.get(position);
        String text = (String) message.get("message");
        boolean isSupport = (boolean) message.get("isSupport");
        Timestamp timestamp = (Timestamp) message.get("sendAt");
        holder.messageTV.setText(text);

        String time = new SimpleDateFormat("hh:mm a").format(timestamp.toDate());
        String date = new SimpleDateFormat("E dMMM, y").format(timestamp.toDate());

        if(timestamp != null)
            holder.dateTV.setText(time + "\n" + date);
        if(!isSupport){
            holder.cardView.setCardBackgroundColor(Color.LTGRAY);
            holder.linearLayout.setGravity(Gravity.END);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        CardView cardView;
        TextView messageTV, dateTV;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.messageBubbleLinearLayout);
            cardView = itemView.findViewById(R.id.messageBubble);
            messageTV = itemView.findViewById(R.id.messageBubbleMessage);
            dateTV = itemView.findViewById(R.id.messageBubbleDate);
        }
    }

}
