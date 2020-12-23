package RobotSpaceExplorer.relics;

import RobotSpaceExplorer.RobotSpaceExplorerMod;
import RobotSpaceExplorer.patches.relics.BottledGravityPatch;
import RobotSpaceExplorer.util.TextureLoader;
import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.Iterator;
import java.util.function.Predicate;

import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicOutlinePath;
import static RobotSpaceExplorer.RobotSpaceExplorerMod.makeRelicPath;

public class BottledGravity extends CustomRelic implements CustomBottleRelic, CustomSavable<Integer> {
     /*
     * Choose a card. It costs 1 less Energy and starts each combat at the bottom of your draw pile..
     */

    private static AbstractCard card;
    private boolean cardSelected = true;

    // ID, images, text.
    public static final String ID = RobotSpaceExplorerMod.makeID("BottledGravity");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BottledGravity.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BottledGravity.png"));

    public BottledGravity() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return BottledGravityPatch.inBottledGravity::get;
    }

    @Override
    public Integer onSave() {
        if (card != null) {
            return AbstractDungeon.player.masterDeck.group.indexOf(card);
        } else {
            return -1;
        }
    }

    @Override
    public void onLoad(Integer cardIndex) {
        if (cardIndex == null) {
            return;
        }
        if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
            card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
            if (card != null) {
                BottledGravityPatch.inBottledGravity.set(card, true);
                setDescriptionAfterLoading();
            }
        }
    }


    @Override
    public void onEquip() { // 1. When we acquire the relic
        cardSelected = false; // 2. Tell the relic that we haven't bottled the card yet
        if (AbstractDungeon.isScreenUp) { // 3. If the map is open - hide it.
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        // 4. Set the room to INCOMPLETE - don't allow us to use the map, etc.
        CardGroup group = getEligibleCards(); // 5. Get a card group of all currently unbottled cards
        AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[2] + name + DESCRIPTIONS[3], false, false, false, false);
        // 6. Open the grid selection screen with the cards from the CardGroup we specified above. The description reads "Select a card to bottle for" + (relic name) + "."
    }

    // we can't select a curse, a previously bottled card, or a card that costs X or 0
    private CardGroup getEligibleCards() {
        CardGroup cards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CardGroup unbottled = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck);

        Iterator<AbstractCard> i = unbottled.group.iterator();
        AbstractCard c;
        while (i.hasNext()) {
            c = (AbstractCard) i.next();
            if (c.type != AbstractCard.CardType.CURSE &&
                    c.cost > 0) {
                cards.group.add(c);
            }
        }

        return cards;
    }

    @Override
    public boolean canSpawn() {
        return getEligibleCards().group.size() > 0;
    }

    @Override
    public void onUnequip() { // 1. On unequip
        if (card != null) { // If the bottled card exists (prevents the game from crashing if we removed the bottled card from our deck for example.)
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card); // 2. Get the card
            if (cardInDeck != null) {
                BottledGravityPatch.inBottledGravity.set(cardInDeck, false); // In our SpireField - set the card to no longer be bottled. (Unbottle it)
            }
        }
    }

    @Override
    public void update() {
        super.update(); //Do all of the original update() method in AbstractRelic

        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            // If the card hasn't been bottled yet and we have cards selected in the gridSelectScreen (from onEquip)
            cardSelected = true; //Set the cardSelected boolean to be true - we're about to bottle the card.
            card = AbstractDungeon.gridSelectScreen.selectedCards.get(0); // The custom Savable "card" is going to equal
            // The card from the selection screen (it's only 1, so it's at index 0)
            BottledGravityPatch.inBottledGravity.set(card, true); // Use our custom spire field to set that card to be bottled.
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.INCOMPLETE) {
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE; // The room phase can now be set to complete (From INCOMPLETE in onEquip)
            AbstractDungeon.gridSelectScreen.selectedCards.clear(); // Always clear your grid screen after using it.
            setDescriptionAfterLoading(); // Set the description to reflect the bottled card (the method is at the bottom of this file)
        }
    }

    // set cost of the card
    @Override
    public void atBattleStartPreDraw() {
        AbstractCard c;
        for (AbstractCard abstractCard : AbstractDungeon.player.drawPile.group) {
            c = abstractCard;
            if (BottledGravityPatch.inBottledGravity.get(c)) {
                if (c.cost > 0) {
                    c.cost = c.cost - 1;
                    c.setCostForTurn(c.cost);
                }
            }
        }
    }

    // Change description after relic is already loaded to reflect the bottled card.
    public void setDescriptionAfterLoading() {
        description = FontHelper.colorString(card.name, "y") + DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    // Standard description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BottledGravity();
    }
}
