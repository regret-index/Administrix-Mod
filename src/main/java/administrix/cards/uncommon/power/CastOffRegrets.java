package administrix.cards.uncommon.power;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.MoultingPower;

public class CastOffRegrets extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:CastOffRegrets";
    public static final String NAME = "Cast Off Regrets";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardType type = CardType.POWER;
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int MOULTING_AMOUNT = 2;
    private static final int FRAIL_AMOUNT = 2;

    public CastOffRegrets() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.CAST_OFF_REGRETS, COST,
                CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = MOULTING_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, FRAIL_AMOUNT, false), FRAIL_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MoultingPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CastOffRegrets();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(UPGRADE_COST);
        }
    }

}