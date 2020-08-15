package tigeax.customwings.wingpurchase;

import me.realized.tokenmanager.TokenManagerPlugin;
import org.bukkit.entity.Player;

public class BuyTokenManager {

    public static boolean makePayment(Integer price, Player player) {

        TokenManagerPlugin tm = TokenManagerPlugin.getInstance();

        if (tm.getTokens(player).isPresent() && tm.getTokens(player).getAsLong() >= price) {
            tm.removeTokens(player, price);
            return true;
        } else {
            return false;
        }

    }

}
