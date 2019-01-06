package administrix.cards.common.skill;

import administrix.AdministrixMod;

import administrix.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class SyncreticPlunge extends CustomCard
{
    public static final String ID = "AdministrixMod:SyncreticPlunge";
    public static final String NAME = "Syncretic Plunge";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;

    public SyncreticPlunge() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.SYNCRETIC_PLUNGE, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        int cardAmount = 0;
        AbstractDungeon.actionManager.addToBottom(new DiscardPileToTopOfDeckAction(p));

        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == CardType.ATTACK) {
                cardAmount++;
            }
        }
        if (this.upgraded) {
            AbstractCard n = new Nightfall().makeCopy();
            n.upgrade();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(n, cardAmount));
        } else {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Nightfall(), cardAmount));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SyncreticPlunge();
    }

    @Override
    public void applyPowers()
    {
        int cardAmount = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == CardType.ATTACK) {
                cardAmount++;
            }
        }

        this.magicNumber = this.baseMagicNumber = cardAmount;

        if (this.upgraded) {
            if (this.magicNumber == 1) {
                this.rawDescription = (UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[1]);
                initializeDescription();
            } else {
                this.rawDescription = (UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0]);
                initializeDescription();
            }
        } else {
            if (this.magicNumber == 1) {
                this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[1]);
                initializeDescription();
            } else {
                this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0]);
                initializeDescription();
            }
        }
    }

    @Override
    public void onMoveToDiscard()
    {
        if (this.upgraded) {
            this.rawDescription = (UPGRADE_DESCRIPTION);
        } else {
            this.rawDescription = (DESCRIPTION);
        }
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}