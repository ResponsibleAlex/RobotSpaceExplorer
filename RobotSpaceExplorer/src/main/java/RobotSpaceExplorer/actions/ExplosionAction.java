package RobotSpaceExplorer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class ExplosionAction extends AbstractGameAction {
    private AbstractCard aftershock;

    public ExplosionAction(AbstractCard cardToPlay) {
        aftershock = cardToPlay;

        duration = Settings.ACTION_DUR_FAST;
        actionType = ActionType.SPECIAL;
    }

    public void update() {
        AbstractCard tmp = aftershock.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = AbstractDungeon.player.hb_x;
        tmp.current_y = AbstractDungeon.player.hb_y;
        tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        tmp.target_y = (float) Settings.HEIGHT / 2.0F;

        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, true, 0, true, true), false);

        isDone = true;
    }
}
