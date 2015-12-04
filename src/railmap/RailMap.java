
package railmap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author jared
 */
public class RailMap {
    
    protected static ArrayList<StationObject> stations;
    protected static LineColor line;
    protected static int maxTransfers;
    protected static Graphics2D g;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        line = LineColor.WHITE;
        stations = new ArrayList<>();
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter(".properties", "properties"));
        fc.setDialogTitle("Select a file to open");
        fc.showOpenDialog(null);
        File getfile = fc.getSelectedFile();
        String name = getfile.getName();
        getStationsFromConfig(getfile);
        File outfile = new File(getfile.toPath().getParent().toString() + "/" + name + ".png");
        makeImage(outfile);
    }
    
    private static void getStationsFromConfig(File input) {
        try{
            Scanner reader = new Scanner(input);
            line = getLineColorFromString(reader.nextLine());
            while(reader.hasNextLine()) {
                String now = reader.nextLine();
                int colon = now.indexOf(":");
                String name = now.substring(0, colon).trim();
                now = now.substring(colon + 1).trim();
                colon = now.indexOf(":");
                String avail = now.substring(0, colon).trim();
                now = now.substring(colon + 1).trim();
                
                StationObject station;
                station = new StationObject(name, getAvailabilityFromString(avail));
                
                while(true) {
                    if(!now.contains(",")) {
                        station.addTransfer(getLineColorFromString(now));
                        break;
                    }
                    colon = now.indexOf(",");
                    String fun = now.substring(0, colon).trim();
                    station.addTransfer(getLineColorFromString(fun));
                    now = now.substring(colon + 1).trim();
                }
                
                stations.add(station);
            }
        }catch (Exception e) {
        }
    }
    
    private static void makeImage(File file) {
        maxTransfers = getMaxTransfers();
        BufferedImage img = new BufferedImage((24 * maxTransfers + 32 + 256), (32 + stations.size() * 32), BufferedImage.TYPE_INT_RGB);
        g = img.createGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(24*(maxTransfers + 1) + 14, 0, 4, 32 + 32*stations.size());
        g.setColor(getColorFromLineColor(line));
        g.fillRect(((24*(maxTransfers+1)) + 8), 8, 16, 16);
        g.setColor(Color.WHITE);
        g.drawString("Line", (24*(maxTransfers + 1)) + 30 , 24);
        int i = 0;
        for(StationObject s: stations) {
            drawStation(s, i);
            i++;
        }
        file.delete();
        try{
            ImageIO.write(img, "png", file);
        }catch (Exception e) {
            
        }
        JOptionPane.showMessageDialog(null, "Created output file at " + file.getAbsolutePath());
    }    
    
    private static Availability getAvailabilityFromString(String input) {
        if(input.equalsIgnoreCase("open")) {
            return Availability.OPEN;
        }else if(input.equalsIgnoreCase("closed")) {
            return Availability.CLOSED;
        }else if(input.equalsIgnoreCase("local")) {
            return Availability.LOCAL;
        }else if(input.equalsIgnoreCase("abandoned")) {
            return Availability.ABANDONED;
        }else if(input.equalsIgnoreCase("under_construction")) {
            return Availability.UNDER_CONSTRUCTION;
        }else{
            return Availability.PLANNED;
        }
    }
    
    private static LineColor getLineColorFromString(String input) {
        if(input.equalsIgnoreCase("black")) {
            return LineColor.BLACK;
        }else if(input.equalsIgnoreCase("blue")) {
            return LineColor.BLUE;
        }else if(input.equalsIgnoreCase("brown")) {
            return LineColor.BROWN;
        }else if(input.equalsIgnoreCase("cyan")) {
            return LineColor.CYAN;
        }else if(input.equalsIgnoreCase("dark_grey")) {
            return LineColor.DARK_GREY;
        }else if(input.equalsIgnoreCase("diamond")) {
            return LineColor.DIAMOND;
        }else if(input.equalsIgnoreCase("emerald")) {
            return LineColor.EMERALD;
        }else if(input.equalsIgnoreCase("gold")) {
            return LineColor.GOLD;
        }else if(input.equalsIgnoreCase("green")) {
            return LineColor.GREEN;
        }else if(input.equalsIgnoreCase("iron")) {
            return LineColor.IRON;
        }else if(input.equalsIgnoreCase("light_blue")) {
            return LineColor.LIGHT_BLUE;
        }else if(input.equalsIgnoreCase("light_grey")) {
            return LineColor.LIGHT_GREY;
        }else if(input.equalsIgnoreCase("lime_green")) {
            return LineColor.LIME_GREEN;
        }else if(input.equalsIgnoreCase("magenta")) {
            return LineColor.MAGENTA;
        }else if(input.equalsIgnoreCase("orange")) {
            return LineColor.ORANGE;
        }else if(input.equalsIgnoreCase("pink")) {
            return LineColor.PINK;
        }else if(input.equalsIgnoreCase("purple")) {
            return LineColor.PURPLE;
        }else if(input.equalsIgnoreCase("red")) {
            return LineColor.RED;
        }else if(input.equalsIgnoreCase("yellow")) {
            return LineColor.YELLOW;
        }else{
            return LineColor.WHITE;
        }
    }
    
    private static Color getColorFromLineColor(LineColor color) {
        if(color == LineColor.BLACK) {
            return Color.BLACK;
        }else if(color == LineColor.BLUE) {
            return Color.BLUE;
        }else if(color == LineColor.BROWN) {
            return new Color(139, 69, 19);
        }else if(color == LineColor.CYAN) {
            return Color.CYAN;
        }else if(color == LineColor.DARK_GREY) {
            return Color.DARK_GRAY;
        }else if(color == LineColor.DIAMOND) {
            return new Color(180, 240, 240);
        }else if(color == LineColor.EMERALD) {
            return new Color(34, 140, 34);
        }else if(color == LineColor.GOLD) {
            return new Color(218, 165, 32);
        }else if(color == LineColor.GREEN) {
            return Color.GREEN;
        }else if(color == LineColor.IRON) {
            return new Color(220,220,200);
        }else if(color == LineColor.LIGHT_BLUE) {
            return new Color(135, 206, 250);
        }else if(color == LineColor.LIGHT_GREY) {
            return Color.LIGHT_GRAY;
        }else if(color == LineColor.LIME_GREEN) {
            return new Color(50,205,50);
        }else if(color == LineColor.MAGENTA) {
            return Color.MAGENTA;
        }else if(color == LineColor.ORANGE) {
            return Color.ORANGE;
        }else if(color == LineColor.PINK) {
            return Color.PINK;
        }else if(color == LineColor.PURPLE) {
            return new Color(50, 60, 130);
        }else if(color == LineColor.RED) {
            return Color.RED;
        }else if(color == LineColor.WHITE) {
            return Color.WHITE;
        }else{
            return Color.YELLOW;
        }
    }
    
    private static int getMaxTransfers() {
        int max = 0;
        for(StationObject s: stations) {
            int tha = s.getTransferObjects().size();
            if(tha > max) {
                max = tha;
            }
        }
        return max;
    }
    
    private static void drawStation(StationObject s, int i) {
        int x = (24*(maxTransfers + 1)) + 30;
        int y = 32+20 + (i * 32);
        g.setColor(Color.WHITE);
        g.drawString(s.getName(), x, y);
        x = (24*(maxTransfers + 1)) + 8;
        y = 32 + 8 + (i * 32);
        if(s.getMode() == Availability.OPEN) {
            g.setColor(Color.WHITE);
            g.fillRoundRect(x, y, 16, 16, 16, 16);
        }else if(s.getMode() == Availability.LOCAL) {
            g.setColor(Color.WHITE);
            g.drawRoundRect(x, y, 16, 16, 16, 16);
        }else if(s.getMode() == Availability.CLOSED) {
            g.setColor(Color.RED);
            g.drawRoundRect(x, y, 16, 16, 16, 16);
        }else if(s.getMode() == Availability.ABANDONED) {
            g.setColor(Color.GRAY);
            g.drawRoundRect(x, y, 16, 16, 16, 16);
        }else if(s.getMode() == Availability.UNDER_CONSTRUCTION) {
            g.setColor(Color.ORANGE);
            g.drawRoundRect(x, y, 16, 16, 16, 16);
        }else if(s.getMode() == Availability.PLANNED) {
            g.setColor(Color.YELLOW);
            g.fillRoundRect(x, y, 16, 16, 16, 16);
        }
        int j = 0;
        for(TransferObject t: s.getTransferObjects()) {
            x = (24*maxTransfers) - (j*24) - 8;
            y = (i * 32) + 32 + 8;
            g.setColor(getColorFromLineColor(t.getColor()));
            g.fillRect(x, y, 16, 16);
            j++;
        }
    }
}
