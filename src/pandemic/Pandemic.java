package pandemic;

import pandemic.cards.RoleCard;
import pandemic.cards.PlayerCard;
import pandemic.cards.InfectionCard;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import pandemic.board.Network;
import pandemic.board.Node;

/**
 * @author Rylo
 */
public class Pandemic
{
    //Main class runs everything and is super-general, ergo a LOT of specifications are needed. Most are unimplemented currently.
    public static final int NOT_A_COLOR = -1;
    public static final int BLACK = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;
    public static final int YELLOW = 3;
    public static final int PURPLE = 4;
    Network<String, City> board = new Network();
    ArrayList<ArrayList<PlayerCard>> playerDeck = new ArrayList();
    ArrayList<PlayerCard> playerDiscard = new ArrayList();
    ArrayList<ArrayList<InfectionCard>> infectionDeck = new ArrayList();
    ArrayList<InfectionCard> infectionDiscard = new ArrayList();
    ArrayList<PlayerCard> virulentEpidemics = new ArrayList();
    ArrayList<PlayerCard> events = new ArrayList();
    ArrayList<PlayerCard> mutationPlayerCards = new ArrayList();
    ArrayList<PlayerCard> modEvents = new ArrayList();
    ArrayList<RoleCard> roleDeck = new ArrayList();
    ArrayList<Player> players = new ArrayList();
    boolean infectionRateUp = true;
    boolean multiEarthMod = false;
    boolean biohazardMod = false;
    boolean coldWarMod = false;
    boolean gameMasterMod = false;
    boolean bioWarMod = false;
    boolean tradePatterns = false;
    boolean oneDiseaseMod = false;
    boolean outbreakLoseCase = false;
    boolean noPlayerCardsLoseCase = false;
    boolean cubesLossCase = false;
    boolean cureAllWinCase = false;
    boolean eradicateAllWinCase = false;
    boolean deadPlayersComeBack = false;
    boolean luckBasedInfections = false; //0/6 to 6/6 chance of outbreak onan infection
    boolean luckBasedDeaths = false;
    boolean quietNight = false;
    int numOfMaps = 1;
    int infectionTrack[] = new int[]
    {
	2, 2, 2, 3, 3, 4, 4
    };
    int turn = 1;
    int infectionTrackLocation = 0;
    int outbreaks = 0;
    int infectionCount = 0;
    int epidemics;
    int commercialTravelBan = 0;
    private boolean bioTerroristMod;

    private void selectGameType()
    {
	Scanner in = new Scanner(System.in);
	System.out.println("How many players are you playing with?");
	int numOfPlayers = Integer.parseInt(in.nextLine());
	for (int i = 0; i < numOfPlayers; i++)
	{
	    //this.players.add(new Player("Player " + (i + 1)));
	    System.out.println("What is the next player's name?");
	    String name = in.nextLine();
	    this.players.add(new Player(name));
	}
	System.out.println("You are playing with ... " + this.players.size() + " players.");

	System.out.println("\n\nHow many epidemics are you playing with?");
	this.epidemics = in.nextInt();

	System.out.println("True or false? You are playing with the game-master mod?");
	this.gameMasterMod = in.nextBoolean();
    }

    private void setUpBoard()
    {
	initializeStructures();
	loadBoard();
	this.testSetup();//not a necessary method obviously
	initializeGame();
	//in = new Scanner(new File("numbers.txt"));

    }//setUpBoard

    private void initializeStructures()
    {
	this.infectionDeck.add(new ArrayList<InfectionCard>());
	this.playerDeck.add(new ArrayList<PlayerCard>());
    }

    private void loadBoard()
    {
	try
	{
	    Scanner in = new Scanner(new File("Board2.txt"));

	    String line = in.nextLine();
	    line = line.substring(1, line.length());
	    System.out.println("The program read line = " + line);
	    //System.out.println("first char = " + (int)line.charAt(0));
	    //System.out.println("last char = " + line.charAt(line.length()-1));
	    //System.out.println("line has " + line.length() + " length");
	    //System.out.println("CITIES: has " + "CITIES:".length() + " length");
	    while (line.compareTo("END") != 0)
	    {
		if (line.compareTo("CITIES:") == 0)
		{
		    System.out.println("Loading cities...");
		    this.loadCities(in);
		}
		else if (line.compareTo("EDGES:") == 0)
		{
		    System.out.println("Loading connections...");
		    this.loadEdges(in);
		}
		else if (line.compareTo("VIRULENT EPIDEMICS:") == 0)
		{
		    System.out.println("Loading virulent epidemics...");
		    this.loadVirulentEpidemics(in);
		}
		else if (line.compareTo("MUTATION PLAYER CARDS:") == 0)
		{
		    System.out.println("Loading mutation player cards...");
		    this.loadMutationPlayerCards(in);
		}
		else if (line.compareTo("MOD EVENTS:") == 0)
		{
		    System.out.println("Loading mod events...");
		    this.loadModEvents(in);
		}
		else if (line.compareTo("ROLES:") == 0)
		{
		    System.out.println("Loading roles...");
		    this.loadRoles(in);
		}
		else if (line.compareTo("EVENTS:") == 0)
		{
		    System.out.println("Loading events...");
		    this.loadEvents(in);
		}
		else
		{
		    System.out.println("Could not read this command: " + line);
		}
		line = in.nextLine();
		System.out.println("We are loading " + line + " next!");

	    }
	}
	catch (FileNotFoundException e)
	{
	    System.out.println("Could not find the board text file.");
	}
    }

