package hackathon.poly.hackathon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import hackathon.poly.hackathon.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    private String textMug = Html.fromHtml("Vendu par : Amazon.com <br> Dishwasher safe, microwave safe. Feeling crafty, Can draw on the mug with an oil based Sharpie paint-pen and Preheat your oven to 350 degrees. Once your oven is heated, set your mugs inside and let them “bake” for 30 minutes. After 30 minutes, turn off the oven,  let the mugs and oven cool down and now you have for Graduation, Birthday, Holidays, Anniversary and much more").toString();

    private String textCup = Html.fromHtml("Vendu par : Amazon.com <br>" +
            "8-ounce paper hot cup; includes 20 sleeves of 50 cups (1,000 cups total) <br>" +
            "Polyethylene lining for resistance to leaking and moisture penetration <br>" +
            "Hot insulated—comfortable to hold and keeps hot beverages hot for longer<br>" +
            "Ideal for to-go orders, cafes, food trucks, and other types of hot-beverage service").toString();

    private String textWineGlass = Html.fromHtml("Vendu par : Amazon.ca <br>" +
            "ELEGANCE: Enrich your drinking experience with this tastefully designed 13.25-ounce wine glass set. Essential piece for your sophisticated barware. Ideal for any table setting from casual dinners to fun parties <br>" +
            "HIGH QUALITY: Thick and durable quality material will add convenience to your life. This special set of 6 stemware is perfect for a Cabernet, Chardonnay or any glass of your favorite wine <br>" +
            "PERFECT FOR GIFTING: Ideal for birthday gifts, corporate gifts, father’s day gifts, mother’s day gifts you name it! Tastefully packaged to meet your needs <br>" +
            "GET CREATIVE: Try serving unique cocktails, spritzers or even delicious desserts. These barware glasses will fit your collection perfectly. Use your imagination to its limits <br>" +
            "DISHWASHER SAFE: Tired of dealing with hand-wash items? This set of wine glasses can be easily washed in the dishwasher")
            .toString();

    private String textTeapot = Html.fromHtml("Vendu par : Martin Newell<br>" +
            "Imported <br>" +
            "Whistles attached to kettle to signal that water is boiling <br>" +
            "Kettle heats up quickly and retains heat well<br>" +
            "Classic stainless steel design to fit any kitchen<br>" +
            "Hand wash with mild soap<br>" +
            "Limited lifetime warranty").toString();

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
                if (DummyContent.ITEMS.size() > 0 && somme > 0) {
                    Snackbar.make(view, "Congratulation, you have by for " + somme + "$CA", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    ItemListActivity.this.finish();
                                }
                            },
                            2000
                    );
                }
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        RecyclerView recyclerView = findViewById(R.id.item_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        if (MainActivity.nImage1 > 0)
            DummyContent.ITEMS.add(new DummyContent.DummyItem("0", MainActivity.nImage1, MainActivity.priceImage1, "mug                ", textMug));
        if (MainActivity.nImage2 > 0)
            DummyContent.ITEMS.add(new DummyContent.DummyItem(String.valueOf(DummyContent.ITEMS.size()), MainActivity.nImage2, MainActivity.priceImage2, "cup of coffee", textCup));
        if (MainActivity.nImage3 > 0)
            DummyContent.ITEMS.add(new DummyContent.DummyItem(String.valueOf(DummyContent.ITEMS.size()), MainActivity.nImage3, MainActivity.priceImage3, "teapot            ", textTeapot));
        if (MainActivity.nImage4 > 0)
            DummyContent.ITEMS.add(new DummyContent.DummyItem(String.valueOf(DummyContent.ITEMS.size()), MainActivity.nImage4, MainActivity.priceImage4, "wine glass    ", textWineGlass));
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final boolean mTwoPane;
        private TextView result;
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
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public int getItemViewType(int position) {
            if (isPositionHeader(position))
                return 0;
            if (isPositionFooter(position))
                return 2;
            return 1;
        }

        private boolean isPositionHeader(int position) {
            return position == 0;
        }

        private boolean isPositionFooter(int position) {
            return position == DummyContent.ITEMS.size() + 1;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            if (viewType == 2) {
                System.out.println("footer créé");
                return new FooterViewHolder(view);
            }
            if (viewType == 0)
                return new HeaderViewHolder(view);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder instanceof HeaderViewHolder) {
                ((HeaderViewHolder)viewHolder).mIdView.setText("Number");
                ((HeaderViewHolder)viewHolder).mContentView.setText("Article");
                ((HeaderViewHolder)viewHolder).mPriceView.setText("Price");
            } else if (viewHolder instanceof FooterViewHolder) {
                ((FooterViewHolder)viewHolder).mIdView.setText("Total");
                Integer sum = 0;
                for (DummyContent.DummyItem item : DummyContent.ITEMS){
                    sum += item.number * item.price;
                }
                result = ((FooterViewHolder)viewHolder).mPriceView;
                result.setText("   "  + sum.toString() + " $CA TTC");
            } else if (viewHolder instanceof ViewHolder) {
                ((ViewHolder)viewHolder).mIdView.setText(DummyContent.ITEMS.get(i-1).number.toString());
                ((ViewHolder)viewHolder).mContentView.setText(DummyContent.ITEMS.get(i-1).content);
                ((ViewHolder)viewHolder).mPriceView.setText("          " + DummyContent.ITEMS.get(i-1).price.toString() + "$CA");
                viewHolder.itemView.setTag(DummyContent.ITEMS.get(i-1));
                viewHolder.itemView.setOnClickListener(mOnClickListener);
            }
        }

        @Override
        public int getItemCount() {
            return DummyContent.ITEMS.size() + 2;
        }

        private class FooterViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            final TextView mPriceView;
            final ImageButton btn;

            public FooterViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
                mPriceView = (TextView) view.findViewById((R.id.id_price));
                btn = (ImageButton) view.findViewById(R.id.btnRemove);
                btn.setVisibility(View.GONE);
            }
        }

        private class HeaderViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            final TextView mPriceView;
            final ImageButton btn;

            public HeaderViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
                mPriceView = (TextView) view.findViewById((R.id.id_price));
                btn = (ImageButton) view.findViewById(R.id.btnRemove);
                btn.setVisibility(View.GONE);

            }
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            final TextView mPriceView;
            final ImageButton btn;

            ViewHolder(View Sview) {
                super(Sview);
                mIdView = (TextView) Sview.findViewById(R.id.id_text);
                mContentView = (TextView) Sview.findViewById(R.id.content);
                mPriceView = (TextView) Sview.findViewById((R.id.id_price));
                btn = (ImageButton) Sview.findViewById(R.id.btnRemove);
                View.OnClickListener mOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DummyContent.DummyItem aSupprimer = null;
                        for (DummyContent.DummyItem item : DummyContent.ITEMS) {
                            if (item.content.equals(mContentView.getText().toString())) {
                                if (item.number <= 0) {
                                    System.out.println("supprime : " + item.content);
                                    aSupprimer = item;
                                } else {
                                    System.out.println("item value : " + --item.number);
                                    mIdView.setText(item.number.toString());

                                }
                            }
                        }
                       /* if (aSupprimer != null) {
                            System.out.println("vrai suppression ! " + aSupprimer.content);
                            DummyContent.ITEMS.remove(aSupprimer);
                            Sview.invalidate();

                            for (DummyContent.DummyItem item : DummyContent.ITEMS)
                                System.out.println(item.content);
                        }*/
                        if (result != null)
                        {
                            Integer sum = 0;
                            for (DummyContent.DummyItem item :  DummyContent.ITEMS){
                                sum += item.number * item.price;
                            }result.setText(sum + " $CA");

                        }
                    }
                };
                    btn.setOnClickListener(mOnClickListener);
            }
        }
    }
}