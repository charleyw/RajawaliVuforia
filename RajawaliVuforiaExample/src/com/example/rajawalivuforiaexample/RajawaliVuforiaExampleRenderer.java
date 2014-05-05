package com.example.rajawalivuforiaexample;

import android.content.Context;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.textures.Texture;
import rajawali.math.Quaternion;
import rajawali.math.vector.Vector3;
import rajawali.parser.LoaderOBJ;
import rajawali.parser.ParsingException;
import rajawali.vuforia.RajawaliVuforiaRenderer;

public class RajawaliVuforiaExampleRenderer extends RajawaliVuforiaRenderer {
	private DirectionalLight mLight;
	private RajawaliVuforiaExampleActivity activity;
    private Object3D sofa;

    public RajawaliVuforiaExampleRenderer(Context context) {
		super(context);
		activity = (RajawaliVuforiaExampleActivity)context;
	}

	protected void initScene() {
		mLight = new DirectionalLight(.1f, 0, -1.0f);
		mLight.setColor(1.0f, 1.0f, 0.8f);
		mLight.setPower(1);
		
		getCurrentScene().addLight(mLight);
		
		try {
            mTextureManager.addTexture(new Texture("wool256.jpg", R.drawable.wool256));
            LoaderOBJ loaderOBJ = new LoaderOBJ(mContext.getResources(), mTextureManager, R.raw.sofa1_obj);

            try {
                sofa = loaderOBJ.parse().getParsedObject();
                sofa.setScale(50f);
                sofa.setRotY(90);
                sofa.setY(-1);

                addChild(sofa);
            } catch (ParsingException e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
			e.printStackTrace();
		}
	}

    public void changeScale(int scale){
        sofa.setScale(scale);
    }

	@Override
	protected void foundFrameMarker(int markerId, Vector3 position,
			Quaternion orientation) {
		
		if (markerId == 0) {
			sofa.setVisible(true);
            position.add(116.768383932224,-36.1243398942542,0);
            sofa.setPosition(position);
            sofa.setOrientation(orientation);
		}
	}

	@Override
	protected void foundImageMarker(String trackableName, Vector3 position,
			Quaternion orientation) {
        sofa.setVisible(true);
        position.add(116.768383932224,-36.1243398942542,0);
        sofa.setPosition(position);
        sofa.setOrientation(orientation);
    }

	@Override
	public void noFrameMarkersFound() {
	}

	public void onDrawFrame(GL10 glUnused) {
		sofa.setVisible(false);
        super.onDrawFrame(glUnused);
		
//		if (!activity.getScanningModeNative())
//		{
//			activity.showStartScanButton();
//		}
	}
}
