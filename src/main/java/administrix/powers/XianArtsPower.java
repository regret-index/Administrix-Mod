package administrix.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

// The implementation of this is in XianArtsPatch.

public class XianArtsPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:XianArts";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static boolean turnReset = true;

    public XianArtsPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("duality_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("duality");
    }

    public void onSpecificTrigger() {
        if (turnReset) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.amount), this.amount));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, this.amount), this.amount));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            turnReset = false;
        }
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        turnReset = true;
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0] + this.amount +
                           DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

}