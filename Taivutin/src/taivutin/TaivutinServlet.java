package taivutin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@SuppressWarnings("serial")
public class TaivutinServlet extends HttpServlet {
	//the vowels in the Finnish language 3 back + 3 front + 2 neutral
	private static ArrayList<Character> vowels = new ArrayList<Character>(Arrays.asList('a', 'o', 'u', 'ä', 'ö', 'y', 'e', 'i'));
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher("/jsp/subadtaivutin.jsp").forward(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		
		resp.setContentType("text/html;charset=UTF-8");
		String word = req.getParameter("word");

		//first check if input is valid
		if (word.length() == 0 || !isAlpha(word)){
			req.setAttribute("error", "Tuo kai ei taivu...");
			req.getRequestDispatcher("/jsp/subadtaivutin.jsp").forward(req, resp);
			return;
		}
		String partitive = "";
		try{
			URL url = new URL(("http://vasilev.users.cs.helsinki.fi/lookup.php?word=" + word));
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			String line;
			
			while ((line = reader.readLine()) != null)
				if(line.contains("Partitiivi: "))
					partitive = line.substring(12);
			
			reader.close();
			if(partitive.length() > 2){
				req.setAttribute("partitive", "Partitiivi: " + partitive);
				req.getRequestDispatcher("/jsp/subadtaivutin.jsp").forward(req, resp);
				return;
			}			
		}
		
		catch(MalformedURLException e){
			e.printStackTrace();
		}
		
		catch(IOException e){
			e.printStackTrace();
		}		
		
		//System.out.println(isFront(word));
		partitive = declineInPartitive(word);
		System.out.println(partitive);
		req.setAttribute("partitive", "Partitiivi: " + partitive);
		req.getRequestDispatcher("/jsp/subadtaivutin.jsp").forward(req, resp);
	}
	
	private static boolean isAlpha(String word){
		char[] chars = word.toCharArray();
		
		for (char c : chars)
			if(!Character.isLetter(c))
				return false;
		
		return true;
	}
	
	private static boolean isFront(String word){
		int length = word.length();
		
		while(length > 0){
			char c = word.charAt(length-1);
			if (c == 'a' || c == 'o' || c == 'u')
				return false;
			
			if (c == 'ä' || c == 'ö' || c == 'y')
				return true;
			length--;
		}
		return true;
	}
	
	private static String declineInPartitive(String word){
		int wordLength = word.length();
		//if word ends in -nen, use follow -nen pattern
		if(wordLength > 3 && word.substring(wordLength-3, wordLength).equalsIgnoreCase("nen")){
			if(isFront(word))
				return word.substring(0, wordLength - 3) + "s" + "tä";
			return word.substring(0, wordLength - 3) + "s" + "ta";
		}
		//if last letter consonant, return ta/tä
		if(!vowels.contains(word.charAt(wordLength-1))){
			if(isFront(word))
				return word + "tä";
			return word + "ta";
		}
		
		if((!vowels.contains(word.charAt(wordLength-2)) && word.charAt(wordLength-1) != 'e') ||
		   word.substring(wordLength-2, wordLength).equalsIgnoreCase("ia") || 
		   word.substring(wordLength-2, wordLength).equalsIgnoreCase("iä") ||
		   word.substring(wordLength-2, wordLength).equalsIgnoreCase("ea") ||
		   word.substring(wordLength-2, wordLength).equalsIgnoreCase("eä")){
			if(isFront(word))
				return word + "ä";
			return word + "a";
		}
		if(vowels.contains(word.charAt(wordLength-2))){
			if(isFront(word))
				return word + "tä";
			return word + "ta";
		 }
		if(isFront(word))
			return word + "ttä";
		return word + "tta";
	}
}
