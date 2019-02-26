package administrix.powers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import administrix.cards.common.attack.RegentEdict;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

public class SpectralThiefPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:SpectralThief";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SpectralThiefPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("spectral_thief_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("spectral_thief");
    }

    @Override
    public void stackPower(int amount)
    {
        if (this.amount == 2) {
            // Due to this effect focusing on a target,
            // the actual theft is implemented over in RegentEdict itself.
            flash();
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else {
            super.stackPower(amount);
        }
    }

    public void updateDescription()
    {
        if (this.amount == 2) {
            this.description = DESCRIPTIONS[0] + (3 - this.amount) + DESCRIPTIONS[1];
        } else {
            this.description = (DESCRIPTIONS[0] + (3 - this.amount) + DESCRIPTIONS[2]);
        }
    }
}
