/*
   Programmer: Ziwei Shen
   Class: CS 145
   Date: August. 8st, 2023
   Subject: Lab 6 Twenty Questions
   Purpose: The program creates a "20 Questions" game where the computer guesses an 
            object chosen by the player. It uses a binary tree structure to organize 
            questions and answers. Players respond to yes/no questions, guiding the 
            computer's guesses. The code handles game rounds, saving/loading, and tracks 
            statistics like games played and won.
*/

import java.io.*;
import java.util.*;


public class QuestionTree {
   // The root node of the question tree
   private QuestionNode root;
   // Interface to interact with the user
   private UserInterface userInterface;
   // Total number of games played
   private int totalGamesPlayed;
   // Number of games won by the machine
   private int gamesWonByMachine;

   /*
      Constructs a QuestionTree for the game.
      Parameter:
         - userInterface: The user interface to interact with the player.
      Precondition: 
         - 'userInterface' is not null.
   */
   public QuestionTree(UserInterface userInterface) {
      // Initialize root node with initial message
      root = new QuestionNode("computer");
      this.userInterface = userInterface;
      totalGamesPlayed = 0;
      gamesWonByMachine = 0;
   }

   /*
      Initiates a new game round and handles game logic.
      Postcondition: 
         - The game round is played and statistics are updated.
   */
   public void play() {
      root = play(root);
      totalGamesPlayed++;
   }
   
   /*
      Recursive method for playing the guessing game.
      Traverses the binary tree of questions and answers based on user responses.
      Parameter:
         - node: The current node being considered in the binary tree.
      Return:
         - The updated node after user interaction.
      Precondition:
         - The 'node' parameter is not null.
      Postcondition:
         - The binary tree is updated according to user interactions.
   */
   private QuestionNode play(QuestionNode node) {
      // If the node is an answer node
      if (node.isAnswer()) {
         if (queryUser("Would your object happen to be " + node + "?")) {
            userInterface.println("I win!");
            gamesWonByMachine++;
         } else {
            // User's object is not guessed correctly
            node = learnNewNode(node);
         }
         // If the node is a question node
      } else {
         userInterface.print(node.toString() + " ");
         
          // Query the user and traverse down the 'yes' or 'no' branch accordingly
         if (queryUser("Please answer (yes/no): ")) {
            // Traverse down the 'yes' branch
            node.yesNode = play(node.yesNode);
         } else {
            // Traverse down the 'no' branch
            node.noNode = play(node.noNode);
         }
      }

      return node;
   }

   /*
      Recursive method for learning a new node based on user input.
      Creates a new question node or answer node and updates the tree accordingly.
      Parameter:
         - node: The current node being considered in the binary tree.
      Return: 
         - The newly created or updated node based on user input.
      Precondition:
         - The 'node' parameter is not null.
      Postcondition:
         - The binary tree is updated with a new question node or answer node.
   */
   private QuestionNode learnNewNode(QuestionNode node) {
      userInterface.print("I lose. What is your object? ");
      QuestionNode newNode = new QuestionNode(userInterface.nextLine());

      userInterface.print(
         "Type a yes/no question to distinguish your item from " + node + ": ");
      String newQuestion = userInterface.nextLine();

      userInterface.print("And what is the answer for your object? ");
      boolean answerForNewQuestion = userInterface.nextBoolean();

      // Create and return a new question node based on user input
      if (answerForNewQuestion) {
         // Create a new question node
         return new QuestionNode(newNode, node, newQuestion);
      } else {
         // Create a new question node
         return new QuestionNode(node, newNode, newQuestion);
      }
   }

   /*
      Saves the current game state to an output stream.
      Parameter:
         - output: The output stream to save to.
      Precondition:
         - 'output' is not null.
   */
   public void save(PrintStream output) {
      save(output, root);
   }

   /*
      Writes the binary tree structure to the output stream, including both
      question nodes and answer nodes, and their connections.
      
      Parameter:
         - out: The output stream to write the binary tree structure to.
         - node: The current node being considered in the binary tree.
      Precondition:
         - The 'out' parameter is not null.
         - The 'node' parameter is not null.
      Postcondition:
         - The binary tree structure is saved to the output stream.
   */
   private void save(PrintStream out, QuestionNode node) {
      if (node.isAnswer()) {
         // Save answer node
         out.println("A:" + node);
      } else {
         // Save question node
         out.println("Q:" + node);
         // Recursively save 'yes' branch
         save(out, node.yesNode);
         // Recursively save 'no' branch
         save(out, node.noNode);
      }
   }

   /*
      Loads a saved game state from a scanner.
      Parameter:
         - input: The scanner from which to load the state.
      Precondition: 
         - 'input' is not null.
   */
   public void load(Scanner input) {
      root = loadNode(input);
   }

   /*
      Reads and constructs the binary tree structure from the input scanner,
      including both question nodes and answer nodes, and their connections.
      Parameter:
         - input: The input scanner used to read the binary tree structure.
      Return:
         - The root node of the loaded binary tree structure.
      Precondition:
         - The 'input' parameter is not null.
      Postcondition:
         - The binary tree structure is read and constructed from the input scanner.
   */
   private QuestionNode loadNode(Scanner input) {
      if (!input.hasNextLine()) {
         return null;
      }

      String[] data = input.nextLine().split(":", 2);

      if (data[0].equals("A")) {
         // Load answer node
         return new QuestionNode(data[1]);
      } else {
         // Load question node
         return new QuestionNode(loadNode(input), loadNode(input), data[1]);
      }
   }

   /*
      Returns the total number of games played.
      Return:
         - The total number of games played.
      Postcondition:
         - Returns a non-negative integer representing the total number of games played.
   */
   public int totalGames() {
      return totalGamesPlayed;
   }

   /*
      Returns the number of games won by the machine.
      Return:
         - The number of games won by the machine.
      Postcondition:
         - Returns a non-negative integer representing the number of games won by the machine.
   */
   public int gamesWon() {
      return gamesWonByMachine;
   }

   /*
      Asks the user a yes/no question based on the provided message and returns their response.
      Parameter:
         - message: The message to display to the user as the yes/no question.
      Return:
         - True if the user's response indicates 'yes', false if the response indicates 'no'.
      Precondition:
         - The 'message' parameter is not null.
      Postcondition:
         - The user is prompted with the provided message and their response is obtained.
         - The response is read and returned as a boolean value. 
   */
   private boolean queryUser(String message) {
      userInterface.print(message);
      return userInterface.nextBoolean();
   }
}