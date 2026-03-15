import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class PantryItem {
    String name;
    LocalDate expiryDate;

    PantryItem(String name, LocalDate expiryDate) {
        this.name = name;
        this.expiryDate = expiryDate;
    }
}

public class PantryExpiryTracker extends JFrame implements ActionListener {

    JTextField nameField, dateField;
    JTextArea displayArea;
    JButton addButton, checkButton;

    PantryItem[] pantry = new PantryItem[50];
    int count = 0;

    PantryExpiryTracker() {

        setTitle("Pantry Expiry Tracker");
        setSize(400,400);
        setLayout(new FlowLayout());

        add(new JLabel("Item Name:"));
        nameField = new JTextField(15);
        add(nameField);

        add(new JLabel("Expiry Date (YYYY-MM-DD):"));
        dateField = new JTextField(15);
        add(dateField);

        addButton = new JButton("Add Item");
        checkButton = new JButton("Check Expiry");

        add(addButton);
        add(checkButton);

        displayArea = new JTextArea(15,30);
        add(new JScrollPane(displayArea));

        addButton.addActionListener(this);
        checkButton.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==addButton){

            String name = nameField.getText();
            String date = dateField.getText();

            LocalDate expiry = LocalDate.parse(date);

            pantry[count] = new PantryItem(name, expiry);
            count++;

            displayArea.append("Added: " + name + " Expiry: " + expiry + "\n");

            nameField.setText("");
            dateField.setText("");
        }

        if(e.getSource()==checkButton){

            displayArea.append("\nExpiry Alerts:\n");

            LocalDate today = LocalDate.now();

            for(int i=0;i<count;i++){

                long days = ChronoUnit.DAYS.between(today, pantry[i].expiryDate);

		if(days == 0){
    			displayArea.append(pantry[i].name + " expires TODAY!\n");
		}
		else if(days > 0 && days <= 3){
    			displayArea.append(pantry[i].name + " expires in " + days + " days!\n");
		}
		else if(days < 0){
    			displayArea.append(pantry[i].name + " is already EXPIRED!\n");
		}
                }
            
        
    }}

    public static void main(String[] args) {
        new PantryExpiryTracker();
    }
}