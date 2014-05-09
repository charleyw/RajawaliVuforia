package hackday.jarvis;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import rajawali.util.RajLog;
import rajawali.vuforia.RajawaliVuforiaActivity;

public class Jarvis extends RajawaliVuforiaActivity {
	private JarvisRenderer mRenderer;

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
		
		createFrameMarker(0, "Marker1", 200, 200);
		createFrameMarker(1, "Marker2", 200, 200);
		createFrameMarker(2, "Marker3", 200, 200);
		createFrameMarker(3, "Marker4", 200, 200);
		createFrameMarker(4, "Marker5", 200, 200);
		createFrameMarker(5, "Marker6", 200, 200);
		createFrameMarker(6, "Marker7", 200, 200);
		createFrameMarker(7, "Marker8", 200, 200);
		createFrameMarker(8, "Marker9", 200, 200);
		createFrameMarker(9, "Marker10", 200, 200);

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
		
	}
}