    private void initializeGame()
    {
	shuffleTopDecks();

	System.out.println("About to infect initial nine cities, this is infection deck:");
	this.displayInfectionDeck();

	//Infect nine cities.
	for (int i = 0; i < 9; i++)
	{
	    InfectionCard toInfect = drawInfectionCard();
	    this.infectionDiscard.add(toInfect);
	    toInfect.getCity().setNaturalColorCubes(i / 3 + 1);
	}

	System.out.println("Have now infected nine cities, infection deck now appears as:");
	this.displayInfectionDeck();

	System.out.println("There are " + this.playerDeck.get(0).size() + " cards in the player deck.");

	//Deal initial cities
	System.out.println("this.players.size() = " + this.players.size());
	switch (this.players.size())
	{
	    case 2:
		for (int i = 0; i < 8; i++)
		{
		    this.players.get(i % 2).addCard(this.drawPlayerCard());
		}
		break;
	    case 3:
		for (int i = 0; i < 9; i++)
		{
		    this.players.get(i % 3).addCard(this.drawPlayerCard());
		}
		break;
	    case 4:
		for (int i = 0; i < 8; i++)
		{
		    this.players.get(i % 4).addCard(this.drawPlayerCard());
		}
		break;
	    case 5:
		for (int i = 0; i < 10; i++)
		{
		    this.players.get(i % 5).addCard(this.drawPlayerCard());
		}
		break;
	}

	System.out.println("There are " + this.playerDeck.get(0).size() + " cards in the player deck after dealing out cities.");

	//Select player order!
	for (int i = 0; i < this.players.size() - 1; i++)
	{
	    for (int j = i + 1; j < this.players.size(); j++)
	    {
		if (this.players.get(i).getLargestPopulation() < this.players.get(j).getLargestPopulation())
		{
		    //i < j for player order. Need to switch.
		    Player temp = this.players.get(i);
		    this.players.set(i, this.players.get(j));
		    this.players.set(j, temp);
		}
	    }
	}

	//Put in events!
	for (int i = 0; i < 2 * this.players.size(); i++)
	{
	    PlayerCard event = this.events.get(0);
	    this.events.remove(0);
	    this.playerDeck.get(0).add(event);
	}

	System.out.println("There are " + this.playerDeck.get(0).size() + " cards in the player deck after adding in events.");


	shuffleTopPlayerDeck();
	ArrayList<ArrayList<PlayerCard>> decks = new ArrayList<>();

	//create the decks needed
	for (int i = 0; i < this.epidemics; i++)
	{
	    decks.add(new ArrayList<PlayerCard>());
	}

	//deal out the decks
	for (int i = 0; i < this.playerDeck.get(0).size(); i++)
	{
	    decks.get(i % epidemics).add(playerDeck.get(0).get(i));
	    System.out.println("Dealing " + playerDeck.get(0).get(i) + " to the " + (i % epidemics) + "th deck");
	    System.out.println("Deck " + i % epidemics + " is " + decks.get(i % epidemics));
	}

	//add in the epidemics and shuffle
	for (int i = 0; i < epidemics; i++)
	{
	    decks.get(i).add(new PlayerCard("Epidemic", "Epidemic"));
	    decks.set(i, shufflePlayerCards(decks.get(i)));
	    System.out.println("The " + i + "th deck has " + decks.get(i).size() + " cards in it.");
	}

	playerDeck = decks; //this should be the derpily obvious way to do this
	System.out.println("There are " + this.playerDeck.get(0).size() + " cards in the top player deck after reassembling partial decks.");
	//this.testSetup();

	//get the players to atlanta, give them roles
	if (!this.bioTerroristMod)
	{
	    for (int i = 0; i < this.roleDeck.size(); i++)
	    {
		if (this.roleDeck.get(i).getName().compareTo("Bio-Terrorist") == 0)
		{
		    this.roleDeck.remove(i);
		}
	    }
	}
	for (int i = 0; i < this.players.size(); i++)
	{
	    this.players.get(i).setLocation(this.board.get("Atlanta"));
	    this.board.get("Atlanta").addPlayer(this.players.get(i));
	    RoleCard role = this.roleDeck.get(0);
	    this.roleDeck.remove(0);
	    this.players.get(i).setRole(role);
	}
    }

    private void shuffleTopDecks()
    {
	shuffleTopInfectionDeck();
	shuffleTopPlayerDeck();
	shuffleEvents();
	shuffleRoleDeck();
    }

    private void shuffleTopInfectionDeck()
    {
	ArrayList<InfectionCard> temp = (ArrayList<InfectionCard>) this.infectionDeck.get(0).clone();
	this.infectionDeck.get(0).clear();
	while (!temp.isEmpty())
	{
	    int index = (int) ((double) (temp.size()) * Math.random());
	    this.infectionDeck.get(0).add(temp.get(index));
	    temp.remove(index);
	}
    }

    private void shuffleTopPlayerDeck()
    {
	System.out.println("Top player deck just before shuffle has size = " + this.playerDeck.get(0).size());
	ArrayList<PlayerCard> temp = (ArrayList<PlayerCard>) this.playerDeck.get(0).clone();
	this.playerDeck.get(0).clear();
	while ((temp.size() > 0))
	{
	    int index = (int) ((double) (temp.size()) * Math.random());
	    this.playerDeck.get(0).add(temp.get(index));
	    temp.remove(index);
	    System.out.println("Selected " + index + "th element");
	}
	System.out.println("Top Player Deck shuffled and has size = " + this.playerDeck.get(0).size());
    }

    private void shuffleRoleDeck()
    {
	ArrayList<RoleCard> temp = (ArrayList<RoleCard>) this.roleDeck.clone();
	this.roleDeck.clear();
	while (!temp.isEmpty())
	{
	    int index = (int) ((double) (temp.size()) * Math.random());
	    this.roleDeck.add(temp.get(index));
	    temp.remove(index);
	}
    }

    private ArrayList<PlayerCard> shufflePlayerCards(ArrayList<PlayerCard> input)
    {
	ArrayList<PlayerCard> temp = (ArrayList<PlayerCard>) input.clone();
	input.clear();
	while (!temp.isEmpty())
	{
	    int index = (int) ((double) (temp.size()) * Math.random());
	    input.add(temp.get(index));
	    temp.remove(index);
	}
	return input;
    }

