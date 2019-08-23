# TetrisJ
A self-written Tetris game in Java, used to train an AI

## Features
It outputs a gzip filed starting with `teris_<numbers>.t7` . I this file are lines representing the choises the player made.
The are formated like this: ```<field blocks>;<new shape>=<x>;<y>;<rot>```
For example 
```000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010001111111000000000000011111111101111101111;1100011000000000=8;26;0```

Each line will contain 3 `;` if a line was full and thus removed, or 5 `;` if this did not happen. 
If we split this information at each `;` we will have the following data:
- <field blocks>, usually 300 chars long, represents the field (10 width, 30 height), each 1 is a block , a 0 is empty space.
- <new shape>, usually 16 chars long, a 4x4 field representing the new block to place

then there is the `=`, and we can split again at each `;` and will gain the <x> pos, the <y> pos and the rotation (0-3, including)

## System args

### dragon.tetris.visible, default false
Weather or not to hide the window when the server is connected and the game is played by an AI or any other connected thing

### dragon.tetris.port, default 4404
The Port where a server is open for this game to get remotely played.

### dragon.tetris.amount, default 1
The amount of simultaneous games. each game has its own port (each incremented by 1)

### dragon.tetris.headless, default false
Do not create/show any window

### dragon.tetris.seed, default random number
this seed is used for all random instances