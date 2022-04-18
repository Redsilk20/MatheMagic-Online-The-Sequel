GROUP MEMBERS: Christian Cuevas, Wafee Alkevy, Jackson Moschet
GITHUB LINK: https://github.com/Redsilk20/MatheMagic-Online-The-Sequel.git

README INSTRUCTIONS:

First a user must run the server file, then run the client file to get a secure connection to the socket. The GUI application will now be ready for its functions.
(Bugs listed at the bottom of file)

There are 5 functions that can be used:
1) LOGIN
2) SOLVE
3) LIST
4) SHUTDOWN 
5) LOGOUT
6) MESSAGE
ALL command words must be fully capitalized, the rest of the statements are lower case.

1) LOGIN :
Format should be "LOGIN johndoe johndoepassword" with the username being the first word after LOGIN and the password being the last.

2) SOLVE :
Can either solve for a circle or a rectangle. For a circle write "SOLVE -c #" and put a number where the pound sign is, this represents the circle's radius.
For a rectangle there are two ways to type in a command, first is "SOLVE -r #", which assumes the rectangle is a square, and the pound side is the length of its sides,
and the next way is "SOLVE -r # #", where the two numbers represent the lengths of the 2 sides.

3) LIST :
Type in "LIST" to get a list of the user's SOLVE commands printed out. If logged in as the root user, they can use the "LIST -all" function which lists every user's solve commands.

4) SHUTDOWN :
Type in "SHUTDOWN" to shutdown the server.

5) LOGOUT : 
Type in "LOGOUT" to logout of the user account and close the client.

6) MESSAGE :
Type in "MESSAGE" with a valid user after a space in between, to send messages to other users in a client. If the root user is logged in, they can send a message to 
all clients by typing "-all" instead of a user name.

EXAMPLE OUTPUT:

C: LOGIN sall sally22

S: FAILURE: Please provide correct username and password. Try again.

C: LOGIN john john22

S: SUCCESS

C: SOLVE -c 3

S: Circle's circumference is 18.84 and area is 28.27

C: SOLVE -r 5 9

S: Rectangle's perimeter is 28 and area is 45

C: LIST

S: john
    
 Circle's circumference is 18.84 and area is 28.27

    
 Rectangle's perimeter is 28 and area is 45

C: LOGOUT

S: 200 OK

C: LOGIN root root22

S: SUCCESS

C: SOLVE -r 1 9

S: Rectangle's perimeter is 20 and area is 9

C: LIST -all

S: root
    
 Rectangle's perimeter is 20 and area is 9

      
 
john
    
 Circle's circumference is 18.84 and area is 28.27

      
 Rectangle's perimeter is 28 and area is 45

      
 
sally
    
 No interactions yet


qiang
    
 No interactions yet

C: SHUTDOWN

S: 200 OK

C: MESSAGE root hey man

S: Error: No other clients are open.

ROOT:
C: LOGIN root root22
S: SUCCESS
C: MESSAGE sally hey sally
S: User is not logged in.
C: MESSAGE john whats going on
S: Message sent.
C: MESSAGE -all hey guys 
S: Message sent.

127.0.0.1
127.0.0.1
LOGIN root root22
LOGIN john john22
MESSAGE sally hey sally
MESSAGE john whats going on
MESSAGE -all hey guys

JOHN: 
C: LOGIN john john22
S: SUCCESS
S: Message from John: whats going on 
S: Message from Root: hey guys


BUGS: No bugs are known, the only thing I would say doesn't work as well as I would like is that when you logout of the client, 
it closes the application very fast so you are unable to see "200 OK" in the GUI but it does print to the console. Also was not able 
to automatically read data from the server, so a receive button was implemented if a user was expecting a message.
