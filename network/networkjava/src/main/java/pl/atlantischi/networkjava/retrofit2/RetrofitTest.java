package pl.atlantischi.networkjava.retrofit2;

import java.util.List;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.internal.schedulers.ImmediateThinScheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 14/06/2017.
 *
 * @author lx
 */

public class RetrofitTest {

    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<Repo>> reposCall = service.listRepos("lx36301766");
                reposCall.enqueue(new Callback<List<Repo>>() {
                    @Override
                    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                        System.out.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Repo>> call, Throwable t) {
                        System.out.println(t);
                    }
                });

//        Observable<List<Repo>> observable = service.listRepos("lx36301766");
//        observable.observeOn(ImmediateThinScheduler.INSTANCE)
//                .subscribe(new Observer<List<Repo>>() {
//
//                    @Override
//                    public void onError(Throwable e) {
////                        Toast.makeText(getApplicationContext(),
////                                e.getMessage(),
////                                Toast.LENGTH_SHORT)
////                                .show();
//                    }
//
//                    @Override
//                    public void onComplete() {
////                        Toast.makeText(getApplicationContext(),
////                                "Completed",
////                                Toast.LENGTH_SHORT)
////                                .show();
//                    }
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(List<Repo> dessertItemCollectionDao) {
////                        Toast.makeText(getApplicationContext(),
////                                dessertItemCollectionDao.getData().get(0).getName(),
////                                Toast.LENGTH_SHORT)
////                                .show();
//                    }
//                });

    }

}
