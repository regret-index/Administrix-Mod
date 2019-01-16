package administrix.cards.uncommon.attack;

import administrix.actions.TransposeAction;
import administrix.cards.AbstractAdministrixCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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

public class SeventeenArticleLaser extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:SeventeenArticleLaser";
    public static final String NAME = "17-Article Laser";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType type = CardType.ATTACK;
    private static final int ATTACK_DMG = 17;
    private static final int TRANSPOSE_AMOUNT = 1;

    public SeventeenArticleLaser() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.SEVENTEEN_ARTICLE_LASER,
                COST, CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, TARGET);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.isMultiDamage = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        // Damage upgrading to damage all, transpose.
        if (this.upgraded) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo != null) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(mo.drawX, mo.drawY), 0.05F));
                }
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        } else {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.05F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }

        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.addToBottom(new TransposeAction(TRANSPOSE_AMOUNT));
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
            this.target = CardTarget.ALL_ENEMY;
            this.isMultiDamage = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
