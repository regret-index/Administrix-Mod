package administrix.powers;

import administrix.AdministrixMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

// This effect doubles Plot effects.
// While a patch like as exists for Moulting would be reasonable,
// for the first release a function check for AbstractAdministrixCard
// will serve until later clean-up.

public class MastermindPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:MastermindPower";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MastermindPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("mastermind_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("mastermind");
    }

    public void updateDescription()
    {
        if (this.amount == 1) {
            this.description = (DESCRIPTIONS[0] + DESCRIPTIONS[1]);
        } else if (this.amount == 2) {
            this.description = (DESCRIPTIONS[0] + DESCRIPTIONS[2]);
        } else {
            this.description = (DESCRIPTIONS[0] + this.amount +
                                DESCRIPTIONS[3] + DESCRIPTIONS[4]);
        }
    }

    public void atEndOfRound()
    {
        if (this.amount == 1) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

}