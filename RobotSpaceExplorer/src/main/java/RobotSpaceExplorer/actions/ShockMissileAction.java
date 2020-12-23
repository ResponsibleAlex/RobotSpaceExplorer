package RobotSpaceExplorer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class ShockMissileAction extends AbstractGameAction {

    private final AbstractPlayer p;
    private final AbstractMonster m;
    private final AbstractCard c;

    public ShockMissileAction(AbstractPlayer player, AbstractMonster monster, AbstractCard card) {
        p = player;
        m = monster;
        c = card;
        actionType = ActionType.DAMAGE;
    }

    public void update() {
        addToBot(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.2F));
        addToBot(new SFXAction("ORB_LIGHTNING_EVOKE"));
        addToBot(new DamageAction(m, new DamageInfo(p, c.damage, c.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -c.magicNumber), -c.magicNumber));

        isDone = true;
    }
}
