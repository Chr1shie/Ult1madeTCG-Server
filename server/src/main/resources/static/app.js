// Reines Vanilla-JS, bewusst ohne Framework/Build-Schritt (siehe CONCEPT.md:
// Nutzer wollte "stabil und sicher" statt experimentellem Tooling). Ruft die
// /api/collection und /api/sealed JSON-Endpunkte des Servers ab.

// Dieselben Akzentfarben wie in der App (tcgAccentColor in App.kt) - eigene
// CSS-Umsetzung statt geteiltem Code, siehe CONCEPT.md.
// Zweisprachige Weboberfläche (18.08., Nutzer-Vorgabe "auch hier machen
// wir es so, dass Englisch Standard-Einstellung ist, man in den
// Einstellungen aber auch auf Deutsch stellen kann") - gleiche Bauart wie
// tr() in der App (AppLanguage.kt): Übersetzungspaare stehen direkt am
// String statt in einem zentralen Wörterbuch. Sprache liegt pro Browser
// im localStorage (wie Token/Account), Standard ist ENGLISCH. Ein Wechsel
// lädt die Seite neu - alles rendert ohnehin aus dem JS-Zustand, und ein
// Reload ist robuster, als jede Render-Funktion einzeln anzustoßen.
// Statische index.html-Texte stehen auf Englisch im Markup und tragen die
// deutsche Fassung als data-de/data-de-placeholder/data-de-aria -
// applyStaticTranslations() tauscht sie beim Start aus, falls Deutsch
// gewählt ist. Der Sprach-Umschalter selbst sitzt im Accounts-Overlay
// (renderLanguageSection()).
const LANG = localStorage.getItem("ult1madeLang") === "de" ? "de" : "en";

function tr(en, de) {
  return LANG === "de" ? de : en;
}

function setLanguage(lang) {
  localStorage.setItem("ult1madeLang", lang);
  location.reload();
}

function applyStaticTranslations() {
  document.documentElement.lang = LANG;
  if (LANG !== "de") return;
  document.querySelectorAll("[data-de]").forEach(el => { el.textContent = el.dataset.de; });
  document.querySelectorAll("[data-de-placeholder]").forEach(el => { el.placeholder = el.dataset.dePlaceholder; });
  document.querySelectorAll("[data-de-aria]").forEach(el => { el.setAttribute("aria-label", el.dataset.deAria); });
}

const GAMES = [
  { code: "DBFW", label: "Dragon Ball Fusion World", color: "#FF9800", image: "images/btn_dbfw.png" },
  { code: "Pokemon", label: "Pokémon", color: "#E3350D", image: "images/btn_pokemon.png" },
  { code: "OnePiece", label: "One Piece", color: "#FFC107", image: "images/btn_onepiece.png" },
  { code: "Digimon", label: "Digimon", color: "#1E88E5", image: "images/btn_digimon.png" },
  { code: "YuGiOh", label: "Yu-Gi-Oh!", color: "#9C27B0", image: "images/btn_yugioh.png" },
  { code: "Lorcana", label: "Disney Lorcana", color: "#0891B2", image: "images/btn_lorcana.png" },
  { code: "MTG", label: "Magic: The Gathering", color: "#C9A227", image: "images/btn_mtg.png" },
  { code: "Riftbound", label: "Riftbound", color: "#FC3F93", image: "images/btn_riftbound.png" },
  { code: "FinalFantasy", label: "Final Fantasy TCG", color: "#C0C0C0", image: "images/btn_finalfantasy.png" },
  { code: "FleshAndBlood", label: "Flesh and Blood", color: "#B87333", image: "images/btn_fleshandblood.png" },
  // Gundam Card Game + Cyberpunk TCG (09.08., Nutzer-Vorgabe) - Platzhalter-
  // Button-Bilder (siehe App.kt accentColorFor), werden ersetzt sobald der
  // Nutzer eigenes Bildmaterial liefert. Cyberpunk TCG erscheint erst
  // September 2026 und hat deshalb noch keinen Katalog, ist aber hier schon
  // gelistet wie in der App.
  { code: "Gundam", label: "Gundam Card Game", color: "#546E7A", image: "images/btn_gundam.png" },
  { code: "Cyberpunk", label: "Cyberpunk TCG", color: "#FCEE0A", image: "images/btn_cyberpunk.png" },
  // Star Wars: Unlimited + Altered (13.08. registriert, 14.08. echte
  // Button-Bilder vom Nutzer geliefert - btn_starwarsunlimited.png/
  // btn_altered.png sind keine Platzhalter mehr, siehe App.kt
  // StarWarsUnlimitedButton.kt/AlteredButton.kt für dasselbe Bildmaterial
  // in der App).
  { code: "StarWarsUnlimited", label: "Star Wars: Unlimited", color: "#E8E4DA", image: "images/btn_starwarsunlimited.png" },
  { code: "Altered", label: "Altered", color: "#1FC8C0", image: "images/btn_altered.png" },
  // Naruto Kayou (18.08., nur NA-Releases, siehe App.kt/CONCEPT.md) -
  // Platzhalter-Button bis der Nutzer eigenes Material liefert
  { code: "NarutoKayou", label: "Naruto Kayou", color: "#F4511E", image: "images/btn_naruto.png" },
];
const GAME_BY_CODE = Object.fromEntries(GAMES.map(g => [g.code, g]));

// TCGs pro Account ausblenden (17.08., Nutzer-Vorgabe "beim Server unter
// Einstellungen Pro Account TCGs ausblenden können. Standardmäßig soll
// aber alles eingeblendet sein") - die Liste kommt pro Account vom Server
// (GET/POST /api/hiddenGames, siehe Main.kt), gepflegt wird sie über die
// Account-Übersicht (renderHiddenGamesSection). Ausblenden betrifft NUR
// diese Weboberfläche (TCG-Chips-Leiste) - Karten/Daten bleiben unberührt.
let hiddenGames = new Set();

function visibleGames() {
  const visible = GAMES.filter(g => !hiddenGames.has(g.code));
  // Sicherheitsnetz: wären (z.B. durch einen von Hand editierten Settings-
  // Eintrag) ALLE TCGs ausgeblendet, bliebe die Oberfläche unbedienbar -
  // dann lieber alles zeigen. Die Pflege-UI verhindert diesen Zustand
  // ohnehin (mindestens ein TCG bleibt immer sichtbar).
  return visible.length > 0 ? visible : GAMES;
}

async function loadHiddenGames() {
  try {
    const res = await authedFetch("/api/hiddenGames");
    if (!res.ok) return;
    const data = await res.json();
    hiddenGames = new Set(data.hidden || []);
  } catch (err) {
    // Best-effort wie loadAccounts(): ohne Antwort einfach alles zeigen
    hiddenGames = new Set();
  }
  ensureActiveGameVisible();
  render();
}

// Steht das gerade aktive TCG auf der Ausblenden-Liste (z.B. nach einem
// Account-Wechsel), springt die Ansicht auf das erste sichtbare TCG statt
// eine ausgeblendete, leere Ansicht zu zeigen.
function ensureActiveGameVisible() {
  if (!visibleGames().some(g => g.code === activeGame)) {
    const fallback = visibleGames()[0];
    activeGame = fallback.code;
    setAccent(fallback.color);
    updateActionTabImages();
  }
}

// Bild-Proxy-Rückfall (17.08., Nutzer-Fund "bei Gundam gar kein Bild, bei
// One Piece nur 2 von 12"): Safari verliert beim HTTP/2-Multiplexing vieler
// paralleler Bild-Streams gegen Apaches mod_http2 (www.gundam-gcg.com,
// en.onepiece-cardgame.com) Streams ohne HTTP-Status - Einzelabrufe
// funktionieren, deshalb erst direkt versuchen und NUR im Fehlerfall über
// den eigenen Server nachladen (/images/proxy in Main.kt). EIN globaler
// Capture-Handler statt Anpassung aller ~10 <img>-Erzeugungsstellen:
// error-Events bubbeln nicht, kommen im Capture auf document aber vorbei,
// BEVOR die per-<img>-Handler ("Kein Bild"-Platzhalter) laufen -
// stopPropagation() hält sie beim ersten Fehlversuch auf. Nur absolute
// https-URLs werden umgeleitet - die Proxy-URL selbst ist relativ, schlägt
// also AUCH der Proxy fehl, greift diese Bedingung nicht mehr und alles
// läuft unverändert wie bisher ("Kein Bild"). Das macht den Handler ganz
// ohne Merker-Flag zustandssicher, auch für wiederverwendete <img>-Elemente
// wie #detailImg. Eigene Fotos (/images/custom/...) und statische Grafiken
// sind relativ und bleiben unberührt.
document.addEventListener("error", (e) => {
  const img = e.target;
  if (!(img instanceof HTMLImageElement)) return;
  const src = img.getAttribute("src") || "";
  if (!src.startsWith("https://")) return;
  e.stopPropagation();
  img.src = "images/proxy?url=" + encodeURIComponent(src);
}, true);

// Holo-Glanz auf den Karten-Kacheln (17.08., Nutzer-Vorgabe: der Homepage-
// Hero-Effekt - "Wenn man mit der Maus über seine Karten fährt dann soll es
// genau so aussehen, nur das Bewegen der Karten muss nicht sein"). EIN
// delegierter Listener für alle Kacheln (auch später nachgerenderte) statt
// je Kachel einen: setzt --hx/--hy auf der .art-Fläche, der Verlauf selbst
// lebt in style.css (.card .art::after). Kein 3D-Kippen im Grid.
// Auf den Bildschirm-Takt gedrosselt (31.08., Nutzer-Fund "es stockt, wenn
// man über die Karten fährt"): pointermove feuert weit öfter als der
// Bildschirm zeichnet, und jedes Event erzwang mit getBoundingClientRect
// eine Layout-Berechnung. Jetzt merkt sich der Listener nur die letzte
// Position und rechnet EINMAL pro Frame - sieht identisch aus.
let holoFrame = 0;
let holoLast = null;
document.addEventListener("pointermove", (e) => {
  const art = e.target.closest ? e.target.closest(".card .art") : null;
  if (!art) return;
  holoLast = { art, x: e.clientX, y: e.clientY };
  if (holoFrame) return;
  holoFrame = requestAnimationFrame(() => {
    holoFrame = 0;
    const { art: a, x, y } = holoLast;
    const r = a.getBoundingClientRect();
    if (r.width === 0 || r.height === 0) return;
    a.style.setProperty("--hx", (((x - r.left) / r.width) - 0.5) * 2);
    a.style.setProperty("--hy", (((y - r.top) / r.height) - 0.5) * 2);
  });
}, { passive: true });

// Detailansicht: Holo-Glanz MIT Kippen (17.08., wie die Hero-Karte der
// Homepage). Neigung auf .detailArt (nicht .detailCard, siehe style.css-
// Kommentar zur Dreh-Animation); die Glanz-Sichtbarkeit macht :hover in
// style.css (Nutzer-Fund 17.08.: eine JS-Klasse blieb beim Schließen kleben).
(() => {
  const detailCard = document.getElementById("detailCard");
  if (!detailCard) return;
  // Gleiche Frame-Drosselung wie beim Grid-Glanz oben (31.08.)
  let tiltFrame = 0;
  let tiltLast = null;
  detailCard.addEventListener("pointermove", (e) => {
    tiltLast = { x: e.clientX, y: e.clientY };
    if (tiltFrame) return;
    tiltFrame = requestAnimationFrame(() => {
      tiltFrame = 0;
      const art = detailCard.querySelector(".detailArt");
      if (!art) return;
      const r = art.getBoundingClientRect();
      if (r.width === 0 || r.height === 0) return;
      const nx = Math.max(-1, Math.min(1, (((tiltLast.x - r.left) / r.width) - 0.5) * 2));
      const ny = Math.max(-1, Math.min(1, (((tiltLast.y - r.top) / r.height) - 0.5) * 2));
      art.style.setProperty("--hx", nx);
      art.style.setProperty("--hy", ny);
      // Kippung aufs IMG statt auf .detailArt (18.08., dritter Anlauf gegen
      // den "Schatten": siehe style.css-Kommentar - so wird sie flach in die
      // Kartenebene projiziert und taucht nie hinter die dunkle Karte)
      const img = art.querySelector("img");
      if (img) img.style.transform = `rotateY(${nx * 10}deg) rotateX(${ny * -8}deg)`;
    });
  }, { passive: true });
  detailCard.addEventListener("pointerleave", () => {
    const art = detailCard.querySelector(".detailArt");
    if (!art) return;
    const img = art.querySelector("img");
    if (img) img.style.transform = "";
  });
})();

let allCards = [];
let allSealed = [];
let activeTab = "cards";
// Kein "Alle"-Filter mehr (24.07., Nutzer-Vorgabe: gibt es in der App auch
// nicht, dort wählt man immer genau ein TCG) - startet mit dem ersten TCG
// vorausgewählt statt einer leeren/gemischten Ansicht
let activeGame = GAMES[0].code;
// Verhindert doppelte Partikel-Animationen, falls z.B. während des Zerfalls
// nochmal auf den (noch sichtbaren, abgedunkelten) Hintergrund geklickt wird
let detailClosing = false;

// Set-Tracking im Karten-Tab (26.07.) - Sets pro Spiel gecacht (setsCache),
// voller Katalog pro Set nur bei Bedarf nachgeladen (catalogCache), da bei
// großen TCGs potenziell tausende Karten. "ALL" ist der Sonderwert für den
// immer vorhandenen "Alle"-Akkordeon-Eintrag; null bedeutet "alles zu".
const setsCache = {};
const catalogCache = {};
let openSetId = "ALL";
let setFilter = "owned"; // "owned" | "full" | "missing" - nur relevant für echte Sets
// Sortierung + Raritäts-Filter fürs Karten-Grid (31.07., Nutzer-Vorgabe) -
// gilt für "Alle" und für ein konkretes Set gleichermaßen, siehe
// buildSortFilterRow()/applySortAndFilter() weiter unten
let gridSortMode = "date"; // "date" | "nameAsc" | "nameDesc"
let gridRarityFilter = new Set();
// Typ-/Farb-Filter (28.08., Parität zur App vom 19.08.) - Chips erscheinen
// nur, wo die Katalogdaten Typ (ruleSupertype) bzw. Farben (ruleSubtypes)
// tragen; mehrere gewählte Werte sind ODER-verknüpft (wie in der App)
let gridTypeFilter = new Set();
let gridColorFilter = new Set();
// Auswahl-Modus für den Export bestimmter Karten (26.07., Nutzer-Vorgabe) -
// pro cardId, wird beim Wechsel des Sets/Filters zurückgesetzt (siehe
// resetSelection())
let selectionMode = false;
let selectedCardIds = new Set();

function resetSelection() {
  selectionMode = false;
  selectedCardIds = new Set();
}

// Zweite, unabhängige Mehrfachauswahl (03.08., Nutzer-Vorgabe "wir haben
// garkeine Massenauswahl" - Long-Press wie in der App gibt es im Browser
// nicht, deshalb ein eigener "Auswählen"-Button neben "Hinzufügen") für
// Löschen + Binder-Hinzufügen eigener Karten in der "Alle"-Ansicht.
// Bewusst GETRENNT von selectionMode/selectedCardIds oben (das bleibt
// unverändert dem Export vorbehalten) - andere Identität (item.id statt
// item.cardId, siehe buildCardTile()-Kommentar) und andere Aktionen.
// selectedOwnedItemIds ist ein JS-Set, das laut Sprachspezifikation seine
// Einfügereihenfolge beim Iterieren beibehält - die Antipp-Reihenfolge wird
// dadurch ohne zusätzlichen Code zur Binder-Seitenreihenfolge (Nutzer-
// Vorgabe "in der entsprechenden Auswahlreihenfolge"), genau wie
// selectedOwnedCardIds (dort als Liste) in der App.
let ownedSelectionMode = false;
let selectedOwnedItemIds = new Set();
let ownedSelectionBinderId = null;
// Wants-/Deck-Ziele in der Mehrfachauswahl (28.08., Parität zur App vom
// 20.08. - dort DeckPickerRow/WishlistPickerRow neben dem Binder-Picker)
let ownedSelectionWishlistId = null;
let ownedSelectionDeckId = null;

function resetOwnedSelection() {
  ownedSelectionMode = false;
  selectedOwnedItemIds = new Set();
  ownedSelectionBinderId = null;
  ownedSelectionWishlistId = null;
  ownedSelectionDeckId = null;
}

// Werkzeugleiste für die Massenauswahl (03.08.) - Binder-Chips (analog zu
// renderAddBinderPicker(), aber eigenständig, da hier "Kein Binder" keinen
// Sinn ergibt) + "Zu Binder hinzufügen" + "Löschen". Reihenfolge des
// Hinzufügens richtet sich nach selectedOwnedItemIds' Einfüge-Reihenfolge
// (siehe dortigen Kommentar), NICHT nach der Grid-Anzeigereihenfolge.
function buildOwnedSelectionToolbar(ownedForGame) {
  const wrap = document.createElement("div");
  wrap.className = "gridSortFilterRow";
  wrap.style.flexWrap = "wrap";

  // "Alle N angezeigten auswählen" (28.08., Parität zur App vom 20.08.) -
  // zählt nur die gerade sichtbaren (Filter + Suche angewendet)
  const shown = applySortAndFilter(ownedForGame);
  const selectAllBtn = document.createElement("button");
  selectAllBtn.className = "gridSortChip";
  selectAllBtn.textContent = tr("Select all ", "Alle ") + shown.length + tr(" shown", " angezeigten auswählen");
  selectAllBtn.addEventListener("click", () => {
    shown.forEach(it => selectedOwnedItemIds.add(it.id));
    render();
  });
  wrap.appendChild(selectAllBtn);
  const selectAllDivider = document.createElement("span");
  selectAllDivider.className = "gridSortDivider";
  wrap.appendChild(selectAllDivider);

  // Ziel-Listen bei Bedarf nachladen (einmalig; render() zeichnet die
  // Chips dann nach) - vorher erschienen Binder-Chips nur, wenn der Cache
  // zufällig schon durch ein Add-Overlay gefüllt war
  if (bindersCache[activeGame] === undefined) loadBindersForGame(activeGame);
  if (wishlistsCache[activeGame] === undefined) loadWishlistsForGame(activeGame);
  if (decksCache[activeGame] === undefined) loadDecksForGame(activeGame);

  const binders = bindersCache[activeGame] || [];
  if (binders.length > 0) {
    binders.forEach(b => {
      const chip = document.createElement("button");
      chip.className = "gridSortChip" + (ownedSelectionBinderId === b.id ? " active" : "");
      chip.textContent = b.name;
      chip.addEventListener("click", () => {
        ownedSelectionBinderId = ownedSelectionBinderId === b.id ? null : b.id;
        render();
      });
      wrap.appendChild(chip);
    });
    const divider = document.createElement("span");
    divider.className = "gridSortDivider";
    wrap.appendChild(divider);
  }

  const addBtn = document.createElement("button");
  addBtn.className = "gridSortChip";
  addBtn.textContent = tr("Add to binder", "Zu Binder hinzufügen");
  addBtn.disabled = ownedSelectionBinderId === null;
  addBtn.addEventListener("click", async () => {
    const binderId = ownedSelectionBinderId;
    if (binderId === null) return;
    const ownedById = new Map(ownedForGame.map(it => [it.id, it]));
    // Reihenfolge = Antipp-Reihenfolge (Einfüge-Reihenfolge des Sets), siehe
    // Kommentar bei selectedOwnedItemIds oben
    const cards = Array.from(selectedOwnedItemIds)
      .map(id => ownedById.get(id))
      .filter(it => it && it.cardId)
      .map(it => ({ cardId: it.cardId, name: it.name, imageUrl: it.imageUrl || null }));
    try {
      await fetch("/api/binderItems/add", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ binderId, cards })
      });
      await loadBindersForGame(activeGame);
      resetOwnedSelection();
      render();
    } catch (err) {
      showAddToast(tr("Adding failed", "Hinzufügen fehlgeschlagen"));
    }
  });
  wrap.appendChild(addBtn);

  // Wants-Listen-Ziel (28.08., Parität zur App) - Chips + Hinzufügen-Knopf,
  // gleiche Mechanik wie beim Binder darüber
  const wishlists = wishlistsCache[activeGame] || [];
  if (wishlists.length > 0) {
    const divider = document.createElement("span");
    divider.className = "gridSortDivider";
    wrap.appendChild(divider);
    wishlists.forEach(w => {
      const chip = document.createElement("button");
      chip.className = "gridSortChip" + (ownedSelectionWishlistId === w.id ? " active" : "");
      chip.textContent = w.name;
      chip.addEventListener("click", () => {
        ownedSelectionWishlistId = ownedSelectionWishlistId === w.id ? null : w.id;
        render();
      });
      wrap.appendChild(chip);
    });
    const wlBtn = document.createElement("button");
    wlBtn.className = "gridSortChip";
    wlBtn.textContent = tr("Add to want list", "Zu Wantsliste hinzufügen");
    wlBtn.disabled = ownedSelectionWishlistId === null;
    wlBtn.addEventListener("click", async () => {
      if (ownedSelectionWishlistId === null) return;
      const ownedById = new Map(ownedForGame.map(it => [it.id, it]));
      const cards = Array.from(selectedOwnedItemIds)
        .map(id => ownedById.get(id))
        .filter(it => it)
        .map(it => ({ cardId: it.cardId || null, name: it.name, imageUrl: it.imageUrl || null }));
      try {
        await fetch("/api/wishlistItems/add", {
          method: "POST",
          headers: authHeaders(true),
          body: JSON.stringify({ wishlistId: ownedSelectionWishlistId, cards })
        });
        delete wishlistItemsCache[activeGame];
        await loadWishlistsForGame(activeGame);
        resetOwnedSelection();
        render();
      } catch (err) {
        showAddToast(tr("Adding failed", "Hinzufügen fehlgeschlagen"));
      }
    });
    wrap.appendChild(wlBtn);
  }

  // Deck-Ziel (28.08., Parität zur App) - ein Aufruf pro Karte, wie im
  // Add-Overlay; Karten ohne Katalog-Bezug (cardId) können nicht ins Deck
  const decks = decksCache[activeGame] || [];
  if (decks.length > 0) {
    const divider = document.createElement("span");
    divider.className = "gridSortDivider";
    wrap.appendChild(divider);
    decks.forEach(d => {
      const chip = document.createElement("button");
      chip.className = "gridSortChip" + (ownedSelectionDeckId === d.id ? " active" : "");
      chip.textContent = d.name;
      chip.addEventListener("click", () => {
        ownedSelectionDeckId = ownedSelectionDeckId === d.id ? null : d.id;
        render();
      });
      wrap.appendChild(chip);
    });
    const deckBtn = document.createElement("button");
    deckBtn.className = "gridSortChip";
    deckBtn.textContent = tr("Add to deck", "Zu Deck hinzufügen");
    deckBtn.disabled = ownedSelectionDeckId === null;
    deckBtn.addEventListener("click", async () => {
      if (ownedSelectionDeckId === null) return;
      const ownedById = new Map(ownedForGame.map(it => [it.id, it]));
      const cards = Array.from(selectedOwnedItemIds)
        .map(id => ownedById.get(id))
        .filter(it => it && it.cardId);
      let ok = 0;
      for (const it of cards) {
        try {
          const res = await fetch("/api/deckCards/add", {
            method: "POST",
            headers: authHeaders(true),
            body: JSON.stringify({ deckId: ownedSelectionDeckId, cardId: it.cardId })
          });
          if (res.ok) ok++;
        } catch (err) {
          // einzelner Fehlschlag stoppt nicht den Rest
        }
      }
      delete deckCardsCache[ownedSelectionDeckId];
      delete decksCache[activeGame];
      await loadDecksForGame(activeGame);
      showAddToast("✓ " + ok + " " + tr("added to deck", "zum Deck hinzugefügt"));
      resetOwnedSelection();
      render();
    });
    wrap.appendChild(deckBtn);
  }

  const deleteBtn = document.createElement("button");
  deleteBtn.className = "gridSortChip danger";
  deleteBtn.textContent = tr("Delete", "Löschen");
  deleteBtn.addEventListener("click", () => {
    const count = selectedOwnedItemIds.size;
    showConfirm(count + " " + tr("card(s) will be removed from the collection - continue?", "Karte(n) wirklich aus der Sammlung löschen?"), async () => {
      try {
        await fetch("/api/collection/delete", {
          method: "POST",
          headers: authHeaders(true),
          body: JSON.stringify({ ids: Array.from(selectedOwnedItemIds) })
        });
        resetOwnedSelection();
        await loadData();
      } catch (err) {
        showAddToast(tr("Delete failed", "Löschen fehlgeschlagen"));
      }
    });
  });
  wrap.appendChild(deleteBtn);

  return wrap;
}
// Gleiches Akkordeon-Prinzip jetzt auch im Vault-Tab (26.07., Nutzer-
// Vorgabe: "genau so wie unter Karten mit den Sets") - "ALL" + Kategorien
// statt "ALL" + Sets, eigener State, damit sich Karten- und Vault-Tab beim
// Umschalten nicht gegenseitig das offene Element zurücksetzen
let openVaultCategory = "ALL";
// Umrechnungsfaktor für den Werte-Tab (26.07.) - server-eigene Kalibrierung,
// siehe Kommentar bei /api/marketFactor in Main.kt. Default identisch zum
// App-Default, bis der echte Wert vom Server geladen ist.
let marketFactor = 0.8;

// Wunschlisten (27.07., Nutzer-Vorgabe) - eigener Akkordeon-Eintrag direkt
// unter "Alle", komplett getrennt von allCards/Statistiken (siehe Server-
// seitigen Kommentar bei WishlistResponse). Pro TCG geladen wie
// setsCache/catalogCache.
const wishlistsCache = {}; // game -> [{id,name,game,itemCount}]
const wishlistItemsCache = {}; // game -> [{id,wishlistId,cardId,name,imageUrl,setId,marketPriceUsd}]
let openWishlistId = null; // welche Liste gerade offen ist (null = Übersicht)
let wishlistListSelectionMode = false;
let selectedWishlistIds = new Set();
let selectedWishlistItemIds = new Set();
// Sortierung + Raritäts-Filter (31.07., Nutzer-Vorgabe "damit man wirklich
// auf seinen Bedarf abgestimmte Wantlisten exportieren kann") - eigener
// State statt gridSortMode/gridRarityFilter wiederzuverwenden, damit beide
// unabhängig bleiben
let wishlistSortMode = "date"; // "date" | "nameAsc" | "nameDesc"
let wishlistRarityFilter = new Set();

// Binder (31.07., Nutzer-Vorgabe) - strukturell identisch zu den
// Wunschlisten-Variablen oben, aber eigener Tab statt Akkordeon-Eintrag
// (siehe Kommentar bei .binderPanel in style.css - auf der Weboberfläche ist
// genug Platz für einen eigenen Button).
const bindersCache = {}; // game -> [{id,name,game,itemCount}]
const binderItemsCache = {}; // game -> [{id,binderId,cardId,name,imageUrl,setId,marketPriceUsd}]
let openBinderId = null;
let binderListSelectionMode = false;
let selectedBinderIds = new Set();
let selectedBinderItemIds = new Set();
// Binder-Auswahl beim Hinzufügen von Karten zur Sammlung (31.07., Nutzer-
// Vorgabe) - im "cards"-Add-Overlay gesetzt, siehe runAddBatch()
let addBinderTargetId = null;
// Binder-Seiten (31.07., Nutzer-Vorgabe "den echten Binder 1:1 abbilden" +
// "Kartenplätze ändern können") - siehe renderBinderDetail()/
// flipToBinderPage() weiter unten
let openBinderPage = 0;
let binderArrangeMode = false;
let binderSwapSourceId = null;
let createBinderPageSize = 9;
// Raritäts-Filter für Binder (31.07., Nutzer-Vorgabe) - bewusst KEINE
// Sortierung (die Reihenfolge IST die physische Seitenanordnung), nicht
// passende Karten bleiben auf ihrem Platz, nur abgedunkelt (siehe
// buildBinderSlotElement())
let binderRarityFilter = new Set();
// Namenssuche für Binder (10.08., Feature-Parität) - dasselbe Prinzip wie
// binderRarityFilter (siehe buildBinderSlotElement()), keine echte Filterung
// der Liste, nur Abdunkeln nicht passender Plätze
let binderSearchQuery = "";

// Pairing-Token (2026-07-24) - im Browser per localStorage gemerkt, wird bei
// jedem API-Aufruf als Header mitgeschickt. Ohne gültiges Token antwortet
// der Server mit 401, dann zeigen wir das Eingabe-Overlay.
function getStoredToken() {
  return localStorage.getItem("ult1madePairingToken") || "";
}

// Nutzer-Vorgabe (08.08.) "IP-Adresse und Pairing-Token direkt sichtbar,
// oben links in der jeweiligen TCG-Farbe" - bisher stand das Token nur im
// Server-Log, man musste sich die IP selbst zusammensuchen. Die IP holt der
// Browser sich direkt aus der eigenen Adresszeile (window.location) - genau
// die Adresse, über die diese Seite gerade erreicht wurde, exakt das, was
// man in der App als Server-Adresse eintragen muss. Das Token kommt vom
// eigens dafür öffentlichen /api/pairingInfo-Endpunkt (siehe isPublic in
// Main.kt) - unabhängig davon, ob schon ein (evtl. falsches) Token im
// localStorage steht.
async function renderServerInfo() {
  const el = document.getElementById("serverInfo");
  if (!el) return;
  const host = window.location.host;
  let token = "…";
  try {
    const res = await fetch("/api/pairingInfo");
    if (res.ok) {
      const data = await res.json();
      token = data.pairingToken;
    }
  } catch (e) {
    token = tr("not reachable", "nicht erreichbar");
  }
  el.textContent = "IP: " + host + " · Token: " + token;
}

// Mandantenfähigkeit (02.08., Nutzer-Vorgabe) - "einfacher Umschalter reicht,
// kein Standard-Haken/Auto-Login wie in der App nötig" (Nutzer-Entscheidung).
// Merkt sich die letzte Wahl im Browser (pro Gerät/Browser, nicht
// synchronisiert - dieselbe Idee wie "zuletzt genutzt" in der App, nur ohne
// deren PIN-Auswahlbildschirm-Zwang). Wird als Header mitgeschickt statt als
// Query-Parameter an jeden einzelnen der ~20 fetch()-Aufrufe anzuhängen -
// der Server prüft beides (siehe resolveAccountId() in Main.kt).
function getActiveAccountId() {
  return localStorage.getItem("ult1madeActiveAccountId") || "";
}

function setActiveAccountId(id) {
  localStorage.setItem("ult1madeActiveAccountId", String(id));
}

function authHeaders(withJson) {
  const headers = { "X-Pairing-Token": getStoredToken(), "X-Account-Id": getActiveAccountId() };
  if (withJson) headers["Content-Type"] = "application/json";
  return headers;
}

function authedFetch(url) {
  return fetch(url, { headers: authHeaders(false) });
}

function showTokenOverlay(message) {
  document.getElementById("tokenOverlay").classList.add("visible");
  document.getElementById("tokenError").textContent = message || "";
}

function hideTokenOverlay() {
  document.getElementById("tokenOverlay").classList.remove("visible");
}

// Generische Ja/Nein-Sicherheitsabfrage (27.07., für die Wunschlisten-
// Löschungen) - Nachricht und Callback werden pro Aufruf gesetzt, damit ein
// einziger Dialog für Listen UND einzelne Karten reicht
let confirmYesHandler = null;

function showConfirm(message, onYes) {
  document.getElementById("confirmMessage").textContent = message;
  confirmYesHandler = onYes;
  document.getElementById("confirmOverlay").classList.add("visible");
}

function hideConfirm() {
  document.getElementById("confirmOverlay").classList.remove("visible");
  confirmYesHandler = null;
}

document.getElementById("confirmNo").addEventListener("click", hideConfirm);
document.getElementById("confirmYes").addEventListener("click", () => {
  const handler = confirmYesHandler;
  hideConfirm();
  if (handler) handler();
});
document.getElementById("confirmOverlay").addEventListener("click", (e) => {
  if (e.target.id === "confirmOverlay") hideConfirm();
});

// Binder-Erstellen-Dialog (31.07.) - siehe openCreateBinderDialog()
document.getElementById("createBinderCancel").addEventListener("click", closeCreateBinderDialog);
document.getElementById("createBinderOverlay").addEventListener("click", (e) => {
  if (e.target.id === "createBinderOverlay") closeCreateBinderDialog();
});
document.getElementById("createBinderConfirm").addEventListener("click", () => {
  const name = document.getElementById("createBinderName").value.trim();
  if (!name) return;
  createBinder(name, createBinderPageSize);
  closeCreateBinderDialog();
});

