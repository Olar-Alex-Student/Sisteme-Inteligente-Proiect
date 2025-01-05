package agents.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import agents.ChatClientAgent;

public class ChatClientGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5270856328379820188L;
	private JTextArea chatArea;
	private JTextField inputField;
	private JComboBox<String> recipientDropdown;
	private JButton sendButton;
	private ChatClientAgent clientAgent;

	public ChatClientGUI(ChatClientAgent clientAgent, List<String> activeUsers) {
		this.clientAgent = clientAgent;
		setTitle("Chat Client");
		setSize(400, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Zona de afișare a mesajelor
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		add(new JScrollPane(chatArea), BorderLayout.CENTER);

		// Panou pentru trimiterea mesajelor
		JPanel inputPanel = new JPanel(new BorderLayout());

		recipientDropdown = new JComboBox<>(activeUsers.toArray(new String[0]));
		inputPanel.add(recipientDropdown, BorderLayout.WEST);

		inputField = new JTextField();
		inputPanel.add(inputField, BorderLayout.CENTER);

		sendButton = new JButton("Send");
		inputPanel.add(sendButton, BorderLayout.EAST);

		add(inputPanel, BorderLayout.SOUTH);

		// Adăugare eveniment pentru butonul de trimitere
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String recipient = (String) recipientDropdown.getSelectedItem();
				String message = inputField.getText();
				if (recipient != null && !message.isEmpty()) {
					clientAgent.sendMessageTo(recipient, message);
					inputField.setText("");
				} else {
					JOptionPane.showMessageDialog(ChatClientGUI.this,
							"Selectați un destinatar și introduceți un mesaj.", "Eroare", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public void updateActiveUsers(List<String> activeUsers) {
		recipientDropdown.removeAllItems();
		for (String user : activeUsers) {
			recipientDropdown.addItem(user);
		}
	}

	public void displayMessage(String message) {
		chatArea.append(message + "\n");
		chatArea.setCaretPosition(chatArea.getDocument().getLength());
	}

}
