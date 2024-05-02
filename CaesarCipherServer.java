//importing the libraries
import java.net.*;
import java.io.*;
import java.util.Random;

//definning the public class
public class CaesarCipherServer {
	public static void main(String[] args) throws IOException {

	//initializing the server socket
	ServerSocket serverSock = null;

	//trying to create a server socket into the port 5000
        try{
		serverSock = new ServerSocket(50000);
	}


	//catching the exception if the code does not run and printing a message to the user
        catch (IOException ie){
		System.out.println("Can't listen on 50000");
		System.exit(1);
	}

	//initializing a socket for the client
	Socket link = null;

	//printing a message to show that the server is listening for a connection or an input
        System.out.println("Listening for connection ...");

	//search and try to find a client connection
        try {
		link = serverSock.accept();
	}

	//catch an exception if the code did not run and print a message
        catch (IOException ie){
		System.out.println("Accept failed");
		System.exit(1);
	}

	//print a successfull connection message
        System.out.println("Connection successful");
        System.out.println("Listening for input ...");

	//initializing a print writer to send data to the client
	PrintWriter output = new PrintWriter(link.getOutputStream(), true);

	//initializing a buffer reader to get input from the user
	BufferedReader input = new BufferedReader(new InputStreamReader(link.getInputStream()));

		//generating a random key for caesar cipher
		Random rand = new Random();

		//setting the limit to be 26 since the alphabet
		int rand1 = rand.nextInt(26);

		//initializing a string to get input from the client
		String inputLine;

		//initializing strings to use later in the while loop
		String encrypted;
		String decrypted;

		//boolean variable that is set to false
		boolean key1 = false;

		//a while loop to continue getting input from the user
        while ((inputLine = input.readLine()) != null)

	{

		//check if the input please send the key then enter the loop
		if (inputLine.equals("Please send the key")) {
			//print to client the secret key
			output.println("The key is " + rand1);

			//print to the server the input
			System.out.println("Message from the client: " + inputLine);

			//set the key to true since the user asked for it
			key1 = true;
		}

		//else statement if the user didnt input the above statement
		else {
			//if statement that runs if the key is set to true
			if (key1) {

				//decrypt the message and send it to the client with the secret key
				decrypted = decryptMessage(inputLine, rand1);

				//print to the server the encrypted message from the client
				System.out.println("Message from the client: " + inputLine);

				//print to the server the decrypted message from the client
				System.out.println("Decrypted: " + decrypted);

				//print to the client the input or echo from client
				output.println(inputLine);
			}

			//else statement if the key wasnt true
			else {
				//print to the server the input
				System.out.println("Message from the client: " + inputLine);

				//print to the client the input as an echo
				output.println(inputLine);
			}
		}

		//if the client inputs bye then it closes the server and breaks
		if (inputLine.equals("Bye")) {
			System.out.println("Closing Connection");
			break;
		}
	}

	//close all of the streams and ports
        output.close();
        input.close();
        link.close();
        serverSock.close();
}

	//a method for decrypting the message using the Caesar cipher
	public static String decryptMessage(String message, int key) {

	//string to store the decrypted message
	String decrypted = "";

		//go through every char in the input
		for (int i = 0; i<message.length(); i++) {

			//get the current char from the input
			char n1 = message.charAt(i);

			//check if the next input is a letter
			if (Character.isLetter(n1)) {

				//char to define the input
				char base;

				//if statement to check if the input is lower or upper case
				if(Character.isUpperCase(n1)){
					base = 'A';
				}
				else{
					base = 'a';
				}

				//decrypt the char using the vigenere cipher method
				decrypted += (char) (((n1 - base - key + 26) % 26) + base);
			}
			else {

				//if the current char is not a letter then keep it as it is
				decrypted += n1;
			}
		}

		//return the decrypted message
		return decrypted;
	}
}