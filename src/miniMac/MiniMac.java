package miniMac;

import tools.Publisher;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MiniMac extends Publisher implements Serializable {
    int ip;
    boolean halt;
    int size;
    Integer[] memory;
    List<Instruction> instructions;

    public MiniMac(){
        ip = 0;
        halt = false;
        size = 32;
        memory = new Integer[size];
        for (int i = 0; i < size; i++) {
            memory[i] = 0;
        }
    }

    public void execute() throws Exception {
        ip = 0;
//        String program = new String(Files.readAllBytes(Paths.get(filename)));
//        instructions = ParserUtils.MiniMacParser.parse(program);
        while (ip < instructions.size())
        {
            instructions.get(ip).execute(this);
            ip++;
        }
        notifySubscribers();
    };
    public void clear(){
        ip = 0;
        for (int i = 0; i < size; i++) {
            memory[i] = 0;
        }
        notifySubscribers();
    };
}
