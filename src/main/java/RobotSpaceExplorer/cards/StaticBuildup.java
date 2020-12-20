package RobotSpaceExplorer.cards;

import RobotSpaceExplorer.powers.DischargerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;

public class StaticBuildup extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(StaticBuildup.class.getSimpleName());
    public static final String IMG = makeCardPath("StaticBuildup.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String ETHEREAL_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.STATUS;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;
    private static final int DAMAGE = 1;
    private static final int WEAK = 1;

    // /STAT DECLARATION/


    public StaticBuildup() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = DAMAGE;
        magicNumber = baseMagicNumber = WEAK;

        // if we have Discharger power, make this Ethereal then update description
        if (AbstractDungeon.player != null &&
                AbstractDungeon.player.hasPower(DischargerPower.POWER_ID)) {
            setEthereal();
        } else {
            initializeDescription();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING));
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, this.magicNumber, true), this.magicNumber));
        }
    }

    public void triggerWhenDrawn() {
        this.addToBot(new SetDontTriggerAction(this, false));
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    public void upgrade() {
    }

    public void setEthereal() {
        this.isEthereal = true;
        // Ethereal doesn't actually work here because the card
        // autoplays itself, so set exhaust = true
        this.exhaust = true;
        this.rawDescription = ETHEREAL_DESCRIPTION;
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new StaticBuildup();
    }
}