// Deck-Erstellen-Dialog (10.08.) - siehe openCreateDeckDialog()
document.getElementById("createDeckCancel").addEventListener("click", closeCreateDeckDialog);
document.getElementById("createDeckOverlay").addEventListener("click", (e) => {
  if (e.target.id === "createDeckOverlay") closeCreateDeckDialog();
});
document.getElementById("createDeckConfirm").addEventListener("click", () => {
  const name = document.getElementById("createDeckName").value.trim();
  if (!name) return;
  createDeck(name);
  closeCreateDeckDialog();
});

function colorFor(game) {
  return (GAME_BY_CODE[game] || {}).color || "#888";
}

function labelFor(game) {
  return (GAME_BY_CODE[game] || {}).label || game || tr("Unknown", "Unbekannt");
}

// EUR-Formatierung wie in der App (ValueOverviewScreen.kt: Komma statt
// Punkt, "€" hinten) - eigener Kotlin-unabhängiger Nachbau hier, kein
// geteilter Code zwischen App und Weboberfläche (siehe CONCEPT.md)
function formatEur(amount) {
  if (amount === null || amount === undefined) return "–";
  const negative = amount < 0;
  const abs = Math.abs(amount);
  const cents = Math.round(abs * 100);
  const whole = Math.floor(cents / 100);
  const fraction = cents % 100;
  const fractionStr = fraction < 10 ? "0" + fraction : "" + fraction;
  return (negative ? "-" : "") + whole + "," + fractionStr + " €";
}

// AUSSCHLIESSLICH der echte Cardmarket-Preis (03.08., Nutzer-Vorgabe "dann
// nehmen wir USD-Alt-Preise raus und gut ist" - siehe CONCEPT.md
// "Copyright-/Release-Recherche") - kein Alt-USD-Rückfall mehr. Für
// die kompakte Kachel-Badge, nicht die ausführliche Detailansicht (siehe
// renderDetailCardmarketOptions()).
function formatTileMarketPrice(item) {
  if (item.marketPriceEur !== null && item.marketPriceEur !== undefined) {
    return formatEur(item.marketPriceEur);
  }
  return "–";
}

// Für Summenbildung (Wishlist-/Binder-Statuszeilen) - dieselbe
// Cardmarket-only-Regel wie formatTileMarketPrice() oben
function tileMarketEur(item) {
  return item.marketPriceEur || 0;
}

function setAccent(color) {
  document.documentElement.style.setProperty("--accent", color);
}

function renderChips() {
  const nav = document.getElementById("gameFilters");
  nav.innerHTML = "";

  // Nur die für diesen Account sichtbaren TCGs (17.08., Nutzer-Vorgabe) -
  // siehe visibleGames()/loadHiddenGames() oben
  for (const g of visibleGames()) {
    const chip = document.createElement("button");
    chip.className = "chip" + (activeGame === g.code ? " active" : "");
    chip.textContent = g.label;
    chip.style.setProperty("--chip-color", g.color);
    chip.addEventListener("click", () => {
      if (activeGame === g.code) return;
      switchGameAnimated(g);
    });
    nav.appendChild(chip);
  }
}

// Karten-/Vault-Button zeigen das Foto des gerade gewählten TCG (wie die
// Themen-Bälle in der App) - Werte bleibt immer die Münze, unabhängig vom TCG
function updateActionTabImages() {
  const image = (GAME_BY_CODE[activeGame] || {}).image || GAMES[0].image;
  document.getElementById("cardsTabImg").src = image;
  document.getElementById("vaultTabImg").src = image;
  document.getElementById("binderTabImg").src = image;
  document.getElementById("decksTabImg").src = image;
}

function currentItems() {
  const source = activeTab === "vault" ? allSealed : allCards;
  return source.filter(item => item.game === activeGame);
}

// Welches Grid gerade wirklich Karten zeigt - je nach Tab entweder das
// flache Vault-Grid, das Grid im offenen Set-Akkordeon-Eintrag (Karten-Tab),
// oder keins (Werte-Tab / nichts aufgeklappt)
function getVisibleGridElement() {
  if (activeTab === "cards" || activeTab === "vault") {
    return document.querySelector("#setAccordion .setAccordionItem.open .grid");
  }
  return null;
}

let switchAnimating = false;

// TCG-Wechsel-Animation (26.07., Nutzer-Vorgabe, per Nutzer-Feedback
// präzisiert): die sichtbaren Karten sammeln sich beim Anklicken eines
// anderen TCG zu einem Stapel GENAU AN DER STELLE DER ERSTEN KARTE (oben
// links) - nicht in der Grid-Mitte, sondern wirklich wie auf einen
// Kartenstapel gelegt. Dann wird tatsächlich umgeschaltet, und die neuen
// Karten verteilen sich von genau diesem Stapelpunkt aus zurück auf ihre
// Positionen, wie beim Austeilen eines Kartendecks (FLIP-Technik wie bei
// der Detailansicht, nur auf viele Kacheln gleichzeitig statt einer).
function switchGameAnimated(g) {
  if (switchAnimating) {
    applyGameSwitch(g);
    return;
  }
  const gridEl = getVisibleGridElement();
  const cards = gridEl ? Array.from(gridEl.querySelectorAll(".card")) : [];

  if (cards.length === 0) {
    // Nichts zum Einsammeln da (Nutzer-Fund 26.07.: z.B. beim Wechsel von
    // einem TCG ohne eigene Karten aus) - die neuen Karten sollen aber
    // trotzdem sichtbar vom Stapel her erscheinen, nicht einfach so dastehen
    applyGameSwitch(g);
    scatterInCards();
    return;
  }

  switchAnimating = true;
  const stackRect = cards[0].getBoundingClientRect();
  const targetX = stackRect.left + stackRect.width / 2;
  const targetY = stackRect.top + stackRect.height / 2;

  cards.forEach((card) => {
    const r = card.getBoundingClientRect();
    const dx = targetX - (r.left + r.width / 2);
    const dy = targetY - (r.top + r.height / 2);
    const rot = (Math.random() - 0.5) * 16;
    card.style.transition = "transform 0.4s cubic-bezier(.4,0,.2,1), opacity 0.35s ease";
    card.style.transitionDelay = (Math.random() * 0.12).toFixed(3) + "s";
    card.style.transform = `translate(${dx}px, ${dy}px) scale(0.12) rotate(${rot}deg)`;
    card.style.opacity = "0";
  });

  setTimeout(() => {
    applyGameSwitch(g);
    scatterInCards();
    switchAnimating = false;
  }, 520);
}

function applyGameSwitch(g) {
  activeGame = g.code;
  setAccent(g.color);
  updateActionTabImages();
  // Sets unterscheiden sich pro TCG - beim Wechsel zurück auf "Alle" und
  // den Standard-Filter, ein zuvor geöffnetes Set eines anderen TCG ergäbe
  // hier keinen Sinn mehr
  openSetId = "ALL";
  setFilter = "owned";
  openVaultCategory = "ALL";
  openWishlistId = null;
  wishlistSearchQuery = "";
  wishlistListSelectionMode = false;
  selectedWishlistIds = new Set();
  selectedWishlistItemIds = new Set();
  openBinderId = null;
  binderListSelectionMode = false;
  selectedBinderIds = new Set();
  selectedBinderItemIds = new Set();
  openBinderPage = 0;
  binderArrangeMode = false;
  binderSwapSourceId = null;
  openDeckId = null;
  deckListSelectionMode = false;
  selectedDeckIds = new Set();
  selectedDeckCardIds = new Set();
  deckValidation = null;
  gridRarityFilter = new Set();
  gridTypeFilter = new Set();
  gridColorFilter = new Set();
  gridSearchQuery = "";
  resetSelection();
  render();
}

// Gegenstück zu switchGameAnimated(): die frisch gerenderten Karten starten
// optisch alle am selben Stapelpunkt - dort, wo jetzt die erste Karte des
// NEUEN Sets liegt (unsichtbar-schnell dorthin "gebeamt", ohne Transition)
// - und verteilen sich dann mit leichter Staffelung zurück auf ihre echten
// Grid-Positionen, wie beim Austeilen eines Kartendecks.
function scatterInCards() {
  const gridEl = getVisibleGridElement();
  if (!gridEl) return;
  const cards = Array.from(gridEl.querySelectorAll(".card"));
  if (cards.length === 0) return;

  const stackRect = cards[0].getBoundingClientRect();
  const originX = stackRect.left + stackRect.width / 2;
  const originY = stackRect.top + stackRect.height / 2;

  cards.forEach((card) => {
    const r = card.getBoundingClientRect();
    const dx = originX - (r.left + r.width / 2);
    const dy = originY - (r.top + r.height / 2);
    const rot = (Math.random() - 0.5) * 16;
    card.style.transition = "none";
    card.style.opacity = "0";
    card.style.transform = `translate(${dx}px, ${dy}px) scale(0.12) rotate(${rot}deg)`;
  });

  // Erzwungenes Reflow statt doppeltem requestAnimationFrame (Nutzer-Fund
  // 26.07.: beim Zurückwechseln zu einem TCG erschienen die Karten teils
  // direkt an ihrer Position statt sich vom Stapel zu verteilen - das
  // doppelte rAF war offenbar nicht zuverlässig genug, wenn im selben
  // Frame vorher noch ein kompletter DOM-Neuaufbau [render()] passiert
  // ist). Das Lesen von offsetHeight zwingt den Browser, den Stapel-
  // Zustand oben wirklich als Layout zu berechnen, BEVOR unten die
  // Transition zum Endzustand gestartet wird - garantiert einen echten
  // Zwischenschritt statt dass beide Zustände zusammenfallen.
  void gridEl.offsetHeight;

  cards.forEach((card, i) => {
    card.style.transition = "transform 0.5s cubic-bezier(.2,.8,.2,1), opacity 0.4s ease";
    card.style.transitionDelay = Math.min(i * 0.01, 0.3).toFixed(3) + "s";
    card.style.transform = "translate(0, 0) scale(1) rotate(0deg)";
    card.style.opacity = "1";
  });
}

// Baut eine einzelne Karten-/Produkt-Kachel - wiederverwendet vom Vault-Tab
// (flaches Grid wie bisher) und vom neuen Set-Akkordeon im Karten-Tab
// (sowohl für eigene Karten als auch für "Fehlende"-Platzhalter aus dem
// Katalog, siehe missing-Flag).
// Auswahl-Set + -Schlüssel sind seit 03.08. parametrisierbar (Nutzer-Vorgabe
// "Massenauswahl" für Löschen/Binder-Hinzufügen auf der Weboberfläche) -
// Standardverhalten (selectedCardIds nach item.cardId) bleibt für die
// bestehende Export-Auswahl unverändert, opts.selectedIds/opts.selectKey
// erlauben eine ZWEITE, unabhängige Auswahl (siehe ownedSelectionMode unten)
// mit einer anderen Identität (item.id statt item.cardId - wichtig, da
// dieselbe Katalogkarte mehrfach als getrennte Bestandszeile existieren kann,
// z.B. normal + Holo, siehe CollectionCardResponse-Kommentar in Main.kt).
function buildCardTile(item, opts) {
  opts = opts || {};
  const selectionSet = opts.selectedIds || selectedCardIds;
  const selectionKey = opts.selectKey ? opts.selectKey(item) : item.cardId;
  const isSelected = opts.selectable && selectionSet.has(selectionKey);
  const card = document.createElement("div");
  card.className = "card" + (opts.missing ? " missing" : "") + (isSelected ? " selected" : "") + (item.isHolo ? " holo" : "");
  card.style.setProperty("--card-color", colorFor(item.game));

  const art = document.createElement("div");
  if (item.imageUrl) {
    art.className = "art";
    const img = document.createElement("img");
    img.src = item.imageUrl;
    img.loading = "lazy";
    img.alt = item.name;
    // Fällt auf den Platzhalter zurück, falls das Bild nicht lädt (z.B.
    // tote URL) statt eines kaputten Bild-Icons
    img.addEventListener("error", () => {
      art.className = "art placeholder";
      art.textContent = tr("No image", "Kein Bild");
    });
    art.appendChild(img);
  } else {
    art.className = "art placeholder";
    art.textContent = tr("No image", "Kein Bild");
  }
  card.appendChild(art);

  const name = document.createElement("div");
  name.className = "name";
  name.textContent = item.name + (item.isHolo ? " ✨" : "");
  card.appendChild(name);

  const meta = document.createElement("div");
  meta.className = "meta";

  const left = document.createElement("span");
  if (opts.missing) {
    const badge = document.createElement("span");
    badge.className = "badge";
    badge.textContent = tr("missing", "fehlt");
    left.appendChild(badge);
  } else {
    const badge = document.createElement("span");
    badge.className = "badge";
    badge.textContent = "×" + item.quantity;
    left.appendChild(badge);
    const gameLabel = document.createElement("span");
    gameLabel.textContent = opts.category ? item.category : labelFor(item.game);
    left.appendChild(gameLabel);
  }
  meta.appendChild(left);

  const right = document.createElement("span");
  right.textContent = formatTileMarketPrice(item);
  meta.appendChild(right);

  card.appendChild(meta);
  if (opts.selectable) {
    // Auswahl-Modus (26.07., Nutzer-Vorgabe: "bestimmte Karten exportieren")
    // - auch fehlende Katalogkarten sind auswählbar, um sie z.B. gezielt in
    // eine Wantsliste aufzunehmen
    card.classList.add("selectable");
    card.addEventListener("click", () => {
      if (selectionSet.has(selectionKey)) {
        selectionSet.delete(selectionKey);
      } else {
        selectionSet.add(selectionKey);
      }
      render();
    });
  } else if (!opts.missing) {
    card.addEventListener("click", () => openCardDetail(item, card));
  }
  return card;
}

function renderGridInto(gridEl, items, opts) {
  gridEl.innerHTML = "";
  for (const item of items) {
    gridEl.appendChild(buildCardTile(item, opts));
  }
}

function render() {
  renderChips();

  const grid = document.getElementById("grid");
  const setAccordion = document.getElementById("setAccordion");
  const status = document.getElementById("status");
  const stats = document.getElementById("stats");
  const valuePanel = document.getElementById("valuePanel");
  const binderPanel = document.getElementById("binderPanel");

  if (activeTab === "value") {
    grid.style.display = "none";
    setAccordion.style.display = "none";
    valuePanel.classList.add("visible");
    binderPanel.classList.remove("visible");
    status.style.display = "none";
    stats.textContent = "";
    renderValuePanel();
    return;
  }
  valuePanel.classList.remove("visible");

  // Binder-Tab (31.07., eigener Button statt unter Karten gequetscht) -
  // eigenes Panel statt des Akkordeons, siehe renderBinderTab()
  if (activeTab === "binder") {
    grid.style.display = "none";
    setAccordion.style.display = "none";
    binderPanel.classList.add("visible");
    status.style.display = "none";
    renderBinderTab();
    return;
  }
  binderPanel.classList.remove("visible");

  // Decks-Tab (10.08., Feature-Parität) - dasselbe Prinzip wie der Binder-Tab
  const deckPanel = document.getElementById("deckPanel");
  if (activeTab === "decks") {
    grid.style.display = "none";
    setAccordion.style.display = "none";
    deckPanel.classList.add("visible");
    status.style.display = "none";
    renderDeckTab();
    return;
  }
  deckPanel.classList.remove("visible");

  if (activeTab === "cards") {
    grid.style.display = "none";
    setAccordion.style.display = "";
    status.style.display = "none";
    renderCardsTab();
    return;
  }

  // Vault-Tab: gleiches Akkordeon-Prinzip wie der Karten-Tab (Nutzer-Vorgabe
  // 26.07.) - "Alle" + Produktkategorien (Starter Decks, Booster Boxen, ...)
  grid.style.display = "none";
  setAccordion.style.display = "";
  status.style.display = "none";
  renderVaultTab();
}

// Vault-Akkordeon: "Alle" (immer vorhanden, zeigt alle eigenen Vault-
// Produkte des TCG flach) klappt zu und gibt die Kategorien frei, die im
// Bestand tatsächlich vorkommen - anders als bei Sets gibt es hier keinen
// festen Gesamt-Katalog pro Kategorie (jedes TCG hat andere Produktarten),
// deshalb nur eine einfache Anzahl statt eines owned/total-Zählers, und
// keine Katalog-Abfrage nötig - alles kommt direkt aus den schon geladenen
// /api/sealed-Daten.
function renderVaultTab() {
  const container = document.getElementById("setAccordion");
  const stats = document.getElementById("stats");
  container.innerHTML = "";

  const ownedForGame = allSealed.filter(item => item.game === activeGame);

  const allItem = document.createElement("div");
  allItem.className = "setAccordionItem" + (openVaultCategory === "ALL" ? " open" : "");
  const allHeader = document.createElement("button");
  allHeader.className = "setAccordionHeader";
  allHeader.innerHTML = "<span>" + tr("All", "Alle") + "</span><span class=\"setAccordionCount\">" + ownedForGame.length + "</span>";
  allHeader.addEventListener("click", () => {
    openVaultCategory = openVaultCategory === "ALL" ? null : "ALL";
    render();
  });
  allItem.appendChild(allHeader);
  const allBody = document.createElement("div");
  allBody.className = "setAccordionBody";
  if (openVaultCategory === "ALL") {
    allBody.appendChild(buildMainActionButton({
      label: tr("Add", "Hinzufügen"),
      imageSrc: (GAME_BY_CODE[activeGame] || {}).image || GAMES[0].image,
      onClick: () => openAddOverlay("vault")
    }));
    // Sealed-Wantslisten (28.08., Parität zur App - dort im Plus-Dialog)
    const wlLists = sealedWishlistsCache[activeGame];
    if (wlLists === undefined) loadSealedWishlistsForGame(activeGame);
    allBody.appendChild(buildMainActionButton({
      label: tr("Want lists", "Wantslisten") + (wlLists ? " (" + wlLists.length + ")" : ""),
      imageSrc: (GAME_BY_CODE[activeGame] || {}).image || GAMES[0].image,
      onClick: () => openSealedWishlistOverlay()
    }));
    if (ownedForGame.length === 0) {
      const empty = document.createElement("p");
      empty.className = "status";
      empty.style.display = "block";
      empty.textContent = tr("No vault products in this selection.", "Keine Vault-Produkte in dieser Auswahl.");
      allBody.appendChild(empty);
    }
    const grid = document.createElement("div");
    grid.className = "grid";
    renderGridInto(grid, ownedForGame, { category: true });
    allBody.appendChild(grid);
    stats.textContent = ownedForGame.length + " " + tr("entries", "Einträge");
  }
  allItem.appendChild(allBody);
  container.appendChild(allItem);

  const categories = Array.from(new Set(ownedForGame.map(item => item.category))).sort();
  for (const cat of categories) {
    const itemsInCat = ownedForGame.filter(item => item.category === cat);
    const isOpen = openVaultCategory === cat;
    const item = document.createElement("div");
    item.className = "setAccordionItem" + (isOpen ? " open" : "");

    const header = document.createElement("button");
    header.className = "setAccordionHeader";
    header.innerHTML = "<span>" + cat + "</span><span class=\"setAccordionCount\">" + itemsInCat.length + "</span>";
    header.addEventListener("click", () => {
      openVaultCategory = openVaultCategory === cat ? null : cat;
      render();
    });
    item.appendChild(header);

    const body = document.createElement("div");
    body.className = "setAccordionBody";
    if (isOpen) {
      const grid = document.createElement("div");
      grid.className = "grid";
      renderGridInto(grid, itemsInCat, { category: true });
      body.appendChild(grid);
      stats.textContent = itemsInCat.length + " " + tr("entries", "Einträge");
    }
    item.appendChild(body);
    container.appendChild(item);
  }
}

// ---------- Sealed-Wantslisten (28.08., Parität zur App vom 25.08.) ----------
// Übersicht (Listen mit Anzahl + Preissumme) und Detail (Produkte mit
// Cardmarket-Preis, Preis-Alarm-Glocke und Entfernen). Produkte kommen über
// das generische Add-Overlay (kind "sealedWishlist", durchsucht den
// Sealed-Katalog wie der Vault-Zweig). Alarm-Semantik wie in der App:
// Schwelle gesetzt = Hinweis, sobald der Preisabgleich sie erreicht.
const sealedWishlistsCache = {};      // game -> [{id,name,game,itemCount}]
const sealedWishlistItemsCache = {};  // game -> [{id,wishlistId,catalogId,...,priceAlarmEur}]
let sealedWlView = { mode: "overview", listId: null };

async function loadSealedWishlistsForGame(game) {
  try {
    const [wRes, iRes] = await Promise.all([
      authedFetch("/api/sealedWishlists?game=" + encodeURIComponent(game)),
      authedFetch("/api/sealedWishlistItems?game=" + encodeURIComponent(game))
    ]);
    if (wRes.ok) sealedWishlistsCache[game] = await wRes.json();
    if (iRes.ok) sealedWishlistItemsCache[game] = await iRes.json();
    if (activeTab === "vault" && activeGame === game) render();
  } catch (err) {
    // Zusatzfeature - bei Fehlern bleibt der Rest der Seite nutzbar
  }
}

function openSealedWishlistOverlay() {
  sealedWlView = { mode: "overview", listId: null };
  document.getElementById("sealedWishlistOverlay").classList.add("visible");
  renderSealedWishlistOverlay();
  loadSealedWishlistsForGame(activeGame).then(renderSealedWishlistOverlay);
}

function closeSealedWishlistOverlay() {
  document.getElementById("sealedWishlistOverlay").classList.remove("visible");
}

function sealedWlPriceSum(items) {
  return items.reduce((sum, it) => sum + (it.marketPriceEur || 0), 0);
}

function buildSealedWlRow(mainText, subText, thumbUrl) {
  const row = document.createElement("div");
  row.className = "addResultRow";
  const thumb = document.createElement("div");
  thumb.className = "thumb";
  if (thumbUrl) {
    const img = document.createElement("img");
    img.src = thumbUrl;
    img.alt = "";
    thumb.appendChild(img);
  }
  const info = document.createElement("div");
  info.className = "info";
  const nameDiv = document.createElement("div");
  nameDiv.className = "name";
  nameDiv.textContent = mainText;
  const subDiv = document.createElement("div");
  subDiv.className = "sub";
  subDiv.textContent = subText || "";
  info.appendChild(nameDiv);
  info.appendChild(subDiv);
  row.appendChild(thumb);
  row.appendChild(info);
  return row;
}

function renderSealedWishlistOverlay() {
  const overlay = document.getElementById("sealedWishlistOverlay");
  if (!overlay.classList.contains("visible")) return;
  const title = document.getElementById("sealedWishlistTitle");
  const content = document.getElementById("sealedWishlistContent");
  const back = document.getElementById("sealedWishlistBack");
  const lists = sealedWishlistsCache[activeGame] || [];
  const items = sealedWishlistItemsCache[activeGame] || [];
  content.innerHTML = "";

  if (sealedWlView.mode === "detail") {
    const list = lists.find(l => l.id === sealedWlView.listId);
    if (!list) {
      sealedWlView = { mode: "overview", listId: null };
      renderSealedWishlistOverlay();
      return;
    }
    const listItems = items.filter(it => it.wishlistId === list.id);
    title.textContent = list.name;
    back.style.display = "";

    const sub = document.createElement("p");
    sub.className = "backupHint";
    sub.textContent = listItems.length + " " + tr("product(s)", "Produkt(e)") + " · " + formatEur(sealedWlPriceSum(listItems)) + " " + tr("total", "gesamt");
    content.appendChild(sub);

    content.appendChild(buildMainActionButton({
      label: tr("Add products", "Produkte hinzufügen"),
      imageSrc: (GAME_BY_CODE[activeGame] || {}).image || GAMES[0].image,
      onClick: () => {
        closeSealedWishlistOverlay();
        openAddOverlay("sealedWishlist", list.id);
      }
    }));

    if (listItems.length === 0) {
      const empty = document.createElement("p");
      empty.className = "backupHint";
      empty.textContent = tr("No products in this list yet.", "Noch keine Produkte in dieser Liste.");
      content.appendChild(empty);
    }
    for (const it of listItems) {
      const priceText = it.marketPriceEur != null ? formatEur(it.marketPriceEur) : tr("no price", "kein Preis");
      const alarmText = it.priceAlarmEur != null
        ? "🔔 " + tr("alert at ", "Alarm bei ") + formatEur(it.priceAlarmEur)
        : "";
      const row = buildSealedWlRow(it.name || it.catalogId, (it.category || "") + " · " + priceText + (alarmText ? " · " + alarmText : ""), it.imageUrl);
      // Alarm-Glocke: setzt/ändert die Schwelle, leer = entfernen (wie der
      // Alarm-Dialog in der App, nur als schlichte Eingabe)
      const bell = document.createElement("button");
      bell.className = "confirmNo";
      bell.style.marginLeft = "auto";
      bell.textContent = it.priceAlarmEur != null ? "🔔" : "🔕";
      bell.title = tr("Price alert", "Preis-Alarm");
      bell.addEventListener("click", async (e) => {
        e.stopPropagation();
        const raw = window.prompt(
          tr("Alert me when the price drops to (EUR, empty = remove):", "Melden, wenn der Preis fällt auf (EUR, leer = entfernen):"),
          it.priceAlarmEur != null ? String(it.priceAlarmEur) : ""
        );
        if (raw === null) return;
        const value = raw.trim() === "" ? null : parseFloat(raw.replace(",", "."));
        if (raw.trim() !== "" && (isNaN(value) || value < 0)) return;
        await fetch("/api/sealedWishlistItems/alarm", {
          method: "POST",
          headers: authHeaders(true),
          body: JSON.stringify({ id: it.id, priceEur: value })
        });
        await loadSealedWishlistsForGame(activeGame);
        renderSealedWishlistOverlay();
      });
      row.appendChild(bell);
      const remove = document.createElement("button");
      remove.className = "confirmNo";
      remove.textContent = "✕";
      remove.title = tr("Remove", "Entfernen");
      remove.addEventListener("click", async (e) => {
        e.stopPropagation();
        await fetch("/api/sealedWishlistItems/delete", {
          method: "POST",
          headers: authHeaders(true),
          body: JSON.stringify({ id: it.id })
        });
        await loadSealedWishlistsForGame(activeGame);
        renderSealedWishlistOverlay();
      });
      row.appendChild(remove);
      content.appendChild(row);
    }
    return;
  }

  // Übersicht
  title.textContent = tr("Want lists", "Wantslisten");
  back.style.display = "none";

  const createRow = document.createElement("div");
  createRow.style.display = "flex";
  createRow.style.gap = "8px";
  createRow.style.marginBottom = "10px";
  const nameInput = document.createElement("input");
  nameInput.type = "text";
  nameInput.className = "addSearchInput";
  nameInput.placeholder = tr("New list name", "Name der neuen Liste");
  nameInput.autocomplete = "off";
  const createBtn = document.createElement("button");
  createBtn.className = "confirmYes";
  createBtn.style.background = "var(--accent)";
  createBtn.style.color = "#10100f";
  createBtn.textContent = tr("Create", "Erstellen");
  createBtn.addEventListener("click", async () => {
    const name = nameInput.value.trim();
    if (!name) return;
    await fetch("/api/sealedWishlists", {
      method: "POST",
      headers: authHeaders(true),
      body: JSON.stringify({ name, game: activeGame })
    });
    nameInput.value = "";
    await loadSealedWishlistsForGame(activeGame);
    renderSealedWishlistOverlay();
  });
  createRow.appendChild(nameInput);
  createRow.appendChild(createBtn);
  content.appendChild(createRow);

  if (lists.length === 0) {
    const empty = document.createElement("p");
    empty.className = "backupHint";
    empty.textContent = tr("No want lists yet - create one above.", "Noch keine Wantslisten - oben eine anlegen.");
    content.appendChild(empty);
  }
  for (const list of lists) {
    const listItems = items.filter(it => it.wishlistId === list.id);
    const row = buildSealedWlRow(
      list.name,
      list.itemCount + " " + tr("product(s)", "Produkt(e)") + " · " + formatEur(sealedWlPriceSum(listItems))
    );
    row.style.cursor = "pointer";
    row.addEventListener("click", () => {
      sealedWlView = { mode: "detail", listId: list.id };
      renderSealedWishlistOverlay();
    });
    const del = document.createElement("button");
    del.className = "confirmNo";
    del.style.marginLeft = "auto";
    del.textContent = "✕";
    del.title = tr("Delete list", "Liste löschen");
    del.addEventListener("click", async (e) => {
      e.stopPropagation();
      if (!window.confirm(tr("Delete this want list?", "Diese Wantsliste löschen?"))) return;
      await fetch("/api/sealedWishlists/delete", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ id: list.id })
      });
      await loadSealedWishlistsForGame(activeGame);
      renderSealedWishlistOverlay();
    });
    row.appendChild(del);
    content.appendChild(row);
  }
}

// Ausgelöste Preis-Alarme einmal pro Seiten-Sitzung anzeigen - Pendant zum
// Alarm-Dialog beim App-Start (Schwelle erreicht/unterschritten)
let sealedAlarmsShown = false;
async function maybeShowSealedAlarms() {
  if (sealedAlarmsShown) return;
  try {
    const res = await authedFetch("/api/sealedWishlistAlarms");
    if (!res.ok) return;
    const alarms = await res.json();
    if (alarms.length === 0) return;
    sealedAlarmsShown = true;
    const names = alarms.slice(0, 3).map(a => a.name || "?").join(", ");
    const more = alarms.length > 3 ? " +" + (alarms.length - 3) : "";
    showAddToast("🔔 " + tr("Price alert: ", "Preis-Alarm: ") + names + more + " " + tr("reached your target price", "hat deinen Wunschpreis erreicht"));
  } catch (err) {
    // Best-effort-Hinweis
  }
}

// Sortierung + Raritäts-Filter fürs Karten-Grid (31.07., Nutzer-Vorgabe) -
// "date" verändert die Reihenfolge bewusst NICHT (entspricht "wie es jetzt
// eh ist"), analog zu GridSortMode.DATE_ADDED in der App. Raritäten werden
// aus den ÜBERGEBENEN (noch ungefilterten) items berechnet, sonst
// verschwindet eine gewählte Rarität aus der Chip-Reihe, sobald gefiltert
// wird.
// Namenssuche (10.08., Nutzer-Vorgabe "Feature-Parität App <-> Web") - die
// App filtert "überall wo man Karten sieht und sortiert werden kann" nach
// Namen (siehe gridSearchQuery in App.kt), die Weboberfläche hatte bisher
// nur Sortierung + Raritäts-Chips ohne Freitextsuche.
let gridSearchQuery = "";

function applySortAndFilter(items) {
  let result = gridRarityFilter.size === 0 ? items : items.filter(it => gridRarityFilter.has(it.rarity));
  // Typ-/Farb-Filter (28.08.) - ODER innerhalb eines Filters, UND zwischen
  // den Filtern (wie in der App: displayedGridCards in App.kt)
  if (gridTypeFilter.size > 0) {
    result = result.filter(it => it.ruleSupertype && gridTypeFilter.has(it.ruleSupertype));
  }
  if (gridColorFilter.size > 0) {
    result = result.filter(it => (it.ruleSubtypes || []).some(s => gridColorFilter.has(s)));
  }
  if (gridSearchQuery.trim()) {
    const q = gridSearchQuery.trim().toLowerCase();
    result = result.filter(it => it.name.toLowerCase().includes(q));
  }
  if (gridSortMode === "nameAsc") {
    result = [...result].sort((a, b) => a.name.localeCompare(b.name));
  } else if (gridSortMode === "nameDesc") {
    result = [...result].sort((a, b) => b.name.localeCompare(a.name));
  }
  return result;
}

// Sucheingabe fürs Karten-Grid - eigene ID + Refokussieren nach dem
// verzögerten render() (10.08.), sonst würde der Cursor bei jedem Tastendruck
// nach der Debounce-Pause verloren gehen, da render() das ganze Panel neu
// aufbaut und damit auch dieses Eingabefeld durch ein neues Element ersetzt.
function buildGridSearchInput() {
  const input = document.createElement("input");
  input.type = "text";
  input.id = "gridSearchInput";
  input.className = "addSearchInput gridSearchInput";
  input.placeholder = tr("Search cards…", "Karte suchen…");
  input.autocomplete = "off";
  input.value = gridSearchQuery;
  input.addEventListener("input", () => {
    gridSearchQuery = input.value;
    clearTimeout(buildGridSearchInput._t);
    buildGridSearchInput._t = setTimeout(() => {
      render();
      const el = document.getElementById("gridSearchInput");
      if (el) { el.focus(); el.setSelectionRange(el.value.length, el.value.length); }
    }, 200);
  });
  return input;
}

