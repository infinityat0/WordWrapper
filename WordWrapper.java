import java.io.File;
import java.io.PrintWriter;
import java.text.BreakIterator;
import java.util.Scanner;

public class WordWrapper {
	
	// Configure this wrap length to any (sane) value.
	private static final int WRAP_WIDTH = 50 ;
	private static final String LINE_SEPERATOR = System.getProperty("line.separator");
	
	/**
	 * This method takes 2 files and reads the input file and creates(and overrides an existing)
	 * output file that is a word-wrapped version of the contents of input file.  
	 * @param iInputFilePath
	 * @param iOutputFilePath
	 */
	public void wrapFile(final String iInputFilePath, final String iOutputFilePath) {
		try{
			final Scanner aLineScanner = new Scanner(new File(iInputFilePath));
			final PrintWriter aFileWriter = new PrintWriter(new File(iOutputFilePath));
			// Not caring about thread safety. 
			final StringBuilder aOutputStringBuilder = new StringBuilder();
			String line ;
			while (aLineScanner.hasNextLine()) {
				line = aLineScanner.nextLine() ;
				BreakIterator wordIterator = BreakIterator.getWordInstance();
				wordIterator.setText(line);
				int start = wordIterator.first(), end, mark = 0;
				while ((end = wordIterator.next()) != BreakIterator.DONE) {
					if (end - mark > WRAP_WIDTH) {
						aOutputStringBuilder.append(LINE_SEPERATOR);
						mark = start ;
					}
					aOutputStringBuilder.append(line.substring(start, end));
					start = end ;
				}
				aOutputStringBuilder.append(LINE_SEPERATOR);
			}
			aFileWriter.write(aOutputStringBuilder.toString());
			aFileWriter.close();
			aLineScanner.close();
		}
		catch (Exception ex) {
			System.err.println("Oops! Something went wrong");
		}
	}
	
	/**
	 * Main method that accepts 2 arguments. If not, it explicitly asks for them. 
	 * @param args
	 */
	public static void main(String [] args) {
		if (args.length != 2) {
			args = usage();
		}
		new WordWrapper().wrapFile(args[0], args[1]);
	}

	/**
	 * Usage method. Asks user for the paths of the input and output files. 
	 * @return
	 */
	private static String[] usage() {
		String [] aReturnVal = new String[2];
		System.out.println("usage - Wordwrapper [inputFilePath] [outputFilePath]");
		System.out.println("Example - Wordwrapper /Users/xyz/input.txt /Users/xyz/output.txt");
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the input file Path :");
		aReturnVal[0] = scanner.nextLine();
		System.out.print("Enter the output file Path :");
		aReturnVal[1] = scanner.nextLine();
		scanner.close();
		return aReturnVal;
	}
}
