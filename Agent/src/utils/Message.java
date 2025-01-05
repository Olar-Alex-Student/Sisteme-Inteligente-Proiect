package utils;

//utils/Message.java
import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
	private String sender;
	private String receiver;
	private String content;
	private LocalDateTime timestamp;
	private MessageType type;

	// Enum pentru diferite tipuri de mesaje
	public enum MessageType {
		CHAT, // Mesaj normal de chat
		CONNECTION, // Mesaj de conectare/deconectare
		USER_LIST, // Actualizare listă utilizatori
		SYSTEM // Mesaj de sistem
	}

	// Constructor
	public Message(String sender, String receiver, String content, MessageType type) {
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.type = type;
		this.timestamp = LocalDateTime.now();
	}

	// Getteri și Setteri
	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public MessageType getType() {
		return type;
	}

	@Override
	public String toString() {
		return String.format("[%s] %s -> %s: %s", timestamp.toString(), sender, receiver, content);
	}
}
