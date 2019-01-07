package administrix.cards.rare.attack;

import administrix.powers.YangPower;
import administrix.powers.YinPower;
import administrix.vfx.BlazingImpactEffect;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

public class NewbornDivinity extends CustomCard
{
    public static final String ID = "AdministrixMod:NewbornDivinity";
    public static final String NAME = "Newborn Divinity";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int YANG_SCALE = 2;
    private static final int UPGRADE_YANG_SCALE = 1;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF_AND_ENEMY;
    private static final CardType type = CardType.ATTACK;

    public NewbornDivinity() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.NEWBORN_DIVINITY, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = 0;
        this.baseMagicNumber = this.magicNumber = YANG_SCALE;
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        // Deal damage scaled to how much more Yin you have than Yang.
        int yinAmount = p.hasPower(YinPower.POWER_ID) ?
                        p.getPower(YinPower.POWER_ID).amount : 0;
        int yangAmount = p.hasPower(YangPower.POWER_ID) ?
                         p.getPower(YangPower.POWER_ID).amount : 0;
        int difference = yangAmount - yinAmount;

        this.damage = this.baseDamage = (difference > 0) ?
                                        difference * this.magicNumber : 0;
        applyPowers();
        calculateCardDamage(null);

        if (this.damage > 0) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.GOLD, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.20F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.WHITE, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.33F));
            if (this.damage < 25) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            } else if (this.damage < 50) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
            } else if (this.damage < 75) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(m.hb.cX - 60.0F * Settings.scale, m.hb.cY, false), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
            } else {
                if (m != null) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new BlazingImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F, new Color(0.95F, 0.90F, 0.8F, 0.9F) )));
                }
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(AbstractDungeon.player));
    }

    public void applyPowers()
    {
        int yinAmount = AbstractDungeon.player.hasPower(YinPower.POWER_ID) ?
                        AbstractDungeon.player.getPower(YinPower.POWER_ID).amount : 0;
        int yangAmount = AbstractDungeon.player.hasPower(YangPower.POWER_ID) ?
                         AbstractDungeon.player.getPower(YangPower.POWER_ID).amount : 0;
        int difference = yangAmount - yinAmount;

        this.damage = this.baseDamage = (difference > 0) ? difference * this.magicNumber : 0;
        super.applyPowers();

        if (this.baseDamage > 0)
        {
            this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0]);
            initializeDescription();
        }
    }

    public void onMoveToDiscard()
    {
        this.rawDescription = (DESCRIPTION);
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo)
    {
        super.calculateCardDamage(mo);
        if (this.baseDamage > 0) {
            this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0]);
        }
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new NewbornDivinity();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_YANG_SCALE);
        }
    }

}