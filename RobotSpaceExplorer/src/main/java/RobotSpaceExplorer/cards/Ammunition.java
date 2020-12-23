package RobotSpaceExplorer.cards;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.actions.AmmunitionAction;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;

public class Ammunition extends AbstractDynamicCard {

    public static final String ID = RobotSpaceExplorerMod.makeID(Ammunition.class.getSimpleName());
    public static final String IMG = makeCardPath("Ammunition.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int ATTACKS_RETURNED = 2;

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
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Ammunition();
    }
}
