package administrix.cards.uncommon.skill;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.WiltingPower;

public class BloodlessSapphire extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:BloodlessSapphire";
    public static final String NAME = "Bloodless Sapphire";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 2;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;
    private static final int BLOCK_AMOUNT = 14;
    private static final int UPGRADE_BLOCK_AMOUNT = 4;
    private static final int HEAL_AMOUNT = 5;
    private static final int UPGRADE_HEAL_AMOUNT = 2;
    private static final int WILTING_AMOUNT = 2;

    public BloodlessSapphire() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.BLOODLESS_SAPPHIRE, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.baseBlock = this.block = BLOCK_AMOUNT;
        this.baseMagicNumber = this.magicNumber = HEAL_AMOUNT;
        this.exhaust = true;
        this.tags.add(AbstractCard.CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if (p.currentHealth > 0) {
            p.heal(this.magicNumber);
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WiltingPower(p, WILTING_AMOUNT, false), WILTING_AMOUNT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BloodlessSapphire();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK_AMOUNT);
            this.upgradeMagicNumber(UPGRADE_HEAL_AMOUNT);
        }
    }

}