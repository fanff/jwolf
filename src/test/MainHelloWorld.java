package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetLocator;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;

import de.lessvoid.nifty.tools.resourceloader.ResourceLocation;

public class MainHelloWorld extends SimpleApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		MainHelloWorld main = new MainHelloWorld();

		main.start();

	}
	JwLevel level = new JwLevel();

	@Override
	public void simpleInitApp() {
		assetManager.registerLocator("./assets", FileLocator.class);

		Material mat_brick1 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		String brick_color_filenae = "Textures/LightBrick/bricksmall.png";
		String brick_normal_filename = "Textures/LightBrick/bricksmall_normal.png";
		Texture texture = assetManager.loadTexture(brick_color_filenae);
		mat_brick1.setTexture("DiffuseMap",texture);
		mat_brick1.setTexture("NormalMap", assetManager.loadTexture(brick_normal_filename));
		mat_brick1.setBoolean("UseMaterialColors",true);    
		mat_brick1.setColor("Specular",ColorRGBA.White);
		mat_brick1.setColor("Diffuse",ColorRGBA.White);
		mat_brick1.setFloat("Shininess", 1f); // [1,128]    

		//opening the level file
		String levelFileName = "assets/Level/level";
		try {
			FileReader reader = new FileReader(new File(levelFileName));
			BufferedReader bufferedReader = new BufferedReader(reader);

			String line = null;
			int lineCount = 0;
			while((line = bufferedReader.readLine()) != null){
				System.out.println(""+line);
				for(int i = 0 ; i< line.length() ; i ++){
					String def = line.substring(i, i+1);
					level.addWall(def, lineCount, i);
				}

				lineCount++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/** create the ground */
		Box ground = new Box(512f,1f,512f);
		Geometry blue = new Geometry("Box", ground);
		Material mat_ground = new Material(assetManager, 
		"Common/MatDefs/Misc/Unshaded.j3md");
		mat_ground.setColor("Color", ColorRGBA.Blue);
		blue.setMaterial(mat_ground);
		blue.move(256f,-2,256f);
		rootNode.attachChild(blue);

		/** create a wall 
		Box box2 = new Box(1f,1f,1f);
		Geometry geom_box2 = new Geometry("Box", box2);
		geom_box2.setMaterial(mat_brick1);
		geom_box2.move(0,0,0);
		rootNode.attachChild(geom_box2);
		 */


		for (int x = 0; x < JwLevel.MAXX; x++) {
			for (int y = 0; y < JwLevel.MAXY; y++) {
				String walldef = level.getWall(x, y);
				if(walldef != null){
					if(walldef.equals("#")){
						createBox(x, y, mat_brick1);
					}else if(walldef.equals("l")){
						PointLight lamp = new PointLight();
						lamp.setPosition(new Vector3f(2f*x, 5f, 2f*x));
						lamp.setColor(ColorRGBA.LightGray);
						rootNode.addLight(lamp);
					}
				}
			}
		}
		//createBox(0, 0, mat_brick1);

		/**
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(0,-1,0).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        PointLight lamp = new PointLight();
						lamp.setPosition(new Vector3f(2f*x, 1f, 2f*x));
						lamp.setColor(ColorRGBA.White);
						rootNode.addLight(lamp);
		 */


	}

	int boxCounter = 0;

	private void createBox(int x,int y,Material boxMaterial){
		boxCounter++;
		Box box3 = new Box(1f,1f,1f);
		Geometry geom_box3 = new Geometry("Box"+boxCounter, box3);
		geom_box3.setMaterial(boxMaterial);
		geom_box3.move(2f*x,0,2f*y);
		rootNode.attachChild(geom_box3);
	}

	private void initKeys() {

		inputManager.clearMappings();
		// You can map one or several inputs to one named action
		inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
		inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_J));
		inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_K));
		inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
				new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		// Add the names to the action listener.
		//inputManager.addListener(actionListener, new String[]{"Pause"});
		//inputManager.addListener(analogListener, new String[]{"Left", "Right", "Rotate"});

	}

}
