package administrix.cards.uncommon.skill;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;
import administrix.powers.YangPower;

import static administrix.AdministrixMod.*;

public class BishamonBlessing extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:BishamonBlessing";
    public static final String NAME = "Bishamon's Blessing";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = -2;
    private static final int YANG_AMOUNT = 3;
    private static final int VULN_AMOUNT = 1;
    private static final int DRAW_AMOUNT = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public BishamonBlessing() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.BISHAMON_BLESSING, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.LichGold,
                rarity, target);
        setBannerTexture(UNCOMMON_BANNER_LICH_GOLD_PLOT, UNCOMMON_BANNER_PORTRAIT);
        setBackgroundTexture(SKILL_LICH_GOLD_PLOT, SKILL_LICH_GOLD_PLOT_PORTRAIT);
        this.baseMagicNumber = this.magicNumber = YANG_AMOUNT;
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;
    }

    public void triggerWhenDrawn()
    {
        this.superFlash();
        for (int i = 0; i < mastermindCheck(); i++) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new YangPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, VULN_AMOUNT, false), VULN_AMOUNT, true, AbstractGameAction.AttackEffect.NONE));
            }
            if (this.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, DRAW_AMOUNT));
            }
        }
    }

    public void triggerOnManualDiscard()
    {
        this.superFlash();
        for (int i = 0; i < mastermindCheck(); i++) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new YangPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, VULN_AMOUNT, false), VULN_AMOUNT, true, AbstractGameAction.AttackEffect.NONE));
            }
            if (this.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, DRAW_AMOUNT));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BishamonBlessing();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}