Bomberman
=========

Project for SYSC 3303 

<img src="http://i.imgur.com/bIBybe8.jpg">

Playing:
 Run Files: Client.java and Server.java 
 For Ease of Use: Use Eclipse 
 For Player Case: - Run Server.java first, 
 - Then Run Client.java for as many players as you want (1-4) 
 - In each Client.java type 'join' to join the game as a player 
 - Once all players have pressed ready, the game will start.
 - Now for movement of player use 'W' to move up, 'A' to move left, 'S' to move 
   down and 'D' to move right 
 - Press 'Spacebar' to drop a bomb 

<img src="http://i.imgur.com/dxTvdu6.jpg">

The game ends when:

1. Only 1 player is still alive and goes into the portal.

2. No players are left alive.

<img src="http://i.imgur.com/gXcwBRD.jpg">
 
Testing:
  Run Files: TestDriver.java and Server.java 
  For Ease of Use: Use Eclipse 
  For Testing Case: 
  - Run Server.java and TestDriver.java 
  - in testdriver.java enter "testcases"
  - watch the test execute


Test 1: 1 player performing simple movements which includes the use of bombs.
	A spectator is also present.
	
Test 2: 2 players are moving and using bombs and end the game by touching.

Test 3: Latency test 1, the server is under low load. The game runs with 1
	player and 1 spectator and measures the latency.
	
Test 4: Latency test 2, the server is under medium load. The game runs with 2
	players and 2 spectators and measures the latency.
	
Test 5: Latency test 3, the server is under high load. The game runs with 4
	players and 4 spectators and measures the latency.
