package Data;

public class FramesPerSecond {
    // fps is short for FramesPerSecond
    private int pfs;

    public FramesPerSecond() {
        this.pfs = 0;
    }

    public int getPfs() {
        return pfs;
    }

    public void update(double deltatime) {
        this.pfs = (int) (1 / deltatime);
    }
}