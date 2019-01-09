package administrix.relics;

import administrix.AdministrixMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;

public class GoldenChickadee
        extends CustomRelic
{
    public static final String ID = "AdministrixMod:GoldenChickadee";
    public static final RelicStrings STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String[] DESCRIPTIONS = STRINGS.DESCRIPTIONS;
    private static final int UPGRADE_COUNT = 2;

    public static final Logger logger = LogManager.getLogger(GoldenChickadee.class.getName());

    public GoldenChickadee()
    {
        super(ID, new Texture(AdministrixMod.GOLDEN_CHICKADEE),
                RelicTier.UNCOMMON, LandingSound.SOLID);
        logger.info(ID + " initialized");
    }

    @Override
    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + UPGRADE_COUNT + this.DESCRIPTIONS[1] +
               this.DESCRIPTIONS[2];
    }

    @Override
    public void onObtainCard(AbstractCard c)
    {
        if ((c.rarity == AbstractCard.CardRarity.RARE) && (c.canUpgrade()) && (!c.upgraded)) {
            c.upgrade();
        }
    }

    @Override
    public void onEquip()
    {
        ArrayList<AbstractCard> upgradableCards = new ArrayList();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if ((c.canUpgrade()) && (c.rarity == AbstractCard.CardRarity.RARE)) {
                upgradableCards.add(c);
            }
        }
        Collections.shuffle(upgradableCards, new java.util.Random(AbstractDungeon.miscRng.randomLong()));
        if (!upgradableCards.isEmpty()) {
            if (upgradableCards.size() == 1)
            {
                ((AbstractCard)upgradableCards.get(0)).upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(
                        ((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            }
            else
            {
                ((AbstractCard)upgradableCards.get(0)).upgrade();
                ((AbstractCard)upgradableCards.get(1)).upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(1));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(

                        ((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale, Settings.HEIGHT / 2.0F));

                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(

                        ((AbstractCard)upgradableCards.get(1)).makeStatEquivalentCopy(), Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 20.0F * Settings.scale, Settings.HEIGHT / 2.0F));

                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            }
        }
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new GoldenChickadee();
    }

}