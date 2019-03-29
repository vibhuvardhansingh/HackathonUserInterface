package info.andriodhive.hackathonuser;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShopProfileFragment extends Fragment implements View.OnClickListener{

    public ShopProfileFragment(){
//        empty constructor
    }
    private ViewPager imagePager;
    private RecyclerView grid;
    private Layout photo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_profile,container,false);
      /*  photo = view.findViewById(R.id.photo);
        imagePager = view.findViewById(R.id.image_pager);
        grid = view.findViewById(R.id.grid);
        imagePager.setAdapter(new ImagePager());*/
        return view;
    }

    @Override
    public void onClick(View v) {

    }
    public class ImagePager extends PagerAdapter{

       /* private List<View> views = new ArrayList<>();

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            View view;
            ViewHolder holder;

            if (views.size() <= position) {
                view = LayoutInflater.from(container.getContext())
                        .inflate(R.layout.item_business_feature, null);
                views.add(view);
            } else {
                view = views.get(position);
            }

            if (view.getTag() != null) {
                holder = ((ViewHolder) view.getTag());
            } else {
                holder = new ViewHolder(view);
            }
            holder.bindData(position,container);
            view.setTag(holder);
            container.addView(view);
            return view;
        }*/

        class ViewHolder {

         /*   public ViewHolder(View view) {
            }

            View bindData(int position, ViewGroup container) {
                switch (position) {
                    case 0:
                        ImageView imageView = new ImageView(container.getContext());
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        imageView.setImageResource(R.drawable.dummy_shop);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        container.addView(imageView);
                        return imageView;

                    break;
                    case 1:

                        break;
                }
            }*/
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return false;
        }
    }

}
