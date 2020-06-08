package com.example.adefault.Reply;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adefault.FeedPost.FollowFeedPresenter;
import com.example.adefault.FeedPost.FollowFeedRecyclerViewAdapter;
import com.example.adefault.FeedPost.TabAdapter;
import com.example.adefault.R;
import com.example.adefault.data.FollowFeedRepository;
import com.example.adefault.data.ReplyRepository;
import com.example.adefault.model.FollowFeedResponseDTO;
import com.example.adefault.model.FollowFeedReview_data;
import com.example.adefault.model.ReplyDTO;
import com.example.adefault.model.ReplyResponseDTO;
import com.example.adefault.model.ReplyReview_data;
import com.example.adefault.model.SharedViewModel;
import com.example.adefault.model.WriteReplyDTO;
import com.example.adefault.model.WriteReplyResponseDTO;

import java.util.ArrayList;

public class ReplyFragment extends Fragment implements ReplyContract.View {
    private TextView text_post_reply_user_name;
    private ImageView image_post_reply_userPic;
    private TextView text_post_reply_context;
    private TextView text_post_reply_date;

    private EditText edit_post_reply_input;
    private ImageView image_post_reply;
    private TextView text_post_reply;

    private String inputText;

    private String mPosting_id;
    private RecyclerView mRecyclerView;

    private ReplyRepository mRepository;
    private ReplyPresenter mPresenter;
    private ReplyRecyclerViewAdapter mAdapter;

    private ReplyDTO replyDTO = new ReplyDTO();
    private ReplyResponseDTO replyResponseDTO;
//    private ArrayList<ReplyResponseDTO> replyResponseDTOList = new ArrayList<>();

    private WriteReplyDTO writeReplyDTO = new WriteReplyDTO();
    private WriteReplyResponseDTO writeReplyResponseDTO;

    private SharedViewModel sharedViewModel;
    private String posting_id;
    private String index;//.............


    public ReplyFragment() { }

    public static ReplyFragment newInstance(String posting_id) {
        ReplyFragment fragment = new ReplyFragment();
        Bundle args = new Bundle();

        args.putString("posting_id", posting_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mPosting_id = getArguments().getString("posting_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_post_reply, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_post_reply);

        edit_post_reply_input = view.findViewById(R.id.edit_post_reply_input);
        image_post_reply = view.findViewById(R.id.image_post_reply);


        System.out.println("111111");

        LinearLayoutManager replyLinearLayoutManager = new LinearLayoutManager(getActivity());
        replyLinearLayoutManager.setReverseLayout(true);
        replyLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(replyLinearLayoutManager);

        System.out.println("2222222");
        mAdapter = new ReplyRecyclerViewAdapter(getActivity(), getmReplyResponseDTO());
        mRecyclerView.setAdapter(mAdapter);
        System.out.println("3333333");


        image_post_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText = edit_post_reply_input.getText().toString();

                System.out.println("input " + inputText);
                if (inputText != null) {
                    writeReplyDTO.setContext(inputText);

                    mRepository = new ReplyRepository(writeReplyDTO, posting_id);
                    System.out.println("reply mRepository.isAvailable() " + mRepository.isAvailable());

                    mPresenter = new ReplyPresenter(mRepository, newInstance(mPosting_id));
                    mPresenter.uploadReply(writeReplyDTO);

                    getmReplyResponseDTO();

                }
            }
        });
                // Inflate the layout for this fragment
        return view;
    }


//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
////        posting_id = ReplyFragmentargs.fromBundle(getArguments()).getPostingId();
////        System.out.println("replyFragment posting_id " + posting_id);
//    }


    public ReplyResponseDTO getmReplyResponseDTO() {

        System.out.println("1");

        replyDTO.setPosting_id(mPosting_id);//posting_id);
        System.out.println("2 " + replyDTO.getPosting_id());

        mRepository = new ReplyRepository(replyDTO);
        System.out.println("mRepository.isAvailable() " + mRepository.isAvailable());

        mPresenter = new ReplyPresenter(mRepository, this);
        replyResponseDTO = mPresenter.callReply(replyDTO); //posing_id 어케 넘기지;;;;
//        writeReplyResponseDTO = mPresenter.uploadReply(writeReplyDTO);

        System.out.println("3");
        return replyResponseDTO;
    }


    public WriteReplyResponseDTO getWriteReplyResponseDTO() {
        writeReplyDTO = new WriteReplyDTO(inputText);
        mRepository = new ReplyRepository(writeReplyDTO, index);//, posting_id);
        return writeReplyResponseDTO;
    }


    @Override
    public void showReply(ReplyResponseDTO replyResponseDTO) {

//        ArrayList<FollowFeedReview_data> followFeedReview_dataList = replyResponseDTO.getFollowFeedReview_data();

        System.out.println("showReply");
//        System.out.println(replyResponseDTO.getNickname());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        System.out.println("너는 왜 실행 안 돼 ");
        mAdapter = new ReplyRecyclerViewAdapter(getActivity(), replyResponseDTO);
        System.out.println("너는 왜 실행 안 돼 2");
        mRecyclerView.setAdapter(mAdapter);
        System.out.println("너는 왜 실행 안 돼3 ");
    }

    @Override
    public void addReply(WriteReplyResponseDTO writeReplyResponseDTO) {

//        mRecyclerView.setLayoutManager(new LinearLayoutManager((getActivity())));

        if(writeReplyResponseDTO != null) {
            mAdapter = new ReplyRecyclerViewAdapter(getActivity(), writeReplyResponseDTO);
//            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

//        mAdapter = new ReplyRecyclerViewAdapter(getActivity(), writeReplyResponseDTO);
//        mRecyclerView.setAdapter(mAdapter);

    }

}
