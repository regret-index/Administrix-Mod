package administrix.relics;

import administrix.AdministrixMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The effects of this relic are implemented in the DragonsEyePatch.
// It's basically Snecko Skull for self-Yin/Yang.

public class DragonsEyeCharm
        extends CustomRelic
{
    public static final String ID = "AdministrixMod:DragonsEyeCharm";
    public static final RelicStrings STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String[] DESCRIPTIONS = STRINGS.DESCRIPTIONS;
    private static final int INCREMENT = 3;

    public static final Logger logger = LogManager.getLogger(DragonsEyeCharm.class.getName());

    public DragonsEyeCharm()
    {
        super(ID, new Texture(AdministrixMod.DRAGONSEYE_CHARM),
                RelicTier.SHOP, LandingSound.SOLID);
        logger.info(ID + " initialized");
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + INCREMENT + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new DragonsEyeCharm();
    }

}