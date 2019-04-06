package administrix.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

// Yin also provides damage if it's higher than Yang,
// scaled to Affinity.

public class YinPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:Yin";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public YinPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("yin_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("yin");
    }

    @Override
    public void stackPower(int amount)
    {
        if (!this.owner.hasPower(YangPower.POWER_ID) ||
             this.owner.getPower(YangPower.POWER_ID).amount < this.amount) {

            int toDamage = 1;

            if (this.owner.hasPower(AffinityPower.POWER_ID)) {
                this.owner.getPower(AffinityPower.POWER_ID).flash();
                toDamage += this.owner.getPower(AffinityPower.POWER_ID).amount;
            }

            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_DEFECT_BEAM"));
            if (Settings.FAST_MODE) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new CleaveEffect(), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(toDamage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new CleaveEffect(), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(toDamage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
            } else {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new CleaveEffect(), 0.2F));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(toDamage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new CleaveEffect(), 0.2F));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(toDamage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
            }
        }

        super.stackPower(amount);
    }

    public void updateDescription()
    {
        int power = (this.owner.hasPower(AffinityPower.POWER_ID)) ?
                     this.owner.getPower(AffinityPower.POWER_ID).amount + 1 : 1;
        this.description = DESCRIPTIONS[0] + power + DESCRIPTIONS[1];

        if (!this.owner.hasPower(YangPower.POWER_ID) ||
            this.owner.getPower(YangPower.POWER_ID).amount < this.amount) {
            this.description = DESCRIPTIONS[0] + power +
                               DESCRIPTIONS[1] + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + power +
                               DESCRIPTIONS[1] + DESCRIPTIONS[3];
        }
    }
}