package BIF.SWE1;

import BIF.SWE1.interfaces.Plugin;
import BIF.SWE1.interfaces.PluginManager;
import BIF.SWE1.interfaces.Request;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * the plugin manager holds the plugin's info and contains methods of managing them
 */

public class WebPluginManager implements PluginManager {

    CopyOnWriteArrayList<Plugin> plugins; // TODO Maybe find a different Collection

    private static PluginManager manager;

    private static final String PACKAGE_NAME = "BIF.SWE1.plugins";

    private WebPluginManager(){
        this.plugins = new CopyOnWriteArrayList<Plugin>();
        this.loadPlugins();
    }

    /**
     * static function to get the plugin manager
     *
     * @return the same instance of PluginManager a la Singleton
     */

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

    /**
     * returns the best suitable plugin for performing a task, based on the request
     *
     * @param request Incoming HTTP WebRequest object
     * @return suitable pluing to deal with the request
     */

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

        try{
            System.out.println(System.getProperty("user.dir"));

            Class pluginClass = Class.forName(plugin);

            Plugin object = (Plugin) pluginClass.getDeclaredConstructor().newInstance();

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