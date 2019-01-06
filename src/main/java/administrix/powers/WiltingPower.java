package administrix.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

public class WiltingPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:Wilting";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static int witherDamage = 1;
    private boolean justApplied = false;

    public WiltingPower(AbstractCreature owner, int amount, boolean isSourceMonster) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("wilting_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("wilting");
        if (isSourceMonster) {
            this.justApplied = true;
        }
        this.type = AbstractPower.PowerType.DEBUFF;
        this.isTurnBased = true;
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this.owner, witherDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        this.updateDescription();
    }

    public void atEndOfRound()
    {
        if (this.justApplied)
        {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

    public void updateDescription()
    {
        if (this.amount == 1) {
            this.description = (DESCRIPTIONS[0] + witherDamage + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3]);
        } else {
            this.description = (DESCRIPTIONS[0] + witherDamage + DESCRIPTIONS[2]);
        }
    }

}