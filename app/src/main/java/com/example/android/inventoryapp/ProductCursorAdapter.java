package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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


import java.io.File;

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
    public void bindView(View view, final Context context, Cursor cursor) {


        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        final TextView stockTextView = (TextView) view.findViewById(R.id.stock);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        ImageView pictureImageView = (ImageView) view.findViewById(R.id.edit_product_picture);

        // Find the columns of product attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int stockColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_STOCK);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int pictureColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PICTURE);

        // Read the product attributes from the Cursor for the current product
        String productName = cursor.getString(nameColumnIndex);
        //TODO adapt the stock, price and image variable types. Likely to be int, int, and??
        //TODO these were all Strings... with getString
        int productStock = cursor.getInt(stockColumnIndex);
        int productPrice = cursor.getInt(priceColumnIndex);
        String productPicture = cursor.getString(pictureColumnIndex);
        Log.v(LOG_TAG, "in bindView product Picture path: " + productPicture);
        Log.v(LOG_TAG, "in bindView picture URI: " + Uri.parse(new File(productPicture).toString()));

        //TODO this is where we would test for eventual discrepancies
        // If the product breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        //if (TextUtils.isEmpty(productStock)) {
        //    productStock = context.getString(R.string.unknown_stock);
        //}

        // Update the TextViews with the attributes for the current product
        nameTextView.setText(productName);
        stockTextView.setText(Integer.toString(productStock));
        priceTextView.setText(Integer.toString(productPrice));
        File pictureFile = new File(productPicture);
        if (pictureFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath());
            pictureImageView.setImageBitmap(myBitmap);
        } else {
            Log.v(LOG_TAG, "no picture file exists");
        }
        //pictureImageView.setImageURI(Uri.parse(new File(productPicture).toString()));
        //pictureImageView.setImageResource(productPicture);

        //hide Sell Button, if stock is zero
        Button sellButton = (Button) view.findViewById(R.id.sell_item);
        if (productStock == 0) {
            sellButton.setVisibility(View.GONE);
        }

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //long rowId = Long.valueOf(stockTextView.getTag().toString());
                //String filter = "_ID=" + rowId;
                int currentStock = Integer.parseInt(stockTextView.getText().toString());
                currentStock = currentStock -1;
                stockTextView.setText(Integer.toString(currentStock));
                Log.v(LOG_TAG, "made it to reduce stock" + currentStock);
                Uri mCurrentProductUri;
                ContentValues values = new ContentValues();
                values.put(ProductEntry.COLUMN_PRODUCT_STOCK, currentStock);
//                context.getContentResolver().update(other data here);
//                        context.getString(other_data_here);
//
//                Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI,values);
////from Editor
//                int rowsAffected = getContentResolver().update(mCurrentProductUri, values, null, null);
//
//                // Show a toast message depending on whether or not the update was successful.
//                if (rowsAffected == 0) {
//                    // If no rows were affected, then there was an error with the update.
//                    Toast.makeText(this, getString(R.string.editor_update_product_failed),
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    // Otherwise, the update was successful and we can display a toast.
//                    Toast.makeText(this, getString(R.string.editor_update_product_successful),
//                            Toast.LENGTH_SHORT).show();
//                }
//
////until here
            }
        });
    }
}

// saleButton.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        long rowId = Long.valueOf(itemQuantity.getTag().toString());
//        String filter = "_ID=" + rowId;
//        int saleCurrentQuantity = Integer.valueOf(itemQuantity.getText().toString());
//        if (saleCurrentQuantity > 0) {
//        mDbHelper = new InventoryDbHelper(context);
//        SQLiteDatabase db = mDbHelper.getWritableDatabase();
//        int saleNewQuantity = saleCurrentQuantity - 1;
//        ContentValues updateValues = new ContentValues();
//        updateValues.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY, saleNewQuantity);
//        db.update(InventoryContract.InventoryEntry.TABLE_NAME, updateValues, filter, null);
//        itemQuantity.setText(String.valueOf(saleNewQuantity));
//        db.close();
//        }
//        }
//        });