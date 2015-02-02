//David Robertson
//dmr73

public class wordSearchGame
{
  public static void main(String[] args)
  {
    //set default values
    int cols=6;
    String dict="dict10.txt";
    String board=null;
    int nVal;
    StdOut.println("Welcome to WORD SEARCH! Run with \"-help\" for cmd-line options.");
    
    //look through input for settings
    for(int k=0; k<args.length; k++)
    {
      //StdOut.println(args[k]);
      //Check for help request
      //If requested, print help options
      if(args[k].equals("-help"))
      {
        StdOut.println("Options:");
        StdOut.println("\"-board FILENAME\": Specifies game board file.");
        StdOut.println("\"-dict FILENAME\": Specifies dictionary file.");
        StdOut.println("\"-cols NUMCOLS\": Specifies the number of columns for printing words.");
      }
      
      //check for board setting
      if(args[k].equals("-board"))
      {
        k++;
        //set board
        board=args[k];
      }
      
      //check for dictionary setting
      if(args[k].equals("-dict"))
      {
        k++;
        //set dictionary
        dict=args[k];
      }
      //check for cols setting
      if(args[k].equals("-cols"))
      {
        k++;
        //set cols variable
        cols=Integer.parseInt(args[k]);
      }
           
    }
    //Check if board was entered; result FALSE
    if(board==null)
    {
      //ask user for board
      StdOut.println("Please enter a board: ");
      board= StdIn.readLine();
    }
    
////////////////////////////////////////////////////////////////////////////    

//Read dictionary from disk to bag
    In dictFile=new In(dict);
    //set stopwatch
    Stopwatch dClock = new Stopwatch();
    //add all dictionary items to dBag
    Bag<String> dBag = new Bag<String>();
    while(!dictFile.isEmpty())
    {
      dBag.add(dictFile.readString());
    }
    //Stop stopwatch; print result
    StdOut.println("Reading dictionary ("+dBag.size()+" words) from disk: " + dClock.elapsedTime() +" seconds.");
    
    
//Read dictionary from Bag to TST
    TST<String> dTST= new TST<String>();
    //Set stopwatch
    Stopwatch dTClock= new Stopwatch();
    //put objects in TST
    int val=0;
    String counter=""+val;
    for(String s : dBag)
    {
    
      //StdOut.println(dTST.get(s));///////////////////////////////////////////////////////
      
      dTST.put(s,counter);
      //StdOut.println(dTST.contains(s));
      val++;
      counter=""+val;
      
    }
    
    //Print results with time
    StdOut.println("Putting dictionary into ternary-search-trie: "+dTClock.elapsedTime() +" seconds.");

   // StdOut.println("\nGame Board:");
//open board text file
    In boardFile= new In(board);
    //set n value
    nVal= boardFile.readInt();
    boardFile.readLine();
    //create 2d array for game board
    String[][] bArray = new String[nVal][nVal];
    int tempR=0;
// while there are lines left in file
    String tLine;
    String[] temp=new String[nVal];
    while(boardFile.hasNextLine())
    {
      //temp string of characters
      tLine= boardFile.readLine();
      //StdOut.println(tLine);
      temp= tLine.split(" ");
      //store temp string into 2d array
      for(int t=0;t<nVal; t++)
      {
        bArray[tempR][t]=temp[t];
      }
      tempR++;
      //print line
     
    }
    
    tempR=0;
/*
    String s;
    while(tempR<nVal)
    {
      for(int t=0; t<nVal; t++)
      {
        s=bArray[tempR][t];
        StdOut.println(s);
      }
      
      tempR++;
    }
 *///2d array testing
    
    //create a TST for game words
    TST<String> game= new TST<String>();
    
    
    //set timer
    Stopwatch searchClock= new Stopwatch();
    //used for searching words
    String wSearch="";
    val=0;
    Iterable<String> test;
    int dcounter=0;
///////    //////Exhaustive Search ~includes wildcard////////////////
    
//iterate through rows of 2d array containing gameboard    
    for(int row=0;row<nVal;row++)
    {
      //iterate through columns of 2d array
      for(int c=0;c<nVal;c++)
      {
//left searching current pos
        //create string of possible words to the left of current pos in array
        for(int left=c;left>=0;left--)
        {
          wSearch=wSearch+bArray[row][left].toLowerCase();
                   //   StdOut.println("left " + wSearch);
          //start search if string is between [4,10]
          if(wSearch.length()>3 && wSearch.length()<11)
          {
            test= dTST.wildcardMatch(wSearch);
             for(String s : test)
             {
                counter=""+val;
                if(!game.contains(s))
                {
                  game.put(s,counter);
                  val++;
                }

              }
          }
        }

//end left search
        
        //reset search string
        wSearch="";
        
//Start right search from current pos on board
        //create string of possible words to the right of current pos in array
        for(int right=c;right<nVal;right++)
        {
          wSearch=wSearch+bArray[row][right].toLowerCase();
                    //  StdOut.println("right "+ wSearch);
          //start search if string is between [4,10]
          if(wSearch.length()>3 && wSearch.length()<11)
          {
            test= dTST.wildcardMatch(wSearch);
             for(String s : test)
             {
                counter=""+val;
                if(!game.contains(s))
                {
                  game.put(s,counter);
                  val++;
                }
                

              }
          }
        }
//end right search
        wSearch="";
        
////////Start up search
        //Start up search from current pos on board
        //create string of possible words upward of current pos in array
        for(int up=row;up>=0;up--)
        {
          wSearch=wSearch+bArray[up][c].toLowerCase();
                     // StdOut.println("up "+ wSearch);
          //start search if string is between [4,10]
          if(wSearch.length()>3 && wSearch.length()<11)
          {
            test= dTST.wildcardMatch(wSearch);
             for(String s : test)
             {
                counter=""+val;
                if(!game.contains(s))
                {
                  game.put(s,counter);
                  val++;
                }
                

              }
          }
        }
///////end up search
        wSearch="";
        
////////Start down search
        //Start down search from current pos on board
        //create string of possible words downward of current pos in array
        for(int down=row;down<nVal;down++)
        {
          wSearch=wSearch+bArray[down][c].toLowerCase();
                     // StdOut.println("down "+ wSearch);
          //start search if string is between [4,10]
          if(wSearch.length()>3 && wSearch.length()<11)
          {
            test= dTST.wildcardMatch(wSearch);
             for(String s : test)
             {
                counter=""+val;
                if(!game.contains(s))
                {
                  game.put(s,counter);
                  val++;
                }
                

              }
          }
        }
///////end down search
        wSearch="";
        
////////Start up left diagonal search
        //Start up left diagonal search from current pos on board
        //create string of possible words up left diagonally of current pos in array
        dcounter=c;
        for(int upD=row;upD>=0;upD--)
        {
          if(dcounter>=0)
          {
          wSearch=wSearch+bArray[upD][dcounter].toLowerCase();
                     // StdOut.println("up left D "+ wSearch);
          //start search if string is between [4,10]
          if(wSearch.length()>3 && wSearch.length()<11)
          {
            test= dTST.wildcardMatch(wSearch);
             for(String s : test)
             {
                counter=""+val;
                if(!game.contains(s))
                {
                  game.put(s,counter);
                  val++;
                }
                
             }
              }
          }
          dcounter--;
         
        }
///////end up left diagonal search
        wSearch="";
        
////////Start up Right diagonal search
        //Start up Right diagonal search from current pos on board
        //create string of possible words up right diagonally of current pos in array
        dcounter=c;
        for(int upD=row;upD>=0;upD--)
        {
          if(dcounter<nVal)
          {
          wSearch=wSearch+bArray[upD][dcounter].toLowerCase();
                      //StdOut.println("up right D "+ wSearch);
          //start search if string is between [4,10]
          if(wSearch.length()>3 && wSearch.length()<11)
          {
            test= dTST.wildcardMatch(wSearch);
             for(String s : test)
             {
                counter=""+val;
                if(!game.contains(s))
                {
                  game.put(s,counter);
                  val++;
                }
                
             }
              }
          }
          dcounter++;
         
        }
///////end up Right diagonal search
        wSearch="";
        
////////Start down left diagonal search
        //Start down left diagonal search from current pos on board
        //create string of possible words down left diagonally of current pos in array
        dcounter=c;
        for(int downD=row;downD<nVal;downD++)
        {
          if(dcounter>=0)
          {
          wSearch=wSearch+bArray[downD][dcounter].toLowerCase();
                      //StdOut.println("down left D "+ wSearch);
          //start search if string is between [4,10]
          if(wSearch.length()>3 && wSearch.length()<11)
          {
            test= dTST.wildcardMatch(wSearch);
             for(String s : test)
             {
                counter=""+val;
                if(!game.contains(s))
                {
                  game.put(s,counter);
                  val++;
                }
                
             }
              }
          }
          dcounter--;
         
        }
///////end down left diagonal search
        wSearch="";
        
////////Start down right diagonal search
        //Start down right diagonal search from current pos on board
        //create string of possible words down right diagonally of current pos in array
        dcounter=c;
        for(int downD=row;downD<nVal;downD++)
        {
          if(dcounter<nVal)
          {
          wSearch=wSearch+bArray[downD][dcounter].toLowerCase();
                      //StdOut.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!down right D "+ wSearch);
          //start search if string is between [4,10]
          if(wSearch.length()>3 && wSearch.length()<11)
          {
            test= dTST.wildcardMatch(wSearch);
             for(String s : test)
             {
                counter=""+val;
                if(!game.contains(s))
                {
                  game.put(s,counter);
                  val++;
                }
                
             }
              }
          }
          dcounter++;
         
        }
///////end down right diagonal search
        wSearch="";
      }
    }
    
/////////////////////////////////// End Exhaustive Search///////////////////////////////////
//all found words in game TST
    val=0;
    StdOut.println("Time to find all words in gameboard: " + searchClock.elapsedTime() +" seconds");
    
    
//print out the gameboard    
    StdOut.println("\nGame Board:");
//open board text file
    boardFile= new In(board);
    boardFile.readLine();
// while there are lines left in file
    while(boardFile.hasNextLine())
    {
      tLine= boardFile.readLine();
      StdOut.println(tLine);
      //print line
    }
    
    
    StdOut.println("\nYou can now type in words. Type a non-alphabetic symbol and ENTER to quit\nplaying and list all the words on the game board.");
    //take user input
    String guess="";
    boolean quit=false;
    char[] inputTest;
    val=0;
    counter="";
    TST<String> userMatches= new TST<String>();
    //while user input is alphabetic
    while(!quit)
    {
      guess= StdIn.readLine();
      //check user input for quit char
      inputTest = guess.toCharArray();
      for (char c : inputTest) 
      {
          if(!Character.isLetter(c))
          {
              quit=true;
          }
      }
      if(quit==true)
        break;

      //make user input lowercase
      guess= guess.toLowerCase();
      //check against found words
      if(game.contains(guess))
      {
        //make sure value is not already in user matches
        if(!userMatches.contains(guess))
        {
          counter=""+val;
         // guess=guess.toUpperCase();
          userMatches.put(guess,counter);
          val++;
        }
        StdOut.println("Yes, \""+guess.toUpperCase()+"\" is in the dictionary and on the game board.");
      }
      //if user guess is incorrect
      if(!game.contains(guess))
      {
        StdOut.println("No, \""+guess.toUpperCase()+"\" is not both in the dictionary and on the game board.");
      }
      
    }
    
    //list the words on game board
    StdOut.println("\nList of all words on the game board:");
    Iterable<String> gameKeys= game.keys();
    val=1;
    for(String s : gameKeys)
             {
                if(val>cols)
                {
                  StdOut.println();
                  val=1;
                }
                StdOut.printf("%10s  ",s.toUpperCase());
                val++;
              }
    StdOut.println("\n\n");
    //list the words that the user found
    StdOut.println("List of words that you found:");
    gameKeys=userMatches.keys();
    val=1;
    for(String s: gameKeys)
    {
      if(val>cols)
      {
        StdOut.println();
        val=1;
      }
      StdOut.printf("%10s  ",s.toUpperCase());
      val++;
    }
    StdOut.println("\n\n");
    
    //print number of words found and the percentage found
    float percent=(float) userMatches.size()/game.size() *100;
    StdOut.printf("You found " +userMatches.size()+" out of "+ game.size()+" words, or %.2f percent.",percent);
    StdOut.print("\nPlay again? [Y/N]");
    String replay= StdIn.readLine();
    replay= replay.toLowerCase();
    if(replay.equals("y"))
    {
      StdOut.println("\n");
      //String r="-board "+board+" -cols "+cols+ " -dict "+dict;
      wordSearchGame.main(new String[] {"-board",board,"-cols",cols+"","-dict",dict});
    }
  }//end main
}//end program