package converter;

import javax.swing.JFrame;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class conveter_frame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField curren;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public conveter_frame() {

        setTitle("Converter");
        setBackground(Color.WHITE);
        setResizable(false);
        setAlwaysOnTop(true);

        // ICON (relative path – works on any PC)
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/Images/icon.png"));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 627, 464);

        contentPane = new JPanel();
        contentPane.setBackground(Color.YELLOW);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Currency Converter");
        lblNewLabel_1.setFont(new Font("Georgia Pro Black", Font.PLAIN, 30));
        lblNewLabel_1.setBounds(152, 25, 350, 43);
        contentPane.add(lblNewLabel_1);

        curren = new JTextField();
        curren.setFont(new Font("Cambria Math", Font.PLAIN, 21));
        curren.setBounds(24, 148, 185, 32);
        curren.setColumns(10);
        Border brd = BorderFactory.createLineBorder(Color.black, 2);
        curren.setBorder(brd);
        contentPane.add(curren);

        JComboBox from = new JComboBox(getCurrencies());
        JComboBox to1 = new JComboBox(getCurrencies());

        from.setEditable(true);
        to1.setEditable(true);

        from.setBackground(new Color(224, 255, 255));
        from.setMaximumRowCount(4);
        from.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        from.setBounds(229, 148, 85, 32);
        contentPane.add(from);

        to1.setBackground(new Color(224, 255, 255));
        to1.setMaximumRowCount(4);
        to1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        to1.setBounds(398, 146, 85, 32);
        contentPane.add(to1);

        JLabel lblNewLabel = new JLabel("TO");
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 21));
        lblNewLabel.setBounds(335, 148, 53, 32);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_2 = new JLabel("Result :");
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 21));
        lblNewLabel_2.setBounds(24, 266, 72, 26);
        contentPane.add(lblNewLabel_2);

        JLabel result = new JLabel("");
        result.setFont(new Font("Times New Roman", Font.PLAIN, 21));
        result.setBounds(106, 268, 300, 23);
        contentPane.add(result);

        JLabel rate = new JLabel("");
        rate.setFont(new Font("Times New Roman", Font.PLAIN, 21));
        rate.setBounds(106, 230, 300, 28);
        contentPane.add(rate);

        JButton convertbtn = new JButton("Convert");
        convertbtn.setToolTipText("converts the currency");
        convertbtn.setBackground(new Color(240, 230, 140));
        convertbtn.setFont(new Font("SimSun", Font.BOLD, 29));
        convertbtn.setBounds(183, 347, 220, 48);
        contentPane.add(convertbtn);

        convertbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                new Thread(() -> {
                    try {
                        backend bk = new backend(
                                from.getSelectedItem().toString(),
                                to1.getSelectedItem().toString(),
                                Double.parseDouble(curren.getText())
                        );

                        if (bk.checkstring()) {

                            String rate1 = Double.toString(bk.getRate());
                            String result1 = Double.toString(bk.getConvertedCurrency());

                            javax.swing.SwingUtilities.invokeLater(() -> {
                                rate.setText(rate1 + " " + to1.getSelectedItem());
                                result.setText(result1 + " " + to1.getSelectedItem());
                            });

                        } else {
                            JOptionPane.showMessageDialog(
                                    convertbtn,
                                    "Currency Should not be Same",
                                    "Mismatch",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }

                    } catch (NumberFormatException nf) {
                        JOptionPane.showMessageDialog(
                                convertbtn,
                                "Enter a valid amount",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }).start();
            }
        });

        JLabel lblNewLabel_3 = new JLabel("Rate :");
        lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 21));
        lblNewLabel_3.setBounds(24, 230, 72, 26);
        contentPane.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Amount :");
        lblNewLabel_4.setFont(new Font("Serif", Font.ITALIC, 24));
        lblNewLabel_4.setBounds(24, 116, 185, 26);
        contentPane.add(lblNewLabel_4);

        JLabel lblNewLabel_4_1 = new JLabel("From :");
        lblNewLabel_4_1.setFont(new Font("Serif", Font.ITALIC, 24));
        lblNewLabel_4_1.setBounds(229, 116, 85, 26);
        contentPane.add(lblNewLabel_4_1);

        // VERY IMPORTANT
        setVisible(true);
    }

    private Vector<String> getCurrencies() {
        Vector<String> vc = new Vector<>();

        // relative path – works everywhere
        File file = new File("src/codes.txt");

        try (Scanner sc = new Scanner(new FileReader(file))) {
            while (sc.hasNext()) {
                vc.add(sc.next());
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "codes.txt not found!",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return vc;
    }
}
