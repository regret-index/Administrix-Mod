package administrix;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import administrix.cards.AbstractAdministrixCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.google.gson.Gson;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import administrix.cards.starter.*;
import administrix.cards.common.attack.*;
import administrix.cards.common.skill.*;
import administrix.cards.uncommon.attack.*;
import administrix.cards.uncommon.skill.*;
import administrix.cards.uncommon.power.*;
import administrix.cards.rare.attack.*;
import administrix.cards.rare.skill.*;
import administrix.cards.rare.power.*;
import administrix.potions.*;
import administrix.characters.Administrix;
import administrix.patches.AbstractCardEnum;
import administrix.patches.CharacterEnum;
import administrix.relics.*;

@SpireInitializer
public class AdministrixMod implements PostInitializeSubscriber,
        EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber,
        EditStringsSubscriber, EditKeywordsSubscriber {

    private static final Color ADMIN_GOLD = CardHelper.getColor(250.0f, 205.0f, 25.0f);

    private static Map<String, Keyword> keywords;

    // Misc stuff
    private static final String Dev = "regret-index";

    public static final String RESOURCE_PATH = "administrix/";
    public static final String IMG_PATH = RESOURCE_PATH + "admin_images/";
    public static final String LOCALIZATION_PATH = RESOURCE_PATH + "admin_text/";
    public static final String CARD_STRINGS = "AdministrixMod-CardStrings";
    public static final String POWER_STRINGS = "AdministrixMod-PowerStrings";
    public static final String RELIC_STRINGS = "AdministrixMod-RelicStrings";
    public static final String KEYWORD_STRINGS = "AdministrixMod-KeywordStrings";
    public static final String UI_STRINGS = "AdministrixMod-UIStrings";
    public static final String POTION_STRINGS = "AdministrixMod-PotionStrings";
    public static final String CHARACTER_STRINGS = "AdministrixMod-CharacterStrings";

    // Asset backgrounds
    private static final String ATTACK_LICH_GOLD = IMG_PATH + "512/bg_attack_admin_gold_512.png";
    private static final String SKILL_LICH_GOLD = IMG_PATH + "512/bg_skill_admin_gold_512.png";
    private static final String POWER_LICH_GOLD = IMG_PATH + "512/bg_power_admin_gold_512.png";
    private static final String ENERGY_ORB_LICH_GOLD = IMG_PATH + "512/card_admin_gold_orb_512.png";
    public static final String ENERGY_ORB_LICH_GOLD_PLOT = IMG_PATH + "512/card_admin_gold_orb_plot_512.png";

    private static final String ATTACK_LICH_GOLD_PORTRAIT = IMG_PATH + "1024/bg_attack_admin_gold_1024.png";
    private static final String SKILL_LICH_GOLD_PORTRAIT = IMG_PATH + "1024/bg_skill_admin_gold_1024.png";
    private static final String POWER_LICH_GOLD_PORTRAIT = IMG_PATH + "1024/bg_power_admin_gold_1024.png";
    public static final String ENERGY_ORB_LICH_GOLD_PORTRAIT = IMG_PATH + "1024/card_admin_gold_orb_1024.png";

    public static final String ATTACK_LICH_GOLD_PLOT = IMG_PATH + "512/bg_attack_admin_plot_512.png";
    public static final String SKILL_LICH_GOLD_PLOT = IMG_PATH + "512/bg_skill_admin_plot_512.png";
    public static final String POWER_LICH_GOLD_PLOT = IMG_PATH + "512/bg_power_admin_plot_512.png";
    public static final String UNCOMMON_BANNER_LICH_GOLD_PLOT = IMG_PATH + "512/banner_uncommon_plot_512";
    public static final String UNCOMMON_BANNER_PORTRAIT = IMG_PATH + "1024/banner_uncommon_plot_1024";

    public static final String ATTACK_LICH_GOLD_PLOT_PORTRAIT = IMG_PATH + "1024/bg_attack_admin_plot_1024.png";
    public static final String SKILL_LICH_GOLD_PLOT_PORTRAIT = IMG_PATH + "1024/bg_skill_admin_plot_1024.png";
    public static final String POWER_LICH_GOLD_PLOT_PORTRAIT = IMG_PATH + "1024/bg_power_admin_plot_1024.png";

    // Miko cart art.
    public static final String CARD_IMG_PATH = IMG_PATH + "cards/";
    public static final String PLACE_HOLDER_CARD_PATH = "corona.png";

    public static final String STRIKE_AX = CARD_IMG_PATH + "Strike.png";
    public static final String DEFEND_AX = CARD_IMG_PATH + "Defend.png";
    public static final String RED_CLOAK = CARD_IMG_PATH + "Red Cloak.png";
    public static final String BLUE_CLOAK = CARD_IMG_PATH + "Blue Cloak.png";
    public static final String DAYBREAK = CARD_IMG_PATH + "Daybreak.png";
    public static final String NIGHTFALL = CARD_IMG_PATH + "Nightfall.png";

    public static final String CRACKING_PANGU = CARD_IMG_PATH + "Cracking Pangu.png";
    public static final String DIVERSION_TACTIC = CARD_IMG_PATH + "Diversion Tactic.png";
    public static final String EXTOL_VIRTUE = CARD_IMG_PATH + "Extol Virtue.png";
    public static final String IMMORTAL_GRACE = CARD_IMG_PATH + "Immortal Grace.png";
    public static final String NOBLE_PATH = CARD_IMG_PATH + "Noble Path.png";
    public static final String PASSAGE_OF_AGES = CARD_IMG_PATH + "Passage of Ages.png";
    public static final String PLATE_OF_ANTIQUITY = CARD_IMG_PATH + "Plate of Antiquity.png";
    public static final String REBUKE_DESIRES = CARD_IMG_PATH + "Rebuke Desires.png";
    public static final String REGENT_EDICT = CARD_IMG_PATH + "Regent's Edict.png";
    public static final String SHADOW_PLAY = CARD_IMG_PATH + "Shadow Play.png";
    public static final String TRICK_OF_THE_LIGHT = CARD_IMG_PATH + "Trick of the Light.png";

    public static final String CENTURIES_ASCENT = CARD_IMG_PATH + "Centuries Ascent.png";
    public static final String FUENRARY_RITES = CARD_IMG_PATH + "Funerary Rites.png";
    public static final String IMMORTAL_PURITY = CARD_IMG_PATH + "Immortal Purity.png";
    public static final String LIGHT_OF_YOUR_LIFE = CARD_IMG_PATH + "Light Of Your Life.png";
    public static final String QUIET_CONSPIRACY = CARD_IMG_PATH + "Quiet Conspiracy.png";
    public static final String SEAL_AWAY = CARD_IMG_PATH + "Seal Away.png";
    public static final String SIP_OF_SPARKS = CARD_IMG_PATH + "Sip of Sparks.png";
    public static final String TOUCH_OF_CINNABAR = CARD_IMG_PATH + "Touch of Cinnabar.png";
    public static final String TRANSITION = CARD_IMG_PATH + "Transition.png";

    public static final String BEGET_ETERNITY = CARD_IMG_PATH + "Beget Eternity.png";
    public static final String CHOOSE_YOUR_END = CARD_IMG_PATH + "Choose Your End.png";
    public static final String FIENDISH_CRIMSON = CARD_IMG_PATH + "Fiendish Crimson.png";
    public static final String JUST_REWARDS = CARD_IMG_PATH + "Just Rewards.png";
    public static final String NEW_WARDROBE = CARD_IMG_PATH + "New Wardrobe.png";
    public static final String REACH_TO_HEAVEN = CARD_IMG_PATH + "Reach to Heaven.png";
    public static final String RISING_SUN_PRINCE = CARD_IMG_PATH + "Rising Sun's Prince.png";
    public static final String SEVENTEEN_ARTICLE_LASER = CARD_IMG_PATH + "Seventeen Article Laser.png";
    public static final String UNSTABLE_VIGOR = CARD_IMG_PATH + "Unstable Vigor.png";
    public static final String WICKED_PUNISHMENT = CARD_IMG_PATH + "Wicked Punishment.png";

    public static final String ARMILLARY_SPHERE = CARD_IMG_PATH + "Armillary Sphere.png";
    public static final String BISHAMON_BLESSING = CARD_IMG_PATH + "Bishamon's Blessing.png";
    public static final String BLOOD_BARGAIN = CARD_IMG_PATH + "Blood Bargain.png";
    public static final String BLOODLESS_SAPPHIRE = CARD_IMG_PATH + "Bloodless Sapphire.png";
    public static final String DREAMS_MAUSOLEUM = CARD_IMG_PATH + "Dreams Mausoleum.png";
    public static final String FEAST_OF_DEW = CARD_IMG_PATH + "Feast of Dew.png";
    public static final String GRANDEUR = CARD_IMG_PATH + "Grandeur.png";
    public static final String GUANYIN_BLESSING = CARD_IMG_PATH + "Guanyin's Blessing.png";
    public static final String KATABASIS = CARD_IMG_PATH + "Katabasis.png";
    public static final String METEMPSYCHOSIS = CARD_IMG_PATH + "Metempsychosis.png";
    public static final String MIX_OF_MERCURY = CARD_IMG_PATH + "Mix of Mercury.png";
    public static final String SYNCRETIC_PLUNGE = CARD_IMG_PATH + "Syncretic Plunge.png";
    public static final String SYNCRETIC_SURGE = CARD_IMG_PATH + "Syncretic Surge.png";
    public static final String TRANSCENSION = CARD_IMG_PATH + "Transcension.png";
    public static final String UNTOUCHABLE = CARD_IMG_PATH + "Untouchable.png";

    public static final String CAST_OFF_REGRETS = CARD_IMG_PATH + "Cast Off Regrets.png";
    public static final String CONVERGENCE = CARD_IMG_PATH + "Convergence.png";
    public static final String HARMONIC_REVERENCE = CARD_IMG_PATH + "Harmonic Reverence.png";
    public static final String HIDDEN_POWER = CARD_IMG_PATH + "Hidden Power.png";
    public static final String OVERDRIVE = CARD_IMG_PATH + "Overdrive.png";
    public static final String WUJI = CARD_IMG_PATH + "Wuji.png";
    public static final String XIAN_ARTS = CARD_IMG_PATH + "Xian Arts.png";

    public static final String GIRLS_DO_IT_BETTER = CARD_IMG_PATH + "Girls Do It Better.png";
    public static final String HAVE_IT_ALL = CARD_IMG_PATH + "Have It All.png";
    public static final String LIFEFORCE_NET = CARD_IMG_PATH + "Lifeforce Net.png";
    public static final String NEWBORN_DIVINITY = CARD_IMG_PATH + "Newborn Divinity.png";
    public static final String SCHISM = CARD_IMG_PATH + "Schism.png";

    public static final String AMASSED_MASKS = CARD_IMG_PATH + "Amassed Masks.png";
    public static final String BATTLEFORGED_BONDS = CARD_IMG_PATH + "Battleforged Bonds.png";
    public static final String BREWING_CLOISTER = CARD_IMG_PATH + "Brewing Cloister.png";
    public static final String COSMOS_CONTROL = CARD_IMG_PATH + "Cosmos Control.png";
    public static final String FALSE_DEATH = CARD_IMG_PATH + "False Death.png";
    public static final String HOPE_IN_THE_DARK = CARD_IMG_PATH + "Hope in the Dark.png";
    public static final String LUMINARY_PATH = CARD_IMG_PATH + "Luminary's Path.png";
    public static final String MASTERMIND = CARD_IMG_PATH + "Mastermind.png";
    public static final String TEN_DESIRES = CARD_IMG_PATH + "Ten Desires.png";

    public static final String DEATH_BY_GLAMOUR = CARD_IMG_PATH + "Death By Glamour.png";
    public static final String FAITHFUL_FLAMES = CARD_IMG_PATH + "Faithful Flames.png";
    public static final String FEAR_OF_DEATH = CARD_IMG_PATH + "Fear Of Death.png";
    public static final String IMMORTAL_CLARITY = CARD_IMG_PATH + "Immortal Clarity.png";
    public static final String STAR_SWORD_SOUL = CARD_IMG_PATH + "Star Sword Soul.png";
    public static final String WIFE_LIGHTNING = CARD_IMG_PATH + "Wife's Lightning.png";

    // Powers
    public static final String POWER_IMG_PATH = IMG_PATH + "powers/";
    public static TextureAtlas ADMIN_POWERS_ATLAS;

    // Relics
    public static final String RELIC_IMG_PATH = IMG_PATH + "relics/";
    public static final String SHAKU = RELIC_IMG_PATH + "Shaku.png";
    public static final String PRESCRIPTION_BOTTLE = RELIC_IMG_PATH + "PrescriptionBottle.png";
    public static final String CRACKED_TAIJITU = RELIC_IMG_PATH + "CrackedTaijitu.png";
    public static final String ABANDONED_ROSE = RELIC_IMG_PATH + "AbandonedRose.png";
    public static final String GOLDEN_CHICKADEE = RELIC_IMG_PATH + "GoldenChickadee.png";
    public static final String DRAGONSEYE_CHARM = RELIC_IMG_PATH + "DragonsEyeCharm.png";
    public static final String KEY_OF_KINGS = RELIC_IMG_PATH + "KeyOfKings.png";
    public static final String BLOODSOAKED_VEIL = RELIC_IMG_PATH + "BloodSoakedVeil.png";
    public static final String ELIXIR_OF_IMMORALITY = RELIC_IMG_PATH + "ElixirOfImmorality.png";

    // Placeholder trackers for Duality and Baton.
    public static final String ATTACK_COUNTER = RELIC_IMG_PATH + "AttackCounter.png";
    public static final String SKILL_COUNTER = RELIC_IMG_PATH + "SkillCounter.png";

    // Icons
    private static final String ADMINISTRX_CHAR_SELECT_PATH = IMG_PATH + "charSelect/";
    private static final String ADMINISTRIX_BUTTON = ADMINISTRX_CHAR_SELECT_PATH + "Logo1.png";
    private static final String ADMINISTRIX_PORTRAIT = ADMINISTRX_CHAR_SELECT_PATH + "Administrix_Select_Screen.png";

    // Badge
    public static final String BADGE_IMG = IMG_PATH + "BaseModBadge.png";

    public static final Logger logger = LogManager.getLogger(AdministrixMod.class.getName());

    public AdministrixMod() {

        BaseMod.subscribe(this);

        logger.info("Creating the color " + AbstractCardEnum.LichGold.toString());
        BaseMod.addColor(AbstractCardEnum.LichGold,
                ADMIN_GOLD, ADMIN_GOLD, ADMIN_GOLD, ADMIN_GOLD, ADMIN_GOLD, ADMIN_GOLD, ADMIN_GOLD,
                ATTACK_LICH_GOLD, SKILL_LICH_GOLD,
                POWER_LICH_GOLD, ENERGY_ORB_LICH_GOLD,
                ATTACK_LICH_GOLD_PORTRAIT, SKILL_LICH_GOLD_PORTRAIT,
                POWER_LICH_GOLD_PORTRAIT, ENERGY_ORB_LICH_GOLD_PORTRAIT);
    }

    public static void initialize() {
        AdministrixMod mod = new AdministrixMod();
    }

    @Override
    public void receivePostInitialize() {
        Texture badgeTexture = new Texture(Gdx.files.internal(BADGE_IMG));
        ModPanel settingsPanel = new ModPanel();
        // If this gets popular with the Chinese Touhou Crowd,
        // setting up language settings might be pertinent...
        // settingsPanel("This mod does not have any settings (yet)", 400.0f, 700.0f, (me) -> {});
        BaseMod.registerModBadge(badgeTexture, "The Administrix",
                "regret-index",
                "The lich princess of the rising sun awakens a second time in a strange land. A master manipulator of the Tao and her Deck.", null);

        Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;
        Settings.isModded = true;
        loadAudio();

        ADMIN_POWERS_ATLAS = new TextureAtlas(Gdx.files.internal(POWER_IMG_PATH + "AdminPowerIcons.atlas"));
    }

    private void loadAudio() {
        HashMap<String, Sfx> map = (HashMap<String, Sfx>) ReflectionHacks.getPrivate(CardCrawlGame.sound, SoundMaster.class, "map");
        map.put("STARRY-BEAT", new Sfx("administrix/admin_audio/STARRY-BEAT.wav", false));
        map.put("TH-MENU-OK", new Sfx("administrix/admin_audio/TH-MENU-OK.wav", false));
        map.put("TH-MENU-CONFIRM", new Sfx("administrix/admin_audio/TH-MENU-CONFIRM.wav", false));
        map.put("TH-SUBMENU", new Sfx("administrix/admin_audio/TH-SUBMENU.wav", false));
        map.put("TH-BONUS", new Sfx("administrix/admin_audio/TH-BONUS.wav", false));
        map.put("TH-WOOSH", new Sfx("administrix/admin_audio/TH-WOOSH.wav", false));
        map.put("HERMIT-HUM", new Sfx("administrix/admin_audio/HERMIT-HUM.wav", false));
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics.");

        BaseMod.addRelicToCustomPool(new ConductorRitualBaton(), AbstractCardEnum.LichGold);
        // BaseMod.addRelicToCustomPool(new AlphaAttackCounter(), AbstractCardEnum.LichGold);
        // BaseMod.addRelicToCustomPool(new AlphaSkillCounter(), AbstractCardEnum.LichGold);
        BaseMod.addRelicToCustomPool(new CrackedTaijitu(), AbstractCardEnum.LichGold);
        BaseMod.addRelicToCustomPool(new PrescriptionBottle(), AbstractCardEnum.LichGold);
        BaseMod.addRelicToCustomPool(new GoldenChickadee(), AbstractCardEnum.LichGold);
        BaseMod.addRelicToCustomPool(new AbandonedRose(), AbstractCardEnum.LichGold);
        BaseMod.addRelicToCustomPool(new KeyOfKings(), AbstractCardEnum.LichGold);
        BaseMod.addRelicToCustomPool(new BloodsoakedVeil(), AbstractCardEnum.LichGold);
        BaseMod.addRelicToCustomPool(new ElixirOfImmorality(), AbstractCardEnum.LichGold);
        BaseMod.addRelicToCustomPool(new DragonsEyeCharm(), AbstractCardEnum.LichGold);

        // RelicLibrary.add(new GoldenChickadee());

        logger.info("Relics finished.");
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new AbstractAdministrixCard.SecondMagicNumber());

        logger.info("Adding cards for the Administrix.");

        BaseMod.addCard(new Defend_Administrix());
        BaseMod.addCard(new Strike_Administrix());
        BaseMod.addCard(new BlueCloak());
        BaseMod.addCard(new RedCloak());
        BaseMod.addCard(new Daybreak());
        BaseMod.addCard(new Nightfall());

        // Common Attacks.
        BaseMod.addCard(new CrackingPangu());
        BaseMod.addCard(new DiversionaryTactic());
        BaseMod.addCard(new ExtolVirtue());
        BaseMod.addCard(new ImmortalGrace());
        BaseMod.addCard(new NoblePath());
        BaseMod.addCard(new PassageOfAges());
        BaseMod.addCard(new PlateOfAntiquity());
        BaseMod.addCard(new RebukeDesires());
        BaseMod.addCard(new RegentEdict());
        BaseMod.addCard(new ShadowPlay());
        BaseMod.addCard(new TrickOfTheLight());
        // BaseMod.addCard(new TraineeJiangshi());

        // Common Skills.
        BaseMod.addCard(new CenturiesAscent());
        BaseMod.addCard(new FuneraryRites());
        BaseMod.addCard(new ImmortalPurity());
        BaseMod.addCard(new LightOfYourLife());
        BaseMod.addCard(new QuietConspiracy());
        BaseMod.addCard(new SealAway());
        BaseMod.addCard(new SipOfSparks());
        BaseMod.addCard(new TouchOfCinnabar());
        BaseMod.addCard(new Transition());

        // Uncommon Attacks.
        BaseMod.addCard(new BegetEternity());
        BaseMod.addCard(new ChooseYourEnd());
        BaseMod.addCard(new FiendishCrimson());
        BaseMod.addCard(new JustRewards());
        BaseMod.addCard(new NewWardrobe());
        BaseMod.addCard(new ReachToHeaven());
        BaseMod.addCard(new RisingSunPrince());
        BaseMod.addCard(new SeventeenArticleLaser());
        BaseMod.addCard(new UnstableVigor());
        BaseMod.addCard(new WickedPunishment());

        // Uncommon Skills.
        BaseMod.addCard(new ArmillarySphere());
        BaseMod.addCard(new BishamonBlessing());
        BaseMod.addCard(new BloodlessSapphire());
        BaseMod.addCard(new BloodBargain());
        BaseMod.addCard(new DreamsMausoleum());
        BaseMod.addCard(new FeastOfDew());
        // BaseMod.addCard(new Grandeur());
        BaseMod.addCard(new GuanyinBlessing());
        BaseMod.addCard(new Katabasis());
        BaseMod.addCard(new Metempsychosis());
        BaseMod.addCard(new MixOfMercury());
        BaseMod.addCard(new SyncreticPlunge());
        BaseMod.addCard(new SyncreticSurge());
        BaseMod.addCard(new Transcension());
        BaseMod.addCard(new Untouchable());

        // Uncommon Powers.
        BaseMod.addCard(new CastOffRegrets());
        BaseMod.addCard(new Convergence());
        BaseMod.addCard(new HarmonicReverence());
        BaseMod.addCard(new HiddenPower());
        BaseMod.addCard(new Overdrive());
        BaseMod.addCard(new Wuji());
        BaseMod.addCard(new XianArts());

        // Rare Attacks.
        BaseMod.addCard(new GirlsDoItBetter());
        BaseMod.addCard(new HaveItAll());
        BaseMod.addCard(new LifeforceNet());
        BaseMod.addCard(new NewbornDivinity());
        BaseMod.addCard(new Schism());

        // Rare Skills.
        BaseMod.addCard(new AmassedMasks());
        BaseMod.addCard(new BrewingCloister());
        BaseMod.addCard(new BattleforgedBonds());
        BaseMod.addCard(new CosmosControl());
        BaseMod.addCard(new FalseDeath());
        BaseMod.addCard(new HopeInTheDark());
        BaseMod.addCard(new LuminaryPath());
        BaseMod.addCard(new Mastermind());
        BaseMod.addCard(new TenDesires());

        // Rare Powers.
        BaseMod.addCard(new DeathByGlamour());
        BaseMod.addCard(new FaithfulFlames());
        BaseMod.addCard(new FearOfDeath());
        BaseMod.addCard(new ImmortalClarity());
        BaseMod.addCard(new StarSwordSoul());
        BaseMod.addCard(new WifeLightning());

        logger.info("Finished adding cards for the Administrix.");
    }

    public static void setPowerImages(AbstractPower power, String powerID){
        powerID = powerID.replace("AdministrixMod:","");
        power.region128 = new TextureAtlas.AtlasRegion(new Texture(POWER_IMG_PATH +(powerID)+".png"), 0, 0, 128, 128);
    }

    public void receiveEditKeywords() {
        Gson gson = new Gson();

        String keywordStrings = Gdx.files.internal(getLang(KEYWORD_STRINGS)).readString(String.valueOf(StandardCharsets.UTF_8));
        Type typeToken = new TypeToken<Map<String, Keyword>>() {}.getType();

        keywords = (Map)gson.fromJson(keywordStrings, typeToken);

        keywords.forEach((k,v)->{
            // Keyword word = (Keyword)v;
            logger.info("Administrix: adding Keyword - " + v.NAMES[0]);
            BaseMod.addKeyword(v.NAMES, v.DESCRIPTION);
        });
    }

    public void receiveEditPotions() {
        logger.info("Begin adding potions.");
        BaseMod.addPotion(AttunementTonic.class, new Color(0.3f,0.2f,0.8f,1.0f),
                new Color(0.8f,0.6f,0.1f,1.0f), new Color(0.5f,0.5f,0.5f,
                        1.0f), AttunementTonic.POTION_ID, CharacterEnum.TheAdministrix);
        logger.info("Finished adding potions.");
    }


    public void receiveSetUnlocks() {

        /*
        // probably would add a relics tier, next
        // Administrix unlock 1: Deck manipulation.
        BaseMod.addUnlockBundle(new CustomUnlockBundle(
                AbstractUnlock.UnlockType.CARD,
                "AdministrixMod:SealAway",
                "AdministrixMod:QuietConspiracy",
                "AdministrixMod:Mastermind"
        ), CharacterEnum.TheAdministrix, 0);
        UnlockTracker.addCard("AdministrixMod:SealAway");
        UnlockTracker.addCard("AdministrixMod:QuietConspiracy");
        UnlockTracker.addCard("AdministrixMod:Mastermind");

        // Administrix unlock 2: Artifact builds.
        BaseMod.addUnlockBundle(new CustomUnlockBundle(
                AbstractUnlock.UnlockType.CARD,
                "AdministrixMod:Overdrive",
                "AdministrixMod:?",
                "AdministrixMod:FearOfDeath"
        ), CharacterEnum.TheAdministrix, 1);
        UnlockTracker.addCard("AdministrixMod:Overdrive");
        UnlockTracker.addCard("AdministrixMod:?");
        UnlockTracker.addCard("AdministrixMod:FearOfDeath");

        // Administrix unlock 3: Mixed Yin / Yang manipulators.
        BaseMod.addUnlockBundle(new CustomUnlockBundle(
                AbstractUnlock.UnlockType.CARD,
                "AdministrixMod:NewWardrobe",
                "AdministrixMod:Transcension",
                "AdministrixMod:Wuji"
        ), CharacterEnum.TheAdministrix, 1);
        UnlockTracker.addCard("AdministrixMod:SealAway");
        UnlockTracker.addCard("AdministrixMod:ArmillarySphere");
        UnlockTracker.addCard("AdministrixMod:FearOfDeath");
        */
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Adding in the Administrix character.");

        logger.info("add " + CharacterEnum.TheAdministrix.toString());

        BaseMod.addCharacter(new Administrix(CardCrawlGame.playerName), ADMINISTRIX_BUTTON,
                ADMINISTRIX_PORTRAIT, CharacterEnum.TheAdministrix);

        logger.info("Finished adding Administrix character.");
        receiveEditPotions();
    }

    private String getLang(String path)
    {
        switch (Settings.language) {
            case ZHS:
                return LOCALIZATION_PATH + "/zhs/" + path + "-ZHS.json";
            default:
                return LOCALIZATION_PATH + "/eng/" + path + "-ENG.json";
        }
    }

    @Override
    public void receiveEditStrings() {
        logger.info("Editing and adding in strings.");

        String relicStrings = Gdx.files.internal(getLang(RELIC_STRINGS)).readString(
                              String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        logger.info("Relic strings read and loaded.");

        String cardStrings = Gdx.files.internal(getLang(CARD_STRINGS)).readString(
                             String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        logger.info("Card strings read and loaded.");

        String powerStrings = Gdx.files.internal(getLang(POWER_STRINGS)).readString(
                              String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        logger.info("Power strings read and loaded.");

        String uiStrings = Gdx.files.internal(getLang(UI_STRINGS)).readString(
                           String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
        logger.info("UI strings read and loaded.");

        String potionStrings = Gdx.files.internal(getLang(POTION_STRINGS)).readString(
                               String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
        logger.info("Potion strings read and loaded.");

        String characterStrings = Gdx.files.internal(getLang(CHARACTER_STRINGS)).readString(
                                  String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CharacterStrings.class, characterStrings);
        logger.info("Character strings read and loaded.");

        logger.info("Finished with strings.");
    }

}