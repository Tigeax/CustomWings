package tigeax.customwings.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import tigeax.customwings.CustomWings;

/**
 * Extention of YamlConfiguration to make it easier to work with YAML files.
 */
public abstract class YamlFile extends YamlConfiguration {

    protected final CustomWings plugin;
    protected final File file;

    public YamlFile(CustomWings plugin, String filename) {
        this(plugin, new File(plugin.getDataFolder(), filename));
    }

    public YamlFile(CustomWings plugin, File file) {
        this.plugin = plugin;
        this.file = file;

        updateFile();
        initDataFromFile();
    }

    protected abstract void initDataFromFile();

    protected abstract void updateDataFromFile();

    public void update() {
        updateFile();
        updateDataFromFile();
    }

    public void save() {
        try {
            super.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reload/update the file, for when it was externally edited. Check if it
     * exists, and if not create it by calling createIfNotExists().
     */
    protected void updateFile() {

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
        plugin.getLogger().info(file.getName() + " file does not exists. Creating it...");
        file.getParentFile().mkdirs();
        plugin.saveResource(file.getName(), false);
    }

    
    public String getColorString(String path) {

        String string;

        try {
            string = getString(path);
            string = Util.parseChatColors(string);
        } catch (IllegalArgumentException e) {
            string = "";
            plugin.getLogger().warning("Failed to get " + path + ". Replacing with an empty string");
        }

        return string;
    }

    public List<String> getColorStringList(String path) {
        List<String> stringList = getStringList(path);
        stringList = Util.parseLoreChatColor(stringList);
        return stringList;
    }

}
