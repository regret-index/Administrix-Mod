package administrix.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

// Affinity affects gaining Yin and Yang.
// As a result, the effects of it (damage and block for Yang and Yin
// on having more of one or another) are implemented in those powers' files.

public class AffinityPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:Affinity";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AffinityPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("affinity_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("affinity");
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0] + this.amount +
                           DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

}