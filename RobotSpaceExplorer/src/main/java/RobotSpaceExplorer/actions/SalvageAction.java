package RobotSpaceExplorer.actions;

import RobotSpaceExplorer.cards.LuckyStrike;
import RobotSpaceExplorer.patches.SalvagePatch;
import RobotSpaceExplorer.powers.ScannerPower;
import RobotSpaceExplorer.relics.SearchSpecs;
import RobotSpaceExplorer.relics.ToughPlating;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

import static RobotSpaceExplorer.cards.AbstractDynamicCard.shouldGlow;

public class SalvageAction extends AbstractGameAction {

    private int cardsToSalvage;
    private boolean upgradeCards = false;
    private boolean hasToughPlating = false;
    private AbstractRelic toughPlating = null;
    private static final int TOUGH_PLATING_BLOCK = 2;
    private boolean hasSearchSpecs = false;
    private AbstractRelic searchSpecs = null;
    private boolean hasScanner;
    private AbstractPlayer p;

    public SalvageAction(int numberOfCardsToSalvage) {
        actionType = ActionType.CARD_MANIPULATION;
        cardsToSalvage = numberOfCardsToSalvage;
        init();
    }

    public SalvageAction(int numberOfCardsToSalvage, boolean upgradeSalvageCards) {
        actionType = ActionType.CARD_MANIPULATION;
        cardsToSalvage = numberOfCardsToSalvage;
        upgradeCards = upgradeSalvageCards;
        init();
    }

    private void init() {
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;

        for (AbstractRelic r : p.relics) {
            if (r.relicId == ToughPlating.ID) {
                hasToughPlating = true;
                toughPlating = r;
            }
            if (r.relicId == SearchSpecs.ID) {
                hasSearchSpecs = true;
                searchSpecs = r;
            }
        }

        hasScanner = p.hasPower(ScannerPower.POWER_ID);
    }

    public void update() {
        CardGroup pile = p.discardPile;
        CardGroup hand = p.hand;
        int count = 0;
        boolean salvagedThisTurn;

        for (int i = 0; i < cardsToSalvage; i++) {
            if (hand.size() == 10) {
                p.createHandIsFullDialog();
                break;
            } else if (!pile.isEmpty()) {

                // perform the salvage action
                count++;
                AbstractCard c = getRandomSalvage(pile);
                hand.addToHand(c);
                pile.removeCard(c);
                c.lighten(false);
                c.unhover();
                c.applyPowers();

                if (upgradeCards) {
                    if (c.canUpgrade() && !c.upgraded) {
                        c.upgrade();
                        c.superFlash();
                    }
                }

                if (shouldGlow(c)) {
                    c.beginGlowing();
                }
                p.hand.refreshHandLayout();

                // if this is the first time we salvaged a card this turn
                salvagedThisTurn = SalvagePatch.salvagedThisTurn.get(AbstractDungeon.actionManager);
                if (!salvagedThisTurn) {
                    // if we have search specs, gain [E]
                    if (hasSearchSpecs) {
                        addToBot(new RelicAboveCreatureAction(p, searchSpecs));
                        addToBot(new GainEnergyAction(1));
                    }

                    // set "salvagedThisTurn" to true
                    SalvagePatch.salvagedThisTurn.set(AbstractDungeon.actionManager, true);
                }

                // gain block for each card salvaged if we have the Tough Plating relic
                if (hasToughPlating) {
                    addToBot(new RelicAboveCreatureAction(p, toughPlating));
                    addToBot(new GainBlockAction(p, p, TOUGH_PLATING_BLOCK));
                }

                tickDuration();
            }
        }

        // if we salvaged at least one card, autoplay any Lucky Strikes
        // and refresh hand layout
        if (count > 0) {
            Iterator<AbstractCard> i = p.drawPile.group.iterator();
            AbstractCard c;
            while (i.hasNext()) {
                c = i.next();
                if (c.cardID == LuckyStrike.ID) {
                    i.remove();
                    playLuckyStrike(c);
                }
            }

            hand.refreshHandLayout();
        }

        isDone = true;
    }

    private AbstractCard getRandomSalvage(CardGroup pile) {
        AbstractCard c;
        
        if (hasScanner) {
            c = pile.getRandomCard(AbstractDungeon.cardRandomRng, AbstractCard.CardRarity.RARE);
            if (c == null) {
                c = pile.getRandomCard(AbstractDungeon.cardRandomRng, AbstractCard.CardRarity.UNCOMMON);
                if (c == null) {
                    c = pile.getRandomCard(AbstractDungeon.cardRandomRng, AbstractCard.CardRarity.COMMON);
                    if (c == null) {
                        c = pile.getRandomCard(AbstractDungeon.cardRandomRng);
                    }
                }
            }
        } else {
            c = pile.getRandomCard(true);
        }
        
        return c;
    }

    private void playLuckyStrike(AbstractCard c) {
        AbstractDungeon.player.drawPile.group.remove(c);
        AbstractDungeon.getCurrRoom().souls.remove(c);

        addToTop(new NewQueueCardAction(c, AbstractDungeon.getRandomMonster(),
                false, true));

        if (!Settings.FAST_MODE) {
            addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        } else {
            addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }
    }
}
