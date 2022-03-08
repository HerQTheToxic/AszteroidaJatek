# Aszteroida Játék

### A játék lényege
A telepeseknek az aszteroidák között mozogva kell nyersanyagokat összegyűjteníük, hogy felépíthessék az űrbázist és nyerhessenek. A nyersanyagokból lehet teleportkapúpárokat és robotokat is építeni, ami megegyszerűsíti a telepesek dolgát.

### A játék futtatása
* Monitor beállítása legyen mindenképpen 1920x1080, amit a Gépház/Rendszer/Kijelző beállításoknál lehet változtatni
* SRC-mappába navigálás után a következő parancs kiadása: javac controls/Controller.java
* Fordítás után a következő parancs kiadása: java controls/Controller

### Játékmenet:

Bal fönt látod, hogy mennyi időd van lépni es hányadik kör van éppen. Az egérrel arébb lehet húzni a képet, ezzel lehet ide-oda menni és a térképen bal alul látod, hogy éppen hol vagy. Eger görgővel tudsz zoomolni. Ha rányomsz egy szomszédos aszteroidára (sárga fény jelzi, hogy melyik szomszédos) akkor a telepesed átlép oda és vége a körödnek. Ha jobboldalt az inventorydban rányomsz egy nyersanyagra és üreges és atfúrt az aszteroida, amin állsz, akkor a nyersanyagot le tudod helyezni. A craft gombokkal tudsz robotot vagy teleportkapút készíteni. Ha nyilas teleportkapú ikonra nyomsz rá az inventorydban, akkor azt le tudod helyezni ott, ahol éppen vagy, ha van nálad, amit az ikon jelez. Használni a teleportkapút a bennelévő kis karikába kattintással lehet. Ha rányomsz az aszteroidara, amin éppen vagy, akkor vékonyítod a kérgét, vagy
kibányászod a nyersanyagát, ha már atfúrt. Nyertek ha egy aszteroidán van nálatok 3-3-3-3 mindegyik nyersanyagból, vagy vesztetek ha az összes telepes meghal.
A telepesek véletlenszerű napkitörésekben és aszteroida felrobbanásokban halhatnak meg. Ha napkitörés ér egy aszteroidát, akkor meghalnak rajta a telepesek, ha csak nem üreges az aszteroida, mert akkor a telepesek elbújnak az üregben, így túlélik a napvihart. Ha radioaktív az aszteroida magja és túl sokáig van kitéve a nap sugárzásának, akkor az felrobban és a telepesek, akik azon tartózkodnak meghalnak.

