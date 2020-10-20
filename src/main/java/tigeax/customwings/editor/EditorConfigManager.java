package tigeax.customwings.editor;

import java.util.Arrays;

import tigeax.customwings.wings.WingParticle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import tigeax.customwings.CustomWings;
import tigeax.customwings.wings.Wing;

/*
 * This classe is used to change settings in the config.yml
 * The settings are changed ingame trough the editor GUI's
 */

public class EditorConfigManager {

	private CustomWings plugin;
	private FileConfiguration config;
	private ConfigurationSection mainGUIConfig;

	public EditorConfigManager(CustomWings plugin) {
		this.plugin = plugin;
		this.config = plugin.getConfig();
		this.mainGUIConfig = config.getConfigurationSection("mainGUI");
	}

	public void setSetting(SettingType setting, Object value) {
		setSetting(setting, value + "", null);
		return;
	}

	// Main method
	// Select if the setting is a global, wing or wingParticle setting
	public void setSetting(SettingType setting, Object value, Object info) {

		String valueString = value + "";

		if (info == null) {
			changeSetting(setting, valueString);
			return;
		}

		if (info instanceof Wing) {
			changeSetting(setting, valueString, (Wing) info);
			return;
		}

		if (info instanceof WingParticle) {
			changeSetting(setting, valueString, (WingParticle) info);
			return;
		}
	}

	// Used for all the global settings
	private void changeSetting(SettingType setting, String value) {

		switch (setting) {
			case VIEWDISTANCE:
				config.set("wingViewDistance", parseInt(value));
				break;
			case MAINGUINAME:
				mainGUIConfig.set("name", value);
				break;
			case MAINGUISIZE:
				mainGUIConfig.set("size", parseInt(value));
				break;
			case REMOVEWINGNAME:
				mainGUIConfig.set("removeWingItem.name", value);
				break;
			case REMOVEWINGMATERIAL:
				mainGUIConfig.set("removeWingItem.material", value);
				break;
			case REMOVEWINGSLOT:
				mainGUIConfig.set("removeWingItem.slot", parseInt(value));
				break;
			case HIDEWINGTOGGLENAMEON:
				mainGUIConfig.set("hideWingsToggleItem.nameON", value);
				break;
			case HIDEWINGTOGGLENAMEOFF:
				mainGUIConfig.set("hideWingsToggleItem.nameOFF", value);
				break;
			case HIDEWINGTOGGLEMATERIALON:
				mainGUIConfig.set("hideWingsToggleItem.materialON", value);
				break;
			case HIDEWINGTOGGLEMATERIALOFF:
				mainGUIConfig.set("hideWingsToggleItem.materialOFF", value);
				break;
			case HIDEWINGTOGGLESLOT:
				mainGUIConfig.set("hideWingsToggleItem.slot", parseInt(value));
				break;
			case EDITORGUINAME:
				config.set("editorGUI.name", value);
				break;
			case EDITORMAINSETTINGSSLOT:
				config.set("editorGUI.mainSettingsItem.slot", parseInt(value));
				break;
			default:
				CustomWings.sendError("Something went wrong while trying to change a setting!");
				return;
		}

		plugin.saveConfig();
		CustomWings.getSettings().reload();
	}

