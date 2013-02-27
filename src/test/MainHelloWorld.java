package test;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

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
		initKeys();
		/** create a blue box at coordinates (1,-1,1) */
        Box box1 = new Box( Vector3f.ZERO, 1,1,1);
        Geometry blue = new Geometry("Box", box1);
        Material mat1 = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Blue);
        blue.setMaterial(mat1);
        blue.move(1,-1,1);
 
        /** create a red box straight above the blue one at (1,3,1) */
        Box box2 = new Box( Vector3f.ZERO, 1,1,1);
        Geometry red = new Geometry("Box", box2);
        Material mat2 = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        //mat2.setColor("Color", ColorRGBA.Red);
        mat2.setTexture("ColorMap", 
        	    assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));
        red.setMaterial(mat2);
        red.move(1,3,1);
 
        /** Create a pivot node at (0,0,0) and attach it to the root node */
        Node pivot = new Node("pivot");
        rootNode.attachChild(pivot); // put this node in the scene
 
        /** Attach the two boxes to the *pivot* node. */
        pivot.attachChild(blue);
        pivot.attachChild(red);
        /** Rotate the pivot node: Note that both boxes have rotated! */
        pivot.rotate(.4f,.4f,0f);
		
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
