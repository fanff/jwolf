package test;

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

	@Override
	public void simpleInitApp() {
		/** create a blue box at coordinates (1,-1,1) */
        Box box1 = new Box( Vector3f.ZERO, 1,1,1);
        Geometry blue = new Geometry("Box", box1);
        Material mat1 = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Blue);
        blue.setMaterial(mat1);
        blue.move(1,-1,1);
 
        /** create a red box straight above the blue one at (1,3,1) */
        Box box2 = new Box(1f,1f,1f);
        Geometry red = new Geometry("Box", box2);
        
        Material mat_lit = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        
        assetManager.registerLocator("./assets", FileLocator.class);
        
        
        String mywallFilename = "Textures/LightBrick/LightBrick-ColorMap.png";
        Texture texture = assetManager.loadTexture(mywallFilename);
        mat_lit.setTexture("DiffuseMap",texture);

        mat_lit.setTexture("NormalMap", assetManager.loadTexture("Textures/LightBrick/LightBrick-NormalMap.png"));
        mat_lit.setBoolean("UseMaterialColors",true);    
        mat_lit.setColor("Specular",ColorRGBA.White);
        mat_lit.setColor("Diffuse",ColorRGBA.White);
        mat_lit.setFloat("Shininess", 5f); // [1,128]    
        
        
        red.setMaterial(mat_lit);
        red.move(1,3,1);
        rootNode.attachChild(red);
 
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1,0,-2).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
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
