
public class Blackjack 
{
	private static Card[] cards = new Card[52];
	private static Deck[] decks;

	@SuppressWarnings("unused")
	public static void main(String[] args) 
	{
		int rounds = Integer.parseInt(args[0]);
		int numOfDecks = Integer.parseInt(args[1]);
		int trace = Integer.parseInt(args[2]);
		Blackjack b = new Blackjack();
		b.fill();
		decks = new Deck[numOfDecks];
		
		for(int i = 0; i < numOfDecks; i++)
		{
			decks[i] = new Deck(cards);
		}
		
		RandIndexQueue<Card> shoe = new RandIndexQueue<Card>(numOfDecks*52);
		RandIndexQueue<Card> discard = new RandIndexQueue<Card>(numOfDecks*52);
		int y = 0;
		int z = 0;
		for(Deck d: decks)
		{
			for(Card c: cards)
			{
				shoe.enqueue(c);
				z++;
			}
			y++;
			z = 0;
		}
		shoe.shuffle();
		
		BlackjackCards player = new BlackjackCards(11);
		BlackjackCards dealer = new BlackjackCards(11);
		int pwins = 0;
		int dwins = 0;
		int pushes = 0;
		int fourthSize = (numOfDecks*52)/4;
		String stands = "STANDS: ";
		String busts = "BUSTS: ";
		String hits = "hits: ";
		
		System.out.println("Starting Blackjack with " + rounds + " rounds and " + numOfDecks + " decks in the shoe\n");
		
		for(int i = 0; i < rounds; i++)
		{

			for(int j = 0; j < 2; j++)
			{
				player.enqueue(shoe.getFront());
				discard.enqueue(shoe.getFront());
				shoe.dequeue();
				dealer.enqueue(shoe.getFront());
				discard.enqueue(shoe.getFront());
				shoe.dequeue();
			}
			
			if(trace > 0)
			{
				System.out.println("Round " + i + " beginning");
				System.out.println("Player: " + player.toString() + " : " + player.getValue());
				System.out.println("Dealer " + dealer.toString() + " : " + dealer.getValue());
			}
			
			boolean pbool = false;
			boolean dbool = false;
			
			if(player.getValue() == 21)
			{
				if(trace > 0)
				{
					System.out.println("Result: Player wins!");
				}
				pwins++;
			}
			else if(dealer.getValue() == 21)
			{
				if(trace > 0)
				{
					System.out.println("Result: Dealer wins!");
				}
				dwins++;
			}
			else
			{
				while(pbool == false && dbool == false)
				{	
					while(player.getValue() < 17)
					{
						player.enqueue(shoe.getFront());
						discard.enqueue(shoe.getFront());
						
						if(trace > 0)
						{
							System.out.println("Player: " + hits + shoe.getFront());
						}
						shoe.dequeue();
					}
					if(player.getValue() >= 17 && player.getValue() <= 21)
					{
						if(trace > 0)
						{
							System.out.println("Player: " + stands + player.toString() + " : " + player.getValue());		
						}
						pbool = true;
					}
					else if(player.getValue() > 21)
					{
						if(trace > 0)
						{
							System.out.println("Player: " + busts + player.toString() + " : " + player.getValue());
						}
						dwins++;
						if(trace > 0)
						{
							System.out.println("Result: Dealer wins!");
						}
						break;
					}
					while(dealer.getValue() < 17)
					{
						dealer.enqueue(shoe.getFront());
						discard.enqueue(shoe.getFront());

						if(trace > 0)
						{
							System.out.println("Dealer: " + hits + shoe.getFront());
						}
						shoe.dequeue();
					}
					if(dealer.getValue() >= 17 && dealer.getValue() <= 21)
					{
						if(trace > 0)
						{
							System.out.println("Dealer: " + stands + dealer.toString() + " : " + dealer.getValue());		
						}
						dbool = true;
					}
					else if(dealer.getValue() > 21)
					{
						if(trace > 0)
						{
							System.out.println("Dealer: " + busts + dealer.toString() + " : " + dealer.getValue());		
						}
						pwins++;
						if(trace > 0)
						{
							System.out.println("Result: Player wins!");
						}
						break;
					}
				}
				
				if(player.getValue() > dealer.getValue() && player.getValue() <= 21)
				{
					if(trace > 0)
					{
						System.out.println("Result: Player wins!");
					}
					pwins++;
				}
				else if(player.getValue() < dealer.getValue() && dealer.getValue() <= 21)
				{
					if(trace > 0)
					{
						System.out.println("Result: Dealer wins!");
					}
					dwins++;
				}
				else if(player.getValue() == dealer.getValue())
				{
					if(trace > 0)
					{
						System.out.println("Result: Push!");
					}
					pushes++;
				}
			}
			
			if(shoe.size() <= fourthSize)
			{
				shoe = new RandIndexQueue<Card>(b.merge(shoe, discard));
				fourthSize = shoe.size()/4;
				discard.clear();
				System.out.println("Reshuffling the shoe in round " + i + "\n");
				shoe.shuffle();
			}
			player.rand.clear();
			dealer.rand.clear();
			
			if(trace > 0)
			{
				trace--;
				System.out.println("\n");
			}
		}	
		
		System.out.println("After " + rounds + " rounds, here are the results:");
		System.out.println("    Player Wins: " + pwins);
		System.out.println("    Dealer Wins: " + dwins);
		System.out.println("    Pushes: " + pushes);
	}
	
	
	public void fill()
	{
		int i = 0;
		
		for (Card.Suits s : Card.Suits.values())
		{
		    for (Card.Ranks r : Card.Ranks.values())
		    {
		      cards[i]= new Card(s,r);
		      i++;
		    }
		 }
	}
	
	public RandIndexQueue<Card> merge(RandIndexQueue<Card> r1, RandIndexQueue<Card> r2)
	{
		RandIndexQueue<Card> merge = new RandIndexQueue<Card>(r1.size() + r2.size());
		
		while(r1.size() != 0)
		{
			merge.enqueue(r1.getFront());
			r1.dequeue();
		}
		while(r2.size() != 0)
        {
        	merge.enqueue(r2.getFront());
			r2.dequeue();
        }
        return merge;
	}
	
	public static class Deck
	{
		public Card[] cards;
		
		public Deck(Card[] c)
		{
			cards = c;
		}
	}
}