function buildSortFilterRow(items) {
  const row = document.createElement("div");
  row.className = "gridSortFilterRow";
  [["date", tr("Date", "Datum")], ["nameAsc", "A-Z"], ["nameDesc", "Z-A"]].forEach(([mode, label]) => {
    const chip = document.createElement("button");
    chip.className = "gridSortChip" + (gridSortMode === mode ? " active" : "");
    chip.textContent = label;
    chip.addEventListener("click", () => { gridSortMode = mode; render(); });
    row.appendChild(chip);
  });
  const rarities = Array.from(new Set(items.map(it => it.rarity).filter(Boolean))).sort();
  if (rarities.length > 0) {
    const divider = document.createElement("span");
    divider.className = "gridSortDivider";
    row.appendChild(divider);
    rarities.forEach(r => {
      const chip = document.createElement("button");
      chip.className = "gridSortChip" + (gridRarityFilter.has(r) ? " active" : "");
      chip.textContent = r;
      chip.addEventListener("click", () => {
        if (gridRarityFilter.has(r)) gridRarityFilter.delete(r); else gridRarityFilter.add(r);
        render();
      });
      row.appendChild(chip);
    });
  }
  // Typ-/Farb-Chips (28.08., Parität zur App vom 19.08.) - erscheinen nur,
  // wenn die Einträge die Katalog-Regelfelder tragen (Set-Grids)
  const addChipGroup = (values, filterSet) => {
    if (values.length === 0) return;
    const divider = document.createElement("span");
    divider.className = "gridSortDivider";
    row.appendChild(divider);
    values.forEach(v => {
      const chip = document.createElement("button");
      chip.className = "gridSortChip" + (filterSet.has(v) ? " active" : "");
      chip.textContent = v;
      chip.addEventListener("click", () => {
        if (filterSet.has(v)) filterSet.delete(v); else filterSet.add(v);
        render();
      });
      row.appendChild(chip);
    });
  };
  addChipGroup(Array.from(new Set(items.map(it => it.ruleSupertype).filter(Boolean))).sort(), gridTypeFilter);
  addChipGroup(Array.from(new Set(items.flatMap(it => it.ruleSubtypes || []))).sort(), gridColorFilter);
  return row;
}

// Set-Tracking (26.07., Nutzer-Vorgabe) - "Alle" ist selbst ein Akkordeon-
// Eintrag (anfangs offen, zeigt alle eigenen Karten flach wie zuvor). Klick
// darauf klappt zu und gibt die echten Sets frei (mit owned/total-Zähler),
// Klick auf ein Set öffnet es (mit den drei Filtern daneben) und schließt
// alles andere - klassisches Akkordeon, nur ein Eintrag gleichzeitig offen.
function renderCardsTab() {
  const container = document.getElementById("setAccordion");
  const stats = document.getElementById("stats");
  container.innerHTML = "";

  const ownedForGame = allCards.filter(item => item.game === activeGame);

  // "Alle" - immer vorhanden, keine Sets/Katalog nötig. Zeigt (sobald die
  // Sets geladen sind) denselben owned/total-Zähler wie die einzelnen Sets,
  // nur aufsummiert über das ganze TCG (Nutzer-Vorgabe 26.07.)
  const allItem = document.createElement("div");
  allItem.className = "setAccordionItem" + (openSetId === "ALL" ? " open" : "");
  const allHeader = document.createElement("button");
  allHeader.className = "setAccordionHeader";
  const gameSets = setsCache[activeGame];
  const allCountHtml = gameSets
    ? "<span class=\"setAccordionCount\">" +
      gameSets.reduce((s, set) => s + set.ownedCount, 0) + "/" +
      gameSets.reduce((s, set) => s + set.totalCards, 0) + "</span>"
    : "";
  allHeader.innerHTML = "<span>" + tr("All", "Alle") + "</span>" + allCountHtml;
  allHeader.addEventListener("click", () => {
    openSetId = openSetId === "ALL" ? null : "ALL";
    gridSearchQuery = "";
    render();
  });
  allItem.appendChild(allHeader);
  const allBody = document.createElement("div");
  allBody.className = "setAccordionBody";
  if (openSetId === "ALL") {
    allBody.appendChild(buildMainActionButton({
      label: tr("Add", "Hinzufügen"),
      imageSrc: (GAME_BY_CODE[activeGame] || {}).image || GAMES[0].image,
      onClick: () => openAddOverlay("cards")
    }));
    // Mehrfachauswahl (03.08., Nutzer-Vorgabe "neben dem Hinzufügen Button
    // oben... einen Button mit Auswählen") - Löschen + Binder-Hinzufügen für
    // eigene Karten, siehe ownedSelectionMode-Kommentar oben
    const selectRow = document.createElement("div");
    selectRow.className = "gridSortFilterRow";
    const selectBtn = document.createElement("button");
    selectBtn.className = "gridSortChip" + (ownedSelectionMode ? " active" : "");
    selectBtn.textContent = ownedSelectionMode
      ? tr("End selection (", "Auswahl beenden (") + selectedOwnedItemIds.size + ")"
      : tr("Select", "Auswählen");
    selectBtn.addEventListener("click", () => {
      ownedSelectionMode = !ownedSelectionMode;
      selectedOwnedItemIds = new Set();
      ownedSelectionBinderId = null;
      if (ownedSelectionMode && !bindersCache[activeGame]) {
        loadBindersForGame(activeGame).then(render);
      } else {
        render();
      }
    });
    selectRow.appendChild(selectBtn);
    allBody.appendChild(selectRow);
    if (ownedSelectionMode && selectedOwnedItemIds.size > 0) {
      allBody.appendChild(buildOwnedSelectionToolbar(ownedForGame));
    }
    allBody.appendChild(buildGridSearchInput());
    allBody.appendChild(buildSortFilterRow(ownedForGame));
    const displayedAll = applySortAndFilter(ownedForGame);
    if (displayedAll.length === 0) {
      const empty = document.createElement("p");
      empty.className = "status";
      empty.style.display = "block";
      empty.textContent = tr("No cards in this selection.", "Keine Karten in dieser Auswahl.");
      allBody.appendChild(empty);
    }
    const allGrid = document.createElement("div");
    allGrid.className = "grid";
    renderGridInto(allGrid, displayedAll, {
      selectable: ownedSelectionMode,
      selectedIds: selectedOwnedItemIds,
      selectKey: it => it.id
    });
    allBody.appendChild(allGrid);
    stats.textContent = displayedAll.length + " " + tr("entries", "Einträge");
  }
  allItem.appendChild(allBody);
  container.appendChild(allItem);

  // Wunschlisten (27.07., Nutzer-Vorgabe) - eigener Eintrag direkt unter
  // "Alle" statt eines Sets, siehe renderWishlistsBody()
  const wishlistsItem = document.createElement("div");
  wishlistsItem.className = "setAccordionItem" + (openSetId === "WISHLISTS" ? " open" : "");
  const wishlistsHeader = document.createElement("button");
  wishlistsHeader.className = "setAccordionHeader";
  const gameWishlists = wishlistsCache[activeGame];
  const wishlistsCountHtml = gameWishlists
    ? "<span class=\"setAccordionCount\">" + gameWishlists.reduce((s, w) => s + w.itemCount, 0) + "</span>"
    : "";
  wishlistsHeader.innerHTML = "<span>Wants</span>" + wishlistsCountHtml;
  wishlistsHeader.addEventListener("click", () => {
    openSetId = openSetId === "WISHLISTS" ? null : "WISHLISTS";
    if (openSetId === "WISHLISTS" && !wishlistsCache[activeGame]) loadWishlistsForGame(activeGame);
    render();
  });
  wishlistsItem.appendChild(wishlistsHeader);
  const wishlistsBody = document.createElement("div");
  wishlistsBody.className = "setAccordionBody";
  if (openSetId === "WISHLISTS") {
    renderWishlistsBody(wishlistsBody);
  }
  wishlistsItem.appendChild(wishlistsBody);
  container.appendChild(wishlistsItem);

  const sets = setsCache[activeGame];
  if (!sets) {
    // Noch nicht geladen - anfordern und bei Ankunft neu rendern (nur
    // relevant, falls "Alle" gerade zu ist bzw. man ein Set öffnen will)
    loadSetsForGame(activeGame);
    return;
  }

  for (const set of sets) {
    const isOpen = openSetId === set.id;
    const item = document.createElement("div");
    item.className = "setAccordionItem" + (isOpen ? " open" : "");

    const header = document.createElement("button");
    header.className = "setAccordionHeader";
    header.innerHTML =
      "<span>" + set.name + "</span><span class=\"setAccordionCount\">" + set.ownedCount + "/" + set.totalCards + "</span>";
    header.addEventListener("click", () => {
      openSetId = openSetId === set.id ? null : set.id;
      setFilter = "owned";
      gridSearchQuery = "";
      resetSelection();
      render();
    });
    item.appendChild(header);

    const body = document.createElement("div");
    body.className = "setAccordionBody";
    if (isOpen) {
      renderOpenSetBody(body, set, ownedForGame);
      if (openSetId === set.id) stats.textContent = ""; // pro Filter unten gesetzt
    }
    item.appendChild(body);
    container.appendChild(item);
  }
}

async function loadWishlistsForGame(game) {
  try {
    const [wRes, iRes] = await Promise.all([
      authedFetch("/api/wishlists?game=" + encodeURIComponent(game)),
      authedFetch("/api/wishlistItems?game=" + encodeURIComponent(game))
    ]);
    if (wRes.ok) wishlistsCache[game] = await wRes.json();
    if (iRes.ok) wishlistItemsCache[game] = await iRes.json();
    if (activeTab === "cards" && activeGame === game) render();
  } catch (err) {
    // Wunschlisten sind ein Zusatzfeature - bei Fehlern bleibt der Rest der
    // Seite nutzbar, kein harter Fehlerzustand nötig
  }
}

async function createWishlist(name) {
  try {
    await fetch("/api/wishlists", {
      method: "POST",
      headers: authHeaders(true),
      body: JSON.stringify({ name, game: activeGame })
    });
    delete wishlistsCache[activeGame];
    delete wishlistItemsCache[activeGame];
    await loadWishlistsForGame(activeGame);
    render();
  } catch (err) {
    showAddToast(tr("Create failed", "Erstellen fehlgeschlagen"));
  }
}

// Zeigt entweder die Listen-Übersicht oder (falls eine Liste offen ist)
// deren Karten - beides innerhalb desselben Akkordeon-Eintrags "Wishlists".
function renderWishlistsBody(body) {
  const wishlists = wishlistsCache[activeGame];
  const items = wishlistItemsCache[activeGame];
  if (!wishlists || !items) {
    const loading = document.createElement("p");
    loading.className = "status";
    loading.style.display = "block";
    loading.textContent = tr("Loading…", "Lädt…");
    body.appendChild(loading);
    return;
  }
  if (openWishlistId != null && !wishlists.find(w => w.id === openWishlistId)) {
    openWishlistId = null;
  }
  const openList = wishlists.find(w => w.id === openWishlistId);
  if (!openList) {
    renderWishlistOverview(body, wishlists, items);
  } else {
    renderWishlistDetail(body, openList, items.filter(it => it.wishlistId === openList.id));
  }
}

// Übersicht aller Wunschlisten des aktuellen TCGs - "Wanted X ca. Y€" oben
// rechts (Nutzer-Vorgabe), darunter der Haupt-Button zum Anlegen einer neuen
// Liste (bzw. "Löschen (N)", solange per "Auswählen" Listen markiert sind).
function renderWishlistOverview(body, wishlists, items) {
  const stats = document.getElementById("stats");
  const totalEur = items.reduce((sum, it) => sum + tileMarketEur(it), 0);
  const statRow = document.createElement("div");
  statRow.className = "wishlistStatRow";
  statRow.textContent = "Wanted " + items.length + " · ca. " + formatEur(totalEur);
  body.appendChild(statRow);

  body.appendChild(buildMainActionButton({
    label: tr("Create list", "Liste erstellen"),
    imageSrc: (GAME_BY_CODE[activeGame] || {}).image || GAMES[0].image,
    onClick: () => {
      const name = window.prompt(tr("Name of the new wishlist:", "Name der neuen Wunschliste:"));
      if (name && name.trim()) createWishlist(name.trim());
    }
  }));

  if (wishlists.length === 0) {
    const empty = document.createElement("p");
    empty.className = "status";
    empty.style.display = "block";
    empty.textContent = tr("No wishlists yet.", "Noch keine Wunschlisten.");
    body.appendChild(empty);
    stats.textContent = "";
    return;
  }

  const filters = document.createElement("div");
  filters.className = "setFilters";
  const selectBtn = document.createElement("button");
  selectBtn.className = "setFilter" + (wishlistListSelectionMode ? " active" : "");
  selectBtn.textContent = wishlistListSelectionMode
    ? tr("End selection (", "Auswahl beenden (") + selectedWishlistIds.size + ")"
    : tr("Select", "Auswählen");
  selectBtn.addEventListener("click", () => {
    wishlistListSelectionMode = !wishlistListSelectionMode;
    if (!wishlistListSelectionMode) selectedWishlistIds = new Set();
    render();
  });
  filters.appendChild(selectBtn);
  if (wishlistListSelectionMode && selectedWishlistIds.size > 0) {
    const delBtn = document.createElement("button");
    delBtn.className = "setFilter";
    delBtn.style.borderColor = "#E57373";
    delBtn.style.color = "#E57373";
    delBtn.textContent = tr("Delete (", "Löschen (") + selectedWishlistIds.size + ")";
    delBtn.addEventListener("click", () => {
      const ids = Array.from(selectedWishlistIds);
      showConfirm(ids.length + " " + tr("list(s) will be deleted - continue?", "Liste(n) wirklich löschen?"), async () => {
        try {
          await fetch("/api/wishlists/delete", {
            method: "POST",
            headers: authHeaders(true),
            body: JSON.stringify({ ids })
          });
        } catch (err) {
          showAddToast(tr("Delete failed", "Löschen fehlgeschlagen"));
        }
        selectedWishlistIds = new Set();
        wishlistListSelectionMode = false;
        delete wishlistsCache[activeGame];
        delete wishlistItemsCache[activeGame];
        await loadWishlistsForGame(activeGame);
        render();
      });
    });
    filters.appendChild(delBtn);
  }
  body.appendChild(filters);

  const list = document.createElement("div");
  list.className = "wishlistList";
  wishlists.forEach(w => {
    const row = document.createElement("div");
    const isSelected = wishlistListSelectionMode && selectedWishlistIds.has(w.id);
    row.className = "wishlistRow" + (isSelected ? " selected" : "");
    const itemsInList = items.filter(it => it.wishlistId === w.id);
    const listTotalEur = itemsInList.reduce((sum, it) => sum + tileMarketEur(it), 0);
    row.innerHTML = "<span>" + w.name + "</span><span class=\"wishlistRowCount\">" +
      w.itemCount + " · ca. " + formatEur(listTotalEur) + "</span>";
    row.addEventListener("click", () => {
      if (wishlistListSelectionMode) {
        if (selectedWishlistIds.has(w.id)) {
          selectedWishlistIds.delete(w.id);
        } else {
          selectedWishlistIds.add(w.id);
        }
      } else {
        openWishlistId = w.id;
        wishlistRarityFilter = new Set();
        wishlistSearchQuery = "";
      }
      render();
    });
    list.appendChild(row);
  });
  body.appendChild(list);
  stats.textContent = "";
}

// Inhalt einer offenen Wunschliste - "←"-Zurück-Zeile (entspricht dem X-
// Button unten links in der App), Export + Hinzufügen/Entfernen daneben,
// dann das Karten-Grid. Antippen einer Karte wählt sie direkt aus (kein
// separater "Auswählen"-Umschalter nötig wie bei den Listen selbst, da es
// hier keine zweite Aktion gibt, mit der ein normaler Klick kollidieren
// könnte) - der Hinzufügen-Button wird zum Entfernen-Button, sobald
// mindestens eine Karte markiert ist.
function renderWishlistDetail(body, wishlist, items) {
  const stats = document.getElementById("stats");

  const backRow = document.createElement("div");
  backRow.className = "wishlistBackRow";
  const backBtn = document.createElement("button");
  backBtn.className = "wishlistBackButton";
  backBtn.textContent = "← " + wishlist.name;
  backBtn.addEventListener("click", () => {
    openWishlistId = null;
    selectedWishlistItemIds = new Set();
    wishlistRarityFilter = new Set();
    wishlistSearchQuery = "";
    render();
  });
  backRow.appendChild(backBtn);
  // Umbenennen (20.08., Parität zur App vom 19.08.) - gleiches Muster wie
  // beim Binder: Stift neben dem Namen, window.prompt, Server-Endpunkt
  const renameBtn = document.createElement("button");
  renameBtn.className = "detailCmChip";
  renameBtn.textContent = "✎";
  renameBtn.title = tr("Rename list", "Liste umbenennen");
  renameBtn.style.marginLeft = "8px";
  renameBtn.addEventListener("click", async () => {
    const newName = window.prompt(tr("New name for the list", "Neuer Name für die Liste"), wishlist.name);
    if (!newName || !newName.trim() || newName.trim() === wishlist.name) return;
    try {
      await fetch("/api/wishlists/rename", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ id: wishlist.id, name: newName.trim() })
      });
      wishlist.name = newName.trim();
      render();
    } catch (err) {
      showAddToast(tr("Renaming failed", "Umbenennen fehlgeschlagen"));
    }
  });
  backRow.appendChild(renameBtn);
  body.appendChild(backRow);

  const actionRow = document.createElement("div");
  actionRow.className = "wishlistActionRow";
  actionRow.appendChild(buildMainActionButton({
    label: tr("Export", "Exportieren"),
    imageSrc: "images/btn_export.png",
    // Export nutzt bewusst die GEFILTERTE/SORTIERTE Liste (31.07., Nutzer-
    // Vorgabe "damit man wirklich auf seinen Bedarf abgestimmte Wantlisten
    // exportieren kann"), nicht die volle Liste
    onClick: () => runWishlistExport(wishlist, applyWishlistSortAndFilter(items))
  }));
  actionRow.appendChild(buildMainActionButton({
    label: selectedWishlistItemIds.size > 0 ? (tr("Remove (", "Entfernen (") + selectedWishlistItemIds.size + ")") : tr("Add", "Hinzufügen"),
    imageSrc: (GAME_BY_CODE[activeGame] || {}).image || GAMES[0].image,
    onClick: () => {
      if (selectedWishlistItemIds.size > 0) {
        const ids = Array.from(selectedWishlistItemIds);
        showConfirm(ids.length + " " + tr("card(s) will be removed from the list - continue?", "Karte(n) wirklich aus der Liste entfernen?"), async () => {
          try {
            await fetch("/api/wishlistItems/delete", {
              method: "POST",
              headers: authHeaders(true),
              body: JSON.stringify({ ids })
            });
          } catch (err) {
            showAddToast(tr("Removing failed", "Entfernen fehlgeschlagen"));
          }
          selectedWishlistItemIds = new Set();
          delete wishlistItemsCache[activeGame];
          await loadWishlistsForGame(activeGame);
          render();
        });
      } else {
        openAddOverlay("wishlist", wishlist.id);
      }
    }
  }));
  body.appendChild(actionRow);

  if (items.length === 0) {
    const empty = document.createElement("p");
    empty.className = "status";
    empty.style.display = "block";
    empty.textContent = tr("No cards in this list yet.", "Noch keine Karten in dieser Liste.");
    body.appendChild(empty);
    stats.textContent = "";
    return;
  }

  body.appendChild(buildWishlistSearchInput());
  body.appendChild(buildWishlistSortFilterRow(items));
  const displayed = applyWishlistSortAndFilter(items);
  const grid = document.createElement("div");
  grid.className = "grid";
  displayed.forEach(item => grid.appendChild(buildWishlistCardTile(item)));
  body.appendChild(grid);
  stats.textContent = displayed.length + " " + tr("entries", "Einträge");
}

// Sortierung + Raritäts-Filter fürs Wunschlisten-Grid (31.07., Nutzer-
// Vorgabe) - eigene, kleine Kopie von applySortAndFilter()/
// buildSortFilterRow() (Karten-Grid) statt Wiederverwendung, damit beide
// unabhängig bleiben (analog WishlistSortMode in WishlistScreen.kt)
let wishlistSearchQuery = "";

function applyWishlistSortAndFilter(items) {
  let result = wishlistRarityFilter.size === 0 ? items : items.filter(it => wishlistRarityFilter.has(it.rarity));
  if (wishlistSearchQuery.trim()) {
    const q = wishlistSearchQuery.trim().toLowerCase();
    result = result.filter(it => it.name.toLowerCase().includes(q));
  }
  if (wishlistSortMode === "nameAsc") {
    result = [...result].sort((a, b) => a.name.localeCompare(b.name));
  } else if (wishlistSortMode === "nameDesc") {
    result = [...result].sort((a, b) => b.name.localeCompare(a.name));
  }
  return result;
}

function buildWishlistSearchInput() {
  const input = document.createElement("input");
  input.type = "text";
  input.id = "wishlistSearchInput";
  input.className = "addSearchInput gridSearchInput";
  input.placeholder = tr("Search cards…", "Karte suchen…");
  input.autocomplete = "off";
  input.value = wishlistSearchQuery;
  input.addEventListener("input", () => {
    wishlistSearchQuery = input.value;
    clearTimeout(buildWishlistSearchInput._t);
    buildWishlistSearchInput._t = setTimeout(() => {
      render();
      const el = document.getElementById("wishlistSearchInput");
      if (el) { el.focus(); el.setSelectionRange(el.value.length, el.value.length); }
    }, 200);
  });
  return input;
}

function buildWishlistSortFilterRow(items) {
  const row = document.createElement("div");
  row.className = "gridSortFilterRow";
  [["date", tr("Date", "Datum")], ["nameAsc", "A-Z"], ["nameDesc", "Z-A"]].forEach(([mode, label]) => {
    const chip = document.createElement("button");
    chip.className = "gridSortChip" + (wishlistSortMode === mode ? " active" : "");
    chip.textContent = label;
    chip.addEventListener("click", () => { wishlistSortMode = mode; render(); });
    row.appendChild(chip);
  });
  const rarities = Array.from(new Set(items.map(it => it.rarity).filter(Boolean))).sort();
  if (rarities.length > 0) {
    const divider = document.createElement("span");
    divider.className = "gridSortDivider";
    row.appendChild(divider);
    rarities.forEach(r => {
      const chip = document.createElement("button");
      chip.className = "gridSortChip" + (wishlistRarityFilter.has(r) ? " active" : "");
      chip.textContent = r;
      chip.addEventListener("click", () => {
        if (wishlistRarityFilter.has(r)) wishlistRarityFilter.delete(r); else wishlistRarityFilter.add(r);
        render();
      });
      row.appendChild(chip);
    });
  }
  return row;
}

function buildWishlistCardTile(item) {
  const isSelected = selectedWishlistItemIds.has(item.id);
  const card = document.createElement("div");
  card.className = "card selectable" + (isSelected ? " selected" : "");

  const art = document.createElement("div");
  if (item.imageUrl) {
    art.className = "art";
    const img = document.createElement("img");
    img.src = item.imageUrl;
    img.loading = "lazy";
    img.alt = item.name;
    art.appendChild(img);
  } else {
    art.className = "art placeholder";
    art.textContent = tr("No image", "Kein Bild");
  }
  card.appendChild(art);

  const name = document.createElement("div");
  name.className = "name";
  name.textContent = item.name;
  card.appendChild(name);

  const meta = document.createElement("div");
  meta.className = "meta";
  const right = document.createElement("span");
  right.textContent = formatTileMarketPrice(item);
  meta.appendChild(right);
  card.appendChild(meta);

  card.addEventListener("click", () => {
    if (selectedWishlistItemIds.has(item.id)) {
      selectedWishlistItemIds.delete(item.id);
    } else {
      selectedWishlistItemIds.add(item.id);
    }
    render();
  });
  return card;
}

function runWishlistExport(wishlist, items) {
  const lines = items.map(item => {
    const set = (setsCache[activeGame] || []).find(s => s.id === item.setId);
    return "1x " + item.name + " (" + (set ? set.name : "?") + ")";
  });
  const safeName = wishlist.name.replace(/[^A-Za-z0-9]+/g, "_").replace(/^_+|_+$/g, "");
  downloadTextFile(safeName + "_wishlist.txt", lines.join("\n"));
}

// Binder (31.07., Nutzer-Vorgabe) - funktional 1:1 identisch zu den
// Wunschlisten-Funktionen oben (Hinzufügen/Exportieren "genau so wie die
// Wishlist"), aber als eigener Tab statt Akkordeon-Eintrag - siehe
// .binderPanel in style.css. Nutzt bewusst dieselben .wishlist*-CSS-Klassen
// für Liste/Zeilen/Karten-Kacheln, um keine optisch identische Kopie im
// Stylesheet zu duplizieren.
async function loadBindersForGame(game) {
  try {
    const [bRes, iRes] = await Promise.all([
      authedFetch("/api/binders?game=" + encodeURIComponent(game)),
      authedFetch("/api/binderItems?game=" + encodeURIComponent(game))
    ]);
    if (bRes.ok) bindersCache[game] = await bRes.json();
    if (iRes.ok) binderItemsCache[game] = await iRes.json();
    if (activeTab === "binder" && activeGame === game) render();
  } catch (err) {
    // Binder sind ein Zusatzfeature - bei Fehlern bleibt der Rest der Seite
    // nutzbar, kein harter Fehlerzustand nötig
  }
}

async function createBinder(name, pageSize) {
  try {
    await fetch("/api/binders", {
      method: "POST",
      headers: authHeaders(true),
      body: JSON.stringify({ name, game: activeGame, pageSize })
    });
    delete bindersCache[activeGame];
    delete binderItemsCache[activeGame];
    await loadBindersForGame(activeGame);
    render();
  } catch (err) {
    showAddToast(tr("Create failed", "Erstellen fehlgeschlagen"));
  }
}

// Binder-Erstellen-Dialog (31.07., Nutzer-Vorgabe "9, 12 oder 16 Karten pro
// Seite") - ersetzt window.prompt(), da hier neben dem Namen auch die
// Seitengröße gewählt werden muss. Reine DOM-Verdrahtung, einmalig beim
// Laden der Seite (siehe ganz unten in dieser Datei).
function openCreateBinderDialog() {
  document.getElementById("createBinderName").value = "";
  createBinderPageSize = 9;
  renderCreateBinderPageSizes();
  document.getElementById("createBinderOverlay").classList.add("visible");
  setTimeout(() => document.getElementById("createBinderName").focus(), 50);
}

function closeCreateBinderDialog() {
  document.getElementById("createBinderOverlay").classList.remove("visible");
}

function renderCreateBinderPageSizes() {
  const wrap = document.getElementById("createBinderPageSizes");
  wrap.innerHTML = "";
  [4, 9, 12, 16].forEach(size => {
    const chip = document.createElement("button");
    chip.className = "createBinderPageSizeChip" + (createBinderPageSize === size ? " active" : "");
    chip.textContent = String(size);
    chip.addEventListener("click", () => {
      createBinderPageSize = size;
      renderCreateBinderPageSizes();
    });
    wrap.appendChild(chip);
  });
}

// Dispatcher fürs Binder-Panel - Übersicht oder (falls einer offen ist)
// dessen Karten, analog zu renderWishlistsBody()
function renderBinderTab() {
  const panel = document.getElementById("binderPanel");
  panel.innerHTML = "";
  const stats = document.getElementById("stats");
  const binders = bindersCache[activeGame];
  const items = binderItemsCache[activeGame];
  if (!binders || !items) {
    if (!binders) loadBindersForGame(activeGame);
    const loading = document.createElement("p");
    loading.className = "status";
    loading.style.display = "block";
    loading.textContent = tr("Loading…", "Lädt…");
    panel.appendChild(loading);
    return;
  }
  if (openBinderId != null && !binders.find(b => b.id === openBinderId)) {
    openBinderId = null;
  }
  const openBinder = binders.find(b => b.id === openBinderId);
  if (!openBinder) {
    renderBinderOverview(panel, binders, items);
  } else {
    renderBinderDetail(panel, openBinder, items.filter(it => it.binderId === openBinder.id));
  }
  stats.textContent = "";
}

function renderBinderOverview(panel, binders, items) {
  const totalEur = items.reduce((sum, it) => sum + tileMarketEur(it), 0);
  const statRow = document.createElement("div");
  statRow.className = "wishlistStatRow";
  statRow.textContent = "Binder " + items.length + " · ca. " + formatEur(totalEur);
  panel.appendChild(statRow);

  panel.appendChild(buildMainActionButton({
    label: tr("Create binder", "Binder erstellen"),
    imageSrc: (GAME_BY_CODE[activeGame] || {}).image || GAMES[0].image,
    onClick: openCreateBinderDialog
  }));

  if (binders.length === 0) {
    const empty = document.createElement("p");
    empty.className = "status";
    empty.style.display = "block";
    empty.textContent = tr("No binders yet.", "Noch keine Binder.");
    panel.appendChild(empty);
    return;
  }

  const filters = document.createElement("div");
  filters.className = "setFilters";
  const selectBtn = document.createElement("button");
  selectBtn.className = "setFilter" + (binderListSelectionMode ? " active" : "");
  selectBtn.textContent = binderListSelectionMode
    ? tr("End selection (", "Auswahl beenden (") + selectedBinderIds.size + ")"
    : tr("Select", "Auswählen");
  selectBtn.addEventListener("click", () => {
    binderListSelectionMode = !binderListSelectionMode;
    if (!binderListSelectionMode) selectedBinderIds = new Set();
    render();
  });
  filters.appendChild(selectBtn);
  if (binderListSelectionMode && selectedBinderIds.size > 0) {
    const delBtn = document.createElement("button");
    delBtn.className = "setFilter";
    delBtn.style.borderColor = "#E57373";
    delBtn.style.color = "#E57373";
    delBtn.textContent = tr("Delete (", "Löschen (") + selectedBinderIds.size + ")";
    delBtn.addEventListener("click", () => {
      const ids = Array.from(selectedBinderIds);
      showConfirm(ids.length + " " + tr("binder(s) will be deleted - continue?", "Binder wirklich löschen?"), async () => {
        try {
          await fetch("/api/binders/delete", {
            method: "POST",
            headers: authHeaders(true),
            body: JSON.stringify({ ids })
          });
        } catch (err) {
          showAddToast(tr("Delete failed", "Löschen fehlgeschlagen"));
        }
        selectedBinderIds = new Set();
        binderListSelectionMode = false;
        delete bindersCache[activeGame];
        delete binderItemsCache[activeGame];
        await loadBindersForGame(activeGame);
        render();
      });
    });
    filters.appendChild(delBtn);
  }
  panel.appendChild(filters);

  // "Echte" Binder (25.08., Nutzer-Vorgabe): Grid gezeichneter Buchdeckel
  // statt Text-Zeilen - CSS-Nachbau des Compose-BinderCoverVisual. Farbe je
  // Binder über die Palette unter dem Namen (server-eigene Deko, siehe
  // /api/binders/color); Standard = Akzentfarbe des aktiven TCGs.
  const grid = document.createElement("div");
  grid.className = "binderGrid";
  binders.forEach(b => {
    const isSelected = binderListSelectionMode && selectedBinderIds.has(b.id);
    const itemsInBinder = items.filter(it => it.binderId === b.id);
    const binderTotalEur = itemsInBinder.reduce((sum, it) => sum + tileMarketEur(it), 0);
    const base = b.color || colorFor(activeGame);

    const cardEl = document.createElement("div");
    cardEl.className = "binderCard" + (isSelected ? " selected" : "");

    const cover = document.createElement("div");
    cover.className = "binderCover";
    if (b.coverImageUrl) {
      // Gesynctes Cover-Foto (25.08.) - Deckelfläche wird zum Foto,
      // Rücken + Ringe bleiben darüber, damit es ein "Binder" bleibt
      cover.style.background = "url('" + b.coverImageUrl + "') center/cover no-repeat";
    } else {
      cover.style.background = "linear-gradient(135deg, " + shadeBinderColor(base, 1.25) + ", " + base + " 55%, " + shadeBinderColor(base, 0.6) + ")";
    }
    const spine = document.createElement("div");
    spine.className = "binderSpine";
    spine.style.background = "linear-gradient(to right, " + shadeBinderColor(base, 0.45) + ", " + shadeBinderColor(base, 0.8) + ")";
    cover.appendChild(spine);
    [20, 50, 80].forEach(pct => {
      const ring = document.createElement("div");
      ring.className = "binderRing";
      ring.style.top = "calc(" + pct + "% - 6px)";
      cover.appendChild(ring);
    });
    cover.addEventListener("click", () => {
      if (binderListSelectionMode) {
        if (selectedBinderIds.has(b.id)) {
          selectedBinderIds.delete(b.id);
        } else {
          selectedBinderIds.add(b.id);
        }
      } else {
        openBinderId = b.id;
        binderRarityFilter = new Set();
        binderSearchQuery = "";
      }
      render();
    });
    cardEl.appendChild(cover);

    const nameEl = document.createElement("div");
    nameEl.className = "binderName wishlistRowRenamable";
    nameEl.textContent = b.name;
    nameEl.title = tr("Click to rename", "Zum Umbenennen anklicken");
    nameEl.addEventListener("click", (e) => {
      e.stopPropagation();
      renameBinderPrompt(b);
    });
    cardEl.appendChild(nameEl);

    const metaEl = document.createElement("div");
    metaEl.className = "binderMeta";
    metaEl.textContent = b.itemCount + " · ca. " + formatEur(binderTotalEur);
    cardEl.appendChild(metaEl);

    const palette = document.createElement("div");
    palette.className = "binderPalette";
    const colors = [null, "#C62828", "#1565C0", "#2E7D32", "#F9A825", "#E65100", "#6A1B9A", "#AD1457", "#00838F", "#546E7A", "#212121"];
    colors.forEach(hex => {
      const dot = document.createElement("div");
      dot.className = "binderPaletteDot" + ((b.color || null) === hex ? " active" : "");
      dot.style.background = hex || colorFor(activeGame);
      dot.title = hex ? hex : tr("Default (TCG color)", "Standard (TCG-Farbe)");
      dot.addEventListener("click", async (e) => {
        e.stopPropagation();
        try {
          await fetch("/api/binders/color", {
            method: "POST",
            headers: authHeaders(true),
            body: JSON.stringify({ id: b.id, color: hex })
          });
          b.color = hex;
          render();
        } catch (err) {
          showAddToast(tr("Saving failed", "Speichern fehlgeschlagen"));
        }
      });
      palette.appendChild(dot);
    });
    cardEl.appendChild(palette);

    grid.appendChild(cardEl);
  });
  panel.appendChild(grid);
}

