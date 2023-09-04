package de.fossag;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    HackatronWrapper client = new HackatronWrapper(
        "hackatron.de",
        4000,
        new DummyHackatronClient());
    client.run();
  }
}