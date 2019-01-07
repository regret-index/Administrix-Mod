The Administrix, the lich princess of the rising sun,
is inspired by Toyosatomimi no Miko (豊聡耳 神子),
a character from the Touhou Project series.

Latest releases can be found at
https://api.github.com/repos/regret-index/Administrix-Mod/releases/latest.

--~== Credits ==~--
 * index-j: Content code, endlessly playtesting and debugging.
 * Robin-MK0.5: Card edit art, playtesting, advanced feature debugging,
   constant support
.
 * Prominent playtesters: ...?
.
 * Twilight Frontier: Official art assets for character art.
 * ZUN: For creating the Touhou Project.
 * Megacrit: For creating Slay the Spire.

--~== Summation ==~--
This card set focuses on themes of duality, synergy, and deck manipulation:
 * cards with effects on both drawing and discarding them,
 * many sources of Artifact to grasp onto transient power
   or brush off toxic side-effects,
 * and a manipulable duo of card fuel in Yin and Yang.
Side-themes include:
 * caring about card types played,
 * flexible card creation,
 * and swapping cards between one's hand, the Draw Pile, and the Discard Pile.
This release contains 70 character-unique cards of a total planned 80,
plus four relics out of a total planned ten and one potion of two.

Design goals have been to make an internally consistent character, with decently
reliable representation and support for multiple interwoven deck archetypes
(Yin-focus vs Yang-focus vs balance + Artifact + Plot abuse). The Administrix
readily uses multi-function cards plus flexible and automatic defenses to
constantly scale up from weaker base cards; playing her can start out slow
in understanding very fully cards, but she has many ways to gain many buffs
as she edges ever closer to becoming a legend.

--~== Known Issues ==~--
 * Playing cards too quickly can sometimes have tooltips or targetting arrows
   mess up the Transpose interface.
 * Rising Sun's Prince can inflict permanent Strength loss against enemies with
   Artifact when drawn in a precise order with other Plot cards.

--~== New keywords ==~--

For those who care about such, card spoilers follow here. This character's list
of keywords is definitely long and complicated enough to deserve this full
write-up, though, as a reference point. This is on the Github page too, after
all. (Note that spreadsheet links and copies of the StS extractor are attached
to each release, for those who wish to spoil themselves completely.)

==== Plot ====
 * Cards with this have an effect on drawing them, the same effect on manually
   discarding them, and an entirely different effect on playing them.
   These are primarily defensive, in contrast to the Silent's utility focus
   and the Servant's offensive focus, though strong counterexamples still exist.
   Two 0-cost cards in the starting deck draw and discard on play, plus apply
   debuff effects through Plot.
== Transpose ==
 * This action is to discard a card from the Draw Pile and then move a card
   from the Discard Pile to the bottom of the Draw Pile. The starting relic
   provides a constant source of this, and so do a slim number of other cards.
   This triggers Plot effects too, though it's a trade of actively choosing such
   versus being able to trigger them twice when drawn.
==== Duality ====
 * Duality provides temporary Strength and Dexterity after each turn
   one plays an equal number of Attacks and Skills (eventually with a unique UI
   to help keep track). Do remember, temporary stats are executed as a stacking
   debuff that applies another debuff to return to normal at the end of the
   turn: try to gather much of it before gaining Artifact to keep it all.
== Wilting ==
 * This self-inflicted debuff (blockably) damages the player whenever they
   play a card. It is joined by Frail and two variations of Draw Reduction as
   the self-debuffs in this card set, and also vies for the use of one's own
   Artifact- transition rituals take a toll on the body, after all.
==== Yin, Yang ====
 * These two buffs do nothing by themselves. They're incidentally provided by
   many cards, provide strong scaling for yet more cards, and slowly equalize
   each turn. (This'll also kept on the card-count UI readied for Duality-
   the Administrix has absurd numbers of buffs to keep track of.)
== Affinity ==
 * One reocurring use of Yin and Yang provides a benefit for focusing on one or
   the other. With this power, if you have more Yang than Yin, then whenever
   you gain Yang you also gain Block. Alternatively, if you have more Yin than
   Yang, then whenever you gain Yin, you also deal damage to all enemies twice.
   A card in the starting deck provides little beyond a source of this;
   it balances out the Plot cards in the starting deck.
== Daybreaks, Nightfalls ==
 * These are 0-cost weak and Ethereal cards that Exhaust when played.
   Daybreaks deal 3 (5) damage and also provide Yang, while Nightfalls gain 3
   (5) block and also provide Yin. These scale strongly to Strength and 
   Dexterity gain, can be played or ignored to balance for Duality and Affinity,
   plus readily help trigger the starting relic's conditions- these are well 
   beyond Shivs. Be careful with playing these against Time Eater and the Heart!

...

Thanks for reading all of this, if you did!