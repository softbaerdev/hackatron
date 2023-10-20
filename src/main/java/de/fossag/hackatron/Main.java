package de.fossag.hackatron;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    HackatronWrapper client = new HackatronWrapper(
        "game.hackatron.de",
        4000,
        new HackatronClient());
    client.run();
  }
}