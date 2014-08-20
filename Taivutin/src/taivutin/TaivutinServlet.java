package taivutin;

import java.io.IOException;
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
		//System.out.println("here");
		//System.out.println(Character.isLetter('ö'));
		
		if (word.length() == 0 || !isAlpha(word)){
			req.setAttribute("error", "Tuo kai ei taivu...");
			req.getRequestDispatcher("/jsp/subadtaivutin.jsp").forward(req, resp);
			return;
		}
		
		//System.out.println(isFront(word));
		word = declineInPartitive(word);
		System.out.println(word);
		req.setAttribute("partitive", "Partitiivi: " + word);
		req.getRequestDispatcher("/jsp/subadtaivutin.jsp").forward(req, resp);
	}
	
	private static boolean isAlpha(String word){
		char[] chars = word.toCharArray();
		
		for (char c : chars)
			if(!Character.isLetter(c))
				return false;
		
		return true;
		
		//return word.matches("[a-zA-Z\00C4\00D6\00E4\00F6]+");
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
		
		//if last letter consonant, return ta/tä
		if(!vowels.contains(word.charAt(word.length()-1))){
			if(isFront(word))
				return word + "tä";
			return word + "ta";
		}
		
		if((!vowels.contains(word.charAt(word.length()-2)) && word.charAt(word.length()-1) != 'e') ||
		   word.substring(word.length()-2, word.length()).equalsIgnoreCase("ia") || 
		   word.substring(word.length()-2, word.length()).equalsIgnoreCase("iä") ||
		   word.substring(word.length()-2, word.length()).equalsIgnoreCase("ea") ||
		   word.substring(word.length()-2, word.length()).equalsIgnoreCase("eä")){
			if(isFront(word))
				return word + "ä";
			return word + "a";
		}
		
		if(isFront(word))
			return word + "ttä";
		return word + "tta";
	}
}
