package administrix.characters;

import java.util.ArrayList;

import java.util.Map.Entry;

import administrix.PseudoFormCharacter;
import administrix.cards.common.attack.Daybreak;
import administrix.cards.common.skill.Nightfall;
import administrix.cards.uncommon.power.*;
import administrix.cards.starter.*;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GameTips;
import com.megacrit.cardcrawl.helpers.ModHelper;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.patches.CharacterEnum;

import administrix.relics.*;

import basemod.abstracts.CustomPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class Administrix extends PseudoFormCharacter {

    public static final int ENERGY_PER_TURN = 3;

    private static final CharacterStrings charStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    static {
        charStrings = CardCrawlGame.languagePack.getCharacterString("Administrix");
        NAME = charStrings.NAMES[0];
        DESCRIPTION = charStrings.TEXT[0];
    }

    public static final String CHARACTER_IMG_PATH = AdministrixMod.IMG_PATH + "char/";
    public static final String AX_SHOULDER_2 = CHARACTER_IMG_PATH + "miko_shoulder2.png";
    public static final String AX_SHOULDER = CHARACTER_IMG_PATH + "miko_shoulder.png";
    public static final String AX_CORPSE = CHARACTER_IMG_PATH + "corpse_administrix.png";
    public static final String ADMINISTRIX_MAIN = CHARACTER_IMG_PATH + "main_administrix.png";

    private static final String ORB_PATH = CHARACTER_IMG_PATH + "orb/";
    private static final String AX_ORB_LAYER1_PATH = ORB_PATH + "layer1.png";
    private static final String AX_ORB_LAYER2_PATH = ORB_PATH + "layer2.png";
    private static final String AX_ORB_LAYER3_PATH = ORB_PATH + "layer3.png";
    private static final String AX_ORB_LAYER4_PATH = ORB_PATH + "layer4.png";
    private static final String AX_ORB_LAYER5_PATH = ORB_PATH + "layer5.png";
    private static final String AX_ORB_LAYER6_PATH = ORB_PATH + "layer6.png";
    private static final String AX_ORB_LAYER1D_PATH = ORB_PATH + "layer1d.png";
    private static final String AX_ORB_LAYER2D_PATH = ORB_PATH + "layer2d.png";
    private static final String AX_ORB_LAYER3D_PATH = ORB_PATH + "layer3d.png";
    private static final String AX_ORB_LAYER4D_PATH = ORB_PATH + "layer4d.png";
    private static final String AX_ORB_LAYER5D_PATH = ORB_PATH + "layer5d.png";
    private static final String ADMINISTRIX_VFX_PATH = ORB_PATH + "vfx.png";

    public static final Logger logger = LogManager.getLogger(Administrix.class.getName());

    public static final String[] orbTextures = {
            AX_ORB_LAYER1_PATH,
            AX_ORB_LAYER2_PATH,
            AX_ORB_LAYER3_PATH,
            AX_ORB_LAYER4_PATH,
            AX_ORB_LAYER5_PATH,
            AX_ORB_LAYER6_PATH,
            AX_ORB_LAYER1D_PATH,
            AX_ORB_LAYER2D_PATH,
            AX_ORB_LAYER3D_PATH,
            AX_ORB_LAYER4D_PATH,
            AX_ORB_LAYER5D_PATH,
    };

    private static final float[] LAYER_SPEED =
            {0.0F, 40.0F, -40.0F, 20.0F, 0.0F, 0.0F, 8.0F, -8.0F, 5.0F, 0.0F};

    public static final int STARTING_HP = 65;
    public static final int MAX_HP = 65;
    public static final int STARTING_GOLD = 101;
    public static final int HAND_SIZE = 5;

    public Administrix(String playerName) {
        super(playerName, CharacterEnum.TheAdministrix, orbTextures,
              ADMINISTRIX_VFX_PATH, LAYER_SPEED,
                new SpriterAnimation(CHARACTER_IMG_PATH + "main/main.scml"),
                new SpriterAnimation(CHARACTER_IMG_PATH + "attack/attack.scml"),
                new SpriterAnimation(CHARACTER_IMG_PATH + "skill/skill.scml"));

        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 220.0F * Settings.scale);

        initializeClass(null,
                AX_SHOULDER_2, // required call to load textures and setup energy/loadout
                AX_SHOULDER,
                AX_CORPSE,
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

        CardCrawlGame.tips = new AdminGameTips();
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAME;
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Administrix(getLocalizedCharacterName());
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(Strike_Administrix.ID);
        retVal.add(Strike_Administrix.ID);
        retVal.add(Strike_Administrix.ID);
        retVal.add(Strike_Administrix.ID);
        retVal.add(RedCloak.ID);
        retVal.add(Defend_Administrix.ID);
        retVal.add(Defend_Administrix.ID);
        retVal.add(Defend_Administrix.ID);
        retVal.add(Defend_Administrix.ID);
        retVal.add(BlueCloak.ID);

        UnlockTracker.markCardAsSeen(Strike_Administrix.ID);
        UnlockTracker.markCardAsSeen(Defend_Administrix.ID);
        UnlockTracker.markCardAsSeen(RedCloak.ID);
        UnlockTracker.markCardAsSeen(BlueCloak.ID);
        UnlockTracker.markCardAsSeen(Daybreak.ID);
        UnlockTracker.markCardAsSeen(Nightfall.ID);

        return retVal;
    }

    @Override
    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool) {
        AbstractCard card = null;
        for (Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            card = (AbstractCard) c.getValue();
            if ((card.color == AbstractCardEnum.LichGold) && (card.rarity != AbstractCard.CardRarity.BASIC)
                    && ((!UnlockTracker.isCardLocked((String) c.getKey())) || (Settings.treatEverythingAsUnlocked()))) {
                tmpPool.add(card);
            }
        }
        if (ModHelper.isModEnabled("Red cards")) {
            CardLibrary.addRedCards(tmpPool);
        }
        if (ModHelper.isModEnabled("Green cards")) {
            CardLibrary.addGreenCards(tmpPool);
        }
        if (ModHelper.isModEnabled("Blue cards")) {
            CardLibrary.addBlueCards(tmpPool);
        }
        if (ModHelper.isModEnabled("Colorless cards")) {
            CardLibrary.addColorlessCards(tmpPool);
        }

        return tmpPool;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(ConductorRitualBaton.ID);
        UnlockTracker.markRelicAsSeen(ConductorRitualBaton.ID);

        return retVal;
    }

//    @Override
//    public void onVictory() {
//        super.onVictory();
//
//    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAME, DESCRIPTION,
                STARTING_HP, MAX_HP,0, STARTING_GOLD, HAND_SIZE,
                this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return getLocalizedCharacterName();
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.LichGold;
    }

    @Override
    public Color getCardRenderColor() {
        return new Color(0.2205f, 0.0635f, 0.2985f, 0.1f);
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        ArrayList<AbstractCard> list = new ArrayList();
        list.add(new RedCloak());
        list.add(new BlueCloak());
        return (AbstractCard)list.get(cardRandomRng.random(list.size() - 1));
    }

    @Override
    public Color getCardTrailColor() {
        return new Color(0.2205f, 0.0635f, 0.2985f, 0.1f);
    }

    @Override
    public int getAscensionMaxHPLoss() { return 4; }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playV("STARRY-BEAT", 1);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "STARRY-BEAT";
    }

    @Override
    public String getSpireHeartText() {
        return charStrings.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return Color.PURPLE.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.SMASH,
                AbstractGameAction.AttackEffect.SLASH_HEAVY
        };
    }

    @Override
    public String getVampireText() {
        return charStrings.TEXT[2];
    }
}