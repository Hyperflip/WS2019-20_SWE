package BIF.SWE1;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebPluginManager implements PluginManager {

    CopyOnWriteArrayList<Plugin> plugins; // TODO Maybe find a different Collection

    private static PluginManager manager;

    private static final String PACKAGE_NAME = "BIF.SWE1.plugins";

    private WebPluginManager(){
        this.plugins = new CopyOnWriteArrayList<Plugin>();
        this.loadPlugins();
    }

    public static PluginManager getPluginManager(){
        if(manager == null){
            manager = new WebPluginManager();
        }
        return manager;
    }

    private void loadPlugins(){
        File directory = new File("src\\" + PACKAGE_NAME.replace('.','\\'));
        for(File file : directory.listFiles()){
            if(file.getName().contains("Plugin")){
                System.out.println("Found Plugin: " + file.getName());
                this.add(PACKAGE_NAME + "." + file.getName().split("\\.")[0]);
            }
        }
    }

    public static Plugin getSuitablePluginForRequest(Request request){
        Plugin highestScoring = null;
        float highestScore = 0.0f;

        for (Plugin plugin : WebPluginManager.getPluginManager().getPlugins()){
            float newScore = plugin.canHandle(request);
            if(newScore > highestScore){
                highestScore = newScore;
                highestScoring = plugin;
            }
        }

        return highestScoring;
    }

    @Override
    public Iterable<Plugin> getPlugins() {
        return plugins;
    }

    @Override
    public void add(Plugin plugin) {
        this.plugins.add(plugin);
    }

    @Override
    public void add(String plugin) {
        for (Plugin plug : plugins) if(plug.getClass().getName().equals(plugin)) return;

        ClassLoader loader = Plugin.class.getClassLoader();
        try{
            // TODO Maybe find a way to do this without Intellij being bitchy about it
            System.out.println(System.getProperty("user.dir"));
            //Class pluginClass = loader.loadClass("PACKAGE_NAME" + "." + plugin);
            Class pluginClass = Class.forName(plugin);
            Plugin object;
            object = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
            plugins.add(object);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        this.plugins.clear();
    }
}