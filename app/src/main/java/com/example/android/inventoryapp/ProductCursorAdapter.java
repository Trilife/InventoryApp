package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ProductContract.ProductEntry;

/**
 * {@link ProductCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of product data as its data source. This adapter knows
 * how to create list items for each row of product data in the {@link Cursor}.
 */
public class ProductCursorAdapter extends CursorAdapter {
    public static final String LOG_TAG = ProductCursorAdapter.class.getSimpleName();

    /**
     * Constructs a new {@link ProductCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the product data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current product can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        final TextView stockTextView = (TextView) view.findViewById(R.id.stock);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        ImageView pictureImageView = (ImageView) view.findViewById(R.id.product_picture);

        // Find the columns of product attributes that we're interested in
        int rowIdColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int stockColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_STOCK);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int pictureColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PICTURE);

        // Read the product attributes from the Cursor for the current product
        final int rowId = cursor.getInt(rowIdColumnIndex);
        String productName = cursor.getString(nameColumnIndex);
        int productStock = cursor.getInt(stockColumnIndex);
        int productPrice = cursor.getInt(priceColumnIndex);
        String productPictureString = cursor.getString(pictureColumnIndex);
        //if there is no image, provide image blank
        if (TextUtils.isEmpty(productPictureString)) {
            productPictureString = ProductEntry.NO_IMAGE;
        }
        //convert string to URI
        Uri productPictureUri = Uri.parse(productPictureString);
        Log.v(LOG_TAG, "in bindView product Picture path: " + productPictureString);

        // Update the Views with the attributes for the current product
        nameTextView.setText(productName);
        stockTextView.setText(Integer.toString(productStock));
        priceTextView.setText(Integer.toString(productPrice));
        pictureImageView.setImageURI(productPictureUri);

        //hide Sell Button, if stock is zero
        Button sellButton = (Button) view.findViewById(R.id.sell_item);
        if (productStock == 0) {
            sellButton.setVisibility(View.GONE);
        } else {sellButton.setVisibility(View.VISIBLE);}

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentStock = Integer.parseInt(stockTextView.getText().toString());
                //reduce stock by one
                currentStock -= 1;
                //display in view
                stockTextView.setText(Integer.toString(currentStock));
                //save in database
                ContentValues values = new ContentValues();
                values.put(ProductEntry.COLUMN_PRODUCT_STOCK, currentStock);
                Uri mCurrentProductUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, rowId);
                //get the affected rows back to check for errors
                int rowsAffected = context.getContentResolver().update(mCurrentProductUri, values, null, null);
                // Show a toast message depending on whether or not the update was successful.
                if (rowsAffected == 0) {
                    // If no rows were affected, then there was an error with the update.
                    Toast.makeText(context.getApplicationContext(), "error with sell button update",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the update was successful and we can display a toast.
                    Toast.makeText(context.getApplicationContext(), "sale updated",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
