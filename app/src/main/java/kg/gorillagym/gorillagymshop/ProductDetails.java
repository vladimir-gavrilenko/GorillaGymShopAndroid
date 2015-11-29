package kg.gorillagym.gorillagymshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import kg.gorillagym.gorillagymshop.async.DownloadImageTask;
import kg.gorillagym.gorillagymshop.cart.CartHolder;
import kg.gorillagym.gorillagymshop.navigation.Navigator;
import ru.egalvi.shop.gorillagym.model.Category;
import ru.egalvi.shop.gorillagym.model.Product;

public class ProductDetails extends AppCompatActivity {

    private Category category;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        final TextView name = (TextView) findViewById(R.id.product_name);
        final TextView description = (TextView) findViewById(R.id.product_description);
        final TextView price = (TextView) findViewById(R.id.product_price);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            product = (Product) extras.getSerializable("PRODUCT");
            category = (Category) extras.getSerializable("CATEGORY");
            name.setText(product.getName());
            new DownloadImageTask((ImageView) findViewById(R.id.product_image)).execute(product.getImage()); //TODO pictures should be loaded directly to product
            description.setText(Html.fromHtml(product.getText().replace("[", "<").replace("]", ">")));
            price.setText(product.getPrice() + " " + getString(R.string.currency));
        }

        Button addToCart = (Button) findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartHolder.getCart().add(product, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_cart:
                Navigator.goToCart(this);
                return true;
            case R.id.action_categories:
                Navigator.goToCategories(this);
                return true;
            case R.id.action_products:
                Navigator.goToProducts(this, category);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
