package administrix.cards.uncommon.skill;

import administrix.AdministrixMod;
import administrix.actions.ConspiracyAction;
import administrix.actions.CosmosDiscardToHandAction;
import administrix.cards.AbstractAdministrixCard;
import administrix.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.SetupAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CosmosControl extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:CosmosControl";
    public static final String NAME = "Cosmos Control";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int TUTOR_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public CosmosControl() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.COSMOS_CONTROL, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseMagicNumber = this.magicNumber = TUTOR_AMOUNT;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new CosmosDiscardToHandAction(TUTOR_AMOUNT, p));
        AbstractDungeon.actionManager.addToBottom(new SetupAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new CosmosControl();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}