    private void shuffleEvents()
    {
	ArrayList<PlayerCard> temp = (ArrayList<PlayerCard>) this.events.clone();
	this.events.clear();
	while (!temp.isEmpty())
	{
	    int index = (int) ((double) (temp.size()) * Math.random());
	    this.events.add(temp.get(index));
	    temp.remove(index);
	}
    }

    private void loadCities(Scanner in)
    {
	//Yes, this method is much longer than really necessary, but it works.
	//Minimize it if it angers you. It'll get refactored when everything else works.
	String line = in.nextLine();
	System.out.println("We are loading cities and read line = " + line);
	while (line.compareTo("END CITIES") != 0)
	{
	    if (line.compareTo("BLUE:") == 0)
	    {
		System.out.println("Loading blue cities...");
		line = in.nextLine();
		System.out.println("Read..." + line);
		while (line.compareTo("END COLOR") != 0)
		{
		    if (line.length() > 0)
		    {
			int population = Integer.parseInt(in.nextLine());
			int popDensity = Integer.parseInt(in.nextLine());
			String nation = in.nextLine();
			char lastChar = line.charAt(line.length() - 1);
			boolean government = (lastChar == '!');
			boolean researchStation = (lastChar == '?');
			boolean both = (lastChar == '‽');
			if (government || researchStation || both)
			{
			    line = line.substring(0, line.length() - 1);
			}
			if (both)
			{
			    government = true;
			    researchStation = true;
			}
			this.board.addNode(line, new City(line, Pandemic.BLUE, government, researchStation, population, popDensity, nation));
			this.board.get(line).setMyNode(this.board.getNode(line));
			this.infectionDeck.get(0).add(new InfectionCard(this.board.get(line)));//add infection card linked to city
			this.playerDeck.get(0).add(new PlayerCard(this.board.get(line)));//and the player card!
			System.out.println("Added " + line + " to the player and infeciton top decks");
		    }
		    line = in.nextLine();
		    System.out.println("Read..." + line);
		}
	    }
	    else if (line.compareTo("YELLOW:") == 0)
	    {
		System.out.println("Loading yellow cities...");
		line = in.nextLine();
		System.out.println("line = " + line);
		while (line.compareTo("END COLOR") != 0)
		{
		    if (line.length() > 0)
		    {
			int population = Integer.parseInt(in.nextLine());
			int popDensity = Integer.parseInt(in.nextLine());
			String nation = in.nextLine();
			char lastChar = line.charAt(line.length() - 1);
			boolean government = (lastChar == '!');
			boolean researchStation = (lastChar == '?');
			boolean both = (lastChar == '‽');
			if (government || researchStation || both)
			{
			    line = line.substring(0, line.length() - 1);
			}
			if (both)
			{
			    government = true;
			    researchStation = true;
			}
			this.board.addNode(line, new City(line, Pandemic.YELLOW, government, researchStation, population, popDensity, nation));
			this.board.get(line).setMyNode(this.board.getNode(line));
			this.infectionDeck.get(0).add(new InfectionCard(this.board.get(line)));//add infection card linked to city
			this.playerDeck.get(0).add(new PlayerCard(this.board.get(line)));
		    }
		    line = in.nextLine();
		    System.out.println("Read..." + line);
		}
	    }
	    else if (line.compareTo("BLACK:") == 0)
	    {
		System.out.println("Loading black cities...");
		line = in.nextLine();
		while (line.compareTo("END COLOR") != 0)
		{
		    if (line.length() > 0)
		    {
			int population = Integer.parseInt(in.nextLine());
			int popDensity = Integer.parseInt(in.nextLine());
			String nation = in.nextLine();
			char lastChar = line.charAt(line.length() - 1);
			boolean government = (lastChar == '!');
			boolean researchStation = (lastChar == '?');
			boolean both = (lastChar == '‽');
			if (government || researchStation || both)
			{
			    line = line.substring(0, line.length() - 1);
			}
			if (both)
			{
			    government = true;
			    researchStation = true;
			}
			this.board.addNode(line, new City(line, Pandemic.BLACK, government, researchStation, population, popDensity, nation));
			this.board.get(line).setMyNode(this.board.getNode(line));
			this.infectionDeck.get(0).add(new InfectionCard(this.board.get(line)));//add infection card linked to city
			this.playerDeck.get(0).add(new PlayerCard(this.board.get(line)));
		    }
		    line = in.nextLine();
		    System.out.println("Read..." + line);
		}
	    }
	    else if (line.compareTo("RED:") == 0)
	    {
		System.out.println("Loading red cities...");
		line = in.nextLine();
		while (line.compareTo("END COLOR") != 0)
		{
		    if (line.length() > 0)
		    {
			int population = Integer.parseInt(in.nextLine());
			int popDensity = Integer.parseInt(in.nextLine());
			String nation = in.nextLine();
			char lastChar = line.charAt(line.length() - 1);
			boolean government = (lastChar == '!');
			boolean researchStation = (lastChar == '?');
			boolean both = (lastChar == '‽');
			if (government || researchStation || both)
			{
			    line = line.substring(0, line.length() - 1);
			}
			if (both)
			{
			    government = true;
			    researchStation = true;
			}
			this.board.addNode(line, new City(line, Pandemic.RED, government, researchStation, population, popDensity, nation));
			this.board.get(line).setMyNode(this.board.getNode(line));
			this.infectionDeck.get(0).add(new InfectionCard(this.board.get(line)));//add infection card linked to city
			this.playerDeck.get(0).add(new PlayerCard(this.board.get(line)));
		    }
		    line = in.nextLine();
		    System.out.println("Read..." + line);
		}
	    }
	    else
	    {
		System.out.println("Failed to read this line: " + line);
	    }
	    line = in.nextLine();
	    System.out.println("Our next line to read is " + line);
	}
	System.out.println("Finished loading cities!");
    }//loadCities()

