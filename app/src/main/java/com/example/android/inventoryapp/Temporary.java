//// The bindView method is used to bind all data to a given view
//// such as setting the text on a TextView
//@Override
//public void bindView(View view, final Context context, Cursor cursor) {
//        saleButton = (Button) view.findViewById(R.id.sale_button);
//
//        // Find fields to populate in inflated template
//        TextView itemName = (TextView) view.findViewById(R.id.item_name);
//        TextView itemPrice = (TextView) view.findViewById(R.id.item_price);
//        ImageView itemImage = (ImageView) view.findViewById(R.id.item_image);
//final TextView itemQuantity = (TextView) view.findViewById(R.id.item_quantity);
//
//        String name = cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME));
//        String price = cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_PRICE));
//        String quantity = cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY));
//        itemQuantity.setTag(cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry._ID)));
//        byte[] image = cursor.getBlob(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_IMAGE));
//
//        itemName.setText(name);
//        itemPrice.setText(price);
//        itemQuantity.setText(quantity);
//
//        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
//        itemImage.setImageBitmap(bmp);
//
//        saleButton.setOnClickListener(new View.OnClickListener() {
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
//
//
//
//        }
//
//        }
//
