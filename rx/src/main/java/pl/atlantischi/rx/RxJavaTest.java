package pl.atlantischi.rx;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

/**
 * Created by xuanluo on 2016/12/30.
 */

public class RxJavaTest {

    public static void main(String[] args) {


        List<House> houses = new ArrayList<>();
        houses.add(new House("中粮·海景壹号", "中粮海景壹号新出大平层！总价4500W起"));
        houses.add(new House("竹园新村", "满五唯一，黄金地段"));
        houses.add(new House("中粮·海景壹号", "毗邻汤臣一品"));
        houses.add(new House("竹园新村", "顶层户型，两室一厅"));
        houses.add(new House("中粮·海景壹号", "南北通透，豪华五房"));
        Observable<GroupedObservable<String, House>> groupByCommunityNameObservable = Observable.from(houses)
                .groupBy(new Func1<House, String>() {

                    @Override
                    public String call(House house) {
                        return house.communityName;
                    }
                });

        Observable.concat(Observable.from(houses)
                .groupBy(new Func1<House, String>() {

                    @Override
                    public String call(House house) {
                        return house.communityName;
                    }
                }))
                .subscribe(new Action1<House>() {
                    @Override
                    public void call(House house) {
                        System.out.println("小区:"+house.communityName+"; 房源描述:"+house.desc);
                    }
                });

    }

    private static void scan() {
        Observable.just(1, 2, 3, 4, 5)
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.print(integer + " ");
                    }
                });
    }

    private static void map() {
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

    private static void flatmap() {
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
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<House>() {

                    @Override
                    public void call(House house) {
                        System.out.println("call: house, id=" + house.id);
                    }
                });
        Observable.from(getCommunities())
                .flatMapIterable(new Func1<Community, Iterable<House>>() {
                    @Override
                    public Iterable<House> call(Community community) {
                        return community.houses;
                    }
                })
                .subscribe(new Action1<House>() {
                    @Override
                    public void call(House house) {

                    }
                });
    }

    private static List<Community> getCommunities() {
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
