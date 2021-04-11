public abstract class WareStateGUI {
    protected static WareContextGUI context =  WareContextGUI.instance();
    protected static Warehouse warehouse = new Warehouse();;
    protected WareStateGUI() {
      //context = WareContext.instance();
      //warehouse = new Warehouse();
    }
    public abstract void run();
  }
