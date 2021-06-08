package tigeax.customwings.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import tigeax.customwings.CustomWings;


/**
 * Extention of YamlConfiguration to make it easier to work with YAML files.
 */
public class YamlFile extends YamlConfiguration {


    private final CustomWings plugin;
    private final String filename;
    private final File file;


    public YamlFile(CustomWings plugin, String filename) {

        this.plugin = plugin;
        this.filename = filename;
        this.file = new File(plugin.getDataFolder(), filename);
        
        this.updateFile();
    }

    /**
    * Reload/update the file, for when it was externally edited.
    * Check if it exists, and if not create it by calling createIfNotExists().
    */
    public void updateFile() {

        plugin.getLogger().info("Loading " + filename + "...");

        createIfNotExists();

        try {
            load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
    * Check if the file exists, if not create it by calling createFile().
    */
    private void createIfNotExists() {
        if (!file.exists()) {
            createFile();
        }
    }

    /**
    * Create the specified file, overwriting it if it already exists.
    */
    private void createFile() {
        plugin.getLogger().info(filename + " file does not exists. Creating it...");
        file.getParentFile().mkdirs();
        plugin.saveResource(filename, false);
    }
}
