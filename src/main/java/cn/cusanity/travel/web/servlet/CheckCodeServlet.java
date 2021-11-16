package cn.cusanity.travel.web.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Verification code servlet
 */
@WebServlet("/checkCodes")
public class CheckCodeServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException {
		
		//No cache for checkcodes
		response.setHeader("pragma","no-cache");
		response.setHeader("cache-control","no-cache");
		response.setHeader("expires","0");

		int width = 80;
		int height = 30;
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		
		Graphics g = image.getGraphics();
		g.setColor(Color.GRAY);
		g.fillRect(0,0, width,height);
		
		String checkCode = getCheckCode();
		//put the verification code in the session
		request.getSession().setAttribute("CHECKCODE_SERVER",checkCode);
		
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Times New Roman",Font.BOLD,24));
		g.drawString(checkCode,15,25);

		ImageIO.write(image,"PNG",response.getOutputStream());
	}
	/**
	 * return 4-digits string containing letters/numbers
	 */
	private String getCheckCode() {
		String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		int size = base.length();
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i <= 4;  i++){
			int index = r.nextInt(size);
			char c = base.charAt(index);
			sb.append(c);
		}
		return sb.toString();
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request,response);
	}
}



