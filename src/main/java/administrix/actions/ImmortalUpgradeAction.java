package administrix.actions;

// Whenever an Immortal card is drawn, upgrade all Immortal cards this combat
// (or just the ones in your hand, if this is the base.) Finnicky,
// as is the rest of Miko, but interesting, I hope.

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import administrix.cards.common.attack.ImmortalGrace;
import administrix.cards.common.skill.ImmortalPurity;
import administrix.cards.rare.power.ImmortalClarity;
// import administrix.cards.rare.power.ImmortalClarity;


public class ImmortalUpgradeAction
        extends AbstractGameAction
{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardPileToTopOfDeckAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private boolean upgraded = false;

    public ImmortalUpgradeAction(boolean immortalPlus)
    {
        this.p = AbstractDungeon.player;
        setValues(null, source, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.upgraded = immortalPlus;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
            return;
        }
        for (AbstractCard c : this.p.hand.group) {
            if (c instanceof ImmortalGrace ||
                    c instanceof ImmortalPurity ||
                    c instanceof ImmortalClarity) {
                c.upgrade();
                c.superFlash();
            }
        }
        if (this.upgraded) {
            for (AbstractCard c : this.p.drawPile.group) {
                if (c instanceof ImmortalGrace ||
                        c instanceof ImmortalPurity ||
                        c instanceof ImmortalClarity) {
                    c.upgrade();
                }
            }
            for (AbstractCard c : this.p.discardPile.group) {
                if (c instanceof ImmortalGrace ||
                        c instanceof ImmortalPurity ||
                        c instanceof ImmortalClarity) {
                    c.upgrade();
                }
            }
            for (AbstractCard c : this.p.exhaustPile.group) {
                if (c instanceof ImmortalGrace ||
                        c instanceof ImmortalPurity ||
                        c instanceof ImmortalClarity) {
                    c.upgrade();
                }
            }
        }
        this.isDone = true;
    }
}