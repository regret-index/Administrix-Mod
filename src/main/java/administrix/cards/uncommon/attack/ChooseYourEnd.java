package administrix.cards.uncommon.attack;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import administrix.AdministrixMod;
import administrix.cards.starter.BlueCloak;
import administrix.cards.starter.RedCloak;
import administrix.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.vfx.combat.WaterDropEffect;

public class ChooseYourEnd extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:ChooseYourEnd";
    public static final String NAME = "ChooseYourEnd";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;
    private static final CardType type = CardType.ATTACK;
    private static final int ATTACK_DMG = 10;
    private static final int UPGRADE_ATTACK_DMG = 4;
    private static final int CARD_AMOUNT = 1;

    public ChooseYourEnd() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.CHOOSE_YOUR_END,
                COST, CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new RedCloak(), CARD_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new BlueCloak(), CARD_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));
        AbstractDungeon.effectsQueue.add(new WaterDropEffect(m.hb.cX - 50.0F * Settings.scale, m.hb.cY - 50.0F * Settings.scale));
        AbstractDungeon.effectsQueue.add(new WaterDropEffect(m.hb.cX + 50.0F * Settings.scale, m.hb.cY + 50.0F * Settings.scale));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ChooseYourEnd();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeDamage((UPGRADE_ATTACK_DMG));
        }
    }

}