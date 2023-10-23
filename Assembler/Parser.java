package Assembler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;


public class Parser {
  private BufferedReader reader;
  private String currentLine;
  private String nextLine;

  public Parser(File file) throws IOException {
    if(file == null) {
      throw new NullPointerException("file");
    } 
    else if(!file.exists()) {
      throw new FileNotFoundException(file.getAbsolutePath());
    } 
    else {
      this.reader = new BufferedReader(new FileReader(file));
      this.currentLine = null;
      this.nextLine = this.getNextLine();
    }
  }

  private String getNextLine() throws IOException {
    String nextLine;
    do {
      nextLine = this.reader.readLine();

      if(nextLine == null) {
        return null;
      }

    } while(nextLine.trim().isEmpty() || this.checkComment(nextLine));
    
    int commentIndex = nextLine.indexOf("//");

    if(commentIndex != -1) {
      nextLine = nextLine.substring(0, commentIndex - 1);
    }

    return nextLine;
  }

  private boolean checkComment(String input) {
    return input.trim().startsWith("//");
  }

  @Override
	public void finalize() {
		this.close();
	}

  public void close() {
		try {
			this.reader.close();
		} catch(IOException e) {
		// do nothing
		}
	}

  public boolean hasMoreCommands() {
    return (this.nextLine != null);
  }

  public void advance() throws IOException {
		this.currentLine = this.nextLine;
		this.nextLine = this.getNextLine();
	}

  public CommandType commandType() {
    String line = this.currentLine.trim();
		
		if(line.startsWith("(") && line.endsWith(")")) {
			return CommandType.L_COMMAND;
		} 
    else if(line.startsWith("@")) {
			return CommandType.A_COMMAND;
		} 
    else {
			return CommandType.C_COMMAND;
		}
  }

  public String symbol() {
    String trimmedLine = this.currentLine.trim();
		
		if(this.commandType().equals(CommandType.L_COMMAND)) {
			return trimmedLine.substring(1, this.currentLine.length() - 1);
		} 
    else if(this.commandType().equals(CommandType.A_COMMAND)) {
			return trimmedLine.substring(1);
		} 
    else {
			return null;
		}
  }

	public String dest() {
		String trimmedLine = this.currentLine.trim();
		int destIndex = trimmedLine.indexOf("=");
		
		if(destIndex == -1) {
			return null;
		} else {
			return trimmedLine.substring(0, destIndex);
		}
	}

  public String comp() {
		String trimmedLine = this.currentLine.trim();
		int destIndex = trimmedLine.indexOf("=");

		if(destIndex != -1) {
			trimmedLine = trimmedLine.substring(destIndex + 1);
		}
		int compIndex = trimmedLine.indexOf(";");
		
		if(compIndex == -1) {
			return trimmedLine;
		} 
    else {
			return trimmedLine.substring(0, compIndex);
		}
	}

  public String jump() {
		String trimmedLine = this.currentLine.trim();
		int compIndex = trimmedLine.indexOf(";");
		
		if(compIndex == -1) {
			return null;
		} 
    else {
			return trimmedLine.substring(compIndex + 1);
		}
	}
}