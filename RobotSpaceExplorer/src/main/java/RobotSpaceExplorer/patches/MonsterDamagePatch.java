package RobotSpaceExplorer.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch(clz = AbstractMonster.class, method = SpirePatch.CLASS)
public class MonsterDamagePatch {
    public static SpireField<Integer> monsterMultiplier = new SpireField<>(() -> 1);

    @SpirePatch(clz = AbstractMonster.class, method = "setMove",
            paramtypez = {String.class, byte.class, AbstractMonster.Intent.class, int.class, int.class, boolean.class})
    public static class SetMove {
        public static void Postfix(AbstractMonster self, String moveName, byte nextMove, AbstractMonster.Intent intent, int baseDamage, int multiplier, boolean isMultiDamage) {
            monsterMultiplier.set(self, multiplier);
        }
    }

}
