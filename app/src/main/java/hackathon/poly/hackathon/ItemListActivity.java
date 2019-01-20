package hackathon.poly.hackathon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import hackathon.poly.hackathon.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int somme = 0;
                for (DummyContent.DummyItem item : DummyContent.ITEMS) {
                    somme += item.number * item.price;
                }
                if (DummyContent.ITEMS.size() > 0)
                    Snackbar.make(view, "Congratulation, you have by for " + somme + "$CA", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        if (MainActivity.nImage1 > 0) DummyContent.ITEMS.add(new DummyContent.DummyItem("0", MainActivity.nImage1, MainActivity.priceImage1,"content 1", "details 1"));
        if (MainActivity.nImage2 > 0) DummyContent.ITEMS.add(new DummyContent.DummyItem(String.valueOf(DummyContent.ITEMS.size()), MainActivity.nImage2, MainActivity.priceImage2,"content 2", "details 2"));
        if (MainActivity.nImage3 > 0) DummyContent.ITEMS.add(new DummyContent.DummyItem(String.valueOf(DummyContent.ITEMS.size()), MainActivity.nImage3, MainActivity.priceImage3, "content 3", "details 3"));
        if (MainActivity.nImage4 > 0) DummyContent.ITEMS.add(new DummyContent.DummyItem(String.valueOf(DummyContent.ITEMS.size()), MainActivity.nImage4, MainActivity.priceImage4, "content 4", "details 4"));
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<DummyContent.DummyItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.content);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<DummyContent.DummyItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).number.toString());
            holder.mContentView.setText(mValues.get(position).content);
            holder.mPriceView.setText(mValues.get(position).price.toString() + "$CA");
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            final TextView mPriceView;
            final Button btn;
            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
                mPriceView = (TextView) view.findViewById((R.id.id_price));
                btn = (Button) view.findViewById(R.id.btnRemove);
                View.OnClickListener mOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer value = Integer.parseInt(mIdView.getText().toString());
                        value = value - 1;
                       mIdView.setText(value.toString());
                       for (DummyContent.DummyItem item : DummyContent.ITEMS) {
                           if (item.content.equals(mContentView.getText().toString())) {
                               if (--item.number == 0) {

                                  // Invalid
                               }
                           }
                       }
                    }
                };
                btn.setOnClickListener(mOnClickListener);
            }
        }
    }
}
