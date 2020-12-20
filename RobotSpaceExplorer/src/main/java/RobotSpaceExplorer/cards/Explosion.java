package RobotSpaceExplorer.cards;

import RobotSpaceExplorer.actions.ExplosionAction;
import RobotSpaceExplorer.actions.PlayRandomMissileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;

import java.util.ArrayList;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class Explosion extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(Explosion.class.getSimpleName());
    public static final String IMG = makeCardPath("Explosion.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 3;
    // private static final int UPGRADED_COST = 0;

    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 2;

    // /STAT DECLARATION/


    public Explosion() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        cardsToPreview = new Aftershock();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlayRandomMissileAction(upgraded));

        ArrayList<AbstractCard> cards = AbstractDungeon.actionManager.cardsPlayedThisCombat;
        int size = cards.size();
        for (int i = 0; i < size; ++i) {
            AbstractCard c = cards.get(i);
            if (c.exhaust && c.type == AbstractCard.CardType.ATTACK) {
                addToBot(new ExplosionAction(cardsToPreview));
            }
        }

        // extra to account for the random missile
        addToBot(new ExplosionAction(cardsToPreview));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            cardsToPreview.upgrade();
            rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Explosion();
    }
}
