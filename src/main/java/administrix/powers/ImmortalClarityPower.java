package administrix.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

public class ImmortalClarityPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:ImmortalClarity";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private AbstractCard card;

    public ImmortalClarityPower(AbstractCreature owner, int cardAmt, AbstractCard copyMe)
    {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = cardAmt;
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("immortal_clarity_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("immortal_clarity");
        this.card = copyMe.makeStatEquivalentCopy();
        this.card.resetAttributes();
        updateDescription();
    }

    public void updateDescription()
    {
        if (this.amount == 1) {
            this.description = (DESCRIPTIONS[0] + FontHelper.colorString(this.card.name, "y") +
                                DESCRIPTIONS[1] + DESCRIPTIONS[5]);
        } else {
            this.description = (DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") +
                                DESCRIPTIONS[3] + this.amount +
                                DESCRIPTIONS[4] + DESCRIPTIONS[5]);
        }
    }

    public void atStartOfTurn()
    {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(this.card, 1));
        if (this.amount == 1) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
        updateDescription();
    }

}
