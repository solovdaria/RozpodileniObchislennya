import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


public class Airport {
    private final static int TERMINAL_AMOUNT = 1;
    private final static int GATES_AMOUNT=2;
    private final Semaphore semaphore = new Semaphore(TERMINAL_AMOUNT*GATES_AMOUNT, true);
    private final ArrayList<Gate> gates=new ArrayList<Gate>();
    public Airport() {
        for (int i=0; i<TERMINAL_AMOUNT; i++){
            for (int j=0; j<GATES_AMOUNT; j++){
                gates.add(new Gate(i,j));
            }
        }
    }

    public Gate getGate(long maxWaitMillis) throws ResourсeException {
        try {
            if (semaphore.tryAcquire(maxWaitMillis, TimeUnit.MILLISECONDS)) {
                for (int i=0; i<gates.size(); i++){
                    if (gates.get(i).isFree()){
                        gates.get(i).occupy();
                        return gates.get(i);
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new ResourсeException(e);
        }
        throw new ResourсeException(": timeout exceeded\n");
    }

    public void returnGate(Gate gate) {
        gate.release();
        semaphore.release();
    }
}