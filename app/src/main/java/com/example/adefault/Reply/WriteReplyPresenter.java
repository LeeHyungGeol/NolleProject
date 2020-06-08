//package com.example.adefault.Reply;
//
//import android.util.Log;
//
//import com.example.adefault.data.ReplyRepository;
//import com.example.adefault.model.FollowFeedResponseDTO;
//import com.example.adefault.model.WriteReplyDTO;
//import com.example.adefault.model.WriteReplyResponseDTO;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class WriteReplyPresenter implements WriteReplyContract.UserActionListener {
//
//    private ReplyRepository mRepository;
//    private ReplyContract.View mView;
//
//    @Override
//    public void callReply() {
//
//        final WriteReplyResponseDTO[] writeReplyResponseDTO = {new WriteReplyResponseDTO()};
//
//        mRepository = new
//        mRepository.sendReply(new Callback<WriteReplyResponseDTO>() {
//            @Override
//            public void onResponse(Call<WriteReplyResponseDTO> call, Response<WriteReplyResponseDTO> response) {
//                if (response.isSuccessful()) Log.d("response", "success");
//                else Log.d("response", "fail");
//
//                writeReplyResponseDTO[0] = response.body();
//                System.out.println("Dto " + writeReplyResponseDTO[0]);
//                mView.showReply(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<WriteReplyResponseDTO> call, Throwable t) {
//                Log.d("fail", "fail");
//            }
//        });
//
//    }
//}
//
//
//    public FollowFeedResponseDTO callPostData() {
//        final FollowFeedResponseDTO[] mFollowReedResponseDTO = {new FollowFeedResponseDTO()};
////        if (mRepository.isAvailable()) {
////            mRepository.getPostData(callback);
//        mFollowFeedRepository.getPostData(new Callback<FollowFeedResponseDTO>() {
//            @Override
//            public void onResponse(Call<FollowFeedResponseDTO> call, Response<FollowFeedResponseDTO> response) {
//                if (response.isSuccessful()) Log.d("response", "success");
//                else Log.d("response", "fail");
//
//                mFollowReedResponseDTO[0] = response.body();
//                System.out.println("Dto " + mFollowReedResponseDTO[0].getFollowFeedReview_data().size());
//                mView.showPostData(mFollowReedResponseDTO[0]);
//            }
//
//            @Override
//            public void onFailure(Call<FollowFeedResponseDTO> call, Throwable t) {
//                Log.d("fail", "fail");
//            }
//        });
//        return mFollowReedResponseDTO[0];
//
//    }