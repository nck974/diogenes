package dev.nichoko.diogenes.utils;

import java.util.Arrays;

public class FileNameCleaner {
    static final int[] illegalChars = { 34, 60, 62, 124, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 58, 42, 63, 92, 47, 32 };

    static {
        Arrays.sort(illegalChars);
    }

    private FileNameCleaner() {
    }

    /**
     * 
     * This method was copied from:
     * https://stackoverflow.com/questions/1155107/is-there-a-cross-platform-java-method-to-remove-filename-special-chars
     * 
     * @param badFileName
     * @return
     */
    public static String cleanFileName(String badFileName) {
        StringBuilder cleanName = new StringBuilder();
        int len = badFileName.codePointCount(0, badFileName.length());
        for (int i = 0; i < len; i++) {
            int c = badFileName.codePointAt(i);
            if (Arrays.binarySearch(illegalChars, c) < 0) {
                cleanName.appendCodePoint(c);
            }
        }
        return cleanName.toString();
    }
}