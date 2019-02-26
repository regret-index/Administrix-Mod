package administrix.cards.common.skill;

import administrix.AdministrixMod;
import administrix.cards.AbstractAdministrixCard;
import administrix.cards.common.attack.Daybreak;
import administrix.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SyncreticSurge extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:SyncreticSurge";
    public static final String NAME = "Syncretic Surge";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;

    public SyncreticSurge() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.SYNCRETIC_SURGE, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        int cardAmount = 0;

        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == AbstractCard.CardType.SKILL) {
                cardAmount++;
            }
        }
        if (this.upgraded) {
            AbstractCard d = new Daybreak().makeCopy();
            d.upgrade();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(d, cardAmount));
        } else {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Daybreak(), cardAmount));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SyncreticSurge();
    }

    @Override
    public void applyPowers()
    {
        int cardAmount = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == AbstractCard.CardType.SKILL) {
                cardAmount++;
            }
        }

        this.magicNumber = this.baseMagicNumber = cardAmount + 1;

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