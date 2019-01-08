package administrix.cards.rare.power;

import administrix.cards.AbstractAdministrixCard;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.AdrenalineEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.WiltingPower;

public class FearOfDeath extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:FearOfDeath";
    public static final String NAME = "Fear Of Death";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 0;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardType type = CardType.POWER;
    private static final int FRAIL_AMOUNT = 3;
    private static final int WILTING_AMOUNT = 3;
    private static final int BUFF_AMOUNT = 4;
    private static final int UPGRADE_BUFF_AMOUNT = 3;

    public FearOfDeath() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.FEAR_OF_DEATH, COST,
                CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = BUFF_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new AdrenalineEffect(), 0.15F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, FRAIL_AMOUNT, false), FRAIL_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WiltingPower(p, WILTING_AMOUNT, false), WILTING_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber), this.magicNumber));
        int count = BaseMod.MAX_HAND_SIZE;
        if (count != 0)
        {
            AbstractDungeon.actionManager.addToTop(new DiscardAction(p, p, count, true));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FearOfDeath();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_BUFF_AMOUNT);
        }
    }

}