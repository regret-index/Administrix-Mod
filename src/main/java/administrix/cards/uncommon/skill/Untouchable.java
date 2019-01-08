package administrix.cards.uncommon.skill;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

public class Untouchable extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:Untouchable";
    public static final String NAME = "Untouchable";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 0;
    private static final int WEAK_AMOUNT = 1;
    private static final int DEX_AMOUNT = 3;
    private static final int UPGRADE_DEX_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF_AND_ENEMY;

    public Untouchable() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.UNTOUCHABLE, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseMagicNumber = this.magicNumber = DEX_AMOUNT;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, WEAK_AMOUNT, false), WEAK_AMOUNT));
        if ((m != null) && ((m.intent == AbstractMonster.Intent.ATTACK) || (m.intent == AbstractMonster.Intent.ATTACK_BUFF) || (m.intent == AbstractMonster.Intent.ATTACK_DEBUFF) || (m.intent == AbstractMonster.Intent.ATTACK_DEFEND))) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                    new DexterityPower(p, this.magicNumber), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                    new LoseDexterityPower(p, this.magicNumber), this.magicNumber));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new Untouchable();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_DEX_AMOUNT);
        }
    }

}