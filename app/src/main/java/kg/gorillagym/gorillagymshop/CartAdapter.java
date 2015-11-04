package kg.gorillagym.gorillagymshop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import kg.gorillagym.gorillagymshop.cart.CartHolder;
import kg.gorillagym.gorillagymshop.navigation.Navigator;
import kg.gorillagym.gorillagymshop.numberpicker.MyNumberField;
import ru.egalvi.shop.CartItem;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.Product;

//TODO change it
public class CartAdapter extends ArrayAdapter<CartItem> {
    private Activity activity;

    private Map<CartItem, Integer> productsInCart;

    public CartAdapter(Activity context, int resource, Map<CartItem, Integer> objects) {
        super(context, resource, new ArrayList<>(objects.keySet()));
        activity = context;
        productsInCart = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cart_list_item, parent, false);
        }
        Button text = (Button) convertView.findViewById(R.id.product_title);
        ImageView image = (ImageView) convertView.findViewById(R.id.product_image);
        final Product product = (Product) getItem(position); //TODO possibly unsafe cast
        text.setText(product.getName());
        image.setImageDrawable(activity.getResources().getDrawable(R.drawable.tst));
//        MyNumberField quantity = (MyNumberField) convertView.findViewById(R.id.cart_quantity);
        final Integer itemQuantity = productsInCart.get(product);
//        quantity.setValue(itemQuantity);
        TextView pricePerItem = (TextView) convertView.findViewById(R.id.cart_price_per_item);
        pricePerItem.setText(String.valueOf(product.getPrice()));
        final TextView price = (TextView) convertView.findViewById(R.id.cart_item_sum);
        price.setText(String.valueOf(product.getPrice() * itemQuantity) + " " + activity.getString(R.string.currency));

        final EditText value = getValueEdit(convertView);
        Button plusBtn = getPlusButton(convertView);
        Button minusBtn = getMinusButton(convertView);

        value.getText().clear();
        value.getText().append(itemQuantity.toString());
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer quantity = Integer.valueOf(value.getText().toString());
                value.getText().clear();
                value.getText().append(String.valueOf(quantity + 1));
                CartHolder.getCart().add(product, 1);
                ((CartActivity) activity).updateCartView();
                price.setText(String.valueOf(product.getPrice() * itemQuantity) + " " + activity.getString(R.string.currency));
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer quantity = Integer.valueOf(value.getText().toString());
                value.getText().clear();
                value.getText().append(String.valueOf(quantity - 1));
                CartHolder.getCart().remove(product, 1);
                ((CartActivity) activity).updateCartView();
                price.setText(String.valueOf(product.getPrice() * itemQuantity) + " " + activity.getString(R.string.currency));
            }
        });

        return convertView;
    }

    private Button getMinusButton(View view) {
        return (Button) view.findViewById(R.id.minus_button);
    }

    private Button getPlusButton(View view) {
        return (Button) view.findViewById(R.id.plus_button);
    }

    private EditText getValueEdit(View view) {
        return (EditText) view.findViewById(R.id.int_value);
    }
}
