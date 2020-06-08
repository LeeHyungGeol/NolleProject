package com.example.adefault.Reply;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adefault.R;
import com.example.adefault.model.ReplyResponseDTO;
import com.example.adefault.model.ReplyReview_data;
import com.example.adefault.model.WriteReplyResponseDTO;

import java.util.ArrayList;

import static com.example.adefault.util.FollowFeedAPI.BASE_URL;

//adapter.notifyDataSetChanged();

class ReplyRecyclerViewAdapter
        extends RecyclerView.Adapter<ReplyRecyclerViewAdapter.ReplyRecyclerViewHolder> {

    private Context context;
    private ReplyResponseDTO replyResponseDTO = new ReplyResponseDTO();
    private ArrayList<ReplyResponseDTO> replyResponseDTOList = new ArrayList<ReplyResponseDTO>();
    private WriteReplyResponseDTO writeReplyResponseDTO;

    public ReplyRecyclerViewAdapter() {}

    public ReplyRecyclerViewAdapter(Context context, ReplyResponseDTO replyResponseDTO) {
        this.context = context;
        this.replyResponseDTO = replyResponseDTO;
    }

    public ReplyRecyclerViewAdapter(Context context, WriteReplyResponseDTO writeReplyResponseDTO){
        this.context = context;
        this.writeReplyResponseDTO = writeReplyResponseDTO;
        replyResponseDTO.setContext(writeReplyResponseDTO.getContext());
        replyResponseDTO.setDate(writeReplyResponseDTO.getDate());
        replyResponseDTO.setNickname(writeReplyResponseDTO.getNickname());
    }


    @NonNull
    @Override
    public ReplyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_reply, viewGroup, false);
        ReplyRecyclerViewAdapter.ReplyRecyclerViewHolder viewHolder = new ReplyRecyclerViewAdapter.ReplyRecyclerViewHolder(view);

        context = viewGroup.getContext();
        System.out.println("ADAPTER VIEWHOLDER");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyRecyclerViewHolder viewHolder, int position) {

        System.out.println("onBindVeiw");
       try {
            System.out.println("happy " + replyResponseDTO.getReview_data().get(position).getNickname());
            ReplyReview_data replyReview_data = replyResponseDTO.getReview_data().get(position);

            System.out.println("mDTO ~~~ " + replyReview_data.getNickname());
            viewHolder.text_post_reply_user_name.setText(replyReview_data.getNickname());
            Glide.with(context).load(BASE_URL + replyReview_data.getImage().substring(1)).into(viewHolder.image_post_reply_userPic);
            viewHolder.text_post_reply.setText(replyReview_data.getContext());
            viewHolder.text_post_date.setText(String.valueOf(replyReview_data.getDate()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return replyResponseDTO.getReview_data() == null ? 0 : replyResponseDTO.getReview_data().size();
    }

    public class ReplyRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView text_post_reply_user_name;
        private ImageView image_post_reply_userPic;
        private TextView text_post_date;
        private TextView text_post_reply;

        private ImageView imageReply;
        private EditText editReply;
        private String reply;

        public ReplyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            text_post_reply_user_name = (TextView) itemView.findViewById(R.id.text_post_reply_user_name);
            image_post_reply_userPic = (ImageView) itemView.findViewById(R.id.image_post_reply_userPic);
            text_post_date = (TextView) itemView.findViewById(R.id.text_post_date);
            text_post_reply = (TextView) itemView.findViewById(R.id.text_post_reply);

            imageReply = (ImageView) itemView.findViewById(R.id.image_post_reply);
            editReply = (EditText) itemView.findViewById(R.id.edit_post_reply_input);

        }
    }

}
