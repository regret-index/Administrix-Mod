package administrix.patches;

import administrix.characters.Administrix;
import administrix.relics.AlphaAttackCounter;
import administrix.relics.AlphaSkillCounter;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

// Provide two UI relics at the start of each game, not as starter relics.

@SpirePatch(
        clz = CardCrawlGame.class,
        method = "update"
)

public class UIRelicsPatch {

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(CardCrawlGame _c) {

        if (AbstractDungeon.player instanceof Administrix)
        {
            RelicLibrary.getRelic(AlphaAttackCounter.ID).makeCopy().instantObtain(AbstractDungeon.player, 1, false);
            RelicLibrary.getRelic(AlphaSkillCounter.ID).makeCopy().instantObtain(AbstractDungeon.player, 2, false);

            AbstractDungeon.relicsToRemoveOnStart.add(AlphaAttackCounter.ID);
            AbstractDungeon.relicsToRemoveOnStart.add(AlphaSkillCounter.ID);

            UnlockTracker.markRelicAsSeen(AlphaAttackCounter.ID);
            UnlockTracker.markRelicAsSeen(AlphaSkillCounter.ID);
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.NewExprMatcher("com.megacrit.cardcrawl.screens.DungeonTransitionScreen");

            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}