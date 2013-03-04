package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.CartoonEdgeFilter;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.scene.control.LightControl;
import com.jme3.scene.shape.Box;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.MagFilter;
import com.jme3.texture.Texture.MinFilter;
import com.jme3.texture.Texture.WrapMode;

public class MainHelloWorld extends SimpleApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		MainHelloWorld main = new MainHelloWorld();

		main.start();

	}
	JwLevel level = new JwLevel();
	
	PointLight lamp=null;

	
	@Override
	public void simpleInitApp() {
		flyCam.setMoveSpeed(10f);
		showSettings = false;
		assetManager.registerLocator("./assets", FileLocator.class);

		Texture texture_blueCartoon = assetManager.loadTexture("Textures/bluecartoon.bmp");
		texture_blueCartoon.setWrap(WrapMode.Repeat);
		
		Texture texture_blueCartoonHeight = assetManager.loadTexture("Textures/bluecartoon_height.bmp");
		Texture texture_Blue17 = assetManager.loadTexture("Textures/WallPack/Blue17.bmp");
		Texture texture_Grey13 = assetManager.loadTexture("Textures/WallPack/Grey13.bmp");
		texture_Grey13.setMagFilter(MagFilter.Nearest);
		texture_Grey13.setMinFilter(MinFilter.NearestNoMipMaps);
		Texture bluecartoon_alpha = assetManager.loadTexture("Textures/bluecartoon_alpha.bmp");
		
		Texture texture_SP1 = assetManager.loadTexture("Textures/WallPack/Sp1.bmp");
		texture_SP1.setMagFilter(MagFilter.Nearest);
		texture_SP1.setMinFilter(MinFilter.NearestNoMipMaps);

		Texture tex_bricksmall = assetManager.loadTexture("Textures/bricksmall.png");
		tex_bricksmall.setMagFilter(MagFilter.Nearest);
		tex_bricksmall.setMinFilter(MinFilter.NearestNoMipMaps);
		
		
		Texture tex_normal = assetManager.loadTexture("Textures/bricksmall_normal.png");
		tex_normal.setMagFilter(MagFilter.Nearest);
		tex_normal.setMinFilter(MinFilter.NearestNoMipMaps);
		
		
		
		
		AbstractHeightMap heightmap = null;
	    heightmap = new ImageBasedHeightMap(texture_blueCartoonHeight.getImage());
	    heightmap.load();
	    
	    
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
		
		//
		//load the level
		//
		for (int x = 0; x < JwLevel.MAXX; x++) {
			for (int y = 0; y < JwLevel.MAXY; y++) {
				String walldef = level.getWall(x, y);
				if(walldef != null){
					if(walldef.equals("#")){
						//box
						//createUnnshadedBox(x, 0,y, texture_SP1);
						
						createBoxShadedBox(x, 0, y, tex_bricksmall, tex_normal);
					}else if(walldef.equals(" ")){
						//ground
						createUnnshadedBox(x, -1,y, texture_Grey13);
						//createBox(x, -1, y, mat_brick_cartoon);
					}
					else if(walldef.equals("l")){
						//createPointLight(x, y);

					}
				}
			}
		}
		
		lamp = new PointLight();
		lamp.setPosition(new Vector3f( 5f,1.2f, 5f));
		lamp.setRadius(30f);
		lamp.setColor(ColorRGBA.White);
		rootNode.addLight(lamp);
		
		
		//createBox(0, 0, mat_brick1);
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(3f));
		rootNode.addLight(al);

		FilterPostProcessor fpp=new FilterPostProcessor(assetManager);
		CartoonEdgeFilter toon=new CartoonEdgeFilter();
		toon.setEdgeWidth(5f);
		toon.setEdgeIntensity(1.0f);
		toon.setNormalThreshold(0.8f);
		fpp.addFilter(toon);
		//viewPort.addProcessor(fpp);

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
	public void simpleUpdate(float tpf) {
    
		cam.getLocation();
		lamp.setPosition(new Vector3f(cam.getLocation().x,1.2f,cam.getLocation().z));
	}
	
	int boxCounter = 0;

	private void createPointLight(int x,int y){
		PointLight lamp = new PointLight();
		lamp.setPosition(new Vector3f(2f*x ,1.2f, 2f*y));
		lamp.setRadius(10f);
		lamp.setColor(ColorRGBA.White);
		rootNode.addLight(lamp);
		
	}
	
	private void createUnnshadedBox(int x,int z,int y,Texture Texture){
		Material unshadedMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		unshadedMaterial.setTexture("ColorMap", Texture);
		unshadedMaterial.setColor("Color", ColorRGBA.White);
		
		boxCounter++;
		Box box3 = new Box(1f,1f,1f);
		Geometry geom_box3 = new Geometry("Box"+boxCounter, box3);
		geom_box3.setMaterial(unshadedMaterial);
		geom_box3.move(2f*x ,z*2f, 2f*y);
		rootNode.attachChild(geom_box3);
		
		//makeToonish(geom_box3);
	}
	
	//
	private void createBoxShadedBox(int x,int z,int y,Texture Texture,Texture normalMap){
		Material mat_lighting = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		mat_lighting.setTexture("DiffuseMap",Texture);
		mat_lighting.setTexture("NormalMap", normalMap);
		
		mat_lighting.setBoolean("UseMaterialColors",false);
		
		mat_lighting.setBoolean("LowQuality",true);
		
		mat_lighting.setColor("Specular",ColorRGBA.White);
		mat_lighting.setColor("Diffuse",ColorRGBA.White);
		mat_lighting.setFloat("Shininess", 50f); // [1,128]    
		
		boxCounter++;
		Box box3 = new Box(1f,1f,1f);
		Geometry geom_box3 = new Geometry("Box"+boxCounter, box3);
		geom_box3.setMaterial(mat_lighting);
		geom_box3.move(2f*x ,z*2f, 2f*y);
		rootNode.attachChild(geom_box3);
		
		
		//makeToonish(geom_box3);
	}

	public void makeToonish(Spatial spatial){
		if (spatial instanceof Node){
			Node n = (Node) spatial;
			for (Spatial child : n.getChildren())
				makeToonish(child);
		}else if (spatial instanceof Geometry){
			Geometry g = (Geometry) spatial;
			Material m = g.getMaterial();
			if (m.getMaterialDef().getName().equals("Phong Lighting")){
				Texture t = assetManager.loadTexture("Textures/ColorRamp/toon.png");
				//                t.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
				//                t.setMagFilter(Texture.MagFilter.Nearest);
				m.setTexture("ColorRamp", t);
				m.setBoolean("UseMaterialColors", true);
				m.setColor("Specular", ColorRGBA.Black);
				m.setColor("Diffuse", ColorRGBA.White);
				m.setBoolean("VertexLighting", true);
			}
		}
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
