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

    private final AbstractCard card;
    private final AbstractPlayer p;

    public LaserBurstAction(AbstractCard card) {
        this.card = card;
        p = AbstractDungeon.player;

        actionType = ActionType.DAMAGE;
    }

    public void update() {

        if (!Settings.FAST_MODE) {
            addToTop(new WaitAction(0.05F));
        }

        target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (target != null) {
            card.calculateCardDamage((AbstractMonster) target);
            addToTop(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            addToTop(new VFXAction(new SmallLaserEffect(target.hb.cX, target.hb.cY, p.hb.cX, p.hb.cY), 0.0F));

            addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn), AttackEffect.NONE));
        }

        isDone = true;
    }
}
