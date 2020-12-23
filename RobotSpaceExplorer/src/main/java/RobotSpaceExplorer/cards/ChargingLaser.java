package RobotSpaceExplorer.cards;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeCardPath;

public class ChargingLaser extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = RobotSpaceExplorerMod.makeID(ChargingLaser.class.getSimpleName());
    public static final String IMG = makeCardPath("ChargingLaser.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = RobotSpaceExplorer.Enums.ROBOT_ORANGE;

    private static final int COST = 0;
    // private static final int UPGRADED_COST = 0;

    private static final int DAMAGE = 5;
    // private static final int UPGRADE_PLUS_DMG = 0;
    private static final int DMG_INCREASE = 3;
    private static final int UPGRADE_PLUS_DMG_INCREASE = 3;

    // /STAT DECLARATION/
    private static final int THRESHOLD = 20;

    public ChargingLaser() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = DMG_INCREASE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (damage < THRESHOLD) {
            beamEffect(p, m);
        } else {
            addToBot(new SFXAction("ATTACK_HEAVY"));
            addToBot(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
        }

        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        addToBot(new ModifyDamageAction(uuid, magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DMG_INCREASE);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ChargingLaser();
    }
}
