# Hackatron Client/Bot

*This is a Kotlin client for the Hackatron multiplayer game server. It connects to the game server, handles incoming game messages, and computes moves using pluggable strategies.*

## Get started

You need at least [JDK 17] installed. We suggest using [IntelliJ IDEA] as an IDE.

### How to install JDK 17 on Windows

1. Download the [Adopt OpenJDK installer MSI][JDK 17] for Windows x86.
2. Execute the MSI file
3. When offered, select "Set JAVA_PATH variable" to be installed
4. Continue installing until done.

### Get the example Code

Download the [example code as a ZIP archive](https://gitlab.fachschaften.org/foss-ag/hackatron-wrapper/-/archive/main/hackatron-wrapper-main.zip) or clone it with git:

```bash
git@gitlab.fachschaften.org:foss-ag/hackatron-wrapper.git
```

### Compile and run the HackaTRON client

Open a terminal and run:

**Linux/MacOS:**

```bash
./mvnw package
```

**Windows:**

```ps
.\mvnw.cmd package
```

[IntelliJ IDEA]: https://www.jetbrains.com/idea/
[JDK 17]: https://adoptium.net/temurin/releases/?package=jdk&version=17&arch=x64&os=windows

## Protocol

For your convenience, you can find a description of the protocol in [`PROTOCOL.md`](./PROTOCOL.md).

## Rules

Please respect the rules of the competition:

- Only one connection to the server per team at any time.
- You must be able to explain your code and strategy
- Try to solve the competition with your own code. You might take a look at the internet for inspiration, but it is more fun if you come up with your own ideas ;)

## License

GPLv3, so feel free to edit and share your improvements in public (e.g. on gitlab.fachschaften.org / Codeberg.org / GitHub.com) after the competition ended.
