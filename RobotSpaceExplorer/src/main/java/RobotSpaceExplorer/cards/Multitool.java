package RobotSpaceExplorer.cards;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;
import RobotSpaceExplorer.powers.MultitoolPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class Multitool extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(Multitool.class.getSimpleName());
    public static final String IMG = makeCardPath("Multitool.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public Multitool() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            Iterator<AbstractCard> i = p.hand.group.iterator();
            AbstractCard c;

            while(i.hasNext()) {
                c = (AbstractCard)i.next();
                if (c.canUpgrade()) {
                    c.upgrade();
                    c.superFlash();
                    c.applyPowers();
                }
            }
        }
        addToBot(new ApplyPowerAction(p, p,
                new MultitoolPower(1), 1));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION +
                             languagePack.getCardStrings(ID).DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Multitool();
    }
}
