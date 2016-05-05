package fabflix;

import java.util.ArrayList;

public class CartList {

	public ArrayList<CartItem> cart;

	public CartList(CartList otherCart) {
		this.cart = otherCart.cart;
	}

	public CartList() {
		cart = new ArrayList<CartItem>();
		cart.add(new CartItem());
	}

	public CartList(CartItem item) {
		cart = new ArrayList<CartItem>();
		cart.add(item);
	}

	public int addToCart(CartItem entry) {
		if (entry == null)
			return -1;

		cart.add(entry);
		return 1;

	}

	public int addToCart(CartList entry) {
		if (entry == null)
			return -1;
		int out = 0;
		for (CartItem a : entry.cart)
			out += addToCart(a);
		return out;

	}

	public int addToCart(ArrayList<CartItem> entry) {
		if (entry == null)
			return -1;
		int out = 0;
		for (CartItem a : entry)
			out += addToCart(a);
		return out;

	}
	public int removeByID(int id) {
		for (int i=0;i<cart.size();++i)
		{
			if(cart.get(i).movieId==id)
			{
				cart.remove(i);
				return i;
			}
			
		}
		return -1;

	}
	public int findByID(int id)
	{
		int index=0;
		for (CartItem a:cart)
		{
			if(a.movieId==id)
				return index;
			++index;
		}
		return -1;
	}
	

}

