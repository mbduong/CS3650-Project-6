package Assembler;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.StringWriter;

public class Assembler {
  private File assemblyCode;
  private BufferedWriter machineCode;
  private Code encoder;
  private SymbolTable symbolTable;

  public Assembler(File source, File target) throws IOException {
    this.assemblyCode = source;
    FileWriter fw = new FileWriter(target);
    this.machineCode = new BufferedWriter(fw);
    this.encoder = new Code();
    this.symbolTable = new SymbolTable();
  }

  public void translate() throws IOException{
    this.updateLabelAddr();
    this.parse();
  }

  private void updateLabelAddr() throws IOException {
    Parser parser = new Parser(this.assemblyCode);
    while(parser.hasMoreCommands()) {
      parser.advance();
      CommandType commandType = parser.commandType();
      if(commandType.equals(CommandType.L_COMMAND)) {
        String symbol = parser.symbol();
        int address = this.symbolTable.getProgramAddress();
        this.symbolTable.addEntry(symbol, address);
      }
      else {
        this.symbolTable.incrementProgramAddr();
      }
    }
    parser.close();
  }

  private void parse() throws IOException {
    Parser parser = new Parser(this.assemblyCode);
    while(parser.hasMoreCommands()) {
      parser.advance();
      CommandType commandType = parser.commandType();
      String instr = null;

      if(commandType.equals(CommandType.A_COMMAND)) {
        String symbol = parser.symbol();
        Character firstChar = symbol.charAt(0);
        boolean isSymbol = (!Character.isDigit(firstChar));
        String addr = null;
        if(isSymbol) {
          boolean symbolExists = this.symbolTable.contains(symbol);
          if(!symbolExists) {
            int symAddr = this.symbolTable.getMemAddr();
            this.symbolTable.addEntry(symbol, symAddr);
            this.symbolTable.incrementMemAddr();
          }
          addr = Integer.toString(this.symbolTable.getAddress(symbol));
        }
        else {
          addr = symbol;
        }
        instr = this.formatAInstr(addr);
      }
      else if(commandType.equals(CommandType.C_COMMAND)) {
        String comp = parser.comp();
				String dest = parser.dest();
				String jump = parser.jump();
        instr = this.formatCInstr(comp, dest, jump);
      }
      if(!commandType.equals(CommandType.L_COMMAND)) {
        this.machineCode.write(instr);
        this.machineCode.newLine();
      }
    }
    parser.close();
    this.machineCode.flush();
    this.machineCode.close();
  }

  private String formatAInstr(String address) {
    String number = this.encoder.changeToBinary(address);
    return "0" + number;
  }
  
  private String formatCInstr(String comp, String dest, String jump) {
    StringWriter instr = new StringWriter();
    instr.append("111");
    instr.append(this.encoder.comp(comp));
    instr.append(this.encoder.dest(dest));
    instr.append(this.encoder.jump(jump));
    return instr.toString();
  }
}
