package administrix.relics;

import administrix.AdministrixMod;
import administrix.cards.uncommon.attack.FiendishCrimson;
import administrix.cards.uncommon.skill.BloodlessSapphire;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomRelic;
import basemod.helpers.BaseModCardTags;
import basemod.helpers.CardTags;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;


public class BloodsoakedVeil extends CustomRelic {
    public static final String ID = "AdministrixMod:BloodSoakedVeil";
    public static final RelicStrings STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String[] DESCRIPTIONS = STRINGS.DESCRIPTIONS;
    private static final int COST_CUTOFF = 2;
    private static final int BLOODDRENCH_AMOUNT = 3;

    public static final Logger logger = LogManager.getLogger(BloodsoakedVeil.class.getName());

    public BloodsoakedVeil() {
        super(ID, new Texture(AdministrixMod.BLOODSOAKED_VEIL),
              RelicTier.BOSS, LandingSound.SOLID);
        if (Settings.language == Settings.GameLanguage.ZHS) {
            this.tips.add(new PowerTip((TipHelper.capitalize("残忍猩红, 无血色的蓝宝石")),
                         (String) GameDictionary.keywords.get("染血")));
        } else {
            this.tips.add(new PowerTip((TipHelper.capitalize("F. Crimson, B. Sapphire")),
                         (String) GameDictionary.keywords.get("bloodsoaked")));
            logger.info(ID + " initialized");
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + BLOODDRENCH_AMOUNT + this.DESCRIPTIONS[1] +
               BLOODDRENCH_AMOUNT + this.DESCRIPTIONS[2] + COST_CUTOFF +
               this.DESCRIPTIONS[3];
    }

    // Iterate over the master deck to remove strikes and defends,
    // then add in the new cards.
    @Override
    public void onEquip()
    {
        Iterator i = AbstractDungeon.player.masterDeck.group.iterator();

        while(i.hasNext()) {
            AbstractCard c = (AbstractCard)i.next();
            boolean doRemove = false;
            if (!(c instanceof CustomCard) || !((CustomCard)c).isStrike() && !((CustomCard)c).isDefend()) {
                if (CardTags.hasTag(c, BaseModCardTags.BASIC_STRIKE) || CardTags.hasTag(c, BaseModCardTags.BASIC_DEFEND)) {
                    doRemove = true;
                }
            } else {
                doRemove = true;
            }

            if (doRemove) {
                i.remove();
            }
        }

        for(int j = 0; j < BLOODDRENCH_AMOUNT; j++) {
            AbstractCard c = new FiendishCrimson();
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        }

        for(int k = 0; k < BLOODDRENCH_AMOUNT; k++) {
            AbstractCard c = new BloodlessSapphire();
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        }
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (card.cost >= 1) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedBluePower(AbstractDungeon.player, 1), 1));
        }
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new BloodsoakedVeil();
    }
}