// Hex-Farbe aufhellen/abdunkeln für die Binder-Verläufe (25.08.) - Pendant
// zu Color.scaled() im Compose-BinderCoverVisual
function shadeBinderColor(hex, f) {
  const clean = (hex || "#888888").replace("#", "");
  const num = parseInt(clean, 16);
  if (isNaN(num)) return hex;
  const r = Math.min(255, Math.round(((num >> 16) & 255) * f));
  const g = Math.min(255, Math.round(((num >> 8) & 255) * f));
  const bl = Math.min(255, Math.round((num & 255) * f));
  return "rgb(" + r + "," + g + "," + bl + ")";
}

// Umbenennen (03.08., Nutzer-Vorgabe) - einfaches window.prompt() statt
// eines eigenen Inline-Editors, analog zum bestehenden "+ Neu"-Fluss beim
// Binder-Anlegen (renderAddBinderPicker()), vorausgefüllt mit dem
// aktuellen Namen
async function renameBinderPrompt(binder) {
  const name = window.prompt(tr("New name for the binder:", "Neuer Name für den Binder:"), binder.name);
  if (!name || !name.trim() || name.trim() === binder.name) return;
  try {
    await fetch("/api/binders/rename", {
      method: "POST",
      headers: authHeaders(true),
      body: JSON.stringify({ id: binder.id, name: name.trim() })
    });
    await loadBindersForGame(activeGame);
    render();
  } catch (err) {
    showAddToast(tr("Rename failed", "Umbenennen fehlgeschlagen"));
  }
}

function renderBinderDetail(panel, binder, items) {
  const backRow = document.createElement("div");
  backRow.className = "wishlistBackRow";
  const backBtn = document.createElement("button");
  backBtn.className = "wishlistBackButton";
  backBtn.textContent = "← " + binder.name;
  backBtn.addEventListener("click", () => {
    openBinderId = null;
    selectedBinderItemIds = new Set();
    openBinderPage = 0;
    binderArrangeMode = false;
    binderSwapSourceId = null;
    binderRarityFilter = new Set();
    binderSearchQuery = "";
    render();
  });
  backRow.appendChild(backBtn);
  panel.appendChild(backRow);

  const actionRow = document.createElement("div");
  actionRow.className = "wishlistActionRow";
  actionRow.appendChild(buildMainActionButton({
    label: tr("Export", "Exportieren"),
    imageSrc: "images/btn_export.png",
    onClick: () => runBinderExport(binder, items)
  }));
  actionRow.appendChild(buildMainActionButton({
    label: selectedBinderItemIds.size > 0 ? (tr("Remove (", "Entfernen (") + selectedBinderItemIds.size + ")") : tr("Add", "Hinzufügen"),
    imageSrc: (GAME_BY_CODE[activeGame] || {}).image || GAMES[0].image,
    onClick: () => {
      if (selectedBinderItemIds.size > 0) {
        const ids = Array.from(selectedBinderItemIds);
        showConfirm(ids.length + " " + tr("card(s) will be removed from the binder - continue?", "Karte(n) wirklich aus dem Binder entfernen?"), async () => {
          try {
            await fetch("/api/binderItems/delete", {
              method: "POST",
              headers: authHeaders(true),
              body: JSON.stringify({ ids })
            });
          } catch (err) {
            showAddToast(tr("Removing failed", "Entfernen fehlgeschlagen"));
          }
          selectedBinderItemIds = new Set();
          delete binderItemsCache[activeGame];
          await loadBindersForGame(activeGame);
          render();
        });
      } else {
        openAddOverlay("binder", binder.id);
      }
    }
  }));
  panel.appendChild(actionRow);

  // Binder-Seiten (31.07., Nutzer-Vorgabe "den echten Binder 1:1 abbilden") -
  // feste Seitengröße (binder.pageSize), pageCount ergibt sich aus der
  // höchsten belegten Position (Lücken durch verschobene/gelöschte Karten
  // sind erlaubt), nicht aus der Kartenzahl.
  const pageSize = binder.pageSize || 9;
  const maxPosition = items.reduce((m, it) => Math.max(m, it.position || 0), -1);
  const pageCount = Math.max(1, Math.ceil((maxPosition + 1) / pageSize));
  if (openBinderPage >= pageCount) openBinderPage = pageCount - 1;
  if (openBinderPage < 0) openBinderPage = 0;

  const navRow = document.createElement("div");
  navRow.className = "binderPageNav";
  const navLeft = document.createElement("div");
  navLeft.className = "binderPageNavLeft";
  const prevBtn = document.createElement("button");
  prevBtn.className = "binderPageArrow";
  prevBtn.textContent = "‹";
  prevBtn.disabled = openBinderPage <= 0;
  prevBtn.addEventListener("click", () => goToBinderPage(binder, items, openBinderPage - 1, pageCount));
  const pageLabel = document.createElement("span");
  pageLabel.className = "binderPageLabel";
  pageLabel.textContent = tr("Page ", "Seite ") + (openBinderPage + 1) + "/" + pageCount;
  const nextBtn = document.createElement("button");
  nextBtn.className = "binderPageArrow";
  nextBtn.textContent = "›";
  nextBtn.disabled = openBinderPage >= pageCount - 1;
  nextBtn.addEventListener("click", () => goToBinderPage(binder, items, openBinderPage + 1, pageCount));
  navLeft.appendChild(prevBtn);
  navLeft.appendChild(pageLabel);
  navLeft.appendChild(nextBtn);
  navRow.appendChild(navLeft);

  if (selectedBinderItemIds.size === 0) {
    // Leere Seite einfügen (10.08., Feature-Parität App <-> Web) - fügt
    // direkt NACH der gerade angezeigten Seite ein, exakt wie in der App
    // (siehe onInsertPage()/showInsertPageConfirm in BinderScreen.kt)
    const insertPageBtn = document.createElement("button");
    insertPageBtn.className = "binderArrangeToggle";
    insertPageBtn.textContent = tr("+ Page", "+ Seite");
    insertPageBtn.addEventListener("click", () => {
      showConfirm(tr("Insert an empty page after page ", "Leere Seite nach Seite ") + (openBinderPage + 1) + tr("?", " einfügen?"), async () => {
        try {
          await fetch("/api/binderItems/insertPage", {
            method: "POST",
            headers: authHeaders(true),
            body: JSON.stringify({ binderId: binder.id, atPage: openBinderPage + 1, pageSize: binder.pageSize || 9 })
          });
        } catch (err) {
          showAddToast(tr("Insert failed", "Einfügen fehlgeschlagen"));
        }
        delete binderItemsCache[activeGame];
        await loadBindersForGame(activeGame);
        openBinderPage += 1;
        render();
      });
    });
    navRow.appendChild(insertPageBtn);
    const arrangeBtn = document.createElement("button");
    arrangeBtn.className = "binderArrangeToggle" + (binderArrangeMode ? " active" : "");
    arrangeBtn.textContent = tr("Arrange", "Anordnen");
    arrangeBtn.addEventListener("click", () => {
      binderArrangeMode = !binderArrangeMode;
      binderSwapSourceId = null;
      render();
    });
    navRow.appendChild(arrangeBtn);
  }
  panel.appendChild(navRow);

  if (binderArrangeMode) {
    const hint = document.createElement("p");
    hint.className = "binderArrangeHint";
    hint.textContent = binderSwapSourceId == null
      ? tr("Tap the card you want to move", "Karte antippen, die du verschieben willst")
      : tr("Now tap the target - a card to swap with or an empty slot", "Jetzt Ziel antippen - Karte zum Tauschen oder freien Platz");
    panel.appendChild(hint);
  }

  // Namenssuche (10.08., Feature-Parität) - siehe binderSearchQuery-Kommentar
  const searchInput = document.createElement("input");
  searchInput.type = "text";
  searchInput.id = "binderSearchInput";
  searchInput.className = "addSearchInput gridSearchInput";
  searchInput.placeholder = tr("Search cards…", "Karte suchen…");
  searchInput.autocomplete = "off";
  searchInput.value = binderSearchQuery;
  searchInput.addEventListener("input", () => {
    binderSearchQuery = searchInput.value;
    clearTimeout(renderBinderDetail._t);
    renderBinderDetail._t = setTimeout(() => {
      render();
      const el = document.getElementById("binderSearchInput");
      if (el) { el.focus(); el.setSelectionRange(el.value.length, el.value.length); }
    }, 200);
  });
  panel.appendChild(searchInput);

  // Raritäts-Filter (31.07., Nutzer-Vorgabe) - bewusst KEINE Sortierung,
  // siehe Kommentar bei binderRarityFilter. Raritäten aus ALLEN Karten des
  // Binders (nicht nur der aktuellen Seite), sonst verschwinden Chips beim
  // Blättern.
  const binderRarities = Array.from(new Set(items.map(it => it.rarity).filter(Boolean))).sort();
  if (binderRarities.length > 0) {
    const rarityRow = document.createElement("div");
    rarityRow.className = "gridSortFilterRow";
    binderRarities.forEach(r => {
      const chip = document.createElement("button");
      chip.className = "gridSortChip" + (binderRarityFilter.has(r) ? " active" : "");
      chip.textContent = r;
      chip.addEventListener("click", () => {
        if (binderRarityFilter.has(r)) binderRarityFilter.delete(r); else binderRarityFilter.add(r);
        render();
      });
      rarityRow.appendChild(chip);
    });
    panel.appendChild(rarityRow);
  }

  if (items.length === 0) {
    const empty = document.createElement("p");
    empty.className = "status";
    empty.style.display = "block";
    empty.textContent = tr("No cards in this binder yet.", "Noch keine Karten in diesem Binder.");
    panel.appendChild(empty);
    return;
  }

  const columns = pageSize <= 4 ? 2 : (pageSize >= 16 ? 4 : 3);
  const stage = document.createElement("div");
  stage.className = "binderFlipStage";
  const pageEl = buildBinderPageElement(binder, items, openBinderPage, columns);
  pageEl.classList.add("binderFlipPage");
  stage.appendChild(pageEl);
  panel.appendChild(stage);

  // Wischen zum Blättern (31.07.) - Pointer Events decken Maus UND Touch ab
  let dragStartX = null;
  let dragAccum = 0;
  stage.addEventListener("pointerdown", (e) => { dragStartX = e.clientX; dragAccum = 0; });
  stage.addEventListener("pointermove", (e) => { if (dragStartX != null) dragAccum = e.clientX - dragStartX; });
  const endDrag = () => {
    if (dragStartX == null) return;
    if (dragAccum < -60 && openBinderPage < pageCount - 1) goToBinderPage(binder, items, openBinderPage + 1, pageCount);
    else if (dragAccum > 60 && openBinderPage > 0) goToBinderPage(binder, items, openBinderPage - 1, pageCount);
    dragStartX = null;
    dragAccum = 0;
  };
  stage.addEventListener("pointerup", endDrag);
  stage.addEventListener("pointerleave", endDrag);
}

// Seiten-Umblätter-Animation (31.07., Nutzer-Vorgabe "können wir Binder
// Seiten animieren ... echtes 3D-Page-Curl"). Bewusst eine eigenständige
// Funktion, die NICHT über den normalen render()-Zyklus läuft (der würde das
// ganze Panel neu aufbauen und jede laufende CSS-Transition sofort
// abbrechen) - baut die neue Seite manuell dazu, animiert beide per CSS-
// Transition (siehe .binderFlipPage) und übergibt erst NACH Abschluss der
// Animation an den normalen render()-Zyklus (Endzustand ist visuell
// identisch, kein sichtbarer Sprung). Kein echtes Papier-Verformen (bräuchte
// Canvas-Mesh-Verzerrung) - klassischer Flip mit Perspektive, isoliert genug,
// um ihn bei Bedarf gegen eine einfachere Animation auszutauschen.
function goToBinderPage(binder, items, newPage, pageCount) {
  if (newPage === openBinderPage || newPage < 0 || newPage >= pageCount) return;
  const stage = document.querySelector(".binderFlipStage");
  if (!stage) {
    openBinderPage = newPage;
    render();
    return;
  }
  const pageSize = binder.pageSize || 9;
  const columns = pageSize <= 4 ? 2 : (pageSize >= 16 ? 4 : 3);
  const direction = newPage > openBinderPage ? 1 : -1;
  const oldPageEl = stage.querySelector(".binderFlipPage");
  const newPageEl = buildBinderPageElement(binder, items, newPage, columns);
  newPageEl.classList.add("binderFlipPage");

  // Nutzer-Fund (31.07.): Karten drehten sich in der Mitte statt zu
  // "blättern" (transform-origin fehlte, Default ist die Mitte) UND
  // überlappten sich stark (die Seite trug wegen position:absolute NIE zur
  // Höhe der Stage bei, siehe Kommentar bei .binderFlipStage in style.css).
  // Fix: Stage-Höhe kurz vorm Wechsel einfrieren (Inhalt bestimmt sie sonst
  // normal), beide Seiten NUR für die Dauer der Animation absolut
  // positionieren, Drehpunkt an den linken Kartenrand.
  const stageHeight = stage.getBoundingClientRect().height;
  stage.style.height = stageHeight + "px";
  if (oldPageEl) {
    oldPageEl.style.position = "absolute";
    oldPageEl.style.top = "0";
    oldPageEl.style.left = "0";
    oldPageEl.style.width = "100%";
    oldPageEl.style.transformOrigin = "left center";
  }
  newPageEl.style.position = "absolute";
  newPageEl.style.top = "0";
  newPageEl.style.left = "0";
  newPageEl.style.width = "100%";
  newPageEl.style.transformOrigin = "left center";
  newPageEl.style.transform = "rotateY(" + (90 * direction) + "deg)";
  newPageEl.style.opacity = "0";
  stage.appendChild(newPageEl);
  void newPageEl.offsetWidth; // Reflow erzwingen, damit die Transition unten wirklich startet
  requestAnimationFrame(() => {
    if (oldPageEl) {
      oldPageEl.style.transform = "rotateY(" + (-90 * direction) + "deg)";
      oldPageEl.style.opacity = "0";
    }
    newPageEl.style.transform = "rotateY(0deg)";
    newPageEl.style.opacity = "1";
  });
  setTimeout(() => {
    openBinderPage = newPage;
    render();
  }, 430);
}

function buildBinderPageElement(binder, items, page, columns) {
  const pageSize = binder.pageSize || 9;
  const itemsByPosition = {};
  items.forEach(it => { itemsByPosition[it.position] = it; });
  const wrap = document.createElement("div");
  wrap.className = "binderPageGrid";
  wrap.style.gridTemplateColumns = "repeat(" + columns + ", 1fr)";
  const base = page * pageSize;
  for (let i = 0; i < pageSize; i++) {
    wrap.appendChild(buildBinderSlotElement(itemsByPosition[base + i], base + i));
  }
  return wrap;
}

function buildBinderSlotElement(item, position) {
  if (!item) {
    const slot = document.createElement("div");
    slot.className = "binderSlot empty" + (binderArrangeMode && binderSwapSourceId != null ? " arrangeable" : "");
    if (binderArrangeMode && binderSwapSourceId != null) {
      slot.addEventListener("click", async () => {
        const sourceId = binderSwapSourceId;
        binderSwapSourceId = null;
        try {
          await fetch("/api/binderItems/move", {
            method: "POST",
            headers: authHeaders(true),
            body: JSON.stringify({ itemId: sourceId, newPosition: position })
          });
        } catch (err) {
          showAddToast(tr("Move failed", "Verschieben fehlgeschlagen"));
        }
        delete binderItemsCache[activeGame];
        await loadBindersForGame(activeGame);
        render();
      });
    }
    return slot;
  }

  const isSelected = selectedBinderItemIds.has(item.id);
  const isSwapSource = binderSwapSourceId === item.id;
  // Nicht passende Karten bleiben auf ihrem Platz (nur abgedunkelt), damit
  // die Seite bei aktivem Filter nicht umbricht - siehe binderRarityFilter.
  // binderSearchQuery (10.08., Feature-Parität) funktioniert nach demselben
  // Prinzip - eine Namenssuche darf die Positionen im echten Binder ja nicht
  // durcheinanderwürfeln, nur zeigen, welche Plätze NICHT passen.
  const searchMiss = binderSearchQuery.trim() !== "" &&
    !(item.name || "").toLowerCase().includes(binderSearchQuery.trim().toLowerCase());
  const filteredOut = (binderRarityFilter.size > 0 && !binderRarityFilter.has(item.rarity)) || searchMiss;
  const card = document.createElement("div");
  card.className = "card selectable binderSlot" +
    (isSelected ? " selected" : "") + (isSwapSource ? " swapSource" : "") + (filteredOut ? " missing" : "");

  const art = document.createElement("div");
  if (item.imageUrl) {
    art.className = "art";
    const img = document.createElement("img");
    img.src = item.imageUrl;
    img.loading = "lazy";
    img.alt = item.name;
    art.appendChild(img);
  } else {
    art.className = "art placeholder";
    art.textContent = tr("No image", "Kein Bild");
  }
  card.appendChild(art);

  // "Ein Fach nach vorn schieben" (28.08., Parität zum Doppel-Tipp in der
  // App): Doppelklick im Einsortieren-Modus schiebt diese und alle
  // folgenden Karten um ein Fach weiter - es entsteht eine Lücke
  card.addEventListener("dblclick", async () => {
    if (!binderArrangeMode) return;
    binderSwapSourceId = null;
    try {
      await fetch("/api/binderItems/shiftForward", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ binderId: item.binderId, atPosition: position })
      });
    } catch (err) {
      showAddToast(tr("Shifting failed", "Verschieben fehlgeschlagen"));
    }
    delete binderItemsCache[activeGame];
    await loadBindersForGame(activeGame);
    render();
  });

  card.addEventListener("click", async () => {
    if (binderArrangeMode) {
      if (binderSwapSourceId == null) {
        binderSwapSourceId = item.id;
        render();
      } else if (binderSwapSourceId === item.id) {
        binderSwapSourceId = null;
        render();
      } else {
        const sourceId = binderSwapSourceId;
        binderSwapSourceId = null;
        try {
          await fetch("/api/binderItems/swap", {
            method: "POST",
            headers: authHeaders(true),
            body: JSON.stringify({ itemId1: sourceId, itemId2: item.id })
          });
        } catch (err) {
          showAddToast(tr("Swap failed", "Tauschen fehlgeschlagen"));
        }
        delete binderItemsCache[activeGame];
        await loadBindersForGame(activeGame);
        render();
      }
    } else {
      if (selectedBinderItemIds.has(item.id)) {
        selectedBinderItemIds.delete(item.id);
      } else {
        selectedBinderItemIds.add(item.id);
      }
      render();
    }
  });
  return card;
}

function runBinderExport(binder, items) {
  const lines = items.map(item => {
    const set = (setsCache[activeGame] || []).find(s => s.id === item.setId);
    return "1x " + item.name + " (" + (set ? set.name : "?") + ")";
  });
  const safeName = binder.name.replace(/[^A-Za-z0-9]+/g, "_").replace(/^_+|_+$/g, "");
  downloadTextFile(safeName + "_binder.txt", lines.join("\n"));
}

// Inhalt eines aufgeklappten Set-Eintrags: Haupt-Export-Button, die drei
// Filter-Buttons + "Auswählen", dann das Grid. "Gesammelte Karten" selbst
// braucht keinen Katalog fürs Anzeigen, aber der Export/die Auswahl schon
// (der volle Kartenkatalog des Sets wird deshalb hier IMMER angefordert,
// sobald ein Set aufklappt, nicht erst bei "Ganzes Set"/"Fehlende") - wird
// bei Bedarf einmalig nachgeladen und dann gecacht.
function renderOpenSetBody(body, set, ownedForGame) {
  const stats = document.getElementById("stats");
  const ownedInSet = ownedForGame.filter(item => item.setId === set.id);
  const catalog = catalogCache[set.id];
  if (!catalog) loadCatalogForSet(set.id);

  body.appendChild(buildMainActionButton({
    label: tr("Export", "Exportieren"),
    imageSrc: "images/btn_export.png",
    disabled: !catalog,
    onClick: () => runSetExport(set, catalog, ownedInSet)
  }));
  // Zusatz-Aktionen (28.08., Parität zum App-Export-Dialog): Dubletten +
  // "Fehlende in Wantsliste übernehmen" - als schlanke Chips unterm Export
  const extraExportRow = document.createElement("div");
  extraExportRow.className = "gridSortFilterRow";
  const dupBtn = document.createElement("button");
  dupBtn.className = "gridSortChip";
  dupBtn.textContent = tr("Export duplicates", "Dubletten exportieren");
  dupBtn.disabled = !catalog;
  dupBtn.addEventListener("click", () => runSetExport(set, catalog, ownedInSet, "duplicates"));
  extraExportRow.appendChild(dupBtn);
  const missingWlBtn = document.createElement("button");
  missingWlBtn.className = "gridSortChip";
  missingWlBtn.textContent = tr("Missing → want list", "Fehlende → Wantsliste");
  missingWlBtn.disabled = !catalog;
  missingWlBtn.addEventListener("click", () => addMissingToWishlist(set, catalog, ownedInSet));
  extraExportRow.appendChild(missingWlBtn);
  body.appendChild(extraExportRow);

  const filters = document.createElement("div");
  filters.className = "setFilters";
  const filterDefs = [
    { key: "owned", label: tr("Collected cards", "Gesammelte Karten") },
    { key: "full", label: tr("Full set", "Ganzes Set") },
    { key: "missing", label: tr("Missing", "Fehlende") }
  ];
  filterDefs.forEach(f => {
    const btn = document.createElement("button");
    btn.className = "setFilter" + (setFilter === f.key ? " active" : "");
    btn.textContent = f.label;
    btn.addEventListener("click", () => {
      setFilter = f.key;
      resetSelection();
      render();
    });
    filters.appendChild(btn);
  });
  const selectBtn = document.createElement("button");
  selectBtn.className = "setFilter" + (selectionMode ? " active" : "");
  selectBtn.textContent = selectionMode ? tr("End selection (", "Auswahl beenden (") + selectedCardIds.size + ")" : tr("Select", "Auswählen");
  selectBtn.addEventListener("click", () => {
    selectionMode = !selectionMode;
    if (!selectionMode) selectedCardIds = new Set();
    render();
  });
  filters.appendChild(selectBtn);
  body.appendChild(filters);

  // Sortierung + Raritäts-Filter (31.07.) - Raritäten für die Chip-Reihe
  // kommen aus dem jeweils passenden UNGEFILTERTEN Kartenpool je setFilter-
  // Modus, sonst würde z.B. beim Wechsel auf "Fehlende" plötzlich eine
  // Rarität fehlen, die nur besessene Karten haben
  const rarityPool = setFilter === "owned" ? ownedInSet : (catalog || []);
  body.appendChild(buildGridSearchInput());
  body.appendChild(buildSortFilterRow(rarityPool));

  const grid = document.createElement("div");
  grid.className = "grid";
  body.appendChild(grid);

  if (setFilter === "owned") {
    const displayed = applySortAndFilter(ownedInSet);
    renderGridInto(grid, displayed, { selectable: selectionMode });
    stats.textContent = displayed.length + " " + tr("entries", "Einträge");
    return;
  }

  if (!catalog) {
    grid.innerHTML = "<p class=\"status\" style=\"display:block\">" + tr("Loading card list…", "Lade Kartenliste…") + "</p>";
    stats.textContent = "";
    return;
  }

  const ownedByCardId = new Map(ownedInSet.map(item => [item.cardId, item]));
  if (setFilter === "full") {
    const items = catalog.map(c => ownedByCardId.get(c.id) || {
      cardId: c.id, name: c.name, imageUrl: c.imageUrl, marketPriceEur: c.marketPriceEur,
      quantity: 0, isHolo: false, game: activeGame, rarity: c.rarity
    });
    const missingIds = new Set(catalog.filter(c => !ownedByCardId.has(c.id)).map(c => c.id));
    const displayed = applySortAndFilter(items);
    grid.innerHTML = "";
    for (const it of displayed) {
      grid.appendChild(buildCardTile(it, { missing: missingIds.has(it.cardId), selectable: selectionMode }));
    }
    stats.textContent = ownedInSet.length + "/" + catalog.length + " " + tr("cards", "Karten");
  } else {
    // missing
    const missing = catalog.filter(c => !ownedByCardId.has(c.id)).map(c => ({
      cardId: c.id, name: c.name, imageUrl: c.imageUrl, marketPriceEur: c.marketPriceEur,
      quantity: 0, isHolo: false, game: activeGame, rarity: c.rarity
    }));
    const displayed = applySortAndFilter(missing);
    renderGridInto(grid, displayed, { missing: true, selectable: selectionMode });
    stats.textContent = displayed.length + " " + tr("missing cards", "fehlende Karten");
  }
}

// Runder Haupt-Button mit Dampf + gelegentlichen Funken (26.07., Nutzer-
// Vorgabe) - exportiert im Set-Kontext die Wantsliste, wird später im
// "Alle"-Eintrag zum Karten-Hinzufügen-Button (gleiche Optik, andere
// Funktion je nach Kontext).
function buildMainActionButton(opts) {
  const row = document.createElement("div");
  row.className = "mainActionRow";

  const btn = document.createElement("button");
  btn.className = "mainActionButton";
  btn.disabled = !!opts.disabled;
  btn.innerHTML =
    "<span class=\"actionTabAura a1\"></span><span class=\"actionTabAura a2\"></span><span class=\"actionTabAura a3\"></span>" +
    "<span class=\"mainActionSpark s1\"></span><span class=\"mainActionSpark s2\"></span><span class=\"mainActionSpark s3\"></span><span class=\"mainActionSpark s4\"></span><span class=\"mainActionSpark s5\"></span>" +
    "<span class=\"mainActionImgWrap\"><img src=\"" + opts.imageSrc + "\" alt=\"\"></span>";
  btn.addEventListener("click", opts.onClick);
  row.appendChild(btn);

  const label = document.createElement("div");
  label.className = "mainActionLabel";
  label.textContent = opts.disabled ? (opts.disabledLabel || tr("Loading…", "Lädt…")) : opts.label;
  row.appendChild(label);

  return row;
}

// Baut die Cardmarket-kompatible Wantslisten-Datei (ein Eintrag pro Zeile,
// "<Anzahl>x <Name> (<Set>)") - exakt derselbe Aufbau wie
// buildCardmarketExportText() in der App (App.kt), nur als JS-Nachbau.
// Ohne Auswahl richtet sich der Modus nach dem aktiven Filter (Gesammelte
// Karten/Ganzes Set/Fehlende), mit Auswahl werden nur die markierten Karten
// exportiert (immer mit echter Anzahl bzw. 1, wenn nicht besessen).
// Varianten lesbar im Namen (28.08., Parität zu displayName() in App.kt) -
// "Alternate Art"-Karten heißen im Export "Name (Variante)", damit sich
// gleichnamige Varianten in der Cardmarket-Wantsliste nicht vermischen
function catalogDisplayName(c) {
  return (!c.variant || c.variant === "Normal") ? c.name : c.name + " (" + c.variant + ")";
}

// modeOverride "duplicates" (28.08., Parität zum App-Export-Dialog):
// exportiert je Karte die überzähligen Exemplare (Anzahl - 1)
function runSetExport(set, catalog, ownedInSet, modeOverride) {
  if (!catalog) return;
  const ownedByCardId = new Map(ownedInSet.map(item => [item.cardId, item]));
  // Sortierung wie die App: (Nummer, Variante) - Varianten sonst zufällig
  const sorted = [...catalog].sort((a, b) =>
    a.number.localeCompare(b.number) || (a.variant || "").localeCompare(b.variant || ""));
  const lines = [];

  for (const c of sorted) {
    if (!modeOverride && selectionMode && selectedCardIds.size > 0 && !selectedCardIds.has(c.id)) continue;
    const owned = ownedByCardId.get(c.id);
    const quantity = owned ? owned.quantity : 0;
    const name = catalogDisplayName(c);
    if (modeOverride === "duplicates") {
      if (quantity > 1) lines.push((quantity - 1) + "x " + name + " (" + set.name + ")");
      continue;
    }
    if (selectionMode && selectedCardIds.size > 0) {
      const exportQuantity = quantity > 0 ? quantity : 1;
      lines.push(exportQuantity + "x " + name + " (" + set.name + ")");
      continue;
    }
    if (setFilter === "owned" && quantity > 0) {
      lines.push(quantity + "x " + name + " (" + set.name + ")");
    } else if (setFilter === "missing" && quantity <= 0) {
      lines.push("1x " + name + " (" + set.name + ")");
    } else if (setFilter === "full") {
      const exportQuantity = quantity > 0 ? quantity : 1;
      lines.push(exportQuantity + "x " + name + " (" + set.name + ")");
    }
  }

  const safeSetName = set.name.replace(/[^A-Za-z0-9]+/g, "_").replace(/^_+|_+$/g, "");
  const suffix = modeOverride === "duplicates" ? tr("duplicates", "dubletten")
    : (selectionMode && selectedCardIds.size > 0 ? tr("selection", "auswahl") : setFilter);
  downloadTextFile(safeSetName + "_" + suffix + ".txt", lines.join("\n"));
}

