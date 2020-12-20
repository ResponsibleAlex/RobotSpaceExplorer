package RobotSpaceExplorer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlayExhaustedAttackAction extends AbstractGameAction {
    private AbstractPlayer p = AbstractDungeon.player;
    private AbstractCard card;

    public PlayExhaustedAttackAction(AbstractCard exhaustedAttack) {
        card = exhaustedAttack;
        init();
    }
    public PlayExhaustedAttackAction() {
        CardGroup pile = p.exhaustPile.getAttacks();
        if (pile.size() > 0) {
            card = pile.getRandomCard(true);
        }
        init();
    }
    private void init() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (card != null) {
            playCard();
        }
        this.isDone = true;
    }

    private void playCard() {
        AbstractCard tmp = card.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        tmp.target_y = (float)Settings.HEIGHT / 2.0F;
        tmp.purgeOnUse = true;

        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, true, card.energyOnUse, true, true), true);
    }
}
