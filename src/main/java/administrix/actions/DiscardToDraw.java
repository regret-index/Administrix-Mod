package administrix.actions;

// This is essentially a mildly adjusted version
// of the standard DiscardPileToTopOfDeck action, allowing
// pulling out more than one card. Thanks, megacrit.

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class DiscardToDraw
        extends AbstractGameAction
{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardToDrawAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private static String COUNT_TEXT;
    private AbstractPlayer p;

    public DiscardToDraw(int num, AbstractCreature source)
    {
        this.amount = num;
        this.p = AbstractDungeon.player;
        setValues(null, source, this.amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;

        if (this.amount == 2) {
            COUNT_TEXT = TEXT[0];
        } else if (this.amount == 3) {
            COUNT_TEXT = TEXT[1];
        } else {
            COUNT_TEXT = TEXT[2];
        }
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
                this.p.discardPile.removeCard(tmp);
                this.p.discardPile.moveToBottomOfDeck(tmp);
            }
            if (this.p.discardPile.group.size() > this.amount)
            {
                AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.amount, COUNT_TEXT, false, false, false, false);
                tickDuration();
                return;
            }
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                this.p.discardPile.removeCard(c);
                this.p.discardPile.moveToBottomOfDeck(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
    }
}