// "Fehlende in Wantsliste übernehmen" (28.08., Parität zur App vom 18.08.):
// legt eine Wantsliste mit dem Set-Namen an (bzw. nutzt eine vorhandene
// gleichen Namens) und trägt alle fehlenden Karten ein - schon enthaltene
// überspringt der Server (addWishlistItems ist insert-if-missing)
async function addMissingToWishlist(set, catalog, ownedInSet) {
  if (!catalog) return;
  const ownedIds = new Set(ownedInSet.map(item => item.cardId).filter(Boolean));
  const missing = catalog.filter(c => !ownedIds.has(c.id));
  if (missing.length === 0) {
    showAddToast(tr("No missing cards - the set is complete!", "Keine fehlenden Karten - das Set ist komplett!"));
    return;
  }
  try {
    let target = (wishlistsCache[activeGame] || []).find(w => w.name === set.name);
    if (!target) {
      const res = await fetch("/api/wishlists", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ name: set.name, game: activeGame })
      });
      if (!res.ok) throw new Error("create failed");
      target = await res.json();
    }
    await fetch("/api/wishlistItems/add", {
      method: "POST",
      headers: authHeaders(true),
      body: JSON.stringify({
        wishlistId: target.id,
        cards: missing.map(c => ({ cardId: c.id, name: catalogDisplayName(c), imageUrl: c.imageUrl || null }))
      })
    });
    delete wishlistItemsCache[activeGame];
    await loadWishlistsForGame(activeGame);
    showAddToast("✓ " + missing.length + " " + tr("card(s) added to \"", "Karte(n) übernommen in \"") + set.name + "\"");
  } catch (err) {
    showAddToast(tr("Adding failed", "Übernehmen fehlgeschlagen"));
  }
}

function downloadTextFile(filename, text) {
  const blob = new Blob([text], { type: "text/plain;charset=utf-8" });
  const url = URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = filename;
  document.body.appendChild(a);
  a.click();
  a.remove();
  URL.revokeObjectURL(url);
}

// Decks (10.08., Nutzer-Vorgabe "Feature-Parität App <-> Web") - die Server-
// API dafür (siehe /api/decks* in Main.kt) existierte schon seit 02.08., nur
// die Weboberfläche hatte noch keinen eigenen Tab. Struktur bewusst analog
// zum Binder-Panel oben (Übersicht/Detail-Dispatcher, dieselben
// .wishlistRow/.wishlistList-Klassen), aber ohne Seiten - Decks sind eine
// einfache Kartenliste mit Anzahl statt eines Positionsrasters. deckCards
// werden anders als binderItems NICHT spielweit vorgeladen (kein
// /api/deckCards?game=-Endpunkt), sondern erst beim Öffnen eines einzelnen
// Decks per deckId nachgeladen.
let decksCache = {}; // game -> Liste (DeckResponse)
let deckCardsCache = {}; // deckId -> Liste (DeckCardResponse)
let openDeckId = null;
let deckListSelectionMode = false;
let selectedDeckIds = new Set();
let selectedDeckCardIds = new Set();
let deckValidation = null; // zuletzt abgerufenes DeckValidationResponse fürs offene Deck

async function loadDecksForGame(game) {
  try {
    const res = await authedFetch("/api/decks?game=" + encodeURIComponent(game));
    if (res.ok) decksCache[game] = await res.json();
    if (activeTab === "decks" && activeGame === game) render();
  } catch (err) {
    // Decks sind ein Zusatzfeature - bei Fehlern bleibt der Rest der Seite nutzbar
  }
}

async function loadDeckCards(deckId) {
  try {
    const res = await authedFetch("/api/deckCards?deckId=" + deckId);
    if (res.ok) deckCardsCache[deckId] = await res.json();
  } catch (err) {
    // s.u.
  }
  if (openDeckId === deckId) render();
}

async function createDeck(name) {
  try {
    await fetch("/api/decks", {
      method: "POST",
      headers: authHeaders(true),
      body: JSON.stringify({ name, game: activeGame })
    });
    delete decksCache[activeGame];
    await loadDecksForGame(activeGame);
    render();
  } catch (err) {
    showAddToast(tr("Create failed", "Erstellen fehlgeschlagen"));
  }
}

function openCreateDeckDialog() {
  document.getElementById("createDeckName").value = "";
  document.getElementById("createDeckOverlay").classList.add("visible");
  setTimeout(() => document.getElementById("createDeckName").focus(), 50);
}

function closeCreateDeckDialog() {
  document.getElementById("createDeckOverlay").classList.remove("visible");
}

function renderDeckTab() {
  const panel = document.getElementById("deckPanel");
  panel.innerHTML = "";
  const stats = document.getElementById("stats");
  const decks = decksCache[activeGame];
  if (!decks) {
    loadDecksForGame(activeGame);
    const loading = document.createElement("p");
    loading.className = "status";
    loading.style.display = "block";
    loading.textContent = tr("Loading…", "Lädt…");
    panel.appendChild(loading);
    return;
  }
  if (openDeckId != null && !decks.find(d => d.id === openDeckId)) {
    openDeckId = null;
  }
  const openDeck = decks.find(d => d.id === openDeckId);
  if (!openDeck) {
    renderDeckOverview(panel, decks);
  } else {
    renderDeckDetail(panel, openDeck);
  }
  stats.textContent = "";
}

function renderDeckOverview(panel, decks) {
  const statRow = document.createElement("div");
  statRow.className = "wishlistStatRow";
  statRow.textContent = "Decks " + decks.length;
  panel.appendChild(statRow);

  panel.appendChild(buildMainActionButton({
    label: tr("Create deck", "Deck erstellen"),
    imageSrc: (GAME_BY_CODE[activeGame] || {}).image || GAMES[0].image,
    onClick: openCreateDeckDialog
  }));

  if (decks.length === 0) {
    const empty = document.createElement("p");
    empty.className = "status";
    empty.style.display = "block";
    empty.textContent = tr("No decks yet.", "Noch keine Decks.");
    panel.appendChild(empty);
    return;
  }

  const filters = document.createElement("div");
  filters.className = "setFilters";
  const selectBtn = document.createElement("button");
  selectBtn.className = "setFilter" + (deckListSelectionMode ? " active" : "");
  selectBtn.textContent = deckListSelectionMode
    ? tr("End selection (", "Auswahl beenden (") + selectedDeckIds.size + ")"
    : tr("Select", "Auswählen");
  selectBtn.addEventListener("click", () => {
    deckListSelectionMode = !deckListSelectionMode;
    if (!deckListSelectionMode) selectedDeckIds = new Set();
    render();
  });
  filters.appendChild(selectBtn);
  if (deckListSelectionMode && selectedDeckIds.size > 0) {
    const delBtn = document.createElement("button");
    delBtn.className = "setFilter";
    delBtn.style.borderColor = "#E57373";
    delBtn.style.color = "#E57373";
    delBtn.textContent = tr("Delete (", "Löschen (") + selectedDeckIds.size + ")";
    delBtn.addEventListener("click", () => {
      const ids = Array.from(selectedDeckIds);
      showConfirm(ids.length + " " + tr("deck(s) will be deleted - continue?", "Deck(s) wirklich löschen?"), async () => {
        try {
          await fetch("/api/decks/delete", {
            method: "POST",
            headers: authHeaders(true),
            body: JSON.stringify({ ids })
          });
        } catch (err) {
          showAddToast(tr("Delete failed", "Löschen fehlgeschlagen"));
        }
        selectedDeckIds = new Set();
        deckListSelectionMode = false;
        delete decksCache[activeGame];
        await loadDecksForGame(activeGame);
        render();
      });
    });
    filters.appendChild(delBtn);
  }
  panel.appendChild(filters);

  const list = document.createElement("div");
  list.className = "wishlistList";
  decks.forEach(d => {
    const row = document.createElement("div");
    const isSelected = deckListSelectionMode && selectedDeckIds.has(d.id);
    row.className = "wishlistRow" + (isSelected ? " selected" : "");
    const nameSpan = document.createElement("span");
    nameSpan.textContent = d.name;
    row.appendChild(nameSpan);
    const countSpan = document.createElement("span");
    countSpan.className = "wishlistRowCount";
    countSpan.textContent = d.cardCount + " " + tr("card(s)", "Karte(n)");
    row.appendChild(countSpan);
    row.addEventListener("click", () => {
      if (deckListSelectionMode) {
        if (selectedDeckIds.has(d.id)) {
          selectedDeckIds.delete(d.id);
        } else {
          selectedDeckIds.add(d.id);
        }
      } else {
        openDeckId = d.id;
        selectedDeckCardIds = new Set();
        deckValidation = null;
        loadDeckCards(d.id);
      }
      render();
    });
    list.appendChild(row);
  });
  panel.appendChild(list);
}

function renderDeckDetail(panel, deck) {
  const backRow = document.createElement("div");
  backRow.className = "wishlistBackRow";
  const backBtn = document.createElement("button");
  backBtn.className = "wishlistBackButton";
  backBtn.textContent = "← " + deck.name;
  backBtn.addEventListener("click", () => {
    openDeckId = null;
    selectedDeckCardIds = new Set();
    deckValidation = null;
    render();
  });
  backRow.appendChild(backBtn);
  panel.appendChild(backRow);

  const actionRow = document.createElement("div");
  actionRow.className = "wishlistActionRow";
  actionRow.appendChild(buildMainActionButton({
    label: tr("Rules check", "Regelcheck"),
    imageSrc: "images/coin_front.png",
    onClick: () => runDeckValidation(deck)
  }));
  actionRow.appendChild(buildMainActionButton({
    label: selectedDeckCardIds.size > 0 ? (tr("Remove (", "Entfernen (") + selectedDeckCardIds.size + ")") : tr("Add", "Hinzufügen"),
    imageSrc: (GAME_BY_CODE[activeGame] || {}).image || GAMES[0].image,
    onClick: () => {
      if (selectedDeckCardIds.size > 0) {
        const ids = Array.from(selectedDeckCardIds);
        showConfirm(ids.length + " " + tr("card(s) will be removed from the deck - continue?", "Karte(n) wirklich aus dem Deck entfernen?"), async () => {
          for (const id of ids) {
            try {
              await fetch("/api/deckCards/setQuantity", {
                method: "POST",
                headers: authHeaders(true),
                body: JSON.stringify({ deckCardId: id, quantity: 0 })
              });
            } catch (err) {
              // einzelner Fehlschlag stoppt nicht den Rest
            }
          }
          selectedDeckCardIds = new Set();
          await loadDeckCards(deck.id);
          delete decksCache[activeGame];
          await loadDecksForGame(deck.game);
          render();
        });
      } else {
        openAddOverlay("deck", deck.id);
      }
    }
  }));
  panel.appendChild(actionRow);

  if (deckValidation) {
    const box = document.createElement("div");
    box.className = "wishlistStatRow";
    box.style.display = "block";
    if (!deckValidation.available) {
      box.textContent = tr("There is no rule set for this game yet.", "Für dieses Spiel gibt es noch kein Regelwerk.");
    } else if (deckValidation.violations.length === 0) {
      box.textContent = tr("Rules check: all good", "Regelcheck: alles ok");
    } else {
      const title = document.createElement("div");
      title.textContent = tr("Rules check: ", "Regelcheck: ") + deckValidation.violations.length + " " + tr("issue(s)", "Hinweis(e)");
      box.appendChild(title);
      const ul = document.createElement("ul");
      ul.style.margin = "6px 0 0 18px";
      deckValidation.violations.forEach(v => {
        const li = document.createElement("li");
        li.textContent = v;
        ul.appendChild(li);
      });
      box.appendChild(ul);
    }
    if (deckValidation.available && deckValidation.cardsWithoutRuleData > 0) {
      const note = document.createElement("div");
      note.style.opacity = "0.7";
      note.style.marginTop = "4px";
      note.textContent = deckValidation.cardsWithoutRuleData + " " + tr("card(s) without rule data - not checked.", "Karte(n) ohne Regeldaten - werden nicht geprüft.");
      box.appendChild(note);
    }
    panel.appendChild(box);
  }

  const cards = deckCardsCache[deck.id];
  if (!cards) {
    const loading = document.createElement("p");
    loading.className = "status";
    loading.style.display = "block";
    loading.textContent = tr("Loading…", "Lädt…");
    panel.appendChild(loading);
    return;
  }
  if (cards.length === 0) {
    const empty = document.createElement("p");
    empty.className = "status";
    empty.style.display = "block";
    empty.textContent = tr("No cards in this deck yet.", "Noch keine Karten in diesem Deck.");
    panel.appendChild(empty);
    return;
  }

  const list = document.createElement("div");
  list.className = "wishlistList";
  cards.forEach(c => {
    const row = document.createElement("div");
    const isSelected = selectedDeckCardIds.has(c.id);
    row.className = "wishlistRow" + (isSelected ? " selected" : "");
    const nameSpan = document.createElement("span");
    nameSpan.textContent = c.name;
    row.appendChild(nameSpan);

    const qtyControls = document.createElement("span");
    qtyControls.className = "detailStepper";
    const minusBtn = document.createElement("button");
    minusBtn.className = "detailStepperBtn";
    minusBtn.textContent = "–";
    minusBtn.addEventListener("click", async (e) => {
      e.stopPropagation();
      const newQty = c.quantity - 1;
      await fetch("/api/deckCards/setQuantity", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ deckCardId: c.id, quantity: newQty })
      });
      delete decksCache[activeGame];
      await Promise.all([loadDeckCards(deck.id), loadDecksForGame(deck.game)]);
      render();
    });
    qtyControls.appendChild(minusBtn);
    const qtyValue = document.createElement("span");
    qtyValue.className = "value";
    qtyValue.textContent = "×" + c.quantity;
    qtyControls.appendChild(qtyValue);
    const plusBtn = document.createElement("button");
    plusBtn.className = "detailStepperBtn";
    plusBtn.textContent = "+";
    plusBtn.addEventListener("click", async (e) => {
      e.stopPropagation();
      await fetch("/api/deckCards/setQuantity", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ deckCardId: c.id, quantity: c.quantity + 1 })
      });
      delete decksCache[activeGame];
      await Promise.all([loadDeckCards(deck.id), loadDecksForGame(deck.game)]);
      render();
    });
    qtyControls.appendChild(plusBtn);
    row.appendChild(qtyControls);

    row.addEventListener("click", () => {
      if (selectedDeckCardIds.has(c.id)) {
        selectedDeckCardIds.delete(c.id);
      } else {
        selectedDeckCardIds.add(c.id);
      }
      render();
    });
    list.appendChild(row);
  });
  panel.appendChild(list);
}

async function runDeckValidation(deck) {
  try {
    const res = await authedFetch("/api/decks/validate?deckId=" + deck.id + "&game=" + encodeURIComponent(deck.game));
    if (res.ok) deckValidation = await res.json();
  } catch (err) {
    showAddToast(tr("Rules check failed", "Regelcheck fehlgeschlagen"));
  }
  render();
}

// "Karte"/"Vault-Produkt hinzufügen" (26.07., erweitert 27.07. auf Nutzer-
// Vorgabe: "nicht nur die Suche, sondern direkt darunter auch Kategorien/
// Sets zum Anklicken, und da wir in der Weboberfläche sind kann man ruhig
// von vorn herein mehrere Dinge auswählen"). Statt Klick = sofort
// hinzufügen jetzt Klick = Auswahl an-/abwählen (analog zur Auswahl beim
// Wantslisten-Export), ein Haupt-Button oben im bewährten Stil (Dampf +
// Funken) fügt am Ende alle ausgewählten Einträge in einem Rutsch hinzu.
// Schreibt weiterhin direkt in die Server-DB (POST, kein Umweg über den
// Sync-Mechanismus - der Server ist hier selbst die Quelle der Änderung,
// siehe Main.kt-Kommentar bei /api/collection/add).
let addOverlayKind = null; // "cards" | "vault" | "wishlist" | "binder" | "deck"
let addSelected = new Map(); // key -> Payload fürs POST beim Hinzufügen
let addGroupId = null; // ausgewähltes Set (Karten/Wishlist/Binder) bzw. Kategorie (Vault)
let addWishlistTargetId = null; // Ziel-Liste, nur bei kind === "wishlist"
// Sealed-Wantslisten (28.08.) - Ziel-Liste, nur bei kind === "sealedWishlist"
let addSealedWishlistTargetId = null;

// "vault" und "sealedWishlist" durchstöbern beide den SEALED-Katalog
// (Kategorien statt Sets) - alle Verzweigungen im Add-Overlay, die bisher
// auf === "vault" prüften, meinen in Wahrheit diese Katalog-Art
function addKindUsesSealedCatalog() {
  return addOverlayKind === "vault" || addOverlayKind === "sealedWishlist";
}
// Ziel-Binder, nur bei kind === "binder" (direktes Hinzufügen vorhandener
// Katalogkarten zu einem offenen Binder, siehe renderBinderDetail()) -
// bewusst getrennt von addBinderTargetId (das ist die optionale
// Binder-ZUORDNUNG beim normalen "Karte hinzufügen"-Fluss, kind === "cards")
let addBinderOverlayTargetId = null;
let addDeckTargetId = null; // Ziel-Deck, nur bei kind === "deck" (10.08.)
let sealedCatalogCache = {}; // game -> Liste, fürs Kategorien-Browsing & Filtern beim Vault-Hinzufügen
let addSearchDebounce = null;

// wishlistId/binderId nur bei kind === "wishlist"/"binder" relevant -
// dieselbe Set-Browsing-Suche wie beim normalen Karten-Hinzufügen (27.07.),
// schreibt aber am Ende in eine Wunschliste/einen Binder statt in die
// Sammlung
function openAddOverlay(kind, listId) {
  addOverlayKind = kind;
  addSelected = new Map();
  addGroupId = null;
  addWishlistTargetId = kind === "wishlist" ? (listId || null) : null;
  addBinderOverlayTargetId = kind === "binder" ? (listId || null) : null;
  addDeckTargetId = kind === "deck" ? (listId || null) : null;
  addSealedWishlistTargetId = kind === "sealedWishlist" ? (listId || null) : null;
  // Binder-Auswahl beim "Karte hinzufügen"-Fluss zurücksetzen (31.07.) - eine
  // vorherige Sitzung darf keinen stillen Binder aus einem früheren Öffnen
  // übernehmen
  addBinderTargetId = null;
  const overlay = document.getElementById("addOverlay");
  const title = document.getElementById("addTitle");
  const input = document.getElementById("addSearchInput");
  title.textContent = kind === "cards" ? tr("Add card", "Karte hinzufügen")
    : kind === "wishlist" ? tr("Add card to wishlist", "Karte zur Wunschliste hinzufügen")
    : kind === "binder" ? tr("Add card to binder", "Karte zum Binder hinzufügen")
    : kind === "deck" ? tr("Add card to deck", "Karte zum Deck hinzufügen")
    : kind === "sealedWishlist" ? tr("Add product to want list", "Produkt zur Wantsliste hinzufügen")
    : tr("Add vault product", "Vault-Produkt hinzufügen");
  input.value = "";
  overlay.classList.add("visible");
  renderAddActionButton();
  renderAddGroups();
  renderAddContent();
  if (kind === "cards") {
    if (!bindersCache[activeGame]) {
      loadBindersForGame(activeGame).then(renderAddBinderPicker);
    } else {
      renderAddBinderPicker();
    }
  } else {
    document.getElementById("addBinderPicker").classList.remove("visible");
  }
  setTimeout(() => input.focus(), 50);
}

function closeAddOverlay() {
  document.getElementById("addOverlay").classList.remove("visible");
  addOverlayKind = null;
  addSelected = new Map();
  addGroupId = null;
  addWishlistTargetId = null;
  addBinderOverlayTargetId = null;
  addBinderTargetId = null;
  addDeckTargetId = null;
  addSealedWishlistTargetId = null;
}

function showAddToast(text) {
  const toast = document.getElementById("addToast");
  toast.textContent = text;
  toast.classList.add("visible");
  clearTimeout(showAddToast._t);
  showAddToast._t = setTimeout(() => toast.classList.remove("visible"), 1800);
}

// Haupt-Button oben im Overlay ("oben drüber", Nutzer-Vorgabe) - zeigt die
// Anzahl der Auswahl im Label und ist erst aktiv, sobald mindestens ein
// Eintrag ausgewählt wurde.
function renderAddActionButton() {
  const slot = document.getElementById("addActionSlot");
  slot.innerHTML = "";
  const count = addSelected.size;
  slot.appendChild(buildMainActionButton({
    label: count > 0 ? (tr("Add (", "Hinzufügen (") + count + ")") : tr("Add", "Hinzufügen"),
    disabledLabel: tr("Add", "Hinzufügen"),
    imageSrc: (GAME_BY_CODE[activeGame] || {}).image || GAMES[0].image,
    disabled: count === 0,
    onClick: runAddBatch
  }));
}

// Binder-Auswahl beim normalen "Karte hinzufügen"-Fluss (31.07., Nutzer-
// Vorgabe "beim Hinzufügen-Button gleich noch einen Binder auswählen können
// ... oder man ignoriert es einfach") - rein optional, nur bei kind ===
// "cards" sichtbar und nur, wenn für das aktuelle TCG mindestens ein Binder
// existiert. "+ Neu" legt direkt hier einen neuen Binder an und wählt ihn
// sofort aus, ohne den Hinzufügen-Fluss zu verlassen.
function renderAddBinderPicker() {
  const wrap = document.getElementById("addBinderPicker");
  wrap.innerHTML = "";
  const binders = bindersCache[activeGame];
  if (addOverlayKind !== "cards" || !binders || binders.length === 0) {
    wrap.classList.remove("visible");
    return;
  }
  wrap.classList.add("visible");

  const label = document.createElement("span");
  label.className = "label";
  label.textContent = "Binder:";
  wrap.appendChild(label);

  const makeChip = (text, active, onClick) => {
    const chip = document.createElement("button");
    chip.className = "addBinderChip" + (active ? " active" : "");
    chip.textContent = text;
    chip.addEventListener("click", onClick);
    wrap.appendChild(chip);
  };

  makeChip(tr("No binder", "Kein Binder"), addBinderTargetId === null, () => {
    addBinderTargetId = null;
    renderAddBinderPicker();
  });
  binders.forEach(b => {
    makeChip(b.name, addBinderTargetId === b.id, () => {
      addBinderTargetId = b.id;
      renderAddBinderPicker();
    });
  });
  makeChip(tr("+ New", "+ Neu"), false, async () => {
    const name = window.prompt(tr("Name of the new binder:", "Name des neuen Binders:"));
    if (!name || !name.trim()) return;
    try {
      const res = await fetch("/api/binders", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ name: name.trim(), game: activeGame })
      });
      if (res.ok) {
        const created = await res.json();
        delete bindersCache[activeGame];
        delete binderItemsCache[activeGame];
        await loadBindersForGame(activeGame);
        addBinderTargetId = created.id;
        renderAddBinderPicker();
      }
    } catch (err) {
      showAddToast(tr("Create failed", "Erstellen fehlgeschlagen"));
    }
  });
}

// Sets (Karten) bzw. Kategorien (Vault) als Akkordeon direkt unter der Suche
// (05.08., Nutzer-Vorgabe "so sortieren wie unser Home Bildschirm... der
// SET Name und wenn man drauf klickt klappen sich die Karten auf" - vorher
// eine Chip-Reihe mit den Karten weit unten in einem separaten Bereich,
// dadurch bei vielen Sets/Kategorien schwer zu überblicken). Gleiche
// .setAccordion*-Optik wie im Karten-/Vault-Tab, siehe renderCardsTab().
// Lädt bei Bedarf einmalig nach und rendert dann neu.
function renderAddGroups() {
  const wrap = document.getElementById("addGroups");
  wrap.innerHTML = "";
  wrap.className = "setAccordion";

  if (!addKindUsesSealedCatalog()) {
    const requestedKind = addOverlayKind;
    const sets = setsCache[activeGame];
    if (!sets) {
      loadSetsForGame(activeGame).then(() => {
        if (addOverlayKind === requestedKind) renderAddGroups();
      });
      return;
    }
    for (const set of sets) {
      wrap.appendChild(buildAddAccordionItem(set.id, set.name, set.totalCards));
    }
  } else {
    const catalog = sealedCatalogCache[activeGame];
    if (!catalog) {
      ensureSealedCatalogLoaded(activeGame).then(() => {
        if (addKindUsesSealedCatalog()) renderAddGroups();
      });
      return;
    }
    // Eigenes Produkt (28.08., Parität zu "Nicht dabei? Eigenes Produkt
    // eintragen" in VaultScreen.kt) - nur beim Vault-Hinzufügen, nicht bei
    // Wantslisten (die brauchen einen Katalog-Bezug für den Preis)
    if (addOverlayKind === "vault") {
      const customBtn = document.createElement("button");
      customBtn.className = "gridSortChip";
      customBtn.style.margin = "0 0 8px 0";
      customBtn.textContent = tr("Not listed? Add a custom product", "Nicht dabei? Eigenes Produkt eintragen");
      customBtn.addEventListener("click", async () => {
        const name = window.prompt(tr("Product name:", "Produktname:"), "");
        if (name === null || !name.trim()) return;
        const category = window.prompt(tr("Category (e.g. Display, Tin):", "Kategorie (z.B. Display, Tin):"), "");
        if (category === null || !category.trim()) return;
        try {
          const res = await fetch("/api/sealed/add", {
            method: "POST",
            headers: authHeaders(true),
            body: JSON.stringify({ catalogId: null, name: name.trim(), category: category.trim(), game: activeGame, imageUrl: null, quantity: 1, isSealed: true })
          });
          if (!res.ok) throw new Error("add failed");
          showAddToast("✓ 1 " + tr("vault product", "Vault-Produkt") + " " + tr("added", "hinzugefügt"));
          await loadData();
        } catch (err) {
          showAddToast(tr("Adding failed", "Hinzufügen fehlgeschlagen"));
        }
      });
      wrap.appendChild(customBtn);
    }
    const categories = Array.from(new Set(catalog.map(c => c.category))).sort();
    for (const cat of categories) {
      const count = catalog.filter(c => c.category === cat).length;
      wrap.appendChild(buildAddAccordionItem(cat, cat, count));
    }
  }
}

// Ein Akkordeon-Eintrag (Set bzw. Vault-Kategorie) - beim Öffnen wird der
// Katalog synchron aus dem Cache gezeichnet, falls schon geladen, sonst
// einmalig nachgeladen und danach die ganze Liste neu gerendert (gleiches
// Prinzip wie renderCardsTab()/loadCatalogForSet() im Haupt-Akkordeon).
function buildAddAccordionItem(id, label, count) {
  const isOpen = addGroupId === id;
  const item = document.createElement("div");
  item.className = "setAccordionItem" + (isOpen ? " open" : "");

  const header = document.createElement("button");
  header.className = "setAccordionHeader";
  header.innerHTML = "<span>" + label + "</span><span class=\"setAccordionCount\">" + count + "</span>";
  header.addEventListener("click", () => selectAddGroup(id));
  item.appendChild(header);

  const body = document.createElement("div");
  body.className = "setAccordionBody";
  if (isOpen) {
    if (!addKindUsesSealedCatalog()) {
      const catalog = catalogCache[id];
      if (!catalog) {
        const loading = document.createElement("p");
        loading.className = "status";
        loading.style.display = "block";
        loading.textContent = tr("Loading…", "Lädt…");
        body.appendChild(loading);
        ensureSetCatalogLoaded(id).then(() => {
          if (addGroupId === id) renderAddGroups();
        });
      } else {
        body.appendChild(buildAddGroupGrid(catalog));
      }
    } else {
      const catalog = (sealedCatalogCache[activeGame] || []).filter(c => c.category === id);
      body.appendChild(buildAddGroupGrid(catalog));
    }
  }
  item.appendChild(body);
  return item;
}

// Kartenraster für einen aufgeklappten Akkordeon-Eintrag (05.08.) - fehlende
// Karten (noch nicht in der Sammlung) werden ausgegraut dargestellt, bereits
// besessene normal farbig (Nutzer-Vorgabe "das sollte ja nur mit den
// Fehlenden so sein", gleiche Konvention wie "Ganzes Set" im Karten-Tab,
// siehe renderOpenSetBody()) - nur bei kind === "cards" relevant, wo
// "besessen" überhaupt einen Sinn ergibt.
function buildAddGroupGrid(catalog) {
  if (catalog.length === 0) {
    const empty = document.createElement("p");
    empty.className = "status";
    empty.style.display = "block";
    empty.textContent = tr("No cards here.", "Keine Karten hier.");
    return empty;
  }
  const grid = document.createElement("div");
  grid.className = "grid";
  const ownedIds = addOverlayKind === "cards"
    ? new Set(allCards.filter(item => item.game === activeGame).map(item => item.cardId))
    : null;
  for (const c of catalog) {
    const missing = ownedIds !== null && !ownedIds.has(c.id);
    grid.appendChild(buildAddCardTile(c, missing));
  }
  return grid;
}

// Einzelne Kachel im Akkordeon-Raster (05.08.) - eigene, schlankere Variante
// von buildCardTile() (dort an die selectedCardIds-Auswahl des Karten-Tabs
// gekoppelt): hier zählt die Auswahl über addSelected, und es gibt keine
// Mengen-/Holo-Anzeige, da diese Kacheln reine Katalogeinträge zum
// Hinzufügen sind, keine bereits besessenen Sammlungs-Zeilen.
function buildAddCardTile(item, missing) {
  const payload = addKindUsesSealedCatalog()
    ? { catalogId: item.id, name: item.name, category: item.category, game: activeGame, imageUrl: item.imageUrl, quantity: 1, isSealed: true }
    : (addOverlayKind === "wishlist" || addOverlayKind === "binder")
      ? { cardId: item.id, name: item.name, imageUrl: item.imageUrl }
      : { cardId: item.id, name: item.name, imageUrl: item.imageUrl, quantity: 1, isHolo: false };
  const isSelected = addSelected.has(item.id);

  const card = document.createElement("div");
  card.className = "card selectable" + (missing ? " missing" : "") + (isSelected ? " selected" : "");
  card.style.setProperty("--card-color", colorFor(activeGame));

  const art = document.createElement("div");
  if (item.imageUrl) {
    art.className = "art";
    const img = document.createElement("img");
    img.src = item.imageUrl;
    img.loading = "lazy";
    img.alt = item.name;
    img.addEventListener("error", () => {
      art.className = "art placeholder";
      art.textContent = tr("No image", "Kein Bild");
    });
    art.appendChild(img);
  } else {
    art.className = "art placeholder";
    art.textContent = tr("No image", "Kein Bild");
  }
  card.appendChild(art);

  const name = document.createElement("div");
  name.className = "name";
  name.textContent = item.name;
  card.appendChild(name);

  const meta = document.createElement("div");
  meta.className = "meta";
  const left = document.createElement("span");
  if (missing) {
    const badge = document.createElement("span");
    badge.className = "badge";
    badge.textContent = tr("missing", "fehlt");
    left.appendChild(badge);
  }
  meta.appendChild(left);
  const right = document.createElement("span");
  right.textContent = formatTileMarketPrice(item);
  meta.appendChild(right);
  card.appendChild(meta);

  card.addEventListener("click", () => {
    if (addSelected.has(item.id)) {
      addSelected.delete(item.id);
      card.classList.remove("selected");
    } else {
      addSelected.set(item.id, payload);
      card.classList.add("selected");
    }
    renderAddActionButton();
  });

  return card;
}

function selectAddGroup(id) {
  addGroupId = addGroupId === id ? null : id;
  renderAddGroups();
}

// Entscheidet, ob die Freitextsuche oder das Set-/Kategorien-Akkordeon
// angezeigt wird - Suche hat Vorrang, sobald etwas eingegeben wurde (05.08.:
// das Akkordeon selbst rendert seinen Karteninhalt jetzt direkt in
// renderAddGroups()/buildAddAccordionItem(), diese Funktion kümmert sich nur
// noch um die Suchergebnis-Liste in #addResults).
function renderAddContent() {
  const input = document.getElementById("addSearchInput");
  const query = input.value.trim();
  const groups = document.getElementById("addGroups");
  if (query) {
    groups.style.display = "none";
    runAddSearch(query);
    return;
  }
  groups.style.display = "";
  document.getElementById("addResults").innerHTML = "";
}

async function runAddSearch(query) {
  if (!addKindUsesSealedCatalog()) {
    try {
      const res = await authedFetch("/api/searchCatalog?game=" + encodeURIComponent(activeGame) + "&query=" + encodeURIComponent(query));
      if (!res.ok) return;
      const items = await res.json();
      renderAddResults(buildCardRows(items));
    } catch (err) {
      renderAddResults([], tr("Search failed.", "Suche fehlgeschlagen."));
    }
  } else {
    const catalog = await ensureSealedCatalogLoaded(activeGame);
    if (!catalog) {
      renderAddResults([], tr("Search failed.", "Suche fehlgeschlagen."));
      return;
    }
    const q = query.toLowerCase();
    const matches = catalog.filter(c => c.name.toLowerCase().includes(q)).slice(0, 40);
    renderAddResults(buildVaultRows(matches));
  }
}

