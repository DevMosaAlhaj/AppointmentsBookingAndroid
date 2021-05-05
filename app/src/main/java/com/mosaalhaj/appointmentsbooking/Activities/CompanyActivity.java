package com.mosaalhaj.appointmentsbooking.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.mosaalhaj.appointmentsbooking.Adapters.CompanyPagerAdapter;
import com.mosaalhaj.appointmentsbooking.Models.Company;
import com.mosaalhaj.appointmentsbooking.R;

import java.util.ArrayList;

import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY_LIST;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.POSITION;
import static com.mosaalhaj.appointmentsbooking.Items.Constants.USER_ID;

public class CompanyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);


        ArrayList<Company> companies = getIntent().getParcelableArrayListExtra(COMPANY_LIST);
        int position = getIntent().getIntExtra(POSITION,0);
        String userId = getIntent().getStringExtra(USER_ID);

        ViewPager viewPager = findViewById(R.id.company_view_pager);
        CompanyPagerAdapter pagerAdapter = new CompanyPagerAdapter(getSupportFragmentManager(),companies);
        viewPager.setAdapter(pagerAdapter);
        ZoomOutPageTransformer transformer = new ZoomOutPageTransformer();
        viewPager.setPageTransformer(false,transformer);
        viewPager.setCurrentItem(position,true);



    }




    public static class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }




}