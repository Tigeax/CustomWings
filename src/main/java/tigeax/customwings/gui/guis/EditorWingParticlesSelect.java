package tigeax.customwings.gui.guis;

import main.java.tigeax.customwings.gui.CWGUIManager;
import main.java.tigeax.customwings.gui.CWGUIType;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import main.java.tigeax.customwings.main.CWPlayer;
import main.java.tigeax.customwings.main.CustomWings;
import main.java.tigeax.customwings.main.Settings;
import main.java.tigeax.customwings.main.Wing;
import main.java.tigeax.customwings.main.WingParticle;

public class EditorWingParticlesSelect {
	
	Settings settings;

	public EditorWingParticlesSelect() {
		settings = CustomWings.getSettings();
	}

	public void open(CWPlayer cwPlayer, Wing wing) {

		String guiName = settings.getEditorGUIName() + " - Wing Particles";

		Inventory gui = Bukkit.createInventory(null, 54, guiName);

		gui.setItem(4, CWGUIManager.getItem(wing.getGuiItem().getType(), "&f" + wing.getID()));
		gui.setItem(53, CWGUIManager.getPlayerHeadItem("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6", "&4Previous Page"));

		int slot = 9;

		for (WingParticle wingParticle : wing.getWingParticles()) {
			gui.setItem(slot, CWGUIManager.getItem(wingParticle.getItemMaterial(), "&f" + wingParticle.getID()));
			slot++;
		}

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, String itemName, Wing wing) {

		if (itemName.equals("Previous Page")) {
			cwPlayer.openCWGUI(CWGUIType.EDITORWINGSETTINGS, wing);
			return;
		}

		WingParticle wingParticle = wing.getWingParticleByID(itemName);
		cwPlayer.openCWGUI(CWGUIType.EDITORWINGPARTICLESETTINGS, wingParticle);
	}
	
}
