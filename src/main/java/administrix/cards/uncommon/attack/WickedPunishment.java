package administrix.cards.uncommon.attack;

import administrix.cards.AbstractAdministrixCard;
import administrix.vfx.FlashySlamEffect;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.YinPower;

public class WickedPunishment extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:WickedPunishment";
    public static final String NAME = "Wicked Punishment";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;
    private static final CardType type = CardType.ATTACK;
    private static final int ATTACK_DMG = 6;
    private static final int UPGRADE_ATTACK_DMG = 2;
    private static final int DRAW_AMOUNT = 2;
    private static final int YIN_AMOUNT = 3;
    private static final int YIN_STACKS_AMOUNT = 2;
    private static final int UPGRADE_YIN_AMOUNT = 1;

    public WickedPunishment() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.WICKED_PUNISHMENT, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = YIN_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if ((p != null) && (m != null)) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlashySlamEffect(p.hb.cX, p.hb.cY, m.hb.cX, new Color(0.30F, 0.05F, 0.35F, 0.0F)), 0.4F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        if ((p != null) && (m != null)) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlashySlamEffect(p.hb.cX, p.hb.cY, m.hb.cX, new Color(0.30F, 0.05F, 0.35F, 0.0F)), 0.4F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW_AMOUNT));
        for (int i = 0; i < YIN_STACKS_AMOUNT; i++) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YinPower(p, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new WickedPunishment();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_ATTACK_DMG);
            this.upgradeMagicNumber(UPGRADE_YIN_AMOUNT);
        }
    }

}