	// Used for all the wing settings
	private void changeSetting(SettingType setting, String value, Wing wing) {

		switch (setting) {

			case WINGSHOWWHENMOVING:
				getWingConfig(wing).set("showWhenMoving", value);
				break;
			case WINGWHITELISTEDWORLDS:
				getWingConfig(wing).set("whitelistedWorlds", Arrays.asList(value));
				break;
			case WINGHIDEINGUI:
				getWingGUIItemConfig(wing).set("hideInGUI", value);
				break;
			case WINGGUINAME:
				getWingGUIItemConfig(wing).set("name", value);
				break;
			case WINGGUIMATERIAL:
				getWingGUIItemConfig(wing).set("material", value);
				break;
			case WINGGUISLOT:
				getWingGUIItemConfig(wing).set("slot", parseInt(value));
				break;
			case WINGGUILOREHWENEQUIPPED:
				getWingGUIItemConfig(wing).set("loreWhenEquipped", Arrays.asList(value));
				break;
			case WINGGUILOREWHENUNEQUIPPED:
				getWingGUIItemConfig(wing).set("loreWhenUnequipped", Arrays.asList(value));
				break;
			case WINGGUILOREWHENNOPERMISSION:
				getWingGUIItemConfig(wing).set("loreWhenNoPermission", Arrays.asList(value));
				break;
			case WINGSTARTVERTICAL:
				getWingLayoutConfig(wing).set("startVertical", parseDouble(value));
				break;
			case WINGSTARTHORIZONTAL:
				getWingLayoutConfig(wing).set("startHorizontal", parseDouble(value));
				break;
			case WINGDISTANCEBETWEENPARTICLES:
				getWingLayoutConfig(wing).set("distanceBetweenParticles", parseDouble(value));
				break;
			case WINGTIMER:
				getWingLayoutConfig(wing).set("wingTimer", parseInt(value));
				break;
			case WINGANIMATION:
				getWingLayoutConfig(wing).set("wingAnimation", value);
				break;
			case WINGFLAPSPEED:
				getWingLayoutConfig(wing).set("wingFlapSpeed", parseDouble(value));
				break;
			case WINGSTARTOFFSET:
				getWingLayoutConfig(wing).set("startOffset", parseInt(value));
				break;
			case WINGSTOPOFFSET:
				getWingLayoutConfig(wing).set("stopOffset", parseInt(value));
				break;
			default:
				CustomWings.sendError("Something went wrong while trying to change a setting!");
				return;

		}

		plugin.saveConfig();
		wing.reload();
	}

	// Used for all the wingParticle settings
	private void changeSetting(SettingType setting, String value, WingParticle wingParticle) {

		switch (setting) {

			case WINGPARTICLEPARTICLE:
				getWingParticleConfig(wingParticle).set("particle", value);
				break;
			case WINGPARTICLEDISTANCE:
				getWingParticleConfig(wingParticle).set("distance", parseDouble(value));
				break;
			case WINGPARTICLEHEIGHT:
				getWingParticleConfig(wingParticle).set("height", parseDouble(value));
				break;
			case WINGPARTICLEANGLE:
				getWingParticleConfig(wingParticle).set("angle", parseDouble(value));
				break;
			case WINGPARTICLESPEED:
				getWingParticleConfig(wingParticle).set("speed", parseDouble(value));
				break;
			case WINGPARTICLEBLOCKTYPE:
				getWingParticleConfig(wingParticle).set("blockType", value);
				break;
			case WINGPARTICLECOLOR:
				getWingParticleConfig(wingParticle).set("color", parseInt(value));
				break;
			default:
				CustomWings.sendError("Something went wrong while trying to change a setting!");
				return;
		}

		plugin.saveConfig();
		wingParticle.getWing().reload();
	}

	public ConfigurationSection getWingConfig(Wing wing) {
		return config.getConfigurationSection("wings." + wing.getID());
	}

	public ConfigurationSection getWingGUIItemConfig(Wing wing) {
		return getWingConfig(wing).getConfigurationSection("guiItem");
	}

	public ConfigurationSection getWingLayoutConfig(Wing wing) {
		return getWingConfig(wing).getConfigurationSection("wingLayout");
	}

	public ConfigurationSection getWingParticleConfig(WingParticle particle) {
		return getWingConfig(particle.getWing()).getConfigurationSection("particles." + particle.getID());
	}

	// Shorter version for Integer.parseInt()
	public int parseInt(String string) {
		return Integer.parseInt(string);
	}

	// Shorter version for Doulbe.parseDouble()
	public double parseDouble(String string) {
		return Double.parseDouble(string);
	}
}
