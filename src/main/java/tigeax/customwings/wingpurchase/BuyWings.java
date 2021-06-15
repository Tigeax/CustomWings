package tigeax.customwings.wingpurchase;

import org.bukkit.entity.Player;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.util.Util;
import tigeax.customwings.wing.Wing;

public class BuyWings {

    public static boolean buyWing(Wing wing, Player player) {

        CustomWings plugin = CustomWings.getInstance();

        WingConfig wingConfig = wing.getConfig();

        String priceType = wingConfig.getPriceType();

        try {
            if (priceType.equalsIgnoreCase("none") || wingConfig.getPrice() == -1) {
                Util.sendMessage(player, plugin.getMessages().noPermissionToEquipWingError(wing));
                return false;
            }

            if (priceType.equalsIgnoreCase("token")) {
                if (CustomWings.getPlugin(CustomWings.class).getServer().getPluginManager().getPlugin("TokenManager") != null) {
                    if (BuyTokenManager.makePayment(wingConfig.getPrice(), player)) {
                        plugin.getPermissions().playerAdd(null, player, "customwings.wing."+wingConfig.getID().toLowerCase());
                        Util.sendMessage(player, plugin.getMessages().wingBuySuccess(wingConfig.getPrice()));
                    } else {
                        Util.sendMessage(player, plugin.getMessages().cantAffordWingError(wing));
                    }
                    return true;
                }
                return false;
            } else if (priceType.equalsIgnoreCase("economy")) {
                double playerbal = CustomWings.getEconomy().getBalance(player);
                if (playerbal >= wingConfig.getPrice()) {
                    CustomWings.getEconomy().withdrawPlayer(player, wingConfig.getPrice() );
                    plugin.getPermissions().playerAdd(null, player, "customwings.wing." + wingConfig.getID().toLowerCase());
                    Util.sendMessage(player, plugin.getMessages().wingBuySuccess(wingConfig.getPrice()));
                } else {
                    Util.sendMessage(player, plugin.getMessages().cantAffordWingError(wing));
                }
                return true;
            }
            return false;
        } catch (NullPointerException e) {
            return false;
        }


    }

}
