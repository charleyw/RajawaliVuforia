package hackday.jarvis;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import rajawali.util.RajLog;
import rajawali.vuforia.RajawaliVuforiaActivity;

public class Jarvis extends RajawaliVuforiaActivity {
	private JarvisRenderer mRenderer;
	private RajawaliVuforiaActivity mUILayout;
    private Button mStartScanButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
		
		createFrameMarker(0, "Marker1", 50, 50);
		createFrameMarker(1, "Marker2", 50, 50);
		
		createImageMarker("firsttest.xml");
		createImageMarker("StonesAndChips.xml");

		// -- this is how you add a cylinder target:
		//    https://developer.vuforia.com/resources/dev-guide/cylinder-targets
		// createImageMarker("MyCylinderTarget.xml");
		
		// -- this is how you add a multi-target:
		//    https://developer.vuforia.com/resources/dev-guide/multi-targets
		// createImageMarker("MyMultiTarget.xml");
	}

    @Override
	protected void initRajawali() {
		super.initRajawali();
		mRenderer = new JarvisRenderer(this);
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

        mUILayout = this;
        mUILayout.addContentView(mStartScanButton,
            new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}    
}
