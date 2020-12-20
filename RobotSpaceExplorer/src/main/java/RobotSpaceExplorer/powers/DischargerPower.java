package RobotSpaceExplorer.powers;

import RobotSpaceExplorer.cards.StaticBuildup;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.util.TextureLoader;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import java.util.Iterator;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makePowerPath;

// Static Buildup cards are now Ethereal. Status cards deal 4 (7) damage to ALL enemies when Exhausted.

public class DischargerPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = RobotSpaceExplorerMod.makeID("DischargerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Discharger84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Discharger32.png"));

    public DischargerPower(final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }

        type = PowerType.BUFF;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();

        // when we deploy this power, set all existing StaticBuildups Ethereal
        setAllStaticBuildupsEthereal();
    }

    private void setAllStaticBuildupsEthereal() {
        AbstractPlayer p = AbstractDungeon.player;

        setGroupStaticBuildupsEthereal(p.drawPile);
        setGroupStaticBuildupsEthereal(p.hand, true);
        setGroupStaticBuildupsEthereal(p.discardPile);
        setGroupStaticBuildupsEthereal(p.exhaustPile);
    }
    private void setGroupStaticBuildupsEthereal(CardGroup g) {
        setGroupStaticBuildupsEthereal(g, false);
    }
    private void setGroupStaticBuildupsEthereal(CardGroup g, boolean flash) {
        Iterator i;
        AbstractCard c;

        i = g.group.iterator();
        while (i.hasNext()) {
            c = (AbstractCard)i.next();
            if (c.cardID == StaticBuildup.ID) {
                ((StaticBuildup) c).setEthereal();
                if (flash) {
                    c.superFlash();
                }
            }
        }
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (card.type == AbstractCard.CardType.STATUS) {
            this.flash();
            this.lightningAllEffect();
            this.addToBot(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
        }
    }

    protected void lightningAllEffect() {
        Iterator i = AbstractDungeon.getMonsters().monsters.iterator();
        AbstractMonster m;

        while(i.hasNext()) {
            m = (AbstractMonster)i.next();
            if (!m.isDeadOrEscaped() && !m.halfDead) {
                this.addToBot(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.0F));
            }
        }

        this.addToBot(new SFXAction("ORB_LIGHTNING_EVOKE"));
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DischargerPower(amount);
    }
}
