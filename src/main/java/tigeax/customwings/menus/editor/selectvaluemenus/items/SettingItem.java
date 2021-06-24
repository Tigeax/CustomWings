package tigeax.customwings.menus.editor.selectvaluemenus.items;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.configuration.settings.SettingType;
import tigeax.customwings.menus.MenuManager;
import tigeax.customwings.menus.editor.selectvaluemenus.ColorSelectMenu;
import tigeax.customwings.menus.editor.selectvaluemenus.DecimalNumberSelectMenu;
import tigeax.customwings.menus.editor.selectvaluemenus.MenuSizeSelectMenu;
import tigeax.customwings.menus.editor.selectvaluemenus.MenuSlotSelectMenu;
import tigeax.customwings.menus.editor.selectvaluemenus.NumberSelectMenu;
import tigeax.customwings.menus.editor.selectvaluemenus.ParticleSelectMenuPage;
import tigeax.customwings.util.ParticleItem;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.MenuItem;

public class SettingItem extends MenuItem {

    private Setting setting;
    private boolean noClickAction;

    public SettingItem(Setting setting, String name, Material material, boolean noClickAction) {
        this.setting = setting;
        this.noClickAction = noClickAction;

        setDisplayName(name);
        setMaterial(material);  
    }

    public SettingItem(Setting setting, String name, Setting materialSetting) {
        this(setting, name, (Material) materialSetting.getCurrentValue(), false);
    }

    public SettingItem(Setting setting, String name, Material material) {
        this(setting, name, material, false);
    }

    public SettingItem(Setting setting, String name) {
        this(setting, name, Material.DIRT, false);
    }

    @Override
    public ItemStack getFinalItem(Player player) {

        Object currentValue = setting.getCurrentValue();

        if (currentValue instanceof Particle) {
            Particle particleCurrentValue = (Particle) currentValue;
            setMaterial(ParticleItem.valueOf(particleCurrentValue.toString()).getMaterial());
            setLore(MenuManager.parseLoreForItem(particleCurrentValue.toString()));

        } else if (currentValue instanceof DustOptions) {
            DustOptions dustOptionsCurrentValue = (DustOptions) currentValue;
            setLore(MenuManager.parseLoreForItem(dustOptionsCurrentValue.getColor().asRGB() + ""));

        } else if (currentValue instanceof Material) {
            Material materialCurrentValue = (Material) currentValue;
            setMaterial(materialCurrentValue);
            setLore(MenuManager.parseLoreForItem(materialCurrentValue.toString()));

        } else {
            setLore(MenuManager.parseLoreForItem(setting.getCurrentValue().toString()));
        }

        return super.getFinalItem(player);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {

        if (noClickAction) {
            return;
        }

        SettingType settingType = setting.getSettingType();
        Player player = event.getPlayer();
        CWPlayer cwPlayer = CustomWings.getInstance().getCWPlayer(player);

        switch (settingType) {

            case BOOLEAN:
                setting.setValue(!((Boolean) setting.getCurrentValue()));
                break;

            case GUISLOT:
                new MenuSlotSelectMenu(event.getItemMenu(), setting).open(player);
                break;

            case GUISIZE:
                new MenuSizeSelectMenu(event.getItemMenu(), setting).open(player);
                break;

            case INT:
                new NumberSelectMenu(event.getItemMenu(), setting, this).open(player);
                break;

            case DOUBLE:
                new DecimalNumberSelectMenu(event.getItemMenu(), setting, this).open(player);
                break;

            case PARTICLE:
                new ParticleSelectMenuPage(event.getItemMenu(), setting, 1).open(player);
                break;

            case COLOR:
                new ColorSelectMenu(event.getItemMenu(), setting).open(player);
                break;

            case MATERIAL:
                cwPlayer.sendMessage(CustomWings.getInstance().getMessages().selectSettingMaterial());
                cwPlayer.setWaitingSetting(setting);
                event.setWillClose(true);
                break;

            case STRING:
                cwPlayer.sendMessage(CustomWings.getInstance().getMessages().typeSettingInChat());
                cwPlayer.setWaitingSetting(setting);
                event.setWillClose(true);
                break;

            case STRINGLIST:
                cwPlayer.sendMessage(CustomWings.getInstance().getMessages().typeSettingInChat());
                cwPlayer.setWaitingSetting(setting);
                event.setWillClose(true);
                break;
        }
    }

}
