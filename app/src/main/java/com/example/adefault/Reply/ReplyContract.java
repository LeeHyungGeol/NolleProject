package com.example.adefault.Reply;

import com.example.adefault.model.ReplyDTO;
import com.example.adefault.model.ReplyResponseDTO;
import com.example.adefault.model.WriteReplyDTO;
import com.example.adefault.model.WriteReplyResponseDTO;

import java.util.ArrayList;

public interface ReplyContract {// View와 Presenter를 각각 저의하여 이해를 돕기 위해 사용
    public interface View {
//        void showPost();
        void showReply(ReplyResponseDTO replyResponseDTO);
        void addReply(WriteReplyResponseDTO writeReplyResponseDTO);
    }

    public interface UserActionListener {
//        void clickReplyBtn();
        ReplyResponseDTO callReply(ReplyDTO replyDTO);
        WriteReplyResponseDTO uploadReply(WriteReplyDTO writeReplyDTO);

    }
//    public interface Presenter {
//        void callReply(ReplyDTO replyDTO);
//        void uploadNewReply(ReplyDTO replyDTO);
//    }
}
