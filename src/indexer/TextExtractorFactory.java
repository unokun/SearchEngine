package indexer;

import java.io.File;

public class TextExtractorFactory {
	private static final String EXT_TEXT = "txt";
	
	public static TextExtractor create(String file) {
		return create(new File(file));
	}
	public static TextExtractor create(File file) {
		String ext = getExt(file.getName());
		switch (ext) {
		case EXT_TEXT:
			return new PlainTextExtractor();
		default:
			return new PlainTextExtractor();
		}
	}
	
	/**
	 * ファイル拡張子を取得します
	 * 
	 * @param fileName
	 * @return
	 */
	static String getExt(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index == -1) { return ""; }
		return fileName.substring(index + 1, fileName.length());
		
	}
}
