public class Gate {
    private int gateId;
    private int gateTerminalId;
    private boolean free;

    public Gate(int gateTerminalId, int gateId) {
        this.gateId=gateId+1;
        this.gateTerminalId=gateTerminalId+1;
        this.free=true;
    }

    public int getGateId() {
        return gateId;
    }

    public int getGateTerminalId() {
        return gateTerminalId;
    }

    public boolean isFree(){
        return free;
    }

    public void occupy(){
        this.free=false;
    }

    public void release(){
        this.free=true;
    }

    public void using(int planeCapacity) {
        try {
            Thread.sleep(planeCapacity*5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}