package miniMac;

public class Load implements Instruction{
    private int location;
    private int value;
    public Load(int location, int value)
    {
        this.location = location;
        this.value = value;
    }
    public void execute(MiniMac mac) {
        Integer[] memory = mac.memory;
        memory[location] = value;
    }
}