function buildCardRows(items) {
  // Wunschlisten- und Binder-Einträge brauchen keine quantity/isHolo (siehe
  // WishlistItemEntity/BinderItemEntity in Portfolio.sq), nur die echte
  // Sammlung ("cards") schon. Deck-Karten (10.08.) brauchen nur die cardId
  // selbst (siehe AddDeckCardRequest in Main.kt, quantity defaultet dort
  // serverseitig auf 1).
  const forListEntry = addOverlayKind === "wishlist" || addOverlayKind === "binder";
  return items.map(c => ({
    key: c.id,
    thumb: c.imageUrl,
    name: c.name,
    sub: c.setName ? c.setName : ("#" + c.number),
    payload: addOverlayKind === "deck"
      ? { cardId: c.id }
      : forListEntry
      ? { cardId: c.id, name: c.name, imageUrl: c.imageUrl }
      : { cardId: c.id, name: c.name, imageUrl: c.imageUrl, quantity: 1, isHolo: false }
  }));
}

function buildVaultRows(items) {
  return items.map(c => ({
    key: c.id,
    thumb: c.imageUrl,
    name: c.name,
    sub: c.category,
    payload: { catalogId: c.id, name: c.name, category: c.category, game: activeGame, imageUrl: c.imageUrl, quantity: 1, isSealed: true }
  }));
}

// Zeilen sind jetzt Toggle-Auswahl statt Sofort-Hinzufügen (Nutzer-Vorgabe
// 27.07.) - ".selected" zeigt eine Markierung, der eigentliche Schreibvorgang
// passiert gesammelt in runAddBatch() über den Haupt-Button oben.
function renderAddResults(rows, emptyMessage) {
  const results = document.getElementById("addResults");
  results.innerHTML = "";
  if (rows.length === 0) {
    results.innerHTML = "<p class=\"status\" style=\"display:block\">" + (emptyMessage || tr("No matches.", "Keine Treffer.")) + "</p>";
    return;
  }
  for (const r of rows) {
    const row = document.createElement("div");
    row.className = "addResultRow" + (addSelected.has(r.key) ? " selected" : "");
    const thumb = document.createElement("div");
    thumb.className = "thumb";
    if (r.thumb) {
      const img = document.createElement("img");
      img.src = r.thumb;
      img.alt = "";
      thumb.appendChild(img);
    }
    const info = document.createElement("div");
    info.className = "info";
    info.innerHTML = "<div class=\"name\">" + r.name + "</div><div class=\"sub\">" + (r.sub || "") + "</div>";
    row.appendChild(thumb);
    row.appendChild(info);
    row.addEventListener("click", () => {
      if (addSelected.has(r.key)) {
        addSelected.delete(r.key);
        row.classList.remove("selected");
      } else {
        addSelected.set(r.key, r.payload);
        row.classList.add("selected");
      }
      renderAddActionButton();
    });
    results.appendChild(row);
  }
}

// Fügt alle ausgewählten Einträge in einem Rutsch hinzu. Karten/Vault: je ein
// POST pro Eintrag (die Endpunkte kennen kein Batch-Format), einzelne
// Fehlschläge stoppen nicht den Rest des Stapels. Wunschlisten (27.07.): ein
// einziger POST mit allen Karten (/api/wishlistItems/add nimmt eine Liste
// entgegen), da hier ohnehin nur EIN Ziel (die gerade offene Liste) existiert.
async function runAddBatch() {
  if (addSelected.size === 0) return;

  // "Karte dreht sich in die Sammlung" auf allen ausgewählten Zeilen, bevor
  // tatsächlich hinzugefügt wird
  document.querySelectorAll(".addResultRow.selected").forEach((row) => {
    const thumb = row.querySelector(".thumb");
    if (thumb) thumb.classList.add("added");
    row.style.pointerEvents = "none";
  });

  const kind = addOverlayKind;
  const payloads = Array.from(addSelected.values());
  let successCount = 0;
  // Nur bei kind === "cards" relevant (31.07., Nutzer-Vorgabe) - welche
  // Karten tatsächlich erfolgreich zur Sammlung hinzugefügt wurden, damit nur
  // die auch dem gewählten Binder zugeordnet werden
  const addedCardPayloads = [];

  if (kind === "wishlist") {
    try {
      const res = await fetch("/api/wishlistItems/add", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ wishlistId: addWishlistTargetId, cards: payloads })
      });
      if (res.ok) successCount = payloads.length;
    } catch (err) {
      // s.u. - Toast zeigt den Fehlschlag
    }
  } else if (kind === "binder") {
    try {
      const res = await fetch("/api/binderItems/add", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ binderId: addBinderOverlayTargetId, cards: payloads })
      });
      if (res.ok) successCount = payloads.length;
    } catch (err) {
      // s.u. - Toast zeigt den Fehlschlag
    }
  } else if (kind === "sealedWishlist") {
    // Sealed-Wantsliste (28.08.) - ein Aufruf pro Produkt, wie beim Deck
    for (const payload of payloads) {
      try {
        const res = await fetch("/api/sealedWishlistItems/add", {
          method: "POST",
          headers: authHeaders(true),
          body: JSON.stringify({ wishlistId: addSealedWishlistTargetId, game: activeGame, catalogId: payload.catalogId })
        });
        if (res.ok) successCount++;
      } catch (err) {
        // einzelner Fehlschlag stoppt nicht den Rest des Stapels
      }
    }
  } else if (kind === "deck") {
    // Kein Batch-Endpunkt fürs Deck (siehe AddDeckCardRequest in Main.kt -
    // ein Aufruf pro Karte), analog zum "cards"/"vault"-Zweig unten
    for (const payload of payloads) {
      try {
        const res = await fetch("/api/deckCards/add", {
          method: "POST",
          headers: authHeaders(true),
          body: JSON.stringify({ deckId: addDeckTargetId, cardId: payload.cardId })
        });
        if (res.ok) successCount++;
      } catch (err) {
        // einzelner Fehlschlag stoppt nicht den Rest des Stapels
      }
    }
  } else {
    const endpoint = kind === "cards" ? "/api/collection/add" : "/api/sealed/add";
    for (const payload of payloads) {
      try {
        const res = await fetch(endpoint, {
          method: "POST",
          headers: authHeaders(true),
          body: JSON.stringify(payload)
        });
        if (res.ok) {
          successCount++;
          if (kind === "cards") addedCardPayloads.push(payload);
        }
      } catch (err) {
        // einzelner Fehlschlag stoppt nicht den Rest des Stapels
      }
    }
  }

  // Binder-Zuordnung (31.07., Nutzer-Vorgabe) - eigene, zweite Schreib-
  // operation NACH dem eigentlichen Hinzufügen zur Sammlung, analog dazu,
  // wie die App das macht (siehe assignToBinderIfSelected() in App.kt)
  if (kind === "cards" && addBinderTargetId !== null && addedCardPayloads.length > 0) {
    try {
      await fetch("/api/binderItems/add", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({
          binderId: addBinderTargetId,
          cards: addedCardPayloads.map(p => ({ cardId: p.cardId, name: p.name, imageUrl: p.imageUrl }))
        })
      });
      delete binderItemsCache[activeGame];
    } catch (err) {
      // Sammlung wurde bereits erfolgreich befüllt - ein fehlgeschlagener
      // zweiter Schritt (Binder-Zuordnung) darf das nicht rückgängig machen
      // oder als Gesamtfehlschlag gemeldet werden
    }
  }

  const noun = kind === "cards"
    ? (successCount === 1 ? tr("card", "Karte") : tr("cards", "Karten"))
    : kind === "wishlist"
      ? (successCount === 1 ? tr("wishlist card", "Karte zur Liste") : tr("wishlist cards", "Karten zur Liste"))
      : kind === "binder"
        ? (successCount === 1 ? tr("binder card", "Karte zum Binder") : tr("binder cards", "Karten zum Binder"))
        : kind === "deck"
          ? (successCount === 1 ? tr("deck card", "Karte zum Deck") : tr("deck cards", "Karten zum Deck"))
          : kind === "sealedWishlist"
            ? (successCount === 1 ? tr("want list product", "Produkt zur Wantsliste") : tr("want list products", "Produkte zur Wantsliste"))
            : (successCount === 1 ? tr("vault product", "Vault-Produkt") : tr("vault products", "Vault-Produkte"));
  showAddToast(successCount > 0 ? ("✓ " + successCount + " " + noun + " " + tr("added", "hinzugefügt")) : tr("Adding failed", "Hinzufügen fehlgeschlagen"));

  addSelected = new Map();
  if (kind === "wishlist") {
    delete wishlistItemsCache[activeGame];
    await loadWishlistsForGame(activeGame);
  } else if (kind === "sealedWishlist") {
    await loadSealedWishlistsForGame(activeGame);
  } else if (kind === "binder") {
    delete binderItemsCache[activeGame];
    await loadBindersForGame(activeGame);
  } else if (kind === "deck") {
    delete deckCardsCache[addDeckTargetId];
    delete decksCache[activeGame];
    await Promise.all([loadDeckCards(addDeckTargetId), loadDecksForGame(activeGame)]);
  } else {
    await loadData();
    if (kind === "cards" && addBinderTargetId !== null) await loadBindersForGame(activeGame);
  }
  if (addOverlayKind === kind) {
    renderAddActionButton();
    renderAddContent();
  }
}

document.getElementById("addClose").addEventListener("click", closeAddOverlay);
document.getElementById("addOverlay").addEventListener("click", (e) => {
  if (e.target.id === "addOverlay") closeAddOverlay();
});
document.getElementById("addSearchInput").addEventListener("input", () => {
  clearTimeout(addSearchDebounce);
  addSearchDebounce = setTimeout(() => renderAddContent(), 250);
});
document.addEventListener("keydown", (e) => {
  if (e.key === "Escape" && document.getElementById("addOverlay").classList.contains("visible")) {
    closeAddOverlay();
  }
});

async function loadSetsForGame(game) {
  try {
    const res = await authedFetch("/api/sets?game=" + encodeURIComponent(game));
    if (!res.ok) return;
    setsCache[game] = await res.json();
    if (activeTab === "cards" && activeGame === game) render();
  } catch (err) {
    // Set-Liste ist ein Zusatzfeature - bei Fehlern bleibt einfach "Alle"
    // nutzbar, kein harter Fehlerzustand für die ganze Seite nötig
  }
}

async function ensureSetCatalogLoaded(setId) {
  if (catalogCache[setId]) return catalogCache[setId];
  try {
    const res = await authedFetch("/api/catalog?setId=" + encodeURIComponent(setId));
    if (!res.ok) return null;
    catalogCache[setId] = await res.json();
    return catalogCache[setId];
  } catch (err) {
    return null;
  }
}

async function loadCatalogForSet(setId) {
  const catalog = await ensureSetCatalogLoaded(setId);
  if (catalog && activeTab === "cards" && openSetId === setId) render();
}

async function ensureSealedCatalogLoaded(game) {
  if (sealedCatalogCache[game]) return sealedCatalogCache[game];
  try {
    const res = await authedFetch("/api/sealedCatalog?game=" + encodeURIComponent(game));
    if (!res.ok) return null;
    sealedCatalogCache[game] = await res.json();
    return sealedCatalogCache[game];
  } catch (err) {
    return null;
  }
}

// Werte-Tab (24.07., neu) - Kennzahlen fürs gerade gewählte TCG, berechnet
// aus den bereits geladenen Karten-/Vault-Daten (kein eigener Server-
// Endpunkt nötig). AUSSCHLIESSLICH echte Cardmarket-EUR-Preise (03.08.,
// Nutzer-Vorgabe USD-Alt-Preise komplett raus (Wortlaut: CONCEPT.md) -
// siehe CONCEPT.md "Copyright-/Release-Recherche") - kein Alt-USD-
// Rückfall mehr. Werte-Tab (26.07., "der ganze Rest, der unter Werte in
// der App ist, genau so übernehmen") - fast 1:1-Nachbau von
// ValueOverviewScreen.kt in der App.
function marketTotalEur(item) {
  if (item.marketPriceEur === null || item.marketPriceEur === undefined) return null;
  return item.marketPriceEur * item.quantity;
}

// Eigener Preis (20.08., Parität zur App): ist einer gesetzt und der
// passende Schalter aktiv, ERSETZT er den Cardmarket-Preis in der
// jeweiligen Summe - global zählt nur customPriceInTotal, im TCG-Wert
// zusätzlich customPriceInGameTotal. Siehe effectiveUnitEur* in
// ValueOverviewScreen.kt für dieselbe Logik App-seitig.
function effectiveUnitEurGlobal(item) {
  if (item.customPriceEur !== null && item.customPriceEur !== undefined && item.customPriceInTotal === 1) return item.customPriceEur;
  return (item.marketPriceEur === null || item.marketPriceEur === undefined) ? null : item.marketPriceEur;
}

function effectiveUnitEurGame(item) {
  if (item.customPriceEur !== null && item.customPriceEur !== undefined && (item.customPriceInTotal === 1 || item.customPriceInGameTotal === 1)) return item.customPriceEur;
  return (item.marketPriceEur === null || item.marketPriceEur === undefined) ? null : item.marketPriceEur;
}

function effectiveTotalEurGame(item) {
  const unit = effectiveUnitEurGame(item);
  return unit === null ? null : unit * item.quantity;
}

function profitEur(item) {
  const total = effectiveTotalEurGame(item);
  if (total === null) return null;
  return total - item.purchasePrice * item.quantity;
}

function renderValuePanel() {
  const panel = document.getElementById("valuePanel");
  const cards = allCards.filter(item => item.game === activeGame);
  const sealed = allSealed.filter(item => item.game === activeGame);
  const all = cards.concat(sealed);

  const totalMarketEur = all.reduce((s, i) => s + (effectiveTotalEurGame(i) || 0), 0);
  const totalSpentEur = all.reduce((s, i) => s + i.purchasePrice * i.quantity, 0);
  const totalProfitEur = totalMarketEur - totalSpentEur;

  const grandTotalEur = allCards.reduce((s, i) => s + ((effectiveUnitEurGlobal(i) || 0) * i.quantity), 0) +
    allSealed.reduce((s, i) => s + ((effectiveUnitEurGlobal(i) || 0) * i.quantity), 0);

  // "Eigene Werte" (20.08., Parität zur App): Summe ALLER von Hand
  // eingetragenen Preise über alle TCGs, unabhängig von den Schaltern
  const yourWorthEur = allCards.concat(allSealed).reduce(
    (s, i) => s + ((i.customPriceEur !== null && i.customPriceEur !== undefined) ? i.customPriceEur * i.quantity : 0), 0);

  const bestCard = maxByProfit(cards);
  const worstCard = minByProfit(cards);
  const bestSealed = maxByProfit(sealed);
  const worstSealed = minByProfit(sealed);
  const mostValuableCard = cards.filter(c => effectiveUnitEurGame(c) !== null).sort((a, b) => effectiveUnitEurGame(b) - effectiveUnitEurGame(a))[0];
  const mostValuableSealed = sealed.filter(s => effectiveUnitEurGame(s) !== null).sort((a, b) => effectiveUnitEurGame(b) - effectiveUnitEurGame(a))[0];

  panel.innerHTML = "";

  const note = document.createElement("p");
  note.className = "status";
  note.style.cssText = "display:block;text-align:left;margin:0;color:rgba(255,255,255,0.55);font-size:0.85rem;";
  note.textContent = tr("Market prices come from Cardmarket. Cards/products without a Cardmarket match don't show up here yet.", "Marktpreise stammen von Cardmarket. Karten/Produkte ohne Cardmarket-Zuordnung tauchen hier noch nicht auf.");
  panel.appendChild(note);

  const heroAll = document.createElement("div");
  heroAll.className = "valueHero";
  heroAll.innerHTML =
    "<div class=\"label\">" + tr("Total collection value", "Gesamtwert der Sammlung") + "</div>" +
    "<div class=\"amount\">" + formatEur(grandTotalEur) + "</div>" +
    "<div class=\"label\">" + tr("all TCGs", "alle TCGs") + "</div>";
  panel.appendChild(heroAll);

  const heroGame = document.createElement("div");
  heroGame.className = "valueHero";
  heroGame.innerHTML =
    "<div class=\"label\">" + tr("Total value of ", "Gesamtwert von ") + labelFor(activeGame) + "</div>" +
    "<div class=\"amount\">" + formatEur(totalMarketEur) + "</div>";
  panel.appendChild(heroGame);

  const statsGrid = document.createElement("div");
  statsGrid.className = "valueStatsGrid";

  // targetItem (28.08., Parität zur App vom 19.08.): Kachel springt direkt
  // in die Karte bzw. das Vault-Produkt (tapFor() in ValueOverviewScreen.kt)
  function addStat(label, amount, sub, color, targetItem) {
    const el = document.createElement("div");
    el.className = "valueStat";
    el.innerHTML =
      "<div class=\"label\">" + label + "</div>" +
      "<div class=\"amount\"" + (color ? " style=\"color:" + color + "\"" : "") + ">" + amount + "</div>" +
      (sub ? "<div class=\"sub\">" + sub + "</div>" : "");
    if (targetItem) {
      el.style.cursor = "pointer";
      el.addEventListener("click", () => openCardDetail(targetItem, null));
    }
    statsGrid.appendChild(el);
  }

  addStat(tr("Spent", "Ausgegeben"), formatEur(totalSpentEur));
  addStat(tr("Your own values", "Eigene Werte"), formatEur(yourWorthEur), tr("all TCGs, always counted", "alle TCGs, zählt immer"));
  addStat(
    totalProfitEur >= 0 ? tr("Profit so far", "Gewinn bisher") : tr("Loss so far", "Verlust bisher"),
    formatEur(totalProfitEur),
    null,
    totalProfitEur >= 0 ? "#66BB6A" : "#E57373"
  );
  addStat(tr("Biggest gain (card)", "Größter Gewinn (Karte)"), bestCard ? formatEur(profitEur(bestCard)) : tr("No data yet", "Noch keine Daten"), bestCard ? bestCard.name : null, "#66BB6A", bestCard);
  addStat(tr("Biggest loss (card)", "Größter Verlust (Karte)"), worstCard ? formatEur(profitEur(worstCard)) : tr("No data yet", "Noch keine Daten"), worstCard ? worstCard.name : null, "#E57373", worstCard);
  addStat(tr("Biggest gain (vault product)", "Größter Gewinn (Vault-Produkt)"), bestSealed ? formatEur(profitEur(bestSealed)) : tr("No data yet", "Noch keine Daten"), bestSealed ? bestSealed.name : null, "#66BB6A", bestSealed);
  addStat(tr("Biggest loss (vault product)", "Größter Verlust (Vault-Produkt)"), worstSealed ? formatEur(profitEur(worstSealed)) : tr("No data yet", "Noch keine Daten"), worstSealed ? worstSealed.name : null, "#E57373", worstSealed);
  addStat(tr("Most valuable card", "Wertvollste Karte"), mostValuableCard ? formatTileMarketPrice(mostValuableCard) : tr("No data yet", "Noch keine Daten"), mostValuableCard ? mostValuableCard.name : null, null, mostValuableCard);
  addStat(tr("Most valuable vault product", "Wertvollstes Vault-Produkt"), mostValuableSealed ? formatTileMarketPrice(mostValuableSealed) : tr("No data yet", "Noch keine Daten"), mostValuableSealed ? mostValuableSealed.name : null, null, mostValuableSealed);

  panel.appendChild(statsGrid);

  // Manuelle Cardmarket-Zuordnung (04.08., Nutzer-Vorgabe "das muss ja beim
  // Server dann sowieso auch gebaut werden") - eigene, klickbare Kachel
  // unter dem Stats-Grid, öffnet renderCardmarketMappingOverlay()
  const mappingCard = document.createElement("div");
  mappingCard.className = "valueStat";
  mappingCard.style.cursor = "pointer";
  mappingCard.style.marginTop = "10px";
  mappingCard.innerHTML =
    "<div class=\"label\">" + tr("Price matching", "Preis-Zuordnung") + "</div>" +
    "<div class=\"amount\" id=\"cardmarketMappingSummary\">…</div>";
  mappingCard.addEventListener("click", () => openCardmarketMappingOverlay());
  panel.appendChild(mappingCard);
  refreshCardmarketMappingSummary();

  // Sealed-Pendant (11.08., Nutzer-Vorgabe) - eigene, klickbare Kachel direkt
  // darunter, öffnet openSealedCardmarketMappingOverlay()
  const sealedMappingCard = document.createElement("div");
  sealedMappingCard.className = "valueStat";
  sealedMappingCard.style.cursor = "pointer";
  sealedMappingCard.style.marginTop = "10px";
  sealedMappingCard.innerHTML =
    "<div class=\"label\">" + tr("Price matching (sealed)", "Preis-Zuordnung (Sealed)") + "</div>" +
    "<div class=\"amount\" id=\"sealedCardmarketMappingSummary\">…</div>";
  sealedMappingCard.addEventListener("click", () => openSealedCardmarketMappingOverlay());
  panel.appendChild(sealedMappingCard);
  refreshSealedCardmarketMappingSummary();

  // Eigene Preise an einer Stelle (20.08., Parität zur App vom 19.08.) -
  // Kachel mit Zähler, öffnet die Liste aller selbst bepreisten Karten
  // und Sealed-Produkte des aktiven TCGs
  const cpCards = cards.filter(c => c.customPriceEur !== null && c.customPriceEur !== undefined);
  const cpSealed = sealed.filter(s => s.customPriceEur !== null && s.customPriceEur !== undefined);
  const customCard = document.createElement("div");
  customCard.className = "valueStat";
  customCard.style.cursor = "pointer";
  customCard.style.marginTop = "10px";
  customCard.innerHTML =
    "<div class=\"label\">" + tr("Your own prices", "Eigene Preise") + "</div>" +
    "<div class=\"amount\">" + cpCards.length + " " + tr("card(s)", "Karte(n)") + " · " + cpSealed.length + " " + tr("product(s)", "Produkt(e)") + "</div>";
  customCard.addEventListener("click", () => openCustomPricedOverlay(cpCards, cpSealed));
  panel.appendChild(customCard);
}

// Liste aller selbst bepreisten Karten/Produkte (20.08.) - schlichtes
// Overlay im Stil der Zuordnungs-Overlays: Zeile antippen öffnet die
// Detailansicht, der Papierkorb entfernt NUR den eigenen Preis
function openCustomPricedOverlay(cpCards, cpSealed) {
  const overlay = document.createElement("div");
  overlay.id = "customPricedOverlay";
  overlay.style.cssText = "position:fixed;inset:0;z-index:60;background:rgba(0,0,0,0.92);overflow-y:auto;padding:24px 16px 60px;";
  const inner = document.createElement("div");
  inner.style.cssText = "max-width:640px;margin:0 auto;";
  const head = document.createElement("div");
  head.style.cssText = "display:flex;align-items:center;justify-content:space-between;margin-bottom:14px;";
  head.innerHTML = "<h2 style=\"margin:0;font-size:1.2rem;\">" + tr("Your own prices", "Eigene Preise") + "</h2>";
  const closeBtn = document.createElement("button");
  closeBtn.className = "detailCmChip";
  closeBtn.textContent = tr("Close", "Schließen");
  closeBtn.addEventListener("click", () => overlay.remove());
  head.appendChild(closeBtn);
  inner.appendChild(head);

  const rows = cpCards.map(c => ({ item: c, isSealed: false })).concat(cpSealed.map(s => ({ item: s, isSealed: true })));
  if (rows.length === 0) {
    const empty = document.createElement("p");
    empty.className = "status";
    empty.style.display = "block";
    empty.textContent = tr("Nothing with your own price in this TCG yet.", "Noch nichts mit eigenem Preis in diesem TCG.");
    inner.appendChild(empty);
  }
  rows.forEach(({ item, isSealed }) => {
    const row = document.createElement("div");
    row.style.cssText = "display:flex;align-items:center;gap:12px;background:rgba(0,0,0,0.45);border:1px solid rgba(255,255,255,0.12);border-radius:10px;padding:8px 12px;margin-bottom:8px;cursor:pointer;";
    const img = document.createElement("img");
    img.src = item.imageUrl || "";
    img.style.cssText = "width:38px;aspect-ratio:0.72;object-fit:cover;border-radius:5px;background:rgba(255,255,255,0.08);";
    row.appendChild(img);
    const col = document.createElement("div");
    col.style.cssText = "flex:1;min-width:0;";
    const scope = item.customPriceInTotal === 1
      ? tr("counts in all totals", "zählt in allen Gesamtwerten")
      : (item.customPriceInGameTotal === 1 ? tr("counts in this TCG's total only", "zählt nur im TCG-Gesamtwert") : tr("not counted in any total", "zählt in keinem Gesamtwert"));
    col.innerHTML = "<div style=\"font-weight:700;\">" + item.name + "</div>" +
      "<div style=\"font-size:0.82rem;color:" + colorFor(item.game) + ";\">" + formatEur(item.customPriceEur) + " · " + scope + "</div>";
    row.appendChild(col);
    const trash = document.createElement("button");
    trash.className = "detailCmChip";
    trash.textContent = "🗑";
    trash.title = tr("Remove own price", "Eigenen Preis entfernen");
    trash.addEventListener("click", (e) => {
      e.stopPropagation();
      item.customPriceEur = null;
      item.customPriceInTotal = 1;
      item.customPriceInGameTotal = 1;
      persistCustomPrice(item, isSealed);
      row.remove();
    });
    row.appendChild(trash);
    row.addEventListener("click", () => {
      overlay.remove();
      openCardDetail(item, null);
    });
    inner.appendChild(row);
  });
  overlay.appendChild(inner);
  document.body.appendChild(overlay);
}

// ---------- Manuelle Cardmarket-Zuordnung (04.08.) ----------

async function refreshCardmarketMappingSummary() {
  const el = document.getElementById("cardmarketMappingSummary");
  if (!el) return;
  try {
    const [unmatchedRes, mappedRes] = await Promise.all([
      authedFetch("/api/cardmarketMapping/unmatched?game=" + encodeURIComponent(activeGame)),
      authedFetch("/api/cardmarketMapping/mapped?game=" + encodeURIComponent(activeGame))
    ]);
    const unmatched = await unmatchedRes.json();
    const mapped = await mappedRes.json();
    el.textContent = unmatched.length + " " + tr("without price", "ohne Preis") + " · " + mapped.length + " " + tr("manually matched", "manuell zugeordnet");
  } catch (e) {
    el.textContent = "-";
  }
}

async function openCardmarketMappingOverlay() {
  const list = document.getElementById("cardmarketMappingList");
  list.innerHTML = "<p class=\"status\">" + tr("Loading…", "Lädt…") + "</p>";
  document.getElementById("cardmarketMappingOverlay").classList.add("visible");

  const [unmatchedRes, mappedRes] = await Promise.all([
    authedFetch("/api/cardmarketMapping/unmatched?game=" + encodeURIComponent(activeGame)),
    authedFetch("/api/cardmarketMapping/mapped?game=" + encodeURIComponent(activeGame))
  ]);
  const unmatched = await unmatchedRes.json();
  const mapped = await mappedRes.json();

  list.innerHTML = "";
  const header1 = document.createElement("p");
  header1.className = "confirmMessage";
  header1.style.fontSize = "1rem";
  header1.textContent = tr("Without price (", "Ohne Preis (") + unmatched.length + ")";
  list.appendChild(header1);
  if (unmatched.length === 0) {
    const p = document.createElement("p");
    p.className = "status";
    p.textContent = tr("A Cardmarket price was found for all of your cards.", "Für alle deine Karten wurde ein Cardmarket-Preis gefunden.");
    list.appendChild(p);
  } else {
    unmatched.forEach(card => list.appendChild(buildCardmarketMappingRow(card, tr("No price found", "Kein Preis gefunden"), null)));
  }

  const header2 = document.createElement("p");
  header2.className = "confirmMessage";
  header2.style.fontSize = "1rem";
  header2.style.marginTop = "14px";
  header2.textContent = tr("Manually matched (", "Manuell zugeordnet (") + mapped.length + ")";
  list.appendChild(header2);
  if (mapped.length === 0) {
    const p = document.createElement("p");
    p.className = "status";
    p.textContent = tr("No manual matches yet.", "Noch keine manuellen Zuordnungen.");
    list.appendChild(p);
  } else {
    mapped.forEach(card => {
      const subtitle = "→ " + card.manualProductName +
        (card.marketPriceEur !== null && card.marketPriceEur !== undefined ? " (" + formatEur(card.marketPriceEur) + ")" : " - " + tr("no price found", "kein Preis gefunden"));
      list.appendChild(buildCardmarketMappingRow(card, subtitle, card.id));
    });
  }

  refreshCardmarketMappingSummary();
}

function buildCardmarketMappingRow(card, subtitle, removableCardId) {
  const row = document.createElement("div");
  row.style.cssText = "display:flex;align-items:center;gap:10px;padding:8px 0;border-bottom:1px solid rgba(255,255,255,0.08);cursor:pointer;";
  const img = document.createElement("img");
  img.src = card.imageUrl;
  img.style.cssText = "width:36px;height:50px;object-fit:cover;border-radius:4px;background:rgba(255,255,255,0.08);";
  // Bild-Tipp öffnet die Karte selbst (28.08., Parität zur App vom 25.08.)
  img.addEventListener("click", (e) => {
    e.stopPropagation();
    const owned = allCards.find(c => c.id === card.id) || allCards.find(c => c.cardId && c.cardId === card.cardId);
    if (!owned) return;
    document.getElementById("cardmarketMappingOverlay").classList.remove("visible");
    openCardDetail(owned, null);
  });
  row.appendChild(img);
  const textWrap = document.createElement("div");
  textWrap.style.cssText = "flex:1;min-width:0;";
  textWrap.innerHTML =
    "<div style=\"color:#fff;font-weight:bold;font-size:0.9rem;\">" + card.name + "</div>" +
    "<div style=\"color:var(--accent);font-size:0.8rem;\">" + subtitle + "</div>";
  row.appendChild(textWrap);
  row.addEventListener("click", () => openCardmarketAssignOverlay(card));
  // Eigener Preis direkt aus der Ohne-Preis-Zeile (28.08., Parität zur
  // App vom 19.08.) - nur bei den Zeilen OHNE Zuordnung sinnvoll
  if (!removableCardId) {
    const priceBtn = document.createElement("button");
    priceBtn.textContent = "€";
    priceBtn.title = tr("Set own price", "Eigenen Preis setzen");
    priceBtn.style.cssText = "background:none;border:1px solid rgba(255,255,255,0.25);border-radius:6px;color:#fff;font-size:0.9rem;cursor:pointer;padding:2px 8px;";
    priceBtn.addEventListener("click", async (e) => {
      e.stopPropagation();
      const owned = allCards.find(c => c.id === card.id);
      if (!owned) return;
      const raw = window.prompt(tr("Own price (EUR):", "Eigener Preis (EUR):"), "");
      if (raw === null) return;
      const value = parseFloat(raw.replace(",", "."));
      if (isNaN(value) || value < 0) return;
      owned.customPriceEur = value;
      if (owned.customPriceInTotal === undefined || owned.customPriceInTotal === null) owned.customPriceInTotal = 1;
      if (owned.customPriceInGameTotal === undefined || owned.customPriceInGameTotal === null) owned.customPriceInGameTotal = 1;
      await persistCustomPrice(owned, false);
      openCardmarketMappingOverlay();
    });
    row.appendChild(priceBtn);
  }
  if (removableCardId) {
    const removeBtn = document.createElement("button");
    removeBtn.textContent = "✕";
    removeBtn.style.cssText = "background:none;border:none;color:#E57373;font-size:1.1rem;cursor:pointer;";
    removeBtn.addEventListener("click", async (e) => {
      e.stopPropagation();
      await fetch("/api/cardmarketMapping/remove", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ cardId: removableCardId })
      });
      openCardmarketMappingOverlay();
    });
    row.appendChild(removeBtn);
  }
  return row;
}

let cardmarketAssignDebounce = null;

