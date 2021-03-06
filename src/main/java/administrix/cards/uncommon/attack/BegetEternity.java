package administrix.cards.uncommon.attack;

import administrix.AdministrixMod;
import administrix.cards.AbstractAdministrixCard;
import administrix.patches.AbstractCardEnum;
import administrix.vfx.FlashySlamEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class BegetEternity extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:BegetEternity";
    public static final String NAME = "Beget Eternity";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 3;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;
    private static final CardType type = CardType.ATTACK;
    private static final int ATTACK_DMG = 7;
    private static final int UPGRADE_ATTACK_DMG = 2;
    private static final int DRAW_AMOUNT = 1;
    private static final int STRENGTH_AMOUNT = 2;
    private static final int UPGRADE_STRENGTH_AMOUNT = 1;

    public BegetEternity() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.BEGET_ETERNITY, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = STRENGTH_AMOUNT;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if ((p != null) && (m != null)) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlashySlamEffect(p.hb.cX, p.hb.cY, m.hb.cX, new Color(0.35F, 0.05F, 0.10F, 0.0F)), 0.4F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        if ((p != null) && (m != null)) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlashySlamEffect(p.hb.cX, p.hb.cY, m.hb.cX, new Color(0.10F, 0.05F, 0.35F, 0.0F)), 0.4F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        if ((p != null) && (m != null)) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlashySlamEffect(p.hb.cX, p.hb.cY, m.hb.cX, new Color(0.30F, 0.05F, 0.35F, 0.0F)), 0.4F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, DRAW_AMOUNT), DRAW_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BegetEternity();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_ATTACK_DMG);
            this.upgradeMagicNumber(UPGRADE_STRENGTH_AMOUNT);
        }
    }

}