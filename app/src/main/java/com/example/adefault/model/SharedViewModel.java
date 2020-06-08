package com.example.adefault.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private String posting_id;

    public String getPosting_id() {
        return posting_id;
    }

    public void setPosting_id(String posting_id) {
        this.posting_id = posting_id;
    }

    private MutableLiveData<String> liveData = new MutableLiveData<>();

    public LiveData<String> getPostingId(){
        return liveData;
    }

    public void setPosingId(String posting_id){
        liveData.setValue(posting_id);
    }

//    private String posting_id;
//
//    public String getPostingId(){
//        return posting_id;
//    }
//
//    public void setPostingId(String posting_id){
//        this.posting_id = posting_id;
//    }
}
