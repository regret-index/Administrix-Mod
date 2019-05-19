package administrix.characters;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import java.util.ArrayList;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameTips;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Collections;

public class AdminGameTips extends GameTips {
    private static final UIStrings axTipsText;
    private ArrayList<String> axTips = new ArrayList();
    private boolean flip = true;

    public AdminGameTips() {
        this.reinitialize();
    }

    public void reinitialize() {
        super.initialize();
        Collections.addAll(axTips, axTipsText.TEXT);
        Collections.shuffle(axTips);
    }

    public String getTip() {
        if (!(Settings.language == Settings.GameLanguage.ENG && AbstractDungeon.player instanceof Administrix)) {
            return super.getTip();
        } else {
            if (axTips.isEmpty()) {
                reinitialize();
                return (String)this.axTips.remove(0);
            } else {
                if (flip) {
                    flip = false;
                    return (String)this.axTips.remove(0);
                } else {
                    flip = true;
                    return super.getTip();
                }
            }
        }
    }

    static {
        axTipsText = CardCrawlGame.languagePack.getUIString("AXRandomTips");
    }
}