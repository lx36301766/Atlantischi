package pl.atlantischi.rx;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xuanluo on 16/11/28.
 */

public class RxAndroidTest extends AppCompatActivity {

    public static final String TAG = RxAndroidTest.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        test1();

        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        });
        observable = Observable.just("1", "2");

        Observable.just(1, 2, 3, 4, 5)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer i) {
                        return "This is " + i;
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }

    private void test1() {
        Observable.from(getCommunities())
                .flatMap(new Func1<Community, Observable<House>>() {
                    @Override
                    public Observable<House> call(Community community) {
                        return Observable.from(community.houses);
                    }
                })
                .filter(new Func1<House, Boolean>() {
                    @Override
                    public Boolean call(House house) {
                        return house.id > 2;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<House>() {

                    @Override
                    public void call(House house) {
                        Log.d(TAG, "call: house, id=" + house.id);
                    }
                });
    }

    @NonNull
    private List<Community> getCommunities() {
        List<Community> communityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Community community = new Community();
            for (int j = 0; j < 5; j++) {
                House house = new House();
                house.id = j;
                community.houses.add(house);
            }
            community.id = "Community-" + i;
            communityList.add(community);
        }
        return communityList;
    }

}
