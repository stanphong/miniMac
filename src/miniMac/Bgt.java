package miniMac;
public class Bgt implements Instruction{
    private int location;
    private int offset;
    public Bgt(int location, int offset){
        this.location = location;
        this.offset = offset;
    }

    public void execute(MiniMac mac) {
        if (mac.memory[location] > 0){
            mac.ip += offset;
        }
    }
}
