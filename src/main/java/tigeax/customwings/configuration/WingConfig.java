package tigeax.customwings.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tigeax.customwings.CustomWings;
import tigeax.customwings.util.Util;
import tigeax.customwings.util.YamlFile;
import tigeax.customwings.wings.WingParticle;

public class WingConfig extends YamlFile {

    protected final String ID;

    protected boolean hideInGUI;
    protected ItemStack guiItem;
    protected String guiItemName;
    protected int guiSlot;

    protected List<String> loreWhenEquipped, loreWhenUnequipped, loreWhenNoPermission, loreWhenCanBuy;

    protected boolean showWhenMoving;
    protected List<String> whitelistedWorlds;

    protected double startVertical, startHorizontal, distanceBetweenParticles;
    protected int wingTimer;

    protected boolean wingAnimation;
    protected int wingFlapSpeed, startOffset, stopOffset;

    protected ArrayList<WingParticle> wingParticles;

    protected int wingPrice;
    protected String priceType;
    protected String buyMessage;

    // Hasmap containing the coordinates relative to the player
    // And the assinged particle at that coordinate
    // double[] functions as double[distance from player, height]
    protected HashMap<double[], WingParticle> particleCoordinates;

    public WingConfig(CustomWings plugin, File configFile) {
        super(plugin, configFile);

        this.ID = configFile.getName().replace(".yml", "").toLowerCase();

        loadDataFromFile();

    }

