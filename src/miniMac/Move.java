package miniMac;

public class Move implements Instruction{

    private int src;
    private int dest;
    public Move(int src, int dest){
        this.src = src;
        this.dest = dest;
    }
    public void execute(MiniMac mac) {
        Integer[] memory = mac.memory;
        memory[dest] = memory[src];
    }
}



