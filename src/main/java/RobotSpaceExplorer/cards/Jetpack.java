package RobotSpaceExplorer.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;

public class Jetpack extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(Jetpack.class.getSimpleName());
    public static final String IMG = makeCardPath("Jetpack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 0;
    // private static final int UPGRADED_COST = 0;

    private static final int BLOCK = 3;
    private static final int UPGRADE_PLUS_BLOCK = 1;
    private static final int DEX = 1;
    private static final int UPGRADE_PLUS_DEX = 1;

    // /STAT DECLARATION/


    public Jetpack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = DEX;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, block));
        this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber), magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, magicNumber), magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_PLUS_DEX);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Jetpack();
    }
}
