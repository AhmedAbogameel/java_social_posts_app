package com.example.socialpostsapp.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.socialpostsapp.R;
import com.example.socialpostsapp.pojo.PostModel;
import com.example.socialpostsapp.sql.SQLHelper;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private List<PostModel> list = new ArrayList<>();
    final private PostsViewModel postsViewModel;

    PostsAdapter(PostsViewModel postsViewModel) {
        this.postsViewModel = postsViewModel;
    }

    public void setList(List<PostModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card, parent, false);
        PostsViewHolder PostsViewHolder = new PostsViewHolder(view);
        return PostsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        final PostModel postModel = list.get(position);
        holder.titleTV.setText(postModel.getTitle());
        holder.userTV.setText(String.valueOf(postModel.getId()));
        holder.bodyTV.setText(postModel.getBody());

        if(postsViewModel.isOffline){
            holder.editButton.setVisibility(View.GONE);
        }

        if(SQLHelper.isFav(postModel.getId())){
            holder.saveButton.setText(R.string.remove);
            holder.saveButton.setBackgroundColor(holder.cardView.getResources().getColor(R.color.red));
        }else{
            holder.saveButton.setText(R.string.save);
            holder.saveButton.setBackgroundColor(holder.cardView.getResources().getColor(R.color.teal_700));
        }

        holder.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SQLHelper.isFav(postModel.getId())){
                    SQLHelper.removeFav(postModel.getId());
                }else{
                    SQLHelper.addFav(postModel);
                }

                if(postsViewModel.isOffline) {
                    notifyItemRemoved(position);
                } else {
                    notifyItemChanged(position);
                }

            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPostDialog.showDialog(view.getContext(), postsViewModel, list.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView titleTV, userTV, bodyTV;
        Button saveButton, editButton;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.titleTV);
            userTV = itemView.findViewById(R.id.userIDTV);
            bodyTV = itemView.findViewById(R.id.bodyTV);

            cardView = itemView.findViewById(R.id.cardView);

            saveButton = itemView.findViewById(R.id.saveButton);
            editButton = itemView.findViewById(R.id.editButton);
//            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

}
