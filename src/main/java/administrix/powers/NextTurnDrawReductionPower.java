package administrix.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

public class NextTurnDrawReductionPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:NextTurnDrawReduction";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public NextTurnDrawReductionPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = AbstractPower.PowerType.DEBUFF;
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("draw_reduction1_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("draw_reduction1");
    }

    public void atStartOfTurn()
    {
        flash();
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void atEndOfRound() {
        AbstractDungeon.player.gameHandSize -= amount;
    }

    public void onRemove()
    {
        AbstractDungeon.player.gameHandSize += amount;
    }

    public void updateDescription()
    {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        } else {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
        }
    }
}