    private void loadEdges(Scanner in)
    {
	System.out.println("loadEdges() entered");
	String line;
	line = in.nextLine();
	System.out.println("Read line = " + line);
	while (line.compareTo("END EDGES") != 0)
	{
	    boolean spansGap = (line.charAt(line.length() - 1) == '!');
	    //We need to keep track of whether or not it spans the gap for n-Earth mods
	    String city1 = line.substring(0, line.indexOf("-"));
	    String city2;
	    if (spansGap)
	    {
		city2 = line.substring(line.indexOf("-") + 1, line.length() - 1);
	    }
	    else
	    {
		city2 = line.substring(line.indexOf("-") + 1, line.length());
	    }
	    System.out.print("Loading connection ");
	    if (line.charAt(line.length() - 1) == '!')
	    {
		System.out.print("that spans the edge ");
	    }
	    System.out.println("between " + city1 + " and " + city2);
	    createEdge(city1, city2, spansGap);
	    line = in.nextLine();
	}

	//scramble all the connections for this game. Why not!?
	Iterator itr = this.board.getIterator();
	while (itr.hasNext())
	{
	    ((Node) (itr.next())).shuffleConnections();
	}

	System.out.println("Finished loading edges!");
    }

    private void createEdge(String city1, String city2, boolean spansGap)
    {
	if (numOfMaps > 1)
	{
	    if (spansGap)
	    {
		//TODO make city1 (left side by convention) one world higher up than city2
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	    }
	    else
	    {
		//TODO create as many copies of city1 and city2 as needed, draw connections on all n maps
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	    }
	}//Yet another mod to get handled
	else
	{
	    this.board.addEdge(city1, city2);
	}
    }

    private void loadVirulentEpidemics(Scanner in)
    {
	System.out.println("Loading Virulent Epidemics...");
	String line;
	line = in.nextLine();
	System.out.println("Read..." + line);

	while (line.compareTo("END VIRULENT EPIDEMICS") != 0)
	{
	    System.out.println("Read..." + line);
	    this.virulentEpidemics.add(new PlayerCard(line, "Virulent Epidemic"));
	    line = in.nextLine();
	}
    }

    private void loadEvents(Scanner in)
    {
	System.out.println("Loading Events...");
	String line;
	line = in.nextLine();
	System.out.println("Read..." + line);

	while (line.compareTo("END EVENTS") != 0)
	{
	    System.out.println("Read..." + line);

	    this.events.add(new PlayerCard(line, "Event"));
	    line = in.nextLine();
	}
    }

    private void loadMutationPlayerCards(Scanner in)
    {
	System.out.println("Loading Mutation Player Cards...");
	String line;
	line = in.nextLine();
	System.out.println("Read..." + line);

	while (line.compareTo("END MUTATION PLAYER CARDS") != 0)
	{
	    System.out.println("Read..." + line);

	    this.mutationPlayerCards.add(new PlayerCard(line, "Mutation"));
	    line = in.nextLine();
	}
    }

    private void loadModEvents(Scanner in)
    {
	System.out.println("Loading Mod Events...");
	String line;
	line = in.nextLine();
	System.out.println("Read..." + line);

	while (line.compareTo("END MOD EVENTS") != 0)
	{
	    System.out.println("Read..." + line);

	    this.modEvents.add(new PlayerCard(line, "Mod Event"));
	    line = in.nextLine();
	}
    }

    private void loadRoles(Scanner in)
    {
	System.out.println("Loading Roles...");
	String line;
	line = in.nextLine();
	System.out.println("Read..." + line);

	while (line.compareTo("END ROLES") != 0)
	{
	    System.out.println("Read..." + line);

	    this.roleDeck.add(new RoleCard(line));
	    line = in.nextLine();
	}
    }

    private void testSetup()
    {
	Scanner in = new Scanner(System.in);
	System.out.println("Enter a city to get data on!");
	//String line = in.nextLine();//Used to be more general, now works consistently
	String line = "Karachi";
	ArrayList<Node> connectedTo = this.board.getConnectedTo(line);
	System.out.print(line + " is adjacent to ");
	for (int i = 0; i < connectedTo.size(); i++)
	{
	    System.out.println((City) (connectedTo.get(i).getValue()) + ", ");
	}

	System.out.println("\n\nTesting infection deck now:");
	for (int i = 0; i < this.infectionDeck.get(0).size(); i++)
	{
	    System.out.println("Found " + i + "=" + this.infectionDeck.get(0).get(i));
	}

	System.out.println("\n\nTesting player deck now:");
	for (int i = 0; i < this.playerDeck.size(); i++)
	{
	    for (int j = 0; j < this.playerDeck.get(i).size(); j++)
	    {
		System.out.println("Found " + i + "'th deck, " + j + "'th card =" + this.playerDeck.get(i).get(j));

	    }
	    System.out.println();
	}

	System.out.println("\n\nTesting events deck now:");
	for (int i = 0; i < this.events.size(); i++)
	{
	    System.out.println("Found " + i + "=" + this.events.get(i).getName());
	}

	System.out.println("\n\nTesting mod events deck now:");
	for (int i = 0; i < this.modEvents.size(); i++)
	{
	    System.out.println("Found " + i + "=" + this.modEvents.get(i).getName());
	}

	System.out.println("\n\nTesting mutation player cards deck now:");
	for (int i = 0; i < this.mutationPlayerCards.size(); i++)
	{
	    System.out.println("Found " + i + "=" + this.mutationPlayerCards.get(i).getName());
	}

	System.out.println("\n\nTesting role deck now:");
	for (int i = 0; i < this.roleDeck.size(); i++)
	{
	    System.out.println("Found " + i + "=" + this.roleDeck.get(i).getName());
	}

	System.out.println("\n\nTesting virulent epidemics deck now:");
	for (int i = 0; i < this.virulentEpidemics.size(); i++)
	{
	    System.out.println("Found " + i + "=" + this.virulentEpidemics.get(i).getName());
	}

	System.out.println("\n\nTesting players now:");
	for (int i = 0; i < this.players.size(); i++)
	{
	    if (this.players.get(i) != null)
	    {
		System.out.println("Found " + i + "=" + this.players.get(i));
	    }
	}

	System.out.println("\n\nTesting infections on board now:");

	Iterator itr = this.board.getIterator();
	while (itr.hasNext())
	{
	    Node node = (Node) itr.next();
	    City city = (City) node.getValue();
	    if (city.infections() > 0)
	    {
		System.out.println("This city is infected:\n" + city);
	    }
	}
    }
    /*
    private void play()
    {
	Scanner in = new Scanner(System.in);
	manageActions(in);
	managePlayerCards(in);
	manageInfections(in);
	this.turn++;
    }//*///This method was killed when I decided to not manage Actions individually, but rather only those that affected cubes/locations

