package de.fossag;

public class DummyHackatronClient implements IHackatronClient {

  private IMessageSender messageSender;
  private static final String CLIENT_NAME = "dummyclient";
  private static final String CLIENT_SECRET = "changeme";

  @Override
  public void setMessageSender(IMessageSender messageSender) {
    this.messageSender = messageSender;
  }

  @Override
  public void onMessage(String message) {
    String[] parts = message.split("\\|");
    String messageType = parts[0];

    switch (messageType) {
      case "motd":
        messageSender.send("join|" + CLIENT_NAME + "|" + CLIENT_SECRET);
        break;
      case "tick":
        messageSender.send("move|up");
        break;
      default:
        System.out.println("Unknown message type :(");
        System.exit(1);
    }
  }
}
