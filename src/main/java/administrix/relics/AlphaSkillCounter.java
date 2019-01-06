package administrix.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import administrix.AdministrixMod;

public class AlphaSkillCounter
        extends CustomRelic
{
    public static final String ID = "AdministrixMod:AlphaSkillCounter";
    public static final RelicStrings STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String[] DESCRIPTIONS = STRINGS.DESCRIPTIONS;

    public static final Logger logger = LogManager.getLogger(AlphaSkillCounter.class.getName());

    public AlphaSkillCounter()
    {
        super(ID, new Texture(AdministrixMod.SKILL_COUNTER),
                RelicTier.SPECIAL, LandingSound.MAGICAL);
        logger.info("initialized");
    }

    @Override
    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart()
    {
        this.counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.type == AbstractCard.CardType.SKILL)
            this.counter += 1;
    }

    @Override
    public void onVictory()
    {
        this.counter = -1;
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new AlphaSkillCounter();
    }
}