    private void playClunky()
    {
	if (this.players.get(this.turn % this.players.size()).getRole().getName().compareTo("Troubleshooter") == 0)//Wow. Dat boolean.
	{
	    troubleshoot();
	}
	this.displayKnowledgeOnInfections();
	if (this.gameMasterMod)//Everything starts unseen.
	{
	    Iterator itr = this.board.getIterator();
	    while (itr.hasNext())
	    {
		Node node = (Node) (itr.next());
		City city = (City) (node.getValue());
		city.setChanged(true);
	    }
	    //Need to show only the things adjacent to Atlanta to start!
	    this.displayAllChangedCities();
	}
	Scanner in = new Scanner(System.in);
	//String line = in.nextLine();
	boolean gameEnd = false;
	while (!gameEnd)
	{
	    System.out.println("What is your next command? (Remove [all] [color] from [city], Infect)\n\n");
	    String text = in.nextLine();

	    //TODO process String action into an Action action and return it.
	    String firstWord;
	    String afterFirstWord = "";
	    if (text.contains(" "))
	    {
		firstWord = text.substring(0, text.indexOf(" "));
		afterFirstWord = text.substring(text.indexOf(" ") + 1);
	    }
	    else
	    {
		firstWord = text;
	    }

	    if (firstWord.compareTo("remove") == 0)
	    {
		boolean all = false;
		int color = -1;
		if (afterFirstWord.substring(0, text.indexOf(" ")).compareTo("all") == 0)
		{
		    all = true;
		    afterFirstWord = afterFirstWord.substring(afterFirstWord.indexOf(" ") + 1);//cut out the word "all"
		}//Haha this never happened in implementation.
		String colorStr = afterFirstWord.substring(0, afterFirstWord.indexOf(" "));
		String fromCity = afterFirstWord.substring(afterFirstWord.indexOf(" ") + 1);
		if (colorStr.compareTo("red") == 0)
		{
		    color = RED;
		}
		else if (colorStr.compareTo("blue") == 0)
		{
		    color = BLUE;
		}
		else if (colorStr.compareTo("black") == 0)
		{
		    color = BLACK;
		}
		else if (colorStr.compareTo("yellow") == 0)
		{
		    color = YELLOW;
		}
		else if (colorStr.compareTo("purple") == 0)
		{
		    color = PURPLE;
		}
		fromCity = fromCity.substring(fromCity.indexOf(" ") + 1);
		this.board.get(fromCity).treat(color);
		System.out.println("We have treated " + colorStr + " in city " + fromCity + ", which now appears as " + this.board.get(fromCity));
	    }
	    else if (firstWord.compareTo("infect") == 0)
	    {
		this.managePlayerCards(in);
		this.eventManager(in);
		this.manageInfections(in);
		this.displayAllChangedCities();
		if (!this.gameMasterMod)
		{
		    this.defaultCityStates(true);//changed and outbroke variables adjusted
		}
		turn++;
		if (this.players.get(this.turn % this.players.size()).getRole().getName().compareTo("Troubleshooter") == 0)
		{
		    troubleshoot();
		}
		this.displayKnowledgeOnInfections();
	    }
	    else if (firstWord.compareTo("move") == 0)
	    {
		String name = afterFirstWord.substring(0, afterFirstWord.indexOf(" "));
		String location = afterFirstWord.substring(afterFirstWord.indexOf(" ") + 1);
		System.out.println("location=(" + location + ")");
		location = location.substring(location.indexOf(" ") + 1);
		System.out.println("location=(" + location + ")");

		//location = afterFirstWord.substring(afterFirstWord.indexOf(" ") + 1);//'of' exists
		Player player = null;
		for (int i = 0; i < this.players.size(); i++)
		{
		    if (this.players.get(i).getName().compareTo(name) == 0)
		    {
			player = this.players.get(i);
		    }
		}
		if (player == null)
		{
		    System.out.println("Player (" + player + ") not found");
		}
		else
		{
		    System.out.println("location=(" + location + ")");
		    this.move(player, location);
		    if (this.gameMasterMod)
		    {
			this.displayAllChangedCities();
		    }
		}
	    }
	    else if(firstWord.compareTo("event")==0)
	    {
		this.eventManager(in);
	    }
	}
    }
	    /*
    private void manageActions(Scanner in)
    {
	for (int i = 0; i < this.players.get(this.turn % this.players.size()).getActions(); i++)
	{
	    Action action = ((Player)(this.players.get(this.turn % this.players.size()))).get
	    System.out.println("Action " + i + ": " + action);
	    resolveAction(this.players.get(this.turn % this.players.size()), action);
	}
    }//*/

