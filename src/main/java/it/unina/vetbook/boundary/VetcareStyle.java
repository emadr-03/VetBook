package it.unina.vetbook.boundary;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.ui.FlatDropShadowBorder;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.RadialGradientPaint;


public final class VetcareStyle {

    public static final Color TXT        = new Color(0x0F, 0x1A, 0x52);
    public static final Color BTN_HOVER  = new Color(0xCC, 0xDF, 0xFF);
    public static final Color GRAD_TOP   = new Color(0xE1, 0xE9, 0xFF);
    public static final Color ACCENT     = new Color(0x1A, 0x2F, 0x64);
    public static final Color CARD_BG    = Color.WHITE;


    public static void initLookAndFeel() {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); }
        catch (Exception ex) { System.err.println("FlatLaf not loaded"); }
    }


    public static JPanel createSpotlightBackground() { return new RadialSpotlight(); }

    private static class RadialSpotlight extends JPanel {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int w = getWidth(), h = getHeight();
            Point2D center = new Point2D.Float(w / 2f, h / 2f);
            float radius   = Math.max(w, h) * 0.6f;

            float[] dist = { 0f, 0.35f, 0.7f, 1f };
            Color[] cols = {
                    Color.WHITE,
                    new Color(0xE3, 0xEC, 0xFF),
                    new Color(0x5C, 0x7B, 0xFF),
                    new Color(0x36, 0x46, 0xA0)
            };
            RadialGradientPaint paint = new RadialGradientPaint(center, radius, dist, cols);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(paint);
            g2.fillRect(0, 0, w, h);
            g2.dispose();
        }
    }


    public static JPanel makeCard(String text, ImageIcon icon, Runnable onClick) {

        JButton btn = new JButton(text, icon);
        btn.setFont(new Font("SansSerif", Font.BOLD, 17));
        btn.setForeground(TXT);
        btn.setBackground(CARD_BG);
        btn.setIconTextGap(18);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.putClientProperty("FlatLaf.style", "hoverBackground:" + toHex(BTN_HOVER) + ";");
        btn.setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30));
        btn.setFocusable(true);

        Border base  = new FlatDropShadowBorder(Color.BLACK, 8, 0.05f);
        Border hover = new FlatDropShadowBorder(Color.BLACK, 16, 0.30f);

        JPanel card = new CardBase();
        card.setBorder(base);
        card.add(btn, BorderLayout.CENTER);

        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { card.setBorder(hover); }
            @Override public void mouseExited (MouseEvent e) { card.setBorder(base);  }
            @Override public void mouseClicked(MouseEvent e) { if (onClick!=null) onClick.run(); }
        });
        return card;
    }


    private static class CardBase extends JPanel {
        CardBase() {
            super(new BorderLayout());
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(0,4,0,0));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        }
        @Override protected void paintComponent(Graphics gg) {
            Graphics2D g = (Graphics2D) gg.create();
            g.setPaint(new GradientPaint(0,0, CARD_BG, getWidth(),0, GRAD_TOP));
            g.fillRoundRect(0,0,getWidth(),getHeight(),16,16);
            g.setColor(ACCENT);
            g.fillRoundRect(0,0,8,getHeight(),16,16);
            g.dispose();
            super.paintComponent(gg);
        }
    }

    public static JTable makeTable(Object[][] rows, String[] cols) {

        JTable t = new JTable(rows, cols);
        t.setFillsViewportHeight(true);
        t.setRowHeight(28);
        t.setFont(new Font("SansSerif", Font.PLAIN, 14));
        t.setForeground(TXT);
        t.setGridColor(new Color(0xDDE3FF));

        /* header look */
        JTableHeader head = t.getTableHeader();
        head.setReorderingAllowed(false);
        head.setFont(new Font("SansSerif", Font.BOLD, 14));
        head.setBackground(ACCENT);
        head.setForeground(Color.WHITE);
        head.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));

        /* selection colours */
        t.setSelectionBackground(BTN_HOVER);
        t.setSelectionForeground(TXT);

        return t;
    }

    public static DatePicker makeDatePicker() {
        DatePicker dp = new DatePicker();
        dp.setDateToToday();
        // font e colori: FlatLaf li prende dal Look&Feel
        return dp;
    }

    public static TimePicker makeHourPicker() {
        TimePicker tp = new TimePicker();
        tp.getSettings().generatePotentialMenuTimes(
                TimePickerSettings.TimeIncrement.OneHour,
                java.time.LocalTime.of(0,0),
                java.time.LocalTime.of(23,0)
        );
        tp.setTime(java.time.LocalTime.of(8,0));
        return tp;
    }

    public static JPanel makeDialogCard() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new FlatDropShadowBorder(Color.BLACK, 12, .20f));
        return card;
    }

    public static JTextField textField(String ph) {
        JTextField t = new JTextField(18);
        t.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, ph);
        t.setPreferredSize(new Dimension(220, 28));
        return t;
    }

    public static JPasswordField passwordField(String ph) {
        JPasswordField p = new JPasswordField(18);
        p.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, ph);
        p.setPreferredSize(new Dimension(220, 28));
        return p;
    }


    private static String toHex(Color c){
        return String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
    }

    private VetcareStyle() {}
}

