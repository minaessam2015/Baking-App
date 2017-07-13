package com.example.android.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.SharedData.SharedRecipes;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mina essam on 17-Jun-17.
 */

public class StepDetailsFragment extends Fragment {
    private boolean isTwoPane=false;
    private int recipePosition;
    private int stepPosition;
    private Context context;
    private OnImageListener listener;
    private SimpleExoPlayer exoPlayer;
    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;
    @BindView(R.id.step_description)
    TextView stepDesc;
    @BindView(R.id.next_step)
    ImageView nextStepImage;
    @BindView(R.id.prev_step)
    ImageView prevStep;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.step_details_fragment,container,false);
        ButterKnife.bind(this,view);
        ///* ButterKnife isn't working

        playerView =(SimpleExoPlayerView) view.findViewById(R.id.playerView);

        //*/

        Bundle bundle=getArguments();
        if(bundle!=null) {
            isTwoPane = bundle.getBoolean("isTwoPane");
            recipePosition = bundle.getInt("recipePosition");
            stepPosition = bundle.getInt("stepPosition");
            Log.d("StepDetailsFragment",stepPosition+"   is step position");
        }else {
            Log.d("StepDetailsFragment","Null bundle");
            isTwoPane=false;
            recipePosition=2;
            stepPosition=2;
        }
        if(view.findViewById(R.id.land_playerView)==null){
            
            stepDesc=(TextView)view.findViewById(R.id.step_description);
            nextStepImage=(ImageView)view.findViewById(R.id.next_step);
            prevStep=(ImageView)view.findViewById(R.id.prev_step);

            stepDesc.setText(SharedRecipes.sharedRecipes.get(recipePosition).getStepAt(stepPosition).getDescription());
            if(isTwoPane){
                //tablet
                nextStepImage.setVisibility(View.GONE);
                prevStep.setVisibility(View.GONE);
            }
            nextStepImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onImageClickListener(stepPosition+1,v);
                }
            });

            prevStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onImageClickListener(stepPosition-1,v);
                }
            });
        }




        initializePlayer();


        return view;
    }

    void initializePlayer(){
        TrackSelector selector=new DefaultTrackSelector();
        LoadControl loadControl=new DefaultLoadControl();
        exoPlayer= ExoPlayerFactory.newSimpleInstance(context,selector);
        playerView.setPlayer(exoPlayer);
        //prepare

        String userAgent=Util.getUserAgent(context,"BakingApp");
        DataSource.Factory dataSource =new DefaultDataSourceFactory(context,userAgent);
        ExtractorsFactory extractorsFactory=new DefaultExtractorsFactory();
        Uri uri=Uri.parse(SharedRecipes.sharedRecipes.get(recipePosition).getStepAt(stepPosition).getVideoUrl());
        MediaSource mediaSource= new ExtractorMediaSource(uri,
                dataSource,extractorsFactory,null,null);
        exoPlayer.prepare(mediaSource);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener=(OnImageListener) context;
            this.context=context;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    interface OnImageListener{
        void onImageClickListener(int position,View view);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
    void releasePlayer(){
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer=null;
    }
}
