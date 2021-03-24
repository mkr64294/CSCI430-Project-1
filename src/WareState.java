public abstract class WareState {
    protected static WareContext context =  WareContext.instance();
    protected static Warehouse warehouse = new Warehouse();;
    protected WareState() {
      //context = WareContext.instance();
      //warehouse = new Warehouse();
    }
    public abstract void run();
  }
  