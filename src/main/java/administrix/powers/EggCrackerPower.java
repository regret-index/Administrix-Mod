package administrix.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

public class EggCrackerPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:EggCracker";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public EggCrackerPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("egg_cracker_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("egg_cracker");
    }

    @Override
    public void stackPower(int amount)
    {
        if (this.amount == 2) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(AbstractDungeon.player));
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
