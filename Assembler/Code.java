package Assembler;

import java.util.HashMap;

public class Code {
  private HashMap<String, String> destTable;
  private HashMap<String, String> compTable;
  private HashMap<String, String> jumpTable;

  public Code() {
    destTable = new HashMap<String, String>();
    compTable = new HashMap<String, String>();
    jumpTable = new HashMap<String, String>();

    // dest table
		destEntry("NULL", "000");
		destEntry("M", "001");
		destEntry("D", "010");
		destEntry("MD", "011");
		destEntry("A", "100");
		destEntry("AM", "101");
		destEntry("AD", "110");
		destEntry("AMD", "111");

    // comp table
		compEntry("0", "0101010");
		compEntry("1", "0111111");
		compEntry("-1", "0111010");
		compEntry("D", "0001100");
		compEntry("A", "0110000");
		compEntry("M", "1110000");
		compEntry("!D", "0001101");
		compEntry("!A", "0110001");
		compEntry("!M", "1110001");
		compEntry("-D", "0001111");
		compEntry("-A", "0110011");
		compEntry("-M", "1110011");
		compEntry("D+1", "0011111");
		compEntry("A+1", "0110111");
		compEntry("M+1", "1110111");
		compEntry("D-1", "0001110");
		compEntry("A-1", "0110010");
		compEntry("M-1", "1110010");
		compEntry("D+A", "0000010");
		compEntry("D+M", "1000010");
		compEntry("D-A", "0010011");
		compEntry("D-M", "1010011");
		compEntry("A-D", "0000111");
		compEntry("M-D", "1000111");
		compEntry("D&A", "0000000");
		compEntry("D&M", "1000000");
		compEntry("D|A", "0010101");
		compEntry("D|M", "1010101");
    
    // jump table
    jumpEntry("NULL", "000");
    jumpEntry("JGT", "001");
    jumpEntry("JEQ", "010");
    jumpEntry("JGE", "011");
    jumpEntry("JLT", "100");
    jumpEntry("JNE", "101");
    jumpEntry("JLE", "110");
    jumpEntry("JMP", "111");

  }

  public void destEntry(String key, String val) {
    destTable.put(key, val);
  }

  public void compEntry(String key, String val) {
    compTable.put(key, val);
  }

  public void jumpEntry(String key, String val) {
    jumpTable.put(key, val);
  }

  public String dest(String mnemonic) {
    if(mnemonic == null || mnemonic.isEmpty()) {
      mnemonic = "NULL";
    }
    return this.destTable.get(mnemonic);
  }

  public String comp(String mnemonic) {
    return this.compTable.get(mnemonic);
  }

  public String jump(String mnemonic) {
    if(mnemonic == null || mnemonic.isEmpty()) {
      mnemonic = "NULL";
    }
    return this.jumpTable.get(mnemonic);
  }

  public String changeToBinary(String number) {
		int value = Integer.parseInt(number);
		String binaryNum = Integer.toBinaryString(value);
		String newBinaryNum = 
      String.format("%15s", binaryNum).replace(' ', '0');
		return newBinaryNum;
	}
}
