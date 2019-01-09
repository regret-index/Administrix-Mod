package administrix.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import administrix.AdministrixMod;
import administrix.powers.WiltingPower;

public class ElixirOfImmorality
        extends CustomRelic
{
    public static final String ID = "AdministrixMod:ElixirOfImmorality";
    public static final RelicStrings STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String[] DESCRIPTIONS = STRINGS.DESCRIPTIONS;
    private static int FRAIL_AMOUNT = 1;
    private static int WILTING_AMOUNT = 1;

    public static final Logger logger = LogManager.getLogger(ElixirOfImmorality.class.getName());

    public ElixirOfImmorality()
    {
        super(ID, new Texture(AdministrixMod.ELIXIR_OF_IMMORALITY),
              RelicTier.BOSS, LandingSound.MAGICAL);
        logger.info(ID + " initialized");
    }

    @Override
    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] +
               this.DESCRIPTIONS[1] + WILTING_AMOUNT + this.DESCRIPTIONS[2] +
               this.DESCRIPTIONS[3] + FRAIL_AMOUNT + this.DESCRIPTIONS[4];
    }

    @Override
    public void atBattleStart() { this.counter = 0; }

    @Override
    public void atTurnStart() {
        this.counter++;
        if (this.counter == 1 || this.counter == 3){
            beginLongPulse();
        } else if (this.counter == 2) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WiltingPower(AbstractDungeon.player, WILTING_AMOUNT, false), WILTING_AMOUNT));
            stopPulse();
        } else if (this.counter == 4) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, FRAIL_AMOUNT, false), FRAIL_AMOUNT));
            stopPulse();
        }
    }

    @Override
    public void onVictory()
    {
        this.counter = -1;
        stopPulse();
    }

    public void onEquip() { AbstractDungeon.player.energy.energyMaster += 1; }

    public void onUnequip() { AbstractDungeon.player.energy.energyMaster -= 1; }

    @Override
    public AbstractRelic makeCopy() { return new ElixirOfImmorality(); }
}