package index;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

// Your class. Notice how it has no generics.
// This is because we use generics when we have no idea what kind of data we are getting
// Here we know we are getting two pieces of data:  a string and a line number
public class IndexTree {

	// This is your root 
	// again, your root does not use generics because you know your nodes
	// hold strings, an int, and a list of integers
	private IndexNode root;
	
	// Make your constructor
	// It doesn't need to do anything
	public IndexTree(){

	}
	// complete the methods below
	
	// this is your wrapper method
	// it takes in two pieces of data rather than one
	// call your recursive add method
	public void add(String word, int lineNumber){
		add(root, word, lineNumber);
	}
	
	
	
	// your recursive method for add
	// Think about how this is slightly different  the regular add method
	// When you add the word to the index, if it already exists, 
	// you want to  add it to the IndexNode that already exists
	// otherwise make a new indexNode
	private IndexNode add(IndexNode root, String word, int lineNumber){
		if(this.root == null)
		{
			this.root = new IndexNode(word, lineNumber);
			return root;
		}
		else if(root.word.equals(word))
		{
			root.occurences++;
			root.list.add(lineNumber);
			return root;
		}
		else if(root.word.compareTo(word) > 0)
		{
			if(root.left == null)
			{
				root.left = new IndexNode(word, lineNumber);
				return root.left;
			}
			else
			{
				return add(root.left, word, lineNumber);
			}
		}
		else
		{
			if(root.right == null)
			{
				root.right = new IndexNode(word, lineNumber);
				return root.right;
			}
			else
			{
				return add(root.right, word, lineNumber);
			}
		}
	}
	
	
	
	
	// returns true if the word is in the index
	public boolean contains(String word){
		IndexNode current = root;
		while(current != null)
		{
			if(current.word.compareTo(word) > 0)
			{
				current = current.left;
			}
			else if(current.word.compareTo(word) < 0)
			{
				current = current.right;
			}
			else
			{
				return true;
			}
		}
		return false;
	}
	
	// call your recursive method
	// use book as guide
	public void delete(String word){
		delete(root, word);
	}
	
	// your recursive case
	// remove the word and all the entries for the word
	// This should be no different than the regular technique.
	private IndexNode delete(IndexNode root, String word){
		if(root == null)
		{
			return null;
		}
		else if(root.word.compareTo(word) > 0)
		{
			return delete(root.right, word);
		}
		else if(root.word.compareTo(word) < 0)
		{
			return delete(root.left, word);
		}
		else
		{
			if(root.left == null && root.right == null)
			{
				root = null;
				return root;
			}
			else if(root.left == null)
			{
				root = root.right;
				return root;
			}
			else
			{
				IndexNode temp = root.left;
				while(temp.right != null)
				{
					temp = temp.right;
				}
				root = temp;
				temp = null;
				return root;
			}
		}
	}
	
	
	// prints all the words in the index in inorder order
	// To successfully print it out
	// this should print out each word followed by the number of occurrences and the list of all occurrences
	// each word and its data gets its own line
	public void printIndex(){
		IndexNode currentNode = root;
		while(currentNode.left != null)
		{
			currentNode = currentNode.left;
		}
		System.out.println(currentNode.word);
	}
	
	public static void main(String[] args){
		IndexTree index = new IndexTree();
		String fileName = "pg100.txt";

		try {
			int lineNumber = 0;
			Scanner scanner = new Scanner(new File(fileName));
			while(scanner.hasNextLine()){
				lineNumber++;
				String line = scanner.nextLine();
				String[] words = line.split("\\s+");
				for(String word : words){
					word = word.replaceAll(":", "");
					word = word.replaceAll(",", "");
					// add all the words to the tree
					if(!word.equals(""))
					{
						index.add(word, lineNumber);
					}

				}
			}
			scanner.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// print out the index
		IndexNode currentNode = index.root;
		while(currentNode.left != null)
		{
			System.out.println(currentNode.word);
			currentNode = currentNode.left;
		}
		System.out.println(currentNode.word);
		// test removing a word from the index


		
	}
}
