package com.example.ultimatemusicplaylist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ultimatemusicplaylist.adapters.CategoryAdapter;
import com.example.ultimatemusicplaylist.adapters.CategoryModel;
import com.example.ultimatemusicplaylist.adapters.MusicAdapter;
import com.example.ultimatemusicplaylist.adapters.MusicModel;
import com.example.ultimatemusicplaylist.adapters.SearchDropdownAdapter;
import com.example.ultimatemusicplaylist.adapters.SearchItem;
import com.example.ultimatemusicplaylist.adapters.SliderAdapter;
import com.example.ultimatemusicplaylist.adapters.SliderModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 sliderViewPager;
    private LinearLayout sliderDots;
    private Handler sliderHandler = new Handler(Looper.getMainLooper());
    private Runnable sliderRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 🔹 1. Setup Image Slider
        sliderViewPager = findViewById(R.id.slider_viewpager);
        sliderDots = findViewById(R.id.slider_dots);

        List<SliderModel> sliderList = new ArrayList<>();
        sliderList.add(new SliderModel(R.drawable.image13));
        sliderList.add(new SliderModel(R.drawable.image_background));
        sliderList.add(new SliderModel(R.drawable.image12));
        sliderList.add(new SliderModel(R.drawable.image15));

        sliderViewPager.setAdapter(new SliderAdapter(sliderList));
        sliderViewPager.setOffscreenPageLimit(3);
        sliderViewPager.setClipToPadding(false);
        sliderViewPager.setClipChildren(false);
        sliderViewPager.setPageTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        setupSliderDots(sliderList.size(), 0);

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateSliderDots(position);
            }
        });

        sliderRunnable = () -> {
            int currentItem = sliderViewPager.getCurrentItem();
            int totalItems = sliderViewPager.getAdapter().getItemCount();
            sliderViewPager.setCurrentItem((currentItem + 1) % totalItems, true);
            sliderHandler.postDelayed(sliderRunnable, 2000);
        };

        // 🔹 2. Setup Category RecyclerView
        RecyclerView categoryRecycler = findViewById(R.id.category_list);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<CategoryModel> categoryList = new ArrayList<>();
        categoryList.add(new CategoryModel("Pop", R.drawable.image2,"https://www.youtube.com/watch?v=SC4xMk98Pdc"));
        categoryList.add(new CategoryModel("Rock", R.drawable.image4,"https://www.youtube.com/watch?v=ZZ6VhTBcc-c"));
        categoryList.add(new CategoryModel("Hip Hop", R.drawable.image3,"https://www.youtube.com/watch?v=GX8Hg6kWQYI"));
        categoryList.add(new CategoryModel("Jazz", R.drawable.image1,"https://www.youtube.com/watch?v=34Na4j8AVgA"));
        categoryList.add(new CategoryModel("Classical", R.drawable.image5,"https://www.youtube.com/watch?v=mzB1VGEGcSU"));
        categoryList.add(new CategoryModel("Trap", R.drawable.image9,"https://www.youtube.com/watch?v=qpgTC9MDx1o"));
        categoryList.add(new CategoryModel("Hard Rock", R.drawable.image6,"https://www.youtube.com/watch?v=eVTXPUF4Oz4"));
        categoryList.add(new CategoryModel("Pop Rock", R.drawable.image8,"https://www.youtube.com/watch?v=euCqAq6BRa4"));
        categoryList.add(new CategoryModel("Hard Core", R.drawable.image7,"https://www.youtube.com/watch?v=B9synWjqBn8"));

        categoryRecycler.setAdapter(new CategoryAdapter(this, categoryList));

        // 🔹 3. Setup Music RecyclerView
        RecyclerView musicRecycler = findViewById(R.id.music_list);
        musicRecycler.setLayoutManager(new LinearLayoutManager(this));

        List<MusicModel> musicList = new ArrayList<>();
        musicList.add(new MusicModel("Song One", R.drawable.image1, false,"https://www.youtube.com/watch?v=34Na4j8AVgA"));
        musicList.add(new MusicModel("Song Two", R.drawable.image3, true,"https://www.youtube.com/watch?v=GX8Hg6kWQYI"));
        musicList.add(new MusicModel("Song Three", R.drawable.image2, false,"https://www.youtube.com/watch?v=SC4xMk98Pdc"));
        musicList.add(new MusicModel("Song Fourth", R.drawable.image4, false,"https://www.youtube.com/watch?v=ZZ6VhTBcc-c"));
        musicList.add(new MusicModel("Song Fifth", R.drawable.image6, false,"https://www.youtube.com/watch?v=eVTXPUF4Oz4"));
        musicList.add(new MusicModel("Song Sixth", R.drawable.image5, false,"https://www.youtube.com/watch?v=mzB1VGEGcSU"));
        musicList.add(new MusicModel("Song Seventh", R.drawable.image7, false,"https://www.youtube.com/watch?v=B9synWjqBn8"));
        musicList.add(new MusicModel("Song Eighth", R.drawable.image9, false,"https://www.youtube.com/watch?v=qpgTC9MDx1o"));

        musicRecycler.setAdapter(new MusicAdapter(this, musicList));

        // 🔹 4. Setup unified search with suggestions
        AutoCompleteTextView searchField = findViewById(R.id.search_field);
        List<SearchItem> searchItems = new ArrayList<>();

        // Add Music items
        for (MusicModel music : musicList) {
            searchItems.add(new SearchItem(
                    music.getTitle(),
                    music.getImageResId(),
                    SearchItem.Type.MUSIC,
                    music.getYoutubeUrl()
            ));
        }

        // Add Category items
        for (CategoryModel category : categoryList) {
            searchItems.add(new SearchItem(
                    category.getName(),
                    category.getImageResId(),
                    SearchItem.Type.CATEGORY,
                    category.getYoutubeUrl()
            ));
        }

        SearchDropdownAdapter searchAdapter = new SearchDropdownAdapter(this, searchItems, 5);
        searchField.setAdapter(searchAdapter);

        searchField.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) searchField.showDropDown();
        });

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchAdapter.getFilter().filter(s);
                searchField.showDropDown();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        searchField.setOnItemClickListener((parent, view, position, id) -> {
            SearchItem selected = searchAdapter.getItem(position);
            if (selected != null) {
                // 🔹 Show Toast
                Toast.makeText(MainActivity.this, "Playing: " + selected.getName(), Toast.LENGTH_SHORT).show();

                // 🔹 Launch PlayerActivity
                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                intent.putExtra("VIDEO_URL", selected.getYoutubeUrl());
                startActivity(intent);

                // 🔹 Clear search text
                searchField.setText("");
            }
        });
    }

    private void setupSliderDots(int count, int currentIndex) {
        sliderDots.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageResource(i == currentIndex ? R.drawable.dot_selected : R.drawable.dot_unselected);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            sliderDots.addView(dot);
        }
    }

    private void updateSliderDots(int currentIndex) {
        int count = sliderDots.getChildCount();
        for (int i = 0; i < count; i++) {
            ImageView dot = (ImageView) sliderDots.getChildAt(i);
            dot.setImageResource(i == currentIndex ? R.drawable.dot_selected : R.drawable.dot_unselected);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sliderHandler.removeCallbacks(sliderRunnable);
    }
}