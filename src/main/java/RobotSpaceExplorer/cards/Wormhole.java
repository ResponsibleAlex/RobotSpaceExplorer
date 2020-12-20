package RobotSpaceExplorer.cards;

import RobotSpaceExplorer.actions.WormholeAction;
import RobotSpaceExplorer.patches.relics.BottledGravityPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;

import java.util.Iterator;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;

public class Wormhole extends AbstractDynamicCard {
    private static final CardStrings cardStrings;

    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(Wormhole.class.getSimpleName());
    public static final String IMG = makeCardPath("Wormhole.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    // /STAT DECLARATION/


    public Wormhole() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        purgeOnUse = true;
        tags.add(CardTags.HEALING);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new WormholeAction(this, cardStrings.EXTENDED_DESCRIPTION[1]));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            Iterator i = p.hand.group.iterator();
            canUse = false;

            while(i.hasNext()) {
                AbstractCard c = (AbstractCard) i.next();
                if (canRemove(c)) {
                    canUse = true;
                    break;
                }
            }

            if (!canUse)
                this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return canUse;
        }
    }

    public static boolean canRemove(AbstractCard c) {
        if (c.type == CardType.STATUS
                || c.inBottleFlame || c.inBottleLightning || c.inBottleTornado
                || BottledGravityPatch.inBottledGravity.get(c)
                || c.cardID.equals(AscendersBane.ID) || c.cardID.equals(Necronomicurse.ID)
                || c.cardID.equals(CurseOfTheBell.ID)) {
            return false;
        }
        return true;
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Wormhole();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(Wormhole.ID);
    }
}
