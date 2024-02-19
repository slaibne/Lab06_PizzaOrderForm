import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.NumberFormat;

public class PizzaForm extends JFrame
{
    static JComboBox sizeCB = new JComboBox<>();
    static ButtonGroup crustType = new ButtonGroup();
    JCheckBox[] toppingsCB = new JCheckBox[6];

    JPanel sizePnl = new JPanel();
    JPanel crustPnl = new JPanel();
    JPanel toppingsPnl = new JPanel();

    JRadioButton thinRB = new JRadioButton("Thin");
    JRadioButton regularRB = new JRadioButton("Regular");
    JRadioButton deepDRB = new JRadioButton("Deep Dish");


    {
        toppingsCB[0] = new JCheckBox("Pepperoni");
        toppingsCB[1] = new JCheckBox("Onions");
        toppingsCB[2] = new JCheckBox("Sausage");
        toppingsCB[3] = new JCheckBox("Bacon");
        toppingsCB[4] = new JCheckBox("Spinach");
        toppingsCB[5] = new JCheckBox("Extra Cheese");
    }

    JTextArea invoiceZone = new JTextArea();

    JScrollPane scroller = new JScrollPane(invoiceZone);

    JButton orderBtn = new JButton("Submit Order");
    JButton resetBtn = new JButton("Reset Order");
    JButton quitBtn = new JButton("Quit");


    JPanel main = new JPanel();
    JPanel topPnl = new JPanel(new GridLayout(1,2));
    JPanel controlPnl = new JPanel();


    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();


    public PizzaForm()
    {
        main.setLayout(new BoxLayout(main ,BoxLayout.Y_AXIS));
        crustPnl.setLayout(new BoxLayout(crustPnl, BoxLayout.Y_AXIS));
        crustType.add(thinRB);
        crustType.add(regularRB);
        crustType.add(deepDRB);
        crustPnl.add(thinRB);
        crustPnl.add(regularRB);
        regularRB.setSelected(true);
        crustPnl.add(deepDRB);
        //crustPnl.add(crustType);
        crustPnl.setBorder(new TitledBorder(new EtchedBorder(), "Crust:"));

        sizeCB.addItem("Small");
        sizeCB.addItem("Medium");
        sizeCB.addItem("Large");
        sizeCB.addItem("Super");
        sizePnl.add(sizeCB);
        sizePnl.setBorder(new TitledBorder(new EtchedBorder(), "Size:"));

        toppingsPnl.setLayout(new GridLayout(3,2));
        for (JCheckBox l : toppingsCB)
            toppingsPnl.add(l);
        toppingsPnl.setBorder(new TitledBorder(new EtchedBorder(), "Toppings:"));

        topPnl.setLayout(new BorderLayout());

        topPnl.add(sizePnl, BorderLayout.WEST);

        topPnl.add(crustPnl, BorderLayout.CENTER);
        topPnl.setMaximumSize(new Dimension(screenSize.width, 100));
        topPnl.add(toppingsPnl, BorderLayout.EAST);
        main.add(topPnl);
        main.add(scroller);
        createControlPanel();
        add(main);


    }



    private void createControlPanel()
    {
        NumberFormat num = NumberFormat.getCurrencyInstance();
        controlPnl.setLayout(new GridLayout(1,3));
        orderBtn.setFont(new Font("Plain", Font.PLAIN, 12));
        resetBtn.setFont(new Font("Plain", Font.PLAIN, 12));
        quitBtn.setFont(new Font("Plain", Font.PLAIN, 12));
        orderBtn.addActionListener(e ->
        {
            double typeCost = 0;
            double topCost = 0;
            double subtotal = 0;
            double total = 0;
            double tax=0;
            String orderSize = "";
            String orderCrust = "";
            StringBuilder orderToppings = new StringBuilder();
            if(sizeCB.getSelectedIndex() == 0) {
                subtotal = typeCost = 8;
                orderSize = "Small";
            }
            else if (sizeCB.getSelectedIndex() == 1) {
                subtotal = typeCost = 12;
                orderSize = "Medium";
            }
            else if (sizeCB.getSelectedIndex() == 2) {
                subtotal = typeCost = 16;
                orderSize = "Large";
            }
            else if (sizeCB.getSelectedIndex() == 3) {
                subtotal = typeCost = 20;
                orderSize = "Super";
            }

            for (JCheckBox l:toppingsCB)
            {
                if(l.isSelected()) {
                    orderToppings.append(l.getText()).append(", ");
                    topCost++;
                }
            }
            subtotal = subtotal+topCost;
            tax = subtotal * .07;

            total = subtotal + tax;

            if(thinRB.isSelected())
                orderCrust = "Thin Crust";
            else if(regularRB.isSelected())
                orderCrust = "Regular Crust";
            else if(deepDRB.isSelected())
                orderCrust = "Deep Dish";




            invoiceZone.setText("-----------------------\n\n");
            invoiceZone.append("Style: " + orderCrust + "\n");
            invoiceZone.append("Size: " + orderSize + num.format(typeCost) +" "+"\n");
            invoiceZone.append("Toppings: " + (orderToppings.length() > 0 ? orderToppings.substring(0, orderToppings.length() - 2) : "None") + " "+num.format(topCost)+ "\n");
            invoiceZone.append("\nSub-total: " + num.format(subtotal) + "\n");
            invoiceZone.append("Tax: " + num.format(tax) + "\n");
            invoiceZone.append("------------------------\n");
            invoiceZone.append("Total: " + num.format(total) + "\n");
            invoiceZone.append("=============\n");
        });
        controlPnl.add(orderBtn);
        resetBtn.addActionListener(e ->
        {
            regularRB.setSelected(true);
            sizeCB.setSelectedItem("Small");
            for (JCheckBox l: toppingsCB)
            {
                l.setSelected(false);
            }
            invoiceZone.setText("");
        });
        controlPnl.add(resetBtn);
        quitBtn.addActionListener(e ->
        {
            if(JOptionPane.showConfirmDialog(main , "Are you sure you want to quit?") == JOptionPane.YES_OPTION)
                System.exit(0);
        });
        controlPnl.add(quitBtn);
        controlPnl.setMaximumSize(new Dimension(screenSize.width, 500));
        main.add(controlPnl);
    }
}