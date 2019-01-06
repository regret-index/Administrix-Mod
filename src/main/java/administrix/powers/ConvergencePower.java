package administrix.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import administrix.cards.common.attack.Daybreak;
import administrix.cards.common.skill.Nightfall;

import static administrix.AdministrixMod.ADMIN_POWERS_ATLAS;

public class ConvergencePower extends AbstractPower {
    public static final String POWER_ID = "AdministrixMod:Convergence";
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ConvergencePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = ADMIN_POWERS_ATLAS.findRegion("convergence_48");
        this.region128 = ADMIN_POWERS_ATLAS.findRegion("convergence");
    }

    public void atStartOfTurn()
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Daybreak(), this.amount));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Nightfall(), this.amount));
        }
    }

    public void updateDescription()
    {
        if (this.amount > 1) {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
        } else {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]);
        }
    }

}