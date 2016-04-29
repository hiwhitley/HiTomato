package com.hiwhitley.potatoandtomato.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.base.BaseFragment;
import com.hiwhitley.potatoandtomato.bean.Suggest;
import com.hiwhitley.potatoandtomato.utils.KeyBoardUtils;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by hiwhitley on 2016/4/13.
 */
public class SuggestFragment extends BaseFragment{

    private View mRootView;
    //防止多次提交数据
    private boolean isPosting = false;

    private Button sendBtn;
    private TextInputLayout mTiSuggest,mTiMailOrQq;

    public SuggestFragment(){

    }

    public static SuggestFragment newInstance(){
        SuggestFragment f = new SuggestFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_suggest, container, false);
            findViews();
            init();
            setListener();
        }
        //缓存的mRootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mRootView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    protected void findViews() {
        sendBtn = (Button) mRootView.findViewById(R.id.btn_send);
        mTiSuggest = (TextInputLayout) mRootView.findViewById(R.id.ti_suggest);
        mTiMailOrQq = (TextInputLayout) mRootView.findViewById(R.id.ti_email_or_qq);
    }

    @Override
    protected void init() {
    }

    @Override
    protected void setListener() {
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.closeKeybord(mTiSuggest.getEditText(), getContext());
                String msg = mTiSuggest.getEditText().getText().toString();
                if(TextUtils.isEmpty(msg)){
                    Snackbar.make(mRootView, R.string.dont_no_text, Snackbar.LENGTH_SHORT).show();
                }else{
                    Suggest suggest = new Suggest();
                    suggest.setMsg(msg);
                    suggest.setMailOrQQ(mTiMailOrQq.getEditText().getText().toString());
                    if(!isPosting) {
                        isPosting = true;
                        suggest.save(getActivity(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                isPosting = false;
                                mTiMailOrQq.getEditText().setText("");
                                mTiMailOrQq.getEditText().setFocusable(false);
                                mTiSuggest.getEditText().setText("");
                                mTiSuggest.getEditText().setFocusable(false);
                                Snackbar.make(mRootView, R.string.thx, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                isPosting = false;
                                Snackbar.make(mRootView, R.string.sugesst_error, Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Snackbar.make(mRootView, R.string.dont_repeat, Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
