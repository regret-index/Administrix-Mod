package administrix.relics;

import administrix.AdministrixMod;
import administrix.cards.common.attack.Daybreak;
import administrix.cards.common.skill.Nightfall;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbandonedRose extends CustomRelic {

    public static final String ID = "AdministrixMod:AbandonedRose";
    public static final RelicStrings STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String[] DESCRIPTIONS = STRINGS.DESCRIPTIONS;

    public static final Logger logger = LogManager.getLogger(AbandonedRose.class.getName());

    public AbandonedRose()
    {
        super(ID, new Texture(AdministrixMod.ABANDONED_ROSE),
                RelicTier.UNCOMMON, LandingSound.MAGICAL);
        logger.info(ID + " initialized");
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new AbandonedRose();
    }

    @Override
    public void onManualDiscard() {
        // if (AbstractDungeon.player.discardPile.group.size() == 0)
        int last = AbstractDungeon.player.discardPile.group.size() - 1;
        AbstractCard lastCard = (AbstractCard)AbstractDungeon.player.discardPile.group.get(last);
        lastCard.upgrade();
        lastCard.superFlash();
    }
}