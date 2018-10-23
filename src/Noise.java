import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;	
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;

public class Noise {	
	static BufferedImage image;
	static JFileChooser gf;
	static JFrame fl;
	static Color ac1;
	static Color ac2;
	static Color ac3;
	static Color ac4;
	static Color ac5;
	static Color ac6;
	static Color ac7;
	static Color ac8;
	static int width;
	static int height;
	
	static BufferedImage PartialGreyScale(BufferedImage image) {
		
		return image;
	}
	
	static BufferedImage MakeGreyScale(BufferedImage image) {
		for(int i = 1; i<height-1;i++) {
			for(int j = 1;j<width-1;j++) {
				Color c = new Color(image.getRGB(j, i));
				int R = c.getRed(),B=c.getBlue(),G=c.getGreen();
				int avg = (R+B+G)/3;
				Color newColor = new Color(avg,avg,avg);
				image.setRGB(j, i, newColor.getRGB());
			}
		}
		return image;
	}
	
	static BufferedImage RemoveNoise(BufferedImage image) {
		int nos = 0;
		for(int k=0;k<2;k++){
			Color t = new Color(nos,nos,nos);
			for(nos=1;nos<=255;nos+=127){			//HISTORY: for(nos=0;nos<=255;nos++)
//				System.out.println(nos);
		for(int i = 1; i<height-1;i++) {
			for(int j = 1;j<width-1;j++) {
				Color c = new Color(image.getRGB(j, i));
				if(c.getRGB()==t.getRGB()) {	//HISTORY c.getRed()==nR && c.getBlue()==nB && c.getGreen()==nG
					try {
					 ac1 = new Color(image.getRGB(j+1, i-1));
					 ac2 = new Color(image.getRGB(j+1, i));
					 ac3 = new Color(image.getRGB(j+1, i+1));
					 ac4 = new Color(image.getRGB(j, i-1));
					 ac5 = new Color(image.getRGB(j, i+1));
					 ac6 = new Color(image.getRGB(j-1, i-1));
					 ac7 = new Color(image.getRGB(j-1, i));
					 ac8 = new Color(image.getRGB(j-1, i+1));
					}catch(Exception ev) {
						
					}
					
					Color[] avgc = new Color[] {ac1,ac2,ac3,ac4,ac5,ac6,ac7,ac8};
					int pR[] = new int[8];
					int pB[] = new int[8];
					int pG[] = new int[8];
					for(int h = 0; h<8;h++) {
						pR[h] = avgc[h].getRed();
						pB[h] = avgc[h].getBlue();
						pG[h] = avgc[h].getGreen();
					}
					for(int m=1;m<8;m++)
					{
						pR[0]=pR[0]+pR[m];
						pB[0]=pB[0]+pB[m];
						pG[0]=pG[0]+pG[m];
					}
					pR[0] = pR[0]/8;
					pB[0] = pB[0]/8;
					pG[0] = pG[0]/8;
					Color newC = new Color(pR[0],pB[0],pG[0]);
					image.setRGB(j, i, newC.getRGB());
					
				}
			}
		}
	}
			nos = 255;
}
		return image;
	}
	
	public static void main(String[] args) throws IOException {
			fl = new JFrame("Image Noise Cancellation Program");
			JFrame vf  = new JFrame("Image Preview");
			JPanel p   = new JPanel();
			JLabel l   = new JLabel();
			JLabel title = new JLabel("                               Image Noise Cancellation program                                ");
			JLabel pro = new JLabel("                                 Project by:                                 ");
			JLabel jas = new JLabel("                                 Jaspreet Singh (16BCS1941)                                ");
			JButton ob = new JButton("Open");
			JButton b  = new JButton("Remove Noise");
			JButton b3 = new JButton("Brighter");
			JButton b4 = new JButton("Darker");
			JButton b5 = new JButton("GreyScale");
			gf = new JFileChooser();
			
			ob.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					gf.setCurrentDirectory(new File(System.getProperty("user.dir")));
					gf.showOpenDialog(null);
					File f = new File(gf.getSelectedFile().toString());
					System.out.println(f);
					try {
						image = ImageIO.read(f);
					} catch (IOException e1) {
					}
					
					 width = image.getWidth();
					 height = image.getHeight();
					ImageIcon ico = new ImageIcon(image);
					l.setIcon(ico);
					
					vf.add(l);
					vf.setBounds(width, height, width, height);
					vf.setLocationRelativeTo(null);
					vf.setDefaultCloseOperation(1);
					vf.setVisible(true);	
				}
			});
			p.add(title);
			p.add(ob);
			p.add(b);
			p.add(b3);
			p.add(b4);
			p.add(b5);
			p.add(pro);
			p.add(jas);
//			p.add(rab);
//			p.add(ujj);
			fl.add(p);
			fl.setVisible(true);
			fl.setBounds(100, 100, 300, 300);
			fl.setLocationRelativeTo(null);
			fl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
//						ImageIO.write(RemoveNoise(image), "png",new File( "temp.png"));
//						backupimg = ImageIO.read(tempf);
//						tempf.isHidden();
						ImageIO.write(RemoveNoise(image), "png",new File( "Result.png"));
//						tempf.deleteOnExit();
					} catch (IOException e2) {
						System.out.println("Please choose an image file!");
					}
					
					ImageIcon ico = new ImageIcon(image);
					l.setIcon(ico);
					
				}
			});
			
			b3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int i = 0; i<height;i++) {
						for(int j = 0;j<width;j++) {
							Color c = new Color(image.getRGB(j, i));
							Color cb = c.brighter();
							int cv = cb.getRGB();
							image.setRGB(j, i, cv);
						}
					}
					ImageIcon ico = new ImageIcon(image);
					l.setIcon(ico);
					b3.setEnabled(false);
				}
			});
			
			b4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int i = 0; i<height;i++) {
						for(int j = 0;j<width;j++) {
							Color c = new Color(image.getRGB(j, i));
							Color cb = c.darker();
							int cv = cb.getRGB();
							image.setRGB(j, i, cv);
						}
					}
					ImageIcon ico = new ImageIcon(image);
					l.setIcon(ico);
					b4.setEnabled(false);
				}
			});
			
			b5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ImageIcon ico = new ImageIcon(MakeGreyScale(image));
					l.setIcon(ico);
				}
			});
			
	}
}
