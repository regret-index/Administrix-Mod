package administrix.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

import java.util.ArrayList;

// This was coded by Robin-MK0.5. Many thanks to her.

public class TransposeAction extends AbstractGameAction {
    private static final UIStrings uiStrings =
            com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getUIString("TransposeAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private static String TRANPOSE_DOWN;
    private static String TRANSPOSE_UP;
    private float startingDuration;

    private boolean doneDiscard;
    private boolean setUpReturn;
    private boolean doneReturn;
    private final ArrayList<AbstractCard> cardsToDiscard = new ArrayList<>();
    private final ArrayList<AbstractCard> cardsToReturn = new ArrayList<>();

    public TransposeAction(int num) {
        this.amount = num;
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DISCARD;
        this.startingDuration = com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        if (this.amount == 1) {
            TRANPOSE_DOWN = TEXT[0];
            TRANSPOSE_UP = TEXT[3];
        } else if (this.amount == 2) {
            TRANPOSE_DOWN = TEXT[1];
            TRANSPOSE_UP = TEXT[4];
        } else {
            TRANPOSE_DOWN = TEXT[2];
            TRANSPOSE_UP = TEXT[5];
        }
    }

    public void update() {
        CardGroup tmpGroup;

        if (this.duration == this.startingDuration)
        {
            if (AbstractDungeon.player.drawPile.isEmpty())
                doneDiscard = true;
            else if (AbstractDungeon.player.drawPile.size() <= this.amount)
            {
                for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    cardsToDiscard.add(c);
                }
                doneDiscard = true;
            }
            else
            {
                 CardCrawlGame.sound.play("TH-MENU-OK");
                 tmpGroup = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
                 for (int i = 0; i < AbstractDungeon.player.drawPile.size(); i++) {
                        tmpGroup.addToTop((AbstractCard) AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
                        AbstractDungeon.gridSelectScreen.open(tmpGroup, this.amount, TRANPOSE_DOWN, false, false, false, false);
                 }
            }
        }
        else if (!doneDiscard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                cardsToDiscard.add(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            doneDiscard = true;
        }

        if (doneDiscard && !doneReturn)
        {
            if (!setUpReturn)
            {
                if (AbstractDungeon.player.discardPile.isEmpty())
                {
                    doneReturn = true;
                }
                else if (AbstractDungeon.player.discardPile.size() <= this.amount)
                {
                    for (AbstractCard c : AbstractDungeon.player.discardPile.group)
                    {
                        cardsToReturn.add(c);
                    }
                    doneReturn = true;
                }
                else if (AbstractDungeon.player.discardPile.group.size() > this.amount)
                {
                        CardCrawlGame.sound.play("TH-MENU-CONFIRM");
                        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.discardPile, amount, TRANSPOSE_UP, false, false, false, false);
                }
                setUpReturn = true;
            }

            if (!doneReturn && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
            {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
                {
                    cardsToReturn.add(c);
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                doneReturn = true;
            }
        }

        if (doneReturn)
        {
            for (AbstractCard c : cardsToDiscard)
            {
                AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }

            for (AbstractCard c : cardsToReturn)
            {
                AbstractDungeon.player.discardPile.removeCard(c);
                AbstractDungeon.player.discardPile.moveToBottomOfDeck(c);
            }

            isDone = true;
        }

        tickDuration();
    }

}