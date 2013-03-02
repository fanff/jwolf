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
import com.jme3.scene.shape.Box;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
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
	//private PssmShadowRenderer pssmRenderer;

	//private DirectionalLightShadowRenderer pssmRenderer;
	@Override
	public void simpleInitApp() {
		flyCam.setMoveSpeed(10f);
		showSettings = false;
		assetManager.registerLocator("./assets", FileLocator.class);

		Texture texture_blueCartoon = assetManager.loadTexture("Textures/bluecartoon.bmp");
		texture_blueCartoon.setWrap(WrapMode.Repeat);
		
		Texture texture_blueCartoonHeight = assetManager.loadTexture("Textures/bluecartoon_height.bmp");
		Texture texture_Blue17 = assetManager.loadTexture("Textures/WallPack/Blue17.bmp");
		Texture texture_Grey131 = assetManager.loadTexture("Textures/WallPack/Grey13.bmp");
		Texture bluecartoon_alpha = assetManager.loadTexture("Textures/bluecartoon_alpha.bmp");
		
		

		
		Material	mat_terrain = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");
		mat_terrain.setTexture("Tex1", texture_blueCartoon);
	    //mat_terrain.setTexture("Alpha", texture_blueCartoonHeight);

		mat_terrain.setFloat("Tex1Scale", 1);


		Material mat_stl = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_stl.setTexture("ColorMap", texture_Blue17);
		//mat_stl.setColor("Color", ColorRGBA.Blue);
		
		Material mat_cartoonheihtUnshaded = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_cartoonheihtUnshaded.setTexture("ColorMap", bluecartoon_alpha);
		mat_cartoonheihtUnshaded.setColor("Color", ColorRGBA.White);

		Material mat_brick1 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		String brick_color_filenae = "Textures/LightBrick/bricksmall.png";
		String brick_normal_filename = "Textures/LightBrick/bricksmall_normal.png";
		mat_brick1.setTexture("DiffuseMap",assetManager.loadTexture(brick_color_filenae));
		mat_brick1.setTexture("NormalMap", assetManager.loadTexture(brick_normal_filename));
		mat_brick1.setBoolean("UseMaterialColors",true);    
		mat_brick1.setColor("Specular",ColorRGBA.White);
		mat_brick1.setColor("Diffuse",ColorRGBA.White);
		mat_brick1.setFloat("Shininess", 120f); // [1,128]    

		Material ground_mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		ground_mat.setBoolean("UseMaterialColors", true);
		ground_mat.setColor("Ambient",  ColorRGBA.DarkGray);
		ground_mat.setColor("Diffuse",  ColorRGBA.DarkGray);
		ground_mat.setColor("Specular", ColorRGBA.White);
		ground_mat.setFloat("Shininess", 12);

		////
		Material mat_brick_blue5 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		String brick3_color_filenae = "Textures/WallPack/Blue5.bmp";
		String brick3_normal_filename = "Textures/LightBrick/bricksmall_normal.png";
		mat_brick_blue5.setTexture("DiffuseMap",assetManager.loadTexture(brick3_color_filenae));
		mat_brick_blue5.setTexture("NormalMap", assetManager.loadTexture(brick3_normal_filename));
		mat_brick_blue5.setBoolean("UseMaterialColors",false);    
		mat_brick_blue5.setColor("Specular",ColorRGBA.White);
		mat_brick_blue5.setColor("Diffuse",ColorRGBA.White);
		mat_brick_blue5.setFloat("Shininess", 1f); // [1,128]    

		Material mat_brick_cartoon = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		mat_brick_cartoon.setTexture("DiffuseMap",texture_blueCartoon);
		mat_brick_cartoon.setTexture("NormalMap", assetManager.loadTexture(brick3_normal_filename));
		mat_brick_cartoon.setBoolean("UseMaterialColors",false);    
		mat_brick_cartoon.setColor("Specular",ColorRGBA.White);
		mat_brick_cartoon.setColor("Diffuse",ColorRGBA.White);
		mat_brick_cartoon.setFloat("Shininess", 50f); // [1,128]    
		
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

		/** create the ground 
		Box ground = new Box(256f,1f,256f);
		Geometry ground_geom = new Geometry("Box", ground);
		TangentBinormalGenerator.generate(ground_geom.getMesh(), true);
		ground_geom.setMaterial(ground_mat);
		ground_geom.move(256f,-2,256f);
		rootNode.attachChild(ground_geom);
*/
		//
		//load the level
		//
		for (int x = 0; x < JwLevel.MAXX; x++) {
			for (int y = 0; y < JwLevel.MAXY; y++) {
				String walldef = level.getWall(x, y);
				if(walldef != null){
					if(walldef.equals("#")){
						//box
						createUnnshadedBox(x, 0,y, texture_Blue17);
					}else if(walldef.equals(" ")){
						//ground
						createUnnshadedBox(x, -1,y, texture_Grey131);
						//createBox(x, -1, y, mat_brick_cartoon);
					}
					else if(walldef.equals("l")){
						//createPointLight(x, y);

					}
				}
			}
		}
		//createBox(0, 0, mat_brick1);
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(30f));
		rootNode.addLight(al);

		FilterPostProcessor fpp=new FilterPostProcessor(assetManager);
		CartoonEdgeFilter toon=new CartoonEdgeFilter();
		toon.setEdgeWidth(5f);
		toon.setEdgeIntensity(1.0f);
		toon.setNormalThreshold(0.8f);
		fpp.addFilter(toon);
		///viewPort.addProcessor(fpp);

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

	private void createPointLight(int x,int y){
		PointLight lamp = new PointLight();
		lamp.setPosition(new Vector3f(2f*x ,1.2f, 2f*y));
		lamp.setRadius(30f);
		lamp.setColor(ColorRGBA.White);
		rootNode.addLight(lamp);
	}
	
	private void createUnnshadedBox(int x,int z,int y,Texture Texture){
		Material mat_cartoonheihtUnshaded = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_cartoonheihtUnshaded.setTexture("ColorMap", Texture);
		mat_cartoonheihtUnshaded.setColor("Color", ColorRGBA.White);
		
		boxCounter++;
		Box box3 = new Box(1f,1f,1f);
		Geometry geom_box3 = new Geometry("Box"+boxCounter, box3);
		geom_box3.setMaterial(mat_cartoonheihtUnshaded);
		geom_box3.move(2f*x ,z*2f, 2f*y);
		rootNode.attachChild(geom_box3);
		//makeToonish(geom_box3);
	}
	
	
	private void createBox(int x,int z,int y,Material boxMaterial){
		boxCounter++;
		Box box3 = new Box(1f,1f,1f);
		Geometry geom_box3 = new Geometry("Box"+boxCounter, box3);
		geom_box3.setMaterial(boxMaterial);
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
