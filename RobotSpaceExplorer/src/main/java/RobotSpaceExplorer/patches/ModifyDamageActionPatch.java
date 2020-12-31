package RobotSpaceExplorer.patches;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;

import java.util.UUID;

@SuppressWarnings("unused")
@SpirePatch(clz = ModifyDamageAction.class, method = SpirePatch.CLASS)
public class ModifyDamageActionPatch {
    @SpirePatch(clz = ModifyDamageAction.class, method = SpirePatch.CONSTRUCTOR)
    public static class ModifyDamageActionConstructor {
        public static void Postfix(ModifyDamageAction self, UUID targetUUID, int amount) {
            RobotSpaceExplorerMod.modifyAutoloadDamageAction(targetUUID, amount);
        }
    }
}
