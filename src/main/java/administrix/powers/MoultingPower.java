package administrix.powers;

// Gain the higher of Yin or Yang on discarding a card.
// The onSpecificTrigger()'s Locator hook for this is in MoultingPatch.java.

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

public class MoultingPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:Moulting";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MoultingPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("moulting_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("moulting");
    }

    @Override
    public void onSpecificTrigger() {
        int yinAmount = this.owner.hasPower("AdministrixMod:Yin") ?
                        this.owner.getPower("AdministrixMod:Yin").amount : 0;
        int yangAmount = this.owner.hasPower("AdministrixMod:Yang") ?
                         this.owner.getPower("AdministrixMod:Yang").amount : 0;

        if (yinAmount > yangAmount) {
            this.flashWithoutSound();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new YinPower(this.owner, this.amount), this.amount));
        } else if (yangAmount > yinAmount) {
            this.flashWithoutSound();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new YangPower(this.owner, this.amount), this.amount));
        }
    }

    public void updateDescription()
    {
        if (this.amount > 1) {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
        }
    }

}