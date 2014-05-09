package hackday.jarvis;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.textures.Texture;
import rajawali.math.Quaternion;
import rajawali.math.vector.Vector3;
import rajawali.parser.LoaderOBJ;
import rajawali.parser.ParsingException;
import rajawali.vuforia.RajawaliVuforiaRenderer;

public class JarvisRenderer extends RajawaliVuforiaRenderer {
	private DirectionalLight mLight;
	private Jarvis activity;
    private Map<Integer, Object3D> object3DMap= new HashMap<Integer, Object3D>();

    public JarvisRenderer(Context context) {
		super(context);
		activity = (Jarvis)context;
	}

	protected void initScene() {
		mLight = new DirectionalLight(.1f, 0, -1.0f);
		mLight.setColor(1.0f, 1.0f, 0.8f);
		mLight.setPower(1);
		
		getCurrentScene().addLight(mLight);
        mTextureManager.addTexture(new Texture("wool256.jpg", R.drawable.wool256));
        mTextureManager.addTexture(new Texture("pitch.jpg", R.drawable.pitch));
        mTextureManager.addTexture(new Texture("rojiza.jpg", R.drawable.rojiza));
//        mTextureManager.addTexture(new Texture("tipical.jpg", R.drawable.wool256));
//        mTextureManager.addTexture(new Texture("caja_falsa_datascopia.jpg", R.drawable.wool256));
//        mTextureManager.addTexture(new Texture("garageDoor2.jpg", R.drawable.wool256));

        loadModel(R.raw.sofa1_obj, 1f, 0);
        loadModel(R.raw.smallerrailing_obj, 50f, 1);
        loadModel(R.raw.piano_droit_obj, 5f, 2);
        loadModel(R.raw.table_football_obj, 2f, 3);
//        loadModel(R.raw.dark_table_and_chairs_obj, 100f, 3);
        loadModel(R.raw.saloon_table_obj, 1f, 4);
        loadModel(R.raw.basketball_post, 10f, 5);
	}

    private void loadModel(int resourceId, float scale, int markerId) {
        LoaderOBJ loaderOBJ = new LoaderOBJ(mContext.getResources(), mTextureManager, resourceId);
        try {
            Object3D object3D = loaderOBJ.parse().getParsedObject();
            object3D.setScale(scale);
            object3DMap.put(markerId, object3D);
            addChild(object3D);
        } catch (ParsingException e) {
            e.printStackTrace();
        }
    }

	@Override
	protected void foundFrameMarker(int markerId, Vector3 position,
			Quaternion orientation) {
        Object3D object3D = object3DMap.get(markerId);
        if(object3D != null){
            object3D.setVisible(true);
            object3D.setPosition(position);
            object3D.setOrientation(orientation);
        }
	}

	@Override
	protected void foundImageMarker(String trackableName, Vector3 position,
			Quaternion orientation) {
    }

	@Override
	public void noFrameMarkersFound() {
	}

	public void onDrawFrame(GL10 glUnused) {
        hideAllObject3Ds();

        super.onDrawFrame(glUnused);
	}

    private void hideAllObject3Ds() {
        for(Object3D object3D : object3DMap.values()){
            object3D.setVisible(false);
        }
    }
}
