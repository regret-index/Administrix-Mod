package administrix.actions;

// This is a further-adjusted version of the DiscardPileToTopOfDeck action:
// moving to the hand instead, allowing multiple, discounting cards grabbed.
// Thanks, megacrit.

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class CosmosDiscardToHandAction
        extends AbstractGameAction
{
    private static final UIStrings uiStrings =
            CardCrawlGame.languagePack.getUIString("DiscardPileToHandAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private static String COUNT_TEXT;
    private AbstractPlayer p;

    public CosmosDiscardToHandAction(int num, AbstractCreature source)
    {
        this.amount = num;
        this.p = AbstractDungeon.player;
        setValues(null, source, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update()
    {
        if (AbstractDungeon.getCurrRoom().isBattleEnding())
        {
            this.isDone = true; return;
        }
        AbstractCard tmp;
        if (this.duration == Settings.ACTION_DUR_FASTER)
        {
            if (this.p.discardPile.isEmpty())
            {
                this.isDone = true;
                return;
            }
            if (this.p.discardPile.size() <= this.amount)
            {
                tmp = this.p.discardPile.getTopCard();
                tmp.freeToPlayOnce = true;
                this.p.discardPile.removeCard(tmp);
                this.p.hand.addToHand(tmp);
            }
            if (this.p.discardPile.group.size() > this.amount)
            {
                AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.amount, TEXT[0], false, false, false, false);
                tickDuration();
                return;
            }
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                if (c.cost > 0) {
                    c.freeToPlayOnce = true;
                }
                this.p.discardPile.removeCard(c);
                this.p.hand.addToHand(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
    }
}
