package RobotSpaceExplorer.actions;

import RobotSpaceExplorer.powers.SolarFlarePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CometAction extends AbstractGameAction {
    AbstractPlayer p;
    AbstractMonster m;

    public CometAction(AbstractMonster monster, int solarFlareAmount) {
        this.m = monster;
        this.amount = solarFlareAmount;
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            // apply Solar Flare
            this.addToBot(new ApplyPowerAction(p, p, new SolarFlarePower(amount), amount));
            this.tickDuration();
        } else {
            // trigger Solar Flare
            int damage = amount;
            AbstractPower pow = p.getPower(SolarFlarePower.POWER_ID);
            if (pow != null) {
                damage += pow.amount;
                pow.flash();
            }
            this.addToBot(new DamageAllEnemiesAction((AbstractCreature)null, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));

            // deal damage = Solar Flare to enemy
            //this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));

            this.isDone = true;
        }
    }
}
