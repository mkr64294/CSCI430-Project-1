public abstract class WareState {
    protected static WareContext context;
    protected static Warehouse warehouse;
    protected WareState() {
      //context = WareContext.instance();
      warehouse = new Warehouse();
    }
    public abstract void run();
  }
  