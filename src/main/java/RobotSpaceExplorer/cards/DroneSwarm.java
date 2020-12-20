package RobotSpaceExplorer.cards;

import RobotSpaceExplorer.actions.DroneSwarmAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class DroneSwarm extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(DroneSwarm.class.getSimpleName());
    public static final String IMG = makeCardPath("DroneSwarm.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = -1;

    // /STAT DECLARATION/


    public DroneSwarm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        //isInnate = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }
        this.addToBot(new DroneSwarmAction(freeToPlayOnce, energyOnUse, upgraded));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isInnate = true;
            rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DroneSwarm();
    }
}
