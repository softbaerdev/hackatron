package de.fossag;

public interface IHackatronClient {

  /**
   * Gets called one time after instance creation to set the {@link IMessageSender} instance. You
   * can use that to send reply messages back to the game server.
   *
   * @param messageSender IMessageSender instance
   */
  void setMessageSender(IMessageSender messageSender);

  /**
   * Gets called every time a new message from the game server arrives.
   *
   * See PROTOCOL.md for details
   *
   * @param message Message string
   */
  void onMessage(String message);
}
