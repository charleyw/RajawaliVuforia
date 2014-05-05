package com.example.rajawalivuforiaexample;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.SeekBar;

import rajawali.util.RajLog;
import rajawali.vuforia.RajawaliVuforiaActivity;

public class RajawaliVuforiaExampleActivity extends RajawaliVuforiaActivity {
	private RajawaliVuforiaExampleRenderer mRenderer;
	private RajawaliVuforiaActivity mUILayout;
    private Button mStartScanButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		useCloudRecognition(true);
		setCloudRecoDatabase("a75960aa97c3b72a76eb997f9e40d210d5e40bf2", "aac883379f691a2550e80767ccd445ffbaa520ca");
		startVuforia();
	}

	@Override
	protected void setupTracker() {
		int result = initTracker(TRACKER_TYPE_MARKER);
		if (result == 1) {
			result = initTracker(TRACKER_TYPE_IMAGE);
			if (result == 1) {
				super.setupTracker();
			} else {
				RajLog.e("Couldn't initialize image tracker.");
			}
		} else {
			RajLog.e("Couldn't initialize marker tracker.");
		}
	}
	
	@Override
	protected void initApplicationAR()
	{
		super.initApplicationAR();
		
		createFrameMarker(1, "Marker1", 50, 50);
		createFrameMarker(2, "Marker2", 50, 50);
		
		createImageMarker("firsttest.xml");
		createImageMarker("StonesAndChips.xml");

		// -- this is how you add a cylinder target:
		//    https://developer.vuforia.com/resources/dev-guide/cylinder-targets
		// createImageMarker("MyCylinderTarget.xml");
		
		// -- this is how you add a multi-target:
		//    https://developer.vuforia.com/resources/dev-guide/multi-targets
		// createImageMarker("MyMultiTarget.xml");
	}
	
    public void showStartScanButton()
    {
        this.runOnUiThread(new Runnable() {
                public void run() {
                    if  (mStartScanButton != null)
                        mStartScanButton.setVisibility(View.VISIBLE);
                 }
         });
    }

	@Override
	protected void initRajawali() {
		super.initRajawali();
		mRenderer = new RajawaliVuforiaExampleRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		
	    //Add button for Cloud Reco:
        mStartScanButton = new Button(this);
        mStartScanButton.setText("Start Scanning CloudReco");
         
        mStartScanButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    enterScanningModeNative();
                     mStartScanButton.setVisibility(View.GONE);
                 }
        });

        SeekBar seekBar = new SeekBar(this);

        seekBar.setMax(10);
        seekBar.setProgress(1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mRenderer.changeScale(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mUILayout = this;
        mUILayout.addContentView(seekBar,
            new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}    
}
