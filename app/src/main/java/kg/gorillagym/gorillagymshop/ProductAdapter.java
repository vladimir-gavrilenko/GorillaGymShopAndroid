package kg.gorillagym.gorillagymshop;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kg.gorillagym.gorillagymshop.async.DownloadImageTask;
import kg.gorillagym.gorillagymshop.navigation.Navigator;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.Product;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Activity activity;

    private Category category;

    public ProductAdapter(Activity context, int resource, List<Product> objects, Category category) {
        super(context, resource, objects);
        activity = context;
        this.category = category;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.product_list_item, parent, false);
        }
        Button text = (Button) convertView.findViewById(R.id.product_title);
        final Product product = getItem(position);
        text.setText(product.getName());
        ImageView image = (ImageView) convertView.findViewById(R.id.product_image);
        image.setImageBitmap(BitmapFactory.decodeByteArray(product.getImageData(), 0, product.getImageData().length));
        View.OnClickListener goToDetailsClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.goToProductDetails(activity, category, product);
            }
        };
        text.setOnClickListener(goToDetailsClickListener);
        image.setOnClickListener(goToDetailsClickListener);
        TextView price = (TextView) convertView.findViewById(R.id.product_price);
        price.setText(String.valueOf(product.getPrice()) + " " + activity.getString(R.string.currency));
        return convertView;
    }
}
