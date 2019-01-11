package administrix.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

// Yang does nothing by itself, except slowly even out with Yin.
// A fair number of cards just use this as fuel in comparison to Yin.
// However, if you have any Affinity and less Yin than Yang,
// one'll gain block whenever they gain Yang.

public class YangPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:Yang";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public YangPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("yang_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("yang");
    }

    @Override
    public void stackPower(int amount)
    {
        if (this.owner.hasPower(AffinityPower.POWER_ID) &&
            (!this.owner.hasPower(YinPower.POWER_ID) ||
             this.owner.getPower(YinPower.POWER_ID).amount < this.amount)) {

            this.owner.getPower(AffinityPower.POWER_ID).flash();
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.owner.getPower(AffinityPower.POWER_ID).amount));
        }

        super.stackPower(amount);
    }

    @Override
    public void atEndOfRound() {
        if (!this.owner.hasPower(YinPower.POWER_ID) ||
             this.owner.hasPower(YinPower.POWER_ID) &&
             this.amount > this.owner.getPower(YinPower.POWER_ID).amount) {
            flash();
            if (this.amount == 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            }
        }
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0];
    }

}