package ru.rocket.menu.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import ru.rocket.menu.R;
import ru.rocket.menu.fragments.main.ChatFragment;
import ru.rocket.menu.fragments.main.HomeFragment;

public class MainActivity extends AppCompatActivity {


    protected static MainActivity instanse;

    // Для поиска
    public static final Integer TYPE_SEARCH_TITLE = 0;
    public static final Integer TYPE_SEARCH_FILTER = 1;
    public static Integer TYPE_SEARCH_ACTIVE = TYPE_SEARCH_TITLE;
    public static String FILTER_OR_TITLE;

    public static Boolean THE_MAIN_BUTTON_FOR_VIDEO = true;
    // Для разрешения
    private static final int PERMISSION_REQUEST_CODE = 1;
    // Для перехода по фрагментам
    private static FragmentManager mFragmentManager;
    private static Fragment mContainer;
    private Fragment mFragment;

    // Название тулбара
    private TextView mToolbarTitle;

    // Для просмотра данных пользователя
    private SharedPreferences mSharedPreferences;

    // Кнопка для перехода к настройкам
    private ImageButton mSettingsBtn;

    // Настройки нижнего меню
    private BottomNavigationViewEx.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        switch (item.getItemId()) {

            case R.id.navigation_home:
                changeFragment(new HomeFragment());
                changeTitle("Главная");
                break;

/*
            case R.id.navigation_current_order:

                changeFragment(new OrderFragment());
                changeTitle("Заказ");
                break;
*/

            case R.id.navigation_chat:

                changeFragment(new ChatFragment());
                changeTitle("Чат");
                break;
            default:
                changeFragment(new ChatFragment());
                changeTitle("Чат");
                break;
        }

        return true;

    };

    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instanse = this;
        setContentView(R.layout.activity_main);


        // Загружаем данные о пользователе

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            loadDate();

        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{

                            Manifest.permission.WRITE_EXTERNAL_STORAGE

                    },
                    PERMISSION_REQUEST_CODE);

        }

        Toolbar mToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(mToolbar);
        mToolbarTitle = findViewById(R.id.toolbarTitle);

        BottomNavigationViewEx bnve = findViewById(R.id.bnve);
        bnve.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFragmentManager = getSupportFragmentManager();
        mContainer = mFragmentManager.findFragmentById(R.id.mainContainer);
        mFragment = new HomeFragment();
        mSettingsBtn = findViewById(R.id.logOut);

        // Переход к настройкам
        mSettingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
            finish();
        });


        if (mContainer == null) {

            mFragmentManager.beginTransaction().add(R.id.mainContainer, mFragment).addToBackStack(null).commit();
            changeTitle("Главная");

        }

        //   bnve.enableAnimation(true);
//        bnve.enableShiftingMode(false);
//        bnve.enableItemShiftingMode(false);

        int widthDp = 33;
        int heightDp = 33;
        int sp = 0;

//        bnve.setIconSize(widthDp, heightDp);
//        bnve.setTextSize(sp);

    }

    // Загрузка данных о пользователе
    private void loadDate() {
        /*
                mSharedPreferences = this.getSharedPreferences(StartActivity.PATH_TO_DATA_ABOUT_USER, Context.MODE_PRIVATE);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();


                String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();

        //        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        //                == PackageManager.PERMISSION_GRANTED) {
                File file = new File(folder, mSharedPreferences.getString(StartActivity.USER_IMAGE_PATH, "sdcard/")); // создать уникальное имя для файла основываясь на дате сохранения

                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                sPersonDate = new PersonDate(
                        mSharedPreferences.getString(StartActivity.USER_NAME, "Пользователь"),
                        mSharedPreferences.getString(StartActivity.USER_EMAIL, "user@email.ru"),
                        mSharedPreferences.getString(StartActivity.USER_AGE, "18"),
                        mSharedPreferences.getString(StartActivity.USER_CITY, "Москва"),
                        mSharedPreferences.getString(StartActivity.USER_IMAGE_PATH, "sdcard/"),
                        baos.toByteArray()

                );*/
//        } else {
//            finishAffinity();
//        }
    }

    // Установка названия тулбара
    private void changeTitle(String title) {

        mToolbarTitle.setText(title);

    }

    public static Resources getResourseForDraw() {
        return instanse.getResources();
    }

    //Смена фрагмента
    public static void changeFragment(Fragment fragment) {

        if (mContainer == null) {

            mFragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit();
            Log.d("DEBUG", "" + mFragmentManager.getBackStackEntryCount());
        }

    }

    public static void addFragment(Fragment fragment) {

        if (mContainer == null) {

            mFragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).addToBackStack("search").commit();
            Log.d("DEBUG", "" + mFragmentManager.getBackStackEntryCount());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length == 1) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                loadDate();

            }

        } else {

            finish();

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count <= 1) {
            getSupportFragmentManager().popBackStack();
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
        Log.d("DEBUG", "Осталось: " + getSupportFragmentManager().getBackStackEntryCount());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        thread.stop();
    }
}
