package administrix.actions;

import administrix.AdministrixMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.Iterator;

public class MetempsychosisAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("MetempsychosisAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;

    public MetempsychosisAction(int amount) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_MED) {

            CardGroup tmp = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);

            if (this.p.drawPile.group.size() <= this.amount) {
                this.isDone = true;
            } else {
                for (AbstractCard c : this.p.drawPile.group) {
                    tmp.addToRandomSpot(c);
                }

                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[0], false, false, false, false);
                this.tickDuration();
            }
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    card = (AbstractCard)var1.next();
                    card.unhover();

                    this.p.drawPile.removeCard(card);
                    this.p.drawPile.addToTop(card);
                }

                this.isDone = true;
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        this.tickDuration();
    }
}