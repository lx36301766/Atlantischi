package pl.atlantischi.networkjava.retrofit2;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created on 14/06/2017.
 *
 * @author lx
 */

public interface GitHubService {

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

//    @GET("users/{user}/repos")
//    Observable<List<Repo>> listRepos(@Path("user") String user);

}
