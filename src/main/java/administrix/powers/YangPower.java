package administrix.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

// Yang provides a source of Block if it's higher than Yin,
// scaled with Affinity.

public class YangPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:Yang";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static boolean YANG_FLIP = false;

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
        if (!this.owner.hasPower(YinPower.POWER_ID) ||
             this.owner.getPower(YinPower.POWER_ID).amount < this.amount) {

            int toBlock = 1;

            if (this.owner.hasPower(AffinityPower.POWER_ID)) {
                this.owner.getPower(AffinityPower.POWER_ID).flash();
                toBlock += this.owner.getPower(AffinityPower.POWER_ID).amount;
            }

            if (YANG_FLIP) {
                YANG_FLIP = false;
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(AbstractDungeon.player.hb.cX - 120.0F * Settings.scale, AbstractDungeon.player.hb.cY + 40.0F * Settings.scale, 0.0F, 140.0F * Settings.scale, 0.0F, 4.0F, Color.GOLD, Color.GOLD)));
            } else {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new AnimatedSlashEffect(AbstractDungeon.player.hb.cX + 120.0F * Settings.scale, AbstractDungeon.player.hb.cY + 40.0F * Settings.scale, 0.0F, 140.0F * Settings.scale, 0.0F, 4.0F, Color.GOLD, Color.GOLD)));
                YANG_FLIP = true;
            }
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, toBlock));
        }

        super.stackPower(amount);
    }

    public void updateDescription()
    {
        int power = (this.owner.hasPower(AffinityPower.POWER_ID)) ?
                     this.owner.getPower(AffinityPower.POWER_ID).amount + 1 : 1;

        if (!this.owner.hasPower(YinPower.POWER_ID) ||
            this.owner.getPower(YinPower.POWER_ID).amount < this.amount) {
            this.description = DESCRIPTIONS[0] + power +
                               DESCRIPTIONS[1] + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + power +
                               DESCRIPTIONS[1] + DESCRIPTIONS[3];
        }
    }
}