    @Override
    protected void updateFile() {
        try {
            load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void loadDataFromFile() {
        hideInGUI = getBoolean("guiItem.hideInGUI", false);

        guiItemName = getString("guiItem.name");
        guiItem = getItem(guiItemName, getString("guiItem.material"));
        guiSlot = getInt("guiItem.slot");

        loreWhenEquipped = Util.parseLoreString(getStringList("guiItem.loreWhenEquipped").toString());
        loreWhenUnequipped = Util.parseLoreString(getStringList("guiItem.loreWhenUnequipped").toString());
        loreWhenNoPermission = Util.parseLoreString(getStringList("guiItem.loreWhenNoPermission").toString());

        showWhenMoving = getBoolean("showWhenMoving", true);
        whitelistedWorlds = parseWhitelistedWorlds(getStringList("whitelistedWorlds").toString());

        startVertical = getDouble("wingLayout.startVertical", 0.0);
        startHorizontal = getDouble("wingLayout.startHorizontal", 0.0);
        distanceBetweenParticles = getDouble("wingLayout.distanceBetweenParticles", 0.1);
        wingTimer = getInt("wingLayout.wingTimer", 5);

        wingAnimation = getBoolean("wingLayout.wingAnimation", false);
        wingFlapSpeed = getInt("wingLayout.wingFlapSpeed", 1);
        startOffset = getInt("wingLayout.startOffset", 30);
        stopOffset = getInt("wingLayout.stopOffset", 70);

        wingParticles = parseWingParticles(getConfigurationSection("particles"));
        particleCoordinates = parseParticleCoordinates(getConfigurationSection("particleLayout"));

        wingPrice = getInt("price");
        priceType = getString("price-type");

        try {
            buyMessage = getString("buyMessage");
            if (buyMessage == null) {
                buyMessage = "&3You just bought " + guiItemName;
            }
        } catch (NullPointerException e) {
            // If buy message was not supplied set it to this
            buyMessage = "&3You just bought " + guiItemName;
        }

        loreWhenCanBuy = getStringList("guiItem.loreWhenCanBuy");
    }

    public void reload() {
        loadDataFromFile();
    }

    public String getID() {
        return ID;
    }

    public boolean getHideInGUI() {
        return hideInGUI;
    }

    public String getGUIItemName() {
        return guiItemName;
    }

    public ItemStack getGuiItem() {
        return guiItem;
    }

    public int getGuiSlot() {
        return guiSlot;
    }

    public List<String> getLoreWhenEquipped() {
        return loreWhenEquipped;
    }

    public List<String> getLoreWhenUnequipped() {
        return loreWhenUnequipped;
    }

    public List<String> getLoreWhenNoPermission() {
        return loreWhenNoPermission;
    }

    public List<String> getloreWhenCanBuy() {

        List<String> lore = new ArrayList<>();

        try {
            loreWhenCanBuy.isEmpty();
        } catch (NullPointerException e) {
            lore.add(ChatColor.translateAlternateColorCodes('&', "You can buy this for " + wingPrice));
            return lore;
        }

        for (String s : loreWhenCanBuy) {
            s = s.replaceAll("%price%", String.valueOf(wingPrice));
            lore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return lore;
    }

    public String getLoreWhenEquippedString() {
        return getLoreWhenEquipped().toString().replace("[", "").replace("]", "");
    }

    public String getLoreWhenUnequippedString() {
        return getLoreWhenUnequipped().toString().replace("[", "").replace("]", "");
    }

    public String getLoreWhenNoPermissionString() {
        return getLoreWhenNoPermission().toString().replace("[", "").replace("]", "");
    }

    public List<String> getWhitelistedWorlds() {
        return this.whitelistedWorlds;
    }

    public String getWhitelistedWorldsString() {
        return getWhitelistedWorlds().toString().replace("[", "").replace("]", "");
    }

    public boolean getShowWhenMoving() {
        return this.showWhenMoving;
    }

    public double getStartVertical() {
        return startVertical;
    }

    public double getStartHorizontal() {
        return startHorizontal;
    }

    public double getDistanceBetweenParticles() {
        return distanceBetweenParticles;
    }

    public int getWingTimer() {
        return wingTimer;
    }

    public boolean getWingAnimation() {
        return wingAnimation;
    }

    public int getWingFlapSpeed() {
        return wingFlapSpeed;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getStopOffset() {
        return stopOffset;
    }

    public ArrayList<WingParticle> getWingParticles() {
        return wingParticles;
    }

    public WingParticle getWingParticleByID(String ID) {
        for (WingParticle wingParticle : wingParticles) {
            if (wingParticle.getID().equals(ID)) {
                return wingParticle;
            }
        }
        return null;
    }

    public int getWingPrice() {
        return wingPrice;
    }

    public String getPriceType() {
        return priceType;
    }

    public String getBuyMessage() {
        return buyMessage;
    }

    public HashMap<double[], WingParticle> getParticleCoordinates() {
        return particleCoordinates;
    }




    // Util

    @Override
    public String getString(String path) {
        String string = super.getString(path);
        string = Util.parseChatColors(string);
        return string;
    }

    private ItemStack getItem(String name, String material) {
		ItemStack item = new ItemStack(Material.valueOf(material));

		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);

		return item;
	}

    private List<String> parseWhitelistedWorlds(String list) {
		return Arrays.asList(list.replace("]", "").replace("[", "").split(", "));
	}

    // TODO Refractor

    // Turn the data gotten from the config.yml into a HashMap containing the relative coordinates and the assinged wingParticle
	private HashMap<double[], WingParticle> parseParticleCoordinates(ConfigurationSection particleLayout) {

		HashMap<double[], WingParticle> particleCoordinates = new HashMap<>();
		Set<String> rows = particleLayout.getKeys(false);
		double distance;
		double height = startVertical + (rows.size() * distanceBetweenParticles); // Highest vertical point of the wing

		// Go through all the horizontal rows
		for (String rowNumber : rows) {

			height = height - distanceBetweenParticles;
			distance = startHorizontal;

			String[] particleLine = particleLayout.getString(rowNumber).split(",");

			// Go though each "particle" on the row
			for (String particleID : particleLine) {

				// "-" means there should be no particle at the coordinate
				if (particleID.equals("-")) {
					distance = distance + distanceBetweenParticles;
					continue;
				}

				double[] coordinates = new double[2];
				coordinates[0] = distance;
				coordinates[1] = height;

				particleCoordinates.put(coordinates, getWingParticleByID(particleID));

				distance = distance + distanceBetweenParticles;

			}
		}
		return particleCoordinates;
	}

	// Turn the data gotten from the config.yml into all the WingParticles of a wing
	private ArrayList<WingParticle> parseWingParticles(ConfigurationSection wingParticlesConfig) {

		ArrayList<WingParticle> particles = new ArrayList<>();

		// Loop throught all the wing particles of the wing
		for (String key : wingParticlesConfig.getKeys(false)) {

			ConfigurationSection particleConfig = wingParticlesConfig.getConfigurationSection(key);

			Particle particle;

			try {
				particle = Particle.valueOf(particleConfig.getString("particle"));
			} catch (Exception e) {
				plugin.sendError(e);
				particle = Particle.BARRIER;
			}

			double distance = particleConfig.getDouble("distance");
			double height = particleConfig.getDouble("height");
			int angle = particleConfig.getInt("angle");
			double speed = particleConfig.getDouble("speed");

			Material material = Material.valueOf(particleConfig.getString("blockType"));
			DustOptions color = new Particle.DustOptions(Color.fromRGB(particleConfig.getInt("color")), (float) 1);

			WingParticle wingParticle = new WingParticle(this, key, particle, distance, height, angle, speed, color, material);

			particles.add(wingParticle);
		}

		return particles;

	}
}
