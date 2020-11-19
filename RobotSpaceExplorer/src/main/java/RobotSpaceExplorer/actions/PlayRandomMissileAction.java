package RobotSpaceExplorer.actions;

import RobotSpaceExplorer.cards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlayRandomMissileAction extends AbstractGameAction {

    private static CardGroup missileGroup = null;
    private boolean upgradeMissile;

    public PlayRandomMissileAction(boolean upgradeMissile) {
        this.upgradeMissile = upgradeMissile;
        initMissiles();

        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            playRandomMissile();
        }

        this.tickDuration();
    }

    // BigMissile, FrostMissile, LavaMissile, ShockMissile, SlimeMissile
    private void initMissiles() {
        if (missileGroup == null) {
            missileGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            missileGroup.addToTop(new BigMissile());
            missileGroup.addToTop(new FrostMissile());
            //missileGroup.addToTop(new LavaMissile());
            missileGroup.addToTop(new ShockMissile());
            missileGroup.addToTop(new SlimeMissile());
            missileGroup.addToTop(new PlasmaMissile());
            missileGroup.addToTop(new ScrapMissile());
        }
    }

    private void playRandomMissile() {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        AbstractCard card = missileGroup.getRandomCard(true);
        AbstractCard tmp = card.makeSameInstanceOf();
        if (upgradeMissile) {
            tmp.upgrade();
        }
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        tmp.target_y = (float)Settings.HEIGHT / 2.0F;
        if (m != null) {
            tmp.calculateCardDamage(m);
        }

        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
    }
}
