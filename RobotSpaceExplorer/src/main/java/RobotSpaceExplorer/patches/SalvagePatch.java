package RobotSpaceExplorer.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;

@SpirePatch(clz = GameActionManager.class, method = SpirePatch.CLASS)
public class SalvagePatch {
    public static final SpireField<Boolean> salvagedThisTurn = new SpireField<>(() -> false);

    @SpirePatch(clz = GameActionManager.class, method = "clear")
    public static class Clear {
        public static void Postfix(GameActionManager self) {
            salvagedThisTurn.set(self, false);
        }
    }
    @SpirePatch(clz = GameActionManager.class, method = "callEndOfTurnActions")
    public static class CallEndOfTurnActions {
        public static void Postfix(GameActionManager self) {
            salvagedThisTurn.set(self, false);
        }
    }
}