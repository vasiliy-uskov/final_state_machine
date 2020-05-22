package lexer;

import lexer.parser.IStringProvider;

import java.io.InputStream;
import java.util.*;

class SafeStringProvider implements IStringProvider {
    private Scanner input;
    private List<String> cachedLines = new LinkedList<>();
    private int lineIndex = 0;
    private int position = 0;
    private ListIterator<String> cacheIterator = cachedLines.listIterator();

    SafeStringProvider(InputStream input) {
        this.input = new Scanner(input);
    }

    int lineIndex() {
        return lineIndex;
    }

    int position() {
        return position;
    }

    public String readLine() {
        if (!cacheIterator.hasNext())
        {
            String newLine = getNewLine();
            if (newLine == null) {
                return null;
            }
            cacheIterator.add(newLine);
            return newLine;
        }
        return cacheIterator.next();
    }

    void forget(int charsCount) {
        ListIterator<String> iterator = cachedLines.listIterator();
        while (charsCount != 0 && iterator.hasNext()) {
            String currentLine = iterator.next();
            if (charsCount >= currentLine.length()) {
                charsCount = charsCount - currentLine.length() - "\n".length();
                lineIndex++;
                position = 0;
                iterator.remove();
            }
            else {
                position += charsCount;
                iterator.set(currentLine.substring(charsCount));
                charsCount = 0;
            }
        }
        restore();
    }

    void restore() {
        cacheIterator = cachedLines.listIterator();
    }

    private String getNewLine() {
        if (input.hasNextLine()) {
            return input.nextLine();
        }
        return null;
    }
}
