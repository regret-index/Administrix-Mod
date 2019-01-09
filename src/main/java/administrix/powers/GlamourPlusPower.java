package administrix.powers;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

public class GlamourPlusPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:GlamourPlus";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GlamourPlusPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("glamour_plus_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("glamour_plus");
    }

    public void atStartOfTurn()
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            flash();
            for (int i = 0; i < this.amount; i++) {
                AbstractCard c = AbstractDungeon.getCard(AbstractCard.CardRarity.RARE, AbstractDungeon.cardRandomRng).makeCopy();
                if (c.cost > 0)
                {
                    c.cost = 0;
                    c.costForTurn = 0;
                    c.isCostModified = true;
                }
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, 1, false));
            }
        }
    }

    public void atStartOfTurnPostDraw()
    {
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(this.owner, this.owner, this.amount, false));
    }

    public void updateDescription()
    {
        if (this.amount > 1) {
            this.description = (DESCRIPTIONS[0] + this.amount +
                                DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3]);
        } else {
            this.description = (DESCRIPTIONS[0] + this.amount +
                                DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[4]);
        }
    }

}