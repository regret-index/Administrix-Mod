package administrix.cards;

import administrix.powers.MastermindPower;
import basemod.abstracts.CustomCard;
import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;

// TODO: Functionize plot backgrounds / scroll.
// For the full release, probably.

public abstract class AbstractAdministrixCard extends CustomCard {
    public int baseSecondMagicNumber;
    public int secondMagicNumber;
    public boolean isSecondMagicNumberModified;
    public boolean upgradedSecondMagicNumber;

    public static final Color PLOT_PURPLE = CardHelper.getColor(205.0f, 25.0f, 250.0f);

    public AbstractAdministrixCard(final String id, final String name, final String img, final int cost, final String rawDescription,
                                   final AbstractCard.CardType type, final AbstractCard.CardColor color,
                                   final AbstractCard.CardRarity rarity, final AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public int mastermindCheck() {
        return (AbstractDungeon.player.hasPower(MastermindPower.POWER_ID)? 2 : 1);
    }

    public void upgradeSecondMagicNumber(int amount) {
        this.baseSecondMagicNumber += amount;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.upgradedSecondMagicNumber = true;
    }

    public static class SecondMagicNumber extends DynamicVariable {

        @Override
        public int baseValue(AbstractCard card) {
            return ((AbstractAdministrixCard)card).baseSecondMagicNumber;
        }

        @Override
        public boolean isModified(AbstractCard card) {
            return ((AbstractAdministrixCard)card).isSecondMagicNumberModified;
        }

        @Override
        public String key() {
            return "ax:M2";
        }

        @Override
        public boolean upgraded(AbstractCard card) {
            return ((AbstractAdministrixCard)card).upgradedSecondMagicNumber;
        }

        @Override
        public int value(AbstractCard card) {
            return ((AbstractAdministrixCard)card).secondMagicNumber;
        }
    }
}