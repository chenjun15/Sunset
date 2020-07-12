package com.cj.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SunsetFragment extends Fragment {

    private View mSceneView;
    private View mSunView;
    private View mShadowView;
    private View mSkyView;
    private View mSeaView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);

        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mShadowView = view.findViewById(R.id.shadow);
        mSkyView = view.findViewById(R.id.sky);
        mSeaView = view.findViewById(R.id.sea);

        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);

        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int skyColor = ((ColorDrawable) mSkyView.getBackground()).getColor();
                if (skyColor == mBlueSkyColor) {
                    startAnimation();
                } else if (skyColor == mNightSkyColor) {
                    startRevertAnimation();
                }
            }
        });

        return view;
    }

    private void startAnimation() {
        float sunYStart = mSunView.getTop(), sunYEnd = mSkyView.getHeight() + (float) Math.ceil(mSunView.getHeight() * .1f);
        float shadowYstart = mShadowView.getTop(), shadowYEnd = -(float) Math.ceil(mShadowView.getHeight() * 1.1f);

        ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(mSunView, "y", sunYStart, sunYEnd).setDuration(3200);
        heightAnimator.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(mSunView, "scaleX", 1.f, 1.1f).setDuration(400);
        scaleXAnimator.setRepeatCount(8);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(mSunView, "scaleY", 1.f, 1.1f).setDuration(400);
        scaleYAnimator.setRepeatCount(8);

        ObjectAnimator shadowHeightAnimator = ObjectAnimator.ofFloat(mShadowView, "y", shadowYstart, shadowYEnd).setDuration(3200);
        shadowHeightAnimator.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator shadowScaleXAnimator = ObjectAnimator.ofFloat(mShadowView, "scaleX", 1.f, 1.1f).setDuration(400);
        shadowScaleXAnimator.setRepeatCount(8);
        ObjectAnimator shadowScaleYAnimator = ObjectAnimator.ofFloat(mShadowView, "scaleY", 1.f, 1.1f).setDuration(400);
        shadowScaleYAnimator.setRepeatCount(8);

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor).setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor).setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(heightAnimator)
                .with(scaleXAnimator)
                .with(scaleYAnimator)
                .with(shadowHeightAnimator)
                .with(shadowScaleXAnimator)
                .with(shadowScaleYAnimator)
                .with(sunsetSkyAnimator)
                .before(nightSkyAnimator);
        animatorSet.start();
    }

    private void startRevertAnimation() {
        float sunYStart = mSunView.getTop(), sunYEnd = mSkyView.getHeight() + (float) Math.ceil(mSunView.getHeight() * .1f);
        float shadowYstart = mShadowView.getTop(), shadowYEnd = -(float) Math.ceil(mShadowView.getHeight() * 1.1f);

        ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(mSunView, "y", sunYEnd, sunYStart).setDuration(3200);
        heightAnimator.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(mSunView, "scaleX", 1.f, 1.1f).setDuration(400);
        scaleXAnimator.setRepeatCount(8);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(mSunView, "scaleY", 1.f, 1.1f).setDuration(400);
        scaleYAnimator.setRepeatCount(8);

        ObjectAnimator shadowHeightAnimator = ObjectAnimator.ofFloat(mShadowView, "y", shadowYEnd, shadowYstart).setDuration(3200);
        shadowHeightAnimator.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator shadowScaleXAnimator = ObjectAnimator.ofFloat(mShadowView, "scaleX", 1.f, 1.1f).setDuration(400);
        shadowScaleXAnimator.setRepeatCount(8);
        ObjectAnimator shadowScaleYAnimator = ObjectAnimator.ofFloat(mShadowView, "scaleY", 1.f, 1.1f).setDuration(400);
        shadowScaleYAnimator.setRepeatCount(8);

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mBlueSkyColor).setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor).setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(heightAnimator)
                .with(scaleXAnimator)
                .with(scaleYAnimator)
                .with(shadowHeightAnimator)
                .with(shadowScaleXAnimator)
                .with(shadowScaleYAnimator)
                .with(sunsetSkyAnimator)
                .after(nightSkyAnimator);
        animatorSet.start();
    }
}
