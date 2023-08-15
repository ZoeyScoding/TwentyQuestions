/*
   Programmer: Ziwei Shen
   Class: CS 145
   Date: August. 8st, 2023
   Subject: Lab 6 Twenty Questions
   Purpose: The program represents nodes in a binary tree used for a guessing game. 
            The binary tree is utilized to play a game where the computer asks a 
            series of yes/no questions to guess an object that the player is thinking
            of. The QuestionNode class encapsulates the structure of the binary tree, 
            with each node containing references to its "yes" and "no" child nodes, 
            as well as holding a piece of data (a question or an answer). This code 
            facilitates the organization and management of the nodes in the game's 
            question tree, enabling the game to ask questions, make guesses, and learn 
            from the player's responses.
*/

public class QuestionNode {
   // Pointer to the "yes" child node
   public QuestionNode yesNode;
   // Pointer to the "no" child node
   public QuestionNode noNode;
   // Data stored in this node (a question or an answer)
   private String data;

   /*
      Constructor for creating a leaf node (Answer node) with the given data.
      Parameter: 
         - data: The data to be stored in the node (an answer to a question)
   */
   public QuestionNode(String data) {
      this(null, null, data);
   }

   /*
      Constructor for creating a node with the given "yes" and "no" child nodes and data.
      Parameter:
         - yesNode: The "yes" child node
         - noNode: The "no" child node
         - data: The data to be stored in the node (a question to ask)
   */
   public QuestionNode(QuestionNode yesNode, QuestionNode noNode, String data) {
      this.yesNode = yesNode;
      this.noNode = noNode;
      this.data = data;
   }

   /*
      Checks if the current node is an Answer node (leaf node).
      Return:
         - True if the node has no "yes" or "no" child nodes (an Answer node), false otherwise.
   */
   public boolean isAnswer() {
      return yesNode == null && noNode == null;
   }

   /*
      Returns a string representation of the data stored in the node.
      Return:
         - The data stored in the node (a question or an answer).
   */
   public String toString() {
      return data;
   }
}