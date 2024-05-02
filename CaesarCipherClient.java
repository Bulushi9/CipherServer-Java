//importting the needed libraries
import java.io.*;
import java.net.*;

//definning the public class
public class CaesarCipherClient{
	public static void main(String[] args) throws IOException{

		//initializing the socket link, print writer and buffered reader to null
		Socket link = null;
		PrintWriter output = null;
		BufferedReader input = null;

		//trying to create a server socket into the port 5000
		try{
			link= new Socket("127.0.0.1", 50000);

			//initializing printwriter to send input to the server
			output = new PrintWriter(link.getOutputStream(), true);

			//initializing buffered reader to send data to the server using the socket stream
			input = new BufferedReader(new InputStreamReader(link.getInputStream()));
		}

		//catching any exception if the the code does not run and print a message
		catch(UnknownHostException e)
		{
			System.out.println("Unknown Host");
			System.exit(1);
		}

		//catching any exception and print if it does not connect to any host
		catch (IOException ie) {
			System.out.println("Cannot connect to host");
			System.exit(1);
		}

		//initializing a buffer reader to read input from the user
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

		//making a user input variable
		String usrInput;

		//while loop to continue getting input from the user
		while ((usrInput = stdIn.readLine())!=null) {

			//print output to the server and the client
			output.println(usrInput);
			System.out.println("Echo from Server: " + input.readLine());
		}

		//closing all of the outputs, inputs, stdinput and link
		output.close();
		input.close();
		stdIn.close();
		link.close();
	}
}