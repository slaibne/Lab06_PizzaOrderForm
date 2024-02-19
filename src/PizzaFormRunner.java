import javax.swing.*;

public class PizzaFormRunner {
    public static void main(String[] args)
    {
        PizzaForm pForm = new PizzaForm();
        pForm.setTitle("Papa's Pizza Order");
        pForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pForm.setSize(500,500);
        pForm.setVisible(true);
    }
}