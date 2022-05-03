import java.util.Random;

public class RandIndexQueue<T> implements MyQ<T>, Indexable<T>, Shufflable
{
	protected T[] arr;
	protected RandIndexQueue<Integer> R;
	protected int moves;
	protected int size;
	
	@SuppressWarnings("unchecked")
	public RandIndexQueue(int i) 
	{
		arr = (T[]) new Object[i];
	}

	@SuppressWarnings("unchecked")
	public RandIndexQueue(RandIndexQueue<T> r) 
	{
		T temp[] = (T[]) new Object[r.arr.length];
		
		for(int i = 0; i <= r.arr.length-1; i++)
		{
			temp[i] = r.arr[i];
			size++;
		}
		arr = temp;
	}

	public void enqueue(T newEntry) 
	{
		if(arr.length == size)
		{
			@SuppressWarnings("unchecked")
			T temp[] = (T[]) new Object[arr.length * 2];
			
			for(int i = 0; i < arr.length; i++)
			{
				temp[i] = arr[i];
			}
			arr = temp;
		}
		arr[size] = newEntry;
		moves++;
		size++;
	}

	public T dequeue()
	{
		if(size > 0)
		{
			T deq = arr[0];
			@SuppressWarnings("unchecked")
			T temp[] = (T[]) new Object[arr.length];
			
			for(int i = 0; i <= arr.length-1; i++)
			{
				if(i+1 <= arr.length-1)
				{
					temp[i] = arr[i+1];
				}
			}
			arr = temp;
			size--;
			moves++;
			return deq;
		}
		return null;
	}

	public T getFront() 
	{
		return arr[0];
	}

	public boolean isEmpty() 
	{
		if(size == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public void clear() 
	{
		
		arr = (T[]) new Object[arr.length];
		size = 0;
	}

	public T get(int i) 
	{
		return arr[i];
	}
	
	public void set(int i, T item) 
	{
		arr[i] = item;
	}

	public int size()
	{
		return size;
	}

	public int capacity() 
	{
		return arr.length;
	}

	public void setMoves(int i) 
	{
		moves = i;
	}
	
	public int getMoves() 
	{
		return moves;
	}
	
	public void shuffle() 
	{
		Random rand = new Random();
		
		for (int i = 0; i < size; i++) 
		{
			int randomIndextoSwap = rand.nextInt(size);
			//System.out.println(randomIndextoSwap);
			T tmp = arr[randomIndextoSwap];
			
			arr[randomIndextoSwap] = arr[i];
			arr[i] = tmp;
		}
	}
	
	public boolean equals(RandIndexQueue<T> r) 
	{
		int i = 0;
		int j = 0;
		int equal = 0;
		
		while(i <= arr.length-1 && j <= r.arr.length-1)
		{
			if(arr[i] == null && r.get(j) == null)
			{
				break;
			}
			else if(arr[i] == r.get(j))
			{
				equal++;
			}
			
			i++;
			j++;
		}
		
		if(equal == size || equal == arr.length-1)
		{
			return true;
		}
		else
		{
			return false;
		}
    }
	
	public String toString()
	{
		String str = "Contents: ";
		
		for(int i = 0; i < arr.length; i++)
		{
			if(arr[i] != null)
			{
				str += arr[i] + " ";	
			}
		}
		return str;
	}
}
