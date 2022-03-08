package model;

import controls.Controller;
import view.Drawable;
import view.SettlerDraw;

import java.io.Serializable;
import java.util.*;

/**
 * Ez az osztaly kepes nyersanyagokat tarolni. Mozogni aszteroidak kozott,
 * szomszedrol szomszedra, vagy teleportkapukat hasznalva. Kepes az aszteroidak
 * kergen vekonyitani es az aszteroidakbol a nyersanyagot kinyerni, vagy akar azokat
 * visszahelyezni egy üres aszteroidaba. Kepes teleportkapukat letrehozni es azokat
 * lehelyezni.
 */
public class Settler extends Worker implements Serializable {
    /**
     * nala levo nyersanyagok listaja
     */
    private  final ArrayList<Material> materials= new ArrayList<>();

    /**
     * nala levo teleportkapuk
     */
    private Deque<TeleportGate> gates = new ArrayDeque<>(3);
    /**
     * azonosito
     */
    public static int ID = 0;
    private String id;
    private transient SettlerDraw myDraw = null ;

    private int no;

    /**
     * getter
     * @return
     */
    public int getNo() {
        return no;
    }

    /**
     * konstruktor
     * @param a
     * @param number
     */
    public Settler(Asteroid a, int number){
        no = number;
        myDraw = new SettlerDraw(this);

        id = "set"+ ++ID;
        Game.getGame().addSettler();
        Controller.getController().addSettler(this);
        a.moveTo(this);
        Game.getGame().addSettler(this);
    }

    /**
     * setterek, getterek
     * @return
     */

    public Drawable getDrawable(){
        if(myDraw == null)
            myDraw = new SettlerDraw(this);
        return myDraw;
    }

    public Deque<TeleportGate> getGates(){
        return gates;
    }

    public void addTeleportGate(TeleportGate tpg){
        gates.add(tpg);
    }

    public String getId() {
        return id;
    }
    public void addMaterial(Material m) { materials.add(m); }


    /**
     * Az atfurt aszteroidaba, ami üres letesz egy nyersanyagot ami nala van
     * @param m - a tarolando nyersanyag
     */
    public void storeMaterial(Material m){
        if(myAst.getCore() == null){
            materials.remove(m);
            m.setAsteroid(myAst);
            myAst.setCore(m);
            Controller.getController().goNext();
        }

    }

    /**
     * Az atfurt aszteroidabol kiveszi a nyersanyagot
     */
    public void mine(){
        if(materials.size() < 10) {
            Material mat = myAst.emptiesAsteroid();
            if(mat != null) {
                addMaterial(mat);
                return;
            }
        }
    }

    /**
     * Kifizet egy Billt, ezzel leveszi majd magatol a nyersanyagokat
     * @param bill - a kifizetendo   model.Bill.
     */
    private void payBill(Bill bill){
        //amelyik materialokat ki akarod venni azt tedd be egy tmp_listaba , es a for loop utan a tmp listaban levo dolgokat removeold a material listabol -Marci
        int co = bill.getCoal();
        int ir = bill.getIron();
        int ic = bill.getIce();
        int ur = bill.getUran();

        ArrayList<Material> tmp_list = new ArrayList<>();

        for(Material m : materials){
            if(co != 0 && m.isEqual(new Coal(null))){
                co--;
                tmp_list.add(m);
            }
            if(ir != 0 && m.isEqual(new Iron(null))){
                ir--;
                tmp_list.add(m);
            }
            if(ic != 0 && m.isEqual(new Ice(null))){
                ic--;
                tmp_list.add(m);
            }
            if(ur != 0 && m.isEqual(new Uran(null))){
                ur--;
                tmp_list.add(m);
            }
        }
        materials.removeAll(tmp_list);
    }


    public void removeFirstTeleportGate(){
        gates.removeFirst();
    }

    /**
     * Ha van eleg nyersanyaga ra, craftol egy teleportkapupart a nala levokbol.
     * Azt hogy van-e eleg nala, az   model.AllBills osztaly ellenorzi.
     * Az uj teleportkaputpart elraktarozza magahoz.
     */
    public boolean createTeleport(){


        Bill tpBill = AllBills.getTpBill();

        boolean hasEnough = AllBills.hasEnough(tpBill, materials);

        if(gates.size() > 1){

        }
        else {
            if (hasEnough) {
                payBill(AllBills.getTpBill());

                TeleportGate gate1 = new TeleportGate(this);
                TeleportGate gate2 = new TeleportGate(this);
                gate1.setPair(gate2);
                gate2.setPair(gate1);

                gates.add(gate1);
                gates.add(gate2);
            } else {

            }
        }
        return hasEnough;
    }

    /**
     * TP. Kapu lehelyezese
     */
    public void placeTeleport() {
        if (gates.size() > 0) {
            TeleportGate placed = gates.pop();
            placed.setMyAst(myAst);
            myAst.addTeleportGate(placed);
            Game.getGame().addSteppable(placed);
            Controller.getController().getGameScene().getDrawables().add(placed.getDrawable());
        }
    }

    /**
     * Ha van eleg nyersanyaga ra, craftol egy robotot a nala levokbol.
     * Azt hogy van-e eleg nala az   model.AllBills osztaly ellenorzi.
     * Jelkepesen kifizeti a telepes a robot arat
     * Az uj robotot az aktualis aszteroidara teszi le
     */
    public Robot createRobot(){
        Bill robotBill = AllBills.getRobotBill();
        boolean hasEnough = AllBills.hasEnough(robotBill, this.materials);
        if(hasEnough){
            payBill(AllBills.getRobotBill());
            Robot ujrobot = new Robot(myAst);
            return ujrobot;
        }
        return null;
    }

    /**
     * A telepes felrobban. A   model.Worker kaboom() függvenyet ez az osztaly ugy valositja meg, hogy meghal.gec
     */
    @Override
    public void kaboom(){
        die();
    }

    /**
     * Meghal a telepes, egyetlen felelossege a függvenynek hogy levegye ot az aszteroidarol es ertesitse a jatekot errol
     */
    @Override
    public void die() {
        myAst.removeBeing(this);
        Game.getGame().settlerDied(this);
    }

    /**
    Visszater a telepesnel levo nyersanyagokkal
    */
    @Override
    public ArrayList<Material> getMaterials() {
        return materials;
    }


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(id+"[") ;
        for(int i = 0; i < materials.size(); i++){
            if(i != materials.size()-1)
                sb.append(materials.get(i).toString()).append(", ");
            else{
                sb.append(materials.get(i).toString());
            }
        }
        if(materials.size()>0 && gates.size()>0)
            sb.append(", ");

        if(gates.size() != 0) {
            Iterator<TeleportGate> QIterator = gates.iterator();
            int counter = 0;
            while (QIterator.hasNext() && counter != gates.size() - 1) {
                sb.append(QIterator.next().toString()).append(", ");
                counter++;
            }
            sb.append(QIterator.next().toString());
        }
        sb.append("]");
        return sb.toString();

    }


}
