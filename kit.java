package kit;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Kit Parser
 * 
 * @version 0.1
 * @author Ji
 * @since 2018-09-08
 */
public class Kit {
	
	private String codeTitle;
	private String content;
	private String language;
	private ArrayList<String> pasredCode;
	private KitData kitData;
	
	public Kit() {
		
	}
	
	/**
	 * Parsing Source text to KitData Object
	 * @param sourceText
	 */
	public Kit(String sourceText) {
		kitParser(sourceText);
	}
	
	/**
	 * Parsing Source file to KitData Object
	 * @param source
	 */
	public Kit(File source) {

		String sourceText = fileToString(source);
		kitParser(sourceText);

	}
	
	
	private String fileToString(File source) {
		
		String filePath = source.getAbsolutePath();
	    StringBuilder contentBuilder = new StringBuilder();
	    try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) {
	    	
	        stream.forEach(s -> contentBuilder.append(s).append("\n"));
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	    return contentBuilder.toString();
	}
	
	private void kitParser(String sourceText) {
		boolean codeContinue = false;
		String code = "";
		pasredCode = new ArrayList<>();
		
		String[] splitStr = sourceText.split("\n");
		for(String s : splitStr) {
			//split kit tags 
			String[] kitbits = s.split("@kit:");
			if(kitbits.length>1) {
				//identify kit tags and put the values
				String kitbit = kitbits[kitbits.length-1];
				System.out.println("[kit]:" + kitbit);
				String[] kitatom = kitbit.split(":");
				switch(kitatom[0].trim()) {
				case "title":
					codeTitle = "";
					for(int i=1;i<kitatom.length;i++) {
						codeTitle +=kitatom[i].trim();
					}
					break;
				case "language":
					language ="";
					for(int i=1;i<kitatom.length;i++) {
						language +=kitatom[i].trim();
					}
					break;
				case "content":
					content = "";
					for(int i=1;i<kitatom.length;i++) {
						content +=kitatom[i].trim();
					}
					break;
				case "start":
					codeContinue = true;
					break;
				case "end":
					//code variable clean
					if(!code.equals("")) {
						codeContinue =false;
						pasredCode.add(code);
						code = "";
					}
					break;
				default : break;
					
				}
			} else {
//				System.out.println(kitbits[0]);
				//non kits, store in codes
				if(codeContinue) {
					if(kitbits[0].length()>0) {
						code += kitbits[0];
						code +="\n";
					}
				} 
			}
			
			//put parsed data in KitData
			kitData = new KitData();
			kitData.setCodeTitle(codeTitle);
			kitData.setLanguage(language);
			kitData.setContent(content);
			kitData.setPasredCode(pasredCode);
			
		}
		
	}

	/**
	 * get Kit data
	 * @return KitData type 
	 */
	public KitData getKitData() {
		return kitData;
	}

	
}