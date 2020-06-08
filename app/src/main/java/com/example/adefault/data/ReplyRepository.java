package com.example.adefault.data;

import com.example.adefault.model.FollowFeedResponseDTO;
import com.example.adefault.model.ReplyDTO;
import com.example.adefault.model.ReplyResponseDTO;
import com.example.adefault.model.WriteReplyDTO;
import com.example.adefault.model.WriteReplyResponseDTO;
import com.example.adefault.util.FollowFeedReplyUtil;
import com.example.adefault.util.UserToken;

import retrofit2.Callback;

public class ReplyRepository implements ReplyRepositoryInterface {

    private FollowFeedReplyUtil mFollowFeedReplyUtil;
    private ReplyDTO replyDTO;
    private WriteReplyDTO writeReplyDTO;
    private String posting_id;


    public ReplyRepository() {
        mFollowFeedReplyUtil = new FollowFeedReplyUtil();
    }

    public ReplyRepository(ReplyDTO replyDTO){
        this();
        this.replyDTO = replyDTO;
    }

    public ReplyRepository(WriteReplyDTO writeReplyDTO, String posting_id){
        this();
        this.writeReplyDTO = writeReplyDTO;
        this.posting_id = posting_id;
    }

//    @Override
    public boolean replyIsAvailable() {
        System.out.println("in repository " + replyDTO.getPosting_id());

        if(replyDTO.getPosting_id() == null) return false;
        else return true;
    }

    public boolean updateIsAvailable(){
        if(writeReplyDTO.getContext() == null) return false;
        else return true;
    }


    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public void getReply(Callback<ReplyResponseDTO> callback) {
        System.out.println("gerReply " + replyDTO.getPosting_id());
        mFollowFeedReplyUtil.getAPI().getReplyData(replyDTO,"Token "+ UserToken.getToken())
                .enqueue(callback);
    }

    @Override
    public void sendReply(Callback<WriteReplyResponseDTO> callback) {
        mFollowFeedReplyUtil.getAPI().postReply(writeReplyDTO, posting_id,"Token "+UserToken.getToken())
                .enqueue(callback);

    }

}
