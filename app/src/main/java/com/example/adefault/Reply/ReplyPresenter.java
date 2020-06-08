package com.example.adefault.Reply;

import android.util.Log;

import com.example.adefault.data.ReplyRepository;
import com.example.adefault.model.ReplyDTO;
import com.example.adefault.model.ReplyResponseDTO;
import com.example.adefault.model.WriteReplyDTO;
import com.example.adefault.model.WriteReplyResponseDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReplyPresenter implements ReplyContract.UserActionListener {

    private ReplyRepository replyRepository;
    private ReplyContract.View mView;

    private ReplyResponseDTO replyResponseDTO = new ReplyResponseDTO();
    private WriteReplyResponseDTO writeReplyResponseDTO = new WriteReplyResponseDTO();
    private String index;

    public ReplyPresenter(ReplyRepository mRepository, ReplyContract.View mView) {
        this.replyRepository = mRepository;
        this.mView = mView;
    }

        @Override
        public ReplyResponseDTO callReply(ReplyDTO replyDTO) {
            replyRepository = new ReplyRepository(replyDTO);

            replyRepository.getReply(new Callback<ReplyResponseDTO>() {
                @Override
                public void onResponse(Call<ReplyResponseDTO> call, Response<ReplyResponseDTO> response) {
                    if(response.isSuccessful()) {
                        try {
                            Log.d("response", "success");
                            System.out.println("next?? " + response.body().getContext());
                            replyResponseDTO = response.body();
                            System.out.println("next + " + replyResponseDTO.getContext());
                            System.out.println("1 + " + replyResponseDTO.getReview_data().get(0).getContext());
                            System.out.println("2 + " + replyResponseDTO.getContext());
                            mView.showReply(response.body());
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else Log.d("response", "fail");

                }
                @Override
                public void onFailure(Call<ReplyResponseDTO> call, Throwable t) {
                    Log.d("fail", "fail");
                    t.printStackTrace();
                }
            });
            System.out.println("whhh " + replyResponseDTO.getContext());
            return replyResponseDTO;
        }


    @Override
    public WriteReplyResponseDTO uploadReply(WriteReplyDTO writeReplyDTO) {

        replyRepository = new ReplyRepository(writeReplyDTO, index);
        replyRepository.sendReply(new Callback<WriteReplyResponseDTO>() {
            @Override
            public void onResponse(Call<WriteReplyResponseDTO> call, Response<WriteReplyResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("success", "plzz");
                } else Log.d("noooooo", "nooooo");
                mView.addReply(response.body());
//                writeReplyResponseDTO = response.body();
            }

            @Override
            public void onFailure(Call<WriteReplyResponseDTO> call, Throwable t) {
                Log.d("fail", "failTT");
                t.printStackTrace();
            }
        });
        return writeReplyResponseDTO;
    }
}