function openCardmarketAssignOverlay(card) {
  document.getElementById("cardmarketAssignTitle").textContent = tr("Match: ", "Zuordnen: ") + card.name;
  const searchInput = document.getElementById("cardmarketAssignSearch");
  searchInput.value = card.name;
  document.getElementById("cardmarketAssignResults").innerHTML = "";
  document.getElementById("cardmarketAssignOverlay").classList.add("visible");

  const renderResults = (items, emptyMessage) => {
    const results = document.getElementById("cardmarketAssignResults");
    results.innerHTML = "";
    if (items.length === 0) {
      results.innerHTML = "<p class=\"status\">" + emptyMessage + "</p>";
      return;
    }
    items.forEach(item => {
      const row = document.createElement("div");
      row.style.cssText = "display:flex;justify-content:space-between;gap:10px;padding:8px 0;border-bottom:1px solid rgba(255,255,255,0.08);cursor:pointer;";
      row.innerHTML =
        "<div style=\"color:#fff;font-size:0.85rem;\">" + item.name + "</div>" +
        "<div style=\"color:var(--accent);font-weight:bold;font-size:0.85rem;\">" + (item.priceEur !== null ? formatEur(item.priceEur) : "-") + "</div>";
      row.addEventListener("click", async () => {
        await fetch("/api/cardmarketMapping/assign", {
          method: "POST",
          headers: authHeaders(true),
          body: JSON.stringify({ cardId: card.id, productId: item.idProduct, productName: item.name })
        });
        document.getElementById("cardmarketAssignOverlay").classList.remove("visible");
        openCardmarketMappingOverlay();
      });
      results.appendChild(row);
    });
  };

  const runSearch = async () => {
    const query = searchInput.value.trim();
    const results = document.getElementById("cardmarketAssignResults");
    if (query.length < 2) { results.innerHTML = ""; return; }
    results.innerHTML = "<p class=\"status\">" + tr("Searching…", "Suche…") + "</p>";
    try {
      const res = await authedFetch("/api/cardmarketMapping/search?game=" + encodeURIComponent(activeGame) + "&query=" + encodeURIComponent(query));
      renderResults(await res.json(), tr("No matches.", "Keine Treffer."));
    } catch (e) {
      results.innerHTML = "<p class=\"status\">" + tr("Cardmarket data could not be loaded.", "Cardmarket-Daten konnten nicht geladen werden.") + "</p>";
    }
  };

  // "Aus dem Set durchstöbern" (12.08., Nutzer-Vorgabe "man kann nur
  // suchen und da findet man ja nichts, da es sonst die APP selbst
  // gefunden hätte") - Alternative zur Textsuche, leitet die Cardmarket-
  // Erweiterung automatisch über eine bereits bepreiste Schwesterkarte aus
  // demselben Set her (siehe findSetExpansionId() in CardmarketPriceSync.kt).
  const runBrowseSet = async () => {
    const results = document.getElementById("cardmarketAssignResults");
    results.innerHTML = "<p class=\"status\">" + tr("Browsing…", "Durchstöbern…") + "</p>";
    try {
      const res = await authedFetch("/api/cardmarketMapping/browseSet?game=" + encodeURIComponent(activeGame) + "&cardId=" + encodeURIComponent(card.id));
      renderResults(await res.json(), tr("No already matched card from this set found - browsing needs at least one reference card with a price in the same set.", "Keine bereits zugeordnete Karte aus diesem Set gefunden - Durchstöbern braucht mindestens eine Vergleichskarte mit Preis im selben Set."));
    } catch (e) {
      results.innerHTML = "<p class=\"status\">" + tr("Cardmarket data could not be loaded.", "Cardmarket-Daten konnten nicht geladen werden.") + "</p>";
    }
  };
  document.getElementById("cardmarketAssignBrowseSet").onclick = runBrowseSet;

  searchInput.oninput = () => {
    if (cardmarketAssignDebounce) clearTimeout(cardmarketAssignDebounce);
    cardmarketAssignDebounce = setTimeout(runSearch, 400);
  };
  runSearch();
}

document.getElementById("cardmarketMappingClose").addEventListener("click", () => {
  document.getElementById("cardmarketMappingOverlay").classList.remove("visible");
});
document.getElementById("cardmarketMappingOverlay").addEventListener("click", (e) => {
  if (e.target.id === "cardmarketMappingOverlay") document.getElementById("cardmarketMappingOverlay").classList.remove("visible");
});
document.getElementById("cardmarketAssignCancel").addEventListener("click", () => {
  document.getElementById("cardmarketAssignOverlay").classList.remove("visible");
});
document.getElementById("cardmarketAssignOverlay").addEventListener("click", (e) => {
  if (e.target.id === "cardmarketAssignOverlay") document.getElementById("cardmarketAssignOverlay").classList.remove("visible");
});

// ---------- Manuelle Cardmarket-Zuordnung für Sealed-Produkte (11.08.) ----------
// Pendant zu den Funktionen oben, siehe dortige Kommentare für die Begründung.

async function refreshSealedCardmarketMappingSummary() {
  const el = document.getElementById("sealedCardmarketMappingSummary");
  if (!el) return;
  try {
    const [unmatchedRes, mappedRes] = await Promise.all([
      authedFetch("/api/sealedCardmarketMapping/unmatched?game=" + encodeURIComponent(activeGame)),
      authedFetch("/api/sealedCardmarketMapping/mapped?game=" + encodeURIComponent(activeGame))
    ]);
    const unmatched = await unmatchedRes.json();
    const mapped = await mappedRes.json();
    el.textContent = unmatched.length + " " + tr("without price", "ohne Preis") + " · " + mapped.length + " " + tr("manually matched", "manuell zugeordnet");
  } catch (e) {
    el.textContent = "-";
  }
}

async function openSealedCardmarketMappingOverlay() {
  const list = document.getElementById("sealedCardmarketMappingList");
  list.innerHTML = "<p class=\"status\">" + tr("Loading…", "Lädt…") + "</p>";
  document.getElementById("sealedCardmarketMappingOverlay").classList.add("visible");

  const [unmatchedRes, mappedRes] = await Promise.all([
    authedFetch("/api/sealedCardmarketMapping/unmatched?game=" + encodeURIComponent(activeGame)),
    authedFetch("/api/sealedCardmarketMapping/mapped?game=" + encodeURIComponent(activeGame))
  ]);
  const unmatched = await unmatchedRes.json();
  const mapped = await mappedRes.json();

  list.innerHTML = "";
  const header1 = document.createElement("p");
  header1.className = "confirmMessage";
  header1.style.fontSize = "1rem";
  header1.textContent = tr("Without price (", "Ohne Preis (") + unmatched.length + ")";
  list.appendChild(header1);
  if (unmatched.length === 0) {
    const p = document.createElement("p");
    p.className = "status";
    p.textContent = tr("A Cardmarket price was found for all of your sealed products.", "Für alle deine Sealed-Produkte wurde ein Cardmarket-Preis gefunden.");
    list.appendChild(p);
  } else {
    unmatched.forEach(item => list.appendChild(buildSealedCardmarketMappingRow(item, tr("No price found", "Kein Preis gefunden"), null)));
  }

  const header2 = document.createElement("p");
  header2.className = "confirmMessage";
  header2.style.fontSize = "1rem";
  header2.style.marginTop = "14px";
  header2.textContent = tr("Manually matched (", "Manuell zugeordnet (") + mapped.length + ")";
  list.appendChild(header2);
  if (mapped.length === 0) {
    const p = document.createElement("p");
    p.className = "status";
    p.textContent = tr("No manual matches yet.", "Noch keine manuellen Zuordnungen.");
    list.appendChild(p);
  } else {
    mapped.forEach(item => {
      const subtitle = "→ " + item.manualProductName +
        (item.marketPriceEur !== null && item.marketPriceEur !== undefined ? " (" + formatEur(item.marketPriceEur) + ")" : " - " + tr("no price found", "kein Preis gefunden"));
      list.appendChild(buildSealedCardmarketMappingRow(item, subtitle, item.id));
    });
  }

  refreshSealedCardmarketMappingSummary();
}

function buildSealedCardmarketMappingRow(item, subtitle, removableSealedId) {
  const row = document.createElement("div");
  row.style.cssText = "display:flex;align-items:center;gap:10px;padding:8px 0;border-bottom:1px solid rgba(255,255,255,0.08);cursor:pointer;";
  const img = document.createElement("img");
  img.src = item.imageUrl || "";
  img.style.cssText = "width:36px;height:50px;object-fit:cover;border-radius:4px;background:rgba(255,255,255,0.08);";
  // Bild-Tipp öffnet das Produkt (28.08., Parität zur App vom 25.08.)
  img.addEventListener("click", (e) => {
    e.stopPropagation();
    const owned = allSealed.find(s => s.id === item.id);
    if (!owned) return;
    document.getElementById("sealedCardmarketMappingOverlay").classList.remove("visible");
    openCardDetail(owned, null);
  });
  row.appendChild(img);
  const textWrap = document.createElement("div");
  textWrap.style.cssText = "flex:1;min-width:0;";
  textWrap.innerHTML =
    "<div style=\"color:#fff;font-weight:bold;font-size:0.9rem;\">" + item.name + "</div>" +
    "<div style=\"color:var(--accent);font-size:0.8rem;\">" + subtitle + "</div>";
  row.appendChild(textWrap);
  row.addEventListener("click", () => openSealedCardmarketAssignOverlay(item));
  // Eigener Preis direkt aus der Ohne-Preis-Zeile (28.08.)
  if (!removableSealedId) {
    const priceBtn = document.createElement("button");
    priceBtn.textContent = "€";
    priceBtn.title = tr("Set own price", "Eigenen Preis setzen");
    priceBtn.style.cssText = "background:none;border:1px solid rgba(255,255,255,0.25);border-radius:6px;color:#fff;font-size:0.9rem;cursor:pointer;padding:2px 8px;";
    priceBtn.addEventListener("click", async (e) => {
      e.stopPropagation();
      const owned = allSealed.find(s => s.id === item.id);
      if (!owned) return;
      const raw = window.prompt(tr("Own price (EUR):", "Eigener Preis (EUR):"), "");
      if (raw === null) return;
      const value = parseFloat(raw.replace(",", "."));
      if (isNaN(value) || value < 0) return;
      owned.customPriceEur = value;
      if (owned.customPriceInTotal === undefined || owned.customPriceInTotal === null) owned.customPriceInTotal = 1;
      if (owned.customPriceInGameTotal === undefined || owned.customPriceInGameTotal === null) owned.customPriceInGameTotal = 1;
      await persistCustomPrice(owned, true);
      openSealedCardmarketMappingOverlay();
    });
    row.appendChild(priceBtn);
  }
  if (removableSealedId) {
    const removeBtn = document.createElement("button");
    removeBtn.textContent = "✕";
    removeBtn.style.cssText = "background:none;border:none;color:#E57373;font-size:1.1rem;cursor:pointer;";
    removeBtn.addEventListener("click", async (e) => {
      e.stopPropagation();
      await fetch("/api/sealedCardmarketMapping/remove", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ sealedId: removableSealedId })
      });
      openSealedCardmarketMappingOverlay();
    });
    row.appendChild(removeBtn);
  }
  return row;
}

let sealedCardmarketAssignDebounce = null;

function openSealedCardmarketAssignOverlay(item) {
  document.getElementById("sealedCardmarketAssignTitle").textContent = tr("Match: ", "Zuordnen: ") + item.name;
  const searchInput = document.getElementById("sealedCardmarketAssignSearch");
  searchInput.value = item.name;
  document.getElementById("sealedCardmarketAssignResults").innerHTML = "";
  document.getElementById("sealedCardmarketAssignOverlay").classList.add("visible");

  const runSearch = async () => {
    const query = searchInput.value.trim();
    const results = document.getElementById("sealedCardmarketAssignResults");
    if (query.length < 2) { results.innerHTML = ""; return; }
    results.innerHTML = "<p class=\"status\">" + tr("Searching…", "Suche…") + "</p>";
    try {
      const res = await authedFetch("/api/sealedCardmarketMapping/search?game=" + encodeURIComponent(activeGame) + "&query=" + encodeURIComponent(query));
      const items = await res.json();
      results.innerHTML = "";
      if (items.length === 0) {
        results.innerHTML = "<p class=\"status\">" + tr("No matches.", "Keine Treffer.") + "</p>";
        return;
      }
      items.forEach(result => {
        const row = document.createElement("div");
        row.style.cssText = "display:flex;justify-content:space-between;gap:10px;padding:8px 0;border-bottom:1px solid rgba(255,255,255,0.08);cursor:pointer;";
        row.innerHTML =
          "<div style=\"color:#fff;font-size:0.85rem;\">" + result.name + "</div>" +
          "<div style=\"color:var(--accent);font-weight:bold;font-size:0.85rem;\">" + (result.priceEur !== null ? formatEur(result.priceEur) : "-") + "</div>";
        row.addEventListener("click", async () => {
          await fetch("/api/sealedCardmarketMapping/assign", {
            method: "POST",
            headers: authHeaders(true),
            body: JSON.stringify({ sealedId: item.id, productId: result.idProduct, productName: result.name })
          });
          document.getElementById("sealedCardmarketAssignOverlay").classList.remove("visible");
          openSealedCardmarketMappingOverlay();
        });
        results.appendChild(row);
      });
    } catch (e) {
      results.innerHTML = "<p class=\"status\">" + tr("Cardmarket data could not be loaded.", "Cardmarket-Daten konnten nicht geladen werden.") + "</p>";
    }
  };

  searchInput.oninput = () => {
    if (sealedCardmarketAssignDebounce) clearTimeout(sealedCardmarketAssignDebounce);
    sealedCardmarketAssignDebounce = setTimeout(runSearch, 400);
  };
  runSearch();
}

document.getElementById("sealedCardmarketMappingClose").addEventListener("click", () => {
  document.getElementById("sealedCardmarketMappingOverlay").classList.remove("visible");
});
document.getElementById("sealedCardmarketMappingOverlay").addEventListener("click", (e) => {
  if (e.target.id === "sealedCardmarketMappingOverlay") document.getElementById("sealedCardmarketMappingOverlay").classList.remove("visible");
});
document.getElementById("sealedCardmarketAssignCancel").addEventListener("click", () => {
  document.getElementById("sealedCardmarketAssignOverlay").classList.remove("visible");
});
document.getElementById("sealedCardmarketAssignOverlay").addEventListener("click", (e) => {
  if (e.target.id === "sealedCardmarketAssignOverlay") document.getElementById("sealedCardmarketAssignOverlay").classList.remove("visible");
});

function maxByProfit(items) {
  let best = null;
  let bestVal = -Infinity;
  for (const item of items) {
    const p = profitEur(item);
    if (p !== null && p > bestVal) { bestVal = p; best = item; }
  }
  return best;
}

function minByProfit(items) {
  let worst = null;
  let worstVal = Infinity;
  for (const item of items) {
    const p = profitEur(item);
    if (p !== null && p < worstVal) { worstVal = p; worst = item; }
  }
  return worst;
}

// Umrechnungsfaktor-UI entfernt (03.08., Nutzer-Vorgabe "USD-Alt-Preise
// raus") - es gibt nichts mehr zu kalibrieren, seit die Wertanzeige
// ausschließlich echte Cardmarket-EUR-Preise zeigt. loadMarketFactor() lädt
// den gespeicherten Wert weiterhin im Hintergrund (marketFactor bleibt Teil
// der Server-Settings/des Sync-Protokolls), er wird aber nirgends mehr
// angezeigt oder verändert.
async function loadMarketFactor() {
  try {
    const res = await authedFetch("/api/marketFactor");
    if (!res.ok) return;
    const data = await res.json();
    marketFactor = data.factor;
  } catch (err) {
    // Ungenutzt, siehe Kommentar oben - Fehler hier sind folgenlos
  }
}

// Detail-Ansicht (24.07.) - Anklicken einer Karte lässt sie sich drehen,
// größer werden und rechts über den anderen Karten schweben, statt einfach
// nur ein Modal einzublenden. Reine CSS-Transform-Animation (FLIP-Technik):
// die Detail-Karte ist immer im DOM (nur unsichtbar über Opacity), wir messen
// ihre echte End-Position/-Größe, "beamen" sie unsichtbar-schnell optisch an
// die Stelle der angeklickten Kachel (Invert) und lassen den Browser dann von
// dort weich zur echten Position animieren (Play) - inklusive einer vollen
// Drehung fürs "dreht sich auf einen zu". Zeigt dabei durchgehend dieselbe
// Vorderseite (kein Rückseiten-Flip, siehe CONCEPT.md).
// Nutzer-Fund (03.08.): die Dreh-Animation ruckelte "wie ein Video mit
// schlechter Verbindung" - Ursache war, dass das Kartenbild noch mitten in
// der 0.55s-Transform-Animation dekodiert wurde (besonders bei großen/noch
// nicht gecachten Bildern). Der Browser musste die Dekodier-Arbeit auf dem
// Hauptthread mit den Animations-Frames teilen, was zu Rucklern führte.
// img.decode() lässt den Browser das Bild VOR dem Animationsstart fertig
// dekodieren (asynchron, blockiert den Hauptthread dabei nicht) - danach
// läuft die reine Transform-Animation ungestört. async statt await macht
// hier nichts kaputt, da openCardDetail() nirgends auf ihr Ergebnis wartet.
async function openCardDetail(item, sourceEl) {
  const overlay = document.getElementById("detailOverlay");
  const detailCard = document.getElementById("detailCard");
  const img = document.getElementById("detailImg");

  detailClosing = false;
  detailCard.style.visibility = "";
  detailCard.style.setProperty("--card-color", colorFor(item.game));
  // Regenbogen-Schimmer nur für Holo-Karten (18.08., siehe style.css)
  detailCard.classList.toggle("holo", !!item.isHolo);
  if (item.imageUrl) {
    img.src = item.imageUrl;
    img.style.display = "";
    try {
      await img.decode();
    } catch (err) {
      // Bild kaputt/nicht ladbar - Animation trotzdem starten statt
      // hängen zu bleiben, das Fehlerbild/leere Feld übernimmt dann
      // das normale <img>-Fallback-Verhalten
    }
    // Nutzer könnte die Detailansicht in der Zwischenzeit schon wieder
    // geschlossen haben (schnelles Antippen) - dann nicht nachträglich
    // wieder aufblenden
    if (detailClosing) return;
  } else {
    img.removeAttribute("src");
    img.style.display = "none";
  }
  renderDetailPhotoUpload(item);
  document.getElementById("detailName").textContent = item.name + (item.isHolo ? " ✨" : "");
  document.getElementById("detailGameRow").innerHTML =
    "<span>" + tr("Game", "Spiel") + "</span><span class=\"value\">" + labelFor(item.game) + "</span>";
  renderDetailEditControls(item);
  // Cardmarket ist der EINZIGE Marktpreis (03.08., Nutzer-Vorgabe "dann
  // nehmen wir USD-Alt-Preise raus und gut ist" - siehe CONCEPT.md
  // "Copyright-/Release-Recherche") - kein Alt-USD-Rückfall mehr,
  // renderDetailCardmarketOptions() unten zeigt die ausführliche Zeile bei
  // mehreren Notierungen. Sealed-Produkte haben SEIT 11.08. genau wie Karten
  // eine Options-Liste (siehe applyCardmarketSealedPrices()) - vorher gab es
  // dort bewusst nur einen einzelnen marketPriceEur-Wert ohne Auswahl, das
  // war der jetzt behobene Nutzer-Fund ("können wir das dann so wie bei den
  // Karten machen die garnicht zu geordnet sind oder von denen es mehrere
  // Preise gibt").
  const hasCardmarketOptions = (item.marketPriceEurOptions || []).length > 0;
  const hasSimpleEurPrice = !hasCardmarketOptions && item.marketPriceEur !== null && item.marketPriceEur !== undefined;
  document.getElementById("detailPriceRow").innerHTML = hasCardmarketOptions ? "" :
    hasSimpleEurPrice
      ? "<span>" + tr("Market price (Cardmarket)", "Marktpreis (Cardmarket)") + "</span><span class=\"value\">" + formatEur(item.marketPriceEur) + "</span>"
      : "<span>" + tr("Market price", "Marktpreis") + "</span><span class=\"value\">–</span>";
  renderDetailCardmarketOptions(item);
  renderDetailArtVariants(item);

  overlay.classList.add("visible");

  const sourceRect = sourceEl.getBoundingClientRect();
  const finalRect = detailCard.getBoundingClientRect();
  const scaleX = sourceRect.width / finalRect.width;
  const scaleY = sourceRect.height / finalRect.height;
  const dx = sourceRect.left + sourceRect.width / 2 - (finalRect.left + finalRect.width / 2);
  const dy = sourceRect.top + sourceRect.height / 2 - (finalRect.top + finalRect.height / 2);

  detailCard.style.transition = "none";
  detailCard.style.transform = `translate(${dx}px, ${dy}px) scale(${scaleX}, ${scaleY}) rotateY(0deg)`;

  // Doppeltes requestAnimationFrame, damit der Browser den Invert-Zustand
  // sicher gemalt hat, bevor die Transition wieder aktiv wird - bei nur
  // einem rAF wird das manchmal noch mit dem nächsten Schritt zusammengefasst
  // und die Animation "springt" statt zu gleiten
  requestAnimationFrame(() => {
    requestAnimationFrame(() => {
      detailCard.style.transition = "";
      detailCard.style.transform = "translate(0, 0) scale(1, 1) rotateY(360deg)";
    });
  });
}

// Eigenes Foto (10.08., Feature-Parität App <-> Web) - siehe
// setCustomCardPhoto()/PlainPhotoCaptureOverlay in App.kt für dasselbe
// Prinzip auf der App-Seite (dort per Kamera, hier per Datei-Upload). Nur
// für Karten mit stabiler cardId relevant (item.category !== undefined
// wären Vault-Produkte - die haben ihr Foto direkt als eigenes imageUrl-Feld
// beim Anlegen, kein separater CustomCardPhotoEntity-Mechanismus dafür,
// siehe Main.kt/PortfolioRepository.kt). isCustom erkennt eine bereits
// gesetzte eigene Datei am eigenen "/images/custom/"-URL-Präfix (siehe
// /api/customPhoto/upload) - rein clientseitige Heuristik, aber ausreichend,
// da nur unsere eigenen Uploads je diesen Pfad bekommen.
function renderDetailPhotoUpload(item) {
  const wrap = document.getElementById("detailPhotoUploadRow");
  if (!wrap) return;
  wrap.innerHTML = "";
  const isSealed = item.category !== undefined;
  // Sealed-/Vault-Produkte (12.08., Nutzer-Vorgabe "fehlt noch bei Vault
  // Produkten") - entityId ist bei Sealed die eigene SealedProductEntity.id
  // (Zahl), bei Karten weiterhin die cardId (String). Beide Fälle teilen
  // sich ab hier dieselbe Logik, nur Formularfeld-Name und Endpunkt weichen
  // via isSealed voneinander ab.
  const entityId = isSealed ? item.id : item.cardId;
  if (entityId === undefined || entityId === null) return;

  const isCustom = (item.imageUrl || "").includes("/images/custom/");

  if (!item.imageUrl || isCustom) {
    const label = document.createElement("label");
    label.className = "detailPhotoUploadBtn";
    label.textContent = item.imageUrl ? tr("Replace photo", "Foto ersetzen") : tr("Upload your own photo", "Eigenes Foto hochladen");
    const input = document.createElement("input");
    input.type = "file";
    input.accept = "image/*";
    input.style.display = "none";
    input.addEventListener("change", async (e) => {
      const file = e.target.files && e.target.files[0];
      e.target.value = "";
      if (!file) return;
      label.textContent = tr("Uploading…", "Lädt hoch…");
      const formData = new FormData();
      formData.append(isSealed ? "sealedId" : "cardId", entityId);
      formData.append("file", file);
      try {
        const res = await fetch(isSealed ? "/api/sealedPhoto/upload" : "/api/customPhoto/upload", {
          method: "POST",
          headers: { "X-Pairing-Token": getStoredToken(), "X-Account-Id": getActiveAccountId() },
          body: formData
        });
        if (!res.ok) throw new Error("upload failed");
        const data = await res.json();
        item.imageUrl = data.imageUrl;
        const detailImg = document.getElementById("detailImg");
        detailImg.src = data.imageUrl;
        detailImg.style.display = "";
        renderDetailPhotoUpload(item);
        await loadData();
      } catch (err) {
        showAddToast(tr("Photo upload failed", "Foto-Upload fehlgeschlagen"));
        renderDetailPhotoUpload(item);
      }
    });
    label.appendChild(input);
    wrap.appendChild(label);
  }
  if (isCustom) {
    const clearBtn = document.createElement("button");
    clearBtn.className = "detailPhotoUploadBtn";
    clearBtn.textContent = tr("Remove photo", "Foto entfernen");
    clearBtn.addEventListener("click", async () => {
      // Bei Sealed-Produkten gibt es (anders als bei Karten, siehe COALESCE
      // in Portfolio.sq) kein automatisches Zurückfallen auf das
      // Katalogbild - der Server liefert die Rückfall-URL deshalb explizit
      // in der Antwort mit (leerer String, falls auch kein Katalogbild da ist).
      let revertedUrl = null;
      try {
        if (isSealed) {
          const res = await fetch("/api/sealedPhoto/clear", {
            method: "POST",
            headers: authHeaders(true),
            body: JSON.stringify({ sealedId: entityId })
          });
          const data = await res.json();
          revertedUrl = data.imageUrl || null;
        } else {
          await fetch("/api/customPhoto/clear", {
            method: "POST",
            headers: authHeaders(true),
            body: JSON.stringify({ cardId: entityId })
          });
        }
      } catch (err) {
        // best effort - Foto bleibt notfalls einfach leer
      }
      item.imageUrl = revertedUrl;
      const detailImg = document.getElementById("detailImg");
      if (revertedUrl) {
        detailImg.src = revertedUrl;
        detailImg.style.display = "";
      } else {
        detailImg.removeAttribute("src");
        detailImg.style.display = "none";
      }
      renderDetailPhotoUpload(item);
      await loadData();
    });
    wrap.appendChild(clearBtn);
  }
}

// Bearbeitbare Anzahl/Holo/Versiegelt/Kaufpreis + Löschen in der
// Kartendetailansicht (09.08., Nutzer-Vorgabe "Feature-Parität App <-> Web")
// - bisher zeigte die Weboberfläche hier nur statischen Text, Anzahl/
// Holo-Status/Kaufpreis waren NUR in der App nachträglich änderbar (siehe
// App.kt-Kartendetailansicht bzw. VaultScreen.kt für Vault-Produkte).
// item.category unterscheidet ein Vault-Produkt (SealedProductResponse) von
// einer normalen Sammlungskarte (CollectionCardResponse) - Karten haben kein
// category-Feld, siehe Main.kt.
function renderDetailEditControls(item) {
  const isSealed = item.category !== undefined;

  const qtyRow = document.getElementById("detailQuantityRow");
  qtyRow.innerHTML = "";
  const qtyLabel = document.createElement("span");
  qtyLabel.textContent = tr("Quantity", "Anzahl");
  qtyRow.appendChild(qtyLabel);
  const qtyControls = document.createElement("span");
  qtyControls.className = "detailStepper";
  const minusBtn = document.createElement("button");
  minusBtn.className = "detailStepperBtn";
  minusBtn.textContent = "–";
  minusBtn.addEventListener("click", () => {
    if (item.quantity <= 1) {
      deleteDetailItem(item, isSealed);
      return;
    }
    item.quantity -= 1;
    persistDetailItem(item, isSealed);
    renderDetailEditControls(item);
  });
  qtyControls.appendChild(minusBtn);
  const qtyValue = document.createElement("span");
  qtyValue.className = "value";
  qtyValue.textContent = "×" + item.quantity;
  qtyControls.appendChild(qtyValue);
  const plusBtn = document.createElement("button");
  plusBtn.className = "detailStepperBtn";
  plusBtn.textContent = "+";
  plusBtn.addEventListener("click", () => {
    item.quantity += 1;
    persistDetailItem(item, isSealed);
    renderDetailEditControls(item);
  });
  qtyControls.appendChild(plusBtn);
  qtyRow.appendChild(qtyControls);

  // Holo-Umschalter nur für echte Karten (isHolo gibt es bei
  // Vault-Produkten nicht)
  const holoRow = document.getElementById("detailHoloRow");
  const sealedRow = document.getElementById("detailSealedRow");
  holoRow.innerHTML = "";
  sealedRow.innerHTML = "";
  if (!isSealed) {
    const holoLabel = document.createElement("span");
    holoLabel.textContent = "Holo";
    holoRow.appendChild(holoLabel);
    const holoChip = document.createElement("button");
    holoChip.className = "detailCmChip" + (item.isHolo ? " active" : "");
    holoChip.textContent = item.isHolo ? tr("Yes", "Ja") : tr("No", "Nein");
    holoChip.addEventListener("click", () => {
      item.isHolo = !item.isHolo;
      persistDetailItem(item, isSealed);
      renderDetailEditControls(item);
    });
    holoRow.appendChild(holoChip);
  } else {
    // Versiegelt/Geöffnet-Umschalter (Pendant zum Switch in VaultScreen.kt)
    const sealedLabel = document.createElement("span");
    sealedLabel.textContent = tr("Condition", "Zustand");
    sealedRow.appendChild(sealedLabel);
    const sealedChip = document.createElement("button");
    sealedChip.className = "detailCmChip active";
    sealedChip.textContent = item.isSealed ? tr("Sealed", "Versiegelt") : tr("Opened", "Geöffnet");
    sealedChip.addEventListener("click", () => {
      item.isSealed = !item.isSealed;
      persistDetailItem(item, isSealed);
      renderDetailEditControls(item);
    });
    sealedRow.appendChild(sealedChip);
    // Name/Kategorie bearbeiten (28.08., Parität zum Bearbeiten-Formular in
    // VaultScreen.kt - /api/sealed/update konnte beides schon, nur die
    // Oberfläche fehlte). Stift-Knopf, damit die Zeile schlank bleibt.
    const editBtn = document.createElement("button");
    editBtn.className = "detailCmChip";
    editBtn.textContent = "✎ " + tr("Edit", "Bearbeiten");
    editBtn.style.marginLeft = "8px";
    editBtn.addEventListener("click", () => {
      const newName = window.prompt(tr("Product name:", "Produktname:"), item.name);
      if (newName === null || !newName.trim()) return;
      const newCategory = window.prompt(tr("Category:", "Kategorie:"), item.category || "");
      if (newCategory === null || !newCategory.trim()) return;
      item.name = newName.trim();
      item.category = newCategory.trim();
      persistDetailItem(item, isSealed);
      const nameEl = document.getElementById("detailName");
      if (nameEl) nameEl.textContent = item.name;
    });
    sealedRow.appendChild(editBtn);
  }

  const priceRow = document.getElementById("detailPurchasePriceRow");
  priceRow.innerHTML = "";
  const priceLabel = document.createElement("span");
  priceLabel.textContent = tr("Bought for", "Gekauft für");
  priceRow.appendChild(priceLabel);
  const priceInput = document.createElement("input");
  priceInput.type = "number";
  priceInput.step = "0.01";
  priceInput.min = "0";
  priceInput.className = "detailPriceInput";
  priceInput.value = item.purchasePrice || 0;
  priceInput.addEventListener("change", () => {
    item.purchasePrice = parseFloat(priceInput.value) || 0;
    persistDetailItem(item, isSealed);
  });
  priceRow.appendChild(priceInput);

  // Eigener Preis (20.08., Nutzer-Fund "beim Server kann man einer Karte
  // noch keinen eigenen Preis geben") - Spiegel der App-Sektion: Betrag +
  // zwei Schalter (Chip 1 = alle Gesamtwerte, impliziert Chip 2; Chip 2 =
  // nur TCG-Gesamtwert; beide aus = nur "Eigene Werte"-Kennzahl). Leeres
  // Feld löscht den Preis. Gilt für Karten UND Vault-Produkte.
  const cpBox = document.getElementById("detailCustomPriceBox");
  cpBox.innerHTML = "";

  const cpRow = document.createElement("div");
  cpRow.className = "detailRow";
  const cpLabel = document.createElement("span");
  cpLabel.textContent = tr("Own price", "Eigener Preis");
  if (item.customPriceEur !== null && item.customPriceEur !== undefined) {
    cpLabel.style.color = colorFor(item.game);
    cpLabel.style.fontWeight = "700";
  }
  cpRow.appendChild(cpLabel);
  const cpInput = document.createElement("input");
  cpInput.type = "number";
  cpInput.step = "0.01";
  cpInput.min = "0";
  cpInput.className = "detailPriceInput";
  cpInput.placeholder = tr("none", "keiner");
  cpInput.value = (item.customPriceEur !== null && item.customPriceEur !== undefined) ? item.customPriceEur : "";
  cpInput.addEventListener("change", () => {
    const v = parseFloat(cpInput.value);
    item.customPriceEur = (cpInput.value === "" || isNaN(v) || v < 0) ? null : v;
    persistCustomPrice(item, isSealed);
    // Chips-Zeile sofort ein-/ausblenden, wenn der Preis gesetzt/geleert wird
    renderDetailEditControls(item);
  });
  cpRow.appendChild(cpInput);
  cpBox.appendChild(cpRow);

  // "Zählt in" nur zeigen, wenn ein eigener Preis gesetzt ist (31.08.,
  // Nutzer-Vorgabe) - ohne Preis haben die Schalter nichts zu schalten
  if (item.customPriceEur !== null && item.customPriceEur !== undefined) {
    const cpSwitchRow = document.createElement("div");
    cpSwitchRow.className = "detailRow";
    const cpSwitchLabel = document.createElement("span");
    cpSwitchLabel.textContent = tr("Counts in", "Zählt in");
    cpSwitchRow.appendChild(cpSwitchLabel);
    const cpChips = document.createElement("span");

    const totalChip = document.createElement("button");
    totalChip.className = "detailCmChip" + (item.customPriceInTotal === 1 ? " active" : "");
    totalChip.textContent = tr("Total value", "Gesamtwert");
    totalChip.addEventListener("click", () => {
      item.customPriceInTotal = item.customPriceInTotal === 1 ? 0 : 1;
      persistCustomPrice(item, isSealed);
      renderDetailEditControls(item);
    });
    cpChips.appendChild(totalChip);

    const gameChip = document.createElement("button");
    const gameChipOn = item.customPriceInTotal === 1 || item.customPriceInGameTotal === 1;
    gameChip.className = "detailCmChip" + (gameChipOn ? " active" : "");
    gameChip.textContent = tr("TCG only", "Nur TCG");
    // Chip 1 an => automatisch mit drin und gesperrt (gleiche Regel wie in
    // der App)
    gameChip.disabled = item.customPriceInTotal === 1;
    if (gameChip.disabled) gameChip.style.opacity = "0.5";
    gameChip.style.marginLeft = "6px";
    gameChip.addEventListener("click", () => {
      if (item.customPriceInTotal === 1) return;
      item.customPriceInGameTotal = item.customPriceInGameTotal === 1 ? 0 : 1;
      persistCustomPrice(item, isSealed);
      renderDetailEditControls(item);
    });
    cpChips.appendChild(gameChip);
    cpSwitchRow.appendChild(cpChips);
    cpBox.appendChild(cpSwitchRow);
  }

  const deleteBtn = document.getElementById("detailDeleteBtn");
  deleteBtn.onclick = () => deleteDetailItem(item, isSealed);
}

