package administrix.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import administrix.cards.common.attack.Daybreak;
import administrix.cards.common.skill.Nightfall;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

// Add an upgraded Daybreak and Nightfall to your hand every turn.

public class ConvergencePlusPower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:ConvergencePlus";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ConvergencePlusPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("convergence_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("convergence_plus");
    }

    public void updateDescription()
    {
        if (this.amount > 1) {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
        } else {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]);
        }
    }

    public void atStartOfTurn()
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            AbstractCard d = new Daybreak().makeCopy();
            AbstractCard n = new Nightfall().makeCopy();
            d.upgrade();
            n.upgrade();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(d, this.amount));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(n, this.amount));
        }
    }

}