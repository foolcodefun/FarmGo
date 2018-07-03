package com.little.farmgo.Fragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.little.farmgo.Data.Product;
import com.little.farmgo.Data.ShoppingCart.ShoppingListRepository;
import com.little.farmgo.R;

import java.util.HashMap;

import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static com.little.farmgo.Data.ShoppingCart.DatabaseContract.ShoppingCartTable.COLUMN_IMAGE_URL;

/**
 * Created by sarah on 05/06/2018.
 */

public class ShoppingCartFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private Button mButton;
    private ShoppingCartAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_chart_list, container, false);
        mRecyclerView = view.findViewById(R.id.shopping_cart_list);
        mButton = view.findViewById(R.id.send_order);
        mButton.setOnClickListener(this);

        mAdapter = new ShoppingCartAdapter(new ShoppingListRepository(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        enableSwipeDelete();
        return view;
    }

    private void enableSwipeDelete() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.remove(viewHolder.getAdapterPosition());
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder != null) {
                    View view = ((ShoppingCartAdapter.ViewHolder) viewHolder).mForeground;
                    getDefaultUIUtil().onSelected(view);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                getDefaultUIUtil().clearView(((ShoppingCartAdapter.ViewHolder) viewHolder).mForeground);
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                getDefaultUIUtil().onDrawOver(c, recyclerView, ((ShoppingCartAdapter.ViewHolder) viewHolder).mForeground
                        , dX, dY, actionState, isCurrentlyActive);
            }


            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                getDefaultUIUtil().onDraw(c, recyclerView, ((ShoppingCartAdapter.ViewHolder) viewHolder).mForeground, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onClick(View v) {

    }

    public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

        ShoppingListRepository mRepository;
        HashMap<Product, Integer> shoppingList;


        public ShoppingCartAdapter(ShoppingListRepository repository) {
            mRepository = repository;
            this.shoppingList = repository.getOreders();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.shopping_cart_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Product order = (Product) shoppingList.keySet().toArray()[position];
            int num = shoppingList.get(order);
            holder.bind(order, num);
        }

        @Override
        public int getItemCount() {
            return shoppingList.size();
        }

        public void remove(int position) {
            Product order = (Product) shoppingList.keySet().toArray()[position];
            mRepository.delete(order);
            notifyDataSetChanged();

        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private final ImageView mImageView;
            private final TextView mTitle;
            private final TextView mPrice;
            private final TextView mBuyNum;
            private final TextView mSubtitle;
            private final Button mPlus;
            private final Button mMinus;
            private Product mProduct;
            public final View mForeground;
            public final View mBackground;


            public ViewHolder(View itemView) {
                super(itemView);
                mImageView = itemView.findViewById(R.id.image);
                mTitle = itemView.findViewById(R.id.title);
                mSubtitle = itemView.findViewById(R.id.subtitle);
                mPrice = itemView.findViewById(R.id.price);
                mBuyNum = itemView.findViewById(R.id.buying_num);
                mPlus = itemView.findViewById(R.id.plus);
                mMinus = itemView.findViewById(R.id.minus);
                mForeground = itemView.findViewById(R.id.foreground);
                mBackground = itemView.findViewById(R.id.background);

                mPlus.setOnClickListener(this);
                mMinus.setOnClickListener(this);
            }

            public void bind(Product order, int num) {
                mProduct = order;
                mTitle.setText(mProduct.getTitle());
                mSubtitle.setText(mProduct.getSubtitle());
                mPrice.setText(mProduct.getPrice() + getString(R.string.dollar));
                mBuyNum.setText(num + "");
                Glide.with(itemView)
                        .load(mProduct.getImage_url())
                        .into(mImageView);
            }

            @Override
            public void onClick(View v) {

                refreshProduct(v);
            }

            private synchronized void updateBuyNum(View v) {
                int num = mProduct.getNumber();
                int buyNum = Integer.parseInt(mBuyNum.getText().toString());
                switch (v.getId()) {
                    case R.id.plus:
                        if (buyNum >= num) {
                            buyNum = num;
                            showToast("已達庫存上限");
                        } else
                            buyNum++;
                        break;
                    case R.id.minus:
                        if (buyNum <= 1) {
                            buyNum = 1;
                            showToast("購物數量下限為 1");
                        } else
                            buyNum--;
                        break;
                    default:
                        break;
                }
                mBuyNum.setText(buyNum + "");
                mRepository.update(mProduct, buyNum);
            }

            private void showToast(String text) {
                Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
            }

            private void refreshProduct(final View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("products").orderByChild(COLUMN_IMAGE_URL).equalTo(mProduct.getImage_url());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                mProduct = ds.getValue(Product.class);
                                updateBuyNum(v);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

}
