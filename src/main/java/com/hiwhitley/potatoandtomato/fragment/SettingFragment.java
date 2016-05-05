package com.hiwhitley.potatoandtomato.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.hiwhitley.potatoandtomato.R;
import com.hiwhitley.potatoandtomato.base.BaseFragment;
import com.hiwhitley.potatoandtomato.base.system.Constants;
import com.hiwhitley.potatoandtomato.utils.SPUtils;
import com.hiwhitley.potatoandtomato.widget.SegmentControl;
import com.hiwhitley.potatoandtomato.widget.ShSwitchView;
import com.hiwhitley.potatoandtomato.widget.animation.MoveAnimation;

/**
 * Created by hiwhitley on 2016/4/7.
 */
public class SettingFragment extends BaseFragment {

    public static final String IS_SOUND_ON = "sound";
    public static final String IS_VIBRATE_ON = "vibrate";
    public static final String SOUND_REPEAT = "sound_repeat";
    public static final String SOUND_TYPE = "sound_type";

    private View mRootView;
    private ShSwitchView mSoundSwitch;
    private ShSwitchView mVibrateSwitch;

    private SegmentControl soundRepeat;
    private SegmentControl soundType;

    public SettingFragment() {

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.LEFT, enter, Constants.DURATION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_setting, container, false);
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


    protected void findViews() {
        mSoundSwitch = findView(mRootView, R.id.swv_sound);
        mVibrateSwitch = findView(mRootView, R.id.swv_vibrate);

        soundRepeat = findView(mRootView, R.id.sec_sound_repeat);
        soundType = findView(mRootView, R.id.sec_sound_type);
    }

    protected void init() {

        mSoundSwitch.setOn((Boolean) getSettingContent(IS_SOUND_ON, true));
        mVibrateSwitch.setOn((Boolean) getSettingContent(IS_VIBRATE_ON, true));

        soundRepeat.setSelectedIndex((Integer) getSettingContent(SOUND_REPEAT, 0));
        soundType.setSelectedIndex((Integer) getSettingContent(SOUND_TYPE, 0));
    }

    protected void setListener() {

        mSoundSwitch.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                SPUtils.put(getActivity(), IS_SOUND_ON, isOn);
            }
        });

        mVibrateSwitch.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                SPUtils.put(getActivity(), IS_VIBRATE_ON, isOn);
            }
        });

        soundRepeat.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                SPUtils.put(getActivity(), SOUND_REPEAT, index);
            }
        });

        soundType.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                SPUtils.put(getActivity(), SOUND_TYPE, index);
            }
        });
    }

    private <T extends Object> T getSettingContent(String key, Object defaultValue) {
        return (T) SPUtils.get(getActivity(), key, defaultValue);
    }

}
