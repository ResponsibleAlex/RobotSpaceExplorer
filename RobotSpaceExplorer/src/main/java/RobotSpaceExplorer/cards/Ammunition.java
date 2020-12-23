package RobotSpaceExplorer.cards;

import RobotSpaceExplorer.actions.AmmunitionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;

public class Ammunition extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(Ammunition.class.getSimpleName());
    public static final String IMG = makeCardPath("Ammunition.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int ATTACKS_RETURNED = 2;
    //private static final int UPGRADE_PLUS_ATTACKS_RETURNED = 1;

    // /STAT DECLARATION/


    public Ammunition() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = 0;
        magicNumber = baseMagicNumber = ATTACKS_RETURNED;
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AmmunitionAction(magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            //upgradeMagicNumber(UPGRADE_PLUS_ATTACKS_RETURNED);
            //rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Ammunition();
    }
}
