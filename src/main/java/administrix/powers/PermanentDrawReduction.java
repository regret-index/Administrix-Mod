package administrix.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

public class PermanentDrawReduction extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:PermanentDrawReduction";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PermanentDrawReduction(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.DEBUFF;
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("draw_reduction2_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("draw_reduction2");
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
