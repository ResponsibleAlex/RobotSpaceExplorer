package RobotSpaceExplorer.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import java.util.Iterator;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractDynamicCard extends AbstractDefaultCard {

    public AbstractDynamicCard(final String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

    }

    protected void beamEffect(AbstractPlayer p, AbstractMonster m) {
        beamEffect(p, m, 0.0F);
    }
    protected void beamEffect(AbstractPlayer p, AbstractMonster m, float duration) {
        addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
        addToBot(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY), duration));
    }

    protected void lightningEffect(AbstractMonster m) {
        addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.0F));
        addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
    }
    protected void lightningEffect(AbstractMonster m, float duration) {
        addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY), duration));
        addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
    }

    protected void lightningAllEffect() {
        Iterator i = AbstractDungeon.getMonsters().monsters.iterator();
        AbstractMonster m;

        while(i.hasNext()) {
            m = (AbstractMonster)i.next();
            if (!m.isDeadOrEscaped() && !m.halfDead) {
                addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.0F));
            }
        }

        addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
    }

    public static boolean shouldGlow(AbstractCard c) {
        if (c.type == AbstractCard.CardType.STATUS && c.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Medical Kit")) {
            return false;
        } else if (c.type == AbstractCard.CardType.CURSE && c.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Blue Candle")) {
            return false;
        } else {
            return c.hasEnoughEnergy();
        }
    }
}