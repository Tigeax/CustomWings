package tigeax.customwings.gui.guis;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import main.java.tigeax.customwings.editor.EditorConfigManager;
import main.java.tigeax.customwings.gui.CWGUIManager;
import main.java.tigeax.customwings.gui.CWGUIType;
import main.java.tigeax.customwings.gui.ParticleItem;
import main.java.tigeax.customwings.main.CWPlayer;
import main.java.tigeax.customwings.main.CustomWings;
import main.java.tigeax.customwings.main.Settings;

public class EditorSelectParticle {

	Settings settings;
	EditorConfigManager editorConfigManager;

	ArrayList<ItemStack> page1Items;
	ArrayList<ItemStack> page2Items;

	public EditorSelectParticle() {
		settings = CustomWings.getSettings();
		editorConfigManager = CustomWings.getEditorConfigManager();

		page1Items = new ArrayList<>();
		page2Items = new ArrayList<>();

		int counter = 0;
		for (ParticleItem particleItem : ParticleItem.values()) {

			if (particleItem.getVersion().equals("1.14")
					&& Arrays.asList("v1_13_R1", "v1_13_R2").contains(CustomWings.getServerVersion())) continue;

			ItemStack item = CWGUIManager.getItem(particleItem.getMaterial(), "&f" + particleItem.getName());

			if (counter <= 44)
				page1Items.add(item);
			else
				page2Items.add(item);

			counter++;
		}
	}

	public void openPage1(CWPlayer cwPlayer) {

		String guiName = settings.getEditorGUIName() + " - Select Particle - Page 1";

		Inventory gui = Bukkit.createInventory(null, 54, guiName);

		int slot = 0;
		for (ItemStack item : page1Items) {
			gui.setItem(slot, item);
			slot++;
		}

		gui.setItem(50, CWGUIManager.getPlayerHeadItem("6527ebae9f153154a7ed49c88c02b5a9a9ca7cb1618d9914a3d9df8ccb3c84", "&2Next Page"));
		gui.setItem(53, CWGUIManager.getPlayerHeadItem("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6", "&4Cancel"));

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void openPage2(CWPlayer cwPlayer) {

		String guiName = settings.getEditorGUIName() + " - Select Particle - Page 2";

		Inventory gui = Bukkit.createInventory(null, 54, guiName);

		int slot = 0;
		for (ItemStack item : page2Items) {
			gui.setItem(slot, item);
			slot++;
		}

		gui.setItem(48, CWGUIManager.getPlayerHeadItem("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6", "&3Previous page"));
		gui.setItem(53, CWGUIManager.getPlayerHeadItem("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6", "&4Cancel"));

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, String itemName, Integer clickedSlot) {

		switch (itemName) {
			case "Cancel":
				cwPlayer.setWaitingSetting(null);
				cwPlayer.openCWGUI(CWGUIType.LASTEDITORGUI);
				return;
			case "Next Page":
				openPage2(cwPlayer);
				return;
			case "Previous page":
				openPage1(cwPlayer);
				return;
		}

		if (itemName.equals("Cancel")) {
			cwPlayer.setWaitingSetting(null);
			cwPlayer.openCWGUI(CWGUIType.LASTEDITORGUI);
			return;
		}

		editorConfigManager.setSetting(cwPlayer.getWaitingSetting(), ParticleItem.getParticleByName(itemName), cwPlayer.getWaitingSettingInfo());
		cwPlayer.setWaitingSetting(null);
		cwPlayer.openCWGUI(CWGUIType.LASTEDITORGUI);
	}
}
