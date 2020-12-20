package RobotSpaceExplorer.actions;

import RobotSpaceExplorer.cards.GlueShot;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class GlueSprayAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private int energyOnUse;
    private boolean upgrade;

    public GlueSprayAction(AbstractPlayer p, boolean upgraded, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.upgrade = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        AbstractCard c = (new GlueShot()).makeCopy();
        if (this.upgrade) {
            c.upgrade();
            c.applyPowers();
        }
        this.addToTop(new MakeTempCardInHandAction(c, effect));

        if (!this.freeToPlayOnce) {
            this.p.energy.use(EnergyPanel.totalCount);
        }

        this.isDone = true;
    }
}
