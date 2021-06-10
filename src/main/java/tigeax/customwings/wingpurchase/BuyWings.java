package tigeax.customwings.wingpurchase;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.wings.Wing;

public class BuyWings {

    public static boolean buyWing(Wing wing, Player player) {

        WingConfig wingConfig = wing.getConfig();

        String priceType = wingConfig.getPriceType();

        try {
            if (priceType.equalsIgnoreCase("none") || wingConfig.getWingPrice() == -1) {
                player.sendMessage(CustomWings.getInstance().getMessages().noPermissionToEquipWingError(wing));
                return false;
            }

            if (priceType.equalsIgnoreCase("token")) {
                if (CustomWings.getPlugin(CustomWings.class).getServer().getPluginManager().getPlugin("TokenManager") != null) {
                    if (BuyTokenManager.makePayment(wingConfig.getWingPrice(), player)) {
                        CustomWings.getInstance().getPermissions().playerAdd(null, player, "customwings.wing."+wingConfig.getID().toLowerCase());
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',wingConfig.getBuyMessage()));
                    } else {
                        player.sendMessage(CustomWings.getInstance().getMessages().cantAffordWingError(wing));
                    }
                    return true;
                }
                return false;
            } else if (priceType.equalsIgnoreCase("economy")) {
                double playerbal = CustomWings.getEconomy().getBalance(player);
                if (playerbal >= wingConfig.getWingPrice()) {
                    CustomWings.getEconomy().withdrawPlayer(player, wingConfig.getWingPrice() );
                    CustomWings.getInstance().getPermissions().playerAdd(null, player, "customwings.wing." + wingConfig.getID().toLowerCase());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',wingConfig.getBuyMessage()));
                } else {
                    player.sendMessage(CustomWings.getInstance().getMessages().cantAffordWingError(wing));
                }
                return true;
            }
            return false;
        } catch (NullPointerException e) {
            return false;
        }


    }

}
