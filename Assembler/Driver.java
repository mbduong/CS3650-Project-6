package Assembler;

import java.io.IOException;
import java.io.StringWriter;
import java.io.File;

public class Driver {
  public static void main(String[] args) {
    if (args.length == 0) {
			System.err.println("No source file provided.");
			System.exit(1);
		}
		
		File sourceFile = new File(args[0].trim());
		if (!sourceFile.exists()) {
			System.err.println("Source file could not be found.");
			System.exit(2);
		}
		// get output file
		String sourceAbsolutePath = sourceFile.getAbsolutePath();
		String fileName = sourceFile.getName();
		int fileNameExtensionIndex = fileName.lastIndexOf(".");
		String fileNameNoExtension = fileName.substring(0, fileNameExtensionIndex);
		int fileNameIndex = sourceFile.getAbsolutePath().indexOf(sourceFile.getName());
		String sourceDirectory = sourceAbsolutePath.substring(0, fileNameIndex);
		String outputFilePath = sourceDirectory + fileNameNoExtension + ".hack";
		File outputFile = new File(outputFilePath);
		
		try {
			// create output file
      if (outputFile.exists()) {
        outputFile.delete();
      }
      outputFile.createNewFile();
      
      // translate source file
      Assembler assembler = new Assembler(sourceFile, outputFile);
      assembler.translate();
 
      StringWriter status = new StringWriter();
      status.append("Translation completed successfully.");
      System.out.println(status.toString());
    } 
    catch (IOException e) {
      System.err.println("I/O error occurred.");
      System.exit(3);
  }
}
}
