package de.fossag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Simple wrapper class for {@link IHackatronClient} implementations that handles
 * network and logging.
 */
public class HackatronWrapper {

  private String host;
  private int port;

  private IHackatronClient hackatronClient;

  public HackatronWrapper(String host, int port, IHackatronClient hackatronClient) {
    this.host = host;
    this.port = port;
    this.hackatronClient = hackatronClient;
  }

  public void run() throws IOException {
    System.out.println("Connecting to " + host + ":" + port);

    // Set up socket and wire up streams
    Socket socket = new Socket(host, port);
    OutputStream out = socket.getOutputStream();
    PrintWriter writer = new PrintWriter(out, true);
    InputStream in = socket.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    System.out.println("Connection successful");

    // The message sender logs the outgoing message and stuffs it into the output stream
    hackatronClient.setMessageSender(s -> {
      System.out.println("OUT MSG: " + s);
      writer.println(s);
    });

    while (true) {
      if (socket.isClosed()) {
        return;
      }
      String line = reader.readLine();
      if (line == null) {
        return;
      }
      System.out.println("IN MSG: " + line);
      hackatronClient.onMessage(line);
    }

  }
}
