package administrix.cards.uncommon.skill;

import administrix.cards.AbstractAdministrixCard;
import administrix.vfx.FlaskBounceEffect;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.WiltingPower;

public class MixOfMercury extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:MixOfMercury";
    public static final String NAME = "Mix Of Mercury";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final int THORNS_AMOUNT = 2;
    private static final int WILTING_AMOUNT = 2;
    private static final int ARTIFACT_AMOUNT = 1;
    private static final int UPGRADE_ARTIFACT_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;

    public MixOfMercury() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.MIX_OF_MERCURY, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.baseMagicNumber = this.magicNumber = ARTIFACT_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlaskBounceEffect(p.hb.cX, p.hb.cY, p.hb.cX, p.hb.cY, new Color(0.15F, 0.35F, 0.40F, 1.0F)), 0.4F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, THORNS_AMOUNT), THORNS_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WiltingPower(p, WILTING_AMOUNT, false), WILTING_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MixOfMercury();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_ARTIFACT_AMOUNT);
        }
    }

}