    private void resolveAction(Player player, Action action)
    {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//HI HI HI THIS IS PROBABLY A GOOD METHOD FOR JAMIE TO WANT ME TO IMPLEMENT

    private void managePlayerCards(Scanner in)
    {
	PlayerCard card = this.drawPlayerCard();
	if (!card.isNormalEpidemic() && !card.isVirulentEpidemic())//Epidemics resolve IMMEDIATELY
	{
	    this.players.get(this.turn % this.players.size()).addCard(card);
	    System.out.println("Player " + this.players.get(this.turn % this.players.size()).getName() + " has drawn " + card);
	}
	//...aaaand a second card
	card = this.drawPlayerCard();
	if (!card.isNormalEpidemic() && !card.isVirulentEpidemic())
	{
	    this.players.get(this.turn % this.players.size()).addCard(card);
	    System.out.println("Player " + this.players.get(this.turn % this.players.size()).getName() + " has drawn " + card);
	}
    }

    private void manageInfections(Scanner in)
    {
	if (!this.quietNight)
	{
	    int infections = this.infectionTrack[this.infectionTrackLocation];
	    if (commercialTravelBan > 0)
	    {
		infections = 1;
		this.commercialTravelBan--;
	    }
	    for (int i = 0; i < infections; i++)
	    {
		InfectionCard card = drawInfectionCard();
		int outbreaksTriggered = card.getCity().infect(card.getCity().getNaturalColor());
		this.outbreaks += outbreaksTriggered;
		this.infectionDiscard.add(card);
		if (outbreaksTriggered > 0)
		{
		    System.out.println("There were " + outbreaksTriggered + " more outbreaks, now totalling " + this.outbreaks);
		}
		else
		{
		    System.out.println("The last infection did not cause any outbreaks!");
		}
		if (outbreaks > 8 && this.outbreakLoseCase)
		{
		    throw new ArithmeticException("Sorry, I don't know how to count past 8 outbreaks.");//yeah need to manage lose cases better than this
		}
		this.defaultCityStates(false);
	    }
	}
	else
	{
	    this.quietNight = false;//had a quiet night. Don't get it again!
	}
    }

    //Picks up the card and you're just kinda holding it. Doesn't go anywhere, needs to be handled by user.
    private InfectionCard drawInfectionCard()
    {
	InfectionCard result = this.infectionDeck.get(0).get(0);
	this.infectionDeck.get(0).remove(0);
	while (this.infectionDeck.get(0).isEmpty())
	{
	    this.infectionDeck.remove(0);
	}
	return result;
    }

    //See above conditions for method
    private PlayerCard drawPlayerCard()
    {
	System.out.println("Top player deck has size = " + this.playerDeck.get(0).size());
	PlayerCard result = this.playerDeck.get(0).get(0);
	this.playerDeck.get(0).remove(0);
	if (this.playerDeck.get(0).isEmpty())
	{
	    this.playerDeck.remove(0);
	}
	if (result.isNormalEpidemic() || result.isVirulentEpidemic())
	{
	    resolveEpidemic(result);
	}
	System.out.println("Top player deck has size = " + this.playerDeck.get(0).size());

	return result;
    }

    private void resolveEpidemic(PlayerCard epidemic)
    {

	if (epidemic.isNormalEpidemic())
	{
	    System.out.println("Resolving a normal epidemic...");
	    //1. Increase.
	    this.infectionTrackLocation++;
	    //2. Infect.
	    InfectionCard bottom = this.infectionDeck.get(this.infectionDeck.size() - 1)//select bottom deck
		    .get(this.infectionDeck.get(this.infectionDeck.size() - 1).size() - 1);//select bottom card off bottom deck
	    this.infectionDeck.get(this.infectionDeck.size() - 1).remove(bottom);
	    this.infectionDiscard.add(bottom);
	    bottom.getCity().setNaturalColorCubes(3);
	    System.out.println(bottom.getCity());
	    //3. Intensify.
	    ArrayList<InfectionCard> toStack = (ArrayList<InfectionCard>) (this.infectionDiscard.clone());
	    if (toStack.isEmpty())
	    {//this can't actually happen? I remember this being necessary though?
	    }
	    else
	    {
		this.infectionDeck.add(0, toStack);
	    }
	    System.out.println("The following is the infection deck after infecting and intensifying...");
	    this.displayInfectionDeck();
	    this.infectionDiscard.clear();
	    System.out.println("The following is the infection deck after the discard has been cleared...");
	    this.displayInfectionDeck();

	    //Now that the deck is back on top, before shuffle, offer removal of card if resilient population is in play!
	    boolean resilientPopulation = false;
	    for (int i = 0; i < this.players.size(); i++)
	    {
		for (int card = 0; card < this.players.get(i).getHand().size(); card++)
		{
		    if (this.players.get(i).getHand().get(card).getName() != null)
		    {
			if (this.players.get(i).getHand().get(card).getName().compareTo("Resilient Population") == 0)
			{
			    resilientPopulation = true;
			    System.out.println("Would " + this.players.get(i).getName() + " like to play Resilient Population? T/F?");
			    Scanner in = new Scanner(System.in);
			    resilientPopulation = in.nextBoolean();
			    if (resilientPopulation)
			    {
				this.resilientPopulation(in, false);//it's already been loaded up into the top section.
			    }
			}
		    }
		}
	    }

	    this.shuffleTopInfectionDeck();
	    System.out.println("The following is the infection deck after the top infection deck has been reshuffled");
	    this.displayInfectionDeck();
	}
	else if (epidemic.isVirulentEpidemic())
	{
	    throw new UnsupportedOperationException("Not supported yet!");
	}
	else
	{
	    System.out.println("You called resolveEpidemic() on a card that is neither an epidemic nor a virulent epidemic. Why?");
	}
    }

    private void displayInfectionDeck()
    {
	System.out.println("The following is the full infection deck:\n");
	for (int i = 0; i < this.infectionDeck.size(); i++)
	{
	    for (int j = 0; j < this.infectionDeck.get(i).size(); j++)
	    {
		System.out.println("Deck " + i + ", card " + j + " = " + this.infectionDeck.get(i).get(j));
	    }
	    System.out.println();
	}
	System.out.println("The following is the infection discard:\n");
	for (int i = 0; i < this.infectionDiscard.size(); i++)
	{
	    System.out.println("index " + i + " = " + this.infectionDiscard.get(i));
	}
    }

    private void defaultCityStates(boolean changedReset)
    {
	Iterator itr = this.board.getIterator();
	while (itr.hasNext())
	{
	    City city = ((City) (((Node) (itr.next())).getValue()));
	    if (changedReset)
	    {
		city.setChanged(false);
	    }
	    city.setOutbroke(false);
	}
    }

    private void displayAllChangedCities()
    {
	boolean first = true;
	Iterator itr = this.board.getIterator();
	while (itr.hasNext())
	{
	    Node node = (Node) (itr.next());
	    City city = (City) (node.getValue());
	    if (city.isChanged())
	    {
		///city.setChanged(false);
		if (city.bordersPlayer())
		{
		    if (first)
		    {
			System.out.println("\nNow displaying all changed cities:\n\n");
			first = false;
		    }
		    System.out.println("BORDERS PLAYER: " + city);
		    city.setChanged(false);
		}
		else if (!this.gameMasterMod)
		{
		    if (first)
		    {
			System.out.println("\nNow displaying all visible changed cities:\n\n");
			first = false;
		    }
		    System.out.println("NOT GAMEMASTER MOD:" + city);
		    city.setChanged(false);
		}
	    }
	}
	System.out.println();
    }

    private void move(Player player, String location)
    {
	System.out.println("location = " + location);
	player.getLocation().removePlayer(player);//leave previous city
	City city = this.board.get(location);
	player.setLocation(city);	//arive in new city
	this.board.get(location).addPlayer(player);
	if (this.gameMasterMod)
	{
	    this.displayAllChangedCities();
	}
    }

    private void troubleshoot()
    {
	int deck = 0;
	int index = 0;
	for (int cardsShown = 0; cardsShown < this.infectionTrack[this.infectionTrackLocation]; cardsShown++)
	{
	    System.out.println("The troubleshooter sees that the next card is...Deck " + deck + ", index " + index + ", ");
	    System.out.println(this.infectionDeck.get(deck).get(index));
	    index++;
	    System.out.println("deck=" + deck + ", index=" + index
		    + ", after index increment, before deck change check. Current deck size=" + this.infectionDeck.get(deck).size());
	    if (this.infectionDeck.get(deck).size() <= index)
	    {
		deck++;
		index = 0;
	    }
	}
    }

    private void displayKnowledgeOnInfections()
    {
	int infectionsSeen = 0;
	int i = 0;
	while (infectionsSeen < this.players.size() * this.infectionTrack[this.infectionTrackLocation]
		&& i < this.infectionDeck.size())
	{
	    System.out.println("This is deck " + (i));
	    infectionsSeen += this.infectionDeck.get(i).size();
	    System.out.println("You will have seen " + infectionsSeen + " cards with "
		    + players.size() + " players and " + infectionTrack[infectionTrackLocation] + " infections per turn");
	    displayShuffledCopyOf(this.infectionDeck.get(i));
	    System.out.println("\n");
	    i++;
	}
    }

    private void displayShuffledCopyOf(ArrayList<InfectionCard> deck)
    {
	ArrayList<InfectionCard> copy = (ArrayList<InfectionCard>) (deck.clone());
	while (!copy.isEmpty())
	{
	    int index = (int) (Math.random() * copy.size());
	    System.out.println((copy.get(index)));
	    copy.remove(index);
	}
    }

    private void resilientPopulation(Scanner in, boolean discard)//if true discard, then it'll be in discard, else in top player deck
    {//Separate method because it's a weird event with a special time to be playecd
	String line = "";
	boolean done = false;
	while (!done)
	{
	    while (this.board.getNode(line) == null)
	    {
		System.out.println("Please enter city:");
		line = in.nextLine();
	    }
	    ArrayList<InfectionCard> toRemove;
	    if (discard)
	    {
		toRemove = this.infectionDiscard;
	    }
	    else
	    {
		toRemove = this.infectionDeck.get(0);
	    }
	    for (int i = 0; i < toRemove.size(); i++)
	    {
		if (toRemove.get(i).getCity().getName().compareTo(line) == 0)
		{
		    toRemove.remove(i);
		    done = true;
		    System.out.println("Successfully removed " + line + " from the toRemove reference to either infection discard or top infection deck.");
		}
	    }
	    System.out.println("That city is not in that deck!");
	    System.out.println("The following is the cities that ARE in that deck!:");
	    for (int i = 0; i < toRemove.size(); i++)
	    {
		System.out.println(toRemove.get(i).getCity().getName());
	    }
	}
    }

    private void eventManager(Scanner in)
    {
	boolean done = false;
	while(!done)
	{
	    done = true;
	    for (int player = 0; player < this.players.size(); player++)
	    {
		for (int card = 0; card < this.players.get(player).getHand().size(); card++)
		{			
		    System.out.println("player index = " + player + ", card inde"
			    + "x = " + card + ", card = " + this.players.get(player).getHand().get(card));
		    if(players.get(player).getHand().get(card).isEvent())
		    {
			//Could play it!
			System.out.println(this.players.get(player).getName() + " has " + this.players.get(player).getHand().get(card).getName());
			System.out.println("Would you like to play this event?");
			done = !in.nextBoolean();
			if(!done)
			{
			    playThisEvent(this.players.get(player).getHand().get(card), in);
			    this.playerDiscard.add(this.players.get(player).getHand().get(card));
			    this.players.get(player).getHand().remove(card);
			}//if youre playing the event
		    }//is it an event?
		}//cycle cards in hand
	    }//cycle players
	}//while you still can/are playing events
    }//eventManager

    private void playThisEvent(PlayerCard card, Scanner in)//None of this has been bug-tested, will probably all be scrapped for final version
    {
	String firstWord = card.getName().toLowerCase();
	while(firstWord.contains(" "))
	{
	    firstWord = firstWord.substring(0, firstWord.indexOf(" "));
	}
	if (firstWord.compareTo("one") == 0)
	    {
		//One quiet night
		this.quietNight = true;
		//done = true;
	    }
	    else if (firstWord.compareTo("resilient") == 0)
	    {
		//Resilient Population
		this.resilientPopulation(in, true);
		//done = true;
	    }
	    else if (firstWord.compareTo("forecast") == 0)
	    {
		//done = true;
		ArrayList<InfectionCard> forecast = new ArrayList<>();
		System.out.println("The forecast finds...\n");
		for (int i = 0; i < 6; i++)
		{
		    forecast.add(this.drawInfectionCard());//draws the top 6 cards
		    System.out.println(forecast.get(i).getCity());
		}
		System.out.println("Please reorder the six as you like. Top cards first!\n");
		ArrayList<InfectionCard> reorder = new ArrayList<>();
		for (int i = 0; i < 6; i++)
		{
		    String line = in.nextLine();
		    int index = -1;
		    for (int j = 0; j < forecast.size(); j++)
		    {
			if (forecast.get(j).getCity().getName().compareTo(line) == 0)
			{
			    index = j;
			}
		    }
		    if (index != -1)
		    {
			reorder.add(forecast.get(index));
			forecast.remove(index);

			System.out.println("Remaining cities:");
			for (int j = 0; j < forecast.size(); j++)
			{
			    System.out.println(forecast.get(j).getCity());
			}
			System.out.println("Reordered cities:");
			for (int j = 0; j < reorder.size(); j++)
			{
			    System.out.println(reorder.get(j).getCity());
			}
		    }
		    else
		    {
			System.out.println("Did not read a valid city!");
			i--;
			System.out.println("Remaining cities:");
			for (int j = 0; j < forecast.size(); j++)
			{
			    System.out.println(forecast.get(j).getCity());
			}
			System.out.println("Reordered cities:");
			for (int j = 0; j < reorder.size(); j++)
			{
			    System.out.println(reorder.get(j).getCity());
			}
		    }
		}//reorder should be loaded
		this.infectionDeck.add(0, reorder);
	    }
	    else if (firstWord.compareTo("commercial") == 0)
	    {
		//done  = true;
	    
		this.commercialTravelBan = this.players.size();
	    }
	    else if (firstWord.compareTo("new") == 0)
	    {
		//done = true;
		Player player;
		System.out.println("Please enter the player who wishes to change role's name!");
		boolean done = false;
		int index = -1;
		while (!done)
		{
		    String name = in.nextLine();
		    for (int i = 0; i < this.players.size(); i++)
		    {
			if (this.players.get(i).getName().compareTo(name) == 0)
			{
			    done = true;
			    index = i;
			}
		    }
		    if (!done)
		    {
			System.out.println("That is not a recognized player name! Player names are...");
			for (int x = 0; x < this.players.size(); x++)
			{
			    System.out.println(this.players.get(x).getName());
			}
		    }
		}//selecting player name
		player = this.players.get(index);
		System.out.println("...and what is your desired player role?");
		done = false;
		index = -1;
		while (!done)
		{
		    String role = in.nextLine();
		    for (int i = 0; i < this.roleDeck.size(); i++)
		    {
			if (this.roleDeck.get(i).getName().toLowerCase().compareTo(role.toLowerCase()) == 0)
			{
			    done = true;
			    index = i;
			}
		    }
		    System.out.println("That is not a valid role. Available roles:");
		    for (int i = 0; i < this.roleDeck.size(); i++)
		    {
			System.out.println(this.roleDeck.get(i).getName());
		    }
		}
		player.setRole(this.roleDeck.get(index));
		this.roleDeck.remove(index);

	    }//new assignment
	    else
	    {
		System.out.println("Event does not require complex resolution. Please resolve on board.");
	    }
    }

    public static class Action//Hi folks, this might be a thing to bring back!
    {
	public static final int TREAT_DISEASE = 0;
	public static final int DRIVE_FERRY = 1;
	public static final int CHARTER_FLIGHT = 2;
	public static final int DIRECT_FLIGHT = 3;
	public static final int SHUTTLE_FLIGHT = 4;
	public static final int BUILD_STATION = 5;
	public static final int CURE_DISEASE = 6;
	public static final int GIVE_INFORMATION = 7;
	public static final int TAKE_INFORMATION = 8;
	public static final int LOBBY_GOVERNMENT = 9;
	public static final int REQUEST_FUNDS = 10;
	public static final int GIVE_FUNDS = 11;
	public static final int TAKE_FUNDS = 12;
	public static final int TREAT_PLAYER = 13;
	public static final int QUARANTINE = 14;
	private int type;
	private String city1;
	private String param3;

	public Action(int type, String city1, String param3)
	{
	    this.city1 = city1;
	    this.param3 = param3;
	    this.type = type;
	}
    }

    public static int colorToCode(String color)
    {
	if (color.toLowerCase().compareTo("blue") == 0)
	{
	    return BLUE;
	}
	if (color.toLowerCase().compareTo("black") == 0)
	{
	    return BLACK;
	}
	if (color.toLowerCase().compareTo("yellow") == 0)
	{
	    return YELLOW;
	}
	if (color.toLowerCase().compareTo("red") == 0)
	{
	    return RED;
	}
	if (color.toLowerCase().compareTo("purple") == 0)
	{
	    return PURPLE;
	}
	return NOT_A_COLOR;
    }

    public static String codeToColor(int code)
    {
	switch (code)
	{
	    case BLUE:
		return "Blue";
	    case RED:
		return "Red";
	    case YELLOW:
		return "Yellow";
	    case PURPLE:
		return "Purple";
	    case BLACK:
		return "Black";
	    default:
		return "Not a color";
	}
    }

    public static void main(String[] args)
    {
	Pandemic game = new Pandemic();
	System.out.println("Selecting game type...");
	game.selectGameType();
	System.out.println("Setting up board...");
	game.setUpBoard();
	System.out.println("Testing set-up!");
	game.testSetup();
	System.out.println("Starting game!");

	game.playClunky();
	//play() for full game, playClunky() for rough text based version
    }
}


/*
 * TODO:    Contingency Planner
 *	    Get it so weird characters get read correctly on move commands (BogotAAAA etc)
 */