package administrix.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;

import administrix.AdministrixMod;
import administrix.actions.TransposeAction;

public class ConductorRitualBaton extends CustomRelic {
    public static final String ID = "AdministrixMod:ConductorRitualBaton";
    public static final RelicStrings STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String[] DESCRIPTIONS = STRINGS.DESCRIPTIONS;
    private static final int ACTIVATE_COUNT = 4;
    private static int shakuAttackCounter;
    private static int shakuSkillCounter;
    private static boolean shakuActivated;
    private static boolean shakuBattleStart;
    public int offsetX = 0;

    public static final Logger logger = LogManager.getLogger(ConductorRitualBaton.class.getName());

    public ConductorRitualBaton()
    {
        super(ID, new Texture(AdministrixMod.SHAKU),
                RelicTier.STARTER, LandingSound.MAGICAL);
        logger.info(ID + " initialized");
    }

    @Override
    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + ACTIVATE_COUNT + this.DESCRIPTIONS[1] + ACTIVATE_COUNT + this.DESCRIPTIONS[2];
    }

    @Override
    public void atTurnStart()
    {
        shakuAttackCounter = 0;
        shakuSkillCounter = 0;
        shakuActivated = false;
        stopPulse();
    }


    @Override
    public void atBattleStart() {
        super.atBattleStart();
        shakuBattleStart = true;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            if (card.type == AbstractCard.CardType.ATTACK) {
                shakuAttackCounter += 1;
            } else if (card.type == AbstractCard.CardType.SKILL) {
                shakuSkillCounter += 1;
            }
            if (!shakuActivated) {
                if (shakuAttackCounter == ACTIVATE_COUNT - 1 ||
                    shakuSkillCounter == ACTIVATE_COUNT - 1) {
                    beginLongPulse();
                }
                if (shakuAttackCounter >= ACTIVATE_COUNT ||
                    shakuSkillCounter >= ACTIVATE_COUNT) {
                    flash();
                    AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("TH-SUBMENU"));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new BorderLongFlashEffect(Color.GOLD), 0.8F));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
                    AbstractDungeon.actionManager.addToBottom(new TransposeAction(1));
                    shakuActivated = true;
                    stopPulse();
                }
            }
        }
    }

    // Borrowed from Blue Hexagon's Elementalist's Magus Staff.
    @Override
    public void renderInTopPanel(SpriteBatch sb) {
        this.scale = Settings.scale;
        if (Settings.hideRelics) {
            return;
        }

        renderOutline(sb, true);
        sb.setColor(Color.WHITE);
        float offsetX = 0f;
        float rotation = 0f;
        sb.draw(this.img, this.currentX - 64.0F + offsetX, this.currentY - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale, this.scale, rotation, 0, 0, 128, 128, false, false);

        renderCounter(sb, true);
        renderFlash(sb, true);
        this.hb.render(sb);
    }

    @Override
    public void renderCounter(SpriteBatch sb, boolean inTopPanel) {
        if (shakuBattleStart) {
            Color attackColor, skillColor;

            if (!shakuActivated && shakuAttackCounter == ACTIVATE_COUNT - 1) {
                attackColor = new Color(0.9F, 0.5F, 0.5F, 1.0F);
            } else {
                attackColor = new Color(0.7F, 0.3F, 0.3F, 1.0F);
            }

            if (!shakuActivated && shakuSkillCounter == ACTIVATE_COUNT - 1) {
                skillColor = new Color(0.5F, 0.9F, 0.8F, 1.0F);
            } else {
                skillColor = new Color(0.3F, 0.7F, 0.6F, 1.0F);
            }

            float offX = (inTopPanel) ? this.currentX + offsetX : this.currentX;
            float offXA = 8.0F * Settings.scale;
            float offXS = (shakuSkillCounter < 10) ? 26.0F * Settings.scale :
                                                     34.0F * Settings.scale;

            FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(shakuAttackCounter), offX - offXA, this.currentY + 28.0F * Settings.scale, attackColor);
            FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(shakuSkillCounter), offX + offXS, this.currentY - 7.0F * Settings.scale, skillColor);
        }
    }

    @Override
    public void onVictory()
    {
        shakuAttackCounter = -1;
        shakuSkillCounter = -1;
        shakuBattleStart = false;
        stopPulse();
    }

    @Override
    public AbstractRelic makeCopy() { return new ConductorRitualBaton(); }
}