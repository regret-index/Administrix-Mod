package administrix.relics;

import administrix.AdministrixMod;
import administrix.cards.common.attack.Daybreak;
import administrix.cards.common.skill.Nightfall;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class KeyOfKings
        extends CustomRelic
{
    public static final String ID = "AdministrixMod:KeyOfKings";
    public static final RelicStrings STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String[] DESCRIPTIONS = STRINGS.DESCRIPTIONS;
    private static final int DISCARD_AMOUNT = 3;

    public static final Logger logger = LogManager.getLogger(KeyOfKings.class.getName());

    public KeyOfKings()
    {
        super(ID, new Texture(AdministrixMod.KEY_OF_KINGS),
                RelicTier.RARE, LandingSound.MAGICAL);
        logger.info(ID + " initialized");
    }

    @Override
    public void onEquip() { this.counter = 0; }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + DISCARD_AMOUNT + this.DESCRIPTIONS[1];
    }

    @Override
    public void onManualDiscard() {
        this.counter++;
        if (this.counter == DISCARD_AMOUNT) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new SFXAction("TH-WOOSH"));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Daybreak(), 1));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Nightfall(), 1));
            this.counter = 0;
        }
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new KeyOfKings();
    }

}