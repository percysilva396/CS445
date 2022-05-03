public class BlackjackCards
{
	protected RandIndexQueue<Card> rand;
	protected int val = 0;
	
	public BlackjackCards(int i) 
	{
		rand = new RandIndexQueue<Card>(i);
	}

	public int getValue()
	{	
		int num = 0;
		boolean bool = false;
		
		for(int i = 0; i < rand.size(); i++)
		{
			if(num == 0)
			{
				num = rand.get(i).value();
				
				if(rand.get(i).value() == 11)
				{
					bool = true;
				}
			}
			else if(num + rand.get(i).value() <= 21)
			{
				num += rand.get(i).value();
			}
			else if(num + rand.get(i).value2() <= 21)
			{
				num += rand.get(i).value2();
			}
			else if(num + rand.get(i).value() > 21)
			{
				if(bool == true)
				{
					bool = false;
					num -= 10;
				}
				num += rand.get(i).value();
			}
		}
		
		val = num;
		return val;
	}

	public void enqueue(Card card) 
	{
		rand.enqueue(card);
	}
	
	public String toString()
	{
		return rand.toString();
	}

}
