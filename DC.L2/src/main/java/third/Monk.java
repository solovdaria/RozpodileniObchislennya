package third;

public class Monk {
    enum Monastery {
        Guan_Yin, Guan_Yan
    }

    private Monastery monastery;
    private int qiEnergy;

    Monk(Monastery monastery, int qiEnergy) {
        this.monastery = monastery;
        this.qiEnergy = qiEnergy;
    }

    public int getQiEnergy() {
        return qiEnergy;
    }

    @Override
    public String toString() {
        return "monk from" + " monastery "
                + (monastery == Monastery.Guan_Yin ? "Guan_Yin" : "Guan_Yan")
                + ", " + qiEnergy + " energy ";
    }
}