async function persistCustomPrice(item, isSealed) {
  try {
    await fetch(isSealed ? "/api/sealed/customPrice" : "/api/collection/customPrice", {
      method: "POST",
      headers: authHeaders(true),
      body: JSON.stringify({
        id: item.id,
        priceEur: (item.customPriceEur === null || item.customPriceEur === undefined) ? null : item.customPriceEur,
        inTotal: item.customPriceInTotal === 1,
        inGameTotal: item.customPriceInGameTotal === 1
      })
    });
  } catch (err) {
    showAddToast(tr("Saving failed", "Speichern fehlgeschlagen"));
  }
  loadData();
}

async function persistDetailItem(item, isSealed) {
  try {
    if (isSealed) {
      await fetch("/api/sealed/update", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({
          id: item.id,
          name: item.name,
          category: item.category,
          quantity: item.quantity,
          isSealed: item.isSealed,
          purchasePrice: item.purchasePrice,
          imageUrl: item.imageUrl
        })
      });
    } else {
      await fetch("/api/collection/update", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({
          id: item.id,
          quantity: item.quantity,
          isHolo: item.isHolo,
          purchasePrice: item.purchasePrice
        })
      });
    }
  } catch (err) {
    showAddToast(tr("Saving failed", "Speichern fehlgeschlagen"));
  }
  loadData();
}

async function deleteDetailItem(item, isSealed) {
  try {
    await fetch(isSealed ? "/api/sealed/delete" : "/api/collection/delete", {
      method: "POST",
      headers: authHeaders(true),
      body: JSON.stringify({ ids: [item.id] })
    });
  } catch (err) {
    showAddToast(tr("Delete failed", "Löschen fehlgeschlagen"));
  }
  shatterAndClose();
  loadData();
}

// Cardmarket-EUR-Preis + Auswahl (28.07., Nutzer-Vorgabe "let the user
// decide ... the right one will be tracked anywhere in our app or the
// server") - Web-Pendant zur App-Kartendetailansicht. Erscheint nur, wenn
// überhaupt eine Cardmarket-Notierung gefunden wurde; die Auswahl-Chips nur,
// wenn es mehr als eine gibt ("if you don't need them it doesn't matter").
// "Preise" eingeklappt (28.08., Nutzer-Fund "soviel Preise, dass man die
// Karte nicht mehr sieht" - Parität zur App, dort seit 13.08.): bei mehreren
// Notierungen steht erstmal nur die Preiszeile mit Pfeil da, die Chip-Liste
// klappt erst beim Anklicken auf. Merker pro Karte/Produkt, damit die Liste
// nach einer Auswahl offen bleibt, bei der nächsten Karte aber wieder zu ist.
let detailCmExpandedFor = null;

function renderDetailCardmarketOptions(item) {
  const row = document.getElementById("detailEurPriceRow");
  const wrap = document.getElementById("detailCmOptions");
  const options = item.marketPriceEurOptions || [];
  wrap.innerHTML = "";
  wrap.classList.remove("visible");
  row.style.cursor = "";
  row.onclick = null;
  if (options.length === 0) {
    row.innerHTML = "";
    return;
  }
  if (options.length <= 1) {
    row.innerHTML = "<span>" + tr("Market price (Cardmarket)", "Marktpreis (Cardmarket)") + "</span><span class=\"value\">" + formatEur(item.marketPriceEur || 0) + "</span>";
    return;
  }

  const identity = item.cardId || item.catalogId || item.name;
  const expanded = detailCmExpandedFor === identity;
  row.innerHTML = "<span>" + tr("Prices", "Preise") + "</span><span class=\"value\">" + formatEur(item.marketPriceEur || 0) + " " + (expanded ? "▴" : "▾") + "</span>";
  row.style.cursor = "pointer";
  row.onclick = () => {
    detailCmExpandedFor = expanded ? null : identity;
    renderDetailCardmarketOptions(item);
  };
  if (!expanded) return;

  wrap.classList.add("visible");
  const hint = document.createElement("div");
  hint.className = "detailCmHint";
  hint.textContent = tr("Several Cardmarket listings found - which one is your card?", "Mehrere Cardmarket-Notierungen gefunden - welche ist deine Karte?");
  wrap.appendChild(hint);

  const chipsRow = document.createElement("div");
  chipsRow.style.display = "flex";
  chipsRow.style.flexWrap = "wrap";
  chipsRow.style.gap = "6px";
  chipsRow.style.justifyContent = "center";
  options.forEach((price, index) => {
    const isActive = item.marketPriceEur === price;
    const chip = document.createElement("button");
    chip.className = "detailCmChip" + (isActive ? " active" : "");
    // Index 0 ist normalerweise die "normale" Variante (günstigste) -
    // "Standard" statt "Special Card #1" (Nutzer-Vorgabe 31.07.), erst ab
    // Index 1 wirklich besondere Notierungen
    chip.textContent = index === 0 ? ("Standard (" + formatEur(price) + ")") : ("Special Card #" + index + " (" + formatEur(price) + ")");
    // Sealed-Produkte (11.08.) - dieselbe Chip-Liste, aber item.cardId gibt
    // es dort nicht (siehe item.category !== undefined-Unterscheidung
    // anderswo in dieser Datei), stattdessen item.catalogId.
    const isSealedItem = item.category !== undefined;
    const hasIdentity = isSealedItem ? !!item.catalogId : !!item.cardId;
    if (!isActive && hasIdentity) {
      chip.addEventListener("click", () => selectCardmarketPrice(item, index));
    }
    chipsRow.appendChild(chip);
  });
  wrap.appendChild(chipsRow);
}

async function selectCardmarketPrice(item, index) {
  const isSealedItem = item.category !== undefined;
  const identityId = isSealedItem ? item.catalogId : item.cardId;
  if (!identityId) return;
  // Sofortiges lokales Update (kein Warten auf den Server), damit sich die
  // Auswahl beim Antippen direkt anfühlt statt zu hängen - item ist dieselbe
  // Objekt-Referenz wie in allCards, also bleibt der Rest der Seite konsistent
  const newPrice = (item.marketPriceEurOptions || [])[index];
  item.marketPriceEur = newPrice;
  renderDetailCardmarketOptions(item);
  try {
    const endpoint = isSealedItem ? "/api/sealedCardmarketPrice/select" : "/api/cardmarketPrice/select";
    const body = isSealedItem ? { sealedId: identityId, index: index } : { cardId: identityId, index: index };
    await fetch(endpoint, {
      method: "POST",
      headers: authHeaders(true),
      body: JSON.stringify(body)
    });
  } catch (err) {
    showAddToast(tr("Selection failed", "Auswahl fehlgeschlagen"));
  }
  loadData();
}

// Art-Wechsel (31.07., Nutzer-Vorgabe "wenn man einen anderen Preis wählt,
// kann man auch das Bild ändern") - unabhängig von der Cardmarket-Preis-
// Auswahl oben, siehe PortfolioRepository.changeCardVariant(). Gruppierung
// über (setId, number) statt nur number, damit keine Karten aus anderen
// Sets/Produkten mit derselben Nummer fälschlich als Variante erscheinen
// (dieselbe Logik wie beim Scan-Mehrdeutigkeits-Fix).
async function renderDetailArtVariants(item) {
  const wrap = document.getElementById("detailArtVariants");
  wrap.innerHTML = "";
  wrap.classList.remove("visible");
  if (!item.cardId || !item.setId) return;

  const catalog = await ensureSetCatalogLoaded(item.setId);
  if (!catalog) return;
  const entry = catalog.find(c => c.id === item.cardId);
  if (!entry) return;
  const variants = catalog.filter(c => c.number === entry.number);
  if (variants.length <= 1) return;

  wrap.classList.add("visible");
  const hint = document.createElement("div");
  hint.className = "detailCmHint";
  hint.textContent = tr("Several arts found - which image matches your card?", "Mehrere Arten gefunden - welches Bild passt zu deiner Karte?");
  wrap.appendChild(hint);

  const chipsRow = document.createElement("div");
  chipsRow.style.display = "flex";
  chipsRow.style.flexWrap = "wrap";
  chipsRow.style.gap = "6px";
  chipsRow.style.justifyContent = "center";
  variants.forEach((variant) => {
    const isActive = variant.id === item.cardId;
    const chip = document.createElement("button");
    chip.className = "detailCmChip" + (isActive ? " active" : "");
    chip.textContent = variant.variant || "Standard";
    if (!isActive) {
      chip.addEventListener("click", () => changeCardVariant(item, variant.id));
    }
    chipsRow.appendChild(chip);
  });
  wrap.appendChild(chipsRow);
}

async function changeCardVariant(item, newCardId) {
  if (!item.cardId) return;
  const oldCardId = item.cardId;
  // Sofortiges lokales Update analog zu selectCardmarketPrice() oben, damit
  // sich die Auswahl direkt anfühlt. Die Detailansicht bleibt dabei OFFEN
  // (31.08., Nutzer-Vorgabe "kann man das nicht adhoc machen?") - Bild und
  // Chips werden an Ort und Stelle getauscht, loadData() läuft im
  // Hintergrund für Grid/Werte nach.
  item.cardId = newCardId;
  const catalog = item.setId ? catalogCache[item.setId] : null;
  const newEntry = catalog ? catalog.find(c => c.id === newCardId) : null;
  if (newEntry) {
    item.imageUrl = newEntry.imageUrl;
    const img = document.getElementById("detailImg");
    if (img && newEntry.imageUrl) img.src = newEntry.imageUrl;
    const nameEl = document.getElementById("detailName");
    if (nameEl) nameEl.textContent = item.name;
  }
  renderDetailArtVariants(item);
  try {
    await fetch("/api/collection/changeVariant", {
      method: "POST",
      headers: authHeaders(true),
      body: JSON.stringify({ cardId: oldCardId, isHolo: item.isHolo, newCardId: newCardId })
    });
  } catch (err) {
    showAddToast(tr("Switch failed", "Wechsel fehlgeschlagen"));
  }
  loadData();
}

function closeCardDetail() {
  document.getElementById("detailOverlay").classList.remove("visible");
  const detailCard = document.getElementById("detailCard");
  // Sichtbarkeit bewusst NICHT hier zurücksetzen (Nutzer-Fund 24.07.: das
  // ließ die volle Karte für die Dauer der Overlay-Ausblend-Transition
  // [350ms] nochmal kurz aufblitzen, weil "visibility" ohne eigene
  // Transition sofort sichtbar wird, während der Hintergrund noch am
  // Ausblenden ist). openCardDetail() setzt visibility beim nächsten
  // Öffnen ohnehin zurück - hier ist es unnötig und schädlich.
  detailCard.style.transition = "none";
  detailCard.style.transform = "none";
  detailClosing = false;
}

// Dampf-Auflösung beim Schließen (24.07. ursprünglich bunte Partikel, 26.07.
// auf Nutzer-Vorgabe durch Dampf ersetzt: "als ob sich die Karte in Dampf
// auflöst", sinkt dabei nach unten weg statt wie die Dampf-Auren sonst nach
// oben zu steigen). Reines Canvas, keine Bibliothek. Weiche, wachsende
// Radial-Gradient-Kreise statt harter Rechtecke, in der Farbe der Karte
// (--card-color) statt einer bunten Palette - passt zu den Dampf-Auren an
// Buttons/Detailkarte, die dieselbe Variable nutzen.
function shatterAndClose() {
  const overlay = document.getElementById("detailOverlay");
  if (!overlay.classList.contains("visible") || detailClosing) return;
  detailClosing = true;

  const detailCard = document.getElementById("detailCard");
  const img = document.getElementById("detailImg");

  // Ohne Bild (Platzhalter-Karte) gibt es nichts zum Auflösen - normal schließen
  if (img.style.display === "none" || !img.naturalWidth) {
    closeCardDetail();
    return;
  }

  try {
    const rect = img.getBoundingClientRect();
    const cardColor = getComputedStyle(detailCard).getPropertyValue("--card-color").trim() ||
      getComputedStyle(document.documentElement).getPropertyValue("--accent").trim() || "#ffffff";
    // Der Dampf sinkt nach unten weg statt in alle Richtungen zu fliegen -
    // die Leinwand braucht deshalb vor allem darunter viel Platz, nach oben/
    // seitlich reicht weniger
    const padX = rect.width * 0.7;
    const padTop = rect.height * 0.4;
    const padBottom = rect.height * 2.4;
    const canvasRect = {
      left: rect.left - padX,
      top: rect.top - padTop,
      width: rect.width + padX * 2,
      height: rect.height + padTop + padBottom
    };
    const canvas = document.createElement("canvas");
    canvas.className = "shatterCanvas";
    canvas.style.left = canvasRect.left + "px";
    canvas.style.top = canvasRect.top + "px";
    canvas.style.width = canvasRect.width + "px";
    canvas.style.height = canvasRect.height + "px";
    const dpr = window.devicePixelRatio || 1;
    canvas.width = canvasRect.width * dpr;
    canvas.height = canvasRect.height * dpr;
    document.body.appendChild(canvas);
    const ctx = canvas.getContext("2d");
    ctx.scale(dpr, dpr);

    const cols = 8;
    const rows = Math.max(3, Math.round(cols * (rect.height / rect.width)));
    const cellW = rect.width / cols;
    const cellH = rect.height / rows;
    const puffs = [];

    for (let r = 0; r < rows; r++) {
      for (let c = 0; c < cols; c++) {
        puffs.push({
          // Startposition um das Padding verschoben, damit die Wolke optisch
          // trotz der größeren Canvas exakt an der echten Kartenstelle beginnt
          x: padX + (c + 0.5) * cellW,
          y: padTop + (r + 0.5) * cellH,
          radius: Math.max(cellW, cellH) * 0.85,
          vx: (Math.random() - 0.5) * 55,
          vy: 55 + Math.random() * 95 // positiv = sinkt nach unten
        });
      }
    }

    // Die echte Karte ist sofort weg, das Canvas übernimmt optisch - der
    // abgedunkelte Hintergrund bleibt aber bis zum Ende des Dampfs stehen,
    // sonst wirkt die Auflösung nicht wie Teil des Schließens
    detailCard.style.visibility = "hidden";

    const duration = 850;
    const start = performance.now();

    function step(now) {
      const elapsed = now - start;
      const t = elapsed / 1000;
      const progress = Math.min(1, elapsed / duration);
      ctx.clearRect(0, 0, canvasRect.width, canvasRect.height);
      const alpha = (1 - progress) * 0.7;
      const scale = 1 + progress * 1.7;
      ctx.globalAlpha = Math.max(0, alpha);
      puffs.forEach((p) => {
        const px = p.x + p.vx * t;
        const py = p.y + p.vy * t + 45 * t * t; // leicht beschleunigtes Absinken
        const r = p.radius * scale;
        const grad = ctx.createRadialGradient(px, py, 0, px, py, r);
        grad.addColorStop(0, cardColor);
        grad.addColorStop(1, "transparent");
        ctx.fillStyle = grad;
        ctx.beginPath();
        ctx.arc(px, py, r, 0, Math.PI * 2);
        ctx.fill();
      });
      ctx.globalAlpha = 1;
      if (progress < 1) {
        requestAnimationFrame(step);
      } else {
        canvas.remove();
        closeCardDetail();
      }
    }
    requestAnimationFrame(step);
  } catch (err) {
    // Sicherheitsnetz - der Dialog muss sich in jedem Fall schließen lassen,
    // auch falls der Dampf-Effekt aus irgendeinem Grund fehlschlägt
    closeCardDetail();
  }
}

// Kein Schließen-X mehr in der Detailansicht (31.08., Nutzer-Entscheidung) -
// Klick neben die Karte oder Escape schließen, siehe die zwei Handler hier
document.getElementById("detailOverlay").addEventListener("click", (e) => {
  if (e.target.id === "detailOverlay") shatterAndClose();
});
document.addEventListener("keydown", (e) => {
  if (e.key === "Escape") shatterAndClose();
});

async function loadData() {
  const status = document.getElementById("status");
  try {
    const [cardsRes, sealedRes] = await Promise.all([
      authedFetch("/api/collection"),
      authedFetch("/api/sealed"),
    ]);
    if (cardsRes.status === 401 || sealedRes.status === 401) {
      showTokenOverlay(getStoredToken() ? tr("Token rejected - please check.", "Token abgelehnt - bitte prüfen.") : "");
      return;
    }
    if (!cardsRes.ok || !sealedRes.ok) throw new Error("Server antwortete mit Fehler");
    hideTokenOverlay();
    allCards = await cardsRes.json();
    allSealed = await sealedRes.json();
    render();
    // Preis-Alarm-Hinweis (28.08., Pendant zum Alarm-Dialog der App)
    maybeShowSealedAlarms();
  } catch (err) {
    status.textContent = tr("Collection could not be loaded: ", "Sammlung konnte nicht geladen werden: ") + err.message;
    status.style.display = "block";
  }
}

// Mandantenfähigkeit (02.08., Nutzer-Vorgabe) - siehe authHeaders()/
// getActiveAccountId() weiter oben und CONCEPT.md "Mandantenfähigkeit /
// Mehrere Accounts". Bewusst ein einfacher <select>-Umschalter statt der
// App-Logik mit Standard-Haken/Auto-Login/erzwungenem Auswahlbildschirm
// (Nutzer-Entscheidung: "eine Website öffnen fühlt sich anders an").
let accountsCache = [];

async function loadAccounts() {
  try {
    const res = await authedFetch("/api/accounts");
    if (!res.ok) return;
    accountsCache = await res.json();
    const stored = getActiveAccountId();
    if (accountsCache.length > 0 && !accountsCache.some(a => String(a.id) === String(stored))) {
      setActiveAccountId(accountsCache[0].id);
    }
    renderAccountSwitcher();
  } catch (err) {
    // Best-effort - ohne geladene Liste bleibt der Server-Fallback (Account
    // mit der niedrigsten id) aktiv, siehe resolveAccountId() in Main.kt
  }
}

function renderAccountSwitcher() {
  const select = document.getElementById("accountSelect");
  const current = getActiveAccountId();
  select.innerHTML = "";
  accountsCache.forEach(acc => {
    const opt = document.createElement("option");
    opt.value = String(acc.id);
    opt.textContent = acc.name + (acc.hasPin ? " 🔒" : "");
    if (String(acc.id) === String(current)) opt.selected = true;
    select.appendChild(opt);
  });
}

document.getElementById("accountSelect").addEventListener("change", async (e) => {
  const previous = getActiveAccountId();
  const newId = e.target.value;
  const acc = accountsCache.find(a => String(a.id) === String(newId));
  if (!acc) return;
  if (acc.hasPin) {
    const pin = window.prompt(tr("PIN for \"", "PIN für \"") + acc.name + "\":");
    if (pin === null) {
      e.target.value = previous;
      return;
    }
    const res = await fetch("/api/accounts/verifyPin", {
      method: "POST",
      headers: authHeaders(true),
      body: JSON.stringify({ id: acc.id, pin: pin })
    });
    const result = await res.json();
    if (!result.correct) {
      showAddToast(tr("Wrong PIN.", "Falsche PIN."));
      e.target.value = previous;
      return;
    }
  }
  setActiveAccountId(acc.id);
  // Sichtbare TCGs sind pro Account (17.08.) - VOR loadData() laden, damit
  // die Chips-Leiste nicht kurz mit der Liste des vorigen Accounts rendert
  await loadHiddenGames();
  loadData();
  loadMarketFactor();
});

// Sichtbare TCGs des AKTIVEN Accounts pflegen (17.08., Nutzer-Vorgabe) -
// Toggle-Chips im Accounts-Overlay, gleiche Optik wie die Sortier-Chips.
// Leuchtend = eingeblendet, grau = ausgeblendet; jede Änderung speichert
// sofort (POST /api/hiddenGames), wie der Auto-Save-Stil der App. Das
// letzte sichtbare TCG lässt sich nicht auch noch ausblenden - sonst
// bliebe eine leere, unbedienbare Chips-Leiste übrig.
function renderHiddenGamesSection() {
  const wrap = document.getElementById("hiddenGamesChips");
  wrap.innerHTML = "";
  for (const g of GAMES) {
    const isHidden = hiddenGames.has(g.code);
    const chip = document.createElement("button");
    chip.className = "gridSortChip" + (isHidden ? "" : " active");
    chip.textContent = g.label;
    chip.addEventListener("click", async () => {
      if (!isHidden && hiddenGames.size >= GAMES.length - 1) {
        showAddToast(tr("At least one TCG must stay visible.", "Mindestens ein TCG muss sichtbar bleiben."));
        return;
      }
      const next = new Set(hiddenGames);
      if (isHidden) next.delete(g.code); else next.add(g.code);
      const res = await fetch("/api/hiddenGames", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ hidden: [...next] })
      });
      if (!res.ok) {
        showAddToast(tr("Saving failed.", "Speichern fehlgeschlagen."));
        return;
      }
      hiddenGames = next;
      ensureActiveGameVisible();
      render();
      renderHiddenGamesSection();
    });
    wrap.appendChild(chip);
  }
}

// Account-Verwaltung (02.08.) - eigenes kleines Overlay statt einer
// Unterseite, siehe accountsOverlay in index.html
// Sprach-Umschalter (18.08., Nutzer-Vorgabe) - Chips im Accounts-Overlay,
// gleiche Optik wie die Sichtbare-TCGs-Chips. Gilt pro Browser (localStorage,
// wie Token/Account), NICHT pro Account - die Sprache ist eine Geräte-/
// Browser-Eigenschaft, kein Sammlungsdatum. Wechsel lädt die Seite neu.
function renderLanguageSection() {
  const wrap = document.getElementById("languageChips");
  if (!wrap) return;
  wrap.innerHTML = "";
  [["en", "English"], ["de", "Deutsch"]].forEach(([code, label]) => {
    const chip = document.createElement("button");
    chip.className = "gridSortChip" + (LANG === code ? " active" : "");
    chip.textContent = label;
    chip.addEventListener("click", () => { if (LANG !== code) setLanguage(code); });
    wrap.appendChild(chip);
  });
}

function renderAccountsOverlay() {
  renderLanguageSection();
  renderHiddenGamesSection();
  const list = document.getElementById("accountsList");
  list.innerHTML = "";
  accountsCache.forEach(acc => {
    const row = document.createElement("div");
    row.className = "accountRow";
    const isActive = String(acc.id) === String(getActiveAccountId());

    const name = document.createElement("span");
    name.className = "name" + (isActive ? " active" : "");
    name.textContent = acc.name + (acc.hasPin ? " 🔒" : "") + (isActive ? tr(" (active)", " (aktiv)") : "");
    row.appendChild(name);

    const renameBtn = document.createElement("button");
    renameBtn.textContent = tr("Rename", "Umbenennen");
    renameBtn.addEventListener("click", async () => {
      const newName = window.prompt(tr("New name:", "Neuer Name:"), acc.name);
      if (!newName) return;
      await fetch("/api/accounts/rename", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ id: acc.id, name: newName })
      });
      await loadAccounts();
      renderAccountsOverlay();
    });
    row.appendChild(renameBtn);

    const pinBtn = document.createElement("button");
    pinBtn.textContent = acc.hasPin ? tr("Change PIN", "PIN ändern") : tr("Set PIN", "PIN setzen");
    pinBtn.addEventListener("click", async () => {
      const newPin = window.prompt(tr("New 4-digit PIN (empty = remove):", "Neue 4-stellige PIN (leer = entfernen):"));
      if (newPin === null) return;
      await fetch("/api/accounts/pin", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ id: acc.id, pin: newPin.trim() === "" ? null : newPin.trim() })
      });
      await loadAccounts();
      renderAccountsOverlay();
    });
    row.appendChild(pinBtn);

    const deleteBtn = document.createElement("button");
    deleteBtn.className = "danger";
    deleteBtn.textContent = tr("Delete", "Löschen");
    deleteBtn.addEventListener("click", async () => {
      if (!window.confirm("\"" + acc.name + "\" " + tr("will be deleted - all cards of this account will be lost. Continue?", "wirklich löschen? Alle Karten dieses Accounts gehen verloren."))) return;
      await fetch("/api/accounts/delete", {
        method: "POST",
        headers: authHeaders(true),
        body: JSON.stringify({ id: acc.id })
      });
      if (isActive) {
        const fallback = accountsCache.find(a => a.id !== acc.id);
        if (fallback) setActiveAccountId(fallback.id);
      }
      await loadAccounts();
      // Account kann gewechselt haben - dessen Sichtbare-TCGs-Liste laden
      await loadHiddenGames();
      renderAccountsOverlay();
      loadData();
      loadMarketFactor();
    });
    row.appendChild(deleteBtn);

    list.appendChild(row);
  });
}

document.getElementById("accountManageBtn").addEventListener("click", () => {
  renderAccountsOverlay();
  document.getElementById("accountsOverlay").classList.add("visible");
});

document.getElementById("accountsClose").addEventListener("click", () => {
  document.getElementById("accountsOverlay").classList.remove("visible");
});

// Sealed-Wantslisten-Overlay (28.08.)
document.getElementById("sealedWishlistClose").addEventListener("click", () => {
  closeSealedWishlistOverlay();
  if (activeTab === "vault") render();
});
document.getElementById("sealedWishlistBack").addEventListener("click", () => {
  sealedWlView = { mode: "overview", listId: null };
  renderSealedWishlistOverlay();
});

document.getElementById("accountsAddConfirm").addEventListener("click", async () => {
  const nameField = document.getElementById("newAccountName");
  const pinField = document.getElementById("newAccountPin");
  const name = nameField.value.trim();
  if (!name) return;
  const pin = pinField.value.trim();
  await fetch("/api/accounts", {
    method: "POST",
    headers: authHeaders(true),
    body: JSON.stringify({ name: name, pin: pin === "" ? null : pin })
  });
  nameField.value = "";
  pinField.value = "";
  await loadAccounts();
  renderAccountsOverlay();
});

// Backup-Export/-Import (10.08., Feature-Parität App <-> Web) - siehe
// BackupDialog.kt in der App für dasselbe Vorgehen (nur Ergänzen, nichts
// überschreiben/löschen). Bezieht sich auf den gerade aktiven Account
// (X-Account-Id-Header, siehe authHeaders()).
document.getElementById("backupExportBtn").addEventListener("click", async () => {
  const status = document.getElementById("backupStatus");
  status.textContent = tr("Exporting…", "Exportiere…");
  try {
    const res = await authedFetch("/api/backup/export");
    if (!res.ok) throw new Error("export failed");
    const json = await res.text();
    downloadTextFile("ult1made-tcg-backup-" + Date.now() + ".json", json);
    status.textContent = tr("Export saved successfully.", "Export erfolgreich gespeichert.");
  } catch (err) {
    status.textContent = tr("Export failed.", "Export fehlgeschlagen.");
  }
});
document.getElementById("backupImportBtn").addEventListener("click", () => {
  document.getElementById("backupImportFile").click();
});
document.getElementById("backupImportFile").addEventListener("change", async (e) => {
  const file = e.target.files && e.target.files[0];
  e.target.value = "";
  if (!file) return;
  const status = document.getElementById("backupStatus");
  status.textContent = tr("Importing…", "Importiere…");
  try {
    const text = await file.text();
    const res = await fetch("/api/backup/import", {
      method: "POST",
      headers: authHeaders(true),
      body: text
    });
    if (!res.ok) throw new Error("import failed");
    const summary = await res.json();
    // Backup v2 (28.08.): Binder/Wants/Decks nur erwähnen, wenn welche kamen
    const extras = [];
    if ((summary.bindersAdded || 0) + (summary.binderItemsAdded || 0) > 0)
      extras.push(summary.bindersAdded + " " + tr("binder(s) with ", "Binder mit ") + summary.binderItemsAdded + " " + tr("card(s)", "Karte(n)"));
    if ((summary.wishlistsAdded || 0) + (summary.wishlistItemsAdded || 0) > 0)
      extras.push(summary.wishlistsAdded + " " + tr("want list(s) with ", "Wants-Liste(n) mit ") + summary.wishlistItemsAdded + " " + tr("card(s)", "Karte(n)"));
    if ((summary.decksAdded || 0) + (summary.deckCardsAdded || 0) > 0)
      extras.push(summary.decksAdded + " " + tr("deck(s) with ", "Deck(s) mit ") + summary.deckCardsAdded + " " + tr("card(s)", "Karte(n)"));
    if ((summary.photosRestored || 0) > 0)
      extras.push(summary.photosRestored + " " + tr("photo(s)", "Foto(s)"));
    status.textContent = tr("Import done: ", "Import fertig: ") + summary.cardsAdded + " " + tr("card(s), ", "Karte(n), ") + summary.sealedAdded +
      " " + tr("vault product(s) added. ", "Vault-Produkt(e) ergänzt. ") +
      (extras.length ? tr("Also restored: ", "Außerdem wiederhergestellt: ") + extras.join(", ") + ". " : "") +
      tr("Existing entries were not changed.", "Bereits Vorhandenes wurde nicht verändert.");
    await loadData();
  } catch (err) {
    status.textContent = tr("Import failed - the file could not be read.", "Import fehlgeschlagen - Datei konnte nicht gelesen werden.");
  }
});

document.querySelectorAll(".actionTab").forEach(tab => {
  tab.addEventListener("click", () => {
    document.querySelectorAll(".actionTab").forEach(t => t.classList.remove("active"));
    tab.classList.add("active");
    activeTab = tab.dataset.tab;
    render();
  });
});

document.getElementById("tokenForm").addEventListener("submit", (e) => {
  e.preventDefault();
  const value = document.getElementById("tokenInput").value.trim();
  if (!value) return;
  localStorage.setItem("ult1madePairingToken", value);
  loadAccounts().then(() => {
    loadHiddenGames();
    loadData();
    loadMarketFactor();
  });
});

applyStaticTranslations();
updateActionTabImages();
setAccent(GAME_BY_CODE[activeGame].color);
renderServerInfo();
// Accounts zuerst (02.08.) - loadData()/loadMarketFactor() sollen mit dem
// korrekten X-Account-Id-Header laufen, siehe authHeaders(). loadAccounts()
// scheitert bei fehlendem/falschem Pairing-Token bewusst still (best-effort,
// siehe dort) - loadData() zeigt in dem Fall wie gehabt das Token-Overlay.
loadAccounts().then(() => {
  loadHiddenGames();
  loadData();
  loadMarketFactor();
});
