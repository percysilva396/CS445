
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
		
		System.out.println("Starting Blackjack with " + rounds + " rounds and " + numOfDecks + " decks in the shoe\n");
		for(int i = 0; i < rounds; i++)
		{
			for(int j = 0; j < 2; j++)
			{
				player.enqueue(shoe.dequeue());
				dealer.enqueue(shoe.dequeue());
			}
			if(trace > 0)
			{
				System.out.println("Round " + i + " beginning");
				System.out.println("Player: " + player.toString() + " : " + player.getValue());
				System.out.println("Dealer: " + dealer.toString() + " : " + dealer.getValue());
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
						if(trace > 0)
						{
							System.out.println("Player hits: " + shoe.getFront());
						}
						player.enqueue(shoe.dequeue());
					}
					if(player.getValue() >= 17 && player.getValue() <= 21)
					{
						if(trace > 0)
						{
							System.out.println("Player STANDS " + player.toString() + " : " + player.getValue());		
						}
						pbool = true;
					}
					else if(player.getValue() > 21)
					{
						if(trace > 0)
						{
							System.out.println("Player BUSTS " + player.toString() + " : " + player.getValue());
							System.out.println("Result: Dealer wins!");
						}
						dwins++;
						break;
					}
					
					while(dealer.getValue() < 17)
					{
						if(trace > 0)
						{
							System.out.println("Dealer hits " + shoe.getFront());
						}
						dealer.enqueue(shoe.dequeue());
					}
					if(dealer.getValue() >= 17 && dealer.getValue() <= 21)
					{
						if(trace > 0)
						{
							System.out.println("Dealer STANDS " + dealer.toString() + " : " + dealer.getValue());		
						}
						dbool = true;
					}
					else if(dealer.getValue() > 21)
					{
						if(trace > 0)
						{
							System.out.println("Dealer BUSTS " + dealer.toString() + " : " + dealer.getValue());		
							System.out.println("Result: Player wins!");
						}
						pwins++;
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

			while(player.rand.size > 0 || dealer.rand.size > 0 )
			{
				discard.enqueue(discard(player, dealer));
			}
			
			if(shoe.size() <= (52*numOfDecks)/4)
			{
				shoe = new RandIndexQueue<Card>(b.merge(shoe, discard));
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

	public static Card discard(BlackjackCards player, BlackjackCards dealer) 
	{
		if(player.rand.size() > 0)
		{
			Card c = player.rand.dequeue();
			return c;
		}
		else 		
		{
			Card c = dealer.rand.dequeue();
			return c;
		}
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
		while(r1.size() != 0)
		{
			r2.enqueue(r1.dequeue());
		}
        return r2;
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