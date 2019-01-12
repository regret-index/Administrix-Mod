package administrix.actions;

// This is essentially a mildly adjusted version of
// the standard Reprogram action, to make it trigger Plot effects
// and get a more specific UI string. Thanks, megacrit.

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ConspiracyAction
        extends AbstractGameAction
{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ConspiracyAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private float startingDuration;

    public ConspiracyAction(int numCards)
    {
        this.amount = numCards;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update()
    {
        CardGroup tmpGroup;
        if (this.duration == this.startingDuration)
        {
            if (AbstractDungeon.player.drawPile.isEmpty())
            {
                this.isDone = true;
                return;
            }
            tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); i++) {
                tmpGroup.addToTop((AbstractCard)AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
            }
            AbstractDungeon.gridSelectScreen.open(tmpGroup, this.amount, true, TEXT[0]);
        }
        else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                // For some reason, other uses of discards are fine with
                // MoultingPatch's locator, but this needs a forced use...
                if (AbstractDungeon.player.hasPower("AdministrixMod:Moulting")) {
                    AbstractDungeon.player.getPower("AdministrixMod:Moulting").onSpecificTrigger();
                }
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        tickDuration();
    }
}
