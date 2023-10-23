package Assembler;

import java.util.Hashtable;

public class SymbolTable {
	private static final int MEM_STARTING_ADDRESS = 16;
	private static final int MEM_ENDING_ADDRESS = 16384;
	private static final int PROGRAM_STARTING_ADDRESS = 0;
	private static final int PROGRAM_ENDING_ADDRESS = 32767;

  private Hashtable<String, Integer> symbolAddr;
  private int programAddr;
  private int memAddr;

  public SymbolTable() {
    this.symbolAddr = new Hashtable<String, Integer>();
		this.symbolAddr.put("SP", Integer.valueOf(0));
		this.symbolAddr.put("LCL", Integer.valueOf(1));
		this.symbolAddr.put("ARG", Integer.valueOf(2));
		this.symbolAddr.put("THIS", Integer.valueOf(3));
		this.symbolAddr.put("THAT", Integer.valueOf(4));
		this.symbolAddr.put("R0", Integer.valueOf(0));
		this.symbolAddr.put("R1", Integer.valueOf(1));
		this.symbolAddr.put("R2", Integer.valueOf(2));
		this.symbolAddr.put("R3", Integer.valueOf(3));
		this.symbolAddr.put("R4", Integer.valueOf(4));
		this.symbolAddr.put("R5", Integer.valueOf(5));
		this.symbolAddr.put("R6", Integer.valueOf(6));
		this.symbolAddr.put("R7", Integer.valueOf(7));
		this.symbolAddr.put("R8", Integer.valueOf(8));
		this.symbolAddr.put("R9", Integer.valueOf(9));
		this.symbolAddr.put("R10", Integer.valueOf(10));
		this.symbolAddr.put("R11", Integer.valueOf(11));
		this.symbolAddr.put("R12", Integer.valueOf(12));
		this.symbolAddr.put("R13", Integer.valueOf(13));
		this.symbolAddr.put("R14", Integer.valueOf(14));
		this.symbolAddr.put("R15", Integer.valueOf(15));
		this.symbolAddr.put("SCREEN", Integer.valueOf(16384));
		this.symbolAddr.put("KBD", Integer.valueOf(24576));
    this.programAddr = PROGRAM_STARTING_ADDRESS;
    this.memAddr = MEM_STARTING_ADDRESS;
  }

  public void addEntry(String symbol, int address) {
    this.symbolAddr.put(symbol, Integer.valueOf(address));
  }

  public int getAddress(String symbol) {
    return this.symbolAddr.get(symbol);
  }

  public boolean contains(String symbol) {
    return this.symbolAddr.containsKey(symbol);
  }

  public void incrementProgramAddr() {
    this.programAddr++;
    if(this.programAddr > PROGRAM_ENDING_ADDRESS) {
      throw new RuntimeException();
    }
  }

  public void incrementMemAddr() {
    this.memAddr++;
    if(this.memAddr > MEM_ENDING_ADDRESS) {
      throw new RuntimeException();
    }
  }

  public int getProgramAddress() {
    return this.programAddr;
  }

  public int getMemAddr() {
    return this.memAddr;
  }
}
