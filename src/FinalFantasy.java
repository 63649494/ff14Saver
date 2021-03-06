import com.alibaba.fastjson.JSON;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FinalFantasy extends JFrame implements ActionListener {

    public FinalFantasy(){
        List<Walkthrough> list = readFile();
        setTitle("豆芽副本指南 by 神意之地-鼓瑟笙箫 o(￣▽￣)ｄ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,500,360);

        JPanel contentPane=new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        contentPane.setLayout(null);

        JLabel label2 = new JLabel("快捷查询:");
        label2.setBounds(50,20,70,20);
        contentPane.add(label2);
        JTextField jTextField = new JTextField();
        jTextField.setBounds(120,20,200,20);
        contentPane.add(jTextField);
        JButton jButton1 = new JButton("查询");
        jButton1.setBounds(340,20,70,20);
        contentPane.add(jButton1);

        JLabel label=new JLabel("选择副本:");
        label.setBounds(50,50,70,20);
        contentPane.add(label);
        JComboBox comboBox=new JComboBox();
        list.forEach(walkthrough -> comboBox.addItem(walkthrough.getName()));
        comboBox.setBounds(120,50,200,20);
        contentPane.add(comboBox);

        JLabel label1=new JLabel("攻略:");
        label1.setBounds(50,80,55,20);
        label1.setHorizontalAlignment(SwingConstants.RIGHT);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setBounds(120,80,330,220);
        jTextArea.setEnabled(true);
        jTextArea.setLineWrap(true);
        jTextArea.setDisabledTextColor(Color.black);
        JButton jButton = new JButton("复制攻略");
        jButton.setBounds(350,20,100,20);
        contentPane.add(label1);
        contentPane.add(jTextArea);
        contentPane.add(jButton);
        jButton.setVisible(false);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.forEach(walkthrough -> {
                    if(comboBox.getSelectedItem().equals(walkthrough.getName())) {
                        jTextArea.setText(walkthrough.getContent());
                    }
                });
            }
        });

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextArea.setEnabled(true);
                jTextArea.selectAll();
                jTextArea.copy();
                jTextArea.setEnabled(false);
            }
        });

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.forEach(walkthrough -> {
                    String s = jTextField.getText();
                    if (s.equals(walkthrough.getName()) || walkthrough.getTag().contains(s)) {
                        jTextArea.setText(walkthrough.getContent());
                        comboBox.setSelectedItem(walkthrough.getName());
                    }
                });
            }
        });


        setContentPane(contentPane);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
       new FinalFantasy();
    }

    public List<Walkthrough> readFile() {
        String jsonStr = "";
        try {
            File jsonFile = new File("副本攻略.json");
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Walkthrough> walkthroughs = JSON.parseArray(jsonStr, Walkthrough.class);
        return walkthroughs;
    }


}
