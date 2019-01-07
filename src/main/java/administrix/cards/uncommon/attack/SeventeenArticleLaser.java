package administrix.cards.uncommon.attack;

import administrix.actions.TransposeAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.YangPower;
import administrix.powers.YinPower;

public class SeventeenArticleLaser extends CustomCard
{
    public static final String ID = "AdministrixMod:SeventeenArticleLaser";
    public static final String NAME = "17-Article Laser";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;
    private static final CardType type = CardType.ATTACK;
    private static final int ATTACK_DMG = 17;
    private static final int TAIJITU_AMOUNT = 17;
    private static final int TRANSPOSE_AMOUNT = 1;

    public SeventeenArticleLaser() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.SEVENTEEN_ARTICLE_LASER,
                COST, CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = TAIJITU_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {

        // Damage, transpose, gain the lesser of Yin and Yang if upgraded.
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.05F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));

        AbstractDungeon.actionManager.addToBottom(new TransposeAction(TRANSPOSE_AMOUNT));

        if (this.upgraded) {
            int yinAmount = p.hasPower(YinPower.POWER_ID) ?
                            p.getPower(YinPower.POWER_ID).amount : 0;
            int yangAmount = p.hasPower(YangPower.POWER_ID) ?
                             p.getPower(YangPower.POWER_ID).amount : 0;

            if (yinAmount > yangAmount)
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YangPower(p, this.magicNumber), this.magicNumber));
            }
            else if (yangAmount > yinAmount)
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YinPower(p, this.magicNumber), this.magicNumber));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SeventeenArticleLaser();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
