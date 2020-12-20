package RobotSpaceExplorer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

public class LaserBurstAction extends AbstractGameAction {

    private AbstractCard card;
    private AbstractPlayer p;

    public LaserBurstAction(AbstractCard card) {
        this.card = card;
        this.p = AbstractDungeon.player;

        this.actionType = ActionType.DAMAGE;
    }

    public void update() {

        if (!Settings.FAST_MODE) {
            this.addToTop(new WaitAction(0.05F));
        }

        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.card.calculateCardDamage((AbstractMonster) this.target);
            this.addToTop(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            this.addToTop(new VFXAction(new SmallLaserEffect(target.hb.cX, target.hb.cY, p.hb.cX, p.hb.cY), 0.0F));

            this.addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn), AttackEffect.NONE));
        }

        this.isDone = true;
    }
}
