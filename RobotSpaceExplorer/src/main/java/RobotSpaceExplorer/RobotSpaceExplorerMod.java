package RobotSpaceExplorer;

import RobotSpaceExplorer.potions.LiquidFusion;
import RobotSpaceExplorer.potions.PlasmaFlask;
import RobotSpaceExplorer.potions.RecyclePotion;
import RobotSpaceExplorer.relics.*;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import RobotSpaceExplorer.cards.*;
import RobotSpaceExplorer.characters.RobotSpaceExplorer;
import RobotSpaceExplorer.util.IDCheckDontTouchPls;
import RobotSpaceExplorer.util.TextureLoader;
import RobotSpaceExplorer.variables.DefaultCustomVariable;
import RobotSpaceExplorer.variables.DefaultSecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "theDefault" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 3 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theDefault". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class RobotSpaceExplorerMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(RobotSpaceExplorerMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Robot Space Explorer";
    private static final String AUTHOR = "ResponsibleAlex";
    private static final String DESCRIPTION = "The Robot Space Explorer playable character.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color ROBOT_ORANGE = CardHelper.getColor(235.0f, 94.0f, 0.0f);
    
    // Potion Colors in RGB
    public static final Color POTION_BLUEWHITE = CardHelper.getColor(230.0f, 230.0f, 255.0f); // Near White
    public static final Color POTION_DARKREDBROWN = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
    
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
  
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "RobotSpaceExplorerResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "RobotSpaceExplorerResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "RobotSpaceExplorerResources/images/512/bg_power_default_gray.png";
    
    private static final String ENERGY_ORB_DEFAULT_GRAY = "RobotSpaceExplorerResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "RobotSpaceExplorerResources/images/512/card_small_orb.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "RobotSpaceExplorerResources/images/1024/card_default_gray_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "RobotSpaceExplorerResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "RobotSpaceExplorerResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "RobotSpaceExplorerResources/images/1024/bg_power_default_gray.png";
    
    // Character assets
    private static final String THE_DEFAULT_BUTTON = "RobotSpaceExplorerResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "RobotSpaceExplorerResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "RobotSpaceExplorerResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "RobotSpaceExplorerResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_IMAGE = "RobotSpaceExplorerResources/images/char/defaultCharacter/main.png";
    public static final String THE_DEFAULT_CORPSE = "RobotSpaceExplorerResources/images/char/defaultCharacter/corpse.png";

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "RobotSpaceExplorerResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "RobotSpaceExplorerResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "RobotSpaceExplorerResources/images/char/defaultCharacter/skeleton.json";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeEffectPath(String resourcePath) {
        return getModID() + "Resources/images/vfx/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================
    
    public RobotSpaceExplorerMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */
      
        setModID("RobotSpaceExplorer");
        // cool
        // TODO: NOW READ THIS!!!!!!!!!!!!!!!:
        
        // 1. Go to your resources folder in the project panel, and refactor> rename theDefaultResources to
        // yourModIDResources.
        
        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project)
        // replace all instances of theDefault with yourModID.
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        
        // 3. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theDefault. They get loaded before getID is a thing.
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + RobotSpaceExplorer.Enums.ROBOT_ORANGE.toString());
        
        BaseMod.addColor(RobotSpaceExplorer.Enums.ROBOT_ORANGE, ROBOT_ORANGE, ROBOT_ORANGE, ROBOT_ORANGE,
                ROBOT_ORANGE, ROBOT_ORANGE, ROBOT_ORANGE, ROBOT_ORANGE,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("RobotSpaceExplorer", "RobotSpaceExplorerConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = RobotSpaceExplorerMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = RobotSpaceExplorerMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = RobotSpaceExplorerMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        RobotSpaceExplorerMod defaultmod = new RobotSpaceExplorerMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + RobotSpaceExplorer.Enums.RobotSpaceExplorer.toString());
        
        BaseMod.addCharacter(new RobotSpaceExplorer("Robot Space Explorer", RobotSpaceExplorer.Enums.RobotSpaceExplorer),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, RobotSpaceExplorer.Enums.RobotSpaceExplorer);
        
        receiveEditPotions();
        logger.info("Added " + RobotSpaceExplorer.Enums.RobotSpaceExplorer.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        /*
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================
        
        // No events planned for this mod
        // BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
        */
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(RecyclePotion.class, ROBOT_ORANGE, ROBOT_ORANGE, POTION_BLUEWHITE,
                RecyclePotion.POTION_ID, RobotSpaceExplorer.Enums.RobotSpaceExplorer);
        BaseMod.addPotion(PlasmaFlask.class, POTION_BLUEWHITE, POTION_BLUEWHITE, POTION_BLUEWHITE,
                PlasmaFlask.POTION_ID, RobotSpaceExplorer.Enums.RobotSpaceExplorer);
        BaseMod.addPotion(LiquidFusion.class, ROBOT_ORANGE, POTION_BLUEWHITE, POTION_BLUEWHITE,
                LiquidFusion.POTION_ID, RobotSpaceExplorer.Enums.RobotSpaceExplorer);

        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        
        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new RoboCore(), RobotSpaceExplorer.Enums.ROBOT_ORANGE);
        BaseMod.addRelicToCustomPool(new Reprocessor(), RobotSpaceExplorer.Enums.ROBOT_ORANGE);
        BaseMod.addRelicToCustomPool(new SearchSpecs(), RobotSpaceExplorer.Enums.ROBOT_ORANGE);
        BaseMod.addRelicToCustomPool(new TurboCore(), RobotSpaceExplorer.Enums.ROBOT_ORANGE);
        BaseMod.addRelicToCustomPool(new ClockworkSextant(), RobotSpaceExplorer.Enums.ROBOT_ORANGE);
        BaseMod.addRelicToCustomPool(new FoglandsKnife(), RobotSpaceExplorer.Enums.ROBOT_ORANGE);
        BaseMod.addRelicToCustomPool(new PlasmaHourglass(), RobotSpaceExplorer.Enums.ROBOT_ORANGE);
        BaseMod.addRelicToCustomPool(new BottledGravity(), RobotSpaceExplorer.Enums.ROBOT_ORANGE);
        BaseMod.addRelicToCustomPool(new ToughPlating(), RobotSpaceExplorer.Enums.ROBOT_ORANGE);
        BaseMod.addRelicToCustomPool(new RingOfTheNewt(), RobotSpaceExplorer.Enums.ROBOT_ORANGE);
        BaseMod.addRelicToCustomPool(new SlideRule(), RobotSpaceExplorer.Enums.ROBOT_ORANGE);

        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
        UnlockTracker.markRelicAsSeen(RoboCore.ID);
        UnlockTracker.markRelicAsSeen(Reprocessor.ID);
        UnlockTracker.markRelicAsSeen(SearchSpecs.ID);
        UnlockTracker.markRelicAsSeen(TurboCore.ID);
        UnlockTracker.markRelicAsSeen(ClockworkSextant.ID);
        UnlockTracker.markRelicAsSeen(FoglandsKnife.ID);
        UnlockTracker.markRelicAsSeen(PlasmaHourglass.ID);
        UnlockTracker.markRelicAsSeen(BottledGravity.ID);
        UnlockTracker.markRelicAsSeen(ToughPlating.ID);
        UnlockTracker.markRelicAsSeen(RingOfTheNewt.ID);
        UnlockTracker.markRelicAsSeen(SlideRule.ID);
        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        logger.info("Adding cards");
        // Add the cards
        // Don't comment out/delete these cards (yet). You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new Probe());
        BaseMod.addCard(new Scan());
        BaseMod.addCard(new LuckyFind());
        BaseMod.addCard(new MiniLasers());
        BaseMod.addCard(new SurgingStrike());
        BaseMod.addCard(new LaserBurst());
        //BaseMod.addCard(new LavaMissile());
        BaseMod.addCard(new FrostMissile());
        BaseMod.addCard(new ScrapMissile());
        BaseMod.addCard(new ImprovisedAttack());
        BaseMod.addCard(new ShockKick());
        BaseMod.addCard(new MetalStrike());
        BaseMod.addCard(new ShovelStrike());
        BaseMod.addCard(new ScrapToss());
        BaseMod.addCard(new StunGrenade());
        BaseMod.addCard(new SlimeMissile());
        BaseMod.addCard(new PlasmaMissile());
        BaseMod.addCard(new LuckyStrike());
        BaseMod.addCard(new StarLightning());
        BaseMod.addCard(new Electropulse());
        BaseMod.addCard(new BigMissile());
        BaseMod.addCard(new Blitz());
        BaseMod.addCard(new MakeshiftVolley());
        BaseMod.addCard(new ChargingLaser());
        BaseMod.addCard(new ShockMissile());
        BaseMod.addCard(new Asteroid());
        BaseMod.addCard(new Blaster());
        BaseMod.addCard(new HighVoltage());
        BaseMod.addCard(new Explosion());
        BaseMod.addCard(new MiniDrones());
        BaseMod.addCard(new SwiftStep());
        BaseMod.addCard(new Pulsar());
        BaseMod.addCard(new Counterblock());
        BaseMod.addCard(new FranticSearch());
        BaseMod.addCard(new GlueShot());
        BaseMod.addCard(new Ammunition());
        BaseMod.addCard(new Jetpack());
        BaseMod.addCard(new JunkDive());
        BaseMod.addCard(new Reload());
        BaseMod.addCard(new Fix());
        BaseMod.addCard(new TurboDodge());
        BaseMod.addCard(new Smokescreen());
        BaseMod.addCard(new DoubleBlock());
        BaseMod.addCard(new IronShell());
        BaseMod.addCard(new DoubleFind());
        BaseMod.addCard(new RigExplosive());
        BaseMod.addCard(new GearShield());
        BaseMod.addCard(new Electroshield());
        BaseMod.addCard(new Calculate());
        BaseMod.addCard(new SelfCleaning());
        BaseMod.addCard(new FreezingShield());
        BaseMod.addCard(new ToxicBomb());
        BaseMod.addCard(new GlueSpray());
        BaseMod.addCard(new Comet());
        BaseMod.addCard(new SurgingShield());
        BaseMod.addCard(new ScrapShield());
        BaseMod.addCard(new Exploration());
        BaseMod.addCard(new Arsenal());
        BaseMod.addCard(new Wormhole());
        BaseMod.addCard(new BlastShield());
        BaseMod.addCard(new MiniBoosters());
        BaseMod.addCard(new ExhaustFan());
        BaseMod.addCard(new DualCore());
        BaseMod.addCard(new PowerGlove());
        BaseMod.addCard(new StarDust());
        BaseMod.addCard(new FreezeRay());
        BaseMod.addCard(new Discharger());
        BaseMod.addCard(new PowerCollector());
        BaseMod.addCard(new Autoloader());
        BaseMod.addCard(new Accelerator());
        BaseMod.addCard(new Multitool());
        BaseMod.addCard(new DroneSwarm());
        BaseMod.addCard(new TrailblazerForm());
        BaseMod.addCard(new Scanner());
        BaseMod.addCard(new StaticBuildup());
        BaseMod.addCard(new Aftershock());

        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        // This is so that they are all "seen" in the library, for people who like to look at the card list
        // before playing your mod.
        UnlockTracker.unlockCard(Strike.ID);
        UnlockTracker.unlockCard(Defend.ID);
        UnlockTracker.unlockCard(Probe.ID);
        UnlockTracker.unlockCard(Scan.ID);
        UnlockTracker.unlockCard(LuckyFind.ID);
        UnlockTracker.unlockCard(MiniLasers.ID);
        UnlockTracker.unlockCard(SurgingStrike.ID);
        UnlockTracker.unlockCard(LaserBurst.ID);
        //UnlockTracker.unlockCard(LavaMissile.ID);
        UnlockTracker.unlockCard(FrostMissile.ID);
        UnlockTracker.unlockCard(ScrapMissile.ID);
        UnlockTracker.unlockCard(ImprovisedAttack.ID);
        UnlockTracker.unlockCard(ShockKick.ID);
        UnlockTracker.unlockCard(MetalStrike.ID);
        UnlockTracker.unlockCard(ShovelStrike.ID);
        UnlockTracker.unlockCard(ScrapToss.ID);
        UnlockTracker.unlockCard(StunGrenade.ID);
        UnlockTracker.unlockCard(SlimeMissile.ID);
        UnlockTracker.unlockCard(PlasmaMissile.ID);
        UnlockTracker.unlockCard(LuckyStrike.ID);
        UnlockTracker.unlockCard(StarLightning.ID);
        UnlockTracker.unlockCard(Electropulse.ID);
        UnlockTracker.unlockCard(BigMissile.ID);
        UnlockTracker.unlockCard(Blitz.ID);
        UnlockTracker.unlockCard(MakeshiftVolley.ID);
        UnlockTracker.unlockCard(ChargingLaser.ID);
        UnlockTracker.unlockCard(ShockMissile.ID);
        UnlockTracker.unlockCard(Asteroid.ID);
        UnlockTracker.unlockCard(Blaster.ID);
        UnlockTracker.unlockCard(HighVoltage.ID);
        UnlockTracker.unlockCard(Explosion.ID);
        UnlockTracker.unlockCard(MiniDrones.ID);
        UnlockTracker.unlockCard(SwiftStep.ID);
        UnlockTracker.unlockCard(Pulsar.ID);
        UnlockTracker.unlockCard(Counterblock.ID);
        UnlockTracker.unlockCard(FranticSearch.ID);
        UnlockTracker.unlockCard(GlueShot.ID);
        UnlockTracker.unlockCard(Ammunition.ID);
        UnlockTracker.unlockCard(Jetpack.ID);
        UnlockTracker.unlockCard(JunkDive.ID);
        UnlockTracker.unlockCard(Reload.ID);
        UnlockTracker.unlockCard(Fix.ID);
        UnlockTracker.unlockCard(TurboDodge.ID);
        UnlockTracker.unlockCard(Smokescreen.ID);
        UnlockTracker.unlockCard(DoubleBlock.ID);
        UnlockTracker.unlockCard(IronShell.ID);
        UnlockTracker.unlockCard(DoubleFind.ID);
        UnlockTracker.unlockCard(RigExplosive.ID);
        UnlockTracker.unlockCard(GearShield.ID);
        UnlockTracker.unlockCard(Electroshield.ID);
        UnlockTracker.unlockCard(Calculate.ID);
        UnlockTracker.unlockCard(SelfCleaning.ID);
        UnlockTracker.unlockCard(FreezingShield.ID);
        UnlockTracker.unlockCard(ToxicBomb.ID);
        UnlockTracker.unlockCard(GlueSpray.ID);
        UnlockTracker.unlockCard(Comet.ID);
        UnlockTracker.unlockCard(SurgingShield.ID);
        UnlockTracker.unlockCard(ScrapShield.ID);
        UnlockTracker.unlockCard(Exploration.ID);
        UnlockTracker.unlockCard(Arsenal.ID);
        UnlockTracker.unlockCard(Wormhole.ID);
        UnlockTracker.unlockCard(BlastShield.ID);
        UnlockTracker.unlockCard(MiniBoosters.ID);
        UnlockTracker.unlockCard(ExhaustFan.ID);
        UnlockTracker.unlockCard(DualCore.ID);
        UnlockTracker.unlockCard(PowerGlove.ID);
        UnlockTracker.unlockCard(StarDust.ID);
        UnlockTracker.unlockCard(FreezeRay.ID);
        UnlockTracker.unlockCard(Discharger.ID);
        UnlockTracker.unlockCard(PowerCollector.ID);
        UnlockTracker.unlockCard(Autoloader.ID);
        UnlockTracker.unlockCard(Accelerator.ID);
        UnlockTracker.unlockCard(Multitool.ID);
        UnlockTracker.unlockCard(DroneSwarm.ID);
        UnlockTracker.unlockCard(TrailblazerForm.ID);
        UnlockTracker.unlockCard(Scanner.ID);
        UnlockTracker.unlockCard(StaticBuildup.ID);
        UnlockTracker.unlockCard(Aftershock.ID);

        logger.info("Done adding cards!");
    }
    
    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Character-Strings.json");
        

        logger.info("Done editing strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================
    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/DefaultMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
