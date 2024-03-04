package miniMac;

import java.util.List;

public class Block implements Instruction{
    private List<Instruction> list;
    public Block(List<Instruction> list){
        this.list = list;
    }
    public void execute(MiniMac mac) {
        for (Instruction instruction : list)
            instruction.execute(mac);
    }
}
