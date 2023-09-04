# Protocol

The protocol is string based.  
Every packet will end with a newline (\n), but the Hackathon wrapper handles them for you.  
E.g: `pos|10|50|5`  

## Packet structure

The general packet structure looks like this:  
\<packet type\>\<...arguments\>  
E.g: `game|5|1|2|3`  
Where game is the packet type, 5 the first argument, 1 the second, 2 the third and 3 the fourth

## Packet types

### motd

The motd packet is sent by the server when you connect to it.  
motd means "Message of the day".

**Name:** motd  
**Sender:** Server  
**Arguments:**  

| # | Type | Description |
|---|--------|------------------------|
| 1 | String | The message of the day |

**Example:** `motd|Hello how are you? :)`

### join

The join packet is the first packet the client has to send to the server when connecting.  
Remember the password otherwise you cant use the username again!

**Name:** join  
**Sender:** Client  
**Arguments:**  

| # | Type   | Description  |
|---|--------|--------------|
| 1 | String | The username |
| 2 | String | The password |

**Example:** `join|Cool Guy|mysupersecret`

### error

The error packet is sent by the server if something went wrong.

**Name:** error  
**Sender:** Server  
**Arguments:**  

| # | Type   | Description                                           |
|---|--------|-------------------------------------------------------|
| 1 | String | The error according to [ERRORCODES.md](ERRORCODES.md) |

**Example:** `error|INVALID_USERNAME`

### game

The game packet is sent by the server to inform the client about the new game round.  
It contains information about the map size and the current player id.

**Name:** game  
**Sender:** Server  
**Arguments:**  

| # | Type   | Description                   |
|---|--------|-------------------------------|
| 1 | Number | The width of the current map  |
| 2 | Number | The height of the current map |
| 3 | Number | The current player id         |

**Example:** `game|100|100|5`

### pos

The pos packet is sent by the server to inform the client about a players current position.

**Name:** pos  
**Sender:** Server  
**Arguments:**  

| # | Type   | Description              |
|---|--------|--------------------------|
| 1 | Number | The player id            |
| 2 | Number | x position of the player |
| 3 | Number | y position of the player |

**Example:** `pos|5|3|8`

### player

The player packet is sent by the server to share informations of an player.

**Name:** player  
**Sender:** Server  
**Arguments:**  

| # | Type   | Description            |
|---|--------|------------------------|
| 1 | Number | The player id          |
| 2 | String | The name of the player |

**Example:** `player|3|Coolguy`

### tick

The tick packet is sent by the server after a turn has been done. Its the best to send a move packet after this!

**Name:** tick  
**Sender:** Server  
**Arguments:**  None

**Example:** `tick`

### die

The die packet is sent by the server to inform the client about a players who died.

**Name:** die  
**Sender:** Server  
**Arguments:**  

| #    | Type   | Description   |
|------|--------|---------------|
| 1... | Number | The player id |

**Example (1 dead player):** `die|5`  
**Example (4 dead player):** `die|5|8|9|13`

### move

The move packet is sent by the client to decide where to move.

**Name:** move  
**Sender:** Client  
**Arguments:**  

| # | Type   | Description             |
|---|--------|-------------------------|
| 1 | String | up, right, down or left |

**Example:** `move|up`

### win

The win packet is sent by the server to inform the client they won.

**Name:** win  
**Sender:** Server  
**Arguments:**  

| # | Type   | Description      |
|---|--------|------------------|
| 1 | Number | amount of wins   |
| 2 | Number | amount of losses |

**Example:** `win|1|20`

### lose

The lose packet is sent by the server to inform the client they lost.

**Name:** lose  
**Sender:** Server  
**Arguments:**  

| # | Type   | Description      |
|---|--------|------------------|
| 1 | Number | amount of wins   |
| 2 | Number | amount of losses |

**Example:** `lose|1|20`
