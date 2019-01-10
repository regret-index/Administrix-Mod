package administrix.relics;

import administrix.AdministrixMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrescriptionBottle
        extends CustomRelic
{
    public static final String ID = "AdministrixMod:PrescriptionBottle";
    public static final RelicStrings STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String[] DESCRIPTIONS = STRINGS.DESCRIPTIONS;
    private static final int NUM_TURNS = 4;
    private static final int ARTIFACT_AMOUNT = 1;

    public static final Logger logger = LogManager.getLogger(PrescriptionBottle.class.getName());

    public PrescriptionBottle()
    {
        super(ID, new Texture(AdministrixMod.PRESCRIPTION_BOTTLE),
                RelicTier.COMMON, LandingSound.SOLID);
        logger.info(ID + " initialized");
    }

    @Override
    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + NUM_TURNS + this.DESCRIPTIONS[1];
    }

    public void onEquip() { this.counter = 0; }

    public void atTurnStart()
    {
        if (this.counter == -1) {
            this.counter += 2;
        } else {
            this.counter += 1;
        }
        if (this.counter >= NUM_TURNS)
        {
            flash();
            this.counter = 0;
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, ARTIFACT_AMOUNT), ARTIFACT_AMOUNT, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new PrescriptionBottle();
    }

}